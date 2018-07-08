package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.service.BookService;
import com.shaoqunliu.demo.estore.validation.groups.book.AddBook;
import com.shaoqunliu.demo.estore.validation.groups.book.DeleteBook;
import com.shaoqunliu.demo.estore.validation.groups.book.ModifyBook;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public RestfulResult addBook(@RequestBody @Validated({AddBook.class}) Book book) {
        if (bookService.addBook(book)) {
            return new RestfulResult(0, "", new HashMap<>());
        }
        return new RestfulResult(1, "Cannot add book due to database error", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyBook(@RequestBody @Validated({ModifyBook.class}) Book book) {
        if (bookService.modifyBook(book)) {
            return new RestfulResult(0, "", new HashMap<>());
        }
        return new RestfulResult(1, "Cannot modify book information due to the original book info is not exist", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestBody @Validated({DeleteBook.class}) Book book) {
        bookService.deleteBook(book);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult findBook(@RequestParam("condition") String condition, @RequestParam("value") String value, HttpServletResponse response) throws IOException {
        List<Book> books = new ArrayList<>();
        switch (condition) {
            case "id":
                books = bookService.findBookById(Long.parseLong(value));
                break;
            case "name":
                books = bookService.findBookByName(value);
                break;
            default:
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No query mode named " + value);
                break;
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("books", books);
        return new RestfulResult(0, "", result);
    }
}
