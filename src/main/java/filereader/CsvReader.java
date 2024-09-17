package filereader;


import Uploader.Post;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class CsvReader implements FileReader {

    private final String PROCESSED = "processed";

    @Override
    public List<Post> read(String filePath) throws IOException {

        Path tempFile = Files.createTempFile(null,null);

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                if (values.length > 0 && values[0].equalsIgnoreCase(PROCESSED)) continue;
                records.add(Arrays.asList(values));

                values[0] = PROCESSED;
                bw.write(String.join(",", values));
                bw.newLine();

            }
         Files.move(tempFile, Paths.get(filePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Post> posts = new ArrayList<>();

        for (List<String> record : records) {
            Post post = new Post();
            post.setFilePath(record.get(1));
            post.setFileType(record.get(2));
            post.setTimeWhenToUpload(Long.parseLong(record.get(3)));
            post.setCaption(record.get(4));
            post.setHashTags(Arrays.asList(record.get(5).split(",")));
            post.setLocation(record.get(6));
            posts.add(post);
        }

        return posts;
    }


}
