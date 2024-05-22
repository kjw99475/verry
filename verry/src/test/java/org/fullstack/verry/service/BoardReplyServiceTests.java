package org.fullstack.verry.service;

import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.BoardReplyDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.service.board.BoardReplyServiceIf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class BoardReplyServiceTests {
    @Autowired(required = false)
    private BoardReplyServiceIf boardReplyService;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.getListOfReply(14, pageRequestDTO);

        log.info("testList : {}", responseDTO.getDtoList());
    }
}
