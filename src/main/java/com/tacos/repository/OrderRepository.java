package com.tacos.repository;

import com.tacos.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {

	List<TacoOrder> findByDeliveryZip(String deliveryZip);
}
