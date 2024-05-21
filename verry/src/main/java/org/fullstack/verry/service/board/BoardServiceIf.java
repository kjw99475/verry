package org.fullstack.verry.service.board;

import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;

import java.util.List;

public interface BoardServiceIf {
    int regist(BoardDTO boardDTO);
    BoardDTO view(int idx);
    int modify(BoardDTO boardDTO);
    void delete(int idx);
    List<BoardDTO> list(String type, int block);
    int countAll(String type);
}
