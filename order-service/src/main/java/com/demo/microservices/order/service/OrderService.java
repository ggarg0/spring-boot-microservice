package com.demo.microservices.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.demo.microservices.order.client.InventoryClient;
import com.demo.microservices.order.dto.OrderRequest;
import com.demo.microservices.order.entity.Order;
import com.demo.microservices.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
	private final OrderRepository orderRepository;
	private final InventoryClient inventoryClient;

	public void placeOrder(OrderRequest orderRequest) {
		boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
		if (inStock) {
			Order order = mapToOrder(orderRequest);
			orderRepository.save(order);
		} else {
			throw new RuntimeException("Product with Skucode " + orderRequest.skuCode() + " is not in stock");
		}
	}

	private static Order mapToOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setPrice(orderRequest.price());
		order.setQuantity(orderRequest.quantity());
		order.setSkuCode(orderRequest.skuCode());
		return order;
	}
}