package vendorclient.instagram;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.retry.annotation.Retryable;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Singleton
@Slf4j
public class InstagramClient {

    @Inject
    @Client(value = "https://graph.instagram.com")
    RxHttpClient httpClient;

    private String postContentPath = "/me/media";
    private String getAccessTokenPath = "/oauth/authorize";
    private String publishContentPath = "/me/media_publish";

    @Retryable(attempts = "5", delay = "2s", multiplier = "2")
    public Optional<InstgramResponse> upload(InstagramRequest request , String accessToken) {
        log.info("upload request received for instgaram {}", request);
        try {

            HttpResponse<InstgramResponse> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(postContentPath)
                                            .queryParam("access_token", accessToken)
                                            .build().toString(), request
                            )
                            .contentType(MediaType.APPLICATION_JSON),
                    InstgramResponse.class
            );

            log.info("post uploaded to instagram content holder {}" , responseHttpResponse.body());

            if (responseHttpResponse.getStatus().equals(HttpStatus.OK)) {
                return Optional.ofNullable(responseHttpResponse.body());
            } else {
                return Optional.empty();
            }
        } catch (HttpClientResponseException hce) {
            log.error(" upload:HttpClientResponseException while calling the instagram for customer={}", request , hce);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" upload:Exception while calling the instagram for customer={}", request , e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Retryable(attempts = "5", delay = "2s", multiplier = "2")
    public Optional<String> getAccessToken(InstagramRequest request , String url , String tempToken){
        log.info("getAccessToken request received for instgaram {}", request);
        try {

            HttpResponse<String> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(getAccessTokenPath)
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
            log.error("getAccessToken :HttpClientResponseException while calling the instagram for customer={}", request, hce);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" getAccessToken:Exception while calling the instagram for customer={}", request, e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Retryable(attempts = "5", delay = "2s", multiplier = "2")
    public Optional<InstagramContentPostResponse> postContent(InstagramContentPostRequest request, String accessToken) {
        log.info("postContent request received for instgaram {}", request);

        try {
            HttpResponse<InstagramContentPostResponse> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(publishContentPath)
                                            .queryParam("access_token", accessToken)
                                            .build().toString(), request
                            )
                            .contentType(MediaType.APPLICATION_JSON),
                    InstagramContentPostResponse.class
            );

            log.info("content uploaded to instagram content{}", responseHttpResponse.body());
            if (responseHttpResponse.getStatus().equals(HttpStatus.OK)) {
                return Optional.ofNullable(responseHttpResponse.body());
            } else {
                return Optional.empty();
            }
        } catch (HttpClientResponseException hce) {
            log.error(" upload:HttpClientResponseException while calling the instagram for customer={}", request, hce);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" upload:Exception while calling the instagram for customer={}", request, e);
            throw new RuntimeException(e.getMessage());
        }


    }



   }
