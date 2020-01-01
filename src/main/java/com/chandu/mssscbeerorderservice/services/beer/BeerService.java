package com.chandu.mssscbeerorderservice.services.beer;

import com.chandu.mssscbeerorderservice.services.beer.domain.BeerDto;


public interface BeerService {

    BeerDto getBeerByUpc(Long Upc);
}
