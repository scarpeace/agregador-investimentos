package gus.buildrun.demo.controller;


import gus.buildrun.demo.controller.dto.CreateStockDto;
import gus.buildrun.demo.service.StockService;
import gus.buildrun.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    final UserService userService;
    private final StockService stockService;

    public StockController(UserService userService, StockService stockService) {
        this.userService = userService;
        this.stockService = stockService;
    }

    @PostMapping()
    public ResponseEntity<Void> createStocks(@RequestBody CreateStockDto createStockDto){

        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
}
