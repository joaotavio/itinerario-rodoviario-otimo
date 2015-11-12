%dados_via(cod, orig, dest, dist, caracter(piso, pedagio, velmedia)).

%Mandaguari
dados_via(1, 'Mandaguari', 'Maringa', 35, caracteristicas(4, 5, 120)).
dados_via(2, 'Mandaguari', 'Marialva', 12, caracteristicas(4, 5, 110)).
dados_via(3, 'Mandaguari', 'Jandaia', 8, caracteristicas(3, 0, 90)).
dados_via(4, 'Mandaguari', 'Londrina', 72, caracteristicas(4, 5, 110)).

%Maringa
dados_via(5, 'Maringa', 'Londrina', 100, caracteristicas(4, 10, 80)).
dados_via(6, 'Maringa', 'Marialva', 22, caracteristicas(3, 0, 90)).
dados_via(7, 'Maringa', 'Sarandi', 10, caracteristicas(3, 0, 75)).
dados_via(8, 'Maringa', 'Curitiba', 420, caracteristicas(3, 30, 100)).

%Curitiba
dados_via(9, 'Curitiba', 'Campo Mourao', 455, caracteristicas(3, 17, 87)).
dados_via(10, 'Curitiba', 'Apucarana', 365, caracteristicas(3, 24, 70)).
dados_via(11, 'Curitiba', 'Toledo', 540, caracteristicas(2, 42, 50)).
dados_via(12, 'Curitiba', 'Guarapuava', 255, caracteristicas(2, 10, 70)).
dados_via(13, 'Curitiba', 'Bom Sucesso', 407, caracteristicas(1, 35, 70)).



itinerario(Origem, Destino) :-
	dados_via(_, Origem, Destino, _, _);
	dados_via(_, Destino, Origem, _, _).
