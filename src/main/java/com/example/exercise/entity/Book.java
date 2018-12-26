package com.example.exercise.entity;

import com.example.exercise.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by chesterjavier
 */
@Table(name = "book")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title", length = 100)
  private String title;

  @Column(name = "author", length = 100)
  private String author;

  @Column(name = "year")
  private Integer year;

  public Book(BookDto book) {
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.year = Integer.valueOf(book.getYear());
  }
}