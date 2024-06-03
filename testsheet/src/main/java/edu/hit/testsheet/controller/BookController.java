package edu.hit.testsheet.controller;

import edu.hit.testsheet.bean.Book;
import edu.hit.testsheet.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 查询所有
     * @return 所有图书信息
     */
    @GetMapping(value = "/select")
    public Map<String, List<Book>> select() {
        System.out.println("ssaavd");
        Map<String, List<Book>> map = new HashMap<>();
        map.put("Books", bookService.selectAllBooks());
        System.out.println(map);
        return map;
    }
}
