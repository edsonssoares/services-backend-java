package com.soulcode.services.Services;


import com.soulcode.services.Models.Cliente;
import com.soulcode.services.Models.Endereco;
import com.soulcode.services.Repositories.ClienteRepository;
import com.soulcode.services.Repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable("enderecosCache")
    public List<Endereco> mostrarTodosEnderecos() {

        return enderecoRepository.findAll();
    }

    // O optional lança uma excessão (erro de no expection) para acaso não encontrar o endereço
    @Cacheable(value = "enderecosCache", key = "#idEndereco")
    public Endereco mostrarUmEnderecoPeloId(Integer idEndereco) {
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return endereco.orElseThrow();
    }

    // Cadastro de um novo endereço de um cliente
    // regras de negócio da tati:
    // 1 - para cadastrar um endereço, o cliente já deve estar cadastrado no database
    // 2 - no momento do cadastro do endereço, precisamos passar o id do cliente dono desse endereço
    // 3 - o id do endereço vai ser o mesmo id do cliente
    // 4 - não permitir que um endereço seja salvo sem a existência do respectivo cliente
    @CachePut(value = "enderecosCache", key = "#endereco.idEndereco")
    public Endereco cadastrarEndereco(Endereco endereco, Integer idCliente) throws Exception {
        endereco.setIdEndereco(null);
        // estamos declarando um Optional de cliente e atribuindo para este os dados do cliente que receberá o novo endereço
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {

            // regra 3
            endereco.setIdEndereco(idCliente);
            enderecoRepository.save(endereco);

            cliente.get().setEndereco(endereco);
            clienteRepository.save(cliente.get());
            return endereco;
        } else {
            throw new Exception();
        }
    }


    @CachePut(value = "enderecosCache", key = "#endereco.idEndereco")
    public Endereco editarEndereco(Endereco endereco, Integer idEndereco) {
        return enderecoRepository.save(endereco);
    }

//    public void excluirEndereco(Integer idEndereco) {
//        enderecoRepository.deleteById(idEndereco);
//    }


}
