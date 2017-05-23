package com.jaf.examples.dubbo.server;

import com.jaf.examples.dubbo.client.MyService;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/05/23 17:56
 */
public class MyServiceSupport implements MyService {

    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }

}
