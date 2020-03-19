package hh.swd20.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import hh.swd20.bookstore.webcontroller.UserDetailServiceImpl;

//tänne täytyy tehdä muutoksia, jotta user ei voi tehdä muutoksia (url suojaus/rajoitukset configuressa)
//muutoksia täytyy tehdä myös thymeleaf templatelle

@Configuration //kertoo spring bootille, että tämä on configurointi tiedosto
@EnableWebSecurity	// mahdollistaa Spring Security web security support:in
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailServiceImpl UserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { // configure(HttpSecurity http) metodissa määritellaan mitkä URLit on turvattu ja pääsy login formiin
		//metodien kutsuja peräkkäin (säännöstö)
		http
		.authorizeRequests()
		.antMatchers("/css/**").permitAll() //antMatchersin sisään endpointit,  kaikki mitä css tyylitiedoston alla kaikki saa käyttää
		.and()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/delete/{id}").hasRole("ADMIN") //vain ADMIN voi poistaa (vain tietty rooli voi tehdä muutoksia)
			.anyRequest().authenticated()	//jos mikään ylläolevista ei mätsää sitten suoritetaan nämä eli kaikki sisäänkirjautuneet (missä tahansa roolissa olevat) voisuorittaa  
			.and()
		.formLogin()
			//.loginPage("/login") ei tarvita koska on default login page tulee Spring securityltä
			.defaultSuccessUrl("/booklist")	//defaultSuccessUrl mihin endpointiin mennään kuin sisäänkirjautuminen onnistuu
			.permitAll()
			.and()
		.logout()
			.permitAll();
	}
	// tietokantapohjainen käyttäjä
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(UserDetailsService).passwordEncoder(new BCryptPasswordEncoder()); //encryptaa automaattisesti salasanan
	}
	
	/*
	// luo testikäyttöön pari käyttäjätunnusta
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		List<UserDetails> users = new ArrayList();	//luodaan lista, jonne testikäyttäjät tallennetaan
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		
		users.add(user);
		
		user = User.withDefaultPasswordEncoder()
				.username("admin")
				.password("password")
				.roles("ADMIN")
				.build();
		
		users.add(user);
			
		return new InMemoryUserDetailsManager(users);
	}
	*/
}



