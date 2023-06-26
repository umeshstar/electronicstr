package com.bikkadit.electronicstroe.service.impl;

import com.bikkadit.electronicstroe.exception.BadApiRequest;
import com.bikkadit.electronicstroe.service.FileServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileServices {
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        log.info("upload file method start of fileServiceImpL");
        String originalFilename = file.getOriginalFilename();
        log.info("Filename:{}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path  + fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase("jpeg"))
        {
            //file Save
            File folder= new File(path);

            if(!folder.exists()){
                //create the folder
                folder.mkdirs();
            }
            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            log.info("upload file method end of fileServiceImpL");

            return fileNameWithExtension;
        }else{
            throw new BadApiRequest("File with this"+extension+"not allowd");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException
    {
        log.info("file impl- getResource method is start");
        String fullPath= path+File.separator+name;
        InputStream inputStream= new FileInputStream(fullPath);
        log.info("file impl- getResource method is end");
        return inputStream;
    }
}
