package org.java.core.base.reflection.part2;

public class Book {

    private String bookName;
    private String bookAuthor;

    public Book() {
        super();
    }

    private Book(String bookName){
        this.bookName = bookName;
    }

    public Book(String bookName, String bookAuthor) {
        super();
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void getBookInfo(){
        System.out.println("my boot info");
    }

}
