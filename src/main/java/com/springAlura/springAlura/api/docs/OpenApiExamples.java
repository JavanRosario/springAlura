package com.springAlura.springAlura.api.docs;

public final class OpenApiExamples {

	private OpenApiExamples() {
	}

	public static final String ERRO_VALIDACAO_JSON = """
			{
			    "type": "https://api.com.br",
			    "title": "Dados inválidos",
			    "status": 400,
			    "detail": "Um ou mais campos estão incorretos. Faça o preenchimento correto.",
			    "instance": "/recurso",
			    "properties": {
			        "fields": [
			            {
			                "fieldName": "campoObrigatorio",
			                "detail": "O campo informado é obrigatório e não pode ser nulo."
			            },
			            {
			                "fieldName": "campoNumerico",
			                "detail": "O valor deve ser maior ou igual a zero."
			            }
			        ]
			    }
			}
			""";

	public static final String ERRO_NOT_FOUND_JSON = """
			{
			    "type": "https://api.com.br",
			    "title": "Recurso não encontrado",
			    "status": 404,
			    "detail": "O registro solicitado não foi encontrado na base de dados.",
			    "instance": "/recurso/1",
			    "properties": {
			        "timestamp": 1786619160432
			    }
			}""";

	public static final String ERRO_OPERACAO_JSON = """
			{
			    "type": "https://api.com.br",
			    "title": "Falha na operação",
			    "status": 400,
			    "detail": "Não foi possível concluir a ação. Verifique se o ID está correto ou se o registro possui dependências.",
			    "instance": "/recurso/1",
			    "properties": {
			        "timestamp": 1786623273703
			    }
			}""";

	public static final String ERRO_INTERNO_JSON = """
			{
			    "type": "https://api.com.br",
			    "title": "Erro interno no servidor",
			    "status": 500,
			    "detail": "Ocorreu um erro inesperado no sistema. Tente novamente mais tarde.",
			    "instance": "/recurso",
			    "properties": {
			        "timestamp": 1786619160432
			    }
			}""";
}
