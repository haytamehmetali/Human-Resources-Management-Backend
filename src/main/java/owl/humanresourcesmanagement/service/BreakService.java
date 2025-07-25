package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.user.*;
import owl.humanresourcesmanagement.enums.company.*;
import owl.humanresourcesmanagement.mapper.*;
import owl.humanresourcesmanagement.repository.*;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreakService {
	private final BreakRepository breakRepository;
	private final TokenService tokenService;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	
	public Boolean createBreak(String token, CreateBreakRequestDto dto) {
		User userValid = validateUserAndCompanyState(token);
		
		Break newBreak = BreakMapper.INSTANCE.fromCreateDto(dto);
		newBreak.setCompanyId(userValid.getEmployeeInformation().getCompanyId());
		
		breakRepository.save(newBreak);
		return true;
	}
	
	public List<BreakResponseDto> getAllBreaks(String token) {
		User userValid = validateUserAndCompanyState(token);
		
		return breakRepository.findAllByCompanyId(userValid.getEmployeeInformation().getCompanyId())
		                      .stream()
		                      .map(BreakMapper.INSTANCE::toDto)
		                      .toList();
	}
	
	public BreakDetailsResponseDto getBreakDetailsById(String token, Long breakId) {
		Break breakValid = validateBreakState(token, breakId);
		
		return BreakMapper.INSTANCE.toDetailsDto(breakValid, userRepository);
	}
	
	public BreakDetailsResponseDto updateBreak(String token, BreakUpdateRequestDto dto, Long breakId) {
		Break breakValid = validateUserAndBreak(token, dto.userId(), breakId);
		
		BreakMapper.INSTANCE.updateBreakFromDto(dto, breakValid);
		Break updatedBreak = breakRepository.save(breakValid);
		
		return BreakMapper.INSTANCE.toDetailsDto(updatedBreak, userRepository);
	}
	
	public Boolean deleteBreak(String token, Long breakId) {
		Break breakValid = validateBreakState(token, breakId);
		breakRepository.delete(breakValid);
		
		return true;
	}
	
	// Validate Methods
	private Break validateUserAndBreak(String token, Long userId, Long breakId) {
		User userValid = validateUserAndCompanyState(token);
		
		User userById = userRepository.findById(userId)
		                           .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		if (!userById.getEmployeeInformation().getCompanyId().equals(userValid.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		if (!userById.getRole().equals(EUserRole.PERSONAL)) {
			throw new Exception(ErrorType.USER_NOT_PERSONAL);
		}
		
		Break breakValid = breakRepository.findById(breakId)
		                                  .orElseThrow(() -> new Exception(ErrorType.BREAK_NOT_FOUND));
		
		if (!breakValid.getCompanyId().equals(userValid.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		Optional<Break> existingBreak = breakId == null
				? breakRepository.findByUserIdAndCompanyId(userId, userValid.getEmployeeInformation().getCompanyId())
				: breakRepository.findByUserIdAndCompanyIdAndBreakIdNot(userId, userValid.getEmployeeInformation().getCompanyId(), breakId);
		
		if (existingBreak.isPresent()) {
			throw new Exception(ErrorType.BREAK_ALREADY_ASSIGNED_TO_USER);
		}
		
		return breakValid;
	}
	
	private Break validateBreakState(String token, Long breakId) {
		User userValid = validateUserAndCompanyState(token);
		
		Break breakValid = breakRepository.findById(breakId)
		                                  .orElseThrow(() -> new Exception(ErrorType.BREAK_NOT_FOUND));
		
		if (!breakValid.getCompanyId().equals(userValid.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		return breakValid;
	}
	
	private User validateUserAndCompanyState(String token) {
		User userFromToken = tokenService.getToken(token);
		
		Long userId = userFromToken.getId();
		Optional<User> userById = userRepository.findById(userId);
		
		Long companyId = userFromToken.getEmployeeInformation().getCompanyId();
		Optional<Company> companyById = companyRepository.findById(companyId);
		
		companyById.filter(c -> c.getCompanyState().equals(ECompanyState.ACCEPTED))
		           .orElseThrow(() -> new Exception(ErrorType.COMPANY_NOT_ACCEPTED));
		
		userById.filter(u -> u.getUserState().equals(EUserState.ACTIVE))
		        .orElseThrow(() -> new Exception(ErrorType.USER_DOESNT_ACTIVE));
		
		userById.filter(u -> u.getRole().equals(EUserRole.MANAGER))
		        .orElseThrow(() -> new Exception(ErrorType.USER_NOT_MANAGER));
		
		return userFromToken;
	}
	
}
