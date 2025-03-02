package top.zetiny.vuemusicproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "top.zetiny.vuemusicproject.dao")
public class                                                                                                                       VueMusicProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueMusicProjectApplication.class, args);
    }

}
