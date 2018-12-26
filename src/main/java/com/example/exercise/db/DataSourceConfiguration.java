package com.example.exercise.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfiguration {

  private static final String DB_SCRIPT_PATH = "classpath:exercise-db.sql";
  private static final String DB_DATA_SCRIPT_PATH = "classpath:exercise-db-data.sql";

  @Bean
  @Primary
  public DataSource datasource() {
    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
        .addScript(DB_SCRIPT_PATH).addScript(DB_DATA_SCRIPT_PATH).build();
    log.info("DataSource initialized.");
    return dataSource;
  }
}