package org.fullstack.verry.repository.board;

import org.fullstack.verry.domain.BoardEntity;
import org.fullstack.verry.repository.board.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, BoardSearch {
    @Query(value = "select NOW()", nativeQuery = true)
    public String getNow();
}
