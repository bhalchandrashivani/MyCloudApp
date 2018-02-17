package com.csye6225.spring2018;

import com.csye6225.spring2018.model.Account;
import com.csye6225.spring2018.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
 public class AccountValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Account u = (Account) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","NotEmpty","Email address can't be empty!");

        if(userService.findUserByUsername(u.getUsername())!=null){
            errors.rejectValue("username","Duplicate.user.email","User Already Exist!!!");
        }
    }
}
