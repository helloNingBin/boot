package com.example.boot.AOP;

/**
 * @author:chichao
 * @date:年月日
 */
public class DefaultProxyService implements PrintService {
    @Override
    public String print(String msg) throws NullPointerException {
        String v = "[DefaultProxyService] -->" + msg;
        System.out.println(v);
        return v;
    }
}
