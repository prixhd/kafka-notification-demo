package com.chef.orderservice.controller;

import com.chef.orderservice.dto.OrderRequest;
import com.chef.orderservice.model.OrderEvent;
import com.chef.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "API для управления заказами")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать заказ",
            description = "Создаёт новый заказ и отправляет событие в Kafka"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Заказ успешно создан",
                    content = @Content(schema = @Schema(implementation = OrderEvent.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса"
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные заказа",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "customerName": "Иван Иванов",
                                      "customerEmail": "ivan@example.com",
                                      "productName": "Ноутбук",
                                      "quantity": 1,
                                      "pricePerUnit": 75000
                                    }
                                    """
                    )
            )
    )
    public OrderEvent createOrder(@RequestBody @Valid OrderRequest request) {
        log.info("Received request to create order for: {}", request.getCustomerName());
        return orderService.createOrder(request);
    }
}