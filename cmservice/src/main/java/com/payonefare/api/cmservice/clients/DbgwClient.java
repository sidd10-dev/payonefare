package com.payonefare.api.cmservice.clients;

import com.payonefare.api.cmservice.customers.dto.GetOrCreateCustomerRequestDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;

@Client(id = "dbgw")
public interface DbgwClient {

    @Get("/customers/{phone}")
    HttpResponse<Long> findCustomerByPhone(@PathVariable String phone);

    @Post("/customers")
    HttpResponse<Long> createCustomer(@Valid @Body GetOrCreateCustomerRequestDto getOrCreateCustomerRequestDto);
}
