package com.elisio.cursomc.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {
    //Não estou usando este service não criei uma conta na amazon
    //Esta iniciando a aplicação no main

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    //Vai pegar arquivos locais de imagem para subir no s3
//    public void uploadFile(String localFilePath) {
    //Essa versão é para subir de um endPoint
    public URI uploadFile(MultipartFile multipartFile) {
        //Estrai o nome do arquivo que foi enviado
        try {
            String filename = multipartFile.getOriginalFilename();
            InputStream inputStream = null;
            inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, filename, contentType);
        } catch (IOException e) {
            throw new RuntimeException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, inputStream, metadata);
            LOG.info("Upload Finalizado");
            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao converter URL para URI");
        }
    }
}
