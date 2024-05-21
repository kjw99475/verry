package org.fullstack.verry.cotroller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class BasicController {
    @GetMapping("/layout/basic")
    public void basicGET() {
        log.info("=========================");
        log.info("BasicController >> basicGET");
        log.info("=========================");
    }

}



