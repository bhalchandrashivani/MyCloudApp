package com.csye6225.spring2018.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.csye6225.spring2018.AccountValidator;
import com.csye6225.spring2018.model.Account;
import com.csye6225.spring2018.services.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import jbr.springmvc.service.UserService;
@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountValidator userValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value="/signup",method= RequestMethod.GET)
    public String registerUser(Model model){
        System.out.println("Say Register");
        model.addAttribute("account",new Account());
        return "signup";

    }

    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public String doRegistration(@ModelAttribute @Valid Account account, BindingResult bindingResult){
        userValidator.validate(account,bindingResult);

        for (FieldError err:bindingResult.getFieldErrors()){
            System.out.println(account.getUsername());
            System.out.println(err.getDefaultMessage());
        }
        if(bindingResult.hasErrors()) {
            System.out.print("from validator");
            return "signupfail";

        }

        boolean validEmail = validateEmail(account.getUsername());
       // JsonObject jsonObject = new JsonObject();

        if(!validEmail) {
            System.out.print("from email");
            return "webresult";
        }
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        userService.saveUser(account);
        System.out.println(account.getUsername());

        return "webresult";
    }



    public boolean validateEmail(String email){

        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher match = email_pattern.matcher(email);
        return match.matches();
    }
}
