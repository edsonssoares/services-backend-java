package com.soulcode.services.Services;


import com.soulcode.services.Models.Cargo;
import com.soulcode.services.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

    // está extendendo o jpa
    @Cacheable("cargosCache")
    public List<Cargo> mostrarTodosCargos() {

        return cargoRepository.findAll();
    }

    @Cacheable(value = "cargosCache", key = "#idCargo")
    public Cargo mostrarUmCargoPeloId(Integer idCargo) {
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }

    // Objeto do tipo Cargo cargo
    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo cadastrarCargo(Cargo cargo) {
        // só por precaução nós vamos colocar o id do cargo como nulo porque o id está automático
        cargo.setIdCargo(null);
        return cargoRepository.save(cargo);
    }

    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo editarCargo (Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    @CacheEvict(value = "cargosCache", key = "#idCargo", allEntries = true)
    public void excluirCargo(Integer idCargo) {
        cargoRepository.deleteById(idCargo);
    }




}
