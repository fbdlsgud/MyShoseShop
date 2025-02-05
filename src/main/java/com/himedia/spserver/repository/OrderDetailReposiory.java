package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Member;
import com.himedia.spserver.entity.Order_Detail;
import com.himedia.spserver.entity.Orders;
import com.himedia.spserver.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface OrderDetailReposiory extends JpaRepository<Order_Detail, Integer> {

    List<Order_Detail> findByOrders(Orders orders);

    @Query(value="select distinct a.orders_oseq from order_detail a, orders b where a.orders_oseq=b.oseq and b.member_userid=:userid and a.result < 4" , nativeQuery=true)
    List<Integer> getOseqListIng(@Param("userid") String userid);

    @Query(value="select distinct a.orders_oseq from order_detail a, orders b where a.orders_oseq=b.oseq and b.member_userid=:userid" , nativeQuery=true)
    List<Integer> getOseqListAll(@Param("userid") String userid);

    int countAllBy();

    @Query("select  count(od.odseq) from Order_Detail od where od.orders.member.name like concat('%' , :key , '%')")
    int getCount(@Param("key") String key);

    @Query("select od  from Order_Detail od where od.orders.member.name like concat('%' , :key , '%')")
    Page<Order_Detail> getOrderList(@Param("key")  String key, Pageable pageable);

    Order_Detail findByOdseq(int odseq);
}
