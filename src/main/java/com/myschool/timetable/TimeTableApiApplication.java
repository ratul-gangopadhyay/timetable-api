package com.myschool.timetable;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource("classpath:application-local.properties")
@EnableSwagger2
@OpenAPIDefinition(
        info = @Info(
                title = "time-table-api",
                description = "A collection of apis for generating a non clashing Time table"
        ))
public class TimeTableApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTableApiApplication.class, args);
    }

}
