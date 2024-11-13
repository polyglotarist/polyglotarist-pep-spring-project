package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select acc from Account acc where acc.username = :username")
    public Account findByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT acc FROM Account acc WHERE acc.username = :username AND acc.password = :password")
    public Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("select acc from Account acc where acc.accountId = :accountId")
    public Account findById(@Param("accountId") int accountId);
}
