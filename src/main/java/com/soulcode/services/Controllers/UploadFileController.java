package com.soulcode.services.Controllers;

import com.soulcode.services.Services.FuncionarioService;
import com.soulcode.services.Util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin // comunicação entre portas diferentes
@RestController
@RequestMapping("services")
public class UploadFileController {

    @Autowired
    FuncionarioService funcionarioService;

    // RequestParam - o nome será passado através dos parametros da requisição
    @PostMapping("/funcionarios/envioFoto/{idFuncionario}")
    public ResponseEntity<Void> enviarFoto(@PathVariable Integer idFuncionario, MultipartFile file, @RequestParam("nome") String nome) throws IOException {

        String fileName = nome;
        String uploadDir = "C:/Users/Edson/Desktop/Desktop/ADS/Bootcamp SoulCode/Soul_Java/fotoFunc/";
        // posso substituir o caminho abaixo por uploadDir
        String nomeMaisCaminho = "C:/Users/Edson/Desktop/Desktop/ADS/Bootcamp SoulCode/Soul_Java/fotoFunc" + nome;


        try {
            UploadFile.saveFile(uploadDir, fileName, file);
            funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);
        }
        catch (IOException e) {
            System.out.println("O arquivo não foi enviado: " + e.getMessage());
        }
        return ResponseEntity.ok().build();

    }
}
