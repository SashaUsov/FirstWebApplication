package sashaVosu.firstWebApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    public String uploadPath;

    @Value("${pic.path}")
    public String picPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**", "/pic/**")
                .addResourceLocations("file://" + uploadPath + "/")
                .addResourceLocations("file://" + picPath + "/");
    }
}
