package com.mindu.go.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user")
@Table(name="user")
public class UserEntity {
    @Id
    @Column(name="user_id")
    private String userid;
    private String password;
    private String email;
    private String type;
    private String role;
}
