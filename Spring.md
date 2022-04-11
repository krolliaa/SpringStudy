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
```

