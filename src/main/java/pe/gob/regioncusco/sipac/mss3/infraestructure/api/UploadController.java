package pe.gob.regioncusco.sipac.mss3.infraestructure.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.application.UploadService;
import pe.gob.regioncusco.sipac.mss3.domain.dto.ResponseUplaod;
import pe.gob.regioncusco.sipac.mss3.domain.model.Asset;
import pe.gob.regioncusco.sipac.mss3.infraestructure.s3.S3Service;

@RestController
@RequestMapping(UploadController.UPLOAD_SERVICE)
public class UploadController {
    public static final String UPLOAD_SERVICE = "/upload";
    private static final String COTIZACIONES = "/cotizaciones";

    @Autowired private UploadService uploadService;
    @Autowired private S3Service s3Service;

    @PostMapping(COTIZACIONES)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseUplaod> upload(@RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(uploadService.uploadFile(file), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Asset> read(){
        return new ResponseEntity<>(s3Service.getObject("bucket"), HttpStatus.OK);
    }

}
