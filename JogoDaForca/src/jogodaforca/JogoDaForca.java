package jogodaforca;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class JogoDaForca {
    private String palavra;             // Palavra a ser adivinhada
    private String dica;                // Dica associada à palavra
    private ArrayList<Integer> ocorrencias; // Posições das letras corretas na palavra
    private int tamanho;                // Tamanho da palavra
    private int acertos;                // Número de letras corretas adivinhadas
    private int penalidade;             // Contador de penalidades (erros)

    // Construtor da classe JogoDaForca
    public JogoDaForca() throws Exception {
        this.ocorrencias = new ArrayList<>();
        this.acertos = 0;
        this.penalidade = 0;

        // Ler palavras/dicas do arquivo palavras.txt
        try {
            File file = new File("palavras.txt");
            Scanner scanner = new Scanner(file);
            ArrayList<String> palavrasDicas = new ArrayList<>();
            while (scanner.hasNextLine()) {
                palavrasDicas.add(scanner.nextLine());
            }
            scanner.close();

            // Escolher uma palavra/dica aleatória
            Random random = new Random();
            String[] palavraDica = palavrasDicas.get(random.nextInt(palavrasDicas.size())).split(";");
            this.palavra = palavraDica[0];
            this.dica = palavraDica[1];
            this.tamanho = palavra.length();
        } catch (FileNotFoundException e) {
            throw new Exception("Arquivo de palavras inexistente");
        }
    }

    // Método para iniciar um novo jogo
    public void iniciar() {
        // Inicializa as variáveis para um novo jogo
        this.ocorrencias.clear();
        this.acertos = 0;
        this.penalidade = 0;
    }

    // Retorna a dica associada à palavra
    public String getDica() {
        return this.dica;
    }

    // Retorna o tamanho da palavra
    public int getTamanho() {
        return this.tamanho;
    }

    // Verifica as ocorrências da letra na palavra e atualiza acertos/penalidades
    public ArrayList<Integer> getOcorrencias(String letra) throws Exception {
        if (letra.isEmpty() || letra.length() > 1 || !(letra.matches("[a-zA-Z]"))) {
            throw new Exception("Letra inválida");
        }

        ArrayList<Integer> ocorrencias = new ArrayList<>();
        char letraChar = letra.toLowerCase().charAt(0); // Tratando a entrada para minúscula
        boolean acertou = false;
        for (int i = 0; i < this.tamanho; i++) {
            if (Character.toLowerCase(this.palavra.charAt(i)) == letraChar) {
                ocorrencias.add(i + 1); // Armazena a posição (índice + 1) da letra correta na palavra
                acertou = true;
            }
        }

        if (acertou) {
            this.acertos += ocorrencias.size(); // Incrementa o número de acertos
        } else {
            this.penalidade++; // Incrementa o contador de penalidades
        }

        return ocorrencias; // Retorna as posições onde a letra correta ocorre na palavra
    }

    // Verifica se o jogo terminou
    public boolean terminou() {
        // O jogo termina se as penalidades alcançarem 6 ou se todos os caracteres da palavra forem adivinhados
        return this.penalidade >= 6 || this.acertos >= this.tamanho;
    }

    // Retorna a palavra adivinhada até o momento (com asteriscos para letras não adivinhadas)
    public String getPalavraAdivinhada() {
        StringBuilder palavraAdivinhada = new StringBuilder();
        for (int i = 0; i < this.tamanho; i++) {
            if (this.ocorrencias.contains(i + 1)) {
                palavraAdivinhada.append(this.palavra.charAt(i)); // Mostra a letra correta
            } else {
                palavraAdivinhada.append("*"); // Mostra asterisco para letras não adivinhadas
            }
        }
        return palavraAdivinhada.toString(); // Retorna a palavra atualmente adivinhada
    }

    // Retorna o número atual de acertos
    public int getAcertos() {
        return this.acertos; // Retorna o número de letras corretas adivinhadas
    }

    // Retorna o número de penalidades (erros) e associações com o nível de penalidade
    public int getNumeroPenalidade() {
        String[] nomesPenalidade = {"sem penalidades", "perdeu a cabeça", "perdeu o tronco",
                                    "perdeu primeiro braço", "perdeu segundo braço", "perdeu a primeira perna", "perdeu a segunda perna"};
        return this.penalidade < nomesPenalidade.length ? this.penalidade : nomesPenalidade.length - 1;
    }

    // Retorna o nome associado ao nível de penalidade atual
    public String getNomePenalidade() {
        String[] nomesPenalidade = {"sem penalidades", "perdeu a cabeça", "perdeu o tronco",
                                    "perdeu primeiro braço", "perdeu segundo braço", "perdeu a primeira perna", "perdeu a segunda perna"};
        return nomesPenalidade[this.penalidade < nomesPenalidade.length ? this.penalidade : nomesPenalidade.length - 1];
    }

    // Retorna o resultado do jogo com base nos acertos e penalidades
    public String getResultado() {
        if (this.acertos == this.tamanho) {
            return "você venceu";
        } else if (this.penalidade >= 6) {
            return "você foi enforcado";
        } else {
            return "jogo em andamento";
        }
    }
}
