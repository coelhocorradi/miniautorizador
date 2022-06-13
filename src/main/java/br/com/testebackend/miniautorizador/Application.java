package br.com.testebackend.miniautorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
        System.out.println( "Iniciado!" );
    }
    
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	return builder.sources(Application.class);
    }
    
    
}
