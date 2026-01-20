package gus.buildrun.demo.repository;

import gus.buildrun.demo.entity.AccountStock;
import gus.buildrun.demo.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
