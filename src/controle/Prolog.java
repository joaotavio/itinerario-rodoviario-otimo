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
        Query q = new Query("cidade(_, X)");
        ArrayList<String> cidades = new ArrayList<>();
        String resposta;
        while (q.hasMoreSolutions()){
            resposta = q.nextSolution().get("X").toString();
            resposta = resposta.replace("'", "");
            cidades.add(resposta);
        }
        return cidades.toArray(new String[cidades.size()]);
    }

    public String gerarItinerario(String origem, String destino, int piso, String criterio) {
        return "";
    }
}
