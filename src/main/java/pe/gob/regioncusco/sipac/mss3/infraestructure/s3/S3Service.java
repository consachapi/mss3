package pe.gob.regioncusco.sipac.mss3.infraestructure.s3;

import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.domain.model.Asset;

public interface S3Service {
    boolean putObject(MultipartFile file, String key);
    Asset getObject(String key);
    String getUrlFile(String key);
}
