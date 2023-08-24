package com.hackathon.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GoogleCloudStorageConfig {

    private final ResourceLoader resourceLoader;

    @Bean
    public Storage storage() throws IOException {

        Resource resource = resourceLoader.getResource("classpath:bright-torch-396915-bcf8749eebab.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId("bright-torch-396915")
                .build()
                .getService();
    }
}