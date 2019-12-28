package com.chandu.mssscbeerorderservice.web.mappers;


import com.chandu.mssscbeerorderservice.domain.BeerOrder;
import com.chandu.mssscbeerorderservice.web.model.BeerOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}

