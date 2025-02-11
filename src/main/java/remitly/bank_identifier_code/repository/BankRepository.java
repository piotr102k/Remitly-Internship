package remitly.bank_identifier_code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.bank_identifier_code.entity.Bank;


public interface BankRepository extends JpaRepository<Bank,Long> {
}
