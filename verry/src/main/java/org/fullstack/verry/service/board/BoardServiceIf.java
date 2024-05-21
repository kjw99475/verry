package org.fullstack.verry.service.board;

import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;

public interface BoardServiceIf {
    int regist(BoardDTO boardDTO);
    BoardDTO view(int idx);
    void modify(BoardDTO boardDTO);
    void delete(int idx);
    PageResponseDTO<BoardDTO> list(PageRequestDTO request);
}
