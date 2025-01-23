package com.safetrust.user_service.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetrust.user_service.status.ETrackingUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private ETrackingUser tracking;

    @Column(name = "borrowed_total")
    private int borowedTotal;

    @Column(name="created_date",updatable = false)
    @CreatedDate
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "created_inventory_id")
    private Inventory created_inventory;
}
