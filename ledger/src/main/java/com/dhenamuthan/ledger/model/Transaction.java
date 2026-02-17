package com.dhenamuthan.ledger.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    private String asset;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDateTime timestamp;

    protected Transaction() {} // required by JPA.. JPA  creates object by reflection. It needs a no args constructor to initiate the class 
    //when reading from DB. here , protected means JPA can use it but regular code wont be able to access it.

    public Transaction(Long userId, 
                      TransactionType type,
                      String asset,
                      BigDecimal quantity,
                      BigDecimal price) {
            this.userId=userId;
            this.type = type;
            this.asset = asset;
            this.quantity = quantity;
            this.price = price;
            this.timestamp = LocalDateTime.now();
            }

    //Getters ( NOTE THERE SHOULD BE NO SETTERS SINCE THIS SHOULD BE IMMUTABLE, ledgers shows historical events that are transactional , fintecth = trust is important and therefore they shouldn't be mutable)
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public TransactionType getType() { return type; }
    public String getAsset() { return asset; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

//NOTES
//import jakarta.persistance is importing JPA annotations where JPA- the tool Spring uses to map Java objects to database tables
//BigDecimal data type is used instead of Double as money must be precise and double can do rounding at times 
// @Entity- means Java class is stored in database
//@Table(name="transactions") means: store it in a table named transactions
//    - without this , JPA would default it to transaction or something based on classname
//this becomes Java class Transaction to DB table transactions

//@Id means the field is the primary key
//@GeneratedValue(IDENTITY) means the databse automatically generates it 
// Used Long datatype here cause DB IDS can get big

//@Enumerated(EnumType.STRING) means store it as "BUY" not 0/1/2.