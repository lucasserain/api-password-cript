package br.com.serain.apipasswordcript;

import br.com.serain.apipasswordcript.adapter.UsuarioRepository;
import br.com.serain.apipasswordcript.core.model.Usuario;
import br.com.serain.apipasswordcript.core.service.AutenticacaoService;
import br.com.serain.apipasswordcript.core.service.FileService;
import br.com.serain.apipasswordcript.core.service.UsuarioFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootApplication
public class ApiPasswordCriptApplication  implements ApplicationRunner {

	@Autowired
	FileService fileService;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	AutenticacaoService autenticacaoService;

	public static void main(String[] args) {
		SpringApplication.run(ApiPasswordCriptApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception, NoSuchAlgorithmException, UnsupportedEncodingException {
		BufferedReader reader = fileService.getFile();

		List<Usuario> usuarios = UsuarioFactory.geraUsuarios(fileService.getValuesFromFile(reader));
		final long startCript = System.currentTimeMillis();
		usuarios.forEach(Usuario::criptografarSenha);
		System.out.println("Tempo decorrido para criptografar as senhas: "+(System.currentTimeMillis() - startCript) +"ms");

		usuarioRepository.saveAll(usuarios);

		final long startAuthNormal = System.currentTimeMillis();
		autenticacaoService.autenticarUsuario(usuarios);
		System.out.println("Tempo decorrido para autenticação normal: "+(System.currentTimeMillis() - startAuthNormal) +"ms");

		final long startAuthCripto = System.currentTimeMillis();
		autenticacaoService.autenticarUsuarioCriptografado(usuarios);
		System.out.println("Tempo decorrido para autenticação criptografada: "+(System.currentTimeMillis() - startAuthCripto) +"ms");

		fileService.createBase(usuarios);

		System.out.println("jooj");

	}
}
