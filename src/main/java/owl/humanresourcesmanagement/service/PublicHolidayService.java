package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.response.PublicHolidayResponseDto;
import owl.humanresourcesmanagement.repository.PublicHolidaysRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicHolidayService {
	private final PublicHolidaysRepository publicHolidaysRepository;
	
	public List<PublicHolidayResponseDto> findAll() {
		return publicHolidaysRepository.findAll().stream()
		                               .map(PublicHolidayResponseDto::fromEntity)
		                               .toList();
	}
}
