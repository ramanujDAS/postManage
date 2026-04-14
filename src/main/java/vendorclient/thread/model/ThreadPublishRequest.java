package vendorclient.thread.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Introspected
public class ThreadPublishRequest {

    @JsonProperty("content_id")
    private String contentId;
    @JsonProperty("access_token")
    private String accessToken;

}
