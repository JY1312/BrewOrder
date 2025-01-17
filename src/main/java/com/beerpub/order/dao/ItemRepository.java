package com.beerpub.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item getItemByName(String name);

    Item getItemByItemId(Integer id);

    List<Item> findByAvailabilityTrueAndCategory(String category);

}
