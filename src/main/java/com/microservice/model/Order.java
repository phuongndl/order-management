package com.microservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.microservice.payload.OrderDetailDto;
import com.microservice.payload.OrderDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
@Data
public class Order extends Auditable {
	
	public enum OrderStatus {
		CREATED, PROCESSING, FINISHED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	private OrderStatus status;
	
	private double total;
	
	private long buyerId;
	
	private String buyerUsername;
	
	public static OrderDto toDto(Order item) {
		OrderDto dto = new OrderDto();
		if (item.getId() != null) {
			dto.setId(item.getId());
		}
		dto.setStatus(item.getStatus().name().toLowerCase());
		dto.setTotal(item.getTotal());
		List<OrderDetailDto> list = item.getOrderDetails()
				.stream()
				.map(detail -> OrderDetail.toDto(detail))
				.collect(Collectors.toList());
		dto.setDetails(list);
		dto.setBuyerId(item.getBuyerId());
		dto.setBuyerUsername(item.getBuyerUsername());
		return dto;
	}
	
	public static Order fromDto(OrderDto dto) {
		Order order = new Order();
		if (dto.getId() != 0) {
			order.setId(dto.getId());
		}
		order.setStatus(OrderStatus.valueOf(dto.getStatus()));
		order.setTotal(dto.getTotal());
		List<OrderDetail> list = dto.getDetails()
				.stream()
				.map(detail -> OrderDetail.fromDto(detail))
				.collect(Collectors.toList());
		order.setOrderDetails(list);
		
		
		return order;
	}
}
