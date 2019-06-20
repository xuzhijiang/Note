package org.java.core.advanced.DesignPatterns.DAODesignPattern;

public class AccessBook {

    public static void main(String[] args) {

        BookDao bookDao = new BookDaoImpl();

        for (Book book : bookDao.getAllBooks()) {
            System.out.println("Book ISBN : " + book.getIsbn());
            System.out.println("Book ISBN : " + book.getBookName());
        }

        //update student
        Book book = bookDao.getAllBooks().get(1);
        book.setBookName("Algorithms");
        bookDao.saveBook(book);
        
        for (Book b : bookDao.getAllBooks()) {
            System.out.println("Book ISBN : " + b.getIsbn());
            System.out.println("Book ISBN : " + b.getBookName());
        }
    }
}
