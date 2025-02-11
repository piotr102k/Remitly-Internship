package remitly.bank_identifier_code.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import remitly.bank_identifier_code.dto.Bank.BankCountryDTO;
import remitly.bank_identifier_code.dto.Bank.BankDTO;
import remitly.bank_identifier_code.entity.Bank;
import remitly.bank_identifier_code.exception.BadRequestException;
import remitly.bank_identifier_code.exception.ResourceNotFoundException;
import remitly.bank_identifier_code.mapper.BankMapper;
import remitly.bank_identifier_code.repository.BankRepository;
import remitly.bank_identifier_code.service.BankService;

import java.util.List;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Override
    public BankCountryDTO getSwiftCodesByCountry(String countryIS02code) {
        List<Bank> banks = bankRepository.findAllByCountryISO2(countryIS02code);
        if(banks.isEmpty()){
            throw new ResourceNotFoundException("No Bank found for IS02 code: " + countryIS02code);
        }

        return BankMapper.mapToBankCountryDTO(countryIS02code,banks.get(0).getCountryName(),banks);
    }

    @Override
    public BankDTO getSwiftCode(String swiftCode) {
        Bank bank = bankRepository.findById(swiftCode)
                .orElseThrow(()-> new ResourceNotFoundException("Swift code "+swiftCode+" was not found"));

        if(bank.isHeadquarter()){
            List<Bank> bankBranches= bankRepository.findAllBySwiftCodeStartingWith(swiftCode.substring(0, 8));
            return BankMapper.mapToBankDTO(bank,bankBranches);
        }
        return BankMapper.mapToBankDTO(bank);
    }

    @Override
    public String createSwiftCodeEntry(BankDTO bankDTO) {
        if(bankDTO.getCountryISO2().length()!=2){
            throw  new BadRequestException("Country ISO2 code must have a length of 2");
        }
        if(bankDTO.getSwiftCode().length()!=11){
            throw  new BadRequestException("Swift code  must have a length of 11");
        }
        if(bankRepository.existsById(bankDTO.getSwiftCode())){
            throw  new BadRequestException("Duplicate Swift code "+bankDTO.getSwiftCode());
        }

        Bank bank = BankMapper.mapFromBankDTO(bankDTO);
        bankRepository.save(bank);
        return "New SWIFT code entry has been created";
    }

    @Override
    public String deleteSwiftCodeEntry(String swiftCode) {
        bankRepository.deleteById(swiftCode);
        return "SWIFT code entry has been deleted";
    }
}
