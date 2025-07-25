package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.CreatePersonalSpendingRequestDto;
import owl.humanresourcesmanagement.dto.request.ExpenseApproveRejectRequestDto;
import owl.humanresourcesmanagement.dto.request.UpdatePersonalSpendingRequestDto;
import owl.humanresourcesmanagement.dto.response.PersonalSpendingDetailResponseDto;
import owl.humanresourcesmanagement.dto.response.PersonalSpendingSummaryDto;
import owl.humanresourcesmanagement.dto.response.PersonalSpendingSummaryWithTotalResponseDto;
import owl.humanresourcesmanagement.entity.PersonalSpending;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.enums.spendings.ESpendingState;
import owl.humanresourcesmanagement.enums.user.EUserRole;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.mapper.PersonalSpendingMapper;
import owl.humanresourcesmanagement.repository.PersonalSpendingRepository;
import owl.humanresourcesmanagement.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpendingService {
	private final TokenService tokenService;
	private final PersonalSpendingRepository personalSpendingRepository;
	private final UserRepository userRepository;
	
	public PersonalSpendingSummaryWithTotalResponseDto getMonthlySummary(String token, Integer year, Integer month) {
		Long userId = tokenService.getToken(token).getId();
		
		// Kullanıcının belirtilen ay ve yıl için onaylanmış harcamaları getir
		List<PersonalSpending> spendingList = personalSpendingRepository.findAllByUserIdAndSpendingState(userId, ESpendingState.APPROVED)
				                                      .stream()
				                                      .filter(ps -> ps.getSpendingDate().getYear() == year && ps.getSpendingDate().getMonthValue() == month)
				                                      .toList();
		
		List<PersonalSpendingSummaryDto> summaries = spendingList.stream()
		                                                      .map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto)
		                                                      .collect(Collectors.toList());
		
		Double totalAmount = spendingList.stream()
		                              .mapToDouble(PersonalSpending::getBillAmount)
		                              .sum();
		
		return new PersonalSpendingSummaryWithTotalResponseDto(summaries, totalAmount);
	}
	
	public PersonalSpendingDetailResponseDto getExpenseDetailForManager(String token, Long id) {
		User manager = tokenService.getToken(token);
		
		if (manager.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		PersonalSpending expense = personalSpendingRepository.findById(id)
		                                                     .orElseThrow(() -> new Exception(ErrorType.EXPENSE_NOT_FOUND));
		
		// Harcama sahibini bul, Optional ile kontrol
		User expenseOwner = userRepository.findById(expense.getUserId())
		                               .orElseThrow(() -> new Exception(ErrorType.COMPANY_OR_EMPLOYEE_NOT_FOUND));
		
		if (!expenseOwner.getEmployeeInformation().getCompanyId().equals(manager.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		return PersonalSpendingMapper.INSTANCE.toPersonalSpendingDetailResponseDto(expense);
	}
	
	public Boolean approveRejectExpense(String token, ExpenseApproveRejectRequestDto dto) {
		User manager = tokenService.getToken(token);
		
		if (manager.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		PersonalSpending expense = personalSpendingRepository.findById(dto.id())
		                                                     .orElseThrow(() -> new Exception(ErrorType.EXPENSE_NOT_FOUND));
		
		User expenseOwner = userRepository.findById(expense.getUserId())
		                               .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		if ((!Objects.equals(manager.getEmployeeInformation().getCompanyId(), expenseOwner.getEmployeeInformation().getCompanyId()))) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		if (expense.getSpendingState() != ESpendingState.PENDING) {
			throw new Exception(ErrorType.INVALID_EXPENSE_OPERATION);
		}
		
		expense.setSpendingState(dto.isApproved() ? ESpendingState.APPROVED : ESpendingState.REJECTED);
		
		personalSpendingRepository.save(expense);
		
		return dto.isApproved();
	}
	
	public PersonalSpendingSummaryWithTotalResponseDto getCompanyUserMonthlySummary(String token, Long userId, Integer year, Integer month) {
		User manager = tokenService.getToken(token);
		
		if (manager.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new Exception(ErrorType.USER_NOT_FOUND);
		}
		
		if (!Objects.equals(manager.getEmployeeInformation().getCompanyId(), user.get().getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		List<PersonalSpending> expenses = personalSpendingRepository.findAllByUserIdAndYearAndMonthAndSpendingState(userId, year, month, ESpendingState.APPROVED);
		
		List<PersonalSpendingSummaryDto> summaries = expenses.stream().map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto).toList();
		
		Double totalAmount = expenses.stream().mapToDouble(PersonalSpending::getBillAmount).sum();
		
		return new PersonalSpendingSummaryWithTotalResponseDto(summaries, totalAmount);
	}
	
	public PersonalSpendingDetailResponseDto getExpenseDetail(String token, Long id) {
		User user = tokenService.getToken(token);
		
		// PersonalSpending id ile bul
		PersonalSpending personalSpending = personalSpendingRepository.findById(id)
				.orElseThrow(() -> new Exception(ErrorType.EXPENSE_NOT_FOUND));
		
		if (!personalSpending.getUserId().equals(user.getId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		return PersonalSpendingMapper.INSTANCE.toPersonalSpendingDetailResponseDto(personalSpending);
	}
	
	public Boolean createExpense(String token, CreatePersonalSpendingRequestDto dto) {
		User user = tokenService.getToken(token);
		
		// Mapper'dan entity'yi üret
		PersonalSpending expense = PersonalSpendingMapper.INSTANCE.toPersonalSpending(dto);
		expense.setUserId(user.getId());
		expense.setSpendingState(ESpendingState.PENDING);
		
		personalSpendingRepository.save(expense);
		return true;
	}
	
	public Boolean updateExpense(String token, Long id, UpdatePersonalSpendingRequestDto dto) {
		User user = tokenService.getToken(token);
		PersonalSpending expense = personalSpendingRepository.findById(id)
		                                                     .orElseThrow(() -> new Exception(ErrorType.EXPENSE_NOT_FOUND));
		
		if (!expense.getUserId().equals(user.getId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		// Eğer durum PENDING değilse silmeye izin verme
		if (expense.getSpendingState() != ESpendingState.PENDING) {
			throw new Exception(ErrorType.INVALID_EXPENSE_OPERATION);
		}
		
		// Null olan alanlar es geçilecek
		PersonalSpendingMapper.INSTANCE.updatePersonalSpendingFromDto(dto, expense);
		
		personalSpendingRepository.save(expense);
		return true;
	}
	
	public Boolean deleteExpense(String token, Long id) {
		PersonalSpending expense = personalSpendingRepository.findById(id)
		                                                     .orElseThrow(() -> new Exception(ErrorType.EXPENSE_NOT_FOUND));
		
		if (!expense.getUserId().equals(tokenService.getToken(token).getId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		// Eğer durum PENDING değilse silmeye izin verme
		if (expense.getSpendingState() != ESpendingState.PENDING) {
			throw new Exception(ErrorType.INVALID_EXPENSE_OPERATION);
		}
		
		personalSpendingRepository.delete(expense);
		return true;
	}
	
	public Page<PersonalSpendingSummaryDto> getPendingExpensesForManager(String token, Pageable pageable) {
		User user = tokenService.getToken(token);
		
		if (user.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		return personalSpendingRepository
				.findAllBySpendingStateAndCompanyId(ESpendingState.PENDING, user.getEmployeeInformation().getCompanyId(), pageable)
				.map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto);
	}
	
	public Page<PersonalSpendingSummaryDto> getApprovedExpensesForManager(String token, Pageable pageable) {
		User user = tokenService.getToken(token);
		
		if (user.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		if (user.getEmployeeInformation().getCompanyId() == null) {
			throw new Exception(ErrorType.COMPANY_OR_EMPLOYEE_NOT_FOUND);
		}
		
		return personalSpendingRepository
				.findAllBySpendingStateAndCompanyId(ESpendingState.APPROVED, user.getEmployeeInformation().getCompanyId(), pageable)
				.map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto);
	}
	
	public Page<PersonalSpendingSummaryDto> getRejectedExpensesForManager(String token, Pageable pageable) {
		User user = tokenService.getToken(token);
		
		if (user.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		return personalSpendingRepository
				.findAllBySpendingStateAndCompanyId(ESpendingState.REJECTED, user.getEmployeeInformation().getCompanyId(), pageable)
				.map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto);
	}
	
	public Page<PersonalSpendingSummaryDto> getAllExpensesForManager(String token, Pageable pageable) {
		User user = tokenService.getToken(token);
		
		if (user.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		return personalSpendingRepository
				.findAllBySpendingStateAndCompanyId(user.getEmployeeInformation().getCompanyId(), ESpendingState.REJECTED, pageable)
				.map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto);
	}
	
	public List<PersonalSpendingSummaryDto> getAllExpensesSummaryForPersonal(String token, Pageable pageable) {
		User user = tokenService.getToken(token);
		
		return personalSpendingRepository
				.findAllByUserIdAndSpendingStateNot(user.getId(), ESpendingState.REJECTED, pageable)
				.stream()
				.map(PersonalSpendingMapper.INSTANCE::toPersonalSpendingSummaryDto)   // Mapper ile dönüşüm
				.toList();
	}
	
}
