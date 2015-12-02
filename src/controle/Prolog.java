package controle;

import org.jpl7.*;

import java.util.ArrayList;

public class Prolog {

    public void iniciar(){
        Query q = new Query("consult('dados.pl')");
        q.hasSolution();
    }

    public void salvarEmArquivo(String caminho, String conteudo) {
    }

    public boolean carregarArquivo(String caminho) {
        caminho = caminho.replace("\\", "/");
        if (!caminho.endsWith(".pl")) {
            return false;
        }
        try {
            Query q = new Query("consult('"+caminho+"')");
            q.hasSolution();
        } catch (JPLException e){
            return false;
        }
        return true;
    }

    public String[] listarCidades() {
        Query q = new Query("listar_cidade(X)");
        q.hasMoreSolutions();

        Term t = q.nextSolution().get("X");

        int tamLista = t.listLength();
        String lista[] = new String[tamLista];

        for (int i = 0; i < tamLista; i++) {
            lista[i] = t.arg(1).toString().replace("'", "");
            t = t.arg(2);
        }

        return lista;
    }

    public String[] listarVias(){
        Query q = new Query("dados_via(_, X, Y, Dist, caracteristicas(Piso, Pedagio, Velocidade))");

        ArrayList<String> lista = new ArrayList<>();
        int i = 0;
        while(q.hasMoreSolutions()) {
            lista.add(q.nextSolution().toString());
            System.out.println(lista.get(i++));
        }

        return lista.toArray(new String[lista.size()]);
    }

    public String gerarItinerario(String origem, String destino, int piso, String criterio) {
        return "";
    }
}
