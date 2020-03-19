package hh.swd20.bookstore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//IDENTITY on taulukohtainen arvo
	@Column(name="id", nullable=false, updatable=false)
	private Long id;
	
	 // Username with unique constraint
	@Column(name="username", nullable=false, unique=true)	//tietokantaa varten oltava uniikki käyttäjätunnus
	private String username;

	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="password", nullable=false)	//kryptattu salasana, Hash springboot alustalta
	private String passwordHash;
	
	@Column(name="role", nullable=false)
	private String role;

	public User() {
		super();
	}

	public User(Long id, String username, String email, String passwordHash, String role) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
	}
	public User(String username, String email, String passwordHash, String role) {
		super();
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", passwordHash=" + passwordHash
				+ ", role=" + role + "]";
	}

	

	
	
	
}
