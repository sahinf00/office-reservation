package com.ofis.rezervasyon.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "desks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deskNumber;

    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
}
