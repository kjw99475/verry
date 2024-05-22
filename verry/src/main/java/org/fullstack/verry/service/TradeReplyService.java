package org.fullstack.verry.service;

import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeReplyDTO;

public interface TradeReplyService {
    int regist(TradeReplyDTO replyDTO);

    void delete(int idx);

    PageResponseDTO<TradeReplyDTO> getListOfReply(int trade_idx, PageRequestDTO pageRequestDTO);
}
