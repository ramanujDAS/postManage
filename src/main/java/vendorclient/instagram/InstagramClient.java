package vendorclient.instagram;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Singleton
@Slf4j
public class InstagramClient {

    @Inject
    @Client(value = "${instagram.config.url}")
    RxHttpClient httpClient;

    private String postPath = "/post";

    public Optional<InstgramResponse> upload(InstagramRequest request , String accessToken) {
        log.info("upload request received for instgaram {}", request);
        try {

            HttpResponse<InstgramResponse> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(postPath)
                                            .build(), request
                            )
                            .contentType(MediaType.APPLICATION_JSON),
                    InstgramResponse.class
            );

            if (responseHttpResponse.getStatus().equals(HttpStatus.OK)) {
                return Optional.ofNullable(responseHttpResponse.body());
            } else {
                return Optional.empty();
            }
        } catch (HttpClientResponseException hce) {
            log.error(" upload:HttpClientResponseException while calling the instagram for customer={}", request);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" upload:Exception while calling the instagram for customer={}", request);
            throw new RuntimeException(e.getMessage());
        }

    }

    public Optional<String> getAccessToken(InstagramRequest request , String url , String tempToken){
        log.info("getAccessToken request received for instgaram {}", request);
        try {

            HttpResponse<String> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(postPath)
                                            .build(), request
                            )
                            .contentType(MediaType.APPLICATION_JSON),
                    String.class
            );

            if (responseHttpResponse.getStatus().equals(HttpStatus.OK)) {
                return Optional.ofNullable(responseHttpResponse.body());
            } else {
                return Optional.empty();
            }
        } catch (HttpClientResponseException hce) {
            log.error("getAccessToken :HttpClientResponseException while calling the instagram for customer={}", request);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" getAccessToken:Exception while calling the instagram for customer={}", request);
            throw new RuntimeException(e.getMessage());
        }

    }


   }




