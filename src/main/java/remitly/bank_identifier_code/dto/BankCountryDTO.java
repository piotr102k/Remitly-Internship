package remitly.bank_identifier_code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCountryDTO {
    private String countryISO2;
    private String countryName;
    private List<BankBranchDTO> swiftCodes;
}
