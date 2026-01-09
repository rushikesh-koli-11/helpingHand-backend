package com.helpingHands.demo.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.FundraiserDetailsDTO;
import com.helpingHands.demo.constants.FundraiserConstants;
import com.helpingHands.demo.entities.Fundraiser;
import com.helpingHands.demo.entities.FundraiserDetails;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.globalException.Response;
import com.helpingHands.demo.mapper.FundraiserDetailsMapper;
import com.helpingHands.demo.repository.FundraiserDetailsRepository;
import com.helpingHands.demo.repository.FundraiserRepository;
import com.helpingHands.demo.services.FundraiserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fundraiser-details")
@CrossOrigin(origins = "http://localhost:3000")
public class FundraiserDetailsController {

    private final FundraiserDetailsService fundraiserDetailsService;
    private final FundraiserDetailsRepository fundraiserDetailsRepository;
    private final FundraiserDetailsMapper fundraiserDetailsMapper;
    private final FundraiserRepository fundraiserRepository;

    // Creating fundraiser details with an optional cover picture
    @PostMapping
    public ResponseEntity<Response<FundraiserDetails>> createFundraiserDetails(
            @RequestParam int fundraiserId, @RequestParam(value = "file", required = false) MultipartFile coverPicture,
            @RequestParam String videoAppeal, @RequestParam String patientName, @RequestParam Integer patientAge,
            @RequestParam String patientGender, @RequestParam String medicalCondition, @RequestParam String story)
            throws IOException {
        return ResponseEntity.ok(fundraiserDetailsService.createFundraiserDetails(coverPicture, fundraiserId,
                videoAppeal, patientName, patientAge, patientGender, medicalCondition, story));
    }

    // Getting all fundraiser details
    @GetMapping
    public ResponseEntity<List<FundraiserDetailsDTO>> getAllFundraiserDetails() {
        return ResponseEntity.ok(fundraiserDetailsService.getAllFundraiserDetails());
    }

    // Getting fundraiser details by ID
    @GetMapping("/{id}")
    public ResponseEntity<FundraiserDetailsDTO> getFundraiserDetailsById(@PathVariable int id) {
    	FundraiserDetailsDTO fundraiserDetailsDTO = fundraiserDetailsService.getFundraiserDetailsById(id);
        if (fundraiserDetailsDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fundraiserDetailsService.getFundraiserDetailsById(id));
    }

    // Deleting fundraiser details by ID
    @DeleteMapping("/{id}")
    public void deleteFundraiserDetails(@PathVariable int id) {
        fundraiserDetailsService.deleteFundraiserDetails(id);
    }

    // Updating fundraiser details by fundraiser ID
    @PutMapping("/{fundraiserId}")
    public ResponseEntity<FundraiserDetailsDTO> updateFundraiserDetails(@PathVariable int fundraiserId,
            @RequestBody FundraiserDetailsDTO dto) {
        return ResponseEntity.ok(fundraiserDetailsService.updateFundraiserDetails(fundraiserId, dto));
    }

    // Downloading a cover picture as a Base64-encoded string
    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadFileAsBase64(@PathVariable int id) {
        FundraiserDetailsDTO fundraiserDetailsDTO = fundraiserDetailsService.getFundraiserDetailsById(id);
        if (fundraiserDetailsDTO != null && fundraiserDetailsDTO.getCoverPicture() != null) {
            String base64Data = Base64.getEncoder().encodeToString(fundraiserDetailsDTO.getCoverPicture());
            return ResponseEntity.ok().header("Content-Type", "application/json").body(base64Data);
        }
        return ResponseEntity.notFound().build();
    }

    // Uploading a cover picture and updating fundraiser details
    @PutMapping("/upload")
    public FundraiserDetailsDTO uploadFile(@RequestParam("file") MultipartFile coverPicture,
            @RequestParam int fundraiserId, @RequestParam String videoAppeal, @RequestParam String patientName,
            @RequestParam Integer patientAge, @RequestParam String patientGender, @RequestParam String medicalCondition,
            @RequestParam String story) {

        FundraiserDetails fundraiserDetails = fundraiserDetailsRepository.findByFundraiserId(fundraiserId)
                .orElseThrow(() -> new RuntimeException("Fundraiser not found for ID: " + fundraiserId));

        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new CustomExceptions(FundraiserConstants.FUNDRAISER_NOT_FOUND + fundraiserId));

        if (coverPicture != null && !coverPicture.isEmpty()) {
            fundraiserDetails.setCoverPicture(convertFileToByteArray(coverPicture));
        }

        fundraiserDetails.setVideoAppeal(videoAppeal);
        fundraiserDetails.setRemainingAmount(fundraiser.getGoalAmount() - fundraiser.getCurrentAmount());
        fundraiserDetails.setPatientName(patientName);
        fundraiserDetails.setPatientAge(patientAge);
        fundraiserDetails.setPatientGender(patientGender);
        fundraiserDetails.setMedicalCondition(medicalCondition);
        fundraiserDetails.setStory(story);

        FundraiserDetails updatedFundraiser = fundraiserDetailsRepository.save(fundraiserDetails);

        return fundraiserDetailsMapper.toDTO(updatedFundraiser);
    }

    // Converting a Base64 string to an image
    @PostMapping("/convert-to-image")
    public ResponseEntity<byte[]> convertBase64ToImage(@RequestBody String base64Data) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            return ResponseEntity.ok().header("Content-Type", "image/jpeg").body(imageBytes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Converting a MultipartFile to a byte array
    private byte[] convertFileToByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error converting file to byte array", e);
        }
    }
}