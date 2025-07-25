package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.dto.response.*;
import java.util.*;

public class PublicApiInitializer {
    public static HomePageContentResponseDto homePageContent() {
        return new HomePageContentResponseDto(
                "Welcome to QuickHR",
                "Fast, Reliable, Effective Human Resources Solutions",
                List.of(
                        "7/24 Live Support",
                        "User Friendly Interface",
                        "Easy Personal Management",
                        "Automatic Tracking (Holidays/Permission)"
                )
        );
    }

    public static PlatformFeaturesResponseDto platformFeatures() {
        return new PlatformFeaturesResponseDto(List.of(
                "Automated Holidays & Permission Calculation",
                "EmployeeInformation Attendance Tracking",
                "Detailed Reporting",
                "Cloud-Based Access"
        ));
    }

    public static HowItWorksResponseDto howItWorks() {
        return new HowItWorksResponseDto(
                "How the Platform Works",
                "QuickHR centralizes your HR data and simplifies your operations with real-time access to cloud-based features."
        );
    }
}
