package com.soulcode.services.Services;


import com.soulcode.services.Models.Cliente;
import com.soulcode.services.Repositories.ClienteRepository;
import com.soulcode.services.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    // aqui fizemos a injeção de dependência
    @Autowired
    ClienteRepository clienteRepository;

    // findAll (método do Spring Data) - busca todos os registros
    //CacheConfig.java
    @Cacheable("clientesCache") // só chama o return se o cache expirar clientesCache::[]
    public List<Cliente> mostrarTodosClientes() {
         return clienteRepository.findAll();
    }

    // findAll (método do Spring Data) - busca um cliente especifico pelo seu id
    @Cacheable(value = "clientesCache", key = "#idCliente") // clientesCache::1
    public Cliente mostrarUmClientePeloId(Integer idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return cliente.orElseThrow(
                () -> new EntityNotFoundException("Cliente não Cadastrado: " + idCliente)
        );
    }

    public Cliente mostrarUmClientePeloEmail(String email) {
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        return cliente.orElseThrow();
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente cadastrarCliente(Cliente cliente) {
        // por precaução vamos limpar o campo de id do cliente com null
        cliente.setIdCliente(null);
        return clienteRepository.save(cliente);
    }

    // editar um cliente já cadastrado
    // atualizar(substituir) a info no cache de acordo com a key
   @CachePut(value = "clientesCache", key = "#cliente.idCliente") // SPEL
    public Cliente editarCliente(Cliente cliente) {
        mostrarUmClientePeloId(cliente.getIdCliente());
        return clienteRepository.save(cliente); // faz o cache desse retorno
    }

    // deleteById - exclui um cliente pelo seu id
    @CacheEvict(value = "clienteCache", key = "#idCliente", allEntries = true)
    public void excluirCliente(Integer idCliente) {
        mostrarUmClientePeloId(idCliente);
        clienteRepository.deleteById(idCliente);
    }


}
