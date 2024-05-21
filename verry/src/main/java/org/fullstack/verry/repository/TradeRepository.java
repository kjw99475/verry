package org.fullstack.verry.repository;

import org.fullstack.verry.domain.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
}
