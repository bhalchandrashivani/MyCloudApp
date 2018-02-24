package com.csye6225.spring2018.services;


import com.csye6225.spring2018.model.Account;
import com.csye6225.spring2018.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

//import com.csye6225.spring2018.repository.UserRepository;

@Service
public class UserService {
    //@Qualifier()
    @Autowired
    private AccountRepository accountRepository;
    private ResourceLoader resourceLoader;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);

    }

    public void saveUser(Account account) {
        //account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    public Account findUserByUsername(String email) {
        Account user1 = accountRepository.findByUsername(email);
        return user1;
    }

    public void  updateImagePath(Account account) {
        account.setImagepath(account.getImagepath());
        accountRepository.save(account);
    }
    private static String UPLOAD_PATH = "/home/danish/csye6225/dev/webapp2/src/main/resources/images";
    public Resource findImage(Account account){
        String impath = account.getImagepath();
        System.out.println("impath>> "+impath);
        return resourceLoader.getResource("file:" + UPLOAD_PATH + "/ "+impath);
    }
    public void updateImage(MultipartFile File) throws Exception{
        if (!File.isEmpty()) {
            Files.copy(File.getInputStream(), Paths.get(UPLOAD_PATH,File.getOriginalFilename()));
            accountRepository.save(new Account(File.getOriginalFilename()));
        }
    }
    public Page<Account> page(Pageable pageable){
        return accountRepository.findAll(pageable);
    }
}

//}


