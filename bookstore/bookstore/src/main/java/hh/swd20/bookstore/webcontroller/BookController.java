package hh.swd20.bookstore.webcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.bookstore.domain.Book;
import hh.swd20.bookstore.domain.BookRepository;
import hh.swd20.bookstore.domain.Category;
import hh.swd20.bookstore.domain.CategoryRepository;

@Controller
public class BookController {
	 // Spring-alusta luo sovelluksen käynnistyessä BookRepository-rajapintaa toteuttavan luokan olion 
	 // ja kytkee olion BookController-luokasta luodun olion attribuuttiolioksi

	@Autowired //automaattisesti ulkopuolelta kytkee tietokannan käsittelyolion
	BookRepository bookRepository;  //tässä kerrotaan rajapinta
	
	@Autowired //liittyy lähimpään yhteen rakenteeseen
	private CategoryRepository crepository;
	
	// login sivu
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String getBook(Model model) {
		return "index";	
	}
	
	// kirjalistaus
	@RequestMapping(value="/booklist", method=RequestMethod.GET)
	public String getAllBooks(Model model) {
		// haetaan tietokannasta kirjat listaan
		List<Book> books = (List<Book>)bookRepository.findAll();
		// laitetaan model-mappiin kirjalista templatea varten
		model.addAttribute("books", books); // findAll:in,"books" on model-mapin keyword, books on java-kielen muuttuja
		// palautetaan sopivan käyttöliittymätemplaten nimi
		return "booklist"; //booklist.html
	}
	
	// TÄSSÄ REST-METODI
	// RESTful service to get all books
	@RequestMapping(value="/books", method=RequestMethod.GET) //(jacksonkirjasto)@ResponseBody muuttaa (java)List<Student> (JSON)muotoon
	public @ResponseBody List<Book> bookListRest() {	//pyydetään tietokannasta opiskelijalista ja suoraan palautetaan(return)
		return (List<Book>) bookRepository.findAll();
	}
	
	// TÄSSÄ REST-METODI
	// RESTful service to get book by id
	// Optional is a container object used to contain not-null objects. Optional object is used to represent null with absent value.
	@RequestMapping(value="/books/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId){
		return bookRepository.findById(bookId);
	}
	
	// on olemassa autogeneroituja rest-metodeja => lisää pom:iin dependencyyn 
	//<dependency>
	//<groupId>org.springframework.boot</groupId>
	//<artifactId>spring-boot-starter-data-rest</artifactId>
	//</dependency>
	// ja application.propertiesiin(spring.data.rest.basePath=/api)
    // REST-METODI POST LÄHETTÄÄ TIETOA
	
	
	// categorialistaus
	@RequestMapping(value="/categorylist", method=RequestMethod.GET)
	public String getAllCategories(Model model) {
		// haetataan tietokannasta categoriat listaan
		List<Category> categories = (List<Category>) crepository.findAll();
		// laitetaan model-mappiin  categorialista templatea varten
		model.addAttribute("categories", categories);
		// palautetaan sopivan käyttöliittymä templaten nimi
		return "categorylist";
	}
	
	// kirjan lisäys
	// tyhjän kirjalomakkeen muodostaminen
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("categories", crepository.findAll());
		return "addbook"; //bookform.html
	}
	
	// kirjalomakkeella syötettyjen tietojen vastaanotto ja tallennus
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@ModelAttribute Book book) {
		// talletetaan yhden kirjan tiedot tietokantaan
		bookRepository.save(book); // save osaa tehdä tarpeen mukaan SQL INSERTIN tai UPDATEN
		return "redirect:/booklist"; //  /booklist-endpointin kutsu, uudelleen ohjaa talletetut tiedot booklist sivulle
	}
	
	// yhden kirjan poisto id:n perusteella
	// jos tallennettavan kirjan id täsmää jo taulussa olevan kirjan id:hen
	// =>tallennetaan kirja silloin sen id:n kohdalle
	// deleteBook metodi kuuntelee /delete/bookId endpointia
	// eli http://localhost:8080/delete/5, niin @PathVariable bookId on 5
	// metodin turvaamiseksi @PreAuthorize
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteBook(@PathVariable("id")Long bookId, Model model) {
		bookRepository.deleteById(bookId);
		return "redirect:../booklist";
	}
	
	// yhden kirjan editointi id:n perusteella
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editBook(@PathVariable("id")Long bookId, Model model) {
		model.addAttribute("book", bookRepository.findById(bookId));
		model.addAttribute("categories", crepository.findAll());
		return "editbook"; 
	}
}
