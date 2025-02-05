package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Order_view;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderViewRepository extends JpaRepository<Order_view, Integer> {

}
