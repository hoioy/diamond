package com.hoioy.diamond.security;

import com.hoioy.diamond.security.jwt.converter.CustomJwtAccessTokenConverter;
import com.hoioy.diamond.security.jwt.converter.TDFOauthServerJwtAccessTokenConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class ApplicationTests {


    public void contextLoads() {
    }

    public static void main(String[] args) {
        try {
            testTDFOauthServerJwtAccessTokenConverter();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        getEncodedAdmin();
    }

    public static void testCustomJwtAccessTokenConverter() throws JsonProcessingException {
        User user = new User("a", "a", Collections.emptyList());
        new CustomJwtAccessTokenConverter().generateToken(user);
    }

    public static void testTDFOauthServerJwtAccessTokenConverter() throws IllegalAccessException, IOException, InvocationTargetException {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDcwMjc0MjYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiNWI2NmVjZjQ1ZDYzNDE1OWEwODQ2ODg5OGIxYjMyMTciXSwianRpIjoiNzRhNjEwYmUtNGZmNi00NDE4LWJjMzEtMWUyY2E3ZTY1YjVhIiwiY2xpZW50X2lkIjoidnVlIiwic2NvcGUiOlsidXNlcl9pbmZvIl19.w2YxtgiNZN-MfWOqiamg15TyxlqxvpsT5HyXAszmDN0";
        new TDFOauthServerJwtAccessTokenConverter().convert(token);
    }

    public static void getEncodedAdmin() {
        String pass = "admin";
        String pstr = new BCryptPasswordEncoder().encode(pass);
        System.out.println(pstr);
    }
}
