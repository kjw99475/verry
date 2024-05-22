package org.fullstack.verry.cotroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeReplyDTO;
import org.fullstack.verry.service.TradeReplyService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/trade/replies")
public class TradeReplyController {

    private final TradeReplyService tradeReplyService;

    @PostMapping(value = "/regist", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> regist(@Valid @RequestBody TradeReplyDTO replyDTO,
                                       BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Integer> map = new HashMap<>();
        int idx = tradeReplyService.regist(replyDTO);

        map.put("idx", idx);

        return map;
    }

    @GetMapping("/list/{trade_idx}")
    public PageResponseDTO<TradeReplyDTO> replyList(@PathVariable(name = "trade_idx") int trade_idx, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<TradeReplyDTO> responseDTO = tradeReplyService.getListOfReply(trade_idx, pageRequestDTO);

        log.info("responseDTO : {}", responseDTO);
        return responseDTO;
    }

    @DeleteMapping("/delete/{trade_reply_idx}")
    public Map<String, Integer> replyDelete(@PathVariable("trade_reply_idx") int trade_reply_idx) {
        tradeReplyService.delete(trade_reply_idx);
        Map<String, Integer> map = new HashMap<>();
        map.put("idx", trade_reply_idx);

        log.info("map : {}", map);

        return map;
    }
}
