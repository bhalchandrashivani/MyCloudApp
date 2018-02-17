package com.csye6225.spring2018.controllers;

import com.csye6225.spring2018.model.Account;
import com.csye6225.spring2018.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @PostMapping("/myprofile")
    public String myProfile(Model model,HttpServletRequest request)  throws Exception {

        HttpSession session = request.getSession();
        String email = request.getParameter("username");
        System.out.println(email);
        session.setAttribute("username",email);

        return "myprofile";
    }

    @RequestMapping("/updateaboutmebutton")
    public String viewMyAboutme(Model model){
        return "updateaboutme";
    }

    @PostMapping("/aboutme")
    public String allProfile(Model model,HttpServletRequest request)  throws Exception {

        HttpSession session = request.getSession();
        String email = request.getParameter("username");
        System.out.println(email);
        Account user = userService.findByUsername(email);
        if(user == null){
            session.setAttribute("message","enter valid credentials!");
        }else{

                String aboutmefromDb = user.getAboutme();
                model.addAttribute("username", email);
                model.addAttribute("aboutme",aboutmefromDb);
                return "aboutmesuccess";
        }
        session.setAttribute("username",email);
        return "aboutmefail";
    }

    @GetMapping("/aboutme")
    public String viewallProfiles(Model model){
        return "allprofiles";
    }

}