package com.soulcode.services.Controllers;


import com.soulcode.services.Models.Endereco;
import com.soulcode.services.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("services")

public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/enderecos")
    public List<Endereco> mostrarTodosEnderecos() {
        List<Endereco> enderecos = enderecoService.mostrarTodosEnderecos();
        return enderecos;
    }

    @GetMapping("/enderecos/{idEndereco}")
    public ResponseEntity<Endereco> mostrarUmEnderecoPeloId(@PathVariable Integer idEndereco) { // abriu o método
        Endereco endereco = enderecoService.mostrarUmEnderecoPeloId(idEndereco); // método criado
        return ResponseEntity.ok().body(endereco); // serviço mapeado
    }

    @PostMapping("/enderecos/{idCliente}")
    public ResponseEntity<Endereco> cadastrarEndereco(@PathVariable Integer idCliente, @RequestBody Endereco endereco) {

        try {
            endereco = enderecoService.cadastrarEndereco(endereco, idCliente); // cadastrado

            URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(endereco.getIdEndereco()).toUri();
            return ResponseEntity.created(novaURI).body(endereco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/enderecos/{idEndereco}")
    public ResponseEntity<Endereco> editarEndereco(@PathVariable Integer idEndereco, @RequestBody Endereco endereco) {
        endereco.setIdEndereco(idEndereco);
        enderecoService.editarEndereco(endereco, idEndereco);
        return ResponseEntity.ok().body(endereco);
    }

//    @DeleteMapping("/enderecos/{idEndereco}")
//    public ResponseEntity<Void> excluirEndereco(@PathVariable Integer idEndereco) {
//        enderecoService.excluirEndereco(idEndereco);
//        return ResponseEntity.noContent().build();
//    }


}
