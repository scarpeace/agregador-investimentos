package gus.buildrun.demo.service;

import gus.buildrun.demo.client.BrapiClient;
import gus.buildrun.demo.controller.dto.AccountResponseDto;
import gus.buildrun.demo.controller.dto.AccountStockReponseDto;
import gus.buildrun.demo.controller.dto.AssociateAccountStockDto;
import gus.buildrun.demo.controller.dto.CreateAccountDto;
import gus.buildrun.demo.entity.Account;
import gus.buildrun.demo.entity.AccountStock;
import gus.buildrun.demo.entity.AccountStockId;
import gus.buildrun.demo.entity.BillingAddress;
import gus.buildrun.demo.repository.AccountRepository;
import gus.buildrun.demo.repository.AccountStockRepository;
import gus.buildrun.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {


    @Value("#{environment.BRAPI_TOKEN}")
    private String BRAPI_TOKEN;
    private final AccountRepository accountRepo;
    private final AccountStockRepository accountStockRepo;
    private final StockRepository stockRepo;
    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepo, AccountStockRepository accountStockRepo, StockRepository stockRepo, BrapiClient brapiClient) {
        this.accountRepo = accountRepo;
        this.accountStockRepo = accountStockRepo;
        this.stockRepo = stockRepo;
        this.brapiClient = brapiClient;
    }

    public void associateStock(String accountId, AssociateAccountStockDto associateAccountStockDto){

        var account = accountRepo.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found in the DATABASE"));

        var stock = stockRepo.findById(associateAccountStockDto.stockId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found in the DATABASE"));

        var accountStockId = new AccountStockId(
                UUID.fromString(accountId),
                associateAccountStockDto.stockId());

        var accountStock = new AccountStock(accountStockId, account, stock, associateAccountStockDto.quantity());

        accountStockRepo.save(accountStock);
    }

    public List<AccountStockReponseDto> getAccount(String accountId){

        var account = accountRepo.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found in the DATABASE"));

        if(account.getAccountStocks().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This account has no stocks");
        }

        return account.getAccountStocks()
                .stream()
                .map(stck ->
                        new AccountStockReponseDto(
                                stck.getStock().getId(),
                                stck.getQuantity(),
                                getTotal(stck.getQuantity(), stck.getStock().getId())))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId){

        var response = brapiClient.getQuote(BRAPI_TOKEN, stockId);
        var price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }
}
