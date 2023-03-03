package site.benitohuerta.starter.service;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public String save(MultipartFile file, String path);

    public Resource load(String filename);

    public void deleteAll();

    public void delete(String filename) throws IOException;

    public Stream<Path> loadAll();

    public String generateFileName(MultipartFile file);
}