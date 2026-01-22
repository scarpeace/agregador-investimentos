package gus.buildrun.demo.service;

import gus.buildrun.demo.controller.dto.CreateStockDto;
import gus.buildrun.demo.entity.AccountStockId;
import gus.buildrun.demo.entity.Stock;
import gus.buildrun.demo.repository.AccountRepository;
import gus.buildrun.demo.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    final StockRepository stockRepo;
    final AccountRepository accountRepo;


    public StockService(StockRepository stockRepo, AccountRepository accountRepo) {
        this.stockRepo = stockRepo;
        this.accountRepo = accountRepo;
    }

    public void createStock(CreateStockDto createStockDto){
        var stock = new Stock(createStockDto.stockId(), createStockDto.description());
        stockRepo.save(stock);
    }

    public void createAccountStock(AccountStockId accountStockId, CreateStockDto createStockDto){

    }
}
