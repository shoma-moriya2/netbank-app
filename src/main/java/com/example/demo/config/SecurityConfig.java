package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccessDeniedHandler customAccessDeniedHandler;

    SecurityConfig(AccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasRole("USER")
					.requestMatchers("/welcome").authenticated()
					.anyRequest().permitAll()
				)
			.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/welcome", true)
					.permitAll()
				)
			.rememberMe(r -> r
		            .key("jH5co3GS8boD63BC0foIC94FiMo294DUNof94Hs")       
		            .tokenValiditySeconds(60 * 60 * 24 * 30) // 30日自動ログイン
		        )
			.logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll()
					)
			.exceptionHandling(ex -> ex
					.accessDeniedHandler(customAccessDeniedHandler)
			)
			
	        .csrf(csrf -> csrf.disable()) // H2 Console 閲覧用です。
	        .headers(headers -> headers.frameOptions(frame -> frame.disable()))
	        
	        ;
			
			return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
