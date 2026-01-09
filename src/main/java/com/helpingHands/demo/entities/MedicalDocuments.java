package com.helpingHands.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "MedicalDocuments")
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int medicalDocumentId;

	@ManyToOne
	@JoinColumn(name = "fundraiserId")
	private Fundraiser fundraiser;

	@Lob
	private byte[] medicalEstimate;
	
	@Lob
	private byte[] consentLetterFromPatient;
	
	@Lob
	private byte[] medicalReports;
	
	@Lob
	private byte[] otherDocs;
	
	private String additionalInformation;

}
