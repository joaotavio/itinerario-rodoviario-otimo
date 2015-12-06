maior_velocidade(120).
menor_pedagio(7).

%Quanto maior velocidade maior o consumo de gasolina
consumo(Velocidade, Consumo) :-
	Consumo is 1 + (Velocidade/100).

listar_cidade(Lista) :-
	setof(X, A^B^C^Y^dados_via(A, X, Y, B, C), Lista1),
	setof(X, A^B^C^Y^dados_via(A, Y, X, B, C), Lista2),
	union(Lista1, Lista2, Lista).

heuristica_distancia(Origem, Destino, Reta):-
	reta(Origem, Destino, Reta), !.
heuristica_distancia(Origem, Destino, Reta):-
	!, reta(Destino, Origem, Reta).

heuristica_tempo(Origem, Destino, H) :-
	maior_velocidade(MaiorVel),
	heuristica_distancia(Origem, Destino, Reta),
	H is Reta/MaiorVel.

heuristica_custo(Origem, Destino, H) :-
	menor_pedagio(MenorPedagio),
	heuristica_distancia(Origem, Destino, Reta),
	H is  MenorPedagio + 2*(Reta/20).

heuristica(Origem, Destino, 'C1', H) :-
	heuristica_distancia(Origem, Destino, H).
heuristica(Origem, Destino, 'C2', H) :-
	heuristica_tempo(Origem, Destino, H).
heuristica(Origem, Destino, 'C3', H) :-
	heuristica_custo(Origem, Destino, H).



funcao_tempo(Origem, Destino, Tempo, Piso) :-
	dados_via(_, Destino, Origem, Dist, caracteristicas(Piso, _, Vel)),
	Tempo is Dist/Vel.
funcao_tempo(Origem, Destino, Tempo, Piso) :-
	dados_via(_, Origem, Destino, Dist, caracteristicas(Piso, _, Vel)),
	Tempo is Dist/Vel.

funcao_custo(Origem, Destino, Custo, Piso) :-
	dados_via(_, Destino, Origem, Dist, caracteristicas(Piso, Pedagio, Vel)),
	consumo(Vel, Consumo),
	Custo is Pedagio + 2*Consumo*(Dist/20).
funcao_custo(Origem, Destino, Custo, Piso) :-
	dados_via(_, Origem, Destino, Dist, caracteristicas(Piso, Pedagio, Vel)),
	consumo(Vel, Consumo),
	Custo is Pedagio + 2*Consumo*(Dist/20).


funcaoG(Origem, Destino, 'C1', Piso, G) :-
	retorna_distancia(Origem, Destino, G, Piso2),
	Piso2 >= Piso.
funcaoG(Origem, Destino, 'C2', Piso, G) :-
	funcao_tempo(Origem, Destino, G, Piso2),
	Piso2 >= Piso.
funcaoG(Origem, Destino, 'C3', Piso, G) :-
	funcao_custo(Origem, Destino, G, Piso2),
	Piso2 >= Piso.



retorna_distancia(A, B, D, Piso) :-
	dados_via(_, A, B, D, caracteristicas(Piso, _, _)).
retorna_distancia(A, B, D, Piso) :-
	dados_via(_, B, A, D, caracteristicas(Piso, _, _)).


calc_dist_custo_tempo([_], 0, 0, 0).
calc_dist_custo_tempo([A, B], D, C, T):- retorna_dist_custo_tempo(A, B, D, C, T),!.
calc_dist_custo_tempo([A, B|Y], Dist, Custo, Tempo):-
	calc_dist_custo_tempo([B|Y], DistResto, CustoResto, TempoResto),
	retorna_dist_custo_tempo(A, B, Dist2, Custo2, Tempo2),
	Custo is Custo2 + CustoResto,
	Dist is Dist2 + DistResto,
	Tempo is Tempo2 + TempoResto.

retorna_dist_custo_tempo(A, B, Distancia, Custo, Tempo) :-
	dados_via(_, A, B, Distancia, caracteristicas(_, Pedagio, Velocidade)),
	consumo(Velocidade, Consumo),
	Custo is Pedagio + 2*Consumo*(Distancia/20),
	Tempo is Distancia/Velocidade.
retorna_dist_custo_tempo(A, B, Distancia, Custo, Tempo) :-
	dados_via(_, B, A, Distancia, caracteristicas(_, Pedagio, Velocidade)),
	consumo(Velocidade, Consumo),
	Custo is Pedagio + 2*Consumo*(Distancia/20),
	Tempo is Distancia/Velocidade.

pai(Cidade, [[Cidade, Custo, Pai] | _], [Cidade, Custo, Pai]) :- !.
pai(Cidade, [_|Resto], Pai) :- pai(Cidade, Resto, Pai).

reconstruirCaminho(_, [Cidade, _, null], [Cidade]).
reconstruirCaminho(Fechados, Vertice, [Cidade|Resto]) :-
	[Cidade, _, Pai] = Vertice,
	pai(Pai, Fechados, PaiNovo),
	reconstruirCaminho(Fechados, PaiNovo, Resto).


encontrarCaminho(Origem, Destino, Piso, 'C1', Caminho, Distancia, Custo, Tempo) :-
	criterioX(Origem, Destino, Piso, 'C1', Caminho, Distancia),
	calc_dist_custo_tempo(Caminho, _, Custo, Tempo), !.

encontrarCaminho(Origem, Destino, Piso, 'C2', Caminho, Distancia, Custo, Tempo) :-
	criterioX(Origem, Destino, Piso, 'C2', Caminho, Tempo),
	calc_dist_custo_tempo(Caminho, Distancia, Custo, _), !.

encontrarCaminho(Origem, Destino, Piso, 'C3', Caminho, Distancia, Custo, Tempo) :-
	criterioX(Origem, Destino, Piso, 'C3', Caminho, Custo),
	calc_dist_custo_tempo(Caminho, Distancia, _, Tempo), !.


%Busca o menor caminho
criterioX(Origem, Destino, Piso, Criterio, Caminho, Total):-
	a_estrela([[Origem, 0, null]], [], Destino, Piso, Criterio, Fechados), %inicia apenas com origem.
	last(Fechados, Ultimo),
	reconstruirCaminho(Fechados, Ultimo, CaminhoInvertido),
	reverse(CaminhoInvertido, Caminho),
	[_,Total,_] = Ultimo.

% Quando n�o exite caminho
criterioX(_, _, _, _, _, _):- fail.


%a_estrela(Abertos, Fechados, Destino, Caminho).
% Abertos e Fechados � uma lista de v�rtices
%[Vertice, Custo, Pai]

%caso de Abertos ser vazio, n�o tem solu��o
a_estrela([], _, _, _, _, _) :- fail.

%caso de achar o destino
a_estrela(Abertos, Fechados, Destino, Piso, Criterio, Caminho) :-
	melhor_vertice(Abertos, V, Destino, Criterio),
	nth0(0, V, X), %pega a cidade
        ( X == Destino -> %achou caminho
	 append(Fechados, [V], Fechado2),
	 Caminho = Fechado2;
	%else
	delete(Abertos, V, Abertos2),
	append(Fechados, [V], Fechados2),
	atualizarAdjacentes(Abertos2, Fechados2, V, Piso, Criterio, NovoAbertos),
	a_estrela(NovoAbertos, Fechados2, Destino, Piso, Criterio, Caminho)).


atualizarAdjacentes(Abertos, Fechados, [Cidade, Custo, _], Piso, Criterio, NovoAbertos) :-
	findall(
	    [Cidade2, S, Cidade],
	    (funcaoG(Cidade, Cidade2, Criterio, Piso, Distancia),
	     S is Distancia+Custo, custo(Cidade2, Abertos, CustoAntigo), S < CustoAntigo,
	     not(member([Cidade2, _, _], Fechados))),
	    Adjacentes),
	deletarRepetidos(Abertos, Adjacentes, Abertos2),
	union(Abertos2, Adjacentes, NovoAbertos).


deletarRepetidos(Abertos, [], Abertos) :- !.
deletarRepetidos(Abertos, [[Cidade, _, _]|Resto], Novo) :-
	delete(Abertos, [Cidade,_,_], Abertos2),
	deletarRepetidos(Abertos2, Resto, Novo).


custo(_, [], 100000000) :- !.
custo(Cidade, [[Cidade, Custo, _] | _], Custo) :- !.
custo(Cidade, [_|Resto], Custo) :- custo(Cidade, Resto, Custo).


%Retorna o melhor v�rtice
melhor_vertice([X],X,_,_):- !.
%Caso: Pegar o caminho 1
melhor_vertice([[Cidade1,Custo1|Resto1],[Cidade2, Custo2|_]|Resto], MelhorVertice, Destino, Criterio):-
	heuristica(Cidade1, Destino, Criterio, Heuristica1),
	heuristica(Cidade2, Destino, Criterio, Heuristica2),
	Heuristica1 +  Custo1 =< Heuristica2 +  Custo2,
	melhor_vertice([[Cidade1,Custo1|Resto1]|Resto], MelhorVertice, Destino, Criterio).

%Caso: Pegar o caminho 2
melhor_vertice([[Cidade1,Custo1|_],[Cidade2,Custo2|Resto2]|Resto], MelhorVertice, Destino, Criterio):-
	heuristica(Cidade1, Destino, Criterio, Heuristica1),
	heuristica(Cidade2, Destino, Criterio, Heuristica2),
	Heuristica1  + Custo1 > Heuristica2 +  Custo2,
	melhor_vertice([[Cidade2,Custo2|Resto2]|Resto], MelhorVertice, Destino, Criterio).


salvarArquivo(Caminho, Conteudo) :-
	open(Caminho, write, Arquivo),
	current_output(Antigo),
	set_output(Arquivo),
	write(Conteudo),
	close(Arquivo),
	set_output(Antigo).

%---------------------------
%  TESTES COM OS CRITERIOS
%---------------------------

:- use_module(library(plunit)).

:- begin_tests(casos_base).

%===================================%
%  TESTE BASE DE ORIGEM == DESTINO  %
%===================================%

% Com Origem e Destino iguais, nao importa a rodovia, os resultados
% serao 0 para qualquer resultado
test(base1) :- encontrarCaminho(X, X, _, 'C2', [X], 0, 0, 0).
test(base2, [fail]):- encontrarCaminho(X, 'Mandaguari', 1, 'C1', [X | _], 0, 0, 0).

% existe rodovia com qualidade
test(base3) :- encontrarCaminho(X, Y, 1, 'C1', [X, Y], _, _, _).
test(base4) :- encontrarCaminho(X, Y, 2, 'C1', [X, Y], _, _, _).

%caminho comeca com origem
test(base5) :- encontrarCaminho(X, _, 1, 'C3', [X | _], _, _, _).
test(base6, [fail]) :- encontrarCaminho(X, _, 1, 'C3', [_, X | _], _, _, _).



:- end_tests(casos_base).

:- begin_tests(criterio_distancia).
%Iremos testar as distancias e caminhos
test(dist1) :- encontrarCaminho('Mandaguari', 'Astorga', 1, 'C1', ['Mandaguari', 'Marialva', 'Sarandi', 'Maringa', 'Astorga'], 80, _, _).
test(dist2, [fail]) :- encontrarCaminho('Mandaguari', 'Curitiba', 2, 'C1', ['Mandaguari', 'Jandaia', 'Cambira', 'Apucarana', 'Ponta Grossa', 'Curitiba'], 410, _, _). %fail porque a qualidade deveria ser 1 ou caminho por arapongas ate apucarana
test(dist3, Caminho == ['Cascavel', 'Guarapuava', 'Ponta Grossa']) :- encontrarCaminho('Cascavel', 'Ponta Grossa', 3, 'C1', Caminho, 409, _, _).
test(dist4, D == 55) :- encontrarCaminho('Cambira', 'Maringa', 2, 'C1',['Cambira', 'Jandaia', 'Mandaguari', 'Marialva', 'Sarandi', 'Maringa'], D, _, _).
test(dist5, [fail]) :- encontrarCaminho('Londrina', 'Itambe', 3, 'C1', _, 128, 126.715,  _). %Custo = 238, ou qualidade menor

:- end_tests(criterio_distancia).
%Os tempos estarao em varias casas decimais por causa da media


:- begin_tests(criterio_tempo).
test(tempo1) :- encontrarCaminho('Sarandi', 'Curitiba', 2, 'C2', ['Sarandi', 'Marialva', 'Mandaguari', 'Arapongas', 'Apucarana', 'Ponta Grossa', 'Curitiba'], _, _, 5.84968253968254).
test(tempo2, [fail]) :- encontrarCaminho('Arapongas', 'Pato Branco', 3, 'C2', _, 644, 330.5389999999999, 6.545054474249522). %Menor tempo para estrada de piso 2.
test(tempo3, T == 1.9597222222222221) :- encontrarCaminho('Paranavai', 'Campo Mourao', 1, 'C2', ['Paranavai', 'Maringa', 'Campo Mourao'], 165, 62.44, T).

:- end_tests(criterio_tempo).


:- begin_tests(criterio_custo).
test(custo1, [fail]) :- encontrarCaminho('Paranavai', 'Pato Branco', 3, 'C3', ['Paranavai', 'Umuarama', 'Toledo', 'Cascavel', 'Pato Branco'], 558, 226.22500000000002, 5.5565124933545995). %custo deveria ser menor que 3
test(custo2, Caminho == ['Cambe', 'Arapongas', 'Mandaguari', 'Marialva', 'Sarandi', 'Maringa', 'Campo Mourao', 'Toledo']) :- encontrarCaminho('Cambe', 'Toledo', 3, 'C3', Caminho, _, 195.16, _).
test(custo3, C == 145.05) :- encontrarCaminho('Umuarama', 'Apucarana', 3, 'C3',['Umuarama', 'Campo Mourao', 'Maringa', 'Sarandi', 'Marialva', 'Mandaguari', 'Arapongas', 'Apucarana'], 281, C, _).
test(custo4, D == 69) :- encontrarCaminho('Marialva', 'Astorga', 3, 'C3', _, D, 60.455, _).
test(custo5) :- encontrarCaminho('Apucarana', 'Cambira', 1, 'C3', ['Apucarana','Cambira'], _, _, _).
test(custo6, [fail]) :- encontrarCaminho('Apucarana', 'Cambira', 2, 'C3',['Apucarana', 'Cambira'], _, _, _). %Caminho deveria ser Apuca->Arap->Mandag->Jandaia->Cambira

:- end_tests(criterio_custo).
