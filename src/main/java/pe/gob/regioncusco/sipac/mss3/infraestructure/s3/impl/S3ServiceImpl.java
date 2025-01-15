package pe.gob.regioncusco.sipac.mss3.infraestructure.s3.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.common.BadRequestException;
import pe.gob.regioncusco.sipac.mss3.common.ParamsManager;
import pe.gob.regioncusco.sipac.mss3.infraestructure.s3.S3Service;
import pe.gob.regioncusco.sipac.mss3.domain.model.Asset;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectAclRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class S3ServiceImpl implements S3Service {
    private static final Logger LOG = LoggerFactory.getLogger(S3ServiceImpl.class);
    private static final String BUCKET = ParamsManager.BUCKET_TRANSPARENCIA;

    private final S3Client s3Client;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public boolean putObject(MultipartFile multipartFile, String key) {
        try {
            byte[] bytes = multipartFile.getBytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key("transparencia/documentos/Resolucion-Alcaldia/" + key)
                    .build();
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, bytes.length));
            System.out.println(putObjectResponse.eTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Asset getObject(String key) {
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request -> request.bucket(BUCKET).key("transparencia/documentos/Resolucion-Alcaldia/10086_R.A.312-2011.pdf"));
        try {
            String fileContent = StreamUtils.copyToString(response, StandardCharsets.UTF_8);
            System.out.println(fileContent);
        } catch (IOException e ) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getUrlFile(String key) {
        return getObjectUrl(key);
    }

    private void deleteObject(String key){
        s3Client.deleteObject(request -> request.bucket(BUCKET).key(key));
    }

    private String getObjectUrl(String key){
        return String.format("https://ewr1.vultrobjects.com/%s/%s", BUCKET, key);
    }
}
