package com.soulcode.services.Services; // para criação do CRUD

// quando se fala em serviços, estamos falando dos métodos do crud da table

import com.soulcode.services.Models.Cargo;
import com.soulcode.services.Models.Funcionario;
import com.soulcode.services.Repositories.CargoRepository;
import com.soulcode.services.Repositories.FuncionarioRepository;
import com.soulcode.services.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.services.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired // injeção de depedência, autorizando FuncionarioRepository a utilizar os métodos do jpa aqui nessa class. a class fica independente
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    // primeiro serviço na table de funcionario vai ser a leitura de todos os funcionarios cadastrados
    // findAll - é um método do spring data jpa -> ele busca todos os registros de uma table.
    @Cacheable("funcionariosCache")
    public List<Funcionario> mostrarTodosFuncionarios() {

        return funcionarioRepository.findAll();
    }

    // vamos criar serviços relacionados ao funcionário criados na Interface Repository
    // criar um serviço de buscar apenas um funcionario pelo seu id (key primary)
    // Optional serve para tratar as exceções (erros) para o programar não travar!
    // O optional lança uma excessão (erro de no expection) para acaso não encontrar o endereço
    @Cacheable(value = "funcionariosCache", key = "#idFuncionario")
    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario) {

        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Funcionário não Cadastrado: " + idFuncionario)
        );

    }

    // criar mais um serviço de buscar apenas um funcionario pelo seu email
    public Funcionario mostrarUmFuncionarioPeloEmail(String email) {
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

    // esse é recomendado se não for possível uso a query
    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(Integer idCargo) {
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargo);
    }

    // vamos criar um serviço para cadastrar um novo funcionario
    // não precisa criar na Interface porque esse serviço de cadastrar já existe no jpa
    // regras de negocios são implementadas no back end
    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo) {

       try {
           //só por precaução nós vamos colocar o id do funcionario como nulo porque o id está automático
           funcionario.setIdFuncionario(null);
           Optional<Cargo> cargo = cargoRepository.findById(idCargo);
           funcionario.setCargo(cargo.get());
           return funcionarioRepository.save(funcionario);
       } catch (Exception e) {
           throw new DataIntegrityViolationException("Erro ao Cadastrar Funcionário");
       }
    }

   // vamos criar um serviço para editar um funcionario
    // não precisa criar na Interface porque esse serviço de editar já existe no jpa
    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
    public Funcionario editarFuncionario(Funcionario funcionario) {

        return funcionarioRepository.save(funcionario);
    }

    // vamos criar um serviço para excluir um funcionario
    // não precisa criar na Interface porque esse serviço de excluir já existe no jpa
    // o serviço de excluir é diferente do de cadastrar porque não tem return ele é void
    @CacheEvict(value = "funcionariosCache", key = "#idFuncionario", allEntries = true)
    public void excluirFuncionario(Integer idFuncionario) {

        funcionarioRepository.deleteById(idFuncionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto) {
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }

}
