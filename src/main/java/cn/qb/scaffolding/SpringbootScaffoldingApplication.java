package cn.qb.scaffolding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.qb.scaffolding.mapper")
public class SpringbootScaffoldingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootScaffoldingApplication.class, args);
    }

}
