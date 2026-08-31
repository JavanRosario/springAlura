package com.springAlura.springAlura.api.docs;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Séries", description = "Endpoints para gerenciamento do catálogo de séries")
public interface SwaggerSerieController {

	@Operation(summary = "Cria uma nova série", description = "Informe os dados para salvar uma nova série")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Série criada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados de entrada inválidos! Verifique os campos!", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_VALIDACAO_JSON))) })
	SerieResponseDto salvar(
			@RequestBody(description = "Dados necessários para a criação da série", required = true) SerieRequestDto dto);

	@Operation(summary = "Lista todas as séries", description = "Lista todas as séries cadastradas no banco de dados de forma irrestrita")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Séries encontradas com sucesso!"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao processar a consulta", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_INTERNO_JSON))) })
	List<SerieResponseDto> listar();

	@Operation(summary = "Busca uma série baseada no ID", description = "Informe o ID numérico da série")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Série encontrada com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Série não encontrada com o ID fornecido", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_NOT_FOUND_JSON))) })
	SerieResponseDto listarPorId(@Parameter(description = "ID numérico da série", example = "1") Long serieId);

	@Operation(summary = "Filtra séries dinamicamente", description = "Busca séries aplicando filtros opcionais combinados no banco de dados")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Filtro processado com sucesso!"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao processar os filtros", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_INTERNO_JSON))),
			@ApiResponse(responseCode = "400", description = "Requisição Inválida", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_VALIDACAO_JSON))) })
	Page<SerieResponseDto> listarComFiltros(@ParameterObject SerieFiltroRequestDto filtros,
			@ParameterObject Pageable pageable);

	@Operation(summary = "Atualiza os campos de uma série baseado no ID", description = "Informe o ID na URL e o novo corpo da série no JSON para realizar a atualização completa.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Série atualizada com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Série não encontrada com o ID fornecido", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_NOT_FOUND_JSON))),
			@ApiResponse(responseCode = "400", description = "Dados de entrada inválidos! Verifique os campos!", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_VALIDACAO_JSON))) })
	ResponseEntity<SerieResponseDto> atualizar(
			@Parameter(description = "ID numérico da série a ser atualizada", example = "1") Long serieId,
			@RequestBody(description = "Novos dados da série", required = true) SerieRequestDto serieRequestDto);

	@Operation(summary = "Deleta uma série baseada no ID", description = "Informe o ID da série para apagá-la da base de dados")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Série deletada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Série não encontrada para exclusão", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_NOT_FOUND_JSON))),
			@ApiResponse(responseCode = "400", description = "Erro ao deletar a Série, verifique o ID", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_OPERACAO_JSON))) })
	void excluir(@Parameter(description = "ID numérico da série a ser excluída", example = "1") Long serieId);

	@Operation(summary = "Ativa uma série no catálogo")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Série ativada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Requisição inválida", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_NOT_FOUND_JSON))) })
	void ativarSerie(@Parameter(description = "ID da série a ser ativada", example = "1") Long serieId);

	@Operation(summary = "Desativa uma série no catálogo")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Série desativada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Requisição inválida", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = OpenApiExamples.ERRO_NOT_FOUND_JSON))) })
	void desativarSerie(@Parameter(description = "ID da série a ser desativada", example = "1") Long serieId);
}
