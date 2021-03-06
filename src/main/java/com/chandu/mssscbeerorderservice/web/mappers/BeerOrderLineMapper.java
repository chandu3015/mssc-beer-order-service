package com.chandu.mssscbeerorderservice.web.mappers;

import com.chandu.mssscbeerorderservice.domain.BeerOrderLine;
import com.chandu.mssscbeerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@DecoratedWith(BeerOderLineMapperDecorator.class)
@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
