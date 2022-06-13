package br.com.testebackend.miniautorizador;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	private static final int BROWSER_CACHE_CONTROL = 604800;
	
	/*
	@Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  registry
	   .addResourceHandler("/images/**")
	   .addResourceLocations("/images/")
	   .setCachePeriod(BROWSER_CACHE_CONTROL);
	 }
	 */
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");

	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	
}
