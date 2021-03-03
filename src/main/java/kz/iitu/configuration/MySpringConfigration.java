package kz.iitu.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("kz.iitu.configuration")
@PropertySource("/application.properties")
public class MySpringConfigration {

}
