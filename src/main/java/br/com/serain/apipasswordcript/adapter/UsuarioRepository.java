package br.com.serain.apipasswordcript.adapter;

import br.com.serain.apipasswordcript.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
