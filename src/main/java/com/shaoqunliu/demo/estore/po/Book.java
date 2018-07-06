package com.shaoqunliu.demo.estore.po;

import com.shaoqunliu.demo.estore.validation.groups.book.AddBook;
import com.shaoqunliu.demo.estore.validation.groups.book.DeleteBook;
import com.shaoqunliu.demo.estore.validation.groups.book.ModifyBook;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_book_info")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {
            DeleteBook.class
    })
    private Long id;

    @NotNull(groups = {
            AddBook.class
    })
    private String name;

    @NotNull(groups = {
            AddBook.class
    })
    @PositiveOrZero(groups = {
            AddBook.class, ModifyBook.class
    })
    private Long price;

    @NotNull(groups = {
            AddBook.class
    })
    @PositiveOrZero(groups = {
            AddBook.class, ModifyBook.class
    })
    private Long remain;

    @Size(max = 255, groups = {
            AddBook.class, ModifyBook.class
    })
    private String img = "";

    @Size(max = 255, groups = {
            AddBook.class, ModifyBook.class
    })
    private String description = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price == null ? 0 : price;
    }

    public Long getRemain() {
        return remain;
    }

    public void setRemain(Long remain) {
        this.remain = remain == null ? 0 : remain;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? "" : img.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }
}