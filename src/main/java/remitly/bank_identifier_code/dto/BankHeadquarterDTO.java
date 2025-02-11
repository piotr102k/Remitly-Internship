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
public class BankHeadquarterDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;
    private List<Branch> branches;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Branch{
        private String address;
        private String bankName;
        private String countryISO2;
        private boolean isHeadquarter;
        private String swiftCode;
    }
}

