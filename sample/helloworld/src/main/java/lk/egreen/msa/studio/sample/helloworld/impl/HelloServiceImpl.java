package lk.egreen.msa.studio.sample.helloworld.impl;

import lk.egreen.msa.studio.sample.helloworld.HelloService;

import javax.enterprise.context.RequestScoped;

/**
 * Created by dewmal on 7/17/16.
 */
@RequestScoped
public class HelloServiceImpl implements HelloService {
    public void sayHello(String name) {
        System.out.println(name + "Hello world");
    }
}
