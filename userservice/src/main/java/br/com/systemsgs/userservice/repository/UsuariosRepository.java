package br.com.systemsgs.userservice.repository;

import br.com.systemsgs.userservice.model.ModelUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<ModelUsuarios, Long> {

}
