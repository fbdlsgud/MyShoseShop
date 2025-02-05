package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Cart;
import com.himedia.spserver.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMember(Member member);
    List<Cart> findByMemberUserid(String userid);

    Cart findByCseq(int cseq);
}
