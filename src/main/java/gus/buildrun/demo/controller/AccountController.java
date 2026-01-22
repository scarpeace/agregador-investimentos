package gus.buildrun.demo.controller;

import gus.buildrun.demo.controller.dto.AccountStockReponseDto;
import gus.buildrun.demo.controller.dto.AssociateAccountStockDto;
import gus.buildrun.demo.entity.Account;
import gus.buildrun.demo.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> createAccount(
            @PathVariable String accountId,
            @RequestBody AssociateAccountStockDto associateAccountStockDto){

        accountService.associateStock(accountId, associateAccountStockDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockReponseDto>> getAccount(@PathVariable String accountId){
        var accountStocks = accountService.getAccount(accountId);

        return ResponseEntity.ok(accountStocks);
    }
}
