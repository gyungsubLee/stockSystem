package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockService stockService;

    public NamedLockStockFacade(LockRepository lockRepository, StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());  // 락 획득
            stockService.decrease(id, quantity);  // 락 획득 이후, 재고 감소 처리
        } finally {
            lockRepository.releaseLock(id.toString());  // 로직 종료 후, 락 해제
        }
    }
}
