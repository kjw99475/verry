package org.fullstack.verry.repository.board;

import org.fullstack.verry.domain.BoardEntity;
import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.repository.board.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, BoardSearch {
    @Query(value = "select NOW()", nativeQuery = true)
    public String getNow();

    //List<BoardEntity> findAllByBoardType(String type);

    @Query(value = "select * from tbl_board where board_type=:bType order by idx desc limit :start, :end ", nativeQuery = true)
    List<BoardEntity> findAllByBoardTypeAndLimit(String bType, int start, int end);

    int countAllByBoardType(String boardType);
}
