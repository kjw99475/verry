package org.fullstack.verry.service;

import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeDTO;

public interface TradeService {

    PageResponseDTO<TradeDTO> list(PageRequestDTO pageRequestDTO);

    int regist(TradeDTO tradeDTO);
}
