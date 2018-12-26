package com.example.exercise.controller;

import com.example.exercise.dto.BookDto;
import com.example.exercise.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by chesterjavier
 */
@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class BookController {

  @Autowired
  private BookService bookService;

  @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity addBook(@RequestBody BookDto bookDto) {
    try {
      log.info("[BookController] - Adding new book into the database with title [{}]", bookDto.getTitle());
      bookService.addBook(bookDto);
      JSONObject json = new JSONObject();
      json.put("json", "Successfully added a new book");
      return new ResponseEntity(json, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("[BookController] ERROR [{}]", e.getLocalizedMessage());
      return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity listBooks() {
    log.info("[BookController] - Listing all books...");
    return Optional.ofNullable(bookService.fetchAllBooks()).map(result -> new ResponseEntity(result, HttpStatus.OK))
        .orElse(new ResponseEntity("No data found", HttpStatus.NOT_FOUND));
  }

  @DeleteMapping(path = "/books/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> deleteBook(@PathVariable("id") final Integer bookId) {
    log.info("[BookController] - Deleting book data with ID [{}]", bookId);
    bookService.deleteBook(bookId);
    return new ResponseEntity<String>(String.format("Successfully deleted book data with ID [%s]", bookId), HttpStatus.OK);
  }
}
