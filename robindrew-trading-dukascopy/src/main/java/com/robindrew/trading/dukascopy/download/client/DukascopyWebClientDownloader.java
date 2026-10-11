package com.robindrew.trading.dukascopy.download.client;

import com.robindrew.spring.web.client.WebClientDownloader;
import io.netty.channel.ChannelOption;
import java.net.URI;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.Http11SslContextSpec;
import reactor.netty.http.client.HttpClient;

/**
 * Base class for downloading from the Dukascopy servers.
 * <ul>
 * <li>Requests look like they come from a browser, as the datafeed rejects requests that do not.</li>
 * <li>Failed requests are retried with an exponential backoff.</li>
 * </ul>
 */
@Slf4j
public abstract class DukascopyWebClientDownloader<R, S> extends WebClientDownloader<R, S> {

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int MAX_RESPONSE_BYTES = 32 * 1024 * 1024;
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration HANDSHAKE_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration RESPONSE_TIMEOUT = Duration.ofSeconds(60);
    private static final int MAX_ATTEMPTS = 5;

    protected DukascopyWebClientDownloader(WebClient.Builder builder) {
        super(configure(builder));
    }

    private static WebClient.Builder configure(WebClient.Builder builder) {
        // The datafeed can be very slow to complete the TLS handshake (well over the 10 second default)
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) CONNECT_TIMEOUT.toMillis())
                .secure(ssl -> ssl.sslContext(Http11SslContextSpec.forClient()).handshakeTimeout(HANDSHAKE_TIMEOUT))
                .responseTimeout(RESPONSE_TIMEOUT)
                .followRedirect(true);

        // Clone so the shared builder is not modified
        return builder.clone()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(MAX_RESPONSE_BYTES))
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .defaultHeader(HttpHeaders.ACCEPT, "*/*")
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "identity");
    }

    /**
     * @return the response body, or null if not found.
     */
    protected byte[] fetch(String url, String referer) {
        Exception lastError = null;
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                // A URI rather than a String so the URL is not treated as a template and re-encoded
                ResponseEntity<byte[]> response = getWebClient()
                        .get()
                        .uri(URI.create(url))
                        .headers(headers -> {
                            if (referer != null) {
                                headers.set(HttpHeaders.REFERER, referer);
                            }
                        })
                        .exchangeToMono(result -> result.toEntity(byte[].class))
                        .block();

                int status = response.getStatusCode().value();
                if (status == 200) {
                    return response.getBody() == null ? new byte[0] : response.getBody();
                }
                if (status == 404) {
                    return null;
                }
                lastError = new IllegalStateException("HTTP " + status);
            } catch (RuntimeException e) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new IllegalStateException("Interrupted fetching " + url, e);
                }
                lastError = e;
            }

            log.warn("Fetch failed (attempt {}/{}): {} - {}", attempt, MAX_ATTEMPTS, url, lastError.toString());
            if (attempt < MAX_ATTEMPTS) {
                sleep(1000L << (attempt - 1)); // 1s, 2s, 4s, 8s
            }
        }
        throw new IllegalStateException("Failed to fetch " + url, lastError);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
