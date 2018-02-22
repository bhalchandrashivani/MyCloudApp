package com.csye6225.spring2018.controllers;

import javax.servlet.http.HttpServletRequest;
import com.csye6225.spring2018.model.Account;
import com.csye6225.spring2018.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LoginController {
    private String email;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   @GetMapping(value="/login")
    public String login(){

        return "login";
    }


    @PostMapping(value = "/login")
    public String isValidUser(Model model, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        email = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(email+ " "+ password);
        if(email.isEmpty() || password.isEmpty()){
            session.setAttribute("message", "Enter valid credentials!");
        }else { //user has entered username and password
            boolean validEmail = validateEmail(email);
            /*check if email present in DB. If email is present, retrieve the corresponding password and */
            if(validEmail){
                //search email in db
                Account user = userService.findByUsername(email);
                //System.out.println("****outside findByEmail method****");
                if(user == null){
                    session.setAttribute("message","enter valid credentials!");
                }else{
                    //user exists
                    //retrieve existing pwd from db
                    String pwdFromDb = user.getPassword();
                    String userImagepathFromDb = user.getImagepath();
                    String aboutmefromDb = user.getAboutme();
                    //decrypt password using bcrypt
                    boolean pwd = bCryptPasswordEncoder.matches(password, pwdFromDb);
                    System.out.println(pwd);
                    if(pwd){
                        Date date = new Date();
                        System.out.println("User logged in");
                        session.setAttribute("message","Hello! The current time is: " + date.toString()
                                +" To logout click on the logout option.");
                        session.setAttribute("loggedInUser",user);
                        model.addAttribute("username", email);
                        model.addAttribute("imagePath", userImagepathFromDb);
                        model.addAttribute("aboutme",aboutmefromDb);
                        return "welcome";
                    }
                    else{
                        session.setAttribute("message","Sorry! Wrong username/password. Try Again!");
                    }
                }
            }else{
                session.setAttribute("message","Username has to be valid email!");
            }
        }
        return "403";
    }
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/home/shivani/cloud/csye6225/dev/csye6225-spring2018-1/webapp/src/main/resources/images/";
   // private static String UPLOADED_FOLDER = "/home/shivani/Shivani/csye6225/dev/csye6225-spring2018-1/webapp/src/main/resources/images/";

    @PostMapping("/upload")
    public String myProfiledetails(Model model, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
                                   @ModelAttribute Account account, HttpServletRequest request) {

        System.out.println(email);
        if (file.isEmpty()) {
            return "welcome";
        } else {
            Account user = userService.findByUsername(email);
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);

                if(user!= null){
                    user.setImagepath(path.toString());
                    userService.saveUser(user);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "welcome";

        }
    }



    @RequestMapping(value="/logout")
    public String logout(HttpSession session) throws Exception{
        session.invalidate();

        return "home";
    }

    @PostMapping("/updateaboutme")
    public String allProfile(Model model,HttpServletRequest request)  throws Exception {

        HttpSession session = request.getSession();
        //String email = request.getParameter("username");
        String useraboutme = request.getParameter("aboutme");
        System.out.println(email);
        Account user = userService.findByUsername(email);
        if(user == null){
            session.setAttribute("message","enter valid credentials!");
        }else{
                user.setAboutme(useraboutme);
                userService.saveUser(user);
            // String userImagepathFromDb = user.getImagepath();
            String aboutmefromDb = user.getAboutme();
            model.addAttribute("username", email);
            model.addAttribute("aboutme",useraboutme);
            return "welcome";
        }
        session.setAttribute("username",email);


        return "welcome";
    }


    @RequestMapping("/profilepic")
    public ResponseEntity<byte[]> profilePic(HttpServletRequest request)  throws Exception {

        HttpSession session = request.getSession();

        System.out.println(email);
        Account user = userService.findByUsername(email);
            String userImagepathFromDb = user.getImagepath();
            InputStream inputStream = new FileInputStream(userImagepathFromDb);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] picturebuffer = new byte[512];
            int l = inputStream.read(picturebuffer);
            while (l >= 0) {
                outputStream.write(picturebuffer, 0, l);
                l = inputStream.read(picturebuffer);
            }
                HttpHeaders headers = new HttpHeaders();
                headers.set("content-type","image/jpg");
                return new ResponseEntity<byte[]>(outputStream.toByteArray(),headers, HttpStatus.OK);
    }

// Only email id is allowed as username
    public boolean validateEmail(String email){
        System.out.println("****inside validEmail method****");
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher match = email_pattern.matcher(email);
        return match.matches();
    }
}
