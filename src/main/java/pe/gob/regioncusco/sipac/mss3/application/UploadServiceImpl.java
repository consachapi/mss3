package pe.gob.regioncusco.sipac.mss3.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.infraestructure.s3.S3Service;

@Service
public class UploadServiceImpl implements UploadService{
    @Autowired
    private S3Service s3Service;

    @Override
    public Boolean uploadFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = String.format("%s.%s", file.getOriginalFilename(), extension);
        return saveRemote(file, key);
    }

    private boolean saveRemote(MultipartFile multipartFile, String key) {
        return s3Service.putObject(multipartFile, key);
    }
}
