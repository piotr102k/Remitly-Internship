package remitly.bank_identifier_code.mapper;

import remitly.bank_identifier_code.dto.Bank.BankBranchDTO;
import remitly.bank_identifier_code.dto.Bank.BankCountryDTO;
import remitly.bank_identifier_code.dto.Bank.BankDTO;
import remitly.bank_identifier_code.entity.Bank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class BankMapper {


    public static BankBranchDTO toBankBranchDTO(Bank bank) {
        return new BankBranchDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.isHeadquarter(),
                bank.getSwiftCode()
        );
    }

    public static Bank mapFromBankDTO(BankDTO bankDTO) {
        return new Bank(
                bankDTO.getAddress(),
                bankDTO.getBankName(),
                bankDTO.getCountryISO2().toUpperCase(),
                bankDTO.getCountryName().toUpperCase(),
                bankDTO.isHeadquarter(),
                bankDTO.getSwiftCode()
        );
    }

    public static BankDTO mapToBankDTO(Bank bank, List<Bank> bankBranches) {
        List<BankBranchDTO> formattedBranches=new ArrayList<>();
        for (Bank b : bankBranches) {
            if(!Objects.equals(b.getSwiftCode(), bank.getSwiftCode())) {
                formattedBranches.add(toBankBranchDTO(b));
            }
        }

        return new BankDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getCountryName(),
                bank.isHeadquarter(),
                bank.getSwiftCode(),
                formattedBranches
        );
    }

    public static BankDTO mapToBankDTO(Bank bank) {

        return new BankDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getCountryName(),
                bank.isHeadquarter(),
                bank.getSwiftCode()
        );
    }

    public static BankCountryDTO mapToBankCountryDTO(String iso, String name, List<Bank> branches){
        List<BankBranchDTO> formattedBranches=new ArrayList<>();
        for (Bank b : branches) {
            formattedBranches.add(toBankBranchDTO(b));
        }

        return new BankCountryDTO(
                iso,
                name,
                formattedBranches
        );
    }
}
