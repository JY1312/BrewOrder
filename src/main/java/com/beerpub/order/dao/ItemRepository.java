package com.beerpub.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item getItemByName(String name);

    Item getItemByItemId(Integer id);

}
