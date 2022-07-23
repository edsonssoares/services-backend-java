package com.soulcode.services.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFile {

    // uploadDir - é o caminho onde será salvo o arquivo
    // fileName - Nome que será salvo o arquivo
    // MultipartFile - qualquer formato de arquivo pode subir
    // adicionou o throws por que pode dar erro
    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {

        // instaciamos o objeto uploadPath
        // Paths.get(uploadDir - fazendo a leitura do caminho
        Path uploadPath = Paths.get(uploadDir);

        // ! - existe esse caminho? create - se não, eu vou criar
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // InputStream - fluxo de dados - tentar fazer a leitura do arquivo que estamos queremos subir / inputStream - objeto instaciado
        // aqui vamos tentar fazer o upload do arquivo
        // file.getInputStream() - faz a leitura byte por byte do arquivo
        try(InputStream inputStream = file.getInputStream()) {

            // arquivo subindo
            // nesse momento o arquivo é salvo no diretório que passamos na assinatura do método
            // filePath - pode ser qualquer nome - caminho do arquivo
            // Files - é uma classe
            // REPLACE_EXISTING - substitui o arquivo se ele já existe
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new IOException("Não foi possível enviar o seu arquivo");
        }

    }
}
