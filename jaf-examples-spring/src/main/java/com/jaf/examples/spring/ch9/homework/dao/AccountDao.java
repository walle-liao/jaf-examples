package com.jaf.examples.spring.ch9.homework.dao;

/**
 * Created by walle on 2017/5/7.
 */
public interface AccountDao {

    void addAmount(String username, int amount);

    void subtractAmount(String username, int amount);

}
