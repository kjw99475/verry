package org.fullstack.verry.cotroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.TradeEntity;
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
import java.util.stream.Collectors;

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

        PageResponseDTO<TradeDTO> tradeDTO = tradeService.mainShoplist(pageRequestDTO);
        List<TradeDTO> dto = tradeDTO.getDtoList();
        model.addAttribute("tradeDTO", tradeDTO);

        List<TradeDTO> category1List = tradeService.mainCategoryList("유아");
        List<TradeDTO> category2List = tradeService.mainCategoryList("초등");
        List<TradeDTO> category3List = tradeService.mainCategoryList("중등");
        List<TradeDTO> category4List = tradeService.mainCategoryList("고등");
        List<TradeDTO> category5List = tradeService.mainCategoryList("교과서/참고서");
        List<TradeDTO> category6List = tradeService.mainCategoryList("기타");
        model.addAttribute("category1List", category1List);
        model.addAttribute("category2List", category2List);
        model.addAttribute("category3List", category3List);
        model.addAttribute("category4List", category4List);
        model.addAttribute("category5List", category5List);
        model.addAttribute("category6List", category6List);

        List<BoardDTO> noticeDTO = boardService.list("n", 0, 3);
        model.addAttribute("noticeDTO", noticeDTO);

        List<BoardDTO> boardDTO = boardService.list("b", 0, 3);
        model.addAttribute("boardDTO", boardDTO);

        model.addAttribute("pageName", "home");

        log.info("=========================");
    }

}



