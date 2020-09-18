package com.microservice.model;

import javax.persistence.*;

import com.microservice.payload.OrderDetailDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail extends Auditable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
	private Order order;
	
	private long productId;
	
	private double productPrice;
	
	private int quantity;
	
	private double subTotal;
	
	public static OrderDetailDto toDto(OrderDetail detail) {
		OrderDetailDto dto = new OrderDetailDto();
		if (detail.getId() != null) {
			dto.setId(detail.getId());
		}
		dto.setProductId(detail.getProductId());
		dto.setQuantity(detail.getQuantity());
		dto.setProductPrice(detail.getProductPrice());
		dto.setQuantity(detail.getQuantity());
		dto.setSubTotal(detail.getSubTotal());
		
		return dto;
	}
	
	public static OrderDetail fromDto(OrderDetailDto dto) {
		OrderDetail detail = new OrderDetail();
		if (dto.getId() != 0) {
			detail.setId(detail.getId());
		}
		detail.setProductId(dto.getProductId());
		detail.setQuantity(dto.getQuantity());
		detail.setProductPrice(dto.getProductPrice());
		detail.setQuantity(dto.getQuantity());
		detail.setSubTotal(dto.getSubTotal());
		
		return detail;
	}
}
