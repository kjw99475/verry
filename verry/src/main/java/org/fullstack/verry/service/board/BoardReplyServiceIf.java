package org.fullstack.verry.service.board;

import org.fullstack.verry.dto.BoardReplyDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;

public interface BoardReplyServiceIf {
    int regist(BoardReplyDTO replyDTO);

    PageResponseDTO<BoardReplyDTO> getListOfReply(int bbs_idx, PageRequestDTO pageRequestDTO);

    void delete(int idx);
}
