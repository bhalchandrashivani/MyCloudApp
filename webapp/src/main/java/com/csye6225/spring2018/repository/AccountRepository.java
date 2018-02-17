package com.csye6225.spring2018.repository;

import com.csye6225.spring2018.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRepository")
public interface AccountRepository  extends CrudRepository<Account,Integer>{
    Account findByUsername(String username);

    Page<Account> findAll(Pageable pageable);
    //  Account findUserByUsername (String username);

}
