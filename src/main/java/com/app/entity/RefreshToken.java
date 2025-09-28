package com.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String token;

    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
