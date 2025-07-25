package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.Permission;
import owl.humanresourcesmanagement.enums.permissions.EPermissionState;
import owl.humanresourcesmanagement.enums.permissions.EPermissionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PermissionInitializer {

    public static List<Permission> permissionInitializer() {
        List<Permission> permissions = new ArrayList<>();

        // EmployeeInformation ID: 11 için izinler
        permissions.add(Permission.builder()
                .userId(11L)
                .permissionType(EPermissionType.ANNUAL_LEAVE)
                .permissionState(EPermissionState.APPROVED)
                .beginDate(LocalDate.of(2025, 7, 1))
                .endDate(LocalDate.of(2025, 7, 10))
                .description("Yaz tatili izni")
                .build());

        permissions.add(Permission.builder()
                .userId(11L)
                .permissionType(EPermissionType.UNPAID_LEAVE)
                .permissionState(EPermissionState.PENDING)
                .beginDate(LocalDate.of(2025, 10, 15))
                .endDate(LocalDate.of(2025, 10, 20))
                .description("Kişisel nedenlerle ücretsiz izin talebi")
                .build());

        // EmployeeInformation ID: 21 için izinler
        permissions.add(Permission.builder()
                .userId(21L)
                .permissionType(EPermissionType.MANDATORY)
                .permissionState(EPermissionState.APPROVED)
                .beginDate(LocalDate.of(2025, 5, 5))
                .endDate(LocalDate.of(2025, 5, 6))
                .description("Zorunlu devlet izni")
                .build());

        // EmployeeInformation ID: 31 için izinler
        permissions.add(Permission.builder()
                .userId(31L)
                .permissionType(EPermissionType.ANNUAL_LEAVE)
                .permissionState(EPermissionState.REJECTED)
                .beginDate(LocalDate.of(2025, 8, 10))
                .endDate(LocalDate.of(2025, 8, 25))
                .description("Yıllık izin talebi reddedildi")
                .build());

        permissions.add(Permission.builder()
                .userId(31L)
                .permissionType(EPermissionType.UNPAID_LEAVE)
                .permissionState(EPermissionState.APPROVED)
                .beginDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 5))
                .description("Taşınma nedeniyle ücretsiz izin")
                .build());

        permissions.add(Permission.builder()
                .userId(22L)
                .permissionType(EPermissionType.UNPAID_LEAVE)
                .permissionState(EPermissionState.PENDING)
                .beginDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 5))
                .description("Taşınma nedeniyle ücretsiz izin")
                .build());

        permissions.add(Permission.builder()
                .userId(33L)
                .permissionType(EPermissionType.UNPAID_LEAVE)
                .permissionState(EPermissionState.APPROVED)
                .beginDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 5))
                .description("Taşınma nedeniyle ücretsiz izin")
                .build());

        permissions.add(Permission.builder()
                .userId(44L)
                .permissionType(EPermissionType.UNPAID_LEAVE)
                .permissionState(EPermissionState.PENDING)
                .beginDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 3, 5))
                .description("Taşınma nedeniyle ücretsiz izin")
                .build());

        return permissions;
    }
}
