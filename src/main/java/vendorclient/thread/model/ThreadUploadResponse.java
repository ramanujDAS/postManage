package vendorclient.thread.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Introspected
public class ThreadUploadResponse {

   @JsonProperty("id")
   private String contentId;
}
