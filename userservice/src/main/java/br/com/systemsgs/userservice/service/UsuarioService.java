package br.com.systemsgs.userservice.service;

import br.com.systemsgs.userservice.dto.ModelUsuariosDTO;
import br.com.systemsgs.userservice.model.ModelUsuarios;

import java.util.List;

public interface UsuarioService {

    ModelUsuarios pesquisaPorId(Long id);

    List<ModelUsuarios> listarUsuarios();

    ModelUsuarios salvarUsuarios(ModelUsuariosDTO modelUsuariosDTO);
}
