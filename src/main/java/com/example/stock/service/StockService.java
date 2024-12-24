package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.hibernate.annotations.Synchronize;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // 부모의 트랜잭션과 별도로 실행되어야 하기 때문에, Propagation.REQUIRES_NEW 적용
        // REQUIRES_NEW: 현재 진행 중인 트랜잭션과 독립적으로 새로운 트랜잭션을 시작하도록 강제함
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void  decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}