package vendorclient.instagram.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class InstgramResponse {

    @JsonProperty("id")
    private String contentId;
}
