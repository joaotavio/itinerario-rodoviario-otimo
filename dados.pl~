%%dados_via(cod, orig, dest, dist, caracter(piso, pedagio, velmedia)).

cidade(1, 'Mandaguari').
cidade(2, 'Jandaia').
cidade(3, 'Marialva').
cidade(4, 'Sarandi').
cidade(5, 'Maringa').
cidade(6, 'Cambira').
cidade(7, 'Apucarana').
cidade(8, 'Arapongas').
cidade(9, 'Cambe').
cidade(10, 'Londrina').
cidade(11, 'Paranavai').
cidade(12, 'Campo Mourao').
cidade(13, 'Toledo').
cidade(14, 'Pato Branco').
cidade(15, 'Guarapuava').
cidade(16, 'Cascavel').
cidade(17, 'Astorga').
cidade(18, 'Umuarama').
cidade(19, 'Ponta Grossa').
cidade(20, 'Curitiba').

reta(1, 1, 0).
reta(2, 1, 10).
reta(3, 1, 11).
reta(4, 1, 20).
reta(5, 1, 27).
reta(6, 1, 13).
reta(7, 1, 23).
reta(8, 1, 28).
reta(9, 1, 49).
reta(10, 1, 57).
reta(11, 1, 92).
reta(12, 1, 91).
reta(13, 1, 248).
reta(14, 1, 317).
reta(15, 1, 209).
reta(16, 1, 241).
reta(17, 1, 30).
reta(18, 1, 168).
reta(19, 1, 234).
reta(20, 1, 324).

distancia(1, 2, 11).
distancia(2, 6, 16).
distancia(6, 7, 16).
distancia(7, 8, 17).
distancia(1, 8, 37).
distancia(8, 9, 24).
distancia(9, 10, 13).
distancia(9, 17, 52).
distancia(17, 5, 52).
distancia(5, 11, 74).
distancia(5, 4, 7).
distancia(4, 3, 10).
distancia(3, 1, 11).
distancia(7, 19, 251).
distancia(5, 18, 165).
distancia(5, 12, 91).
distancia(12, 13, 220).
distancia(18, 13, 130).
distancia(13, 16, 45).
distancia(16, 14, 234).
distancia(14, 15, 187).
distancia(15, 19, 163).
distancia(19, 20, 116).

dados_via(1, 1, 2, 11, caracteristicas(4, 0, 80)).    %Mandaguari-Jandaia
dados_via(2, 2, 6, 16, caracteristicas(2, 0, 70)).     %Jandaia-Cambira
dados_via(3, 6, 7, 16, caracteristicas(1, 0, 50)).     %Cambira-Apucarana
dados_via(4, 7, 8, 17, caracteristicas(3, 0, 70)).	   %Apucarana-Arapongas
dados_via(5, 1, 8, 37, caracteristicas(3, 0, 85)).     %Mandaguari-Arapongas
dados_via(6, 8, 9, 24, caracteristicas(3, 0, 80)).     %Arapongas-Cambe
dados_via(7, 9, 10, 13, caracteristicas(4, 8, 70)).    %Cambe-Londrina
dados_via(8, 9, 17, 52, caracteristicas(2, 5, 75)).	   %Cambe-Astorga
dados_via(9, 17, 5, 52, caracteristicas(3, 0, 100)).    %Astorga-Maringa
dados_via(10, 5, 11, 74, caracteristicas(3, 0, 90)).   %Maringa-Paranavai
dados_via(11, 5, 4, 7, caracteristicas(3, 0, 65)).     %Maringa-Sarandi
dados_via(12, 4, 3, 10, caracteristicas(3, 0, 90)).    %Sarandi-Marialva
dados_via(13, 3, 1, 11, caracteristicas(4, 7, 100)).    %Marialva-Mandaguari
dados_via(14, 7, 19, 251, caracteristicas(2, 0, 70)).  %Apucarana-Ponta Grossa
dados_via(15, 5, 18, 165, caracteristicas(3, 0, 82)).  %Maringa-Umuarama
dados_via(16, 5, 12, 91, caracteristicas(3, 10, 80)).   %Maringa-Campo Mourao
dados_via(17, 12, 13, 220, caracteristicas(1, 5, 88)). %Campo Mourao-Toledo
dados_via(18, 18, 13, 130, caracteristicas(1, 0, 100)). %Umuarama-Toledo
dados_via(19, 13, 16, 45, caracteristicas(3, 5, 95)).  %Toledo-Cascavel
dados_via(20, 16, 14, 234, caracteristicas(5, 10, 110)). %Cascavel-Pato Branco
dados_via(21, 14, 15, 187, caracteristicas(4, 8, 99)). %Pato Branco-Guarapuava
dados_via(22, 15, 19, 163, caracteristicas(3, 5, 80)). %Guarapuava-Ponta Grossa
dados_via(23, 19, 20, 116, caracteristicas(4, 8, 85)). %Ponta Grossa-Curitiba

obter_codigo(Origem, CodOr, Destino, CodDest):-
	cidade(CodOr, Origem),
	cidade(CodDest, Destino).

%Retorna a distancia RETA entre origem e destino%
%obter_reta(Origem, Destino, Reta):-
%	obter_codigo(Origem, CodOr),
%        obter_codigo(Destino, CodDest),
%	reta(CodOr, CodDest, Reta), !.

%obter_reta(Origem, Destino, Reta):-
%	!,
%	obter_codigo(Origem, CodOr),
%        obter_codigo(Destino, CodDest),
%	reta(CodDest, CodOr, Reta).

obter_reta(Origem, Destino, Reta):-
	reta(Origem, Destino, Reta), !.

obter_reta(Origem, Destino, Reta):-
	!,
	reta(Destino, Origem, Reta).

pede_origem(X) :-
	write('Digite a origem: '),
	read(X), nl, !.

pede_destino(Y) :-
	write('Digite o Destino: '),
	read(Y).

pede():-
	pede_origem(X),
	pede_destino(Y),
	encontrarCaminho(X, Y).

%Busca o menor caminho
encontrarCaminho(Origem, Destino):-
	obter_codigo(Origem, CodOr, Destino, CodDest),        %pega os codigos da cidade.
	buscaHeuristica([[0,CodOr]],CaminhoInvertido,CodDest), %inicia apenas com origem.
	inverterCaminho(CaminhoInvertido, Caminho),
	mostraCaminho(Caminho), !.

% en caso exista algun nodo isolado
encontrarCaminho(Origem, Destino):-
	write('Não existe caminho de '),
	write(Origem),
	write(' a '),
	write(Destino).

% Caminhos = [Custo, Destino | Caminho] ->
% Custo: minimo 'local',
% Destino : ultima cidade encontrada no momento
% Caminho : Caminho de Destino até Origem (precisa inverter para mostrar
% de Origem a Destino
buscaHeuristica(Caminhos, [Custo,Destino|Camino], Destino):-
	member([Custo,Destino|Camino],Caminhos),
	escogerProximo(Caminhos, [Custo1|_], Destino),
	Custo1 == Custo.


buscaHeuristica(Caminhos, Solucion, Destino):-
	escogerProximo(Caminhos, Prox, Destino),
	removerCamino(Prox, Caminhos, CaminosRestantes),
	extenderSiguienteCamino(Prox, NuevosCaminos),
	concatenarCaminhos(CaminosRestantes, NuevosCaminos, ListaCompleta),
	buscaHeuristica(ListaCompleta, Solucion, Destino).

% Obtiene todos los caminos recorridos hasta el momento
% y realiza las comparaciones. Solo se detiene cuando se encuentra
% el menor camino (menor costo)
escogerProximo([X],X,_):-!.

escogerProximo([[Custo1,Cidade1|Resto1],[Custo2,Cidade2|_]|Cola], MejorCamino, Destino):-
	obter_reta(Cidade1, Destino, Avaliacao1),
	obter_reta(Cidade2, Destino, Avaliacao2),
	Avaliacao1 +  Custo1 =< Avaliacao2 +  Custo2,
	escogerProximo([[Custo1,Cidade1|Resto1]|Cola], MejorCamino, Destino).

escogerProximo([[Custo1,Cidade1|_],[Custo2,Cidade2|Resto2]|Cola], MejorCamino, Destino):-
 obter_reta(Cidade1, Destino, Avaliacao1),
 obter_reta(Cidade2, Destino, Avaliacao2),
 Avaliacao1  + Custo1 > Avaliacao2 +  Custo2,
 escogerProximo([[Custo2,Cidade2|Resto2]|Cola], MejorCamino, Destino).


extenderSiguienteCamino([Custo,No|Caminho],NovosCaminhos):-
 findall([Custo,NovoNo,No|Caminho], (verificaDistancia(No, NovoNo,_),not(member(NovoNo,Caminho))), ListaResultante),
 actualizarCostosCaminos(ListaResultante, NovosCaminhos).

% <span id="IL_AD6" class="IL_AD">Actualizacion</span> de los costos de los caminos
actualizarCostosCaminos([],[]):-!.
actualizarCostosCaminos([[Custo,NovoNo,No|Caminho]|Cola],[[NovoCusto,NovoNo,No|Caminho]|Cauda1]):-
 verificaDistancia(No, NovoNo, Distancia),
 NovoCusto is Custo + Distancia,
 actualizarCostosCaminos(Cola,Cauda1).


verificaDistancia(Origem, Destino, Distancia):-
	distancia(Origem, Destino, Distancia).
verificaDistancia(Origem, Destino, Distancia):-
	distancia(Destino, Origem, Distancia).

inverterCaminho([X],[X]).
inverterCaminho([X|Y], Lista):-
	inverterCaminho(Y,ListaInt),
        concatenarCaminhos(ListaInt,[X],Lista).

concatenarCaminhos([],L,L).
concatenarCaminhos([X|Y],L,[X|Lista]):- concatenarCaminhos(Y,L,Lista).


removerCamino(X,[X|T],T):-!.
removerCamino(X,[Y|T],[Y|T2]):-removerCamino(X,T,T2).


mostraCidade([Distancia]):-
 nl,
 write('Menor distancia: '),
 write(Distancia),
 write(' Km.').


mostraCidade([CodCidade|Caminho]):-
 cidade(CodCidade, Cidade),
 write(Cidade),
 write(', '),
 mostraCidade(Caminho).


mostraCaminho([CodCidade|Caminho]):-
	write('O melhor caminho a percorrer: '),
	mostraCidade([CodCidade | Caminho]).





