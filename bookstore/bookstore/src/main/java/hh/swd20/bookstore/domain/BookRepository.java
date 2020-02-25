package hh.swd20.bookstore.domain;

import org.springframework.data.repository.CrudRepository;

//tietokantakäsittelyn rajapinta: tämä tarvitaan, jotta Entity annotaatio toimii
//CrudRepositoryn metodit periytyy BookRepositorylle

public interface BookRepository extends CrudRepository<Book, Long> {
	// CrudRepository-rajapinnan parametrisoinnissa annetaan Entity-luokan nimi: 
	// tässä Book ja toisena parametrina pääavainsarakkeen luokkatietotyyppi, tässä Long	
		
	// BookRepository periytyy(extends) CrudRepositorystä ja perii mm. metodiesittelyt
	// findAll(), findById(), save(), deleteById()
	
}
