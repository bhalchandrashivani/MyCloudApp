package com.csye6225.spring2018.controllers;

import com.csye6225.spring2018.model.Account;
//import com.csye6225.spring2018.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WebController {

    private final static Logger logger = LoggerFactory.getLogger(WebController.class);

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        //logger.info("Loading home page.");
        logger.warn("Loading home page ");
        //HttpSession session = request.getSession();

       // if (session.getAttribute("loggedInUser") == null) {
            //show login and signup options
            return "index";
       // } else {
       //     return "403";
//
       // }
    }

}