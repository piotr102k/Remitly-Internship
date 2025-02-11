package remitly.bank_identifier_code;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import remitly.bank_identifier_code.dto.Bank.BankCountryDTO;
import remitly.bank_identifier_code.dto.Bank.BankDTO;
import remitly.bank_identifier_code.entity.Bank;
import remitly.bank_identifier_code.exception.BadRequestException;
import remitly.bank_identifier_code.exception.ResourceNotFoundException;
import remitly.bank_identifier_code.repository.BankRepository;
import remitly.bank_identifier_code.service.implementation.BankServiceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankUnitTests {
    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImpl bankServiceImpl;

    @Test
    void returnsBankBySwiftCode(){
        Bank mockBank = new Bank("test_address","test_name","TT","Testland",false,"TESTTEST123");
        when(bankRepository.findById("TESTTEST123")).thenReturn(Optional.of(mockBank));

        BankDTO bank = bankServiceImpl.getSwiftCode("TESTTEST123");

        assertNotNull(bank);
        assertEquals("TESTTEST123",bank.getSwiftCode());
        assertEquals("test_name",bank.getBankName());
    }

    @Test
    void getBySwiftCodeThrowsExceptionWhenBankNotFound(){
        when(bankRepository.findById("TESTTEST123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            bankServiceImpl.getSwiftCode("TESTTEST123");
        });

        assertEquals("Swift code TESTTEST123 was not found", exception.getMessage());
    }

    @Test
    void returnsBankByCountry(){
        Bank mockBank = new Bank("test_address","test_name","TT","Testland",false,"TESTTEST123");
        List<Bank> mockBanks = List.of(mockBank);
        when(bankRepository.findAllByCountryISO2("TT")).thenReturn(mockBanks);

        BankCountryDTO banksByCountry = bankServiceImpl.getSwiftCodesByCountry("TT");

        assertNotNull(banksByCountry);
        assertEquals("Testland",banksByCountry.getCountryName());
        assertEquals("TESTTEST123",banksByCountry.getSwiftCodes().get(0).getSwiftCode());
    }

    @Test
    void getByCountryThrowsExceptionWhenBankNotFound(){
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            bankServiceImpl.getSwiftCode("TESTTEST123");
        });

        assertEquals("Swift code TESTTEST123 was not found", exception.getMessage());
    }

    @Test
    void createsSwiftCodeEntry(){
        BankDTO mockBank = new BankDTO("test_address","test_name","TT","Testland",false,"TESTTEST123");
        when(bankRepository.existsById("TESTTEST123")).thenReturn(false);

        String response = bankServiceImpl.createSwiftCodeEntry(mockBank);

        assertNotNull(response);
        assertEquals("New SWIFT code entry has been created",response);
    }

    @Test
    void createSwiftCodeEntryThrowsExceptionWhenIncorrectISO2IsGiven(){
        BankDTO mockBank = new BankDTO("test_address","test_name","WRONG","Testland",false,"TESTTEST123");


        Exception exception = assertThrows(BadRequestException.class, () -> {
            bankServiceImpl.createSwiftCodeEntry(mockBank);
        });


        assertEquals("Country ISO2 code must have a length of 2",exception.getMessage());
    }

    @Test
    void createSwiftCodeEntryThrowsExceptionWhenIncorrectSwiftCodeIsGiven(){
        BankDTO mockBank = new BankDTO("test_address","test_name","TT","Testland",false,"WRONG");

        Exception exception = assertThrows(BadRequestException.class, () -> {
            bankServiceImpl.createSwiftCodeEntry(mockBank);
        });

        assertEquals("Swift code  must have a length of 11",exception.getMessage());
    }

    @Test
    void createSwiftCodeEntryThrowsExceptionWhenSwiftCodeAlreadyExists(){
        BankDTO mockBank = new BankDTO("test_address","test_name","TT","Testland",false,"TESTTEST123");
        when(bankRepository.existsById("TESTTEST123")).thenReturn(true);

        Exception exception = assertThrows(BadRequestException.class, () -> {
            bankServiceImpl.createSwiftCodeEntry(mockBank);
        });

        assertEquals("Duplicate Swift code TESTTEST123",exception.getMessage());
    }

    @Test
    void deletesSwiftCodeEntry(){
        String response = bankServiceImpl.deleteSwiftCodeEntry("TESTTEST123");

        assertNotNull(response);
        assertEquals("SWIFT code entry has been deleted",response);
    }
}
