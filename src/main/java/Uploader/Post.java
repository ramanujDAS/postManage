package Uploader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class Post {

    @NotNull
    private String filePath;
    private String fileType;
    private Long timeWhenToUpload;
    private String caption;
    private List<String> hashTags;
    private String location;

}
