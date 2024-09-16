package Uploader;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UploadAttributes {

    @NotNull
    private String filePath;
    private String fileType;
    private Long timeWhenToUpload;
    private String caption;
    private List<String> hashTags;
    private String location;
    @NotNull
    private Credentials credentials;

}
