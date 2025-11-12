package org.smartjob.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Entidad JPA que representa la tabla USERS en la base de datos.
 * <p>
 * Esta clase mapea la estructura de la tabla de usuarios, incluyendo
 * información personal, credenciales, y metadatos de auditoría.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED")
    private Instant created;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "MODIFIED")
    private Instant modified;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "LAST_LOGIN")
    private Instant lastLogin;

    @ColumnDefault("TRUE")
    @Column(name = "ISACTIVE")
    private Boolean isactive;

    @Column(name = "TOKEN", nullable = false)
    private UUID token;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Phone> phones = new ArrayList<>();

}