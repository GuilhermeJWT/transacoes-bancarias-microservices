package br.com.systemsgs.userservice.controller;

import br.com.systemsgs.userservice.dto.request.ModelUsuariosDTO;
import br.com.systemsgs.userservice.dto.response.UsuarioResponse;
import br.com.systemsgs.userservice.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper mapper;

    public UsuarioController(UsuarioService usuarioService, ModelMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<UsuarioResponse> pesquisaUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(usuarioService.pesquisaPorId(id), UsuarioResponse.class));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios()
                .stream().map(modelUsuarios -> mapper.map(modelUsuarios, UsuarioResponse.class)).toList());
    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid ModelUsuariosDTO modelUsuariosDTO){
        var usuarioSalvo = usuarioService.salvarUsuarios(modelUsuariosDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(usuarioService.pesquisaPorId(usuarioSalvo.getId())).toUri();

        return ResponseEntity.created(uri).body(mapper.map(usuarioSalvo, UsuarioResponse.class));
    }
}
