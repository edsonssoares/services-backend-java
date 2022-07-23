package com.soulcode.services.Controllers; // para criação dos end points


import com.soulcode.services.Models.Funcionario;
import com.soulcode.services.Services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin // faz a comunicação entre as portas 8080 e a 4200
@RestController // estamos criando uma api do tipo rest e será responsável pelo controller da class da minha aplicação
@RequestMapping("services")

public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    // @GetMapping faz uma busca - crud
    @GetMapping("/funcionarios")
    public List<Funcionario> mostrarTodosFuncionarios() { // abriu o método
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios(); // método criado
        return funcionarios; // serviço mapeado
    }

    // /{número}
    // @PathVariable vai pela rota
    @GetMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloId(@PathVariable Integer idFuncionario) {
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario); //.ok() é para caso a requisição retorne null e não vai travar o programa
    }

    @GetMapping("/funcionariosEmail/{email}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloEmail(@PathVariable String email) {
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
        return ResponseEntity.ok().body(funcionario); // para mostrar os dados no postman
    }

    @GetMapping("/funcionariosDoCargo/{idCargo}")
    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(@PathVariable Integer idCargo) {
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionariosDeUmCargo(idCargo);
        return funcionarios;
    }

    // @PostMapping
    // @RequestBody vai enviar todos os dados do funcionario atraves do corpo da requisição
    // agora precisamos criar uma uri para esse novo registro da table
    // cadastrando funcionário já com idCargo
    // getIdFuncionario() = /{id}
    // fromCurrentRequest() = /funcionarios
    @PostMapping("/funcionarios/{idCargo}")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@PathVariable Integer idCargo, @RequestBody Funcionario funcionario) {
        funcionario = funcionarioService.cadastrarFuncionario(funcionario, idCargo); // funcionario cadastrado na table do database
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(funcionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(novaURI).body(funcionario);

    }

    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario) {
        funcionarioService.excluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario, @RequestBody Funcionario funcionario) {
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.ok().body(funcionario);
    }

}
