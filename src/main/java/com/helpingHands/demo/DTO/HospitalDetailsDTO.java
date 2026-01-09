package com.helpingHands.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDetailsDTO {
    private int id;
    private int fundraiserId;
    private String hospitalName;
    private String hospitalAddress;  
    private Long patientUHIDNumber;
    private String consultingDoctor;
    private Long doctorPhoneNumber;
    private String additionalInformation;  
}
