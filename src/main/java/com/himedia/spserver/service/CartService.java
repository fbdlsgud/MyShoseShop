package com.himedia.spserver.service;

import com.himedia.spserver.entity.Cart;
import com.himedia.spserver.entity.Member;
import com.himedia.spserver.entity.Product;
import com.himedia.spserver.repository.CartRepository;
import com.himedia.spserver.repository.MemberRepository;
import com.himedia.spserver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    ProductRepository pR;

    @Autowired
    MemberRepository mR;

    @Autowired
    CartRepository cR;


    public void insertCart(int pseq, String userid, int quantity) {
        Member member = mR.findByUserid(userid);
        Product product = pR.findByPseq(pseq);
        Cart cart = new Cart();
        cart.setQuantity(quantity);
        cart.setMember(member);
        cart.setProduct(product);
        cR.save(cart);
    }

    public HashMap<String, Object> getCartList(String userid) {
        HashMap<String, Object> result = new HashMap<>();

        // Member member = mR.findByUserid(userid);  // 1
        // List<Cart> list = cR.findByMember(member);

        //Member member = new Member();  // 2
        //member.setUserid(userid);
        //List<Cart> list = cR.findByMember(member);

        List<Cart> list = cR.findByMemberUserid(userid);   // 3

        result.put("cartList", list);
        int totalPrice = 0;
        for (Cart cart : list) {
            totalPrice += (cart.getProduct().getPrice2() * cart.getQuantity());
        }
        result.put("totalPrice", totalPrice);
        return result;
    }


    public void deleteCart(int cseq) {
        Cart cart = cR.findByCseq( cseq );
        cR.delete( cart );
    }
}
