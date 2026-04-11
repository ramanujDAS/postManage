package vendorclient.instagram.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InstagramContentPostRequest {

    @JsonProperty("creation_id")
    private String creationId;

    private String customerNo;
}
