package owl.humanresourcesmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class BaseResponse<T> {
	Boolean success;
	String message;
	Integer code;
	T data;
}
