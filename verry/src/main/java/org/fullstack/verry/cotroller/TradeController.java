package org.fullstack.verry.cotroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeDTO;
import org.fullstack.verry.service.TradeService;
import org.fullstack.verry.utils.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    private final TradeService tradeService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        pageRequestDTO.setPage_size(9);
        PageResponseDTO<TradeDTO> pageResponseDTO = tradeService.list(pageRequestDTO);

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("pageName", "shop");
    }

    @GetMapping("/regist")
    public void regist(Model model) {
        model.addAttribute("pageName", "shop");
    }

    @PostMapping("/regist")
    public String registPost(@Valid TradeDTO tradeDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("file") MultipartFile multipartFile) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("tradeDTO", tradeDTO);

            return "redirect:/trade/regist";
        }

        String saveFileName = "";

        if(!multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "product");
            tradeDTO.setOrgFileName(multipartFile.getOriginalFilename());
            tradeDTO.setSaveFileName(saveFileName);
        } else {
            tradeDTO.setOrgFileName("product-10.jpg");
            tradeDTO.setSaveFileName("product-10.jpg");
        }

        int result = tradeService.regist(tradeDTO);

        if(result > 0) {
            return "redirect:/trade/list";
        } else {
            return "redirect:/trade/regist";
        }
    }

    @GetMapping("/view")
    public void view(int trade_idx, PageRequestDTO pageRequestDTO, Model model) {
        TradeDTO tradeDTO = tradeService.view(trade_idx);

        List<TradeDTO> relatedList = tradeService.relatedProducts(tradeDTO.getCategory(), trade_idx);

        model.addAttribute("tradeDTO", tradeDTO);
        model.addAttribute("relatedList", relatedList);
        model.addAttribute("pageName", "shop");
    }

    @GetMapping("/modify")
    public void modify(int trade_idx, Model model) {
        TradeDTO tradeDTO = tradeService.view(trade_idx);

        model.addAttribute("tradeDTO", tradeDTO);
        model.addAttribute("pageName", "shop");
    }

    @PostMapping("/modify")
    public String modifyPost(@Valid TradeDTO tradeDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("file") MultipartFile multipartFile) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("tradeDTO", tradeDTO);

            return "redirect:/trade/modify?trade_idx=" + tradeDTO.getTradeIdx();
        }

        String saveFileName = "";

        if(!multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "product");
            FileUploadUtil.deleteFile(tradeDTO.getSaveFileName(), "product");
            tradeDTO.setOrgFileName(multipartFile.getOriginalFilename());
            tradeDTO.setSaveFileName(saveFileName);
        } else {
            // 기존 파일 유지
            tradeDTO.setOrgFileName(tradeDTO.getOrgFileName());
            tradeDTO.setSaveFileName(tradeDTO.getSaveFileName());
        }

        int result = tradeService.modify(tradeDTO);

        if(result > 0) {
            return "redirect:/trade/view?trade_idx="+tradeDTO.getTradeIdx();
        } else {
            return "redirect:/trade/modify?trade_idx="+tradeDTO.getTradeIdx();
        }
    }

    @PostMapping("/delete")
    public String delete(int tradeIdx) {

        TradeDTO dto = tradeService.view(tradeIdx);
        if(dto != null) {
            FileUploadUtil.deleteFile(dto.getSaveFileName(), "product");
        }

        tradeService.deleteOne(tradeIdx);

        return "redirect:/trade/list";
    }
}
