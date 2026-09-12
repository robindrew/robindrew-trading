package com.robindrew.taskmanager.app;

import static org.springframework.boot.SpringApplication.run;

import com.robindrew.spring.security.BasicAuthSecurityConfig;
import com.robindrew.taskmanager.config.TradingManagerConfig;
import com.robindrew.taskmanager.config.TradingManagerServletConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BasicAuthSecurityConfig.class, TradingManagerConfig.class, TradingManagerServletConfig.class})
public class TradingManagerService {

    public static void main(String[] args) {
        run(TradingManagerService.class, args);
    }
}
