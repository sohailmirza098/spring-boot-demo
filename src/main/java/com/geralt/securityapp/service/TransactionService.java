package com.geralt.securityapp.service;

import com.geralt.securityapp.model.Product;
import com.geralt.securityapp.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TransactionService {

    void save(Transaction transaction);
//    List<Transaction> getTransactionsByUserId(int userid);

}
