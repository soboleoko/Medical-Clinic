package com.soboleoko.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long patientId;
    private Long doctorId;
}