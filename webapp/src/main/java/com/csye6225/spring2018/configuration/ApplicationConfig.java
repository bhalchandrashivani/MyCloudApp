package com.csye6225.spring2018.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.csye6225.spring2018.SpringBootWebApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;


@Configuration
@ComponentScan(basePackageClasses = SpringBootWebApplication.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
public class ApplicationConfig {

   // @Value("${aws_access_key_id}")
   // private String awsId;

   // @Value("${aws_secret_access_key}")
   // private String awsKey = "";

//    @Bean
//    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
//        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
//        ppc.setLocations(new Resource[] {
//                new ClassPathResource("/application-aws.properties")
//        });
//        return ppc;
//    }

//    @Bean
//    public AWSCredentials credential() {
//        return new BasicAWSCredentials(awsId, awsKey);
//    }
//    @Bean
//    AWSCredentialsProvider credProvider() {
//        return new InstanceProfileCredentialsProvider();
//    }

   // @Bean
    //public AmazonS3 s3client() {

    //    return new AmazonS3Client(credential());
    //}
//    @Bean
//    public AmazonS3 s3() {
//
//        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
//                .withCredentials(new InstanceProfileCredentialsProvider(true))
//                .build();
//        return s3;
//    }
    @Bean
    public AmazonS3 s3() {
        InstanceProfileCredentialsProvider provider
                = new InstanceProfileCredentialsProvider(true);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(provider)
                .build();
    }
//    @Bean
//    public AmazonSNS amazonSNSClient(){
//        InstanceProfileCredentialsProvider provider = new InstanceProfileCredentialsProvider(true);
//        // AmazonSNSClientBuilder snsClient = new AmazonSNSClientBuilder(new InstanceProfileCredentialsProvider(true));
//        return AmazonSNSClientBuilder.standard().withCredentials(provider).withRegion(Regions.US_EAST_1).build();
//    }
}