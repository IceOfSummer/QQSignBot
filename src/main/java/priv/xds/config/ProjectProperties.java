package priv.xds.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author "DeSen Xu"
 * @date 2021-09-14 22:04
 */
@ConfigurationProperties(prefix = "project")
@Data
public class ProjectProperties {

    /**
     * 项目版本
     */
    private String version;

}
