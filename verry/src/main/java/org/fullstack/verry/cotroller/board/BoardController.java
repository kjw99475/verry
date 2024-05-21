package org.fullstack.verry.cotroller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.service.board.BoardServiceIf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardServiceIf boardService;

    @GetMapping("/bbs/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("=================================================");
        log.info("BoardController >> list START");

        pageRequestDTO.setType("b");
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);

        log.info("pageResponseDTO : {}", pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        log.info("BoardController >> list END");
        log.info("=================================================");
    }

    @GetMapping("/bbs/view")
    public void view(int idx, PageRequestDTO pageRequestDTO, Model model) {
        log.info("=================================================");
        log.info("BoardController >> view START");

        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);

        log.info("BoardController >> view END");
        log.info("=================================================");
    }

    @GetMapping("/bbs/regist")
    public void registGET(PageRequestDTO pageRequestDTO, Model model) {
        log.info("registGET");
    }

    @PostMapping("/bbs/regist")
    public String registPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("=================================================");
        log.info("BoardController >> registPOST START");

        boardDTO.setType("b");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> registPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> registPOST END");
            log.info("============================================");
            return "redirect:/bbs/regist";
        }
        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);


        log.info("boardDTO : {}", boardDTO);
        log.info("result_idx : {}" , result_idx);
        log.info("BoardController >> registPOST END");
        log.info("=================================================");

        return "redirect:/bbs/list";
    }

    @GetMapping("/bbs/delete")
    public void delete(int idx, Model model) {
        boardService.delete(idx);
    }


    @GetMapping("/notice/list")
    public void listNotice(PageRequestDTO pageRequestDTO, Model model) {
        log.info("=================================================");
        log.info("BoardController >> list START");

        pageRequestDTO.setType("n");
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);

        log.info("pageResponseDTO : {}", pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        log.info("BoardController >> list END");
        log.info("=================================================");
    }

    @GetMapping("/notice/view")
    public void viewNotice(int idx, PageRequestDTO pageRequestDTO, Model model) {
        log.info("=================================================");
        log.info("BoardController >> view START");

        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);

        log.info("BoardController >> view END");
        log.info("=================================================");
    }

    @GetMapping("/notice/regist")
    public void registGETNotice(PageRequestDTO pageRequestDTO, Model model) {
        log.info("registGET");
    }

    @PostMapping("/notice/regist")
    public String registPOSTNotice(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("=================================================");
        log.info("BoardController >> registPOST START");

        boardDTO.setType("n");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> registPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> registPOST END");
            log.info("============================================");
            return "redirect:/bbs/regist";
        }
        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);


        log.info("boardDTO : {}", boardDTO);
        log.info("result_idx : {}" , result_idx);
        log.info("BoardController >> registPOST END");
        log.info("=================================================");

        return "redirect:/bbs/list";
    }

    @GetMapping("/notice/delete")
    public void deleteNotice(int idx, Model model) {
        boardService.delete(idx);
    }
}
