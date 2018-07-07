package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.service.BookService;
import com.shaoqunliu.demo.estore.validation.groups.book.AddBook;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public RestfulResult modifyBook(@RequestBody @Validated Book book) {
        if (bookService.modifyBook(book)) {
            return new RestfulResult(0, "", new HashMap<>());
        }
        return new RestfulResult(1, "Cannot modify book information due to the original book info is not exist", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestBody @Validated Book book) {
        bookService.deleteBook(book);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult findBook(@RequestParam("mode") String mode, @RequestParam("condition") String condition, HttpServletResponse response) {
        List<Book> books;
        switch (mode) {
            case "id":
                books = bookService.findBookById(Long.parseLong(condition));
                break;
            case "name":
                books = bookService.findBookByName(condition);
                break;
            default:
                response.setStatus(400);
                return new RestfulResult(400, "No query mode named " + mode, new HashMap<>());
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("books", books);
        return new RestfulResult(0, "", result);
    }
}
