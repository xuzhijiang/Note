package com.springdata.jdbc.multidatasource.service;

import com.springdata.jdbc.multidatasource.domain.Book;
import com.springdata.jdbc.multidatasource.request.BookReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

/**
 * Book service which uses the second data source.
 */
@RestController
public class BookService {

	@Autowired
	@Qualifier("secondJdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate.execute("DROP TABLE IF EXISTS book");
		jdbcTemplate.execute("CREATE TABLE book (" //
				+ "id VARCHAR(50) NOT NULL," //
				+ "name VARCHAR(50) NOT NULL," //
				+ "isbn VARCHAR(50) NOT NULL," //
				+ "createdAt BIGINT NOT NULL," //
				+ "updatedAt BIGINT NOT NULL," //
				+ "PRIMARY KEY (id))");
		for (int i = 0; i < 25; i++) {
			BookReq req = new BookReq();
			req.setName("Java-" + randomString());
			req.setIsbn("ISBN-" + randomString().hashCode() % 2017);
			createBook(req);
		}
	}

	@PostMapping("/api/books")
	public Book createBook(@RequestBody BookReq req) {
		String id = randomString();
		Long now = System.currentTimeMillis();
		jdbcTemplate.update("INSERT INTO book (id, name, isbn, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)", // SQL
				id, req.getName(), req.getIsbn(), now, now);
		return getBook(id);
	}

	@GetMapping("/api/books")
	public List<Book> getBooks() {
		return jdbcTemplate.query("SELECT * FROM book ORDER BY createdAt",
				new BeanPropertyRowMapper<>(Book.class));
	}

	@GetMapping("/api/books/{id}")
	public Book getBook(@PathVariable("id") String id) {
		return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?",
				new BeanPropertyRowMapper<>(Book.class), id);
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

}
