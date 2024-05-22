package org.fullstack.verry.repository.board;

import org.fullstack.verry.domain.BoardReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Integer> {
    @Query(value="select r from BoardReplyEntity r where r.board.idx = :idx")
    Page<BoardReplyEntity> listOfBoardReply(@Param("idx") Integer idx, Pageable pageable);
}
