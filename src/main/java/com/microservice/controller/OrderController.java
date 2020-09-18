package com.microservice.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.client.BookClient;
import com.microservice.client.UserClient;
import com.microservice.exceptions.BadRequestException;
import com.microservice.exceptions.NotFoundException;
import com.microservice.model.Order;
import com.microservice.model.OrderDetail;
import com.microservice.model.Order.OrderStatus;
import com.microservice.payload.BookDto;
import com.microservice.payload.OrderDetailDto;
import com.microservice.payload.OrderDetailRequest;
import com.microservice.payload.OrderDto;
import com.microservice.payload.OrderRequest;
import com.microservice.payload.UserDto;
import com.microservice.service.OrderService;
import com.microservice.utils.Utils;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	private final UserClient userClient;
	
	private final BookClient bookClient;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public List<OrderDto> list() {
		return orderService.findAll().stream().map(Order::toDto).collect(Collectors.toList());
	}

	@GetMapping("/detail/{id}")
	public OrderDto getDetail(@PathVariable("id") long id, Principal principal) {
		Order order = orderService.findById(id).orElseThrow(() -> new NotFoundException("Order is not found"));
		OrderDto orderDto = Order.toDto(order);
		
		if (!Utils.isAdmin()) {
			UserDto user = userClient.getUser();
			if (user.getId() != order.getBuyerId()) {
				throw new BadRequestException("UnAuthorized access");
			}
		}
		
		List<OrderDetailDto> details = orderDto.getDetails()
				.stream()
				.map(d -> {
					BookDto bookDto = bookClient.getBookDetail(d.getProductId());
					d.setProductName(bookDto.getName());
					
					return d;
				}).collect(Collectors.toList());
		orderDto.setDetails(details);

		return orderDto;
	}

	@PostMapping("/detail")
	public OrderDto createOrder(@RequestBody OrderRequest orderRequest) {
		Order order = new Order();
		order.setStatus(Order.OrderStatus.CREATED);
		
		UserDto userDto = userClient.getUser();
		List<OrderDetail> listOrderDetail = new ArrayList<>();
		double total = 0;
		for(OrderDetailRequest detail : orderRequest.getDetails()) {
			BookDto bookDto = bookClient.getBookDetail(detail.getId());
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProductId(bookDto.getId());
			orderDetail.setProductPrice(bookDto.getPrice());
			orderDetail.setQuantity(detail.getQuantity());
			orderDetail.setSubTotal(orderDetail.getQuantity() * orderDetail.getProductPrice());
			listOrderDetail.add(orderDetail);
			
			total += orderDetail.getSubTotal();
		}
		order.setBuyerId(userDto.getId());
		order.setBuyerUsername(userDto.getUsername());
		order.setOrderDetails(listOrderDetail);
		order.setTotal(total);
		order = orderService.save(order);
		
		return Order.toDto(order);
	}
	
	@PutMapping("/detail")
	public OrderDto updateOrder(@RequestBody OrderRequest dto) {
		Order order = orderService.findById(dto.getId()).orElse(new Order());
		order.setStatus(OrderStatus.valueOf(dto.getStatus()));
		order = orderService.save(order);
		
		return Order.toDto(order);
	}

	@DeleteMapping("/detail/{id}")
	public void delete(@PathVariable("id") long id) {
		Order order = orderService.findById(id).orElseThrow(() -> new NotFoundException("Order is not found"));
		orderService.delete(order);
	}
	
	@GetMapping("/count/{id}")
	public int countItemSoldById(@PathVariable("id") long productId) {
		return orderService.countProductOrderedById(productId);
	}
}
