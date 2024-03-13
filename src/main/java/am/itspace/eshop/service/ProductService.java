package am.itspace.eshop.service;

import am.itspace.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Product findById(int id);

}
