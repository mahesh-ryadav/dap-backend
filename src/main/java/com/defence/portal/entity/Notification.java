package com.defence.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===== BASIC INFO ===== */
    @Column(nullable = false)
    private String examName;

    private String conductingAuthority;
    private String status;
    private LocalDate notificationDate;

    /* ===== IMPORTANT DATES ===== */
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private LocalDate examDate;
    private LocalDate admitCardDate;
    private LocalDate resultDate;

    /* ===== OVERVIEW ===== */
    @Lob
    private String overview;

    /* ===== ELIGIBILITY ===== */
    @Lob
    private String educationalQualification;

    private Integer minAge;
    private Integer maxAge;

    @Lob
    private String ageRelaxation;

    private String nationality;

    /* ===== VACANCY ===== */
    private Integer totalVacancies;

    @Lob
    private String vacancyDetails;

    /* ===== APPLICATION FEE ===== */
    private Double feeGeneral;
    private Double feeScSt;
    private String paymentMode;

    /* ===== DYNAMIC LIST DATA ===== */

    @ElementCollection
    @CollectionTable(
            name = "notification_selection_process",
            joinColumns = @JoinColumn(name = "notification_id")
    )
    @Column(name = "step")
    private List<String> selectionProcess;

    @ElementCollection
    @CollectionTable(
            name = "notification_exam_pattern",
            joinColumns = @JoinColumn(name = "notification_id")
    )
    @Column(name = "pattern")
    private List<String> examPattern;

    @ElementCollection
    @CollectionTable(
            name = "notification_syllabus",
            joinColumns = @JoinColumn(name = "notification_id")
    )
    @Column(name = "topic")
    private List<String> syllabus;

    @ElementCollection
    @CollectionTable(
            name = "notification_physical_standards",
            joinColumns = @JoinColumn(name = "notification_id")
    )
    @Column(name = "standard")
    private List<String> physicalStandards;

    /* ===== IMPORTANT LINKS ===== */
    private String applyOnlineLink;
    private String officialNotificationPdf;
    private String officialWebsite;

    /* ===== METADATA ===== */
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
