package org.fullstack.verry.cotroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.MemberDTO;
import org.fullstack.verry.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("/join")
    public void join(MemberDTO memberDTO,
                     Model model){
        log.info("memberDTO : {}",memberDTO.toString());
        model.addAttribute("memberDTO",memberDTO);
    }
    @PostMapping("/join")
    public String postjoin(@Valid MemberDTO memberDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes
                           ){
        log.info("memberDTO : {}",memberDTO.toString());
        if (bindingResult.hasErrors()) {
            log.info("Errors");
            log.info("error : {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("memberDTO", memberDTO);

            return "redirect:/member/join";
        }
        int id = memberService.join(memberDTO);
        redirectAttributes.addFlashAttribute("memberDTO", memberDTO);
        if(id != 0){
            return "redirect:/trade/list";
        }else{
            return "redirect:/member/join";
        }
    }
    @GetMapping("/login")
    public void login(){

    }
    @PostMapping("/login")
    public void postlogin(){

    }
    @GetMapping("/modify")
    public void modify(){

    }
    @PostMapping("/modify")
    public void postmodify(){

    }
}
