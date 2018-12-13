package sashaVosu.firstWebApplication.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SaveFileUtil {

    public static String saveFile(MultipartFile file, String path) throws IOException {

        File uploadDir = new File(path);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(path + "/" + resultFileName));

        return resultFileName;
    }
}
