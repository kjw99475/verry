package org.fullstack.verry.service.board;

import org.fullstack.verry.dto.BoardReplyDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;

public class BoardReplyServiceImpl implements BoardReplyServiceIf{
    @Override
    public int regist(BoardReplyDTO replyDTO) {
        return 0;
    }

    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfReply(int bbs_idx, PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public void delete(int idx) {

    }
}
