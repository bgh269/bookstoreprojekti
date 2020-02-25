package hh.swd20.bookstore.webcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.swd20.bookstore.domain.Book;
import hh.swd20.bookstore.domain.BookRepository;

@Controller
public class BookController {
	 // Spring-alusta luo sovelluksen käynnistyessä BookRepository-rajapintaa toteuttavan luokan olion 
	 // ja kytkee olion BookController-luokasta luodun olion attribuuttiolioksi

	@Autowired //automaattisesti ulkopuolelta kytkee tietokannan käsittelyolion
	BookRepository bookRepository;  //tässä kerrotaan rajapinta
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String getBook(Model model) {
		return "index";	
	}
	
	
	// kirjalistaus
	@RequestMapping(value="/booklist", method=RequestMethod.GET)
	public String getAllBooks(Model model) {
		// haetaan tietokannasta kirjat listaan
		List<Book> books = (List<Book>)bookRepository.findAll();
		// laitetaan model-mapin autolista templatea varten
		model.addAttribute("books", books); // findAll:in,"books" on model-mapin keyword, books on java-kielen muuttuja
		// palautetaan sopivan käyttöliittymätemplaten nimi
		return "booklist"; //booklist.html
	}
	
	// tyhjän kirjalomakkeen muodostaminen
	@RequestMapping(value="/newbook", method=RequestMethod.GET)
	public String getNewBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "bookform"; //bookform.html
	}
	
	// kirjalomakkeella syötettyjen tietojen vastaanotto ja tallennus
	@RequestMapping(value="/newbook", method=RequestMethod.POST)
	public String saveBook(@ModelAttribute Book book) {
		// talletetaan yhden kirjan tiedot tietokantaan
		bookRepository.save(book); // save osaa tehdä tarpeen mukaan SQL INSERTIN tai UPDATEN
		return "redirect:/booklist"; //  /booklist-endpointin kutsu, uudelleen ohjaa talletetut tiedot booklist sivulle
	}
	
	
}
