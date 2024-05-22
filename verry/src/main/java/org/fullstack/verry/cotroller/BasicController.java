package org.fullstack.verry.cotroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeDTO;
import org.fullstack.verry.service.TradeService;
import org.fullstack.verry.service.board.BoardServiceIf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BasicController {
    private final BoardServiceIf boardService;
    private final TradeService tradeService;
    @GetMapping("/basic")
    public void basicGET(
            Model model,
            PageRequestDTO pageRequestDTO
    ) {
        log.info("=========================");
        log.info("BasicController >> basicGET");

        PageResponseDTO<TradeDTO> tradeDTO = tradeService.list(pageRequestDTO);
        model.addAttribute("tradeDTO", tradeDTO);


        List<BoardDTO> noticeDTO = boardService.list("n", 0, 3);
        model.addAttribute("noticeDTO", noticeDTO);

        List<BoardDTO> boardDTO = boardService.list("b", 0, 3);
        model.addAttribute("boardDTO", boardDTO);

        model.addAttribute("pageName", "home");

        log.info("=========================");
    }

}



