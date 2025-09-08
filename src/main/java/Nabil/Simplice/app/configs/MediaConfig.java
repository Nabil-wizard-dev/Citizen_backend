package Nabil.Simplice.app.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MediaConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path mediaDir = Paths.get("Media");
        String mediaPath = mediaDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/Media/**")  // l'URL accessible
                .addResourceLocations("file:" + mediaPath + "/");
    }
}
