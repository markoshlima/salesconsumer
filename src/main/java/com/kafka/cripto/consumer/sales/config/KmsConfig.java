package com.kafka.cripto.consumer.sales.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KmsConfig {

	@Value("${aws.kms.arn}")
	private String kmsArn;

	@Value("${aws.auth.accessKey}")
	private String accessKey;

	@Value("${aws.auth.secretKey}")
	private String secretKey;

	private KmsMasterKeyProvider keyProvider;

	@Bean
	public KmsMasterKeyProvider keyProvider(){
		if(keyProvider == null){
			this.keyProvider = KmsMasterKeyProvider.builder()
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
					.buildStrict(kmsArn);
		}
		return this.keyProvider;
	}

}