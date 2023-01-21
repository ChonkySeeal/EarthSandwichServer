package com.EarthSandwich.BackBlaze.B2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class B2Config {

	@Value("${aws.accessid}")
	private String accessid;

	@Value("${aws.secret.accesskey}")
	private String secretkey;

	@Value("${aws.endpoint}")
	private String endPoint;

	@Value("${aws.region}")
	private String region;

	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessid, secretkey);
		return AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

	}

}
