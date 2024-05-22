package org.fullstack.verry.repository;

import org.fullstack.verry.domain.TradeReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeReplyRepository extends JpaRepository<TradeReplyEntity, Integer> {

    @Query("SELECT r FROM TradeReplyEntity r  WHERE r.tradeIdx = :tradeIdx")
    Page<TradeReplyEntity> listOfBoardReply(int tradeIdx, PageRequest pageable);
}
