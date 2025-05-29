package lt.asirtu.manoFilmuSvetaine.dao;

import lt.asirtu.manoFilmuSvetaine.entity.Role;
import lt.asirtu.manoFilmuSvetaine.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Roles name);
}
