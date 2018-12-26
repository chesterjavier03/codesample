package com.example.excerise.controller;

import com.example.exercise.BookExampleApplication;
import com.example.exercise.dto.BookDto;
import com.example.exercise.service.BookService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by chesterjavier
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = BookExampleApplication.class)
@PropertySource("classpath:application.properties")
public class BookControllerTest {

  @MockBean
  private BookService bookService;

  protected MockMvc mockMvc;

  @Autowired
  protected WebApplicationContext wac;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test(expected = JsonProcessingException.class)
  public void testEndpoint_addBookData() throws Exception {
    BookDto book = mock(BookDto.class);
    book.setAuthor("J. D. Salinger");
    book.setTitle("The Catcher in the Rye");
    book.setYear("1951");

    this.mockMvc.perform(post("/api/v1/books")
        .content(new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).writeValueAsString(book))
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.json").value("Successfully added a new book"));
  }

  @Test
  public void testEndpoint_listBooks() throws Exception {
    JSONObject json = new JSONObject();
    json.put("totalRows", 3);

    when(bookService.fetchAllBooks()).thenReturn(json);

    this.mockMvc.perform(get("/api/v1/books"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalRows").value(3));
  }

  @Test
  public void testEndpoint_deleteBook() throws Exception {
    Integer id = 1;

    this.mockMvc.perform(delete("/api/v1/books/{id}", id).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").value("Successfully deleted book data with ID [" + id + "]"));
  }
}
