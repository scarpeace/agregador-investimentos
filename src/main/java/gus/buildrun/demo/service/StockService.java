package gus.buildrun.demo.service;

import gus.buildrun.demo.controller.dto.CreateStockDto;
import gus.buildrun.demo.entity.Stock;
import gus.buildrun.demo.repository.AccountRepository;
import gus.buildrun.demo.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockService {

    final StockRepository stockRepo;
    final AccountRepository accountRepo;


    public StockService(StockRepository stockRepo, AccountRepository accountRepo) {
        this.stockRepo = stockRepo;
        this.accountRepo = accountRepo;
    }

    public Stock createStock(CreateStockDto createStockDto){

        if(checkIfStockExists(createStockDto.stockId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Already exists");
        }

        var stock = new Stock(createStockDto.stockId(), createStockDto.description());
        return stockRepo.save(stock);
    }

    private boolean checkIfStockExists(String stockId){
        return stockRepo.existsById(stockId);
    }

}
