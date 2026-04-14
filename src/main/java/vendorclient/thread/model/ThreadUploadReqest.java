package vendorclient.thread.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@Introspected
@ToString
public class ThreadUploadReqest {

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("text")
    private String captionText;

    @JsonProperty("access_token")
    private String accessToken;




}
