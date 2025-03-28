package dh.backend.music_store.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud_name}")
    private String cloudName;
    @Value("${cloudinary.api_key}")
    private String apiKey;
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String > credentialsConfig = new HashMap<>();
        credentialsConfig.put("cloud_name", this.cloudName);
        credentialsConfig.put("api_key", this.apiKey);
        credentialsConfig.put("api_secret", this.apiSecret);
        return new Cloudinary(credentialsConfig);
    }
}