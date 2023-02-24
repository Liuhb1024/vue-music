package top.zetiny.vuemusicproject.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 解决本地跨域问题
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowCredentials(true);
    }

    /**
     * 处理地址映射问题
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //处理歌手图片的映射
        registry.addResourceHandler("/img/singer-img/**").
                addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "singer-img" + System.getProperty("file.separator"));
        //处理歌曲图片的映射
        registry.addResourceHandler("/img/song-img/**").
                addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "song-img" + System.getProperty("file.separator"));
        //处理歌单图片的映射
        registry.addResourceHandler("/img/song-list-img/**").
                addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "song-list-img" + System.getProperty("file.separator"));
        //处理歌曲的映射
        registry.addResourceHandler("/song/**").
                addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "song" + System.getProperty("file.separator"));
        //处理用户头像的映射
        registry.addResourceHandler("/img/consumer-img/**").
                addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "consumer-img" + System.getProperty("file.separator"));

    }

    /**
     * 用于JSON解析
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(sdf);
        return objectMapper;
    }
}
