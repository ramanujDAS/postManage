package vendorclient.thread;

import com.nimbusds.jose.util.JSONStringUtils;
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
import vendorclient.instagram.model.InstagramContentPostRequest;
import vendorclient.instagram.model.InstagramContentPostResponse;
import vendorclient.instagram.model.InstagramRequest;
import vendorclient.instagram.model.InstgramResponse;
import vendorclient.thread.model.ThreadPublishRequest;
import vendorclient.thread.model.ThreadPublishResponse;
import vendorclient.thread.model.ThreadUploadReqest;
import vendorclient.thread.model.ThreadUploadResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@Singleton
@Slf4j
public class ThreadClient {

    @Inject
    @Client(value = "https://graph.thread.net")
    RxHttpClient httpClient;

    private final String postContentPath = "/v1.0/me/threads";
    private final String getAccessTokenPath = "/oauth/authorize";
    private final String publishContentPath = "/v1.0/me/threads_publish";

    @Retryable(attempts = "5", delay = "2s", multiplier = "2")
    public Optional<ThreadUploadResponse> upload(ThreadUploadReqest request , String accessToken) {

        request.setAccessToken(accessToken);
        log.info("upload request received for instagram {}", request);
        try {

            HttpResponse<ThreadUploadResponse> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(postContentPath)
                                            .build(),getFormData(request)
                            )
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                            .accept(MediaType.ALL_TYPE),
                    ThreadUploadResponse.class
            );

            log.info("post uploaded to thread content holder {} {}" , responseHttpResponse.getBody().get(),responseHttpResponse);


            return Optional.of(responseHttpResponse.getBody().get());

        } catch (HttpClientResponseException hce) {
            String errorMessage = hce.getResponse().getBody(String.class).orElse("No body");
            log.error(" upload:HttpClientResponseException while calling the thread for customer={} error ={}", request,errorMessage , hce);

            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" upload:Exception while calling the thread for customer={}", request , e);
            throw new RuntimeException(e.getMessage());
        }


    }

    @Retryable(attempts = "5", delay = "2s", multiplier = "2")
    public Optional<String> getAccessToken(InstagramRequest request , String url , String tempToken){
        log.info("getAccessToken request received for thread {}", request);
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
            String errorMessage = hce.getResponse().getBody(String.class).orElse("No body");
            log.error(" upload:HttpClientResponseException while calling the thread for customer={} error ={}", request,errorMessage , hce);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {
            log.error(" getAccessToken:Exception while calling the thread for customer={}", request, e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Retryable(attempts = "5", delay = "2s", multiplier = "2")
    public Optional<ThreadPublishResponse> postContent(ThreadPublishRequest request, String accessToken) {
        log.info("postContent request received for instagram {}", request);
        request.setAccessToken(accessToken);

        try {
            HttpResponse<ThreadPublishResponse> responseHttpResponse = httpClient.toBlocking().exchange(
                    HttpRequest.POST(
                                    UriBuilder
                                            .of(publishContentPath)
                                            .build().toString(), request
                            )
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                    ThreadPublishResponse.class
            );

            log.info("content uploaded to instagram content{}", responseHttpResponse.body());
            if (responseHttpResponse.getStatus().equals(HttpStatus.OK)) {
                return Optional.ofNullable(responseHttpResponse.body());
            } else {
                return Optional.empty();
            }
        } catch (HttpClientResponseException hce) {
            String errorMessage = hce.getResponse().getBody(String.class).orElse("No body");
            log.error(" upload:HttpClientResponseException while calling the thread for customer={} error ={}", request,errorMessage , hce);
            throw new RuntimeException(hce.getMessage());
        } catch (Exception e) {

            log.error(" upload:Exception while calling the thread for customer={}", request, e);
            throw new RuntimeException(e.getMessage());
        }


    }


    private Map<String , String> getFormData(ThreadUploadReqest request){

        HashMap<String , String> formData = new LinkedHashMap<>();
        formData.put("media_type" , request.getMediaType());
        formData.put("image_url" , request.getImageUrl());
        formData.put("text" , request.getCaptionText());
        formData.put("access_token" , request.getAccessToken());
        return formData;
    }



   }
