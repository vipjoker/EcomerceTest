package com.vipjoker.ecomerce.service.product;

import com.vipjoker.ecomerce.dto.ImageDto;
import com.vipjoker.ecomerce.dto.ProductDto;
import com.vipjoker.ecomerce.exception.ProductDontFoundException;
import com.vipjoker.ecomerce.exception.ResourceNotFoundException;
import com.vipjoker.ecomerce.model.Category;
import com.vipjoker.ecomerce.model.Image;
import com.vipjoker.ecomerce.model.Product;
import com.vipjoker.ecomerce.repository.CategoryRepository;
import com.vipjoker.ecomerce.repository.ImageRepository;
import com.vipjoker.ecomerce.repository.ProductRepository;
import com.vipjoker.ecomerce.request.AddProductRequest;
import com.vipjoker.ecomerce.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    @Override
    public Product   addProduct(AddProductRequest product) {

        String name = product.getCategory().getName();
        Category byName = ofNullable(categoryRepository.findByName(name)).orElseGet(()->{
            Category category = new Category(name);
            return categoryRepository.save(category);
        });

        Product product1 = createProduct(product, byName);




        return productRepository.save(product1 );
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(()->{
            throw new ProductDontFoundException("Product not found");
        });
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()->{
             throw new ProductDontFoundException("Product not found");
        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest productRequest, Long productId) {

        return productRepository.findById(productId)
                .map(existingProduct-> updateExistingProduct(existingProduct,productRequest))
                .map(productRepository::save)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request ){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category byName = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(byName);
        return existingProduct;
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll() ;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name) ;
    }



    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }
    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());

        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();

        productDto.setImages(imageDtos);
        return productDto
    }
}
