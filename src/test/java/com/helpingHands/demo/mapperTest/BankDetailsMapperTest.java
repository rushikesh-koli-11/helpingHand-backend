package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.helpingHands.demo.DTO.BankDetailsDTO;
import com.helpingHands.demo.entities.BankDetails;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.mapper.BankDetailsMapper;
import com.helpingHands.demo.repository.FundraiserRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankDetailsMapperTest {

    @InjectMocks
    private BankDetailsMapper bankDetailsMapper;

    @Mock
    private FundraiserRepository fundraiserRepository;

    private Fundraiser fundraiser;
    private BankDetails bankDetails;
    private BankDetailsDTO bankDetailsDTO;

    @BeforeEach
    void setUp() {
        fundraiser = new Fundraiser();
        fundraiser.setId(101);

        bankDetails = new BankDetails();
        bankDetails.setBankId(1);
        bankDetails.setFundraiser(fundraiser);
        bankDetails.setBankName("Test Bank");
        bankDetails.setAccountHolderName("Om Parshetti");
        bankDetails.setAccountNumber("123456789");
        bankDetails.setIfscCode("IFSC0001234");
        bankDetails.setAccountType("Savings");
        bankDetails.setBranchName("Main Branch");
        bankDetails.setBranchAddress("123 Church Street");

        bankDetailsDTO = new BankDetailsDTO();
        bankDetailsDTO.setBankId(1);
        bankDetailsDTO.setFundraiserId(101);
        bankDetailsDTO.setBankName("Test Bank");
        bankDetailsDTO.setAccountHolderName("Om Parshetti");
        bankDetailsDTO.setAccountNumber("123456789");
        bankDetailsDTO.setIfscCode("IFSC0001234");
        bankDetailsDTO.setAccountType("Savings");
        bankDetailsDTO.setBranchName("Main Branch");
        bankDetailsDTO.setBranchAddress("123 Church Street");
    }

    // Test converting a valid entity to DTO
    @Test
    void testToDTO_ValidEntity() {
        BankDetailsDTO result = bankDetailsMapper.toDTO(bankDetails);
        assertNotNull(result);
        assertEquals(bankDetails.getBankId(), result.getBankId());
        assertEquals(bankDetails.getFundraiser().getId(), result.getFundraiserId());
        assertEquals(bankDetails.getBankName(), result.getBankName());
        assertEquals(bankDetails.getAccountHolderName(), result.getAccountHolderName());
        assertEquals(bankDetails.getAccountNumber(), result.getAccountNumber());
        assertEquals(bankDetails.getIfscCode(), result.getIfscCode());
        assertEquals(bankDetails.getAccountType(), result.getAccountType());
        assertEquals(bankDetails.getBranchName(), result.getBranchName());
        assertEquals(bankDetails.getBranchAddress(), result.getBranchAddress());
    }

    // Test converting null entity to DTO
    @Test
    void testToDTO_NullEntity() {
        assertNull(bankDetailsMapper.toDTO(null));
    }

    // Test converting a valid DTO to entity
    @Test
    void testToEntity_ValidDTO() {
        when(fundraiserRepository.findById(bankDetailsDTO.getFundraiserId())).thenReturn(Optional.of(fundraiser));

        BankDetails result = bankDetailsMapper.toEntity(bankDetailsDTO);
        assertNotNull(result);
        
        // Do not assert bankId since it's likely unset
        assertEquals(bankDetailsDTO.getFundraiserId(), result.getFundraiser().getId());
        assertEquals(bankDetailsDTO.getBankName(), result.getBankName());
        assertEquals(bankDetailsDTO.getAccountHolderName(), result.getAccountHolderName());
        assertEquals(bankDetailsDTO.getAccountNumber(), result.getAccountNumber());
        assertEquals(bankDetailsDTO.getIfscCode(), result.getIfscCode());
        assertEquals(bankDetailsDTO.getAccountType(), result.getAccountType());
        assertEquals(bankDetailsDTO.getBranchName(), result.getBranchName());
        assertEquals(bankDetailsDTO.getBranchAddress(), result.getBranchAddress());

        verify(fundraiserRepository, times(1)).findById(bankDetailsDTO.getFundraiserId());
    }


    // Test converting null DTO to entity
    @Test
    void testToEntity_NullDTO() {
        assertNull(bankDetailsMapper.toEntity(null));
    }

    // Test converting DTO to entity when fundraiser is not found
    @Test
    void testToEntity_FundraiserNotFound() {
        when(fundraiserRepository.findById(bankDetailsDTO.getFundraiserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bankDetailsMapper.toEntity(bankDetailsDTO));
        assertEquals("Fundraiser not found for ID: " + bankDetailsDTO.getFundraiserId(), exception.getMessage());
    }
}
