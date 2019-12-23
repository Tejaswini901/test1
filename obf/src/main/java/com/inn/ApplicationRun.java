package com.inn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.inn.logFile.utils.LogFileProperties;
@SpringBootApplication
//@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.inn.*"})
@ComponentScan
@EnableJpaRepositories("com.inn.*")
@EntityScan("com.inn.*")
@EnableConfigurationProperties({
  LogFileProperties.class
})
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}







