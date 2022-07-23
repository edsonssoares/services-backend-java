package com.soulcode.services.Repositories;

import com.soulcode.services.Models.Chamado;
import com.soulcode.services.Models.Cliente;
import com.soulcode.services.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

   // serviços não existem no Jpa
   List<Chamado> findByCliente(Optional<Cliente> cliente);

   List<Chamado> findByFuncionario(Optional<Funcionario> funcionario);

   // SELECT trouxe do mysql
   @Query(value = "SELECT * FROM chamado WHERE status =:status",nativeQuery = true)
   List<Chamado> findByStatus(String status);

   @Query(value = "SELECT * FROM chamado WHERE data_entrada BETWEEN :data1 AND :data2",nativeQuery = true)
   List<Chamado> findyByIntervaloData(Date data1, Date data2);

}