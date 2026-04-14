package vendorclient.instagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@ToString
public class InstagramRequest {
    private String caption;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("video_url")
    private String videoUrl;
}
