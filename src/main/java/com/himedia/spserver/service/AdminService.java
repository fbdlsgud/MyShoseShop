package com.himedia.spserver.service;

import com.himedia.spserver.dto.Paging;
import com.himedia.spserver.entity.*;
import com.himedia.spserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    ProductRepository pR;
    @Autowired
    MemberRepository mR;
    @Autowired
    OrderDetailReposiory oR;
    @Autowired
    QnaRepository qR;


    public HashMap<String, Object> getPrductList(int page, String key) {
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        if( key.equals("") ){
            // 화면 아래쪽 숫자 페이지를 표시하기위한 객체 설정
            paging.setDisplayPage(10);
            paging.setDisplayRow(10);
            paging.setPage(page);
            int count = pR.findAll().size();
            System.out.println("count : " + count);
            paging.setTotalCount(count);
            paging.calPaging();

            // 해당 페이지의 게시물을 검색
            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "pseq"));
            Page<Product> list = pR.findAll( pageable );
            result.put("productList", list.getContent());
        }else{

            paging.setDisplayPage(10);
            paging.setDisplayRow(10);
            paging.setPage(page);
            int count = pR.findByNameContaining(key).size();
            System.out.println("count : " + count);
            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "pseq"));
            Page<Product> list = pR.findAllByNameContaining( key, pageable );
            System.out.println(key + " list : " + list.getContent().size());
            result.put("productList", list.getContent());

        }
        result.put("paging", paging);
        result.put("key", key);
        return result;
    }

    public void insertProduct(Product product) {
        pR.save(product);
    }

    public Product getProduct(int pseq) {
        return pR.findByPseq(pseq);
    }

    public void deleteProduct(int pseq) {
        Product product = pR.findByPseq(pseq);
        pR.delete(product);
    }

    public void updateProduct(Product product) {
        Product updateProduct = pR.findByPseq(product.getPseq());
        updateProduct.setName(product.getName());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setBestyn(product.getBestyn());
        updateProduct.setUseyn(product.getUseyn());
        updateProduct.setContent(product.getContent());
        updateProduct.setImage(product.getImage());
        updateProduct.setSavefilename(product.getSavefilename());
        updateProduct.setPrice1(product.getPrice1());
        updateProduct.setPrice2(product.getPrice2());
        updateProduct.setPrice3(product.getPrice3());
    }


    @Autowired
    OrderViewRepository ovR;

    public HashMap<String, Object> getOrderList(int page, String key) {
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setDisplayPage(10);
        paging.setDisplayRow(10);
        paging.setPage(page);
        if( key.equals("") ||  key == null){
            //int count = oR.findAll().size();
            int count = oR.countAllBy();
            System.out.println("order count : " + count);
            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "odseq"));
            Page<Order_Detail> list = oR.findAll( pageable );
            result.put("orderList", list.getContent());
        }else{

            System.out.println("orderList Key : " + key + "  --------------------------------------------");
            int count = oR.getCount(key);
            System.out.println("order key count : " + count  + "  --------------------------------------------");

            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "odseq"));

            Page<Order_Detail> orderList = oR.getOrderList(key, pageable);
            System.out.println(key + " orderList size : " + orderList.getContent().size() + "--------------------------------");
            result.put("orderList", orderList.getContent());

        }
        result.put("paging", paging);
        result.put("key", key);
        return result;
    }

    public HashMap<String, Object> getMemberList(int page, String key) {
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setDisplayPage(10);
        paging.setDisplayRow(10);
        paging.setPage(page);
        if( key.equals("") ||  key == null){
            //int count = oR.findAll().size();
            int count = mR.countAllBy();
            System.out.println("order count : " + count);
            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "indate"));
            Page<Member> list = mR.findAll( pageable );
            result.put("memberList", list.getContent());
        }else{
            System.out.println("key : " + key);
            paging.setDisplayPage(10);
            paging.setDisplayRow(10);
            paging.setPage(page);
            int count = mR.findByNameContaining(key).size();
            System.out.println("count : " + count);
            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "indate"));
            Page<Member> list = mR.findAllByNameContaining( key, pageable );
            System.out.println(key + " list : " + list.getContent().size());
            result.put("memberList", list.getContent());

        }
        result.put("paging", paging);
        result.put("key", key);
        return result;

    }

    public void nextResult(int odseq) {
        Order_Detail od = oR.findByOdseq( odseq );
        if( od.getResult() < 3 )  od.setResult( od.getResult()+ 1 );
    }

    public void changeRoleAdmin(String userid) {
        Member member = mR.findByUserid( userid );
        List<MemberRole> roleList = new ArrayList<>();
        roleList.add(MemberRole.valueOf("USER"));
        roleList.add(MemberRole.valueOf("ADMIN"));
        member.setMemberRoleList( roleList );
    }

    public void changeRoleUser(String userid) {
        Member member = mR.findByUserid( userid );
        List<MemberRole> roleList = new ArrayList<>();
        roleList.add(MemberRole.valueOf("USER"));
        member.setMemberRoleList( roleList );
    }

    public HashMap<String, Object> getQnaList(int page, String key) {
        HashMap<String, Object> result = new HashMap<>();
        Paging paging = new Paging();
        paging.setDisplayPage(10);
        paging.setDisplayRow(10);
        paging.setPage(page);

        if( key.equals("") ||  key == null){

            int count = qR.countAllBy();
            System.out.println("order count : " + count);
            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "indate"));
            Page<Qna> list = qR.findAll( pageable );
            result.put("qnaList", list.getContent());

        }else{

            int count = qR.findBySubjectContaining(key).size();
            paging.setTotalCount(count);
            paging.calPaging();

            Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC, "indate"));
            Page<Qna> list = qR.findAllBySubjectContaining( key, pageable );
            System.out.println(key + " list : " + list.getContent().size());
            result.put("qnaList", list.getContent());
        }
        result.put("paging", paging);
        result.put("key", key);
        return result;
    }

    public Qna getQna(int qseq) {
        return qR.findByQseq( qseq );
    }

    public void updateReply(String reply, int qseq) {
        Qna qna = qR.findByQseq( qseq );
        qna.setReply( reply );
    }
}
