package com.example.accessingdatamongodb.config;

import com.example.accessingdatamongodb.converter.ZonedDateTimeReadConverter;
import com.example.accessingdatamongodb.converter.ZonedDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

  //******* MongoDB driver doesn't recognize ZonedDateTime, it's needed to convert before ******//
  @Bean
  public MongoCustomConversions customConversions() {
    List<Converter<?,?>> converters = new ArrayList<>();
    converters.add(new ZonedDateTimeReadConverter());
    converters.add(new ZonedDateTimeWriteConverter());
    return new MongoCustomConversions(converters);
  }

}
