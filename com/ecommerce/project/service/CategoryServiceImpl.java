package com.ecommerce.project.service;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

//	private List<Category> categories = new ArrayList<>();
//	private Long nextId = 1L;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if(categories.isEmpty())
			throw new APIException("No Category created till now");
		return categoryRepository.findAll();
	}

	@Override
	public void createCategory(Category category) {
//		category.setCategoryId(nextId++);
//		categories.add(category);
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if(savedCategory!=null)
			throw new APIException("Category with the name" +category.getCategoryName()+" already exists!!!!");
		categoryRepository.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

//		List<Category> categories = categoryRepository.findAll();
//
//		Category category = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst()
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rescource Not Found"));

//		if (category == null) 
//			return "Category not found ";

//		categories.remove(category);
		categoryRepository.delete(category);
		return "Category with CategoryId: " + categoryId + " deleted Successfully";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {

//		List<Category> categories = categoryRepository.findAll();
		Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
//
//		Category savedCategory = savedCategoryOptional
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rescource Not Found"));
//		
		Category savedCategory = savedCategoryOptional
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

		category.setCategoryId(categoryId);
		savedCategory = categoryRepository.save(category);
		return savedCategory;
//		Optional<Category> optionalCategory = categories.stream()
//				.filter(c->c.getCategoryId().equals(categoryId))
//				.findFirst();

//		if(optionalCategory.isPresent()) {
//			Category existingCategory = optionalCategory.get();
////			existingCategory.setCategoryName(category.getCategoryName());
//			Category savedCategory = categoryRepository.save(existingCategory);
//			return savedCategory;
//		}else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
//		}

	}

}
