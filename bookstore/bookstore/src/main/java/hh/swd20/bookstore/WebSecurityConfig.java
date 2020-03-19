package hh.swd20.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//tänne täytyy tehdä muutoksia, jotta user ei voi tehdä muutoksia (url suojaus/rajoitukset configuressa)
//muutoksia täytyy tehdä myös thymeleaf templatelle

@Configuration //kertoo spring bootille, että tämä on configurointi tiedosto
@EnableWebSecurity	// mahdollistaa Spring Security web security support:in
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
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
			.loginPage("/login")
			.defaultSuccessUrl("/booklist")	//defaultSuccessUrl mihin endpointiin mennään kuin sisäänkirjautuminen onnistuu
			.permitAll()
			.and()
		.logout()
			.permitAll();
	}
	
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
}



