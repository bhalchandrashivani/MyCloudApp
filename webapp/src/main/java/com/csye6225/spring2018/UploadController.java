package com.csye6225.spring2018;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UploadController {
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/tmp/cloudpic/";
    private static String bucketName;
    private static String folderName;
    private static AmazonS3 client;
    //private static String UPLOADED_FOLDER = "../images/";




    @Value("${app.user.root}")
    private String userRoot;

    @GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        /*AWSCredentials credentials = new BasicAWSCredentials("awscli", "ipE5+vs+hmsY4VsIb73+YZJkG7xUsXE46lUPI+Wo");

        AmazonS3 awss3  =  new AmazonS3Client(credentials);
        String bucketName = "danish-"+ UUID.randomUUID();
        try{

            InputStream is = file.getInputStream();
            awss3.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

            S3Object s3Obj = awss3.getObject(new GetObjectRequest(bucketName, file.getOriginalFilename()));

            redirectAttributes.addAttribute("pickurl", s3Obj.getObjectContent().getHttpRequest().getURI().toString());
            System.out.println("pickurl>> "+s3Obj.getObjectContent().getHttpRequest().getURI().toString());
            return "redirect:/upload.html";
        }
        catch(Exception e){
            e.printStackTrace();
        }*/


        AWSCredentials credentials = new BasicAWSCredentials("awscli", "ipE5+vs+hmsY4VsIb73+YZJkG7xUsXE46lUPI+Wo");


        // create a client connection based on credentials
        AmazonS3 s3client = new AmazonS3Client(credentials);

        // create bucket - name must be unique for all S3 users
        String bucketName = "javatutorial-net-example-bucket";
        s3client.createBucket(bucketName);

        // list buckets
        for (Bucket bucket : s3client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }

        // create folder into bucket
        String folderName = "testfolder";
        createFolder(bucketName, folderName, s3client);

        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // upload file to folder and set it to public
        //String fileName = folderName + "/" + "testvideo.mp4";
        s3client.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), convFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        //deleteFolder(bucketName, folderName, s3client);

        // deletes bucket
        s3client.deleteBucket(bucketName);
        //return "redirect:/upload.html";







        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/welcome.html";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/welcome.html";
    }

    public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
        UploadController.bucketName = bucketName;
        UploadController.folderName = folderName;
        UploadController.client = client;
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folderName + "/", emptyContent, metadata);
        // send request to S3 to create folder
        client.putObject(putObjectRequest);
    }


}
