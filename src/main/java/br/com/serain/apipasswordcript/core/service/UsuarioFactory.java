package br.com.serain.apipasswordcript.core.service;

import br.com.serain.apipasswordcript.core.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioFactory {

    public static List<Usuario> geraUsuarios(List<String[]> valuesFromFile){
        List<Usuario> usuarios = new ArrayList<>();
        valuesFromFile.forEach(v -> {
            usuarios.add(Usuario.builder()
                    .nome(v[1])
                    .senha(v[2])
                    .build());
        });

        return usuarios;
    }
}
