package remitly.bank_identifier_code.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banks")
public class Bank {
    private String address;
    @Column(nullable = false)
    private String bankName;
    @Column(nullable = false)
    private String countryISO2;
    @Column(nullable = false)
    private String countryName;
    @Column(nullable = false)
    private boolean isHeadquarter;
    @Id
    @Column(nullable = false, unique = true)
    private String swiftCode;
}
