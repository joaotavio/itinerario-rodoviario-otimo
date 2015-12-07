package controle;

import org.jpl7.*;

import java.lang.Float;
import java.util.ArrayList;

public class Prolog {

    public boolean iniciar(){
        try {
            Query p = new Query("consult('src/prolog/rodovias.pl')");
            Query q = new Query("consult('src/prolog/algoritmo.pl')");
            p.hasSolution();
            q.hasSolution();
        } catch (JPLException e){
            return false;
        }
        return true;
    }

    public boolean salvarEmArquivo(String caminho, String conteudo) {
        caminho = caminho.replace("\\", "/");
        conteudo = conteudo.replace("'", "''");
        try {
            Query q = new Query("salvarArquivo('"+caminho+"', '"+conteudo+"')");
            q.hasSolution();
        } catch (JPLException e){
            return false;
        }
        return true;
    }

    public boolean carregarArquivo(String caminho) {
        caminho = caminho.replace("\\", "/");
        if (!caminho.endsWith(".pl")) {
            return false;
        }
        try {
            Query p = new Query("consult('"+caminho+"')");
            p.hasSolution();
        } catch (JPLException e){
            return false;
        }
        return true;
    }

    private String[] transformarEmLista(Term t){
        int tamLista = t.listLength();
        String lista[] = new String[tamLista];

        for (int i = 0; i < tamLista; i++) {
            lista[i] = t.arg(1).toString().replace("'", "");
            t = t.arg(2);
        }

        return lista;
    }

    public String[] listarCidades() {
        Query q = new Query("listar_cidade(X)");
        q.hasMoreSolutions();

        Term t = q.nextSolution().get("X");

        return transformarEmLista(t);
    }

    public String[] listarVias(){
        Query q = new Query("dados_via(_, X, Y, Dist, caracteristicas(Piso, Pedagio, Velocidade))");

        ArrayList<String> lista = new ArrayList<>();
        String cidades, piso, distancia, pedagio, velocidade, caracteristicas;
        while(q.hasMoreSolutions()) {
            cidades = "Via: " + q.nextSolution().get("X").toString() + " -- " + q.nextSolution().get("Y").toString()+"\n";
            distancia = "Distância: " + q.nextSolution().get("Dist") + "km\n";
            piso = "  Piso: " + q.nextSolution().get("Piso") + "\n";
            pedagio = "  Pedágio: R$" + q.nextSolution().get("Pedagio") + "\n";
            velocidade = "  Velocidade: " + q.nextSolution().get("Velocidade") + "km/h\n";
            caracteristicas = "Características: \n" + piso + pedagio + velocidade;
            lista.add(cidades + distancia + caracteristicas + "---------------------------------------");
        }

        return lista.toArray(new String[lista.size()]);
    }

    private String transformarEmCaminho(String lista[]){
        String caminho = lista[0];
        for (int i = 1; i < lista.length; i++){
            caminho += " -> " + lista[i];
        }
        return caminho;
    }

    public String gerarItinerario(String origem, String destino, int piso, String criterio) {
        Query q;
        String resposta, caminho, strCusto, strTempo, strDistancia;
        q = new Query("encontrarCaminho('"+origem+"', '"+destino+"', "+piso+", '"+criterio+"', Caminho, Distancia, Custo, Tempo)");
        if (criterio.equals("C1")){
            resposta = "Caminho mais curto:\n";
        } else if (criterio.equals("C2")){
            resposta = "Caminho mais rápido:\n";
        } else {
            resposta = "Caminho mais barato:\n";
        }
        if (q.hasSolution()){
            Term t = q.oneSolution().get("Caminho");
            caminho =  transformarEmCaminho(transformarEmLista(t));
            strDistancia = "\nDistância: " + q.oneSolution().get("Distancia").toString()+"km.";
            float custo = Float.parseFloat(q.oneSolution().get("Custo").toString());
            strCusto = "\nCusto: R$" + String.format("%.2f", custo)+".";
            float tempo = Float.parseFloat(q.oneSolution().get("Tempo").toString());
            int horas = (int) tempo;
            int minutos = (int) (60 * (tempo - horas));
            strTempo = "\nTempo: "+ horas + " horas e " + minutos + " minutos.";
            resposta += "Caminho: " + caminho + strDistancia + strCusto + strTempo;
        } else {
            resposta = "Não existe caminho com essas características.";
        }

        return resposta;
    }
}
