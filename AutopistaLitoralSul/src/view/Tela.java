package view;

import controller.Controle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import model.ObservadorController;
import model.ObservadorVeiculo;
import model.PedacoMapa;
import model.Veiculo;

public class Tela extends javax.swing.JFrame implements ObservadorVeiculo, ObservadorController {

    private Controle controller = new Controle();

    private JLabel[][] matrizJLabel;
    private PedacoMapa[][] mapa;

    private final int ESPACO = 27;
    private final int TAMANHO = 25;

    private JLabel tituloMetodo = new JLabel();
    private JLabel tituloQuantidade = new JLabel();
    private JLabel tituloArquivo = new JLabel();
    
    private JTextField textoQuantidade = new JTextField();
    private JTextField textoArquivo = new JTextField();
    
    private JButton botaoIniciar = new JButton();
    private JButton botaoEncerrar = new JButton();
    private JRadioButton radioMonitor = new JRadioButton();
    private JRadioButton radioSemaforo = new JRadioButton();
    private ButtonGroup grupoBotao = new ButtonGroup();
    
    private JPanel painel = new JPanel();

    public Tela() {
        controller.adicionarObservador(this);

        initComponents();

        iniciarComponentes();
//        carregarMapa();
        iniciarListeners();
    }

    private void iniciarComponentes() {
        this.setSize(1500, 1500);
        this.add(painel);

        tituloQuantidade.setLocation(100, 60);
        tituloQuantidade.setVisible(true);
        tituloQuantidade.setSize(1050, 40);
        tituloQuantidade.setText("Insira a quantidade de carros desejada:");
        
        textoQuantidade.setLocation(100, 100);
        textoQuantidade.setVisible(true);
        textoQuantidade.setSize(150, 40);
        textoQuantidade.setText("5");
        
        tituloMetodo.setLocation(100, 150 );
        tituloMetodo.setVisible(true);
        tituloMetodo.setSize(1050, 40);
        tituloMetodo.setText("Selecione o método desejado:");
        
        radioMonitor.setLocation(100, 200);
        radioMonitor.setVisible(true);
        radioMonitor.setSize(100, 15);
        radioMonitor.setText("Monitor");
        radioMonitor.setSelected(true);

        radioSemaforo.setLocation(100, 225);
        radioSemaforo.setVisible(true);
        radioSemaforo.setSize(100, 15);
        radioSemaforo.setText("Semaforo");
        
        tituloArquivo.setLocation(100, 275);
        tituloArquivo.setVisible(true);
        tituloArquivo.setSize(1050, 40);
        tituloArquivo.setText("Insira o mapa:");
        
        textoArquivo.setLocation(100, 325);
        textoArquivo.setVisible(true);
        textoArquivo.setSize(250, 40);
        textoArquivo.setText("");

        botaoIniciar.setLocation(100, 400);
        botaoIniciar.setVisible(true);
        botaoIniciar.setSize(180, 30);
        botaoIniciar.setText("Iniciar");

        botaoEncerrar.setLocation(100, 500);
        botaoEncerrar.setVisible(true);
        botaoEncerrar.setSize(180, 30);
        botaoEncerrar.setText("Encerrar");

        grupoBotao.add(radioMonitor);
        grupoBotao.add(radioSemaforo);

        painel.add(tituloMetodo);
        painel.add(tituloQuantidade);
        painel.add(tituloArquivo);
        painel.add(botaoIniciar);
        painel.add(botaoEncerrar);
        painel.add(radioMonitor);
        painel.add(radioSemaforo);
        painel.add(textoQuantidade);
        painel.add(textoArquivo);
        painel.setLocation(0, 0);
        painel.setSize(1500, 1500);
        painel.setVisible(true);
        painel.setLayout(null);
    }

    private void carregarMapa() {
        for (int i = 0; i < mapa.length; i++) {// se tiver espaço na matriz
            for (int j = 0; j < mapa[i].length; j++) {
                JLabel jl = new JLabel();
                jl.setLocation((int) ((i + 13.5) * ESPACO), (int) ((j + 0.5) * ESPACO));
                jl.setSize(TAMANHO, TAMANHO);
                jl.setVisible(true);
                jl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (mapa[i][j] != null) {
                    jl.setIcon(new ImageIcon(
                            getClass().getResource("../imagens/" + mapa[i][j].getTipo() + ".png")
                    ));
                }
                painel.add(jl);
                matrizJLabel[i][j] = jl;
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void iniciarListeners() {
        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int qtdVeiculos;
                try {
                    qtdVeiculos = Integer.parseInt(textoQuantidade.getText());
                    controller.carregarMapa( textoArquivo.getText(), qtdVeiculos, radioSemaforo.isSelected());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });

        botaoEncerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.finalizarTudo();
            }
        });
    }

    private void limparMapa() {
        if (matrizJLabel != null) {
            for (int i = 0; i < matrizJLabel.length; i++) {
                for (int j = 0; j < matrizJLabel[i].length; j++) {
                    painel.remove(matrizJLabel[i][j]);
                }
            }
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public synchronized void avisarMovimento(int xAnterior, int yAnterior, int x, int y) {
        matrizJLabel[xAnterior][yAnterior]
                .setIcon(new ImageIcon(
                        getClass().getResource("../imagens/" + mapa[xAnterior][yAnterior].getTipo() + ".png")
                ));
        matrizJLabel[x][y]
                .setIcon(new ImageIcon(
                        getClass().getResource("../imagens/" + mapa[x][y].getTipo() + ".png")
                ));
//        painel.repaint();
    }

    @Override
    public void inserirMapa(PedacoMapa[][] mapa) {
        limparMapa();
        this.mapa = mapa;
        matrizJLabel = new JLabel[mapa.length][mapa[0].length];
        carregarMapa();
        painel.repaint();
    }

    @Override
    public void cadastrarVeiculo(Veiculo v) {
        v.adicionarObservador(this);
    }

    @Override
    public void avisarFinalizacao(int x, int y, boolean criarNovo) {
        matrizJLabel[x][y]
                .setIcon(new ImageIcon(
                        getClass().getResource("../imagens/" + mapa[x][y].getTipo() + ".png")
                ));
        if (criarNovo) {
            controller.inserirNovoVeiculo();
        }
    }

    public static void main(String[] args) {
        Tela t = new Tela();
        t.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
