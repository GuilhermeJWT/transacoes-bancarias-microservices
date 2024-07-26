package br.com.systemsgs.userservice.service.impl;

import br.com.systemsgs.userservice.dto.ModelUsuariosDTO;
import br.com.systemsgs.userservice.exception.UsuarioNaoEncontradoException;
import br.com.systemsgs.userservice.model.ModelUsuarios;
import br.com.systemsgs.userservice.repository.UsuariosRepository;
import br.com.systemsgs.userservice.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuariosRepository usuariosRepository;
    private final ModelMapper mapper;

    public UsuarioServiceImpl(UsuariosRepository usuariosRepository, ModelMapper mapper) {
        this.usuariosRepository = usuariosRepository;
        this.mapper = mapper;
    }

    @Override
    public ModelUsuarios pesquisaPorId(Long id) {
        return usuariosRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException());
    }

    @Override
    public List<ModelUsuarios> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    @Override
    public ModelUsuarios salvarUsuarios(ModelUsuariosDTO modelUsuariosDTO) {
        return usuariosRepository.save(mapper.map(modelUsuariosDTO, ModelUsuarios.class));
    }
}
