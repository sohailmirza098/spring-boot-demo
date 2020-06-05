package com.geralt.securityapp.repository;

import com.geralt.securityapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
//    @Query(
////            value = "SELECT product.product_id, product.product_name, product.price, transaction.timestamp " +
////                    "FROM product INNER JOIN transaction ON product.product_id = transaction.product_product_id " +
////                    "WHERE transaction.user_id = :userid " +
////                    "ORDER BY transactionid DESC"
//            value = "SELECT trans " +
//                    "FROM Transaction trans" +
//                    "WHERE trans.user.userid=:userid "
//    )
//    List<Transaction> getTransactionsByUserId(@Param("userid") int userid);
}
