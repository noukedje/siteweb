package com.gestion.commerce.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userdetails;
	
	//@Autowired
	//private DataSource dataSource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**"); 
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
	    http.authorizeRequests()
	    .antMatchers("/", "/home/**","/login","/cart/**","/services/commandes","/produits/**","/categories/**").permitAll()
	  	.antMatchers("/users/**").hasAuthority("ADMIN")  //seuls hasAuthority et hasAnyAuthority fonctionnent, peut-être parce que dans
	  	.antMatchers("/admin/**").hasAnyAuthority("USER", "ADMIN") //UserDetailsServiceImpl, on utilise le rôle pour définir l'autorité   
        .and().exceptionHandling().accessDeniedPage("/403")
	        .and().formLogin()
	        .loginProcessingUrl("/users/auth") // Submit URL is by default /perform_login
	        .loginPage("/login")//
	        .defaultSuccessUrl("/admin")//
	        .failureUrl("/login?error")//
	        .usernameParameter("username")//
	        .passwordParameter("password")
	       // .and()
	     //   .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
            .and().csrf().disable();
    
		//http.requiresChannel().anyRequest().requiresSecure(); //force une connexion sécurisée HTTPS
		/*If you’re using a JavaScript framework like Angular or React, you will need to configure the CookieCsrfTokenRepository 
		  so JavaScript can read the cookie.	
		http
			.csrf()
	        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); ou http.csrf().disable();*/
		
	}
	/*public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {
		auth
		  .jdbcAuthentication().dataSource(dataSource)   //.inMemoryAuthentication()  //les infos de connexion étaient stockées dans la mémoire et non dans la BD
	      .withDefaultSchema()  //Avec ça, les infos sont liées à la BD
          .withUser("user").password(passwordEncoder().encode("Bangoua1980")).roles("USER")
          .and()
          .withUser("admin").password(passwordEncoder().encode("Bangoua1980")).roles("USER", "ADMIN");
    }*/
	
    
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

      // Setting Service to find User in the database.
      // And Setting PassswordEncoder
	  /* auth
		  .inMemoryAuthentication()  //pour que ce soit lié uniquement à ces deux utilisateurs 'user' et 'admin'
	      .withUser("user").password(passwordEncoder().encode("Douala2022")).authorities("USER")  
	      .and()
	      .withUser("admin").password(passwordEncoder().encode("Douala2022")).authorities("USER", "ADMIN");*/  
	   
	  //pour que ce soit lié à n'importe quel utilisateur 
	   auth.userDetailsService(userdetails).passwordEncoder(passwordEncoder());
      
   }
   
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
   
}
