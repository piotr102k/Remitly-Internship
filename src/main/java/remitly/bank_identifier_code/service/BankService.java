package remitly.bank_identifier_code.service;

import remitly.bank_identifier_code.dto.Bank.BankCountryDTO;
import remitly.bank_identifier_code.dto.Bank.BankDTO;

public interface BankService {
BankCountryDTO getSwiftCodesByCountry(String countryIS02code);
BankDTO getSwiftCode(String swiftCode);
String createSwiftCodeEntry(BankDTO bankDTO);
String deleteSwiftCodeEntry(String swiftCode);
}
