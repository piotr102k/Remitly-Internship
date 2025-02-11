package remitly.bank_identifier_code.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import remitly.bank_identifier_code.dto.Bank.BankCountryDTO;
import remitly.bank_identifier_code.dto.Bank.BankDTO;
import remitly.bank_identifier_code.dto.DefaultResponseDTO;
import remitly.bank_identifier_code.service.BankService;

@AllArgsConstructor
@RestController
@RequestMapping("v1/swift-codes")
public class BankController {

    private BankService bankService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> createSwiftCodeEntry(@RequestBody BankDTO bankDTO) {
        String message = bankService.createSwiftCodeEntry(bankDTO);
        return new ResponseEntity<>(new DefaultResponseDTO(message), HttpStatus.CREATED);
    }

    @GetMapping("{swift-code}")
    public ResponseEntity<BankDTO> getSwiftCode(@PathVariable("swift-code") String swiftCode) {
        BankDTO bankDTO = bankService.getSwiftCode(swiftCode);
        return ResponseEntity.ok(bankDTO);
    }

    @GetMapping("country/{countryISO2code}")
    public ResponseEntity<BankCountryDTO> getSwiftCodesByCountry(@PathVariable("countryISO2code") String countryISO2) {
        BankCountryDTO banksInCountry = bankService.getSwiftCodesByCountry((countryISO2));
        return ResponseEntity.ok(banksInCountry);
    }

    @DeleteMapping("{swift-code}")
    public ResponseEntity<DefaultResponseDTO> deleteSwiftCodeEntry(@PathVariable("swift-code") String swiftCode) {
        String message = bankService.deleteSwiftCodeEntry(swiftCode);
        return new ResponseEntity<>(new DefaultResponseDTO(message), HttpStatus.OK);
    }
}
