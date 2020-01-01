package com.chandu.mssscbeerorderservice.web.mappers;


import com.chandu.mssscbeerorderservice.domain.BeerOrder;
import com.chandu.mssscbeerorderservice.web.model.BeerOrderDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    @Mapping(target="customerId",source="customer.id")
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}

