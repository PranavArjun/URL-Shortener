package com.app.url_shortener.entities;

import com.app.url_shortener.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name="password", nullable = false, length=300)
    private String password;

    @Column(name="name", nullable = false, length=100)
    private String name;


    @Column(name = "role", nullable = false, length=20)
    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist(){
        if(role==null){
            role = Role.ROLE_USER;
        }
        if(createdAt==null){
            createdAt= Instant.now();
        }
    }
}
