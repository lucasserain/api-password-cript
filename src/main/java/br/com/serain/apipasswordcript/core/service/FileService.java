package br.com.serain.apipasswordcript.core.service;

import br.com.serain.apipasswordcript.core.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {


    private static  final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    @Value("${base.file.path}")
    private Resource baseLocation;
    private List<String[]> baseValues = new ArrayList<>();


    public BufferedReader getFile() throws IOException {
        return new BufferedReader(new InputStreamReader(baseLocation.getInputStream()));
    }

    public List<String[]> getValuesFromFile(BufferedReader reader) throws IOException {
        String[] list = reader.lines().distinct().map(String::trim).toArray(String[]::new);

        for(String linha: list){
            baseValues.add(linha.split("\\|"));
        }

        LOGGER.info("Leitura de : {}",list.length+ " registros.");

        return baseValues;
    }

    public void createBase(List<Usuario> usuarios) {
        File fout = new File("DataCript.txt");
        try (FileOutputStream fos = new FileOutputStream(fout); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {
            for (Usuario u : usuarios) {
                bw.write("|"+u.getNome()+"|"+u.getSenhaCriptografada()+"|");
                bw.newLine();
            }
        } catch (IOException ignored) {

        }

    }
}
