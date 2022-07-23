package com.soulcode.services.Controllers; // end point - mapeando os serviços

import com.soulcode.services.Models.Chamado;
import com.soulcode.services.Services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("services")
public class ChamadoController {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping("/chamados")
    public List<Chamado> mostrarTodosChamados() {
        List<Chamado> chamados = chamadoService.mostrarTodosChamados();
        return chamados;
    }

    @GetMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> mostrarUmChamadoPeloId(@PathVariable Integer idChamado) {
        Chamado chamado = chamadoService.mostrarUmChamadoPeloId(idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    @GetMapping("/chamadosPeloCliente/{idCliente}")
    public List<Chamado> buscarChamadosPeloCliente(@PathVariable Integer idCliente) {
        List<Chamado> chamados = chamadoService.buscarChamadosPeloCliente(idCliente);
        return chamados;
    }

    @GetMapping("/chamadosPeloFuncionario/{idFuncionario}")
    public List<Chamado> buscarChamadosPeloFuncionario(@PathVariable Integer idFuncionario) {
        List<Chamado> chamados = chamadoService.buscarChamadosPeloFuncionario(idFuncionario);
        return chamados;
    }

    @GetMapping("/chamadosPeloStatus")
    public List<Chamado> buscarChamadosPeloStatus(@RequestParam("status") String status) {
        List<Chamado> chamados = chamadoService.buscarChamadosPeloStatus(status);
        return chamados;
    }

    @GetMapping("/chamadosPorIntervaloData")
    public List<Chamado> buscarPorIntervaloData(@RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1,
                                                @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data2) {
        List<Chamado> chamados = chamadoService.buscarPorIntervaloData(data1, data2);
        return chamados;
    }

    // aqui vamos definir o endpoint para o serviço de cadastro de um novo chamado
    // para cadastro precisamos anotar como método http - post
    // fromCurrentRequest().path("/{id}") = /chamados/{idCliente}
    @PostMapping("/chamados/{idCliente}")
    public ResponseEntity<Chamado> cadastrarChamado(@PathVariable Integer idCliente, @RequestBody Chamado chamado) {
        chamado = chamadoService.cadastrarChamado(chamado, idCliente);
        // nesse momento o chamado já foi cadastrado no database
        // precisamos agora criar o caminho (path) (uri) para que esse novo chamado possa ser acessado
        // build - não mostra os dados no postman quando criado
        // body - mostra os dados no postman quando criado
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chamado.getIdChamado()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);
    }

    //vamos mapear o serviço de excluir um chamado
    // como vou deletar não preciso ver o chamado excluido
    @DeleteMapping("/chamados/{idChamado}")
    public ResponseEntity<Void> excluirChamado(@PathVariable Integer idChamado) {
        chamadoService.excluirChamado(idChamado);
        return ResponseEntity.noContent().build();
    }

    // vamos mapear o serviço de editar um chamado
    // para edição precisamos do método http do tipo put
    @PutMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> editarChamado(@PathVariable Integer idChamado, @RequestBody Chamado chamado) {
        chamado.setIdChamado(idChamado);
        chamadoService.editarChamado(chamado, idChamado);
        return ResponseEntity.ok().build(); // é o que aparece no postman
    }

    // vamos fazer o mapeamento do método de atribuir um funcionario a um determinado chamado
    // estamos editando também
    @PutMapping("chamadosAtribuirFuncionario/{idChamado}/{idFuncionario}")
    public ResponseEntity<Chamado> atribuirFuncionario(@PathVariable Integer idChamado, @PathVariable Integer idFuncionario) {
        chamadoService.atribuirFuncionario(idChamado, idFuncionario);
        return ResponseEntity.noContent().build();
    }

    // vamos construir o mapeamento do método para modificar o status
    @PutMapping("/chamadosModificarStatus/{idChamado}")
    public ResponseEntity<Chamado> modificarStatus(@PathVariable Integer idChamado, @RequestParam("status") String status) {
        chamadoService.modificarStatus(idChamado, status);
        return ResponseEntity.noContent().build();
    }

}
