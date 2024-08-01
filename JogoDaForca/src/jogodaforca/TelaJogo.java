package jogodaforca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaJogo extends JFrame {
    private JogoDaForca jogo;          // Objeto do jogo da forca
    private JLabel tamanhoLabel;        // Label para exibir o tamanho da palavra
    private JLabel dicaLabel;           // Label para exibir a dica da palavra
    private JTextField letraTextField;  // Campo de texto para entrada da letra
    private JLabel mensagemLabel;       // Label para exibir mensagens do jogo
    private JLabel palavraAdivinhadaLabel; // Label para exibir a palavra adivinhada
    private JLabel acertosLabel;        // Label para exibir o número de acertos
    private JLabel penalidadeLabel;     // Label para exibir a penalidade
    private JButton iniciarButton;      // Botão para iniciar um novo jogo
    private JButton adivinharButton;    // Botão para adivinhar uma letra

    // Construtor da classe TelaJogo
    public TelaJogo() {
        super("Jogo da Forca");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Inicializa o jogo da forca
        try {
            jogo = new JogoDaForca();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Componentes da tela
        iniciarButton = new JButton("Iniciar Jogo");
        tamanhoLabel = new JLabel("Tamanho da palavra: ");
        dicaLabel = new JLabel("Dica: ");
        letraTextField = new JTextField();
        adivinharButton = new JButton("Adivinhar Letra");
        mensagemLabel = new JLabel();
        palavraAdivinhadaLabel = new JLabel();
        acertosLabel = new JLabel();
        penalidadeLabel = new JLabel();

        // Adiciona componentes à tela
        add(iniciarButton);
        add(tamanhoLabel);
        add(dicaLabel);
        add(letraTextField);
        add(adivinharButton);
        add(mensagemLabel);
        add(palavraAdivinhadaLabel);
        add(acertosLabel);
        add(penalidadeLabel);

        // Eventos dos botões
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });

        adivinharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adivinharLetra();
            }
        });

        setVisible(true);
    }

    // Método para iniciar um novo jogo
    private void iniciarJogo() {
        try {
            jogo = new JogoDaForca(); // Cria um novo jogo da forca
            tamanhoLabel.setText("Tamanho da palavra: " + jogo.getTamanho()); // Exibe o tamanho da palavra
            dicaLabel.setText("Dica: " + jogo.getDica()); // Exibe a dica associada à palavra
            mensagemLabel.setText(""); // Limpa a mensagem de status
            palavraAdivinhadaLabel.setText(""); // Limpa a palavra adivinhada
            acertosLabel.setText(""); // Limpa o número de acertos
            penalidadeLabel.setText(""); // Limpa a penalidade
            letraTextField.setEnabled(true); // Habilita o campo de texto para entrada de letras
            adivinharButton.setEnabled(true); // Habilita o botão de adivinhar letra
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para adivinhar uma letra
    private void adivinharLetra() {
        try {
            String letra = letraTextField.getText().trim();
            jogo.getOcorrencias(letra); // Chama o método para verificar a letra adivinhada
            palavraAdivinhadaLabel.setText("Palavra: " + jogo.getPalavraAdivinhada()); // Exibe a palavra adivinhada até o momento
            acertosLabel.setText("Acertos: " + jogo.getAcertos()); // Exibe o número de acertos
            penalidadeLabel.setText("Penalidade: " + jogo.getNumeroPenalidade() + " - " + jogo.getNomePenalidade()); // Exibe a penalidade atual
            mensagemLabel.setText(jogo.getResultado()); // Exibe o resultado do jogo (vitória, derrota ou em andamento)
            
            // Desabilita os controles se o jogo terminou
            if (jogo.terminou()) {
                letraTextField.setEnabled(false); // Desabilita o campo de texto para entrada de letras
                adivinharButton.setEnabled(false); // Desabilita o botão de adivinhar letra
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Ponto de entrada do programa
    public static void main(String[] args) {
        new TelaJogo(); // Cria uma nova instância da tela do jogo
    }
}
