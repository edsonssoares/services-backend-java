package com.soulcode.services.Services;


import com.soulcode.services.Models.Chamado;
import com.soulcode.services.Models.Cliente;
import com.soulcode.services.Models.Funcionario;
import com.soulcode.services.Models.StatusChamado;
import com.soulcode.services.Repositories.ChamadoRepository;
import com.soulcode.services.Repositories.ClienteRepository;
import com.soulcode.services.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Cacheable("chamadosCache")
    public List<Chamado> mostrarTodosChamados() {

        return chamadoRepository.findAll();
    }

    @Cacheable(value = "chamadosCache", key = "#idChamado")
    public Chamado mostrarUmChamadoPeloId(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }

    public List<Chamado> buscarChamadosPeloCliente(Integer idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    public List<Chamado> buscarChamadosPeloFuncionario(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    public List<Chamado> buscarChamadosPeloStatus(String status) {

        return chamadoRepository.findByStatus(status);
    }

    public List<Chamado> buscarPorIntervaloData(Date data1, Date data2) {
        return  chamadoRepository.findyByIntervaloData(data1, data2);
    }

    // para cadastrar uma novo chamado temos 3 regras:
    // 1 - no momento do cadastro do chamado, já devemos informar o cliente
    // 2 - no momento do cadastro do chamado, não há a necessidade de informar o funcionário
    // 3 - no momento do cadastro do chamado, o status desse chamado deve ser RECEBIDO

    // serviço para cadastro de novo chamado
    @CachePut(value = "chamadosCache", key = "#chamado.idChamado")
    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente) {
        // regra 3 - atribuição do status recebido para o chamado que está sendo cadastrado
        chamado.setStatus(StatusChamado.RECEBIDO);
        // regra 2 - informando que ainda não atribuimos esse chamado para nenhum funcionário
        chamado.setFuncionario(null);
        // regra 1 -  buscando os dados do cliente dono do chamado
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

   // método para editar um chamado
    // no momento da edição de um chamado devemos presevar os dados do cliente e do funcionário
    // vamos editar os dados do chamado, mas manteremos os dados do cliente e do funcionário
    // chamado = chamado novo
    @CachePut(value = "chamadosCache", key = "#chamado.idChamado")
    public Chamado editarChamado(Chamado chamado, Integer idChamado) {
        // instaciamos aqui um objeto do tipo Chamado para guardar os dados dos chamados sem as novas alterações (chamados antigos)
        Chamado chamadoSemAsNovasAlteracoes = mostrarUmChamadoPeloId(idChamado);
        Funcionario funcionario = chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente = chamadoSemAsNovasAlteracoes.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }

    // método para excluir um chamado
    // void não tem return
    // não preciso de retorno para o método delete
    @CacheEvict(value = "chamadosCache", key = "#idChamado", allEntries = true)
    public void excluirChamado(Integer idChamado) {
        chamadoRepository.deleteById(idChamado);
    }

    // método para atribruir um funcionário para um determinado chamado ou substituir o funcionário
    // regra: no momento em que um determinado chamado é atribuido a um funcionário o status precisa ser alterado para ATRIBUIDO
    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario) {
        // buscar os dados do funcionário que vai ser atribuido a esse chamado
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        // buscar o chamado para o qual vai ser especificado o funcionario escolhido
        Chamado chamado = mostrarUmChamadoPeloId(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);

        return chamadoRepository.save(chamado);
    }

    // método para modificar o status de um chamado
    public Chamado modificarStatus(Integer idChamado, String status) {
        Chamado chamado = mostrarUmChamadoPeloId(idChamado);

        if (chamado.getFuncionario() != null) {

            switch (status) {
                case "ATRIBUIDO":
                {
                    chamado.setStatus(StatusChamado.ATRIBUIDO);
                    break;
                }
                case "CONCLUIDO":
                {
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;
                }
                case "ARQUIVADO":
                {
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;
                }
            }
        }

        switch (status) {
            case "RECEBIDO":
            {
                chamado.setStatus(StatusChamado.RECEBIDO);
                break;
            }
        }

        return chamadoRepository.save(chamado);

    }

}

