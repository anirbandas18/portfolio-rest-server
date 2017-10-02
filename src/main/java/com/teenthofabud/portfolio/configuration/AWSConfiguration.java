package com.teenthofabud.portfolio.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.teenthofabud.portfolio.configuration.externalized.AWSCredentials;

@Configuration
public class AWSConfiguration {
	
	@Autowired
	private AWSCredentials s3Credentials;

	@Bean
	public AmazonS3 s3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(s3Credentials.getAccessKeyId(),
				s3Credentials.getSecretKey());
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withRegion(s3Credentials.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
		return s3;
	}
	
}
