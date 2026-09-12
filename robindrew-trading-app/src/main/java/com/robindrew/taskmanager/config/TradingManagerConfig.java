package com.robindrew.taskmanager.config;

import com.robindrew.taskmanager.controller.ChartController;
import com.robindrew.taskmanager.controller.HomeController;
import com.robindrew.taskmanager.controller.PcfController;
import com.robindrew.taskmanager.controller.PtfController;
import com.robindrew.taskmanager.controller.RestExceptionHandler;
import com.robindrew.trading.price.candle.format.pcf.source.file.ReloadablePcfFileManager;
import com.robindrew.trading.price.candle.format.ptf.source.file.IPtfFileManager;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileManager;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradingManagerConfig {

    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }

    @Bean
    public HomeController homeController() {
        return new HomeController();
    }

    @Bean
    public IPtfFileManager ptfFileManager(@Value("${ptf.directory}") File directory) {
        return new PtfFileManager(directory);
    }

    @Bean
    public ReloadablePcfFileManager pcfFileManager(@Value("${pcf.directory}") File directory) {
        return new ReloadablePcfFileManager(directory);
    }

    @Bean
    public PtfController ptfController(IPtfFileManager ptfFileManager, ReloadablePcfFileManager pcfFileManager) {
        return new PtfController(ptfFileManager, pcfFileManager);
    }

    @Bean
    public PcfController pcfController(ReloadablePcfFileManager pcfFileManager) {
        return new PcfController(pcfFileManager);
    }

    @Bean
    public ChartController chartController(ReloadablePcfFileManager pcfFileManager) {
        return new ChartController(pcfFileManager);
    }
}
