package com.soulcode.services.Repositories;

import com.soulcode.services.Models.Cargo;
import com.soulcode.services.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository; // tras 13 métodos: ddl - dml - dql

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    // FuncionarioRepository está autorizado a utilizar os métodos do jpa
    // FuncionarioRepository é uma subclasse do jpa because usamos o extends

    Optional<Funcionario> findByEmail(String email);
    // Optional<Funcionario> findByNome - serve para procurar qualquer atributo

    // Optional<Funcionario> findByNomeAndEmail(String nome, String email);

    List<Funcionario> findByCargo(Optional<Cargo> cargo);

}
