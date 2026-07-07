package stream.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.http.server.types.files.SystemFile;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Controller("/video")
@Secured(SecurityRule.IS_ANONYMOUS)
public class VideoStreamingController {

    @Get("/{fileName}")
    public SystemFile streamVideo(String fileName) throws URISyntaxException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        URI uri = getClass().getClassLoader().getResource(fileName).toURI();
        File file = new File(uri);
        if (stream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return new SystemFile(file, MediaType.of("video/mp4")).attach(fileName);
    }
}
