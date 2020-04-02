package hh.swd20.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hh.swd20.bookstore.webcontroller.BookController;
//import hh.swd20.bookstore.webcontroller.UserDetailServiceImpl;

/**
 * Testing that the context is creating your controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class BookstoreApplicationTests {
	
	@Autowired
	private BookController bookController;
/*	@Autowired
	private UserDetailServiceImpl udsi;*/

	@Test
	public void contextLoads()throws Exception {
		assertThat(bookController).isNotNull();
	}

/*	@Test
	public void contextLoads1()throws Exception {
		assertThat(udsi).isNotNull();
	}*/
}
