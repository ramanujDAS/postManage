package filereader;

import java.io.IOException;
import java.util.List;

public interface FileReader {

    List<Post> read(String filePath) throws IOException;
}
