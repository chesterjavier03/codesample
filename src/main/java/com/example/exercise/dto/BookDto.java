package com.example.exercise.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Created by chesterjavier
 */
@Getter
@Setter
@Component
public class BookDto {
  private String title;
  private String author;
  private String year;
}
