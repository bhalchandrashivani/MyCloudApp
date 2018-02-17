//package com.csye6225.spring2018.repository;
//
//
//import com.csye6225.spring2018.model.Account;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//// CRUD refers Create, Read, Update, Delete
//@Repository("userRepository")
//public interface UserRepository extends CrudRepository<Account, Integer> {
//    Account findByUsernameAndPassword(String username , String password);
//    Account findAllByUsernameNotNull();
//    Account findByUsername(String username);
//
//
//}