package org.smartjob.dao;

import org.smartjob.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de acceso a datos para la entidad User.
 * <p>
 * Esta interfaz extiende JpaRepository proporcionando operaciones CRUD básicas
 * y métodos personalizados para consultas específicas de usuarios.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Repository
public interface UserDao extends JpaRepository<User, UUID> {
    
    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar
     * @return Optional con el usuario si existe, vacío si no se encuentra
     */
    Optional<User> findByEmail(String email);
}
