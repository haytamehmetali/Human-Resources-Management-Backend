package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.*;

import java.time.*;
import java.util.*;

public class ShiftInitializer {
    public static List<Shift> shiftInitializer() {
        return List.of(
                Shift.builder()
                        .companyId(1L)
                        .assignedUserIds(Set.of(11L))
                        .capacity(2)
                        .shiftName("Evening Shift")
                        .beginHour(LocalTime.of(16, 0))
                        .endHour(LocalTime.of(23, 59))
                        .description("Güne geç başlayanlar, mesai 16:00'da başlıyor❗")
                        .build(),

                Shift.builder()
                        .companyId(1L)
                        .assignedUserIds(Set.of(21L, 31L))
                        .capacity(5)
                        .shiftName("Night Shift")
                        .beginHour(LocalTime.of(0, 0))
                        .endHour(LocalTime.of(7, 59))
                        .description("Gece çalışmayı sevenler, mesai 00:00'da başlıyor❗")
                        .build(),

                Shift.builder()
                        .companyId(2L)
                        .assignedUserIds(Set.of(22L, 32L, 42L))
                        .capacity(10)
                        .shiftName("Night Shift")
                        .beginHour(LocalTime.of(0, 0))
                        .endHour(LocalTime.of(7, 59))
                        .description("Gece çalışmayı sevenler, mesai 00:00'da başlıyor❗")
                        .build(),

                Shift.builder()
                        .companyId(3L)
                        .assignedUserIds(Set.of(13L, 23L, 33L, 43L))
                        .capacity(4)
                        .shiftName("Night Shift")
                        .beginHour(LocalTime.of(0, 0))
                        .endHour(LocalTime.of(7, 59))
                        .description("Gece çalışmayı sevenler, mesai 00:00'da başlıyor❗")
                        .build(),

                Shift.builder()
                        .companyId(4L)
                        .assignedUserIds(Set.of(14L, 44L))
                        .capacity(10)
                        .shiftName("Night Shift")
                        .beginHour(LocalTime.of(0, 0))
                        .endHour(LocalTime.of(7, 59))
                        .description("Gece çalışmayı sevenler, mesai 00:00'da başlıyor❗")
                        .build(),

                Shift.builder()
                        .companyId(7L)
                        .assignedUserIds(Set.of(37L, 47L))
                        .capacity(3)
                        .shiftName("Night Shift")
                        .beginHour(LocalTime.of(0, 0))
                        .endHour(LocalTime.of(7, 59))
                        .description("Gece çalışmayı sevenler, mesai 00:00'da başlıyor❗")
                        .build()
        );
    }
}
