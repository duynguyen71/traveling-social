package com.tv.socket;

import java.security.Principal;

public class MyUserPrinciple implements Principal {

    String name;

    public MyUserPrinciple(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
