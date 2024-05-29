package org.fullstack.verry.cotroller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.dto.MemberDTO;
import org.fullstack.verry.service.MemberServiceIf;
import org.fullstack.verry.service.MemberServiceImpl;
import org.fullstack.verry.service.board.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

import static java.util.regex.Pattern.matches;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberServiceIf memberService;
    @Autowired
    private BoardServiceImpl boardServiceImpl;

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
        int idcount = memberService.membercount(memberDTO.getMemberId());
        if (bindingResult.hasErrors()) {
            log.info("Errors");
            log.info("error : {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("memberDTO", memberDTO);
            if(idcount != 0){
                redirectAttributes.addFlashAttribute("message", "사용불가능한 아이디입니다");
            }

            return "redirect:/member/join";
        }
        if(idcount != 0){
            redirectAttributes.addFlashAttribute("memberDTO", memberDTO);
            redirectAttributes.addFlashAttribute("message", "사용불가능한 아이디입니다");
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
    public String postlogin(@RequestParam(name = "id", defaultValue = "") String memberId,
                            @RequestParam(name = "pwd", defaultValue = "") String pwd,
                            HttpSession session,
                            RedirectAttributes redirectAttributes){
        if(!memberId.isEmpty()&&!pwd.isEmpty()) {
            MemberDTO memberinfo = memberService.memberinfo(memberId);
            if (memberinfo != null) {
                if (!pwd.equals(memberinfo.getPwd())) {
                    redirectAttributes.addFlashAttribute("id", memberId);
                    return "redirect:/member/login";
                } else {
                    session.setAttribute("memberName", memberinfo.getName());
                    session.setAttribute("memberId", memberId);
                    session.setAttribute("memberType", memberinfo.getMemberType());
                    return "redirect:/trade/list";
                }
            } else {

            }
        }else{
            redirectAttributes.addFlashAttribute("id", memberId);
        }
        return "redirect:/member/login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/basic";
    }
    @GetMapping("/modify")
    public void modify(HttpSession session,
                       Model model){
        String memberId = (String)session.getAttribute("memberId");
        MemberDTO memberinfo = memberService.memberinfo(memberId);
        model.addAttribute("memberDTO",memberinfo);
    }
    @PostMapping("/modify")
    public String postmodify(HttpSession session,
                           MemberDTO memberDTO,
                           @RequestParam(value = "pwdChangeYN", defaultValue = "N") String pwdChangeYN){
        String memberId = (String)session.getAttribute("memberId");
        MemberDTO memberinfo = memberService.memberinfo(memberId);
        log.info("memberDTO : {}",memberDTO);
        if(pwdChangeYN.equals("Y")){
            memberinfo.setPwd(memberDTO.getPwd().isEmpty()?memberinfo.getPwd():memberDTO.getPwd());
        }
        memberinfo.setAddr(memberDTO.getAddr().isEmpty()?memberinfo.getAddr():memberDTO.getAddr());
        memberinfo.setAddrDetail(memberDTO.getAddrDetail().isEmpty()?memberinfo.getAddrDetail():memberDTO.getAddrDetail());
        memberinfo.setEmail(memberDTO.getEmail().isEmpty()?memberinfo.getEmail():memberDTO.getEmail());
        memberinfo.setName(memberDTO.getName().isEmpty()?memberinfo.getName():memberDTO.getName());
        memberinfo.setZipcode(memberDTO.getZipcode().isEmpty()?memberinfo.getZipcode():memberDTO.getZipcode());
        memberinfo.setPhone(memberDTO.getPhone().isEmpty()?memberinfo.getPhone():memberDTO.getPhone());
        int idx = memberService.join(memberinfo);

        return "redirect:/member/modify";
    }
    @ResponseBody
    @PostMapping("/idcheck")
    public int idcheck(@RequestParam(name = "memberId", defaultValue = "") String memberId,
                       HttpSession session){
//        if(){
            log.info(matches("^[a-z0-9]{4,12}", memberId));
//        }
        int idcount = memberService.membercount(memberId);
        if(idcount == 0){
            session.setAttribute("idcheck", "Y");
        }else{
            session.setAttribute("idcheck", "N");
        }
        return idcount;
    }
}
