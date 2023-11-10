package Exercises.E5_D10.Configurations;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinaryUploader(@Value("${CLOUDINARY_NAME}") String name,
                                         @Value("${CLOUDINARY_KEY}") String apikey,
                                         @Value("${CLOUDINARY_SECRET}") String secret
    ){

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", name);
        config.put("api_key", apikey);
        config.put("api_secret", secret);

        return new Cloudinary(config);
    }
}