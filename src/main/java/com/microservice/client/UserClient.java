package com.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.microservice.payload.UserDto;

@FeignClient("auth") //name-id that eureka server
public interface UserClient {

	@GetMapping("/api/auth/info")
	public UserDto getUser();
}
