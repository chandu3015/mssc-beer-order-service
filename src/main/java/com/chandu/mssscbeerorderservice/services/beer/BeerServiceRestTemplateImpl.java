package com.chandu.mssscbeerorderservice.services.beer;

import com.chandu.mssscbeerorderservice.services.beer.domain.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ConfigurationProperties(prefix = "chandu.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceRestTemplateImpl implements  BeerService {

   private  final String BEER_PATH="/api/v1/beer/upc/{upc}";
    private String beerServiceHost;
   private RestTemplate restTemplate;


    public BeerServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplate = restTemplateBuilder.build();
    }


    public void setBeerServiceHost(String beerServiceHost)
    {
         this.beerServiceHost=beerServiceHost;
    }

    @Override
    public BeerDto getBeerByUpc(Long upc) {

      System.out.println("beerSerice to be called ");
        ResponseEntity<BeerDto> responseEntity = restTemplate.exchange(beerServiceHost+BEER_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BeerDto>() {
                },
                (Object) upc
                );
        BeerDto beerDto = responseEntity.getBody();
        return beerDto;
    }
}
