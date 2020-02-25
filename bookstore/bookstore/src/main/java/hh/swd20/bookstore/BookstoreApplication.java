package hh.swd20.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.swd20.bookstore.domain.Book;
import hh.swd20.bookstore.domain.BookRepository;

@SpringBootApplication //tämä annotaatio mahdollistaa automaattisen määrityksen
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);  // uusi loggeriattribuutti
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

//  testidatan luonti H2-testitietokantaan aina sovelluksen käynnistyessä
	@Bean
	public CommandLineRunner bookDemo(BookRepository bookRepository) {
		return (args) -> {
			log.info("save a couple of books");
			//title, author, year, isbn, price
			bookRepository.save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 15.00));
			bookRepository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 10.00));
			bookRepository.save(new Book("Havukka-ahon ajattelija", "Veikko Huovinen", 1952, "9876543-21", 30.00));
			bookRepository.save(new Book("Jäniksen vuosi", "Arto Paasilinna", 1975, "951-35-1252-5", 35.00));
			log.info("fetch all books");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}

		};
	}
}
