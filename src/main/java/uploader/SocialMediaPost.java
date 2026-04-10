package uploader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SocialMediaPost {
    private String customerNo;
    private String content;
    private String imageUrl;
    private String videoUrl;
    private List<String> hashTags;
}
