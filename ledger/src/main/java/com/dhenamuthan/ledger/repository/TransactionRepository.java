package com.dhenamuthan.ledger.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhenamuthan.ledger.model.Transaction;


//below we are telling Spring to create a repository  for the Transaction entity with the primary key as Long as in our entity our id was Long
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByUserIdAndAsset(Long userId, String asset); //THIS SIS SO COOL BECAUSE ALTHOUGH 
    //I DIDNT IMPLEMENT THE METHOD SPRING parses the name as 
    //find -- SELECT an ByUserId as WEHRE user_id=? and this is called query method derivation. the user Id is in camel case cause Hibernate transaltes cmel to case jav fields to snake case fields in DB
}
//NOTES: 
//JPA repository is like the bridge between JPA and DB its equivalent to selcting all table entries from the transactions table

//importance of repository in Architecture:
// Controller → handles HTTP
// Service → business logic
// Repository → database interaction
// Model → data structure

// The repository:
//      should not contain business rules
//      should only talk to DB