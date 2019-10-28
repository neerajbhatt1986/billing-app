package com.billing.dao;

import com.billing.entity.Category;
import com.billing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
