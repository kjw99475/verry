package org.fullstack.verry.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    @GetMapping("/list")
    public void list() {

    }
}
