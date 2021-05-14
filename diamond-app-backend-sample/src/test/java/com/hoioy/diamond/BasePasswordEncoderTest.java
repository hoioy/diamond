package com.hoioy.diamond;

import com.hoioy.diamond.security.BasePasswordEncoder;

public class BasePasswordEncoderTest {
    public static void main(String[] args) {
        System.out.println(new BasePasswordEncoder().encode("diamond"));
        System.out.println(new BasePasswordEncoder().encode("diamondcloud"));
    }
}
