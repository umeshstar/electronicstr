package com.bikkadit.electronicstroe.repositories;

import com.bikkadit.electronicstroe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , String> {

    // custom finder method for searching
    List<Product> findByTitleContaining(String subTitle);
    List<Product>findByLiveTrue();
}
