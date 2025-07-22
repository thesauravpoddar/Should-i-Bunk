package com.should_i_bunk.should_i_bunk.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@MappedSuperclass
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    // This class can be used to define common fields for all entities, such as createdAt, updatedAt, etc.
    // Currently, it is empty but can be extended in the future if needed.
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(name = "ENABLED_AT")
    private LocalDateTime createdAt;

    @Column(name = "LASTMODIFIEDDATE")
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(name = "CREATED_BY" , nullable = false, updatable = false)
    private String createdBy;
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY" , insertable = false)
    private String lastModifiedBy;

}
