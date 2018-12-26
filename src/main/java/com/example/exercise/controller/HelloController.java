package com.example.exercise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @RequestMapping(path = "/hello/{callerName}", method = RequestMethod.GET)
  public ResponseEntity<String> sayHello(@PathVariable("callerName") String callerName) {
    return new ResponseEntity<String>(String.format("Hello, %s!", callerName), HttpStatus.OK);
  }
}
