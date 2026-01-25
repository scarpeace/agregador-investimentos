package gus.buildrun.demo.service;

import gus.buildrun.demo.controller.dto.CreateStockDto;
import gus.buildrun.demo.entity.Stock;
import gus.buildrun.demo.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepo;

    @InjectMocks
    private StockService stockService;

    @Captor
    private ArgumentCaptor<Stock> stockArgumentCaptor;


    @Nested
    class createStock{

        @DisplayName("Should create stock with success")
        @Test
        void shouldCreateStockWithSuccess(){
            //Arrange

            Stock stock = new Stock("PETR4","Petrobras");

            doReturn(stock).when(stockRepo).save(stockArgumentCaptor.capture());

            CreateStockDto input = new CreateStockDto("PETR4", "Petrobras");

            //Act
            var output = stockService.createStock(input);

            //Assert
            var stockCaptured = stockArgumentCaptor.getValue();
            assertNotNull(output);
            assertEquals(stockCaptured.getId(), input.stockId());
            assertEquals(stockCaptured.getDescription(), input.description());
            assertEquals(output.getId(), stockCaptured.getId());
            assertEquals(output.getDescription(), stockCaptured.getDescription());
            assertSame(String.class, output.getId().getClass());
            assertSame(String.class, output.getDescription().getClass());
        }

        @DisplayName("Should throw exception when stock already exists")
        @Test
        void shouldThrowExceptionWhenStockAlreadyExists(){
            doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(stockRepo).save(any());

            CreateStockDto stock = new CreateStockDto("PETR4", "Petrobras");

            assertThrows(ResponseStatusException.class, ()-> stockService.createStock(stock));
        }

    }


}