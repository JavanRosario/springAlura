package com.springAlura.springAlura.api.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

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
public interface SwaggerDocControllers {

	@Operation(summary = "Cria uma nova série", description = "Informe os dados para salvar uma nova série")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Série criada com sucesso", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Sucesso", summary = "Exemplo de quando o POST é feito com sucesso", value = OpenApiExamples.SUCESSO_POST_JSON))),
			@ApiResponse(responseCode = "400", description = "Dados de entrada inválidos! Verifique os campos!", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Campos Inválidos", summary = "Exemplo de erro quando os dados enviados no JSON falham na validação", value = OpenApiExamples.ERRO_VALIDACAO_JSON))) })
	SerieResponseDto salvar(
			@RequestBody(description = "Dados necessários para a criação da série", required = true) SerieRequestDto dto);

	@Operation(summary = "Lista todas as séries.", description = "Lista todas as séries cadastradas no banco de dados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Séries encontradas com sucesso!", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Sucesso!", summary = "Exemplo quando o GET é retornado com sucesso", value = OpenApiExamples.SUCESSO_GET_JSON))),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor ao processar a consulta", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Erro Interno", summary = "Exemplo de erro inesperado no sistema", value = OpenApiExamples.ERRO_GET_JSON))) })
	List<SerieResponseDto> listar();

	@Operation(summary = "Atualiza os campos de uma série baseado no ID", description = "Informe o ID na URL e o novo corpo da série no JSON para realizar a atualização completa.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Série atualizada com sucesso!", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Sucesso!", summary = "Exemplo de quando o PUT é feito com sucesso", value = OpenApiExamples.SUCESSO_JSON))),
			@ApiResponse(responseCode = "404", description = "Série não encontrada com o ID fornecido"),
			@ApiResponse(responseCode = "400", description = "Dados de entrada inválidos! Verifique os campos!", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Campos Inválidos", summary = "Exemplo de erro quando os dados enviados no JSON falham na validação", value = OpenApiExamples.ERRO_VALIDACAO_JSON))) })
	ResponseEntity<SerieResponseDto> atualizar(
			@Parameter(description = "ID numérico da série a ser atualizada", example = "1") Long serieId,
			@RequestBody(description = "Novos dados da série", required = true) SerieRequestDto serieRequestDto);

	@Operation(summary = "Deleta uma série baseada no ID", description = "Informe o ID da série para apagá-la da base de dados")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Série deletada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Série não encontrada para exclusão"),
			@ApiResponse(responseCode = "400", description = "Erro ao deletar a Série, verifique o ID", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Falha", summary = "Exemplo quando há um erro no deletar", value = OpenApiExamples.ERRO_DELETE_JSON))) })
	void excluir(@Parameter(description = "ID numérico da série a ser excluída", example = "1") Long serieId);
}
