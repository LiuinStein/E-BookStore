package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.repository.BookRepository;
import com.shaoqunliu.demo.estore.service.BookService;
import com.shaoqunliu.jpa.util.ObjectCopyingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("myBookService")
public class MyBookService implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public MyBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(rollbackOn = {
            Exception.class
    })
    public boolean addBook(Book book) {
        return bookRepository.save(book).getId() != null;
    }

    @Override
    @Transactional(rollbackOn = {
            Exception.class
    })
    public boolean modifyBook(Book book) {
        Optional<Book> book1 = bookRepository.findById(book.getId());
        if (book1.isPresent()) {
            ObjectCopyingUtil.copyNullProperties(book1.get(), book);
            bookRepository.saveAndFlush(ObjectCopyingUtil.coverDifferentProperties(book, book1.get()));
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackOn = {
            Exception.class
    })
    public void deleteBook(Book book) {
        bookRepository.deleteById(book.getId());
    }

    @Override
    public List<Book> findBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> ret = new ArrayList<>();
        book.ifPresent(ret::add);
        return ret;
    }

    @Override
    public List<Book> findBookByName(String name) {
        return bookRepository.findByNameContaining(name);
    }
}
