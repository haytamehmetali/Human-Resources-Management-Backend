package owl.humanresourcesmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
	// COMPANY
	ALREADY_EXIST_COMPANY(9001, "Aynı isimde bir şirket zaten mevcut! Lütfen farklı bir isim seçin!", HttpStatus.CONFLICT),
	INVALID_COMPANY_MAIL_FORMAT(9002, "E-posta adresi formatı geçersiz veya beklenen şirket domainiyle uyuşmuyor (örnek: yourname@yourcompany.com)", HttpStatus.UNPROCESSABLE_ENTITY),
	MAIL_COMPANY_MISMATCH(9003, "E-posta domaini, kayıtlı şirket ismiyle uyuşmuyor! Lütfen şirketinize ait bir e-posta adresi kullanın!", HttpStatus.UNPROCESSABLE_ENTITY),
	COMPANY_NOT_FOUND(9004, "Şirket bulunamadı! Lütfen girdiğiniz bilgileri kontrol edin!", HttpStatus.NOT_FOUND),
	COMPANY_ALREADY_ACCEPTED(9005, "Bu şirkete ait bir hesap zaten mevcut! Lütfen farklı bir şirket deneyin!", HttpStatus.CONFLICT),
	COMPANY_STATE_SAME(9006, "Bu isimde bir şirket zaten mevcut! Lütfen farklı bir isim seçin!", HttpStatus.CONFLICT),
	COMPANY_NOT_ACCEPTED(9007, "Şirket henüz onaylanmamış!", HttpStatus.CONFLICT),
	COMPANY_DOESNT_PENDING(9008, "Şirket 'PENDING' durumunda değil! Bu işlem yalnızca beklemede olan şirketler için yapılabilir!", HttpStatus.BAD_REQUEST),
	EMPLOYEE_IN_COMPANY_NOT_FOUND(9009, "Belirtilen şirkete ait çalışan bulunamadı!", HttpStatus.NOT_FOUND),
	COMPANY_OR_EMPLOYEE_NOT_FOUND(9010, "Belirtilen şirket veya çalışanları bulunamadı!", HttpStatus.NOT_FOUND),
	EMPLOYEE_NOT_FOUND(9011, "Çalışan bulunamadı! Lütfen girdiğiniz bilgileri kontrol edin!", HttpStatus.NOT_FOUND),
	EMPLOYEE_ALREADY_EXIST_ACTIVE(9012, "Kullanıcı zaten 'ACTIVE' durumdadır!", HttpStatus.CONFLICT),
	EMPLOYEE_ALREADY_EXIST_INACTIVE(9013, "Kullanıcı zaten 'INACTIVE' durumdadır!", HttpStatus.CONFLICT),
	USER_STATE_DOESNT_ACTIVE(9014, "Token sahibi kullanıcı aktif durumda değildir!", HttpStatus.BAD_REQUEST),
	USER_COMPANY_STATE_DOESNT_ACCEPTED(9015, "Kullanıcının bağlı olduğu şirket onaylı durumda değildir!", HttpStatus.BAD_REQUEST),
	USER_NOT_MANAGER(9016, "Erişim yetkiniz yok! Bu işlemi yalnızca yöneticiler gerçekleştirebilir!", HttpStatus.UNPROCESSABLE_ENTITY),
	USER_STATE_SAME(9017, "Kullanıcının mevcut durumu ile aynı değer girildi! Lütfen farklı bir durum belirtin!", HttpStatus.CONFLICT),
	EMPLOYEE_DOESNT_PENDING(9018, "Kullanıcının durumu 'PENDING' değildir!", HttpStatus.BAD_REQUEST),
	EMPLOYEE_ALREADY_EXIST_DELETED(9019, "Kullanıcı zaten 'DELETED' durumdadır!", HttpStatus.CONFLICT),
	
	// USER
	ALREADY_EXIST_USER_MAIL(8001, "Bu e-posta adresine ait bir hesap zaten mevcut! Lütfen farklı bir e-posta adresi deneyin!", HttpStatus.CONFLICT),
	ALREADY_EXIST_USER_PHONE(8002, "Bu telefon numarasına ait bir hesap zaten mevcut! Lütfen farklı bir telefon numarası deneyin!", HttpStatus.CONFLICT),
	USER_NOT_FOUND(8003, "Kullanıcı bulunamadı! Lütfen girdiğiniz bilgileri kontrol edin!", HttpStatus.NOT_FOUND),
	
	// ACCOUNT
	ACCOUNT_ALREADY_ACTIVE(7001, "Bu hesap zaten aktif!", HttpStatus.BAD_REQUEST),
	ACCOUNT_DOESNT_ACTIVE(7002, "Bu hesap henüz aktifleştirilmemiş! Devam edebilmek için lütfen hesabınızı aktifleştirin!", HttpStatus.FORBIDDEN),
	ACCOUNT_PENDING(7003, "Hesabınız onay bekliyor! Lütfen onay sürecinin tamamlanmasını bekleyin!", HttpStatus.FORBIDDEN),
	ACCOUNT_DENIED(7004, "Hesap talebiniz reddedildi! Daha fazla bilgi için lütfen destek ile iletişime geçin!", HttpStatus.FORBIDDEN),
	ACCOUNT_INACTIVE(7005, "Hesabınız şu anda pasif durumda! Lütfen bu durumu çözmek için destek ile iletişime geçin!", HttpStatus.FORBIDDEN),
	ACCOUNT_BANNED(7006, "Hesabınız yasaklandı! Erişiminiz kısıtlanmıştır!", HttpStatus.FORBIDDEN),
	ACCOUNT_ALREADY_PASSIVE(7007, "Bu hesap zaten pasif durumda!", HttpStatus.BAD_REQUEST),
	
	// AUTHENTICATION (REGISTER-LOGIN-ACTIVATION)
	INVALID_MAIL_OR_PASSWORD(6001, "Geçersiz e-posta adresi veya şifre! Lütfen bilgilerinizi kontrol ederek tekrar deneyin!", HttpStatus.UNAUTHORIZED),
	ACTIVATION_CODE_MISMATCH(6002, "Girdiğiniz aktivasyon kodu hatalı! Lütfen kodu kontrol ederek tekrar deneyin!", HttpStatus.BAD_REQUEST),
	ACTIVATION_CODE_EXPIRED(6003, "Aktivasyon kodunun süresi dolmuş! Lütfen yeni bir kod talep edin!", HttpStatus.GONE),
	PASSWORD_RESET_CODE_MISMATCH(6004, "Parola sıfırlama kodu hatalı veya geçersiz!", HttpStatus.BAD_REQUEST),
	PASSWORD_RESET_CODE_EXPIRED(6005, "Parola sıfırlama kodunun süresi dolmuş!", HttpStatus.BAD_REQUEST),
	INVALID_TOKEN(6006, "Token bilgisi geçersiz!", HttpStatus.BAD_REQUEST),
	NEW_PASSWORD_RENEW_PASSWORD_MISMATCH(6007, "Yeni şifre ile tekrar edilen şifre uyuşmuyor!", HttpStatus.BAD_REQUEST),
	CHANGE_MAIL_CODE_MISMATCH(6008, "Doğrulama kodu eşleşmiyor. Lütfen kodu kontrol ederek tekrar deneyin!", HttpStatus.BAD_REQUEST),
	CHANGE_MAIL_CODE_EXPIRED(6009, "Doğrulama kodunun süresi dolmuş. Lütfen yeni bir kod talep edin!", HttpStatus.GONE),
	
	// HOLIDAY
	IN_LAST_THERE_YEARS(5001, "Yıl, son üç yıl içinde olmalıdır!", HttpStatus.BAD_REQUEST),
	FUTURE_YEARS(5002, "Yıl gelecekte olamaz!", HttpStatus.BAD_REQUEST),
	
	// ADMIN
	ALREADY_EXIST_ADMIN_USERNAME(4001, "Bu admin kullanıcı adı zaten mevcut!", HttpStatus.CONFLICT),
	ALREADY_EXIST_ADMIN_MAIL(4002, "Bu e-posta adresi başka bir admin tarafından kullanılmaktadır!", HttpStatus.CONFLICT),
	ADMIN_NOT_FOUND(4003, "Admin bulunamadı!", HttpStatus.NOT_FOUND),
	INVALID_USERNAME_OR_PASSWORD(4004, "Geçersiz kullanıcı adı veya şifre!", HttpStatus.UNAUTHORIZED),
	SUPER_ADMIN_NOT_DELETED(4005, "Süper admin silinemez! (Kurucu veya Baş Admin)", HttpStatus.UNAUTHORIZED),
	
	//PERMISSION
	PERMISSION_NOT_FOUND(3001, "İzin bulunamadı!", HttpStatus.NOT_FOUND),
	PERMISSION_STATE_DOESNT_PENDING(3002, "İzin durumu 'PENDING' değildir!", HttpStatus.BAD_REQUEST),
	ALREADY_HAS_PENDING_LEAVE_REQUEST(3003, "Çalışanın cevaplanmamış bir izin talebi bulunmaktadır.", HttpStatus.BAD_REQUEST),
	INSUFFICIENT_LEAVE_BALANCE(3004, "Yıllık izin bakiyesi yetersiz!", HttpStatus.BAD_REQUEST),
	
	// EXPENSES
	EXPENSE_NOT_FOUND(1001, "Harcama bulunamadı!", HttpStatus.NOT_FOUND),
	INVALID_EXPENSE_OPERATION(1002, "Bu işlem yalnızca bekleyen harcamalar için yapılabilir.", HttpStatus.BAD_REQUEST),
	
	// COMMON
	VALIDATION_EXCEPTION(500, "Bir veya birden fazla alan geçersiz! Lütfen giriş bilgilerinizi kontrol ederek tekrar deneyin!", HttpStatus.UNPROCESSABLE_ENTITY),
	AUTHENTICATION_EXCEPTION(501, "Kimlik doğrulama başarısız oldu! Lütfen giriş bilgilerinizi kontrol edin!", HttpStatus.UNAUTHORIZED),
	AUTHORIZATION_EXCEPTION(502, "Bu kaynağa erişim izniniz bulunmamaktadır!", HttpStatus.FORBIDDEN),
	RESOURCE_NOT_FOUND_EXCEPTION(503, "Kaynak bulunamadı!", HttpStatus.NOT_FOUND),
	BUSINESS_EXCEPTION(504, "İş kuralı ihlali nedeniyle işlem gerçekleştirilemedi!", HttpStatus.UNPROCESSABLE_ENTITY),
	INTERNAL_SERVER_ERROR(505, "Sunucuda beklenmeyen bir hata oluştu! Lütfen daha sonra tekrar deneyin!", HttpStatus.INTERNAL_SERVER_ERROR),
	JSON_CONVERT_ERROR(506, "Geçersiz giriş parametreleri! JSON verisi çözümlenemedi!", HttpStatus.BAD_REQUEST),
	BAD_REQUEST_ERROR(507, "Geçersiz veya hatalı istek parametreleri! Lütfen giriş bilgilerinizi kontrol edin!", HttpStatus.BAD_REQUEST),
	DUPLICATE_KEY(508, "Aynı benzersiz alana sahip bir kayıt zaten mevcut! Lütfen farklı değerler kullanın!", HttpStatus.CONFLICT),
	DATA_INTEGRITY_ERROR(509, "Veri bütünlüğü hatası! Tutarsız veya çelişkili veriler nedeniyle işlem tamamlanamadı!", HttpStatus.CONFLICT),
	PASSWORD_MISMATCH(510, "Şifreler uyuşmuyor!", HttpStatus.UNPROCESSABLE_ENTITY),
	PASSWORD_SAME(511, "Yeni şifre mevcut şifreniz ile aynı olamaz!", HttpStatus.UNPROCESSABLE_ENTITY),
	DELETED_ERROR_NOT_AUTH(512, "Silme yetkiniz bulunmamaktadır!", HttpStatus.UNPROCESSABLE_ENTITY),
	UNAUTHORIZED_OPERATION(513, "Yetkisiz işlem! Bu işlemi gerçekleştirme izniniz yok!", HttpStatus.FORBIDDEN),
	INVALID_REFRESH_TOKEN(514, "Geçersiz yenileme (refresh) token!", HttpStatus.BAD_REQUEST),
	EXPIRED_REFRESH_TOKEN(515, "Yenileme (refresh) token süresi dolmuş!", HttpStatus.BAD_REQUEST),
	MAIL_ALREADY_TAKEN(516, "Bu e-posta adresi zaten kullanımda!", HttpStatus.BAD_REQUEST),
	MAIL_SAME(517, "Yeni e-posta adresi, mevcut adresinizle aynı! Lütfen farklı bir e-posta girin!", HttpStatus.BAD_REQUEST),
	
	// EMBEZZLEMENT
	EMBEZZLEMENT_NOT_FOUND(1, "Zimmet Kaydı Bulunamadı!", HttpStatus.NOT_FOUND),
	EMBEZZLEMENT_ALREADY_ASSIGNED(2, "Zimmet zaten atanmış!", HttpStatus.BAD_REQUEST),
	INVALID_STATE_CHANGE(3, "Geçersiz durum değişikliği!", HttpStatus.BAD_REQUEST),
	NOTE_REQUIRED(4, "Not giriniz, ZORUNLU!", HttpStatus.BAD_REQUEST),
	EMBEZZLEMENT_STATE_DOESNT_PENDING(5, "Ekipman pending de değil" , HttpStatus.BAD_REQUEST ),
	CANNOT_DELETE_APPROVED_EMBEZZLEMENT(6, "Onaylanmış silinemez.", HttpStatus.BAD_REQUEST ),
	UNAUTHORIZED_NOT_MANAGER(7, "Bu işlemi yalnızca yöneticiler gerçekleştirebilir.", HttpStatus.FORBIDDEN),
	UNAUTHORIZED_DIFFERENT_COMPANY(8, "Yönetici, çalışan ve zimmet aynı şirkete ait olmalıdır.", HttpStatus.FORBIDDEN),
	UNAUTHORIZED_EMBEZZLEMENT_OWNER(9, "Sadece size ait zimmetleri onaylayabilir veya reddedebilirsiniz.", HttpStatus.FORBIDDEN),
	
	// COMMENT
	COMMENT_NOT_FOUND(2000, "Yorum bulunamadı", HttpStatus.NOT_FOUND),
	COMMENT_ALREADY_EXITS(2001, "Şirkete ait zaten yorum bulunmaktadır. Birden fazla yorum eklenemez.", HttpStatus.CONFLICT),
	INVALID_COMMENT_STATUS(2002, "Yorum gönderimde değil.", HttpStatus.BAD_REQUEST),
	
	// SHIFT
	USER_DOESNT_ACTIVE(1001, "Kullanıcı aktif değildir!", HttpStatus.UNPROCESSABLE_ENTITY),
	SHIFT_NOT_FOUND(1002, "Vardiya bulunamadı!", HttpStatus.NOT_FOUND),
	SHIFT_NOT_ASSIGNED_TO_THIS_USER(1003, "Vardiyada kullanıcı mevcut değildir!", HttpStatus.CONFLICT),
	SHIFT_DOESNT_ASSIGNED_TO_USER(1004, "Kullanıcıya vardiya atanmamış!", HttpStatus.CONFLICT),
	SHIFT_ALREADY_ASSIGNED_TO_USER(1005, "Bu kullanıcıya daha önce vardiya atanmış!", HttpStatus.CONFLICT),
	USER_NOT_PERSONAL(1006, "Kullanıcı 'PERSONAL' değildir!", HttpStatus.UNPROCESSABLE_ENTITY ),
	TIME_ZONE_DOES_SAME(1007, "Başlangıç ve bitiş saati aynı olamaz!", HttpStatus.BAD_REQUEST),
	END_HOUR_BEFORE_BEGIN_HOUR(1008, "Bitiş saati başlangıç saatinden sonra olmalıdır!", HttpStatus.BAD_REQUEST),
	SHIFT_ALREADY_ASSIGNED(1009, "Vardiyada tanımlı personel var o yüzden silinemez!", HttpStatus.BAD_REQUEST),
	SHIFT_CAPACITY_FULL(1010, "Kapasitesi doldu! Ya kapasitesini güncelle yada yeni bir vardiya açman gerekiyor!", HttpStatus.BAD_REQUEST),
	INVALID_CAPACITY(1011, "Geçersiz kapasite değeri girildi!", HttpStatus.BAD_REQUEST),
	USER_ALREADY_ASSIGNED_SHIFT(1012, "Kullanıcı zaten bu vardiyaya atanmış!", HttpStatus.CONFLICT),
	
	// BREAK
	BREAK_NOT_FOUND(2000, "Mola bulunamadı!", HttpStatus.NOT_FOUND),
	BREAK_ALREADY_ASSIGNED_TO_USER(2001, "Bu kullanıcıya daha önce mola atanmış!", HttpStatus.CONFLICT),
	
	
	
	
	// FINISH
	FINISH(0, "THE END!", HttpStatus.OK);
	
	int code;
	String message;
	HttpStatus httpStatus;
}
