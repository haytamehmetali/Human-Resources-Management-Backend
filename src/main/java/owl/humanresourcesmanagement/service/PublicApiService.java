package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.response.HomePageContentResponseDto;
import owl.humanresourcesmanagement.dto.response.HowItWorksResponseDto;
import owl.humanresourcesmanagement.dto.response.PlatformFeaturesResponseDto;
import owl.humanresourcesmanagement.entity.PublicHolidays;
import owl.humanresourcesmanagement.init.PublicApiInitializer;
import owl.humanresourcesmanagement.repository.PublicHolidaysRepository;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicApiService {
	private final PublicHolidaysRepository publicHolidaysRepository;
	
	// Home Page Content
	public HomePageContentResponseDto getHomePageContent() {
		return PublicApiInitializer.homePageContent();
	}
	
	// Platform Features
	public PlatformFeaturesResponseDto getPlatformFeatures() {
		return PublicApiInitializer.platformFeatures();
	}
	
	// How It Works
	public HowItWorksResponseDto getHowItWorks() {
		return PublicApiInitializer.howItWorks();
	}
	
	// Current Year PublicHolidays
	public List<PublicHolidays> findByPublicHolidaysCurrentYear() {
		LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
		LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
		return publicHolidaysRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);
	}
	
}
