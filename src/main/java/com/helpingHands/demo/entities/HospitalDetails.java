package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "hospital_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDetails {
    @Id
    private String id;

    @DBRef
    private Fundraiser fundraiser;

    private String hospitalName;
    private String hospitalAddress;  
    private Long patientUHIDNumber;
    private String consultingDoctor;
    private Long doctorPhoneNumber;
    private String additionalInformation;
}

