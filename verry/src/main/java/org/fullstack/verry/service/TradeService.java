package org.fullstack.verry.service;

import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeDTO;

import java.util.List;

public interface TradeService {

    PageResponseDTO<TradeDTO> list(PageRequestDTO pageRequestDTO);

    int regist(TradeDTO tradeDTO);

    TradeDTO view(int trade_idx);

    List<TradeDTO> relatedProducts(String category, int trade_idx);

    int modify(TradeDTO tradeDTO);

    void deleteOne(int trade_idx);
}
