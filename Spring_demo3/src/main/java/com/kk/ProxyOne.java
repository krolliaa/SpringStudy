package com.kk;

public class ProxyOne {
    public static void main(String[] args) {
        buy();
    }

    public static void buy() {
        try {
            System.out.println("事务开启...");
            System.out.println("图书功能业务实现");
            System.out.println("事务关闭...");
        } catch (Exception e) {
            System.out.println("事务回滚...");
        }
    }
}
