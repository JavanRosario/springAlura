package com.springAlura.springAlura.api.docs;

public final class OpenApiExamples {

	private OpenApiExamples() {
	}

	public static final String ERRO_VALIDACAO_JSON = """
			{
			    "type": "https://serieApi.com.br",
			    "title": "Dados inválidos",
			    "status": 400,
			    "detail": "Um ou mais campos estão incorretos. Faça o preenchimento certo",
			    "instance": "/series",
			    "properties": {
			        "fields": [
			            {
			                "fieldName": "totalTemporada",
			                "detail": "Total de Temporada da Série deve ser maior ou igual a zero."
			            },
			            {
			                "fieldName": "titulo",
			                "detail": "Titulo da Série é obrigatório."
			            },
			            {
			                "fieldName": "avaliacao",
			                "detail": "Avaliação da Série deve ser maior ou igual a zero."
			            }
			        ]
			    }
			}
			""";

	public static final String SUCESSO_JSON = """
			{
			    "id": 1,
			    "titulo": "TESTE PUT",
			    "totalTemporada": 800,
			    "avaliacao": 800.0,
			    "atores": "TESTE PUT",
			    "poster": "TESTE PUT",
			    "sinopse": "TESTE PUT",
			    "dataLancamento": "20/01/2008"
			}
						""";
	public static final String SUCESSO_POST_JSON = """
			{
			    "id": 1,
			    "titulo": "TESTE POST",
			    "totalTemporada": 800,
			    "avaliacao": 800.0,
			    "atores": "TESTE POST",
			    "poster": "TESTE POST",
			    "sinopse": "TESTE POST",
			    "dataLancamento": "20/01/2008"
			}
						""";
	public static final String SUCESSO_GET_JSON = """
			[
			{
			    "id": 1,
			    "titulo": "Breaking Bad",
			    "totalTemporada": 5,
			    "avaliacao": 9.5,
			    "atores": "Bryan Cranston, Aaron Paul",
			    "poster": "poster001.jpg",
			    "sinopse": "Um professor de quimica se volta para o crime.",
			    "dataLancamento": "20/01/2008"
			},
			{
			    "id": 2,
			    "titulo": "Stranger Things",
			    "totalTemporada": 4,
			    "avaliacao": 8.7,
			    "atores": "Winona Ryder, David Harbour",
			    "poster": "poster002.jpg",
			    "sinopse": "Criancas enfrentam forcas sobrenaturais.",
			    "dataLancamento": "15/07/2016"
			}
			]""";
	public static final String ERRO_GET_JSON = """
						{
			    "type": "https://serieApi.com.br",
			    "title": "O recurso não foi encontrado, garanta que a URI está escrita corretamente!",
			    "status": 404,
			    "detail": "Endereço não encontrado: Verifique se há erros de digitação, letras maiúsculas incorretas ou caracteres extras na URI. Se o erro persistir, limpe o cache com Ctrl + F5 e tente novamente.",
			    "instance": "/seriess",
			    "properties": {
			        "timestamp": 1786619160432
			    }
			}""";
	public static final String ERRO_DELETE_JSON = """
									{
			    "type": "https://seusite.com",
			    "title": "Séri não encontrada",
			    "status": 400,
			    "detail": "Não existe um cadastro de Serie com código: SEU_CODIGO_ERRADO",
			    "instance": "/series/SEU_CODIGO_ERRADO",
			    "properties": {
			        "timestamp": 1786623273703
			    }
			}""";
}
