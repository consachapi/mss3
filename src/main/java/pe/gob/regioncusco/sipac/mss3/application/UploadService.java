package pe.gob.regioncusco.sipac.mss3.application;

import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.domain.dto.ResponseUplaod;

public interface UploadService {
    ResponseUplaod uploadFile(MultipartFile file);
}
