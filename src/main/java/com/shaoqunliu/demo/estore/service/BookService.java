package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.Book;

public interface BookService {

    boolean addBook(Book book);

    boolean modifyBook(Book book);
}
