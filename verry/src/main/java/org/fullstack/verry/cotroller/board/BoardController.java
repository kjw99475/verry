package org.fullstack.verry.cotroller.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.util.FileUtil;
import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.service.board.BoardServiceIf;
import org.fullstack.verry.utils.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        model.addAttribute("total_count", total_count);
        model.addAttribute("block", block);
        model.addAttribute("pageName", "community");

        log.info("BoardController >> list END");
        log.info("=================================================");
    }

    @GetMapping("/bbs/view")
    public void view(int idx, PageRequestDTO pageRequestDTO, Model model) {
        log.info("=================================================");
        log.info("BoardController >> view START");

        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("pageName", "community");

        log.info("boardDTO : {}", boardDTO);
        log.info("BoardController >> view END");
        log.info("=================================================");
    }

    @GetMapping("/bbs/download")
    public void downloadBbs(int idx,
                            HttpServletResponse resp,
                            HttpServletRequest req
                           ) {
        BoardDTO boardDTO = boardService.view(idx);

        FileUploadUtil.download(req, resp, boardDTO.getOrgFileName(), boardDTO.getSaveFileName(), "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\board");
    }

    @GetMapping("/bbs/regist")
    public void registGET(PageRequestDTO pageRequestDTO, Model model) {
        log.info("registGET");
    }

    @PostMapping("/bbs/regist")
    public String registPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multipartFile, Model model) {
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


        String saveFileName = "";

        if(multipartFile!= null && !multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\board");
            boardDTO.setOrgFileName(multipartFile.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
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
        model.addAttribute("pageName", "community");
    }

    @PostMapping("/bbs/modify")
    public String modifyPOST(@Valid BoardDTO boardDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @RequestParam(value = "upload", defaultValue = "") String upload,
                             @RequestParam(value = "upload2", defaultValue = "") String upload2,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        log.info("=================================================");
        log.info("BoardController >> modifyPOST START");

//        boardDTO.setBoardType("b");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> modifyPOST ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> modifyPOST END");
            log.info("============================================");
            return "redirect:/bbs/modify?idx=" + boardDTO.getIdx();
        }


        String saveFileName = "";

        if(file!= null && !file.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(file, "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\board");
            boardDTO.setOrgFileName(file.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
            FileUploadUtil.deleteFile(upload2, "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\board");
        } else {
            boardDTO.setOrgFileName(upload);
            boardDTO.setSaveFileName(upload2);
        }



        int result_idx = boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);



        log.info("BoardController >> modifyPOST END");
        log.info("=================================================");

        return "redirect:/bbs/view?idx=" + boardDTO.getIdx();
    }

    @PostMapping("/bbs/delete")
    public String delete(int idx, RedirectAttributes redirectAttributes) {
        BoardDTO boardDTO = boardService.view(idx);
        if (boardDTO.getSaveFileName() != null && !boardDTO.getSaveFileName().isEmpty()) {
            FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\board");
        }
        boardService.delete(idx);
        return "redirect:/bbs/list";
    }

    @RequestMapping(value = "/bbs/deleteFile", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
    @ResponseBody
    public String deleteFilePOST(@RequestParam int idx) {
        BoardDTO boardDTO = boardService.view(idx);
        FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\board");
        boardDTO.setOrgFileName("");
        boardDTO.setSaveFileName("");
        boardService.regist(boardDTO);
        return "ok";
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
        model.addAttribute("total_count", total_count);
        model.addAttribute("block", block);
        model.addAttribute("pageName", "notice");

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
        model.addAttribute("pageName", "notice");

        log.info("BoardController >> view END");
        log.info("=================================================");
    }

    @GetMapping("/notice/download")
    public void downloadNotice(int idx,
                            HttpServletResponse resp,
                            HttpServletRequest req
    ) {
        BoardDTO boardDTO = boardService.view(idx);

        FileUploadUtil.download(req, resp, boardDTO.getOrgFileName(), boardDTO.getSaveFileName(), "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\notice");
    }

    @GetMapping("/notice/regist")
    public void registGETNotice(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("pageName", "notice");
        log.info("registGET");
    }

    @PostMapping("/notice/regist")
    public String registPOSTNotice(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multipartFile, Model model) {
        log.info("=================================================");
        log.info("BoardController >> registPOSTNotice START");

        boardDTO.setBoardType("n");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> registPOSTNotice ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info(bindingResult.getAllErrors());
            log.info("BoardController >> registPOSTNotice END");
            log.info("============================================");
            return "redirect:/notice/regist";
        }


        String saveFileName = "";

        if(multipartFile!= null && !multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\notice");
            boardDTO.setOrgFileName(multipartFile.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
        }


        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);


        log.info("boardDTO : {}", boardDTO);
        log.info("result_idx : {}" , result_idx);
        log.info("BoardController >> registPOSTNotice END");
        log.info("=================================================");

        return "redirect:/notice/list";
    }

    @GetMapping("/notice/modify")
    public void modifyGETNotice(int idx, Model model) {
        log.info("modifyGET");
        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("pageName", "notice");
    }

    @PostMapping("/notice/modify")
    public String modifyPOSTNotice(@Valid BoardDTO boardDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @RequestParam(value = "upload", defaultValue = "") String upload,
                             @RequestParam(value = "upload2", defaultValue = "") String upload2,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        log.info("=================================================");
        log.info("BoardController >> modifyPOSTNotice START");

        boardDTO.setBoardType("n");
        if (bindingResult.hasErrors()) {
            log.info("BoardController >> modifyPOSTNotice ERROR");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            log.info("BoardController >> modifyPOSTNotice END");
            log.info("============================================");
            return "redirect:/notice/modify?idx=" + boardDTO.getIdx();
        }


        String saveFileName = "";

        if(file!= null && !file.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(file, "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\notice");
            boardDTO.setOrgFileName(file.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
            FileUploadUtil.deleteFile(upload2, "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\notice");
        } else {
            boardDTO.setOrgFileName(upload);
            boardDTO.setSaveFileName(upload2);
        }



        int result_idx = boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);



        log.info("BoardController >> modifyPOSTNotice END");
        log.info("=================================================");

        return "redirect:/notice/view?idx=" + boardDTO.getIdx();
    }
    


    @PostMapping("/notice/delete")
    public String deleteNotice(int idx, RedirectAttributes redirectAttributes) {
        BoardDTO boardDTO = boardService.view(idx);
        if (boardDTO.getSaveFileName() != null && !boardDTO.getSaveFileName().isEmpty()) {
            FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\notice");
        }
        boardService.delete(idx);
        return "redirect:/notice/list";
    }

    @RequestMapping(value = "/notice/deleteFile", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
    @ResponseBody
    public String deleteFilePOSTNotice(@RequestParam int idx) {
        BoardDTO boardDTO = boardService.view(idx);
        FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "D:\\java4\\verry\\verry\\src\\main\\resources\\static\\uploads\\notice");
        boardDTO.setOrgFileName("");
        boardDTO.setSaveFileName("");
        boardService.regist(boardDTO);
        return "ok";
    }
}
