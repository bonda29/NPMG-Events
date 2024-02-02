package org.example.events.npmg.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.events.npmg.service.Images.AzureBlobUploader;
import org.example.events.npmg.service.Images.QrCodeGenerator;
import org.example.events.npmg.models.*;
import org.example.events.npmg.payload.TicketInfo;
import org.example.events.npmg.payload.request.TicketPurchaseRequest;
import org.example.events.npmg.payload.response.TicketPurchaseResponse;
import org.example.events.npmg.repository.TicketPurchaseRepository;
import org.example.events.npmg.repository.TicketRepository;
import org.example.events.npmg.repository.TicketTypeRepository;
import org.example.events.npmg.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
	private final UserRepository userRepository;
	private final TicketRepository ticketRepository;
	private final TicketTypeRepository ticketTypeRepository;
	private final TicketPurchaseRepository ticketPurchaseRepository;
	private final QrCodeGenerator qrCodeGenerator;
	private final AzureBlobUploader azureBlobUploader;

	public ResponseEntity<?> purchaseTicket(TicketPurchaseRequest data) {
		TicketType ticketType = findById(ticketTypeRepository, data.getTicketTypeId());
		User user = findById(userRepository, data.getUserId());
		Event event = ticketType.getEvent();

		Ticket ticket = createTicket(ticketType);
		TicketInfo ticketInfo = createTicketInfo(event, ticketType, user, ticket);

		String imageName = LocalDateTime.now().format(formatter) + "_" + ticket.getId().toString() + ".jpg";
		String qrCodePath = qrCodeGenerator.generateQrCode(imageName, ticketInfo.toJson());
		String qrCodeImageUrl = uploadImage(qrCodePath, imageName);
		updateTicketWithQrCode(ticket, qrCodeImageUrl);

		TicketPurchase ticketPurchase = createTicketPurchase(ticket, user);

		TicketPurchaseResponse response = createResponseObject(ticket, ticketPurchase);

		return ResponseEntity.ok(response);
	}

	private Ticket createTicket(TicketType ticketType) {
		Ticket ticket = new Ticket();
		ticket.setTicketType(ticketType);
		ticketRepository.save(ticket);
		return ticket;
	}

	private TicketInfo createTicketInfo(Event event, TicketType ticketType, User user, Ticket ticket) {
		TicketInfo ticketInfo = new TicketInfo();
		ticketInfo.setEventName(event.getName());
		ticketInfo.setTicketType(ticketType.getName());
		ticketInfo.setTicketPrice(ticketType.getPrice());
		ticketInfo.setUserEmail(user.getEmail());
		ticketInfo.setTicketId(ticket.getId());
		return ticketInfo;
	}

	private String uploadImage(String qrCodePath, String imageName) {
		return azureBlobUploader.uploadImage(qrCodePath, imageName);
	}

	private void updateTicketWithQrCode(Ticket ticket, String qrCodeImageUrl) {
		ticket.setQrCode(qrCodeImageUrl);
		ticketRepository.save(ticket);
	}

	private TicketPurchase createTicketPurchase(Ticket ticket, User user) {
		TicketPurchase ticketPurchase = new TicketPurchase();
		ticketPurchase.setTicket(ticket);
		ticketPurchase.setUser(user);
		ticketPurchase.setPurchaseDate(LocalDateTime.now());
		ticketPurchaseRepository.save(ticketPurchase);
		ticket.setTicketPurchase(ticketPurchase);
		ticketRepository.save(ticket);
		return ticketPurchase;
	}

	private TicketPurchaseResponse createResponseObject(Ticket ticket, TicketPurchase ticketPurchase) {
		TicketPurchaseResponse ticketPurchaseResponse = new TicketPurchaseResponse();
		ticketPurchaseResponse.setTicket(ticket);
		ticketPurchaseResponse.setTicketPurchase(ticketPurchase);
		return ticketPurchaseResponse;
	}
}