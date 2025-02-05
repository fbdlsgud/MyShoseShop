package com.himedia.spserver.service;

import com.himedia.spserver.dto.Paging;
import com.himedia.spserver.entity.Qna;
import com.himedia.spserver.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Service
@Transactional
public class CustomerService {

    @Autowired
    QnaRepository qR;


    public HashMap<String, Object> getQnaList(int page) {
        HashMap<String, Object> result = new HashMap<>();

        Paging paging = new Paging();
        paging.setPage(page);
        int count = qR.findBySubjectContaining("환불").size();
        System.out.println(count);
        paging.setTotalCount(count);
        paging.calPaging();

        Pageable pageable = PageRequest.of( page , paging.getDisplayRow() , Sort.by(Sort.Direction.DESC, "qseq"));
        Page<Qna> pageList = qR.findAllBySubjectContaining( "환불", pageable );

        result.put("qnaList", pageList.getContent() );
        result.put("paging", paging);

        return result;
    }


    public Qna getQna(int qseq) {
        return qR.findByQseq(qseq);
    }

    public void insertQna(Qna qna) {
        qR.save(qna);
    }
}
