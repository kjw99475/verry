package org.fullstack.verry.repository.board.search;

import org.fullstack.verry.domain.BoardEntity;
import org.fullstack.verry.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<BoardEntity> search(Pageable pageable, String[] types, String search_keyword);
    Page<BoardDTO> searchWithReplyCnt(Pageable pageable, String[] types, String search_keyword);
}
