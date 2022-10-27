package pe.gob.regioncusco.sipac.mss3.infraestructure.s3.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.regioncusco.sipac.mss3.common.BadRequestException;
import pe.gob.regioncusco.sipac.mss3.common.ParamsManager;
import pe.gob.regioncusco.sipac.mss3.infraestructure.s3.S3Service;
import pe.gob.regioncusco.sipac.mss3.domain.model.Asset;

import java.io.IOException;

@Service
public class S3ServiceImpl implements S3Service {
    private static final Logger LOG = LoggerFactory.getLogger(S3ServiceImpl.class);
    @Autowired private AmazonS3Client amazonS3Client;

    private static final String BUCKET = ParamsManager.BUCKET_COTIZACION;

    @Override
    public boolean putObject(MultipartFile multipartFile, String key) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try {
            AccessControlList accessControlList = amazonS3Client.getBucketAcl(BUCKET);
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);

            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), objectMetadata);
            putObjectRequest.withAccessControlList(accessControlList);
            amazonS3Client.putObject(putObjectRequest);
            return true;
        } catch (IOException ex){
            LOG.error("Error al guardar el archivo en amazon s3 {}, exception {}", multipartFile.getName(), ex.getLocalizedMessage());
            throw new BadRequestException("Ocurrio un error al guardar el archivo " + multipartFile.getName());
        }
    }

    @Override
    public Asset getObject(String key) {
        S3Object s3Object = amazonS3Client.getObject(BUCKET, key);
        ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
        try {
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(s3ObjectInputStream);
            return new Asset(bytes, objectMetadata.getContentType());
        } catch (IOException e) {
            LOG.error("Error get objeto amazon s3 {}", e.getLocalizedMessage());
            throw new BadRequestException("Error al recuperar el archivo");
        }
    }

    public void deleteObject(String key){
        amazonS3Client.deleteObject(BUCKET, key);
    }

    public String getObjectUrl(String key){
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }
}
