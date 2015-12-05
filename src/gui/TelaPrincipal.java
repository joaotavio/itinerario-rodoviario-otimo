package gui;

import controle.Prolog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TelaPrincipal extends JFrame {
    private JPanel painelCampos;
    private JLabel labelOrigem;
    private JComboBox campoOrigem;
    private JLabel labelDestino;
    private JComboBox campoDestino;
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
        prolog = new Prolog();
        if (!prolog.iniciar()){
            mostrarMensagem("Arquivo do prolog não pode ser encontrado.");
            dispose();
            System.exit(0);
        }
        iniciarComponentes();
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

        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        fileChooser = new JFileChooser();
        File diretorioAtual = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(diretorioAtual);

        botaoGerar.addActionListener(new AcaoBotaoGerar());
        botaoListarCidades.addActionListener(new AcaoBotaoListarVias());
        botaoSalvarEmArquivo.addActionListener(new AcaoBotaoSalvarArquivo());

        String listaCidades[] = prolog.listarCidades();
        campoOrigem.setModel(new DefaultComboBoxModel(listaCidades));
        campoDestino.setModel(new DefaultComboBoxModel(listaCidades));
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

    private void mostrarMensagem(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }

    private class AcaoBotaoListarVias implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String listaVias[] = prolog.listarVias();
            areaTexto.setText("");
            for (String via : listaVias) {
                areaTexto.append(via + "\n");
            }
        }
    }

    private class AcaoBotaoGerar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String origem = (String)campoOrigem.getSelectedItem();
            String destino = (String)campoDestino.getSelectedItem();
            int piso = (Integer)spinnerPiso.getValue();
            String criterio = grupoCriterio.getSelection().getActionCommand();
            String resposta = prolog.gerarItinerario(origem, destino, piso, criterio);
            areaTexto.setText(resposta);
        }
    }

    private class AcaoBotaoSalvarArquivo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setSelectedFile(new File("saida.txt"));
            int ret = fileChooser.showSaveDialog(TelaPrincipal.this);

            if (ret == JFileChooser.APPROVE_OPTION){
                String caminho = fileChooser.getSelectedFile().getAbsolutePath();
                int resultado = JOptionPane.YES_OPTION;
                if (new File(caminho).exists()){
                    resultado = JOptionPane.showConfirmDialog(TelaPrincipal.this,"Arquivo já existe, deseja substituir?", "Substituir arquivo", JOptionPane.YES_NO_OPTION);
                }
                if (resultado == JOptionPane.YES_OPTION){
                    String conteudo = areaTexto.getText();
                    String nome = fileChooser.getSelectedFile().getName();
                    if (prolog.salvarEmArquivo(caminho, conteudo)){
                        mostrarMensagem("Arquivo " + nome + " salvo com sucesso.");
                    } else {
                        mostrarMensagem("ERRO: Não foi possível salvar o arquivo " + nome + ".");
                    }
                }
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
            fileChooser.setSelectedFile(new File(""));
            int ret = fileChooser.showOpenDialog(TelaPrincipal.this);

            if (ret == JFileChooser.APPROVE_OPTION){
                String caminho = fileChooser.getSelectedFile().getAbsolutePath();
                String nome = fileChooser.getSelectedFile().getName();

                if (prolog.carregarArquivo(caminho)) {
                    mostrarMensagem("Arquivo " + nome + " aberto com sucesso.");
                    String listaCidades[] = prolog.listarCidades();
                    campoOrigem.setModel(new DefaultComboBoxModel(listaCidades));
                    campoDestino.setModel(new DefaultComboBoxModel(listaCidades));

                } else {
                    mostrarMensagem("ERRO: Não foi possível abrir o arquivo " + nome + ".");
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

