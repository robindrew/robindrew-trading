package com.robindrew.taskmanager.app;

import static org.springframework.boot.SpringApplication.run;

import com.robindrew.spring.security.BasicAuthSecurityConfig;
import com.robindrew.taskmanager.config.TradingManagerConfig;
import com.robindrew.taskmanager.config.TradingManagerServletConfig;
import com.robindrew.trading.dukascopy.config.DukascopyConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    BasicAuthSecurityConfig.class,
    TradingManagerConfig.class,
    TradingManagerServletConfig.class,
    DukascopyConfig.class
})
public class TradingManagerService {

    public static void main(String[] args) {
        run(TradingManagerService.class, args);
    }
}
