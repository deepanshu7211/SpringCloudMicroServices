package com.microservices.currencyconversionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConverterController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean getCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
            ){

        Map<String,String> map = new HashMap<>();
        map.put("from",from);
        map.put("to",to);


        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                map);

        CurrencyConversionBean currencyConversionBean = responseEntity.getBody();

        System.out.println("currencyConversionBean  ");
        System.out.println(currencyConversionBean);

        return new CurrencyConversionBean(currencyConversionBean.getId(),from,to,currencyConversionBean.getConversionMultiple()
                ,quantity,quantity.multiply(currencyConversionBean.getConversionMultiple()),currencyConversionBean.getPort());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean getCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){


//        Just to use Feign Client to get the Response using proxy

        CurrencyConversionBean currencyConversionBean = currencyExchangeServiceProxy.getExchange(from,to);

        /*
        * The below 2 lines are to get the response code from Feign
        * */
//        ResponseEntity<CurrencyConversionBean> responseEntity = currencyExchangeServiceProxy.getExchange(from,to);

//        CurrencyConversionBean currencyConversionBean = responseEntity.getBody();

        System.out.println("currencyConversionBean  feign");
        System.out.println(currencyConversionBean);

        logger.info("currencyConversionBean  feign {} ",currencyConversionBean);

        return new CurrencyConversionBean(currencyConversionBean.getId(),from,to,currencyConversionBean.getConversionMultiple()
                ,quantity,quantity.multiply(currencyConversionBean.getConversionMultiple()),currencyConversionBean.getPort());
    }
}
