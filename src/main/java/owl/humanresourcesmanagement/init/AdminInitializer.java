package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class AdminInitializer {

    public static List<Admin> adminInitializer() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Admin admin1 = Admin.builder()
                .username("admin")
                .password(passwordEncoder.encode("Admin123*"))
                .adminRole(EAdminRole.ADMIN)
                .build();

        Admin admin2 = Admin.builder()
                .username("mario")
                .password(passwordEncoder.encode("Admin123*"))
                .adminRole(EAdminRole.SUPER_ADMIN)
                .build();

        return List.of(admin1, admin2);
    }

}
