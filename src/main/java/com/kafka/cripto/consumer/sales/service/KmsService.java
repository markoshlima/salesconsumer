package com.kafka.cripto.consumer.sales.service;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class KmsService {

    @Autowired
    private KmsMasterKeyProvider keyProvider;

    /**
     * Decript with KMS master key ARN
     * @param data64
     * @return
     */
    public String decryptData(final String data64) {

        AwsCrypto crypto = AwsCrypto.builder()
                .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
                .build();

        byte[] data = Base64.getDecoder().decode(data64.getBytes(StandardCharsets.UTF_8));

        CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(keyProvider, data);

        return new String(decryptResult.getResult());

    }

}