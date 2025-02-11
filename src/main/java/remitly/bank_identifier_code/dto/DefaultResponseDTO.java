package remitly.bank_identifier_code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import remitly.bank_identifier_code.dto.Bank.BankBranchDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponseDTO {
    private String message;
}
