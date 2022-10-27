package pe.gob.regioncusco.sipac.mss3.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.domain.dto.ResponseUplaod;
import pe.gob.regioncusco.sipac.mss3.infraestructure.s3.S3Service;

@Service
public class UploadServiceImpl implements UploadService{
    @Autowired
    private S3Service s3Service;

    @Override
    public ResponseUplaod uploadFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = StringUtils.getFilename(file.getOriginalFilename());
        String key = String.format("%s.%s", fileName, extension);
        return new ResponseUplaod(saveRemote(file, fileName), s3Service.getUrlFile(fileName));
    }

    private boolean saveRemote(MultipartFile multipartFile, String key) {
        return s3Service.putObject(multipartFile, key);
    }
}
