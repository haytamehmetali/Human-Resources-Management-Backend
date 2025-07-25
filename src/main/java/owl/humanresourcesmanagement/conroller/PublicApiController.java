package owl.humanresourcesmanagement.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import owl.humanresourcesmanagement.dto.response.BaseResponse;
import owl.humanresourcesmanagement.dto.response.HomePageContentResponseDto;
import owl.humanresourcesmanagement.dto.response.HowItWorksResponseDto;
import owl.humanresourcesmanagement.dto.response.PlatformFeaturesResponseDto;
import owl.humanresourcesmanagement.entity.PublicHolidays;
import owl.humanresourcesmanagement.service.*;
import java.util.List;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PUBLIC_API)
@CrossOrigin(origins = "*")
public class PublicApiController {
	private final PublicApiService publicApiService;
	
	@GetMapping(HOMEPAGE_CONTENT)
	public ResponseEntity<BaseResponse<HomePageContentResponseDto>> getHomePageContent() {
		HomePageContentResponseDto homePageContent = publicApiService.getHomePageContent();
		return ResponseEntity.ok(BaseResponse.<HomePageContentResponseDto>builder()
		                                     .code(200)
		                                     .data(homePageContent)
		                                     .success(true)
		                                     .message("Home page contents have been brought in!")
		                                     .build());
	}
	
	@GetMapping(PLATFORM_FEATURES)
	public ResponseEntity<BaseResponse<PlatformFeaturesResponseDto>> getPlatformFeatures() {
		PlatformFeaturesResponseDto platformFeatures = publicApiService.getPlatformFeatures();
		return ResponseEntity.ok(BaseResponse.<PlatformFeaturesResponseDto>builder()
		                                     .code(200)
		                                     .data(platformFeatures)
		                                     .success(true)
		                                     .message("Platform features have been created!")
		                                     .build());
	}
	
	@GetMapping(HOW_IT_WORKS)
	public ResponseEntity<BaseResponse<HowItWorksResponseDto>> getHowItWorks() {
		HowItWorksResponseDto howItWorks = publicApiService.getHowItWorks();
		return ResponseEntity.ok(BaseResponse.<HowItWorksResponseDto>builder()
		                                     .code(200)
		                                     .data(howItWorks)
		                                     .success(true)
		                                     .message("Platform working structure!")
		                                     .build());
	}
	
	@GetMapping(CURRENT_YEAR_HOLIDAYS)
	public ResponseEntity<BaseResponse<List<PublicHolidays>>> findByPublicHolidaysCurrentYear() {
		List<PublicHolidays> holidayList = publicApiService.findByPublicHolidaysCurrentYear();
		return ResponseEntity.ok(BaseResponse.<List<PublicHolidays>>builder()
		                                     .code(200)
		                                     .data(holidayList)
		                                     .success(true)
		                                     .message("The public holidays of the current year have been successfully brought in!")
		                                     .build());
	}
	
}
