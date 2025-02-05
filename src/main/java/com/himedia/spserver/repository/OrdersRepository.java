package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findByOseq(int oseq);


}
