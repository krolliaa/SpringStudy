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

#### 1.1.3 自动注入引用类型`byName`

之前的在对引用类型进行注入时采用的是：`ref`方式，这次直接加一个`autowired`就可以自动注入，自动注入有两种，一种是按照名称自动注入，一种是按照类型自动注入，这里先说说前者，其实这种方法的本质还是：`<property name="mySchool" ref="school"/>`

`name`值还是需要跟`setter`方法中的名称所对应，所以本质上是一样的，并没有什么改变：

`applicationContext4.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="mySchool" class="com.zwm.pojo.School">
        <property name="schoolName" value="华南理工大学"/>
        <property name="schoolAddress" value="广东省广州市天河区五山路381号"/>
    </bean>
    <bean id="student" class="com.zwm.pojo.Student" autowire="byName">
        <property name="name" value="kroll"/>
        <property name="age" value="3"/>
    </bean>
</beans>
```

`App4`实体类：

```java
package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App4 {
    public static void main(String[] args) {
        String springConfig = "applicationContext4.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
```

#### 1.1.4 自动注入引用类型`byType`

该方式通过类路径去寻找引用数据类型的配置，路径匹配不一定要一模一样，同源即可，要么是实体类本身要么存在继承关系要么存在实现关系：

`applicationContext5.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="school" class="com.zwm.pojo.PrimarySchool">
        <property name="schoolName" value="华南理工大学"/>
        <property name="schoolAddress" value="广东省广州市天河区五山路381号"/>
    </bean>
    <bean id="student" class="com.zwm.pojo.Student" autowire="byType">
        <property name="name" value="kroll"/>
        <property name="age" value="3"/>
    </bean>
</beans>
```

`PrimarySchool`继承`School`的子类：

```java
package com.zwm.pojo;

public class PrimarySchool extends School {
}
```

`App5`实体类：

```java
package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App5 {
    public static void main(String[] args) {
        String springConfig = "applicationContext5.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
```

#### 1.1.5 总配置文件

当有多个`Bean`的时候，可以为每一个`Bean`单独配置一个`Spring`配置文件，然后通过`import`合体到一个总配置文件即可：`spring-school.xml` + `spring-student.xml` + `applicationContext6.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="student" class="com.zwm.pojo.Student" autowire="byType">
        <property name="name" value="kroll"/>
        <property name="age" value="3"/>
    </bean>
</beans>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="school" class="com.zwm.pojo.PrimarySchool">
        <property name="schoolName" value="华南理工大学"/>
        <property name="schoolAddress" value="广东省广州市天河区五山路381号"/>
    </bean>
</beans>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <import resource="classpath:spring-*"/>
</beans>
```

### 1.2 `IOC`之基于注解`Annotation`的`DI`

基于注解的依赖注入不再需要再配置文件中配置`Bean`相关配置信息，只需要在每个`Bean`实体类上加入注解然后在配置文件中添加组件扫描器`context:component-scan`【上下文：组件扫描器】，该组件扫描器可以扫描配置包下的所有注解，使添加的注解产生作用。

`applicationContext7.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <bean id="student" class="com.zwm.pojo.Student"/>
    <context:component-scan base-package="com.zwm.pojo"/>
</beans>
```

`Student`实体类：

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;

public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
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

`App7`测试类：

```java
package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App7 {
    public static void main(String[] args) {
        String springConfig = "applicationContext7.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
```

#### 1.2.1 组件扫描器指定多个包的`3`种方式

第一种方式：使用多个`context:component-scan`指定不同包的路径

第二种方式：使用分隔符指定多个不同包的路径，分隔符可以使用`,`也可以使用`;`和`[空格]`

第三种方式：使用`base-package`指定到父包涵盖掉需要扫描的注解，虽然快捷方便了，但是需要扫描的包就多了，是否选择此类型看具体需求

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <bean id="student" class="com.zwm.pojo.Student"/>
    <!--使用一条条列举扫描-->
    <context:component-scan base-package="com.zwm.pojo"/>
    <context:component-scan base-package="com.zwm.service"/>
    <!--使用分隔符添加组件扫描器 逗号 分号 空格-->
    <context:component-scan base-package="com.zwm.pojo,com.zwm.service"/>
    <context:component-scan base-package="com.zwm.pojo;com.zwm.service"/>
    <context:component-scan base-package="com.zwm.pojo com.zwm.service"/>
    <!--使用父类扫描多个包-->
    <context:component-scan base-package="com.zwm"/>
</beans>
```

#### 1.2.2 创建对象的注解

具体用法为：`@Component(value = "xxxxxx")`，这里的`value`值相当于`xml`中`<bean>`中的`id`，可以不指定默认就是类名的小写名称。

`@Compoent`常用于创建没有什么具体含义的普通`Bean`对象，除此之外，创建对象的注解还有：`@Repository`还有`@Service`以及`@Controller`，这三个的区别就在于：`@Repository`常用于创建持久层的对象，`@Service`常用于创建业务逻辑层的对象，`@Controller`常用于创建用户访问层的对象，这三个注解其实就是`@Component`的细化注解，包括了持久层、业务逻辑层以及用户访问层的对象注解。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.zwm.pojo"/>
</beans>
```

```java
package com.zwm;

import com.zwm.pojo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App8 {
    public static void main(String[] args) {
        String springConfig = "applicationContext8.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        Student student = (Student) applicationContext.getBean("myStudent");
        System.out.println(student.toString());
    }
}
```

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "myStudent")
public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
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

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "school")
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

    @Value(value = "华南理工大学")
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    @Value(value = "广东省广州市天河区五山路381号")
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

#### 1.2.3 基本数据类型赋值的注解

具体用法为：直接在要赋值的属性上使用`@Value(value = "xxxxxx")`即可，属性值`value`可以省略，类中无需`setter`方法就可以直接赋值，当然也可以有`setter`方法，然后你也可以直接在`setter`方法上直接使用`@Value(value = "xxxxxx")`，如果属性上面和`setter`方法上面都有`@Value`注解，以`setter`方法上的注解为基准

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "myStudent")
public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
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

    @Value(value = "乌拉！")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Value(value = "100")
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

#### 1.2.4 引用数据类型的注解`@Autowired + @Qualifier`

要想在引用数据类型上使用注解，可以使用`@Autowired`，该注解默认是按照`byType`方式进行的，但是这里有一个跟`xml`不一样的点就是，基于`xml`的依赖注入只要是同源的我们配父类或者接口就可以，在`xml`里头，会自动的去找继承父类的子类以及实现接口的实现类。

但是注解的依赖注入不一样，即使是有同源的，只要对象中的对象属性不是特定的那个类就不回赋予属性，比如这里的`private School school`然后给予一个`@Autowired`注解，此时有另外一个继承了`School`类的子类`PrimarySchool`是`School`的子类，在`School`类中并不给属性赋值，但是在`PrimartSchool`，你觉得他会自动寻找子类吗？其实并不会，默认是按照`byType`，但是它只认`byType`

如果`byType`方式注入不成，就会转换成`byName`的方式注入。

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "myStudent")
public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
    private int age;
    @Autowired
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

    @Value(value = "乌拉！")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Value(value = "100")
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

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "school")
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

    @Value(value = "华南理工大学")
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    @Value(value = "广东省广州市天河区五山路381号")
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

指定使用`byName`的方式进行自动注入，没找到的话不会自动转化为`byType`方式：

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "myStudent")
public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
    private int age;
    @Autowired
    @Qualifier(value = "mySchool")
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

    @Value(value = "乌拉！")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Value(value = "100")
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

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "mySchool")
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

    @Value(value = "华南理工大学")
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    @Value(value = "广东省广州市天河区五山路381号")
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

关于`@Autowired`有一个默认值`required`默认值为`true`，表示如果`Spring`容器匹配对象属性的时候不匹配上就终止程序运行然后抛出异常，如果改为`false`表示该引用数据类型的属性匹配不上，程序照常运行，没有匹配上的属性值为`null`

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "myStudent")
public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
    private int age;
    @Autowired(required = false)
    @Qualifier(value = "mySchoollllllll")
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

    @Value(value = "乌拉！")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Value(value = "100")
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

#### 1.2.5 自动注入`@Resource`

`@Resource`是`JDK`提供的，默认是按照`byName`的方式进行注入，若名称无法注入就按照`byType`的方式进行注入，这跟`@Autowired`恰好相反。

```java
package com.zwm.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component(value = "myStudent")
public class Student {
    @Value(value = "kroll")
    private String name;
    @Value(value = "3")
    private int age;
    //@Autowired(required = false)
    //@Qualifier(value = "mySchoollllllll")
    @Resource(name = "mySchool")
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

    @Value(value = "乌拉！")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Value(value = "100")
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

### 1.3 比较两种依赖注入的方式

1. 基于`XML`依赖注入的方式

   > - 优点：代码和配置相分离，修改了`XML`配置文件无需重新编译，只需要重启服务器就会加载新的配置信息
   > - 缺点：不方便不直观编写麻烦，在大型项目上使用将变得非常复杂

2. 基于`Annotation`依赖注入的方式

   > - 优点：方便直观高效【代码量少，并不用像`XML`那样书写大量配置信息】
   > - 缺点：直接写到源代码中，若需要修改则需要重新编译

【注：经常需要修改的配置使用`Annotation`依赖注入，不经常修改的不动的使用`XML`进行依赖注入 ---> 选用哪个的关键点在于是否需要修改大量的源代码】

## 2. `AOP(Aspect Oriented Programming)`面向切面编程

`AOP`就是采用动态代理实现的，它是可以在运行期通过动态代理实现程序功能统一维护的一种技术，想要搞明白动态代理，就需要先搞明白`Java`反射机制。其优点无非两点：一是减少重复二是专注业务。

所谓的切面就是由交叉业务代码封装二厂的，然后织入到主业务逻辑代码中。而交叉业务呢指的是通用的与主业务逻辑无关，如安全检查、事务、日志、缓存等。利用好`AOP`可以对业务逻辑的各个部分进行隔离拆分，从而降低了各个业务逻辑的耦合度，提高了程序的重用性，因为对业务逻辑的各个部分进行了隔离，那么一组一组的人员可以负责开发各个业务逻辑部分，这样就大大提升了开发效率。如果不使用`AOP`，就会出现主业务逻辑代码和非业务逻辑代码相互纠缠，混合在一起的情况。这样导致主业务逻辑变得很不清晰透彻，非常冗余，还降低了开发的效率等问题。

`AspectJ`很好的实现了`AOP`概念，并且简单快捷还支持注解开发，`Spring`才不傻傻的花费大量的功夫自己搞一套，所以`Spring`将`AspectJ`直接引入到了自己的框架中。

注：面向切面编程`AOP`只是面向对象编程`OOP`的一种补充

### 2.1 `AOP`相关术语

> 1. 切面`Aspect`：泛指交叉业务逻辑对主业务逻辑的增强
> 2. 连接点`JoinPoint`：可以被切面织入的具体方法，通常业务接口中的方法都是连接点
> 3. 切入点`Pointcut`：一个或者多个连接点，可以被切面织入的具体方法的集合
> 4. 目标对象`Target`：指要被增强的对象也就是包含主业务逻辑的类的对象，对象的模板即类称为目标类也就是包含主业务逻辑的类
> 5. 通知`Advice`：切面执行的时间

【注：若某个方法被`final`关键字修饰则是无法作为连接点或者切入点的，因为被`final`关键字修饰的方法/属性都是完美的，不可更改的，所以也就无法增强，不能被增强也就无法作为连接点】

### 2.2 `AspectJ`切入点表达式

切入点表达式跟定义方法是一样的：`public int com.zwm.test.getSum(int a, int b) throws Exception{}`，定义切入点有三部分是必须定义的：返回值类型 方法名 参数

- 访问权限修饰符【可选】
- 返回值类型【必须】
- 包名【可选】
- 方法名【必须，包括参数类型和参数个数】
- 异常【可选】

`execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern)`

举例：

```java
指定切入点为任意公共方法： execution(public * *(..))
指定切入点为任意以set打头的方法名的方法： execution(* set*(..))
指定切入点为 com.xyz.service 包下任意类的任意方法： execution(* com.xyz.service.*.*(..))
指定切入点为 com.xyz.service 包及其子包下任意类的任意方法： execution(* com.xyz.service..*.*(..))
指定切入点为任意一级包下或者一级包下所有类包括子包的`service`包下的任意类的任意方法： execution(* *..service.*.*(..))
指定切入点为任意一级包下所有service包下任意类的任意方法： execution(* *.service.*.*(..))
指定切入点为任意一级包下所有ISomeService接口中的任意方法： execution(* *.ISomeService.*(..))
指定切入点为 com.xyz.service.IAccountService 下所有方法： execution(* com.xyz.service.IAccountService.*(..))
指定切入点为 IAccountService 若为接口则表示该接口及其实现类任意方法，IAccountService若为类
表示该类及其子类任意方法： execution(* *.IAccountService+.*(..))
指定切入点为所有的joke()方法，且第一个参数数据类型必须为String，第二个为int，如果参数类型是java.lang包下的，则可以省略全类名，如果不是则必须使用全限定类名，比如第一个参数数据类型是List，则需要描述为： execution(* *.joke(java.util.List, int))
execution(* *.joke(String, int))
指定切入点为指定所有的joke()方法为切入点，且一共只有两个参数，第一个参数数据类型必须为
String，第二个参数数据类型为任意数据类型： execution(* joke(String,..))
指定切入点为指定所有的joke()方法为切入点，只有一个参数且必须为Object类型： execution(* *.joke(Object))
指定切入点为指定所有的joke()方法为切入点，只有一个参数，数据类型可以为Object类型也可以是其子类: execution(* *.joke(Object+))
```

### 2.3 `AspectJ`的五种通知类型

> 1. 前置通知
> 2. 后置通知
> 3. 环绕通知
> 4. 异常通知
> 5. 最终通知

### 2.4 `AspectJ`基于注解的面向切面编程的实现

#### 2.4.1 前置通知`@Before`

`pom.xml`引入`AOP`依赖：

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
            <artifactId>spring-aspects</artifactId>
            <version>5.3.17</version>
        </dependency>
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

`SomeService`主业务逻辑代码接口实现：

```java
package com.zwm.service;

public interface SomeService {
    public abstract void doSome(String name, int age);

    public abstract void doOther();
}
```

`SomeServiceImpl`接口实现类：

```java
package com.zwm.service.impl;

import com.zwm.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome(String name, int age) {
        System.out.println("========SomeService的doSome()方法========");
    }

    @Override
    public void doOther() {
        System.out.println("========SomeService的doOther()方法========");
    }
}
```

`MyAspectBefore`前置通知：

```java
package com.zwm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyBeforeAspect {
    @Before(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public void myBefore(JoinPoint joinPoint) {
        System.out.println("前置通知获取方法全类名：" + joinPoint.getSignature());
    }
}
```

`ApplicationContext14.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    <aop:aspectj-autoproxy proxy-target-class="true"/>
    <context:component-scan base-package="com.zwm"/>
</beans>
```

`App14`测试类：

```java
package com.zwm;

import com.zwm.service.SomeService;
import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App14 {
    public static void main(String[] args) {
        String springConfig = "applicationContext14.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeService someService = (SomeServiceImpl) applicationContext.getBean("someServiceImpl");
        someService.doSome("ABC", 3);
    }
}
```

#### 2.4.2 后置通知`@AfterReturning`

`MyAfterReturning`：

```java
package com.zwm.aspect;

import com.zwm.pojo.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class MyAfterReturningAspect {
    @AfterReturning(value = "execution(* com.zwm.service.impl.SomeServiceImpl.*(..))", returning = "student")
    public void myAfterReturning(JoinPoint joinPoint, Student student) {
        System.out.println("该方法的全类名 - 后置通知获取：" + joinPoint.getSignature());
        System.out.println("该方法的方法名 - 后置通知获取：" + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                System.out.println("该方法的形式参数：" + arg);
            }
        }
        System.out.println("后置通知：doSome()返回参数值后打印当前时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("在后置通知执行方法之前的 student 数据：" + student.toString());
        student.setName("DEF");
        System.out.println("在后置通知执行方法之前的 student 数据：" + student.toString());
    }
}
```

`applicationContext15.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    <aop:aspectj-autoproxy proxy-target-class="true"/>
    <context:component-scan base-package="com.zwm"/>
</beans>
```

`App15`测试类：

```java
package com.zwm;

import com.zwm.pojo.Student;
import com.zwm.service.SomeService;
import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App15 {
    public static void main(String[] args) {
        String springConfig = "applicationContext15.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeService someService = (SomeServiceImpl) applicationContext.getBean("someServiceImpl");
        Student student = someService.doSome("ABC", 3);
        System.out.println("执行 doSome 方法返回的数据：" + student.toString());
    }
}
```

可以看到后置通知的方法执行顺序是：执行方法位置 ---> 后置通知位置 ---> 调用方法位置

#### 2.4.3 环绕通知`@Around`

`applicationContext16.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    <aop:aspectj-autoproxy proxy-target-class="true"/>
    <context:component-scan base-package="com.zwm"/>
</beans>
```

`App16`测试类：

```java
package com.zwm;

import com.zwm.service.SomeService;
import com.zwm.service.impl.SomeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App16 {
    public static void main(String[] args) {
        String springConfig = "applicationContext16.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        SomeService someService = (SomeServiceImpl) applicationContext.getBean("someServiceImpl");
        Object result = (Object) someService.doSome("ABC", 3);
        System.out.println(result);
    }
}
```

`MyAroundAspect`切面类：

```java
package com.zwm.aspect;

import com.zwm.pojo.Student;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class MyAroundAspect {
    @Around(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public Object MyAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        System.out.println("环绕通知方法执行前的时间：" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println("环绕通知获取全类名：" + proceedingJoinPoint.getSignature());
        System.out.println("环绕通知获取方法名：" + proceedingJoinPoint.getSignature().getName());
        Object[] args = proceedingJoinPoint.getArgs();
        String name = "";
        if (args != null && args.length > 0) {
            for (Object arg : args) System.out.println("环绕通知获取方法形式参数：" + arg);
            name = (String) args[0];
        }
        if ("ABC".equals(name)) {
            System.out.print("环绕通知执行方法，返回方法执行结果保存在 result 中：");
            result = proceedingJoinPoint.proceed(args);
        }
        System.out.print("方法执行完毕，可以修改方法的返回结果：");
        if (result != null && result instanceof Student) ((Student) result).setName("DEF");
        return result;
    }
}
```

环绕通知是众多通知之神，它可以干其它所有通知的事情，非常之强大，所以也经常使用到环绕通知。

#### 2.4.4 异常通知`@AfterThrowing`

```java
package com.zwm.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAfterThrowableAspect {
    @AfterThrowing(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))", throwing = "throwable")
    public void myAfterThrowable(Throwable throwable) throws InterruptedException {
        System.out.println("异常通知，方法执行抛出异常前执行：" + throwable.getMessage());
        Thread.sleep(3000);
    }
}
```

#### 2.4.5 最终通知`@After`

总是会执行的通知就是最终通知：

```java
package com.zwm.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAfterAspect {
    @After(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public void myAfterThrowable() {
        System.out.println("最终通知，最后且总是会执行的通知");
    }
}
```

#### 2.4.6 定义切入点

```java
package com.zwm.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyPointCut {
    @AfterThrowing(value = "myPointcut()")
    public void myAfterThrowing() {
        System.out.println("最终通知：最终总是会执行的方法");
    }

    @Pointcut(value = "execution(* com.zwm.service.impl.SomeServiceImpl.doSome(..))")
    public void myPointcut() {
    }
}
```

## 3. `Spring`集成`MyBatis`

### 3.1 使用`JDBC`访问数据库

`JDBCReview`：

```java
package com.zwm;

import java.sql.*;

public class App19 {
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //2.创建连接
        String url = "jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        //3.获取连接对象
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL
        String sql = "select * from student";
        //5.处理结果集
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println(id + " " + name + " " + age);
        }
        //6.关闭资源
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }
}
```

### 3.2 使用`MyBatis`访问数据库

`User`实体类【这里使用了`lombok`】：

```java
package com.zwm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
}
```

`UserMapper`接口：

```java
package com.zwm.mapper;

import com.zwm.pojo.User;

import java.util.List;

public interface UserMapper {
    public abstract List<User> selectAllUsers();
}
```

`UserMapper.xml`：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zwm.mapper.UserMapper">
    <select id="selectAllUsers" resultType="user">
        select * from student
    </select>
</mapper>
```

`jdbc.properties`：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=123456
```

`mybatis.xml`配置文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="jdbc.properties"/>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <typeAliases>
        <typeAlias type="com.zwm.pojo.User" alias="user"/>
    </typeAliases>
    <environments default="mysql">
        <environment id="mysql">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="mapper/userMapper.xml"/>
    </mappers>
</configuration>
```

`App20`测试类：

```java
package com.zwm;

import com.zwm.mapper.UserMapper;
import com.zwm.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class App20 {
    public static void main(String[] args) throws IOException {
        String mybatisConfig = "mybatis.xml";
        InputStream mybatisInputStream = Resources.getResourceAsStream(mybatisConfig);
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(mybatisInputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.selectAllUsers();
        for(User user : userList) System.out.println(user.toString());
    }
}
```

### 3.3 `Spring`结合`MyBatis`

`pom.xml`导入必要的`jar`包：

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
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.2.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.16</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.4</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.18</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.27</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.3.17</version>
        </dependency>
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

`jdbc.properties`：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=123456
jdbc.maxActive=10
```

`pojo`层 - `User`实体类：

```java
package com.zwm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
}
```

`Dao`层 - `UserMapper`接口：

```java
package com.zwm.mapper;

import com.zwm.pojo.User;

import java.util.List;

public interface UserMapper {
    public abstract List<User> selectAllUsers();
}
```

`Dao层` - `resources/mapper/UserMapper.xml`：

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zwm.mapper.UserMapper">
    <select id="selectAllUsers" resultType="user">
        select * from student
    </select>
</mapper>
```

`mybatis`配置文件`mybatis19.xml`：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <typeAliases>
        <package name="com.zwm.pojo"/>
    </typeAliases>
    <mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>
</configuration>
```

`service`层 - `UserService`接口：

```java
package com.zwm.service.impl;

import com.zwm.mapper.UserMapper;
import com.zwm.pojo.User;
import com.zwm.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> selectAllUsers() throws IOException {
        return userMapper.selectAllUsers();
    }
}
```

`Spring`配置文件 - `applicationContext19.xml`：

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd   http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <aop:aspectj-autoproxy proxy-target-class="true"/>
    <context:component-scan base-package="com.zwm"/>
    <context:property-placeholder location="jdbc.properties"/>
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="${jdbc.maxActive}"/>
    </bean>
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="mybatis19.xml"/>
        <property name="dataSource" ref="druidDataSource"/>
    </bean>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
        <property name="basePackage" value="com.zwm.mapper"/>
    </bean>
    <bean id="userService" class="com.zwm.service.impl.UserServiceImpl">
        <property name="userMapper" ref="userMapper"/>
    </bean>
</beans>
```

`App21`测试类：

```java
package com.zwm;

import com.zwm.pojo.User;
import com.zwm.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class App21 {
    public static void main(String[] args) throws IOException {
        String springConfig = "applicationContext19.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        UserService userService = (UserService) applicationContext.getBean("userService");
        List<User> userList = userService.selectAllUsers();
        for(User user : userList) System.out.println(user.toString());
    }
}
```

## 4. `Spring`事务

`Spring`可以使用注解定义事务，包括定义：

> - 事务隔离级别`isolation`
> - 事务传播行为`propagation`
> - 超时时间`timeout`[默认值为`-1`]
> - 对数据库操作是否为只读`readonly`
> - `rollbackFor`指定需要回滚的异常类【默认值为数组类】
> - `rollbackForClassName`指定需要回滚的异常类的类名【默认值为`String`类型的数组类】
> - `noRollbackFor`指定不需要回滚的异常类
> - `noRollbackForClassName`指定不需要回滚的异常类。

数据库的饰事务隔离级别`isolation`一共有五个：读未提交、读已提交、可重复读、序列化、默认，即：`READ_UNCOMMITED`、`READ_COMMITED`、`REPEATABLE_READ`、`SERIALIZABLE`、`DEFAULT`

事务传播行为一共有七个，前三个最常用：需要事务传播行为、需要新的事务传播行为、支持事务传播行为、强制事务传播行为、嵌套事务传播行为、永远不需要事务传播行为、不支持事务传播行为

> `PROPAGATION_REQUIRED`：如果上下文中已经存在事务那么就加入到这个事务，如果当前上下文中不存在事务则新建事务。该级别的事务传播可以满足处理大多数业务场景。
>
> `PROPAGATION_SUPPOTRS`：如果上下文存在事务则支持事务加入，如果没有事务则使用非事务的方式执行。所以说，并非所有的包含在事务操作的代码都会有实物支持。该级别的事务传播通常是用来处理那些并非原子性的核心业务逻辑操作。应用场景较少。
>
> `PROPAGATION_MANDATORY`：强制性要求上下文中必须存在事务否则抛出异常。该级别的事务传播是有效控制上下文调用遗漏添加事务控制的保证手段。举个例子：一段代码不能被调用执行，但是一旦被调用就必须有事务包含的情况就可以使用该传播级别。
>
> `PROPAGATION_REQUIRES_NEW`：每次都会创建一个新的事务，如果当前上下文存在事务就将事务挂起，执行当前新建事务，等新建事务完成之后上下文事务恢复再执行。这是一个很有用的传播级别，举一个应用场景：现在有一个发送`100`个红包的操作，在发送之前，要做一些系统的初始化、验证、数据记录操作，然后发送`100`封红包，然后再记录发送日志，发送日志要求`100%`的准确，如果日志不准确，那么整个父事务逻辑需要回滚。
>
> `PROPAGATION_NOT_SUPPORTED`：如果上下文中存在事务就挂起改事务，执行当前逻辑，执行结束后再恢复上下文的事务，这个级别的事务传播的好处就在于这样的事务就可以尽可能的缩小。我们知道一个事务越大，它存在的风险也就越多。所以在处理事务的过程中应该尽可能的缩小事务的范围。比如一段代码，是每次逻辑操作都必须调用的，比如循环1000次的某个非核心业务逻辑操作。这样的代码如果包在事务中，势必造成事务太大，导致出现一些难以考虑周全的异常情况，所以事务的这个传播级别就派上用场了。用当前级别的事务模板包含起来就可以了。
>
> `PROPAGATION_NEVER`：比`PROPAGATION_SUPPORTED`更严格，这个事务只是不支持事务而已，但是这个就是上下文中不能存在事务，一旦有事务，就抛出`RunTime`异常强制停止执行。
>
> `PROPAGTION_NESTED`：嵌套级别事务，如果上下文中存在事务则嵌套事务执行，如果不存在事务则新建事务。嵌套指的是子事务嵌套在父事务中执行，子事务是父事务的一部分，在进入子事务之前，父事务建立一个回滚点，叫做`save_point`，然后执行子事务，这个子事务的执行也算是父事务的一部分，子事务执行结束之后父事务继续执行。重点就在于那个回滚点。如果子事务回滚不会影响到父事务，如果父事务回滚则子事务必将也会回滚，总是子事务先提交然后才提交父事务。

### 4.1 `Spring`使用注解的事务

从最底层业务：`Mapper`然后写`SQL`开始做起`GoodsMapper.java GoodsMapper.xml`写起，然后到业务层`GoodsService[interface] GoodsServiceImpl.java`最后到逻辑层，因为这里还没有搞到`SpringMVC`所以先不搞逻辑层，最后就是测试类`App22.java`，搞完了以后还需要写`MyBatis`的配置文件：`mybatis20.xml`，还有就是`Spring`的配置文件：`applicationContext20.xml`，附加一个`jdbc.properties`，除此之外还定义了两个异常类用于`GoodsServiceImpl`

```java
package com.zwm.mapper;

import com.zwm.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    public abstract List<Goods> selectAllGoods();

    public abstract Goods selectGoodsById(int id);

    public abstract int updateGoodsPriceById(@Param("id") int id, @Param("price") int price);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zwm.mapper.GoodsMapper">
    <select id="selectAllGoods" resultType="goods">
        select *
        from goods;
    </select>
    <select id="selectGoodsById" resultType="goods">
        select *
        from goods
        where id = #{id}
    </select>
    <update id="updateGoodsPriceById">
        update goods set price = #{price} where id = #{id}
    </update>
</mapper>
```

```java
package com.zwm.service;

import com.zwm.exception.GoodsNotEnoughException;
import com.zwm.exception.GoodsNullPointerException;
import com.zwm.pojo.Goods;

import java.util.List;

public interface GoodsService {
    public abstract List<Goods> selectAllGoods();

    public abstract Goods selectGoodsById(int id);

    public abstract Goods buy(int id, int amount) throws GoodsNullPointerException, GoodsNotEnoughException;

    public abstract int updateGoodsPriceById(int id);
}
```

```java
package com.zwm.service.impl;

import com.zwm.exception.GoodsNotEnoughException;
import com.zwm.exception.GoodsNullPointerException;
import com.zwm.mapper.GoodsMapper;
import com.zwm.pojo.Goods;
import com.zwm.service.GoodsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public List<Goods> selectAllGoods() {
        return goodsMapper.selectAllGoods();
    }

    @Override
    public Goods selectGoodsById(int id) {
        return goodsMapper.selectGoodsById(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, timeout = -1, rollbackFor = {GoodsNotEnoughException.class, GoodsNullPointerException.class})
    public Goods buy(int id, int amount) throws GoodsNullPointerException, GoodsNotEnoughException {
        Goods goods = this.selectGoodsById(id);
        if (goods == null) {
            throw new GoodsNullPointerException("无此商品");
        }
        //更改价格
        this.updateGoodsPriceById(id);
        if (goods.getAmount() < amount) {
            throw new GoodsNotEnoughException("商品数量不足，无法更改价格");
        }
        return goods;
    }

    @Override
    public int updateGoodsPriceById(int id) {
        int price = goodsMapper.selectGoodsById(id).getPrice() + 100;
        return goodsMapper.updateGoodsPriceById(id, price);
    }
}
```

```java
package com.zwm;

import com.zwm.exception.GoodsNotEnoughException;
import com.zwm.exception.GoodsNullPointerException;
import com.zwm.pojo.Goods;
import com.zwm.service.GoodsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App22 {
    public static void main(String[] args) throws GoodsNotEnoughException, GoodsNullPointerException {
        String springConfig = "applicationContext20.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springConfig);
        GoodsService goodsService = (GoodsService) applicationContext.getBean("goodsService");
        List<Goods> goodsList = goodsService.selectAllGoods();
        for (Goods goods : goodsList) System.out.println(goods.toString());
        goodsService.buy(1001, 0);
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <typeAliases>
        <package name="com.zwm.pojo"/>
    </typeAliases>
    <mappers>
        <mapper resource="mapper/GoodsMapper.xml"/>
    </mappers>
</configuration>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
    <context:property-placeholder location="jdbc.properties"/>
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="${jdbc.maxActive}"/>
    </bean>
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="mybatis20.xml"/>
        <property name="dataSource" ref="druidDataSource"/>
    </bean>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
        <property name="basePackage" value="com.zwm.mapper"/>
    </bean>
    <bean id="goodsService" class="com.zwm.service.impl.GoodsServiceImpl">
        <property name="goodsMapper" ref="goodsMapper"/>
    </bean>
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="druidDataSource"/>
    </bean>
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

这些个代码的具体含义就是如果商品数量不够传递的，就无法更改价格，因为有事务管理所以会自动回滚，如果商品数量够的话就会把价格在原先的基础上增加`100`元，重点就是要体现事务管理。

### 4.2 `Spring`使用配置文件的事务

本质是使用`Spring`两大机制 - 控制反转`IOC`和面向切面编程`AOP`：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <typeAliases>
        <package name="com.zwm.pojo"/>
    </typeAliases>
    <mappers>
        <mapper resource="mapper/GoodsMapper.xml"/>
    </mappers>
</configuration>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    <context:property-placeholder location="jdbc.properties"/>
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="${jdbc.maxActive}"/>
    </bean>
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="druidDataSource"/>
        <property name="configLocation" value="mybatis21.xml"/>
    </bean>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
        <property name="basePackage" value="com.zwm.mapper"/>
    </bean>
    <bean id="goodsService" class="com.zwm.service.impl.GoodsServiceImpl">
        <property name="goodsMapper" ref="goodsMapper"/>
    </bean>
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="druidDataSource"/>
    </bean>
    <!--定义通知-->
    <tx:advice id="buyAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="buy" isolation="REPEATABLE_READ" propagation="REQUIRED" timeout="-1"
                       rollback-for="com.zwm.exception.GoodsNullPointerException, com.zwm.exception.GoodsNotEnoughException"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <!--定义切入点-->
        <aop:pointcut id="goodsServicePointcut"
                      expression="execution(* com.zwm.service.impl.GoodsServiceImpl.buy(..))"/>
        <!--使用通知织入至切入点-->
        <aop:advisor advice-ref="buyAdvice" pointcut-ref="goodsServicePointcut"/>
    </aop:config>
</beans>
```

## 5. `Spring`与`Web`

在`web`项目中，按逻辑讲，如果每次用到`Spring`的时候就创建一个`Spring`容器势必会造成很大的资源浪费，如何避免呢？所以在创建`web`项目的时候，每次启动项目创建`ServletContext`初始化的时候就创建`Spring`容器，就需要在`web.xml`中配置这些信息，使得`Spring`容器有且只有一个并且在整个`web`项目中都可以使用到

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>Web Application</display-name>
    <!--注册一个Servlet-->
    <servlet>
        <servlet-name>RegisterServlet</servlet-name>
        <servlet-class>com.zwm.controller.RegisterServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>RegisterServlet</servlet-name>
        <url-pattern>/registerServlet</url-pattern>
    </servlet-mapping>
    <!--监听Spring的applicationContext，Web项目启动时就创建容器-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
</web-app>
```

```java
package com.zwm.controller;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private WebApplicationContext webApplicationContext;

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(key);
        webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        String strName = httpServletRequest.getParameter("name");
        String strAge = httpServletRequest.getParameter("age");
        System.out.println("容器信息：" + webApplicationContext);
        httpServletRequest.getRequestDispatcher("/result.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
```

