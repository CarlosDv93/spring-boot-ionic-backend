package com.carlosdv93.udemy.cursomc.services;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Services {

	private Logger log = org.slf4j.LoggerFactory.getLogger(S3Services.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public void uploadFile(String localFilePath) {
		
		try {
			File file = new File(localFilePath);
			log.info("Iniciando Upload........");
			s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			log.info("Upload Finalizado!!!");
		} catch (AmazonServiceException e) {
			log.info(" AmazonServiceException: " + e.getErrorMessage());
			log.info("Status Code: " + e.getErrorCode());
		} catch (AmazonClientException aCE) {
			log.info("AmazonClientException" + aCE.getMessage());
		}
		
	}
	
}
