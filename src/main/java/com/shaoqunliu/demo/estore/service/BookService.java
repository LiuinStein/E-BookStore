package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.Book;

import java.util.List;

public interface BookService {

    boolean addBook(Book book);

    boolean modifyBook(Book book);

    void deleteBook(Book book);

    List<Book> findBookById(Long id);

    List<Book> findBookByName(String name);
}
