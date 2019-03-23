package evg299.sample.web.dojo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@Import({HibernateConfig.class, PropertiesConfig.class})
@ComponentScan(value = {"evg299.sample.web.dojo"})
public class MainConfig
{
    @Bean
    public MultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(64 * 1024 * 1024);
        return multipartResolver;
    }
}
