package com.helpingHands.demo.services;

public interface PDFService {

	public byte[] generateReceipt(String htmlContent) throws Exception;
}
