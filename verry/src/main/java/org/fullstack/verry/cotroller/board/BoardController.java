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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardServiceIf boardService;

    @GetMapping("/bbs/list")
    public void list(@RequestParam(name="block", defaultValue = "1") int block, Model model) {
        log.info("=================================================");
        log.info("BoardController >> list START");

        String type = "b";
        List<BoardDTO> pageResponseDTO = boardService.list(type, (block-1)*10, 10);
        int block_start = (int)(Math.ceil(block / (double)10) -1 ) * 10 + 1;
        int total_count = boardService.countAll(type);
        int block_end = 0;
        if (total_count/10 < 1) {
            block_end = 1;
        } else {
            block_end = (int)Math.ceil(block/(double)10)*10;
            block_end = (block_end < 1 ? 1 : block_end);
            block_end = (total_count/10 +1 > block_end ? block_end : total_count/10 +1);
        }

        log.info("pageResponseDTO : {}", pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("block_start", block_start);
        model.addAttribute("block_end", block_end);

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

        boardDTO.setBoardType("b");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> registPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info(bindingResult.getAllErrors());
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

    @GetMapping("/bbs/modify")
    public void modifyGET(int idx, Model model) {
        log.info("modifyGET");
        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/bbs/modify")
    public String modifyPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("=================================================");
        log.info("BoardController >> modifyPOST START");

        boardDTO.setBoardType("b");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> modifyPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> modifyPOST END");
            log.info("============================================");
            return "redirect:/bbs/modify?idx=" + boardDTO.getIdx();
        }
        int result_idx = boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);



        log.info("BoardController >> modifyPOST END");
        log.info("=================================================");

        return "/bbs/view?idx=" + boardDTO.getIdx();
    }

    @GetMapping("/bbs/delete")
    public void delete(int idx, Model model) {
        boardService.delete(idx);
    }


    @GetMapping("/notice/list")
    public void listNotice(@RequestParam(name="block", defaultValue = "1") int block, PageRequestDTO pageRequestDTO, Model model) {
        log.info("=================================================");
        log.info("BoardController >> list START");

        String type = "n";
        List<BoardDTO> pageResponseDTO = boardService.list(type, (block-1)*10, 10);
        int block_start = (int)(Math.ceil(block / (double)10) -1 ) * 10 + 1;
        int total_count = boardService.countAll(type);
        int block_end = 0;
        if (total_count < 1) {
            block_end = 1;
        } else {
            block_end = (int)Math.ceil(block/(double)10)*10;
            block_end = (block_end < 1 ? 1 : block_end);
            block_end = (total_count > block_end ? block_end : total_count);
        }

        log.info("pageResponseDTO : {}", pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("block_start", block_start);
        model.addAttribute("block_end", block_end);

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

        boardDTO.setBoardType("n");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> registPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> registPOST END");
            log.info("============================================");
            return "redirect:/notice/regist";
        }
        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);


        log.info("boardDTO : {}", boardDTO);
        log.info("result_idx : {}" , result_idx);
        log.info("BoardController >> registPOST END");
        log.info("=================================================");

        return "redirect:/notice/list";
    }

    @GetMapping("/notice/modify")
    public void modifyGETNotice(int idx, Model model) {
        log.info("modifyGET");
        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/notice/modify")
    public String modifyPOSTNotice(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("=================================================");
        log.info("BoardController >> modifyPOST START");

        boardDTO.setBoardType("b");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> modifyPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> modifyPOST END");
            log.info("============================================");
            return "redirect:/notice/modify?idx=" + boardDTO.getIdx();
        }
        int result_idx = boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);



        log.info("BoardController >> modifyPOST END");
        log.info("=================================================");

        return "/notice/view?idx=" + boardDTO.getIdx();
    }


    @GetMapping("/notice/delete")
    public void deleteNotice(int idx, Model model) {
        boardService.delete(idx);
    }
}
