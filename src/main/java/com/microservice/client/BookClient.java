package com.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.payload.BookDto;

@FeignClient("book") //name-id that eureka server
public interface BookClient {

	@GetMapping("/api/detail/{id}")
	public BookDto getBookDetail(@PathVariable long id);
}
