package org.me.community.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
@Configuration
public class CustomCorsConfiguration {

	@Value("${corsOrigin}")
	private String address;

	@Bean
	public CorsFilter corsFilter(){

		CorsConfiguration configuration = new CorsConfiguration();

		// 设置允许跨域的域名
		configuration.addAllowedOriginPattern(address);
		configuration.setAllowCredentials(true);
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
		configurationSource.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(configurationSource);
	}


}
