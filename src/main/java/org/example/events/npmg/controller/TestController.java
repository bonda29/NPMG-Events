package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.payload.request.TicketPurchaseRequest;
import org.example.events.npmg.service.Images.AzureBlobUploader;
import org.example.events.npmg.service.Images.QrCodeGenerator;
import org.example.events.npmg.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

	private final AzureBlobUploader azureBlobUploader;
	private final QrCodeGenerator qrCodeGenerator;
	private final PaymentService paymentService;

//	@PostMapping
//	public ResponseEntity<MessageResponse> qrcode(@RequestParam String text, @RequestParam String name) {
//		return ResponseEntity.ok(new MessageResponse(qrCodeGenerator.generateQrCode(name, text)));
//	}

	@PostMapping
	public ResponseEntity<?> payment(@RequestBody TicketPurchaseRequest data) {
		return paymentService.purchaseTicket(data);
	}


}
