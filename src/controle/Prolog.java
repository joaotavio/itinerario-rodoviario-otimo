package controle;

import org.jpl7.*;

import java.util.ArrayList;

public class Prolog {

    public boolean iniciar(){
        try {
            Query q = new Query("consult('dados.pl')");
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
            Query q = new Query("consult('"+caminho+"')");
            q.hasSolution();
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
        Query q = null;
        String resposta, caminho, strCusto, strTempo, strDistancia;
        if (criterio.equals("C1")){
            q = new Query("criterio_distancia('"+origem+"', '"+destino+"', "+piso+", Caminho, Distancia, Custo)");
        } else if (criterio.equals("C2")){

        } else {
            q = new Query("criterio_custo('"+origem+"', '"+destino+"', "+piso+", Caminho, Distancia, Custo)");
        }
        if (q != null && q.hasSolution()){
            Term t = q.oneSolution().get("Caminho");
            caminho =  transformarEmCaminho(transformarEmLista(t));
            strDistancia = "\nDistância: " + q.oneSolution().get("Distancia").toString()+"km.";
            strCusto = "\nCusto: R$" + q.oneSolution().get("Custo").toString()+".";
            strTempo = "\nTempo: 0 horas";
            resposta = "Caminho: " + caminho + strDistancia + strCusto + strTempo;
        } else {
            resposta = "Não existe caminho com essas características.";
        }

        return resposta;
    }
}
