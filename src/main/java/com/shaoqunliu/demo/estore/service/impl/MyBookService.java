package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.repository.BookRepository;
import com.shaoqunliu.demo.estore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myBookService")
public class MyBookService implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public MyBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }
}
