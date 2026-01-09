package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.entities.Donations;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.DonationsMapper;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.repository.UserRepository;

public class DonationsMapperTest {

    @InjectMocks
    private DonationsMapper donationsMapper;

    @Mock
    private FundraiserRepository fundraiserRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Testing toDTO method (Success Case)
    @Test
    void testToDTO_Success() {
        Donations donation = new Donations();
        donation.setDonationId(1);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);
        donation.setFundraiser(fundraiser);

        User user = new User();
        user.setUserId(2);
        donation.setUser(user);

        donation.setAmount(500.0);
        donation.setDonationDate(LocalDate.of(24, 3, 4));
        donation.setTransactionId("T-12345678");
        donation.setStatus(DonationStatus.SUCCESS);

        DonationsDTO dto = donationsMapper.toDTO(donation);

        assertNotNull(dto);
        assertEquals(1, dto.getDonationId());
        assertEquals(1, dto.getFundraiserId());
        assertEquals(2, dto.getUserId());
        assertEquals(500.0, dto.getAmount());
        assertEquals("04-03-24", dto.getDonationDate()); // Ensure date formatting works
        assertEquals("T-12345678", dto.getTransactionId());
        assertEquals(DonationStatus.SUCCESS, dto.getStatus());
    }

    //Testing toDTO method (Null Case)
    @Test
    void testToDTO_NullDonation() {
        assertNull(donationsMapper.toDTO(null));
    }

    //Testing toEntity method (Success Case)
    @Test
    void testToEntity_Success() {
        DonationsDTO dto = new DonationsDTO();
        dto.setFundraiserId(1);
        dto.setUserId(2);
        dto.setAmount(500.0);
        dto.setStatus(DonationStatus.SUCCESS);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        User user = new User();
        user.setUserId(2);

        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(userRepository.findById(2)).thenReturn(Optional.of(user));

        Donations donation = donationsMapper.toEntity(dto);

        assertNotNull(donation);
        assertEquals(fundraiser, donation.getFundraiser());
        assertEquals(user, donation.getUser());
        assertEquals(500.0, donation.getAmount());
        assertNotNull(donation.getTransactionId()); // Ensure transaction ID is generated
        assertEquals(DonationStatus.SUCCESS, donation.getStatus());
        assertEquals(LocalDate.now(), donation.getDonationDate()); // Ensure current date is set
    }

    //Testing toEntity method (Fundraiser Not Found)
    @Test
    void testToEntity_FundraiserNotFound() {
        DonationsDTO dto = new DonationsDTO();
        dto.setFundraiserId(1);
        dto.setUserId(2);
        dto.setAmount(500.0);

        when(fundraiserRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationsMapper.toEntity(dto);
        });

        assertEquals("Fundraiser not found for ID: 1", exception.getMessage());
    }

    // Testing toEntity method (User Not Found)
    @Test
    void testToEntity_UserNotFound() {
        DonationsDTO dto = new DonationsDTO();
        dto.setFundraiserId(1);
        dto.setUserId(2);
        dto.setAmount(500.0);

        Fundraiser fundraiser = new Fundraiser();
        fundraiser.setId(1);

        when(fundraiserRepository.findById(1)).thenReturn(Optional.of(fundraiser));
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationsMapper.toEntity(dto);
        });

        assertEquals("User not found for ID: 2", exception.getMessage());
    }

    // Testing generateTransactionId (Ensures format is correct)
    @Test
    void testGenerateTransactionId() throws Exception {
        // Use reflection to access private method
        java.lang.reflect.Method method = DonationsMapper.class.getDeclaredMethod("generateTransactionId");
        method.setAccessible(true);

        String transactionId = (String) method.invoke(donationsMapper);

        assertNotNull(transactionId);
        assertTrue(transactionId.startsWith("T-"));
        assertEquals(10, transactionId.length()); // "T-" + 8 random chars
    }

}
