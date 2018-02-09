package com.csye6225.spring2018;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.csye6225.spring2018.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WebControllerTest {

    private Account myAccount;

    @Before
    public void setUp() {
        myAccount = new Account();
        myAccount.setPassword("123456");
    }


    @Test
    public void TestEncryption() {

        String hashedPassword = myAccount.passwordEncrption(myAccount.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String secondhashedPassword = passwordEncoder.encode(myAccount.getPassword());

        Assert.assertNotEquals(hashedPassword, secondhashedPassword);
    }
}

//public class WebControllerTest {
//
//    public static void main (String args[]){
//        Connection conn = getConnection();
//
//
//    }
//
//
//    public static Connection getConnection() {
//
//        //File prop = new File("resources\\connectionDetails.properties");
//       // String[] connectionDetails = new Helper().readPropertiesFile(prop);
//
//        String driverManager = "com.mysql.jdbc.Driver";
//        String uname = "cloudteam4";
//        String pwd = "cloud";
//
//        Connection connection = null;
//        try {
//            //Class.forName("oracle.jdbc.driver.OracleDriver");
//
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "cloudteam4","cloud");
//
//
//
//        } catch (Exception e) {
//            System.err.println("Connection Failed.\n");
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        return connection;
//    }
//
//    //System.out.println("done");
//
///*@Autowired
//    MockMvc mockMvc;
//
//    @InjectMocks
//    WebController webController;
//
//    @Before
//    public void load() throws Exception{
//        mockMvc = MockMvcBuilders.standaloneSetup(webController).build();
//    }
//
//    @Test
//    public void TestGreetingSubmit() throws Exception{
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/login")
//                .accept(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//*/


//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Test
//    //@PostMapping("/signup")
//    public void TestGreetingSubmit() {
//
//        String username = "ahmet@gmail.com";
//        String password = "123456";
//
//        String sql = "SELECT username FROM users WHERE username = ?";
//        System.out.println(sql);
//        List<String> certs = jdbcTemplate.queryForList(sql, new Object[] { username},String.class);
//
//        //String sql = "SELECT username FROM users WHERE username = '" +username+"';";
//        //List<String> certs = jdbcTemplate.queryForList(sql, new Object[] {username },String.class);
//
//        if(certs.isEmpty()) {
//            String newpassword = password;
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String hashedPassword = passwordEncoder.encode(newpassword);
//
//            System.out.println("Succesfull Sign Up! Username:"+username+" and Crypto Password: "+hashedPassword+" are inserted into Database");
//        }
//
//        else {
//            System.out.println("Sign Up Failed! Username:"+username+" is already exist.");
//
//        }
//    }




//}