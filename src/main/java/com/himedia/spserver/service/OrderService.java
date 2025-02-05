package com.himedia.spserver.service;

import com.himedia.spserver.dto.OrderDetailDto;
import com.himedia.spserver.entity.*;
import com.himedia.spserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    MemberRepository mR;
    @Autowired
    ProductRepository pR;
    @Autowired
    CartRepository cR;
    @Autowired
    OrdersRepository oR;
    @Autowired
    OrderDetailReposiory odR;


    public int insertOrders(String userid) {
        Orders orders = new Orders();
        Member member = mR.findByUserid(userid);
        orders.setMember(member);
        Orders o = oR.save(orders);
        return o.getOseq();
    }

    public int insertOrderDetail(int oseq, int cseq) {
        Cart  cart = cR.findByCseq(cseq);
        Orders orders = oR.findByOseq(oseq);
        Product product = pR.findByPseq( cart.getProduct().getPseq() );

        Order_Detail od = new Order_Detail();
        od.setOrders(orders);
        od.setProduct(product);
        od.setQuantity(cart.getQuantity());
        od.setResult(1);

        Order_Detail orderDetail =  odR.save(od);
        cR.delete(cart);

        return orderDetail.getOdseq();
    }

    public HashMap<String, Object> getOrderList(int oseq) {
        HashMap<String, Object> result = new HashMap<>();
        Orders orders = new Orders();
        orders.setOseq(oseq);

        List<Order_Detail> list = odR.findByOrders( orders );
        result.put("orderList", list);
        result.put("orderDetail", list.get(0));
        System.out.println(list.get(0));
        int totalPrict=0;
        for( Order_Detail orderDetail : list )
            totalPrict += ( orderDetail.getProduct().getPrice2() * orderDetail.getQuantity() );
        result.put("totalPrice", totalPrict);

        return result;
    }

    public void insertOrderDetailOne(int oseq, int pseq, int quantity) {
        Order_Detail orderDetail = new Order_Detail();

        Orders orders = oR.findByOseq(oseq);
        orderDetail.setOrders(orders);

        Product product = pR.findByPseq(pseq);
        orderDetail.setProduct(product);

        orderDetail.setQuantity(quantity);
        orderDetail.setResult(1);

        odR.save(orderDetail);
    }

    public List<OrderDetailDto> getOrderIng(String userid) {
        List<OrderDetailDto> finalList = new ArrayList<>();

        // userid가 주문한 주문의 oseq 들을 조회
        List<Integer> oseqList = odR.getOseqListIng( userid );

//        List<Order_Detail> odlist = odR.findOrdersUserid( userid );
//        List<Integer> oseqList2 = new ArrayList<>();
//        for( Order_Detail orderDetail : odlist ){
//            oseqList2.add(orderDetail.getOrders().getOseq());
//        }

        for( int oseq : oseqList ) {
            // 주문번호별로 주문 세부사항을 검색
            Orders orders = oR.findByOseq(oseq);
            List<Order_Detail> list = odR.findByOrders(orders);

            OrderDetailDto dto = new OrderDetailDto();
            dto.setOdseq( list.get(0).getOdseq());
            dto.setOseq( list.get(0).getOrders().getOseq() );
            dto.setQuantity( list.get(0).getQuantity());
            dto.setUserid( list.get(0).getOrders().getMember().getUserid() );
            dto.setIndate( list.get(0).getOrders().getIndate() );
            dto.setPseq( list.get(0).getProduct().getPseq() );
            dto.setMname( list.get(0).getOrders().getMember().getName() );
            dto.setZip_num( list.get(0).getOrders().getMember().getZip_num());
            dto.setAddress1( list.get(0).getOrders().getMember().getAddress1());
            dto.setAddress2( list.get(0).getOrders().getMember().getAddress2());
            dto.setAddress3( list.get(0).getOrders().getMember().getAddress3());
            dto.setPhone( list.get(0).getOrders().getMember().getPhone());
            dto.setPname( list.get(0).getProduct().getName() + " 포함 " + list.size() + " 건" );
            int totalPrice = 0;
            for( Order_Detail orderDetail : list )
                totalPrice += ( orderDetail.getProduct().getPrice2() * orderDetail.getQuantity() );
            dto.setPrice2( totalPrice );
            dto.setResult( list.get(0).getResult() );
            finalList.add(dto);
        }

        return finalList;
    }

    public List<OrderDetailDto> getOrderAll(String userid) {
        List<OrderDetailDto> finalList = new ArrayList<>();
        // userid가 주문한 주문의 oseq 들을 조회
        List<Integer> oseqList = odR.getOseqListAll( userid );
        for( int oseq : oseqList ) {
            // 주문번호별로 주문 세부사항을 검색
            Orders orders = oR.findByOseq(oseq);
            List<Order_Detail> list = odR.findByOrders(orders);

            OrderDetailDto dto = new OrderDetailDto();
            dto.setOdseq( list.get(0).getOdseq());
            dto.setOseq( list.get(0).getOrders().getOseq() );
            dto.setQuantity( list.get(0).getQuantity());
            dto.setUserid( list.get(0).getOrders().getMember().getUserid() );
            dto.setIndate( list.get(0).getOrders().getIndate() );
            dto.setPseq( list.get(0).getProduct().getPseq() );
            dto.setMname( list.get(0).getOrders().getMember().getName() );
            dto.setZip_num( list.get(0).getOrders().getMember().getZip_num());
            dto.setAddress1( list.get(0).getOrders().getMember().getAddress1());
            dto.setAddress2( list.get(0).getOrders().getMember().getAddress2());
            dto.setAddress3( list.get(0).getOrders().getMember().getAddress3());
            dto.setPhone( list.get(0).getOrders().getMember().getPhone());
            dto.setPname( list.get(0).getProduct().getName() + " 포함 " + list.size() + " 건" );
            int totalPrice = 0;
            for( Order_Detail orderDetail : list )
                totalPrice += ( orderDetail.getProduct().getPrice2() * orderDetail.getQuantity() );
            dto.setPrice2( totalPrice );
            dto.setResult( list.get(0).getResult() );
            finalList.add(dto);
        }

        return finalList;
    }
}















