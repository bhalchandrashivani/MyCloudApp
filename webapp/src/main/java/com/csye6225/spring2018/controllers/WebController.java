package com.csye6225.spring2018.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

//import com.csye6225.spring2018.repository.UserRepository;

@Controller
public class WebController {

    private final static Logger logger = LoggerFactory.getLogger(WebController.class);

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        logger.info("Loading home page.");
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