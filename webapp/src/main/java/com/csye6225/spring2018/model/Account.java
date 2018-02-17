package com.csye6225.spring2018.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity // This tells Hibernate to make a table out of this class

@Table(name = "user")
public class Account implements Serializable {

//
//    @Id
//    @Column(name="userid")
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Integer id;
    @Id
    @Column (name = "username")
    @Pattern(regexp = "^[_  A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email address is invalid")
    private String username;


    @Column (name = "password")
    @Size(min=1, message="Password can not be empty")
    private String password;

    @Column (name= "imagepath")
    private String imagepath;

    @Column (name= "aboutme")
    private String aboutme;

    public Account() {
    }
    public Account(Account account) {
        this.password=account.getPassword();
//        this.id=account.getId();
        this.username=account.getUsername();
        this.imagepath = account.getImagepath();
        this.aboutme = account.getAboutme();


    }
    public Account(String impagepath){
        this.imagepath = impagepath;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String passwordEncrption(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    @Override
    public String toString(){
        return this.username;
    }
}


