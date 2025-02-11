package remitly.bank_identifier_code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import remitly.bank_identifier_code.dto.Bank.BankCountryDTO;
import remitly.bank_identifier_code.dto.Bank.BankDTO;
import remitly.bank_identifier_code.exception.BadRequestException;
import remitly.bank_identifier_code.exception.ResourceNotFoundException;
import remitly.bank_identifier_code.repository.BankRepository;
import remitly.bank_identifier_code.service.implementation.BankServiceImpl;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BankIntegrityTests {
    @Autowired
    private BankServiceImpl bankService;

    @Autowired
    private BankRepository bankRepository;

    @BeforeEach
    void setUp() {
        bankRepository.deleteAll();
    }

    @Test
    void createsSwiftCodeEntry(){
        BankDTO bankDTO = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");

        String response = bankService.createSwiftCodeEntry(bankDTO);
        BankDTO bank = bankService.getSwiftCode("TESTTEST123");

        assertNotNull(response);
        assertEquals("New SWIFT code entry has been created",response);
        assertEquals("test_name",bank.getBankName());
    }

    @Test
    void createSwiftCodeEntryThrowsExceptionWhenSwiftCodeAlreadyExists(){
        BankDTO bankDTO = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");
        bankService.createSwiftCodeEntry(bankDTO);

        assertThrows(BadRequestException.class, ()->bankService.createSwiftCodeEntry(bankDTO));
    }

    @Test
    void deletesSwiftCodeEntry(){
        BankDTO bankDTO = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");
        bankService.createSwiftCodeEntry(bankDTO);

        String response = bankService.deleteSwiftCodeEntry("TESTTEST123");


        assertNotNull(response);
        assertEquals("SWIFT code entry has been deleted",response);
        assertThrows(ResourceNotFoundException.class, () -> {
            bankService.getSwiftCode("TESTTEST123");
        });
    }

    @Test
    void returnsBanksByCountry(){
        BankDTO bankDTO1 = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");
        BankDTO bankDTO2 = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST456");
        bankService.createSwiftCodeEntry(bankDTO1);
        bankService.createSwiftCodeEntry(bankDTO2);

        BankCountryDTO banksByCountry = bankService.getSwiftCodesByCountry("TT");

        assertNotNull(banksByCountry);
        assertEquals("TESTLAND",banksByCountry.getCountryName());
        assertEquals("TESTTEST123",banksByCountry.getSwiftCodes().get(0).getSwiftCode());
        assertEquals("TESTTEST456",banksByCountry.getSwiftCodes().get(1).getSwiftCode());
    }

    @Test
    void getByCountryThrowsNotFoundExceptionWhenNoBanksAreFound(){
        BankDTO bankDTO1 = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");
        BankDTO bankDTO2 = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST456");
        bankService.createSwiftCodeEntry(bankDTO1);
        bankService.createSwiftCodeEntry(bankDTO2);

        assertThrows(ResourceNotFoundException.class, ()->bankService.getSwiftCodesByCountry("WR"));
    }

    @Test
    void returnsBankBranchBySwiftCode(){
        BankDTO bankDTO = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");
        bankService.createSwiftCodeEntry(bankDTO);

        BankDTO bank = bankService.getSwiftCode("TESTTEST123");

        assertNotNull(bank);
        assertEquals("test_name",bank.getBankName());
        assertEquals("TESTTEST123",bank.getSwiftCode());
    }

    @Test
    void returnsBankHeadquarterWithoutBranchesBySwiftCode(){
        BankDTO bankDTO = new BankDTO("test_address","test_name","TT","TESTLAND",true,"TESTTESTXXX");
        bankService.createSwiftCodeEntry(bankDTO);

        BankDTO bank = bankService.getSwiftCode("TESTTESTXXX");

        assertNotNull(bank);
        assertEquals("test_name",bank.getBankName());
        assertEquals("TESTTESTXXX",bank.getSwiftCode());
        assertTrue(bank.getBranches().isEmpty());
    }

    @Test
    void returnsBankHeadquarterWithBranchesBySwiftCode(){
        BankDTO bankDTO1 = new BankDTO("test_address","test_name","TT","TESTLAND",false,"TESTTEST123");
        BankDTO bankDTO2 = new BankDTO("test_address","test_name","TT","TESTLAND",true,"TESTTESTXXX");
        bankService.createSwiftCodeEntry(bankDTO1);
        bankService.createSwiftCodeEntry(bankDTO2);

        BankDTO bank = bankService.getSwiftCode("TESTTESTXXX");

        assertNotNull(bank);
        assertEquals("test_name",bank.getBankName());
        assertEquals("TESTTESTXXX",bank.getSwiftCode());
        assertEquals("TESTTEST123",bank.getBranches().get(0).getSwiftCode());
    }

    @Test
    void getBySwiftCodeThrowsNotFoundExceptionWhenNoBanksAreFound() {
        BankDTO bankDTO = new BankDTO("test_address", "test_name", "TT", "TESTLAND", true, "TESTTESTXXX");
        bankService.createSwiftCodeEntry(bankDTO);

        assertThrows(ResourceNotFoundException.class, () -> bankService.getSwiftCode("WRONGWRONG1"));
    }
}
