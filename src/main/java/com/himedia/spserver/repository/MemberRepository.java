package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from Member m where m.userid=:userid")
    Member getWithRoles(@Param("userid") String userid );

    Member findByUserid(String userid);

    List<Member> findByNameContaining(String key);

    int countAllBy();

    Page<Member> findAllByNameContaining(String key, Pageable pageable);
}
