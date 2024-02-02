package org.example.events.npmg.service.Images;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;


@Service
@RequiredArgsConstructor
public class AzureBlobUploader {

	@Value("${bonda.app.storageaccount.sas}")
	private String sasToken;

	@Value("${bonda.app.storageaccount.blob.endpoint}")
	private String blobEndpoint;

	@Value("${bonda.app.storageaccount.container.name.qrcode}")
	private String qrCodeContainerName;

	private BlobServiceClient blobServiceClient;
	@PostConstruct
	public void init() {
		this.blobServiceClient = new BlobServiceClientBuilder().endpoint(blobEndpoint).sasToken(sasToken).buildClient();
	}

	public String uploadImage(String pathToImage, String imageName) {
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(qrCodeContainerName);
		BlobClient blobClient = containerClient.getBlobClient(imageName);
		try {
			blobClient.uploadFromFile(pathToImage, false);
		} catch (UncheckedIOException e) {
			System.out.printf("Error: %s", e.getMessage());
		}

		return blobClient.getBlobUrl();
	}

/*	public ResponseEntity<MessageResponse> uploadImage() {
		String yourSasToken = "?sv=2022-11-02&ss=bfqt&srt=sco&sp=rwdlacupiytfx&se=2024-01-21T23:12:48Z&st=2024-01-21T15:12:48Z&spr=https&sig=AyySdFu6Dfk118ACzjDYZoTsG9lhT5miknsolhhfSGY%3D";
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.endpoint("https://bonda.blob.core.windows.net")
				.sasToken(yourSasToken)
				.buildClient();

		BlobContainerClient containerClient = null;
		try {
			containerClient = blobServiceClient.getBlobContainerClient("qrcodes");
		} catch (BlobStorageException ex) {
			throw ex;

		}

		BlobClient blobClient = containerClient.getBlobClient("qrcode1.jpg");
		blobClient.uploadFromFile("C:\\Users\\bonda\\OneDrive\\Pictures\\Icon\\218225177_1664897827232675_4879823125441694674_n.jpg");


		return ResponseEntity.ok(new MessageResponse("File uploaded successfully!"));
	}*/


}
