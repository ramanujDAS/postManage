package vendorclient.instagram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@ToString
public class InstagramRequest {
    private String caption;
    @JsonProperty("image_url")
    private String imageUrl;
    private String videoUrl;
}
