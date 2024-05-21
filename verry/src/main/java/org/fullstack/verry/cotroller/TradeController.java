package org.fullstack.verry.cotroller;

import lombok.RequiredArgsConstructor;
import org.fullstack.verry.dto.TradeDTO;
import org.fullstack.verry.service.TradeService;
import org.fullstack.verry.utils.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    private final TradeService tradeService;

    @GetMapping("/list")
    public void list() {

    }

    @GetMapping("/regist")
    public void regist() {

    }

    @PostMapping("/regist")
    public String registPost(TradeDTO tradeDTO, @RequestParam("file") MultipartFile multipartFile) {
        String saveFileName = "";

        if(!multipartFile.isEmpty()) {
            saveFileName = FileUploadUtil.saveFile(multipartFile, "trade");
            tradeDTO.setOrgFileName(multipartFile.getOriginalFilename());
            tradeDTO.setSaveFileName(saveFileName);
        } else {
            // TODO: 파일 없을 때 기본 이미지?
        }

        int result = tradeService.regist(tradeDTO);

        if(result > 0) {
            return "redirect:/trade/list";
        } else {
            return "redirect:/trade/regist";
        }
    }
}
