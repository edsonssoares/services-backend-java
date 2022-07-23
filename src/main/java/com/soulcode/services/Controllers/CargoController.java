package com.soulcode.services.Controllers;

import com.soulcode.services.Models.Cargo;
import com.soulcode.services.Repositories.CargoRepository;
import com.soulcode.services.Services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("services")

public class CargoController {

    @Autowired
    CargoService cargoService;

    @GetMapping("/cargos")
    public List<Cargo> mostrarTodosCargos() {
        List<Cargo> cargos = cargoService.mostrarTodosCargos();
        return cargos;
    }

    @GetMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> mostrarUmCargoPeloId(@PathVariable Integer idCargo) {
        Cargo cargo = cargoService.mostrarUmCargoPeloId(idCargo);
        return ResponseEntity.ok().body(cargo);
    }

    @PostMapping("/cargos")
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo) {
        cargo = cargoService.cadastrarCargo(cargo);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargo.getIdCargo()).toUri();
        return ResponseEntity.created(novaURI).body(cargo);
    }

    @PutMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> editarCargo(@PathVariable Integer idCargo, @RequestBody Cargo cargo) {
        cargo.setIdCargo(idCargo);
        cargoService.editarCargo(cargo);
        return ResponseEntity.ok().body(cargo);
    }

    @DeleteMapping("/cargos/{idCargo}")
    public ResponseEntity<Void> excluirCargo(@PathVariable Integer idCargo) {
        cargoService.excluirCargo(idCargo);
        return ResponseEntity.noContent().build();
    }


}
