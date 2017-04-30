package com.jaf.examples.spring.ch8;

import com.jaf.examples.spring.ch8.IUserService;

/**
 * Created by walle on 2017/4/29.
 */
public class UserServiceImpl implements IUserService {

    @Override
    public void findUser() {
        System.out.println("find user");
    }
}
