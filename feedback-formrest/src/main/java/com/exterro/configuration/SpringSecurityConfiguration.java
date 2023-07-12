package com.exterro.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.exterro.service.JpaUserDetailsService;

@Configuration
public class SpringSecurityConfiguration {
	 private JpaUserDetailsService myUserDetailsService;

	 public SpringSecurityConfiguration(JpaUserDetailsService myUserDetailsService) {
	        this.myUserDetailsService = myUserDetailsService;
	    }
	 
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf()
//				.disable().authorizeHttpRequests().requestMatchers("/home").permitAll().and()
//				.authorizeHttpRequests().requestMatchers("/reports/**").hasRole("ADMIN").and()
//				
//				.authorizeHttpRequests().anyRequest().authenticated().and()
//				.formLogin().and().build(); // enable form based log in
//		// set permitAll for all URLs associated with Form Login
//
//		// return http.build();
//	}

	
	
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
        		.authorizeHttpRequests().requestMatchers("/home","/user","/csharp.html","/data/api/questions/java","/data/api/questions/cSharp").permitAll().and()
                .authorizeHttpRequests().requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().userDetailsService(myUserDetailsService).formLogin().and().build();
    }
	
	
	
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers("feedbackConfirmatio.html","index.html","ChooseBatch.html","/java.html","/csharp.html");
			//	.requestMatchers("/home","/ChooseBatch.html","/java.html","/script/script.js");
	}

//	@Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails admin = User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build();
//        UserDetails user = User.withUsername("user").password(encoder.encode("user")).roles("USER").build();
//        return new InMemoryUserDetailsManager(admin,user);
//    }
	
	
	@Bean
    PasswordEncoder passwordEncoder() {
    	return new MessageDigestPasswordEncoder("SHA-256");
    }
}
