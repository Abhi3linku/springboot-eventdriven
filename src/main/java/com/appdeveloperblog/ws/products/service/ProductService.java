package com.appdeveloperblog.ws.products.service;

import com.appdeveloperblog.ws.products.rest.CreateProductRestModel;

public interface ProductService {
	
	String createProduct(CreateProductRestModel productRestModel) throws Exception;

}
