package com.helpingHands.demo.services.serviceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.helpingHands.demo.DTO.DonationsDTO;
import com.helpingHands.demo.constants.DonationConstants;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.DonationStatus;
import com.helpingHands.demo.entities.Donations;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.globalException.Response;
import com.helpingHands.demo.mapper.DonationsMapper;
import com.helpingHands.demo.repository.DonationsRepository;
import com.helpingHands.demo.repository.FundraiserDetailsRepository;
import com.helpingHands.demo.services.DonationsService;
import com.helpingHands.demo.services.PDFService;

/**
 * Service implementation for managing donations.
 * This class provides functionality for saving, retrieving, and updating donations,
 * as well as generating receipts and sending emails for successful donations.
 */
@Service
public class DonationsServiceImpl implements DonationsService {

    @Autowired
    private DonationsRepository donationRepository;

    @Autowired
    private FundraiserDetailsRepository fundraiserDetailsRepository;

    @Autowired
    private DonationsMapper donationMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PDFService pdfService;

    /**
     * Saves a donation by validating the data and updating the remaining amount for the fundraiser.
     *
     * @param dto The {@link DonationsDTO} containing the donation details.
     * @return A {@link Response} object with a success message and the saved donation details.
     * @throws CustomExceptions If the donation data is invalid or a database error occurs.
     */
    @Override
    public Response<DonationsDTO> saveDonation(DonationsDTO dto) {
        if (dto == null || dto.getAmount() <= 0) {
            throw new CustomExceptions(DonationConstants.INVALID_DONATION_DATA);
        }

        // Updating the remaining amount for the fundraiser
        FundraiserDetails fundraiserDetails = fundraiserDetailsRepository.findById(dto.getFundraiserId())
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND));
        fundraiserDetails.setRemainingAmount(fundraiserDetails.getRemainingAmount() - dto.getAmount());
        fundraiserDetailsRepository.save(fundraiserDetails);

        // Creating a donation entity and saving it to the database
        Donations donation = donationMapper.toEntity(dto);
        try {
            Donations savedDonation = donationRepository.save(donation);
            return new Response<>("Donated", "Successfully Donated", donationMapper.toDTO(savedDonation));
        } catch (Exception e) {
            throw new CustomExceptions(DonationConstants.DATABASE_ERROR);
        }
    }

    /**
     * Retrieves a donation by its ID.
     *
     * @param id The ID of the donation to retrieve.
     * @return The {@link DonationsDTO} representing the donation.
     * @throws CustomExceptions If the donation is not found.
     */
    @Override
    public DonationsDTO getDonationById(String id) {
        Donations donation = donationRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions(DonationConstants.DONATION_NOT_FOUND + id));
        return donationMapper.toDTO(donation);
    }

    /**
     * Retrieves all donations made by a specific user.
     *
     * @param userId The ID of the user whose donations are being retrieved.
     * @return A list of {@link DonationsDTO} representing the user's donations.
     * @throws CustomExceptions If a database error occurs.
     */
    @Override
    public List<DonationsDTO> getDonationsByUserId(String userId) {
        try {
            List<Donations> donations = donationRepository.findAll().stream()
                    .filter(x -> x.getUser() != null && x.getUser().getUserId().equals(userId))
                    .collect(Collectors.toList());

            return donations.stream()
                    .map(donationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomExceptions(DonationConstants.DATABASE_ERROR);
        }
    }

    /**
     * Retrieves all donations made to a specific fundraiser.
     *
     * @param fundraiserId The ID of the fundraiser whose donations are being retrieved.
     * @return A list of {@link DonationsDTO} representing the fundraiser's donations.
     * @throws CustomExceptions If a database error occurs.
     */
    @Override
    public List<DonationsDTO> getDonationsByFundraiserId(String fundraiserId) {
        try {
            List<Donations> donations = donationRepository.findAll().stream()
                    .filter(x -> x.getFundraiser() != null && x.getFundraiser().getId().equals(fundraiserId))
                    .collect(Collectors.toList());

            return donations.stream()
                    .map(donationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomExceptions(DonationConstants.DATABASE_ERROR);
        }
    }

    /**
     * Retrieves a donation as a DTO by its ID.
     *
     * @param donationId The ID of the donation to retrieve.
     * @return The {@link DonationsDTO} representing the donation.
     * @throws CustomExceptions If the donation is not found.
     */
    @Override
    public DonationsDTO getDonationDTO(String donationId) {
        Donations donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomExceptions(DonationConstants.DONATION_NOT_FOUND + donationId));
        return donationMapper.toDTO(donation);
    }

    /**
     * Updates the status of a donation and sends a receipt email if the donation is successful.
     *
     * @param donationId The ID of the donation to update.
     * @param status     The new status of the donation.
     * @throws CustomExceptions If the donation is not found or a database error occurs.
     */
    @Override
    public void updateDonationStatus(String donationId, DonationStatus status) {
        Donations donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomExceptions(DonationConstants.DONATION_NOT_FOUND + donationId));

        try {
            donation.setStatus(status);
            if (DonationStatus.SUCCESS.equals(donation.getStatus())) {
                Fundraiser fundraiser = donation.getFundraiser();
                FundraiserDetails fundraiserDetails = null;
                if (fundraiser != null && fundraiser.getId() != null) {
                    fundraiserDetails = fundraiserDetailsRepository.findByFundraiserId(fundraiser.getId())
                        .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND));
                    fundraiserDetails.setRemainingAmount(fundraiserDetails.getRemainingAmount() - donation.getAmount());
                    fundraiserDetailsRepository.save(fundraiserDetails);
                }

                // Generating a PDF receipt
                Path htmlPath = ResourceUtils.getFile("classpath:templates/receipt.html").toPath();
                String receiptContent = Files.readString(htmlPath);

                // Replacing placeholders with actual data
                if (donation.getUser() != null) {
                    receiptContent = receiptContent.replace("{{name}}", donation.getUser().getName() != null ? donation.getUser().getName() : "");
                    receiptContent = receiptContent.replace("{{email}}", donation.getUser().getEmail() != null ? donation.getUser().getEmail() : "");
                    receiptContent = receiptContent.replace("{{contactNumber}}", donation.getUser().getContactNumber() != null ? donation.getUser().getContactNumber() : "");
                } else {
                    receiptContent = receiptContent.replace("{{name}}", "");
                    receiptContent = receiptContent.replace("{{email}}", "");
                    receiptContent = receiptContent.replace("{{contactNumber}}", "");
                }
                receiptContent = receiptContent.replace("{{amount}}", donation.getAmount() != null ? donation.getAmount().toString() : "0");
                receiptContent = receiptContent.replace("{{donationDate}}", donation.getDonationDate() != null ? donation.getDonationDate().toString() : "");
                receiptContent = receiptContent.replace("{{transactionId}}", donation.getTransactionId() != null ? donation.getTransactionId() : "");
                if (fundraiserDetails != null && fundraiserDetails.getPatientName() != null) {
                    receiptContent = receiptContent.replace("{{fundraiserTitle}}", fundraiserDetails.getPatientName());
                } else {
                    receiptContent = receiptContent.replace("{{fundraiserTitle}}", "");
                }
                receiptContent = receiptContent.replace("{{receiptNumber}}", "R-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

                // Generating the PDF
                byte[] pdfAttachment = pdfService.generateReceipt(receiptContent);

                // Constructing the email content
                String subject = "🙏 Thank You for Your Donation!";
                String body = "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<style>"
                        + "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }"
                        + ".container { max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9; }"
                        + "h2 { color: #2E86C1; }"
                        + "p { font-size: 16px; margin-bottom: 10px; }"
                        + ".details { background-color: #fff; padding: 15px; border-radius: 8px; box-shadow: 0px 0px 10px #ddd; }"
                        + ".footer { font-size: 14px; color: #777; margin-top: 20px; text-align: center; }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "<div class='container'>"
                        + "<h2>🙏 Thank You for Your Donation!</h2>"
                        + "<p>Dear <strong>" + (donation.getUser() != null && donation.getUser().getName() != null ? donation.getUser().getName() : "Valued Donor") + "</strong>,</p>"
                        + "<p>We sincerely appreciate your generous donation to <strong>" + (fundraiser != null && fundraiser.getTitle() != null ? fundraiser.getTitle() : "our cause") + "</strong>. "
                        + "Your support helps us make a difference! 🌍</p>"
                        + "<div class='details'>"
                        + "<h3>💡 Donation Details</h3>"
                        + "<p><strong>💰 Amount:</strong> ₹" + (donation.getAmount() != null ? donation.getAmount() : "0") + "</p>"
                        + "<p><strong>🔗 Transaction ID:</strong> " + (donation.getTransactionId() != null ? donation.getTransactionId() : "N/A") + "</p>"
                        + "<p><strong>📅 Donation Date:</strong> " + (donation.getDonationDate() != null ? donation.getDonationDate() : "N/A") + "</p>"
                        + "</div>"
                        + "<p>Please find your <strong>Donation Receipt</strong> attached as a PDF.</p>"
                        + "<p>Thank you for your kindness and generosity! ❤️</p>"
                        + "<div class='footer'>"
                        + "<p>Best Regards,</p>"
                        + "<p><strong>Helping Hands Team</strong></p>"
                        + "</div>"
                        + "</div>"
                        + "</body>"
                        + "</html>";

                // Sending the email with the PDF attachment
                if (donation.getUser() != null && donation.getUser().getEmail() != null) {
                    emailService.sendEmail(donation.getUser().getEmail(), subject, body, pdfAttachment, "Donation_Receipt.pdf");
                }
            }

            donationRepository.save(donation);
        } catch (Exception e) {
            throw new CustomExceptions(DonationConstants.DATABASE_ERROR);
        }
    }
}