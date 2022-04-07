# `Spring`

`Spring`是一个超级大工厂，其核心是控制反转`IOC`和面向切面编程`AOP`

## 1.`IOC (Inversion Of Control)`

`IOC`控制反转是一种思想，一种概念，原本属于代码本身的对象控制权现在交给容器。由`Spring`容器来完成对象的创建以及属性的装配还有依赖管理。

`IOC`思想在`JavaWeb`的学习其实就遇到过了，当时我们并没有直接创建`Servlet`对象，但是我们却可以实实在在的使用到它，原因是`Tomcat`的存在，他帮我们创建了`Servlet`对象，这就是控制反转思想的一种体现。

控制反转的思想的具体实现就是依赖注入，程序代码不做定位，这些工作全部交给容器来完成。

`Maven ---> pom.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zwm</groupId>
    <artifactId>SpringStudy</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
</project>
```

`SomeService`接口：

```java
package com.zwm.service;

public interface SomeService {
    public abstract void doSome();
}
```

`SomeServiceImpl`实现类：

```java
package com.zwm.service.impl;

import com.zwm.service.SomeService;

public class SomeServiceImpl implements SomeService {

    public SomeServiceImpl() {
        System.out.println("执行 SomeServiceImpl 中的无参构造方法");
    }

    @Override
    public void doSome() {
        System.out.println("执行 SomeServiceImpl 中的 doSome() 方法");
    }
}
```

`Spring`配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="someService0" class="com.zwm.service.impl.SomeServiceImpl"/>
    <bean id="someService1" class="com.zwm.service.impl.SomeServiceImpl"/>
    <bean id="date" class="java.util.Date"/>
</beans>
```

`App`测试类：

```java
package com.zwm;

import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String springConfig = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeServiceImpl someService0 = (SomeServiceImpl) applicationContext.getBean("someService0");
        SomeServiceImpl someService1 = (SomeServiceImpl) applicationContext.getBean("someService1");
        someService0.doSome();
        someService1.doSome();
        Date date = (Date) applicationContext.getBean("date");
        System.out.println(date.toString());
    }
}
```

**小问题：`ApplicationContext`容器中对象的装配时机？**

对象将在容器初始化的时候一次性全部装配好然后存储在内存当中，容器的初始化就称为注入，当代码中有需要的时候直接从内存中拿来把这个对象拿出来使用就可以了，这种方式虽然占用内存但是效率比较高。大部分应用场景下还是可以接受的。这一整个的装配其实就是`Spring`容器`ApplicationContext`将对象都装好了，要用的时候直接往外拿就可以了，是不是觉得很方便？没错，就是如此方便。

### 1.1 `IOC`之基于`XML`的依赖注入

#### 1.1.1 `set`注入

##### 1.1.1.1 `set`注入之简单类型

通过`setter`方法进行注入，这种方法注入方式简单直观，需要注意的是`Spring`容器注入创建对象，如果没有添加构造器`<constructor-arg>`则需要一个无参构造器，否则无法创建对象，学习过`Java`基础理应知道创建对象是通过构造器创建的，并且有了有参构造器，无参构造器不再默认创建需要手动创建。

`pom.xml`还是跟之前的一样不变，这里不再贴出来。

`applicationContext1.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="student" class="com.zwm.pojo.Student">
        <property name="name" value="ZhangSan"/>
        <property name="age" value="18"/>
    </bean>
</beans>
```

测试用的`Student`类：

```java
package com.zwm.pojo;

public class Student {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

`App1`测试类：

```java
import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App1 {
    public static void main(String[] args) {
        String springConfig = "applicationContext1.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
```

##### 1.1.1.2 `set`注入之引用类型

当指定的对象的属性值为另一对象的时候，也就是属性中包含引用类型数据的时候，此时可以使用`ref`指定他们的引用关系，但是就算是引用类型，这里还是通过`setter`方法注入的：

`applicationContext`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="school" class="com.zwm.pojo.School">
        <property name="schoolName" value="华南理工大学"/>
        <property name="schoolAddress" value="广东省广州市天河区五山路381号"/>
    </bean>
    <bean id="student" class="com.zwm.pojo.Student">
        <property name="name" value="kroll"/>
        <property name="age" value="24"/>
        <property name="mySchool" ref="school"/>
    </bean>
</beans>
```

`School`实体类：

```java
package com.zwm.pojo;

public class School {
    private String schoolName;
    private String schoolAddress;

    public School() {
    }

    public School(String schoolName, String schoolAddress) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolName='" + schoolName + '\'' +
                ", schoolAddress='" + schoolAddress + '\'' +
                '}';
    }
}
```

`Student`实体类：

```java
package com.zwm.pojo;

public class Student {
    private String name;
    private int age;
    private School school;

    public Student() {
    }

    public Student(String name, int age, School school) {
        this.name = name;
        this.age = age;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setMySchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }
}
```

`App`测试类：

```java
package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
        String springConfig = "applicationContext2.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
```

#### 1.1.2 构造注入

`Spring`容器初始化时通过有参构造器创建对象：

`applicationContext3.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="school" class="com.zwm.pojo.School">
        <property name="schoolName" value="华南理工大学"/>
        <property name="schoolAddress" value="广东省广州市天河区五山路381号"/>
    </bean>
    <bean id="student" class="com.zwm.pojo.Student">
        <constructor-arg name="name" value="kroll"/>
        <constructor-arg name="age" value="24"/>
        <constructor-arg name="school" ref="school"/>
    </bean>
</beans>
```

`App3`实体类：

```java
package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App3 {
    public static void main(String[] args) {
        String springConfig = "applicationContext3.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
```

#### 1.1.3 自动注入引用类型

之前的在对引用类型进行注入时采用的是：`ref`方式，这次直接加一个`autowired`就可以自动注入，自动注入有两种，一种是按照名称自动注入，一种是按照类型自动注入，这里先说说前者，其实这种方法的本质还是：`<property name="mySchool" ref="school"/>`

`name`值还是需要跟`setter`方法中的名称所对应，所以本质上是一样的，并没有什么改变：

`applicationContext4.xml`：







