package com.app.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends BaseEntity {

    private String title;
    private String description;
    private int duration;
    private String posterUrl;
}
