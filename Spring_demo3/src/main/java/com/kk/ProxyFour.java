package com.kk;

public class ProxyFour {
    public static void main(String[] args) {
        BookServiceImplementTwo bookServiceImplementTwo = new BookServiceImplementTwo();
        BookServiceAop bookServiceAop = new LogAop();
        BookServiceAop bookServiceAop1 = new TransactionAop();
        BookServiceStaticProxyTwo bookServiceStaticProxyTwo = new BookServiceStaticProxyTwo(bookServiceImplementTwo, bookServiceAop1);
        BookServiceStaticProxyTwo bookServiceStaticProxyTwo1 = new BookServiceStaticProxyTwo(bookServiceStaticProxyTwo, bookServiceAop);
        bookServiceStaticProxyTwo1.buy();
    }
}

interface BookServiceInterfaceTwo {
    public abstract void buy();
}

//代理类需要实现同一个接口，使用多态，不仅可以传递 com.kk.BookService 的实现类，还可以传递 com.kk.BookServiceStaticProxy 静态代理从而实现多个切面的实现
class BookServiceStaticProxyTwo implements BookServiceInterfaceTwo {
    private BookServiceInterfaceTwo bookServiceInterfaceTwo;
    private BookServiceAop bookServiceAop;

    public BookServiceStaticProxyTwo(BookServiceInterfaceTwo bookServiceInterfaceTwo, BookServiceAop bookServiceAop) {
        this.bookServiceInterfaceTwo = bookServiceInterfaceTwo;
        this.bookServiceAop = bookServiceAop;
    }

    @Override
    public void buy() {
        try {
            bookServiceAop.before();
            bookServiceInterfaceTwo.buy();
            bookServiceAop.after();
        } catch (Exception e) {
            bookServiceAop.exception();
        }
    }
}

interface BookServiceAop {
    public abstract void before();

    public abstract void after();

    public abstract void exception();
}

class BookServiceImplementTwo implements BookServiceInterfaceTwo {
    @Override
    public void buy() {
        System.out.println("图书功能业务实现...");
    }
}

class TransactionAop implements BookServiceAop {
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

class LogAop implements BookServiceAop {

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