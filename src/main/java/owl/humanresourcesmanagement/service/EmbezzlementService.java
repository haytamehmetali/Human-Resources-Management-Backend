package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.AssignEmbezzlementRequestDto;
import owl.humanresourcesmanagement.dto.request.CreateEmbezzlementRequestDto;
import owl.humanresourcesmanagement.dto.request.IsConfirmEmbezzlementRequestDto;
import owl.humanresourcesmanagement.dto.request.UpdateEmbezzlementProductRequestDto;
import owl.humanresourcesmanagement.dto.response.EmbezzlementProductDetailResponseDto;
import owl.humanresourcesmanagement.dto.response.EmbezzlementResponseDto;
import owl.humanresourcesmanagement.entity.Embezzlement;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.enums.user.*;
import owl.humanresourcesmanagement.enums.embezzlement.*;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.mapper.EmbezzlementMapper;
import owl.humanresourcesmanagement.repository.EmbezzlementRepository;
import owl.humanresourcesmanagement.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmbezzlementService {
	private final TokenService tokenService;
	private final EmbezzlementRepository embezzlementRepository;
	private final UserRepository userRepository;
	
	public List<EmbezzlementProductDetailResponseDto> getMyEmbezzlement(String token) {
		User user = tokenService.getToken(token);
		return embezzlementRepository.findAllByUserId(user.getId()).stream()
		                             .map(embezzlement -> EmbezzlementMapper.INSTANCE.toDetailDto(embezzlement, userRepository))
		                             .collect(Collectors.toList());
	}
	
	public Boolean createEmbezzlement(String token, CreateEmbezzlementRequestDto dto) {
		User userFromToken = tokenService.getToken(token);
		
		if (!userFromToken.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_NOT_MANAGER);
		}
		
		Embezzlement embezzlement = EmbezzlementMapper.INSTANCE.fromCreateDto(dto);
		embezzlement.setCompanyId(userFromToken.getEmployeeInformation().getCompanyId());
		embezzlement.setEmbezzlementType(dto.embezzlementType());
		embezzlement.setEmbezzlementState(EEmbezzlementState.PENDING);
		embezzlementRepository.save(embezzlement);
		return true;
	}
	
	public void assignEmbezzlementToUser(String token, AssignEmbezzlementRequestDto dto) {
		User manager = tokenService.getToken(token);
		
		Embezzlement embezzlement = embezzlementRepository.findById(dto.id())
		                                                  .orElseThrow(() -> new Exception(ErrorType.EMBEZZLEMENT_NOT_FOUND));
		
		User employee = userRepository.findById(dto.userId())
		                              .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		if (!manager.getRole().equals(EUserRole.MANAGER)){
			throw new Exception(ErrorType.UNAUTHORIZED_NOT_MANAGER);
		}
		
		if (!manager.getEmployeeInformation().getCompanyId().equals(employee.getEmployeeInformation().getCompanyId()) ||
				!manager.getEmployeeInformation().getCompanyId().equals(embezzlement.getCompanyId()) ||
				!embezzlement.getCompanyId().equals(employee.getEmployeeInformation().getCompanyId()))
		{
			throw new Exception(ErrorType.UNAUTHORIZED_DIFFERENT_COMPANY);
		}
  
		embezzlement.setUserId(dto.userId());
		embezzlement.setCompanyId(manager.getEmployeeInformation().getCompanyId());
		embezzlement.setAssignedDate(LocalDate.now());
		embezzlement.setEmbezzlementState(EEmbezzlementState.APPROVED);
		embezzlementRepository.save(embezzlement);
		
		// TODO: E-posta bildirimi - employee.getEmail() adresine mail gönderilebilir (mock)
	}
	
	public Boolean confirmOrRejectEmbezzlement(String token, IsConfirmEmbezzlementRequestDto dto) {
		User user = tokenService.getToken(token);
		
		Embezzlement embezzlement = embezzlementRepository.findById(dto.id())
		                                                  .orElseThrow(() -> new Exception(ErrorType.EMBEZZLEMENT_NOT_FOUND));
		
		if (!embezzlement.getUserId().equals(user.getId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_EMBEZZLEMENT_OWNER);
		}
		
		if (!embezzlement.getEmbezzlementState().equals(EEmbezzlementState.PENDING)) {
			throw new Exception(ErrorType.EMBEZZLEMENT_STATE_DOESNT_PENDING);
		}
		
		if (dto.isConfirm()) {
			embezzlement.setEmbezzlementState(EEmbezzlementState.APPROVED);
			embezzlement.setRejectReason(null);
		} else {
			embezzlement.setEmbezzlementState(EEmbezzlementState.REJECTED);
			embezzlement.setRejectReason(dto.rejectReason());
		}
		return dto.isConfirm();
		// TODO: yöneticisine mail gönderimi — şimdilik log
	}
	
	public void updateEmbezzlementProduct(String token,Long embezzlementId, UpdateEmbezzlementProductRequestDto dto) {
		User manager = tokenService.getToken(token);
		
		Embezzlement product = embezzlementRepository.findById(embezzlementId)
		                                             .orElseThrow(() -> new Exception(ErrorType.EMBEZZLEMENT_NOT_FOUND));
		
		User user = userRepository.findById(dto.userId())
		                          .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		if (!manager.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_NOT_MANAGER);
		}
		
		if (!manager.getEmployeeInformation().getCompanyId().equals(product.getCompanyId()) ||
				!manager.getEmployeeInformation().getCompanyId().equals(user.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_DIFFERENT_COMPANY);
		}
		
		product.setDescription(dto.description());
		product.setUserId(dto.userId());
		product.setAssignedDate(dto.assignedDate());
		product.setReturnedDate(dto.returnedDate());
		product.setEmbezzlementState(dto.embezzlementState());
		embezzlementRepository.save(product);
		// TODO: Güncellenen kişiye bilgi ver (log veya e-posta)
	}
	
	public void deleteEmbezzlementProduct(String token, Long productId) {
		User manager = tokenService.getToken(token);
		
		Embezzlement product = embezzlementRepository.findById(productId)
		                                             .orElseThrow(() -> new Exception(ErrorType.EMBEZZLEMENT_NOT_FOUND));
		
		if (!manager.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_NOT_MANAGER);
		}
		
		if (!manager.getEmployeeInformation().getCompanyId().equals(product.getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_DIFFERENT_COMPANY);
		}
		
		if (!(product.getEmbezzlementState().equals(EEmbezzlementState.PENDING) ||
				product.getEmbezzlementState().equals(EEmbezzlementState.REJECTED))) {
			throw new Exception(ErrorType.CANNOT_DELETE_APPROVED_EMBEZZLEMENT);
		}
		embezzlementRepository.delete(product);
		
		// TODO: Kullanıcıya bilgi ver (log/e-posta)
	}
	
	public List<Embezzlement> getAssignedEmbezzlement(String token) {
		User userFromToken = tokenService.getToken(token);
		return embezzlementRepository.findAllByEmbezzlementState(userFromToken.getEmployeeInformation().getCompanyId(), EEmbezzlementState.APPROVED);
	}
	
	public List<Embezzlement> getUnAssignedEmbezzlement(String token) {
		User user = tokenService.getToken(token);
		return embezzlementRepository.findAllByEmbezzlementStateNot(user.getEmployeeInformation().getCompanyId(), EEmbezzlementState.APPROVED);
	}
	
	public List<Embezzlement> getRejectedEmbezzlement(String token) {
		User user = tokenService.getToken(token);
		return embezzlementRepository.findAllByEmbezzlementState(user.getEmployeeInformation().getCompanyId(), EEmbezzlementState.REJECTED);
	}
	
	public List<EmbezzlementResponseDto> getAllByCompany(String token) {
		User userFromToken = tokenService.getToken(token);
		List<Embezzlement> embezzlementlist = embezzlementRepository.findAllByCompanyId(userFromToken.getEmployeeInformation().getCompanyId());
		
		return embezzlementlist.stream()
		                    .map(EmbezzlementMapper.INSTANCE::toResponseDto)
		                    .collect(Collectors.toList());
	}
	
	public EmbezzlementProductDetailResponseDto getEmbezzlementDetails(String token, Long embezzlementId) {
		User userFromToken = tokenService.getToken(token);
		
		Embezzlement embezzlement = embezzlementRepository.findById(embezzlementId)
		                                                  .orElseThrow(() -> new Exception(ErrorType.EMBEZZLEMENT_NOT_FOUND));
		
		if (!userFromToken.getEmployeeInformation().getCompanyId().equals(embezzlement.getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_DIFFERENT_COMPANY);
		}
		
		return EmbezzlementMapper.INSTANCE.toEmbezzlementDetails(embezzlement, userRepository);
	}
	
}
