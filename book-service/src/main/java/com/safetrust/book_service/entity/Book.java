package com.safetrust.book_service.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.safetrust.book_service.status.EBookStatus;

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

@Entity(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String author;

    @Column
    private String genre;

    @Column(name = "borrowed_total")
    private Integer borrowedTotal;

    @Column
    private String code;

    @Column
    @Enumerated(EnumType.STRING)
    private EBookStatus status;

    @Column(name = "borrowed_date", updatable = false)
    @CreatedDate
    private Date borrowedDate;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
}
