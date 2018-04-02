package com.csye6225.spring2018.services;

import java.io.*;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.http.AmazonHttpClient;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;


@Service
public class AwsS3ServiceImpl implements AwsS3Service {

   // @Autowired
    //private AmazonS3 s3client;

    @Autowired
    private AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();


    @Value("${aws_namecard_bucket}")
    private String nameCardBucket;

    @Value("${endpointUrl}")
    private String endpointUrl;

    /*
     * upload file to folder and set it to public
     */
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
        private String generateFileName(MultipartFile multiPart) {
      return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public String uploadFile(MultipartFile file1, String filename) {
        String fileUrl = "";
        try {
        File file = convertMultiPartToFile(file1);
        String fileName = generateFileName(file1);
        endpointUrl="https://s3.amazonaws.com";
        fileUrl = endpointUrl + "/" + nameCardBucket + "/" + fileName;
        //String fileNameInS3 = filename;
           // s3.putObject(nameCardBucket,fileName,file);
            s3.putObject(new PutObjectRequest(nameCardBucket,
                    fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        //s3client.putObject(
        //        new PutObjectRequest(nameCardBucket,
        //                fileName, file)
        //                .withCannedAcl(CannedAccessControlList.PublicRead));

    }catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileNameToDelete) {
        //String fileUrl;
        //fileUrl = fileNameToDelete;
        String fileName = fileNameToDelete.substring(fileNameToDelete.lastIndexOf("/") + 1);
        ////////////////s3client.deleteObject(nameCardBucket,fileName);
        s3.deleteObject(nameCardBucket,fileName);
       // s3client.deleteObject(new DeleteObjectRequest(nameCardBucket+ "/", fileName));
        return "Successfully deleted";
    }

    public ResponseEntity<byte[]> downloadFile(String fileName){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            System.out.println("Downloading an object");
           ///////////////S3Object s3object = s3client.getObject(new GetObjectRequest(nameCardBucket, fileName));
            S3Object s3object = s3.getObject(new GetObjectRequest(nameCardBucket, fileName));
            //InputStream inputStream = new FileInputStream(s3object);
            InputStream inputStream =s3object.getObjectContent();

            byte[] picturebuffer = new byte[512];
            int l = inputStream.read(picturebuffer);
            while (l >= 0) {
                outputStream.write(picturebuffer, 0, l);
                l = inputStream.read(picturebuffer);
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "image/jpg");
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

}
