package com.bluecode.chatbot.config.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie=new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response,String name){
        Cookie[] cookies= request.getCookies();
        if(cookies==null){
            return;
        }

        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    public static String serialize(Object object){
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deseriazlie(Cookie cookie,Class<T> tClass){
        byte[] data=Base64.getUrlDecoder().decode(cookie.getValue());
        try(ObjectInputStream objectInputStream= new ObjectInputStream(new ByteArrayInputStream(data))){
            Object object=objectInputStream.readObject();
            return tClass.cast(object);
        } catch (IOException| ClassNotFoundException e){
            throw new RuntimeException("deserialize cookie fail");
        }
    }
}
