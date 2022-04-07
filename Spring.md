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



