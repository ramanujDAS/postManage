package vendorclient.instagram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class InstagramContentPostResponse {

    @JsonProperty("id")
    private String id ;
}
