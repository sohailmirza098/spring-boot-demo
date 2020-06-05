package com.geralt.securityapp.service;

import com.geralt.securityapp.model.Transaction;
import com.geralt.securityapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

//    @Override
//    public List<Transaction> getTransactionsByUserId(int userid) {
//        return transactionRepository.getTransactionsByUserId(userid);
//    }


}
