package evg299.sample.web.dojo.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfig {

    public static Properties slice(final Properties source, final String prefix, final boolean stripPrefix) {
        Properties result = new Properties();
        for (Map.Entry<Object, Object> entry : source.entrySet()) {
            if (entry.getKey().toString().startsWith(prefix)) {
                if (stripPrefix) {
                    result.put(entry.getKey().toString().substring(prefix.length()), entry.getValue());
                } else {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }

    public static Properties slice(final Properties source, final String prefix) {
        return slice(source, prefix, false);
    }

    @Bean
    public PropertiesFactoryBean properties() {
        PropertiesFactoryBean factory = new PropertiesFactoryBean();
        factory.setLocations(new Resource[] {
                new ClassPathResource("evg299/sample/web/dojo/db.properties"),
                new ClassPathResource("evg299/sample/web/dojo/mail.properties")
        });
        return factory;
    }
}