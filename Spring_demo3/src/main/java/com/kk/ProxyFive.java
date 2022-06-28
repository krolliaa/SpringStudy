package com.kk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFive {
    public static void main(String[] args) {
        BookServiceInterfaceFive bookServiceImplFive = new BookServiceImplementFive();
        BookServiceDynamicProxyFive bookServiceDynamicProxyFive = new BookServiceDynamicProxyFive();
        BookServiceAopFive bookServiceAop = new LogAopFive();
        BookServiceInterfaceFive bookServiceInterfaceFive = (BookServiceInterfaceFive) bookServiceDynamicProxyFive.getNewInstance(bookServiceImplFive, bookServiceAop);
        bookServiceInterfaceFive.buy();
    }
}


interface BookServiceInterfaceFive {
    public abstract void buy();
}

class BookServiceImplementFive implements BookServiceInterfaceFive {
    @Override
    public void buy() {
        System.out.println("图书功能业务实现...");
    }
}

class BookServiceDynamicProxyFive {
    public Object getNewInstance(Object object, BookServiceAopFive bookServiceAopFive) {
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(object, bookServiceAopFive);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), myInvocationHandler);
    }
}

class MyInvocationHandler implements InvocationHandler {

    private Object target;
    private BookServiceAopFive bookServiceAop;

    public MyInvocationHandler(Object object, BookServiceAopFive bookServiceAop) {
        this.target = object;
        this.bookServiceAop = bookServiceAop;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnObject = null;
        try {
            bookServiceAop.before();
            returnObject = method.invoke(target, args);
            bookServiceAop.after();
        } catch (Exception e) {
            bookServiceAop.exception();
        }
        return returnObject;
    }
}

interface BookServiceAopFive {
    public abstract void before();

    public abstract void after();

    public abstract void exception();
}

class TransactionAopFive implements BookServiceAopFive {
    @Override
    public void before() {
        System.out.println("事务开启...");
    }

    @Override
    public void after() {
        System.out.println("事务关闭...");
    }

    @Override
    public void exception() {
        System.out.println("事务回滚...");
    }
}

class LogAopFive implements BookServiceAopFive {

    @Override
    public void before() {
        System.out.println("前置日志输出...");
    }

    @Override
    public void after() {
        System.out.println("后置日志输出...");
    }

    @Override
    public void exception() {
        System.out.println("错误日志输出...");
    }
}

