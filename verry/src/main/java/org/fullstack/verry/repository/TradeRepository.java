package org.fullstack.verry.repository;

import org.fullstack.verry.domain.TradeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
    List<TradeEntity> findDistinctTop4ByCategoryAndTradeIdxNotOrderByTradeIdxDesc(String category, int trade_idx);
}
