package com.defence.portal.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class NotificationDto {

    /* ===== BASIC INFO ===== */

    @NotBlank(message = "Exam name is required")
    private String examName;

    @NotBlank(message = "Conducting authority is required")
    private String conductingAuthority;

    @NotBlank(message = "Status is required")
    private String status;   // UPCOMING / ACTIVE / CLOSED

    private LocalDate notificationDate;

    /* ===== IMPORTANT DATES ===== */

    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private LocalDate examDate;
    private LocalDate admitCardDate;
    private LocalDate resultDate;

    /* ===== OVERVIEW ===== */

    @NotBlank(message = "Overview is required")
    private String overview;

    /* ===== ELIGIBILITY ===== */

    @NotBlank(message = "Educational qualification is required")
    private String educationalQualification;

    @Min(value = 18, message = "Minimum age must be at least 18")
    private Integer minAge;

    @Min(value = 18, message = "Maximum age must be at least 18")
    private Integer maxAge;

    private String ageRelaxation;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    /* ===== VACANCY ===== */

    @Min(value = 0, message = "Total vacancies cannot be negative")
    private Integer totalVacancies;

    private String vacancyDetails;

    /* ===== APPLICATION FEE ===== */

    @DecimalMin(value = "0.0", inclusive = true, message = "General fee cannot be negative")
    private Double feeGeneral;

    @DecimalMin(value = "0.0", inclusive = true, message = "SC/ST fee cannot be negative")
    private Double feeScSt;

    private String paymentMode;

    /* ===== DYNAMIC LIST DATA ===== */

    @NotEmpty(message = "Selection process must contain at least one step")
    private List<@NotBlank(message = "Selection step cannot be blank") String> selectionProcess;

    @NotEmpty(message = "Exam pattern must contain at least one item")
    private List<@NotBlank(message = "Exam pattern item cannot be blank") String> examPattern;

    @NotEmpty(message = "Syllabus must contain at least one topic")
    private List<@NotBlank(message = "Syllabus topic cannot be blank") String> syllabus;

    private List<@NotBlank(message = "Physical standard cannot be blank") String> physicalStandards;

    /* ===== IMPORTANT LINKS ===== */

    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Apply online link must be a valid URL"
    )
    private String applyOnlineLink;

    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Notification PDF link must be a valid URL"
    )
    private String officialNotificationPdf;

    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Official website must be a valid URL"
    )
    private String officialWebsite;
}
