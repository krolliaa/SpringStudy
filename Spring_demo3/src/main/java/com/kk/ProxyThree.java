package com.kk;

public class ProxyThree {
    public static void main(String[] args) {
        BookServiceImplement bookServiceImplement = new BookServiceImplement();
        BookServiceStaticProxy bookServiceStaticProxy = new BookServiceStaticProxy(bookServiceImplement);
        bookServiceStaticProxy.buy();
    }
}

interface BookServiceInterface {
    public abstract void buy();
}

//代理类需要实现同一个接口
class BookServiceStaticProxy implements BookServiceInterface {
    private BookServiceImplement bookServiceImplement;

    public BookServiceStaticProxy(BookServiceImplement bookServiceImplement) {
        this.bookServiceImplement = bookServiceImplement;
    }

    @Override
    public void buy() {
        try {
            System.out.println("静态代理实现事务开启...");
            bookServiceImplement.buy();
            System.out.println("静态代理实现事务关闭...");
        } catch (Exception e) {
            System.out.println("静态代理实现事务回滚...");
        }
    }
}

class BookServiceImplement implements BookServiceInterface {
    @Override
    public void buy() {
        System.out.println("图书功能业务实现...");
    }
}