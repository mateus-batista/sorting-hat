package br.ufsc.bridge.sortinghat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufsc.bridge.sortinghat.model.Aluno;
import br.ufsc.bridge.sortinghat.model.Casa;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	Aluno findByEmail(String email);

	List<Aluno> findByCasa(Casa casa);
}
