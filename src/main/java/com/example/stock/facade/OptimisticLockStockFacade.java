package com.example.stock.facade;


import com.example.stock.service.OptimisticLockStockService;
import org.springframework.stereotype.Component;

@Component
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public OptimisticLockStockFacade(OptimisticLockStockService optimisticLockStockService) {
        this.optimisticLockStockService = optimisticLockStockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        // 업데이트가 실패 되었을 시 계속 재시도
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity);
                break;  // 정상적으로 업데이트 시, while 문 종료
            } catch (Exception e) {
                Thread.sleep(50);  // 업데이트 실패 시, 50ms 이후 재시도
            }
        }
    }
}
