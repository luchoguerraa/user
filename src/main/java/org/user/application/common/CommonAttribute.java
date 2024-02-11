package org.user.application.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class CommonAttribute {

    @Value("${patter.password}")
    private  String patterPassword;

    public  boolean validateEmail(String email) {
        String regex = "^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public  boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(patterPassword);
        return pattern.matcher(password).matches();
    }
}
