package com.safetrust.borrow_service.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String tracking;

    @Column(name="created_date", updatable = false)
    @CreatedDate
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name="created_inventory_id")
    private Inventory created_inventory;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Borrow> borrows;
}
