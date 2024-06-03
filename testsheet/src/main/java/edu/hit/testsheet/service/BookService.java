package edu.hit.testsheet.service;

import edu.hit.testsheet.bean.Book;

import java.util.List;

public interface BookService {

    // 查询所有图书信息
    List<Book> selectAllBooks();
}
