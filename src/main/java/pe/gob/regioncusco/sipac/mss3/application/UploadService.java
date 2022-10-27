package pe.gob.regioncusco.sipac.mss3.application;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Boolean uploadFile(MultipartFile file);
}
