package com.example.demo;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductTests {

	@Autowired
	private ProductRepository repo; 
	
	@Test
	@Rollback(false)
	public void testCreateProduct() {
		Product prod = new Product("iPhone 10", 1000);
		Product savedProduct = repo.save(prod);
		
		Assertions.assertNotNull(savedProduct);
	}
	
	@Test
	public void testFindProductByName() {
		String name = "iPhone 10";
		Product prod = repo.findByName(name);
		
		assertThat(prod.getName()).isEqualTo(name);
	}
	
	@Test
	public void testFindProductByNameNotExists() {
		String name = "iPhone 11";
		Product prod = repo.findByName(name);
		
		Assertions.assertNull(prod);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateProduct() {
		String prodName = "Kindle Reader";
		Product prod = new Product(prodName, 800);
		prod.setId(2);
		repo.save(prod);
		
		Product updatedProduct = repo.findByName(prod.getName());
		
		assertThat(updatedProduct.getName()).isEqualTo(prodName);
		
	}
	
	
	@Test
	public void testListProducts() {
		List<Product> list = (List<Product>)repo.findAll();
		
		for(Product prod : list) {
			System.out.println(prod);
		}
		
		assertThat(list).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteProduct() {
		Integer id = 2;
		
		boolean before = repo.findById(id).isPresent();
		
		repo.deleteById(id);
		
		boolean after = repo.findById(id).isPresent();
		
		Assertions.assertTrue(before);
		Assertions.assertFalse(after);
		
	}
	
	
}
