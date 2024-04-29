package com.neha.ShoppingCart.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Data
public class ProductDto {
    private Long id;

    private String name;

    private Long price;

    private String description;

    private byte[] byteImg;

    private Long categoryId;

    private String categoryName;

    private MultipartFile img;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, Long price, String description, byte[] byteImg, Long categoryId, MultipartFile img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.byteImg = byteImg;
        this.categoryId = categoryId;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getByteImg() {
        return byteImg;
    }

    public void setByteImg(byte[] byteImg) {
        this.byteImg = byteImg;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", byteImg=" + Arrays.toString(byteImg) +
                ", categoryId=" + categoryId +
                ", img=" + img +
                '}';
    }
}
