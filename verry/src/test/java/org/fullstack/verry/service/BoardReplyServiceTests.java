package org.fullstack.verry.service;

import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.BoardEntity;
import org.fullstack.verry.domain.BoardReplyEntity;
import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.BoardReplyDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.repository.board.BoardReplyRepository;
import org.fullstack.verry.service.board.BoardReplyServiceIf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Log4j2
@SpringBootTest
public class BoardReplyServiceTests {
    @Autowired(required = false)
    private BoardReplyServiceIf boardReplyService;
    @Autowired(required = false)
    private BoardReplyRepository boardReplyRepository;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.getListOfReply(14, pageRequestDTO);

        log.info("testList : {}", responseDTO.getDtoList());
    }

//    @Test
//    public void testRegist() {
//        int boardIdx = 14;
//        BoardEntity board = BoardEntity.builder().idx(boardIdx).build();
//        BoardReplyEntity reply = BoardReplyEntity.builder()
//                .board(board)
//                .comment("댓글 테스트")
//                .memberId("test")
//                .regDate(LocalDateTime.now())
//                .build();
//
//        boardReplyRepository.save(reply);
//    }

    @Test
    public void testRegistService() {

        BoardReplyDTO replyDTO = BoardReplyDTO.builder()
                .comment("서비스 댓글 테스트")
                .memberId("test")
                .regDate(LocalDateTime.now())
                .boardIdx(14)
                .build();

        log.info(boardReplyService.regist(replyDTO));
    }
}
