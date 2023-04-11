package com.elisio.cursomc.service;


import com.elisio.cursomc.service.validation.exceptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    //classe para converter um multi em um buffer de imagem

    public BufferedImage getJpgImageFromFile(MultipartFile uploadFile) {
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }
        try {
            BufferedImage img = ImageIO.read(uploadFile.getInputStream());
            if ("png".equals(ext)) {
                img = pngTojpg(img);
            }

            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler o arquivo");
        }
    }

    //Metodo para converter uma img png para jpg
    public BufferedImage pngTojpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);

        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

}
