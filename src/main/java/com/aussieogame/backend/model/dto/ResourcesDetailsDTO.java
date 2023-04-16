package com.aussieogame.backend.model.dto;

import com.aussieogame.backend.model.dao.enumeration.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourcesDetailsDTO {
    private OperationType operationType;
    private Long dollars;
    private Long eucalyptus;
    private LocalDateTime operationDate;
}
