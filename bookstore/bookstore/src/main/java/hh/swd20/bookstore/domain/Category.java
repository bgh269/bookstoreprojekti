package hh.swd20.bookstore.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryId;
	private String name;
	
	// OneToMany yhteyden luonti Book.java luokkaan
	// cascade, jos kategoria poistetaan myös sen sisältämät kirjat poistetaan
	@JsonBackReference // tämä tarvitaan, koska Book luokassa on @JsonManagedReference-annotaatio
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category") //mapattu category on oltava täysin sama attribuutti Category luokalla Book.java
	private List<Book> books;
	
	//konstruktorit
	public Category() {
	
	}

	public Category(String name) { //hox poista tästä categoryId parametri
		super();
		this.name = name;
	}
	//Getterit ja setterit
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	//toString HOX! ilman listaa, muuten ikuinen looppi

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + "]";
	}
	

}
