package hh.swd20.bookstore;

import static org.assertj.core.api.Assertions.assertThat; // tämä täytyi lisätä tänne kopioimalla demosta, eclipse ei löytänyt

import java.util.List;


import org.junit.jupiter.api.Test;
//import org.junit.Test; ei löydä tällä
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import hh.swd20.bookstore.domain.Book;
import hh.swd20.bookstore.domain.BookRepository;
import hh.swd20.bookstore.domain.Category;
import hh.swd20.bookstore.domain.CategoryRepository;
import hh.swd20.bookstore.domain.User;
import hh.swd20.bookstore.domain.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookstoreRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CategoryRepository crepository;
	@Autowired
	private UserRepository urepository;
	
	@Test
	public void findByAuthorShouldReturnBook() {
		List<Book> books = bookRepository.findByAuthor("Veikko Huovinen");
		assertThat(books).hasSize(1);
		assertThat(books.get(0).getTitle()).isEqualTo("Havukka-ahon ajattelija");
	}
		
	
	@Test
	public void createNewBook() {
		Book book = new Book("Testi kirja", "Testi Kirjailija", 2020, "999-666", 20.95, null);
		bookRepository.save(book);
		assertThat(book.getId()).isNotNull();
	}
	
	@Test
	public void deleteByIdNewBook() {
		Book book = new Book("Testi2 kirja", "Testi2 Kirjailija", 2019, "666-666", 20.50, null);
		bookRepository.save(book);
		bookRepository.deleteById(book.getId());
		
	}
	
	@Test
	public void findByNameShouldReturnCategory() {
		List<Category> category = crepository.findByName("Humor"); //jos kirjoittaa pienellä ei löydä!
		assertThat(category).hasSize(1);
		assertThat(category.get(0).getName()).isEqualTo("Humor");
	} 
	
	@Test
	public void createNewCategory() {
		Category category = new Category("science");
		crepository.save(category);
		assertThat(category.getCategoryId()).isNotNull();
	}
	
	@Test
	public void deleteCategory() {
		Category category = new Category("romance");
		crepository.save(category);
		crepository.delete(category);
	}
	
	@Test
	public void findByUsernameShouldReturnUser() {
		User user = urepository.findByUsername("user");
		assertThat(user.getUsername()).isNotNull();
		assertThat(user.getRole()).isEqualTo("USER");
	}
	
	@Test
	public void createNewUser() {
		User user = new User("Matti","matti@email.com", "$2a$10$9vVnizvwSquGWrG7G9Tj7.R36UCSOn7R9cJFvcdL0SpoPk/iQX6mm", "USER");
		urepository.save(user);
		assertThat(user.getId()).isNotNull();
	}
	
	@Test
	public void deleteAllUsers() {
		User user = new User("Maija", "maija@email.com", "$2a$10$9vVnizvwSquGWrG7G9Tj7.R36UCSOn7R9cJFvcdL0SpoPk/iQX6mm", "USER");
		urepository.save(user);
		urepository.deleteAll();
		assertThat(urepository.count()).isEqualTo(0);
	}
}
