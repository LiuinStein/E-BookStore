package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.repository.BookRepository;
import com.shaoqunliu.demo.estore.service.BookService;
import com.shaoqunliu.jpa.util.ObjectCopyingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("myBookService")
public class MyBookService implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public MyBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public boolean addBook(Book book) {
        return bookRepository.save(book).getId() != null;
    }

    @Override
    @Transactional
    public boolean modifyBook(Book book) {
        Optional<Book> book1 = bookRepository.findById(book.getId());
        if (book1.isPresent()) {
            ObjectCopyingUtil.copyNullProperties(book1.get(), book);
            bookRepository.saveAndFlush(ObjectCopyingUtil.coverDifferentProperties(book, book1.get()));
            return true;
        }
        return false;
    }
}
