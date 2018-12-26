package com.example.exercise.service;

import com.example.exercise.dto.BookDto;
import com.example.exercise.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by chesterjavier
 */
@Service
@Slf4j
@Repository
@Transactional
public class BookService {

  @Autowired
  private EntityManager em;

  public void addBook(BookDto book) {
    log.info("[BookService] - Adding book into the database....");
    em.persist(new Book(book));
    em.flush();
  }

  public JSONObject fetchAllBooks() {
    log.info("[BookService] - Fetching all books in the database....");
    JSONObject json = new JSONObject();
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Book> query = builder.createQuery(Book.class);
    Root<Book> book = query.from(Book.class);
    query.select(book);
    TypedQuery<Book> result = em.createQuery(query);
    List<Book> resultList = result.getResultList();
    json.put("data", resultList);
    json.put("totalRows", resultList.size());
    return json;
  }

  public void deleteBook(Integer id) {
    log.info("[BookService] - Deleting book in the database with ID [{}]", id);
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaDelete<Book> delete = builder.createCriteriaDelete(Book.class);
    Root<Book> bookRoot = delete.from(Book.class);
    Predicate bookId = builder.equal(bookRoot.get("id"), id);
    delete.where(bookId);
    em.createQuery(delete).executeUpdate();
  }
}
