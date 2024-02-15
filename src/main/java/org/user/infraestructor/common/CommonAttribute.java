package org.user.infraestructor.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class CommonAttribute {

    @Value("${patter.password}")
    private  String patterPassword;

    public  boolean validateEmail(String email) {
        if (email == null)
            return false;

        String regex = "^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public  boolean validatePassword(String password) {
        if (password == null)
            return false;

        Pattern pattern = Pattern.compile(patterPassword);
        return pattern.matcher(password).matches();
    }
}
