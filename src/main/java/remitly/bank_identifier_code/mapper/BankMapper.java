package remitly.bank_identifier_code.mapper;

import remitly.bank_identifier_code.dto.BankBranchDTO;
import remitly.bank_identifier_code.dto.BankHeadquarterDTO;
import remitly.bank_identifier_code.entity.Bank;
import java.util.ArrayList;
import java.util.List;

public class BankMapper {
    public static BankBranchDTO mapToBankBranchDTO(Bank bank) {
        return new BankBranchDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getCountryName(),
                bank.isHeadquarter(),
                bank.getSwiftCode()
        );
    }

    public static BankHeadquarterDTO mapToBankHeadquarterDTO(Bank bank, Bank[] branches) {
        List<BankHeadquarterDTO.Branch> formattedBranches=new ArrayList<BankHeadquarterDTO.Branch>();
        for (Bank b : branches) {
            formattedBranches.add(new BankHeadquarterDTO.Branch(b.getAddress(),b.getBankName(),b.getCountryISO2(),b.isHeadquarter(),b.getSwiftCode()));
        }

        return new BankHeadquarterDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountryISO2(),
                bank.getCountryName(),
                bank.isHeadquarter(),
                bank.getSwiftCode(),
                formattedBranches
        );
    }
}
