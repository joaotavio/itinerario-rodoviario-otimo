package controle;

import org.jpl7.JPLException;
import org.jpl7.Query;

public class Prolog {

    public void iniciar(){
        Query q = new Query("consult('dados.pl')");
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
            System.out.println(q.hasSolution());
        } catch (JPLException e){
            return false;
        }
        return true;
    }

    public String listarCidades() {
        return "";
    }

    public String gerarItinerario(String origem, String destino, int piso, String criterio) {
        return "";
    }
}
