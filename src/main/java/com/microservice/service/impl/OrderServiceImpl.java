package com.microservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.microservice.exceptions.NotFoundException;
import com.microservice.model.Order;
import com.microservice.repository.OrderDetailRepository;
import com.microservice.repository.OrderRepository;
import com.microservice.service.OrderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

	private final OrderRepository repository;
	
	private final OrderDetailRepository orderDetailRepository;
	

	@PreAuthorize("hasPermission('order', 'view')")
	@Override
	public Optional<Order> findById(long id) {
		return repository.findById(id);
	}

	@PreAuthorize("hasPermission('order', 'view')")
	@Override
	public List<Order> findAll() {
		return repository.findAll();
	}

	@PreAuthorize("hasPermission('order', 'create')")
	@Override
	public Order save(Order t) {
		return repository.save(t);
	}

	@PreAuthorize("hasPermission('order', 'update')")
	@Override
	public Order update(Order t) {
		findById(t.getId())
				.orElseThrow(() -> new NotFoundException("Not found request order"));
		// TODO Auto-generated method stub
		return repository.save(t);
	}

	@PreAuthorize("hasPermission('order', 'delete')")
	@Override
	public void delete(Order t) {
		Order b = findById(t.getId())
				.orElseThrow(() -> new NotFoundException("Not found request order"));
		repository.delete(b);
	}
	
	@Override
	public int countProductOrderedById(long productId) {
		return orderDetailRepository.findAllByProductId(productId)
				.stream()
				.mapToInt(t -> t.getQuantity())
				.sum();
	}
}
