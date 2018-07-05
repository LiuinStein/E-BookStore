package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.service.BookService;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return new RestfulResult(0,"", new HashMap<>());
    }
}
