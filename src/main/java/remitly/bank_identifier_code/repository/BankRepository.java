package remitly.bank_identifier_code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.bank_identifier_code.entity.Bank;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank,String> {
    List<Bank> findAllBySwiftCodeStartingWith(String code);
    List<Bank> findAllByCountryISO2(String countryISO2);
}
