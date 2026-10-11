package com.robindrew.trading.dukascopy.config;

import com.robindrew.trading.dukascopy.controller.DukascopyController;
import com.robindrew.trading.dukascopy.download.DukascopyDownloadService;
import com.robindrew.trading.dukascopy.download.client.DukascopySymbolDownloader;
import com.robindrew.trading.dukascopy.download.client.DukascopyTickDownloader;
import java.io.File;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DukascopyConfig {

    @Bean
    public DukascopySymbolDownloader dukascopySymbolDownloader(ObjectProvider<WebClient.Builder> builder) {
        return new DukascopySymbolDownloader(builder.getIfAvailable(WebClient::builder));
    }

    @Bean
    public DukascopyTickDownloader dukascopyTickDownloader(ObjectProvider<WebClient.Builder> builder) {
        return new DukascopyTickDownloader(builder.getIfAvailable(WebClient::builder));
    }

    @Bean(destroyMethod = "close")
    public DukascopyDownloadService dukascopyDownloadService(
            DukascopySymbolDownloader dukascopySymbolDownloader,
            DukascopyTickDownloader dukascopyTickDownloader,
            @Value("${ptf.directory}") File ptfDirectory,
            @Value("${dukascopy.download.threads:4}") int threads) {
        return new DukascopyDownloadService(dukascopySymbolDownloader, dukascopyTickDownloader, ptfDirectory, threads);
    }

    @Bean
    public DukascopyController dukascopyController(DukascopyDownloadService dukascopyDownloaderService) {
        return new DukascopyController(dukascopyDownloaderService);
    }
}
