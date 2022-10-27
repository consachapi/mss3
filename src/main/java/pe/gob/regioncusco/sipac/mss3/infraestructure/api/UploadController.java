package pe.gob.regioncusco.sipac.mss3.infraestructure.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.application.UploadService;

@RestController
@RequestMapping(UploadController.UPLOAD_SERVICE)
public class UploadController {
    public static final String UPLOAD_SERVICE = "/upload";
    private static final String COTIZACIONES = "/cotizaciones";

    @Autowired private UploadService uploadService;

    @PostMapping(COTIZACIONES)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> upload(@RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(uploadService.uploadFile(file), HttpStatus.CREATED);
    }

}
