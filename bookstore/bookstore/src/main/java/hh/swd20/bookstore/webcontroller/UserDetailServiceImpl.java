package hh.swd20.bookstore.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hh.swd20.bookstore.domain.User;
import hh.swd20.bookstore.domain.UserRepository;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	private final UserRepository urepository;
	
	@Autowired //autowired luokan construktorin avulla
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.urepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User curruser = urepository.findByUsername(username);
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), // HOX! eri User (springbootin) kuin oma domain luokassa luotu User
				AuthorityUtils.createAuthorityList(curruser.getRole())); //roolilista
		return user;
	}
}
