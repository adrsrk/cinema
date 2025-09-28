package com.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hall")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hall extends BaseEntity {

    private String name;

    private int rows;

    private int seatsPerRow;
}
