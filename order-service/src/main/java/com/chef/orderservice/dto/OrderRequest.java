package com.chef.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Запрос на создание заказа")
public class OrderRequest {

    @Schema(description = "Имя покупателя", example = "Иван Иванов")
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Schema(description = "Email покупателя", example = "ivan@example.com")
    @NotBlank(message = "Customer email is required")
    @Email(message = "Valid email is required")
    private String customerEmail;

    @Schema(description = "Название товара", example = "Ноутбук")
    @NotBlank(message = "Product name is required")
    private String productName;

    @Schema(description = "Количество товара", example = "1")
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Schema(description = "Цена за единицу товара", example = "75000")
    @NotNull(message = "Price per unit is required")
    private BigDecimal pricePerUnit;

    public OrderRequest() {}

    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getProductName() { return productName; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPricePerUnit() { return pricePerUnit; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; }
}