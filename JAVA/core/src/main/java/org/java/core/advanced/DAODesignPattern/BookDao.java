package org.java.core.advanced.DAODesignPattern;

import java.util.List;

public interface BookDao {
	List<Book> getAllBooks();
    Book getBookByIsbn(int isbn);
    void saveBook(Book book);
    void deleteBook(Book book);
}
