
INSERT INTO spring_alura.usuario(nome,email,senha) VALUES('JAVAN','JAVAN@GMAIL.COM',  'D1SA23');
INSERT INTO spring_alura.usuario(nome,email,senha) VALUES('RODRIGO','RODRIGO@GMAIL.COM',  'D1A2W3');
INSERT INTO spring_alura.usuario(nome,email,senha) VALUES('LUCAS','LUCAS@GMAIL.COM',  'D1SA23');
INSERT INTO spring_alura.usuario(nome,email,senha) VALUES('ELIAS','ELIAS@GMAIL.COM',  'WD1Q263');

INSERT INTO spring_alura.streaming(data_na_plataforma,esta_na_plataforma,usuario_id,usuario_ativo) VALUES('2005-05-05',true,  1,true);



INSERT INTO spring_alura.categoria(categoria) VALUES('DRAMA');
INSERT INTO spring_alura.categoria(categoria) VALUES('TERROR');
INSERT INTO spring_alura.categoria(categoria) VALUES('AÇÃO');
INSERT INTO spring_alura.categoria(categoria) VALUES('SI-FI');
INSERT INTO spring_alura.categoria(categoria) VALUES('COMÉDIA');
INSERT INTO spring_alura.categoria(categoria) VALUES('AVENTURA');
INSERT INTO spring_alura.categoria(categoria) VALUES('FICÇÃO');
INSERT INTO spring_alura.categoria(categoria) VALUES('GUERRA');





INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Breaking Bad', 5, 9.5, 'Bryan Cranston, Aaron Paul', 'poster001.jpg', 'Um professor de quimica se volta para o crime.', '2008-01-20', 1);
--INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Twin Peaks', 3, 8.8, 'Kyle MacLachlan, Michael Ontkean', 'poster037.jpg', 'Investigacao do assassinato de Laura Palmer.', '1990-04-08 00:00:00', 2);

INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Breaking Bad', 5, 9.5, 'Bryan Cranston, Aaron Paul', 'poster001.jpg', 'Um professor de quimica se volta para o crime.', '2008-01-20 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Stranger Things', 4, 8.7, 'Winona Ryder, David Harbour', 'poster002.jpg', 'Criancas enfrentam forcas sobrenaturais.', '2016-07-15 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Game of Thrones', 8, 9.2, 'Emilia Clarke, Kit Harington', 'poster003.jpg', 'Familias nobres disputam o Trono de Ferro.', '2011-04-17 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Chernobyl', 1, 9.4, 'Jared Harris, Stellan Skarsgard', 'poster004.jpg', 'A historia do pior desastre nuclear do mundo.', '2019-05-06 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Wire', 5, 9.3, 'Dominic West, Idris Elba', 'poster005.jpg', 'O cenario das drogas em Baltimore.', '2002-06-02 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Avatar: The Last Airbender', 3, 9.3, 'Dee Bradley Baker, Zach Tyler', 'poster006.jpg', 'O Avatar precisa dominar os quatro elementos.', '2005-02-21 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Sopranos', 6, 9.2, 'James Gandolfini, Lorraine Bracco', 'poster007.jpg', 'A vida de um chefe da mafia em terapia.', '1999-01-10 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Sherlock', 4, 9.1, 'Benedict Cumberbatch, Martin Freeman', 'poster008.jpg', 'O detetive classico nos dias modernos.', '2010-07-25 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Fullmetal Alchemist: Brotherhood', 1, 9.1, 'Kent Williams, Iemasa Kayumi', 'poster009.jpg', 'Irmaos alquimistas buscam a Pedra Filosofal.', '2009-04-05 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Office', 9, 9.0, 'Steve Carell, Jenna Fischer', 'poster010.jpg', 'O cotidiano comico de uma empresa de papel.', '2005-03-24 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Better Call Saul', 6, 9.0, 'Bob Odenkirk, Rhea Seehorn', 'poster011.jpg', 'A transformacao de Jimmy McGill em Saul Goodman.', '2015-02-08 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Attack on Titan', 4, 9.1, 'Yuki Kaji, Yui Ishikawa', 'poster012.jpg', 'A humanidade luta contra titas gigantes.', '2013-04-07 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Mandalorian', 3, 8.7, 'Pedro Pascal, Carl Weathers', 'poster013.jpg', 'Um cacador de recompensas no universo Star Wars.', '2019-11-12 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Severance', 1, 8.7, 'Adam Scott, Zach Cherry', 'poster014.jpg', 'Funcionarios dividem memorias cirurgicamente.', '2022-02-18 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Succession', 4, 8.9, 'Brian Cox, Jeremy Strong', 'poster015.jpg', 'A disputa pelo controle de um imperio de midia.', '2018-06-03 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Last of Us', 1, 8.8, 'Pedro Pascal, Bella Ramsey', 'poster016.jpg', 'Jornada de sobrevivencia em mundo pos-apocaliptico.', '2023-01-15 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Fargo', 5, 8.9, 'Billy Bob Thornton, Martin Freeman', 'poster017.jpg', 'Antologia de crimes reais cheia de humor negro.', '2014-04-15 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('True Detective', 4, 8.9, 'Matthew McConaughey, Woody Harrelson', 'poster018.jpg', 'Investigacoes policiais complexas e sombrias.', '2014-01-12 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Death Note', 1, 8.9, 'Mamoru Miyano, Kappei Yamaguchi', 'poster019.jpg', 'Estudante encontra caderno que causa mortes.', '2006-10-04 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Boys', 4, 8.7, 'Karl Urban, Jack Quaid', 'poster020.jpg', 'Grupo tenta derrubar super-herois corruptos.', '2019-07-26 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Friends', 10, 8.9, 'Jennifer Aniston, Courteney Cox', 'poster021.jpg', 'A rotina de seis amigos em Nova York.', '1994-09-22 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Black Mirror', 6, 8.7, 'Daniel Lapaine, Hannah John-Kamen', 'poster022.jpg', 'O lado sombrio da tecnologia moderna.', '2011-12-04 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Dark', 3, 8.7, 'Louis Hofmann, Karoline Eichhorn', 'poster023.jpg', 'Segredos e viagens no tempo em uma cidade alema.', '2017-12-01 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('The Crown', 6, 8.6, 'Claire Foy, Olivia Colman', 'poster024.jpg', 'A historia do reinado da Rainha Elizabeth II.', '2016-11-04 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('House of the Dragon', 2, 8.5, 'Matt Smith, Emma D Arcy', 'poster025.jpg', 'A ascensão e queda da dinastia Targaryen.', '2022-08-21 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Peaky Blinders', 6, 8.8, 'Cillian Murphy, Paul Anderson', 'poster026.jpg', 'Uma gangue familiar na Inglaterra de 1919.', '2013-09-12 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Narcos', 3, 8.8, 'Wagner Moura, Boyd Holbrook', 'poster027.jpg', 'A ascensão dos carteis de droga na Colombia.', '2015-08-28 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Fleabag', 2, 8.7, 'Phoebe Waller-Bridge, Sian Clifford', 'poster028.jpg', 'Uma mulher tenta lidar com o luto em Londres.', '2016-07-21 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento) VALUES ('Ted Lasso', 3, 8.8, 'Jason Sudeikis, Hannah Waddingham', 'poster029.jpg', 'Treinador americano gerencia time de futebol ingles.', '2020-08-14 00:00:00');
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Mindhunter', 2, 8.6, 'Jonathan Groff, Holt McCallany', 'poster030.jpg', 'Agentes do FBI entrevistam serial killers.', '2017-10-13 00:00:00',8);
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('The Queen s Gambit', 1, 8.6, 'Anya Taylor-Joy, Bill Camp', 'poster031.jpg', 'Orfa prodígio do xadrez luta contra vicios.', '2020-10-23 00:00:00',7);
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('The Bear', 3, 8.6, 'Jeremy Allen White, Ebon Moss-Bachrach', 'poster032.jpg', 'Chef de alta gastronomia assume lanchonete falida.', '2022-06-23 00:00:00', 6);
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Rome', 2, 8.7, 'Kevin McKidd, Ray Stevenson', 'poster033.jpg', 'A transição de Roma de Republica para Imperio.', '2005-08-28 00:00:00', 5);
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Mad Men', 7, 8.7, 'Jon Hamm, Elisabeth Moss', 'poster034.jpg', 'Agencias de publicidade em Nova York nos anos 60.', '2007-07-19 00:00:00', 4);
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Six Feet Under', 5, 8.7, 'Peter Krause, Michael C. Hall', 'poster035.jpg', 'O cotidiano de uma família dona de funeraria.', '2001-06-03 00:00:00', 3);
INSERT INTO spring_alura.serie (titulo, total_temporada, avaliacao, atores, poster, sinopse, data_lancamento, categoria_id) VALUES ('Arrested Development', 5, 8.7, 'Jason Bateman, Michael Cera', 'poster036.jpg', 'Uma família rica perde tudo e tenta se reestruturar.', '2003-11-02 00:00:00', 2);


INSERT INTO spring_alura.series_streamings(serie_id, streaming_Id) VALUES(1,1);
