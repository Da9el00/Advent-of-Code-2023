package adventofcode.utill;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class InputReader {
    public static List<String> readInputByLine(String inputLocation, Class<?> callingClass) {
        URL resource = callingClass.getClassLoader().getResource(inputLocation);

        URI uri = null;
        try {
            assert resource != null;
            uri = resource.toURI();

            Path path = Path.of(uri);
            return Files.lines(path)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
