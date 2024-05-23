package org.fullstack.verry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ServletComponentScan
@EnableJpaAuditing
@SpringBootApplication
public class VerryApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerryApplication.class, args);
    }

}
