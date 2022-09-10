package com.gsfranzoni.springawss3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 s3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Async
    public CompletableFuture<String> upload(File file) {
        String key = UUID.randomUUID().toString();
        PutObjectRequest request = new PutObjectRequest(this.bucketName, key, file);
        this.s3.putObject(request);
        return CompletableFuture.completedFuture(key);
    }

    @Async
    public CompletableFuture<byte[]> download(String key) throws IOException {
        S3Object object = this.s3.getObject(this.bucketName, key);
        S3ObjectInputStream objectInputStream = object.getObjectContent();
        return CompletableFuture.completedFuture(objectInputStream.readAllBytes());
    }
}
