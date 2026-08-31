package com.springAlura.springAlura.api.error;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.springAlura.springAlura.domain.exception.EntidadeNulaNoPayloadException;
import com.springAlura.springAlura.domain.exception.SenhaNaoConcideComAtualException;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExcepitionHandler extends ResponseEntityExceptionHandler {

	private static final String DOMINIO = "www.serieApi.com.br";

	@Autowired
	private MessageSource messageSource;

//	@ExceptionHandler(IllegalArgumentException.class)
//	public ProblemDetail handlePropertuReferenceException(IllegalArgumentException e) {
//		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
//
//		String msgOriginal = e.getMessage() != null ? e.getMessage() : "";
//		String msg = "O valor enviado para o parâmetro é inválido.";
//
//		if (msgOriginal.contains("Page Index") || msgOriginal.contains("Page size")) {
//			msgOriginal = "O número da página (index) deve ser maior que 0(zero)";
//
//		}
//
//		problemDetail.setType(URI.create(DOMINIO));
//		problemDetail.setDetail(msgOriginal);
//		problemDetail.setTitle(msg);
//		problemDetail.setProperty("timestamp", System.currentTimeMillis());
//
//		return problemDetail;
//
//	}

	@ExceptionHandler(EntidadeNulaNoPayloadException.class)
	public ProblemDetail handleSerieNaoEncontradaException(EntidadeNulaNoPayloadException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

		problemDetail.setType(URI.create(DOMINIO));
		problemDetail.setTitle("No seu recurso contém uma entidade nula.. ");
		problemDetail
				.setDetail("No objeto do recurso que você está trabalhando, contém um campo nulo, por favor, confira.");
		problemDetail.setProperty("timestamp", System.currentTimeMillis());

		return problemDetail;
	}

	@ExceptionHandler(SerieNaoEncontradaException.class)
	public ProblemDetail handleSerieNaoEncontradaException(SerieNaoEncontradaException e) {
		log.warn("Falha na requisição {}", e.getMessage());
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

		problemDetail.setType(URI.create(DOMINIO));
		problemDetail.setTitle("Série não encontrada");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}

	@ExceptionHandler(SenhaNaoConcideComAtualException.class)
	public ProblemDetail handleSerieNaoEncontradaException(SenhaNaoConcideComAtualException e) {
		log.warn("Falha na requisição {}", e.getMessage());
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

		problemDetail.setType(URI.create(DOMINIO));
		problemDetail.setTitle("Senha não coincide com a atual");
		problemDetail.setDetail("A senha fornecida não coincide com a senha atual. Por favor, verifique!");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.warn("Falha na requisição {]", e.getMessage());
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

		problemDetail.setType(URI.create(DOMINIO));
		problemDetail.setTitle("Recurso não pode ser excluído.");
		problemDetail.setDetail(
				"Recurso não pode ser excluído. O recurso está sendo usado por outras relações, exclua essas relações primeiro");
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ProblemDetail detail = ex.getBody();

		detail.setStatus(status.value());
		detail.setType(URI.create(DOMINIO));
		detail.setTitle("Dados inválidos");
		detail.setDetail("Um ou mais campos estão incorretos. Faça o preenchimento certo");

		List<Map<String, String>> invalidFiels = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {

			String menssagemTraduzida = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

			return Map.of("fieldName", fieldError.getField(), "detail",
					menssagemTraduzida != null ? menssagemTraduzida : "Campo Inválido");

		}).toList();
		List<String> fieldNames = invalidFiels.stream().map(f -> f.get("fieldName")).toList();
		log.warn("Falha de validação na requisição. Campos incorretos: {}", fieldNames);

		detail.setProperty("fields", invalidFiels);
		detail.setProperty("timestamp", Instant.now());

		return ResponseEntity.status(status).body(detail);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

		problemDetail.setType(URI.create(DOMINIO));
		problemDetail.setTitle("O seu formato de data está errado! Por favor, corrija-o");
		problemDetail.setProperty("timestamp", System.currentTimeMillis());
		problemDetail
				.setDetail("Houve um erro no parse do JSON, confira a sua data, se está no formato certo xx/xx/xxxx");

		return ResponseEntity.status(status).body(problemDetail);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setType(URI.create(DOMINIO));
		problemDetail.setTitle("O recurso não foi encontrado, garanta que a URI está escrita corretamente!");
		problemDetail.setProperty("timestamp", System.currentTimeMillis());
		problemDetail.setDetail(
				"Endereço não encontrado: Verifique se há erros de digitação, letras maiúsculas incorretas ou caracteres extras na URI. Se o erro persistir, limpe o cache com Ctrl + F5 e tente novamente.");

		return ResponseEntity.status(status).body(problemDetail);
	}

}
