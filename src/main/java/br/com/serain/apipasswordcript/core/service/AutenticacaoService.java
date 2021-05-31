package br.com.serain.apipasswordcript.core.service;

import br.com.serain.apipasswordcript.adapter.UsuarioRepository;
import br.com.serain.apipasswordcript.core.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AutenticacaoService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public void autenticarUsuario(List<Usuario> usuarios){
        usuarios.forEach(u -> {
            Optional<Usuario> userFromDB = usuarioRepository.findById(u.getId());
            if(Objects.requireNonNull(userFromDB).get().getSenha().equals(u.getSenha())){
                //System.out.println("Usuario: " + u.getNome() + " Validado com sucesso!");
            }else{
                //System.out.println("Usuario: " + u.getNome() + " Senha incorreta!");
            }
        });
    }

    public void autenticarUsuarioCriptografado(List<Usuario> usuarios){
        usuarios.forEach(u -> {
            Optional<Usuario> userFromDB = usuarioRepository.findById(u.getId());
            String senhacripto = u.senhaCriptoGrafada();
            if(Objects.requireNonNull(userFromDB).get().getSenhaCriptografada().equals(senhacripto)){
                //System.out.println("Usuario: " + u.getNome() + " Validado com sucesso!");
            }else{
                //System.out.println("Usuario: " + u.getNome() + " Senha incorreta!");
            }
        });
    }


}
