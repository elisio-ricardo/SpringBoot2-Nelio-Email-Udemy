package com.elisio.cursomc.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
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
    public void uploadFile(String localFilePath) {
        //Essa versão é para subir de um endPoint
        //public URI uploadFile(MultipartFile multipartFile) {
        try {


            File file = new File(localFilePath);
            LOG.info("Iniciando upload");
            s3client.putObject(new PutObjectRequest(bucketName, "teste", file));
            LOG.info("Upload Finalizado");
        } catch (AmazonServiceException e) {
            LOG.info("AmazonServiceException: " + e.getErrorMessage());
            LOG.info("Status code: " + e.getErrorCode());
        } catch (AmazonClientException | IOException e) {
            LOG.info("AmazonClientException: " + e.getMessage());
        }

        //return null;
    }

//    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
//    }
