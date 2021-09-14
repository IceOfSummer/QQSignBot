package priv.xds.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author "DeSen Xu"
 * @date 2021-09-14 22:04
 */
@Configuration
@EnableConfigurationProperties(ProjectProperties.class)
@Slf4j
public class ProjectConfiguration {

    private ProjectProperties projectProperties;

    @Autowired
    public void setProjectProperties(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Bean
    public String version() {
        log.info("当前项目版本: " + projectProperties.getVersion());
        return projectProperties.getVersion();
    }
}
