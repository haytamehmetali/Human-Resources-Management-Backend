package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageService {
	private static final String UPLOAD_DIR = "uploads/";
	
	public String save(MultipartFile file, Long userId) {
		try {
			// Eğer klasör yoksa oluştur
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			
			// Dosya adı: userId_timestamp_orijinaldosyaadı.ext
			String fileName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
			Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
			
			// Dosyayı kaydet
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return targetLocation.toString();
		} catch (IOException e) {
			throw new RuntimeException("Dosya kaydedilemedi: " + e.getMessage(), e);
		}
	}
}
