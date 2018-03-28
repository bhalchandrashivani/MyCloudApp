package com.csye6225.spring2018.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AwsS3Service {

    public String uploadFile(MultipartFile uploadFile, String fileName);
    public String deleteFileFromS3Bucket(String fileNameToDelete);
    public String resetPassword(String email);

}