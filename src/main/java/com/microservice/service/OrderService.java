package com.microservice.service;

import com.microservice.model.Order;

public interface OrderService extends GeneralService<Order>{

	int countProductOrderedById(long productId);

}
