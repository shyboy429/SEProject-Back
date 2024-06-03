package edu.hit.testsheet.impl;

import edu.hit.testsheet.bean.Book;
import edu.hit.testsheet.repository.BookRepository;
import edu.hit.testsheet.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> selectAllBooks() {
        return bookRepository.findAll();
    }
}