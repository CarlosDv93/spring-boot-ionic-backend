package com.carlosdv93.udemy.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carlosdv93.udemy.cursomc.domain.Categoria;
import com.carlosdv93.udemy.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	/*@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat where obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest); 
	//Ambas fazem a mesma coisa, se tirar a query do de baixo, e tirar os param fazem o mesmo, se deixar a query leva o prioridade sobre o nome
	*/
	
	//Ambas fazem a mesma coisa, se tirar a query do de baixo, e tirar os param, fazem o mesmo, se deixar a @query leva o prioridade sobre o nome da classe JPA
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat where obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
}
