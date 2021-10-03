package priv.xds.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import java.io.IOException;

/**
 * 用于读取yaml配置
 * @author DeSen Xu
 * @date 2021-09-28 21:21
 */
public class YamlSourceFactory implements PropertySourceFactory {

    @NotNull
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        return loader.load(resource.getResource().getFilename(), new FileUrlResource(resource.getResource().getURL())).get(0);
    }
}
