package org.fullstack.verry.cotroller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.service.board.BoardReplyServiceIf;
import org.fullstack.verry.dto.BoardReplyDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/bbs/replies")
public class BoardReplyController {

    private final BoardReplyServiceIf boardReplyService;


    @PostMapping(value = "/regist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> regist(
            @Valid @RequestBody BoardReplyDTO replyDTO,
            BindingResult bindingResult
            ) throws BindException {

        log.info("replyDTO : {}", replyDTO);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Integer> map = new HashMap<>();
        int idx = boardReplyService.regist(replyDTO);
        map.put("idx", idx);


        return map;
    }


    @GetMapping("/list/{idx}")
    public PageResponseDTO<BoardReplyDTO> replyList(
            @PathVariable("idx") int idx,
            PageRequestDTO pageRequestDTO
    ) {
        log.info("================================================");
        log.info("BoardReplyController >> replyList START");

        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.getListOfReply(idx, pageRequestDTO);

        log.info("responseDTO : {}", responseDTO);
        log.info("BoardReplyController >> replyList END");
        log.info("================================================");
        return responseDTO;
    }


    @DeleteMapping("/delete/{board_reply_idx}")
    public Map<String, Integer> replyDelete(@PathVariable("board_reply_idx") int board_reply_idx) {
        boardReplyService.delete(board_reply_idx);
        Map<String, Integer> map = new HashMap<>();
        map.put("idx", board_reply_idx);

        log.info("map : {}", map);

        return map;
    }
}
