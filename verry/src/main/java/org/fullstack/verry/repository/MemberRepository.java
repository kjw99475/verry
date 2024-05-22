package org.fullstack.verry.repository;

import org.fullstack.verry.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    MemberEntity findByMemberId(String memberId);
}
