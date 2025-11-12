package org.smartjob.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entidad JPA que representa la tabla PHONES en la base de datos.
 * <p>
 * Esta clase mapea la estructura de la tabla de teléfonos, incluyendo
 * la relación con la entidad User mediante una relación Many-to-One.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "PHONES")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "NUMBER", nullable = false, length = 20)
    private String number;

    @Column(name = "CITY_CODE", length = 10)
    private String citycode;

    @Column(name = "COUNTRY_CODE", length = 10)
    private String countrycode;

}