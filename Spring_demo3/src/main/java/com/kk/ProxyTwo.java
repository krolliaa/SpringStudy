package com.kk;

public class ProxyTwo {
    public static void main(String[] args) {
        BookService bookService = new SubBookService();
        bookService.buy();
    }
}

class BookService {
    public void buy() {
        System.out.println("图书功能业务代码实现...");
    }
}

class SubBookService extends BookService {
    @Override
    public void buy() {
        try {
            System.out.println("事务开启...");
            super.buy();
            System.out.println("事务关闭");
        } catch (Exception e) {
            System.out.println("事务回滚...");
        }
    }
}