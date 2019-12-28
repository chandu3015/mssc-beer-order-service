package com.chandu.mssscbeerorderservice.services;

import com.chandu.mssscbeerorderservice.domain.BeerOrder;
import com.chandu.mssscbeerorderservice.domain.Customer;
import com.chandu.mssscbeerorderservice.domain.OrderStatusEnum;
import com.chandu.mssscbeerorderservice.repositories.BeerOrderRepository;
import com.chandu.mssscbeerorderservice.repositories.CustomerRepository;
import com.chandu.mssscbeerorderservice.web.mappers.BeerOrderLineMapper;
import com.chandu.mssscbeerorderservice.web.mappers.BeerOrderMapper;
import com.chandu.mssscbeerorderservice.web.model.BeerOrderDto;
import com.chandu.mssscbeerorderservice.web.model.BeerOrderPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final ApplicationEventPublisher publisher;



    public BeerOrderServiceImpl(BeerOrderRepository beerOrderRepository,
                                CustomerRepository customerRepository,
                                BeerOrderMapper beerOrderMapper, ApplicationEventPublisher publisher) {
        this.beerOrderRepository = beerOrderRepository;
        this.customerRepository = customerRepository;
        this.beerOrderMapper = beerOrderMapper;
        this.publisher = publisher;

    }

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {

        Optional<Customer> customerOptional=customerRepository.findById(customerId);
        if(customerOptional.isPresent())
        {


            Page<BeerOrder> beerOrderPage = beerOrderRepository.findAllByCustomer(customerOptional.get(),pageable);
            BeerOrderPagedList beerOrderPagedList = new  BeerOrderPagedList(beerOrderPage.stream().
                    map(beerOrderMapper::beerOrderToDto).collect(Collectors.toList()),
                    PageRequest.of(
                    beerOrderPage.getPageable().getPageNumber(),
                    beerOrderPage.getPageable().getPageSize()),
                    beerOrderPage.getTotalElements());




            return beerOrderPagedList;

        }
        return null;
    }

    @Transactional
    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto)
    {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent())
        {
            BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
            beerOrder.setId(null);
            beerOrder.setCustomer(customerOptional.get());
            beerOrder.getBeerOrderLines().forEach(line->line.setBeerOrder(beerOrder));

            BeerOrder savedBeerOrder= beerOrderRepository.saveAndFlush(beerOrder);

            log.debug("Saved Beer Order: " + beerOrder.getId());
            System.out.println("Saved Beer Order: " + beerOrder.getId());
            return beerOrderMapper.beerOrderToDto(savedBeerOrder);

        }
        throw  new RuntimeException("Not found");
    }

    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrder);

    }

    private BeerOrder getOrder(UUID customerId, UUID orderId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

            if(beerOrderOptional.isPresent()){
                BeerOrder beerOrder = beerOrderOptional.get();

                // fall to exception if customer id's do not match - order not for customer
                if(beerOrder.getCustomer().getId().equals(customerId)){
                    return beerOrder;
                }
            }
            throw new RuntimeException("Beer Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}
