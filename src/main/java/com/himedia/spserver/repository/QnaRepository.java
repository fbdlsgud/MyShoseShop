package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Integer> {

    Qna findByQseq(int qseq);




    int countBySubjectContaining(String key);

    List<Qna> findBySubjectContaining(String key);

    Page<Qna> findAllBySubjectContaining(String key, Pageable pageable);

    int countAllBy();
}
