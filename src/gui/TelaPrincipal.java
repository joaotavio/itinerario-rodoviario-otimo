package gui;

import controle.Prolog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JPanel painelCampos;
    private JLabel labelOrigem;
    private JTextField campoOrigem;
    private JLabel labelDestino;
    private JTextField campoDestino;
    private JLabel labelPiso;
    private JSpinner spinnerPiso;
    private JPanel painelPrincipal;
    private JRadioButton radioMenorDist;
    private JPanel painelCriterio;
    private JRadioButton radioMenorDuracao;
    private JRadioButton radioMenorCusto;
    private JTextArea areaTexto;
    private JScrollPane scrollPane;
    private JButton botaoGerar;
    private JButton botaoSalvarEmArquivo;
    private JButton botaoListarCidades;

    ButtonGroup grupoCriterio;

    private JMenuBar menubar;
    private JMenu arquivo;
    private JMenu ajuda;
    private JMenuItem abrirArquivo;
    private JMenuItem rodarTestes;
    private JMenuItem sair;
    private JMenuItem sobre;

    private JFileChooser fileChooser;

    private final int ALTURA = 400;
    private final int LARGURA = 700;

    Prolog prolog;

    public TelaPrincipal() {
        iniciarComponentes();
        prolog = new Prolog();
        prolog.iniciar();
    }

    private void iniciarComponentes(){
        setTitle("Itinerário Rodoviário");
        setMinimumSize(new Dimension(LARGURA, ALTURA));
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMenu();
        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null);

        spinnerPiso.setModel(new SpinnerNumberModel(1, 1, 5, 1));

        grupoCriterio = new ButtonGroup();
        grupoCriterio.add(radioMenorDist);
        grupoCriterio.add(radioMenorDuracao);
        grupoCriterio.add(radioMenorCusto);
        radioMenorDist.setSelected(true);
        radioMenorDist.setActionCommand("C1");
        radioMenorDuracao.setActionCommand("C2");
        radioMenorCusto.setActionCommand("C3");

        //areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        fileChooser = new JFileChooser();

        botaoGerar.addActionListener(new AcaoBotaoGerar());
        botaoListarCidades.addActionListener(new AcaoBotaoListarCidades());
        botaoSalvarEmArquivo.addActionListener(new AcaoBotaoSalvarArquivo());
    }

    private void addMenu(){
        menubar = new JMenuBar();
        setJMenuBar(menubar);

        arquivo = new JMenu("Arquivo");
        ajuda = new JMenu("Ajuda");

        menubar.add(arquivo);
        menubar.add(ajuda);

        abrirArquivo = new JMenuItem("Abrir arquivo");
        rodarTestes = new JMenuItem("Rodar testes");
        sair = new JMenuItem("Sair");
        sobre = new JMenuItem("Sobre");

        arquivo.add(abrirArquivo);
        arquivo.add(rodarTestes);
        arquivo.addSeparator();
        arquivo.add(sair);
        ajuda.add(sobre);

        rodarTestes.addActionListener(new AcaoItemMenuRodarTestes());
        abrirArquivo.addActionListener(new AcaoItemMenuAbrirArquivo());
        sair.addActionListener(new AcaoItemMenuSair());
        sobre.addActionListener(new TelaSobre(this));
    }

    private class AcaoBotaoListarCidades implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String listaCidades = prolog.listarCidades();
            areaTexto.setText(listaCidades);
        }
    }

    private class AcaoBotaoGerar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String origem = campoOrigem.getText();
            String destino = campoDestino.getText();
            int piso = (Integer)spinnerPiso.getValue();
            String criterio = grupoCriterio.getSelection().getActionCommand();
            String resposta = prolog.gerarItinerario(origem, destino, piso, criterio);
            areaTexto.setText(resposta);
        }
    }

    private class AcaoBotaoSalvarArquivo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int ret = fileChooser.showSaveDialog(TelaPrincipal.this);

            if (ret == JFileChooser.APPROVE_OPTION){
                String caminho = fileChooser.getSelectedFile().getAbsolutePath();
                String conteudo = areaTexto.getText();
                prolog.salvarEmArquivo(caminho, conteudo);
                String nome = fileChooser.getSelectedFile().getName();
                areaTexto.setText("Arquivo " + nome + " salvo com sucesso.");
            }
        }
    }

    private class AcaoItemMenuRodarTestes implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class AcaoItemMenuAbrirArquivo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int ret = fileChooser.showOpenDialog(TelaPrincipal.this);

            if (ret == JFileChooser.APPROVE_OPTION){
                String caminho = fileChooser.getSelectedFile().getAbsolutePath();
                String nome = fileChooser.getSelectedFile().getName();
                if (prolog.carregarArquivo(caminho)) {
                    areaTexto.setText("Arquivo " + nome + " aberto com sucesso.");
                } else {
                    areaTexto.setText("Não foi possível abrir o arquivo " + nome + ".");
                }
            }
        }
    }

    private class AcaoItemMenuSair implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            System.exit(0);
        }
    }
}

