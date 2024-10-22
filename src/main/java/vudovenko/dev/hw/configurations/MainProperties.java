package vudovenko.dev.hw.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
public class MainProperties {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
