package com.microservices.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
* To configure and call the currency-exchange service
* */

//@FeignClient(name = "currency-exchange-service",url = "localhost:8000")
//@FeignClient(name = "currency-exchange-service")

@FeignClient(name = "zuul-api-gateway-server")  // THis is for to connect through API Gateway
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

//    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean getExchange(@PathVariable String from, @PathVariable String to);

//    To get the response code
//    public ResponseEntity<CurrencyConversionBean> getExchange(@PathVariable String from, @PathVariable String to);
}
