package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
