package com.chandu.mssscbeerorderservice.web.mappers;

import com.chandu.mssscbeerorderservice.domain.BeerOrderLine;
import com.chandu.mssscbeerorderservice.services.beer.BeerService;
import com.chandu.mssscbeerorderservice.services.beer.domain.BeerDto;
import com.chandu.mssscbeerorderservice.web.model.BeerOrderLineDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


public  abstract class BeerOderLineMapperDecorator  implements  BeerOrderLineMapper{

    BeerService beerService;
    BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerInventoryService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    public void setMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine) {
        BeerDto beerDto = beerService.getBeerByUpc(beerOrderLine.getUpc());

        BeerOrderLineDto beerOrderLineDto= beerOrderLineMapper.beerOrderLineToDto(beerOrderLine);
        beerOrderLineDto.setBeerName(beerDto.getBeerName());
        beerOrderLineDto.setVersion(beerDto.getVersion());
        beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());

        return beerOrderLineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {

        BeerDto beerDto = beerService.getBeerByUpc(beerOrderLineDto.getUpc());

        //beerOrderLineDto.setBeerName(beerDto.getBeerName());
        beerOrderLineDto.setBeerId(beerDto.getId());

        return beerOrderLineMapper.dtoToBeerOrderLine(beerOrderLineDto);
    }
}
