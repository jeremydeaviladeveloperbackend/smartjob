package org.smartjob.dao;

import org.smartjob.dao.entities.Phone;
import org.smartjob.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de acceso a datos para la entidad Phone.
 * <p>
 * Esta interfaz extiende JpaRepository proporcionando operaciones CRUD básicas
 * y métodos personalizados para consultas específicas de teléfonos.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Repository
public interface PhoneDao extends JpaRepository<Phone, UUID> {

}
