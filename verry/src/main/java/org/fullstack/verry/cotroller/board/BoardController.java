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

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("block_start", block_start);
        model.addAttribute("block_end", block_end);
        model.addAttribute("total_count", total_count);
        model.addAttribute("block", block);
        model.addAttribute("pageName", "community");
    }

    @GetMapping("/bbs/view")
    public void view(int idx, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("pageName", "community");
    }

    @GetMapping("/bbs/download")
    public void downloadBbs(int idx,
                            HttpServletResponse resp,
                            HttpServletRequest req
                           ) {
        BoardDTO boardDTO = boardService.view(idx);

        FileUploadUtil.download(req, resp, boardDTO.getOrgFileName(), boardDTO.getSaveFileName(), "uploads\\board");
    }

    @GetMapping("/bbs/regist")
    public void registGET(PageRequestDTO pageRequestDTO, Model model) {

    }

    @PostMapping("/bbs/regist")
    public String registPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multipartFile, Model model) {
        boardDTO.setBoardType("b");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("boardDTO", boardDTO);
            return "redirect:/bbs/regist";
        }

        String saveFileName = "";
        if(multipartFile!= null && !multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "uploads\\board");
            boardDTO.setOrgFileName(multipartFile.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
        }

        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);

        return "redirect:/bbs/list";
    }

    @GetMapping("/bbs/modify")
    public void modifyGET(int idx, Model model) {
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

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/bbs/modify?idx=" + boardDTO.getIdx();
        }

        String saveFileName = "";

        if(file!= null && !file.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(file, "uploads\\board");
            boardDTO.setOrgFileName(file.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
            FileUploadUtil.deleteFile(upload2, "uploads\\board");
        } else {
            boardDTO.setOrgFileName(upload);
            boardDTO.setSaveFileName(upload2);
        }

        int result_idx = boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);
        return "redirect:/bbs/view?idx=" + boardDTO.getIdx();
    }

    @PostMapping("/bbs/delete")
    public String delete(int idx, RedirectAttributes redirectAttributes) {
        BoardDTO boardDTO = boardService.view(idx);
        if (boardDTO.getSaveFileName() != null && !boardDTO.getSaveFileName().isEmpty()) {
            FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "uploads\\board");
        }
        boardService.delete(idx);
        return "redirect:/bbs/list";
    }

    @RequestMapping(value = "/bbs/deleteFile", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
    @ResponseBody
    public String deleteFilePOST(@RequestParam int idx) {
        BoardDTO boardDTO = boardService.view(idx);
        log.info(boardDTO.getSaveFileName());
        FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "uploads\\board");
        boardDTO.setOrgFileName("");
        boardDTO.setSaveFileName("");
        boardService.regist(boardDTO);
        return "ok";
    }


    @GetMapping("/notice/list")
    public void listNotice(@RequestParam(name="block", defaultValue = "1") int block, PageRequestDTO pageRequestDTO, Model model) {
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

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("block_start", block_start);
        model.addAttribute("block_end", block_end);
        model.addAttribute("total_count", total_count);
        model.addAttribute("block", block);
        model.addAttribute("pageName", "notice");

        model.addAttribute("pageResponseDTO", pageResponseDTO);
    }

    @GetMapping("/notice/view")
    public void viewNotice(int idx, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.view(idx);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("pageName", "notice");
    }

    @GetMapping("/notice/download")
    public void downloadNotice(int idx,
                            HttpServletResponse resp,
                            HttpServletRequest req
    ) {
        BoardDTO boardDTO = boardService.view(idx);

        FileUploadUtil.download(req, resp, boardDTO.getOrgFileName(), boardDTO.getSaveFileName(), "uploads\\notice");
    }

    @GetMapping("/notice/regist")
    public void registGETNotice(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("pageName", "notice");
    }

    @PostMapping("/notice/regist")
    public String registPOSTNotice(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multipartFile, Model model) {

        boardDTO.setBoardType("n");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/notice/regist";
        }

        String saveFileName = "";

        if(multipartFile!= null && !multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "uploads\\notice");
            boardDTO.setOrgFileName(multipartFile.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
        }

        int result_idx = boardService.regist(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);

        return "redirect:/notice/list";
    }

    @GetMapping("/notice/modify")
    public void modifyGETNotice(int idx, Model model) {
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
        boardDTO.setBoardType("n");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/notice/modify?idx=" + boardDTO.getIdx();
        }

        String saveFileName = "";

        if(file!= null && !file.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(file, "uploads\\notice");
            boardDTO.setOrgFileName(file.getOriginalFilename());
            boardDTO.setSaveFileName(saveFileName);
            FileUploadUtil.deleteFile(upload2, "uploads\\notice");
        } else {
            boardDTO.setOrgFileName(upload);
            boardDTO.setSaveFileName(upload2);
        }

        int result_idx = boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result_idx", result_idx);

        return "redirect:/notice/view?idx=" + boardDTO.getIdx();
    }
    


    @PostMapping("/notice/delete")
    public String deleteNotice(int idx, RedirectAttributes redirectAttributes) {
        BoardDTO boardDTO = boardService.view(idx);
        if (boardDTO.getSaveFileName() != null && !boardDTO.getSaveFileName().isEmpty()) {
            FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "uploads\\notice");
        }
        boardService.delete(idx);
        return "redirect:/notice/list";
    }

    @RequestMapping(value = "/notice/deleteFile", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
    @ResponseBody
    public String deleteFilePOSTNotice(@RequestParam int idx) {
        BoardDTO boardDTO = boardService.view(idx);
        FileUploadUtil.deleteFile(boardDTO.getSaveFileName(), "uploads\\notice");
        boardDTO.setOrgFileName("");
        boardDTO.setSaveFileName("");
        boardService.regist(boardDTO);
        return "ok";
    }
}
