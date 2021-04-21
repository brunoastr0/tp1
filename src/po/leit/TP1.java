package po.leit;

import po.leit.ui.Le;
import po.leit.ui.MyCommand;
import po.leit.org;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.Math;
import java.text.DecimalFormat;

public class TP1 {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos = 0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair = false;

        interC = new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if (cmd.get(0).equalsIgnoreCase("carregar")) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if (alunosLidos != notA) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }

                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar")) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar")) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    int numeroU = Integer.parseInt(input);
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp")) {
                    mostrarPauta();

                } else if (cmd.get(0).equalsIgnoreCase("mostrarr")) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top")) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome")) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota")) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help")) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar")) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe como
     * parâmetros um array e um ficheiro Lẽ cada linha do ficheiro e guarda no
     * array. Retorna o número de linhas que forma lidas do ficheiro.
     * 
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        // PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ((nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty())) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe como
     * parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros
     * entre 0 e 20 que representam a nota de cada aluno.
     * 
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the
     *         file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont = 0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1. O método deverá listar todos os nomes dos
     * alunos guardados no array nomesAlunos. O método deverá verificar se já foi
     * carregado os dados para o array. Se sim mostra os nomes dos alunos. Senão
     * deve mostrar a mensagem "Não há dados"
     * 
     * @param
     * @return
     */
    public static void mostrarAlunos() {
        if (nomeAlunos[0] == null) {
            interC.showMsg("Nao ha dados");/**Se o primeiro elemento do array estiver vazio 
                                            assumimos que o resto do array esta vazio,
                                            entao terminamos o metodo com a instruçao return */
            return;
        } 

            int cod = 0;
            System.out.println("Codigo" + " " + "Nome estudante"); /**
                                                                    O array é percorrido utilizando o forEach*/
            for (String aluno : nomeAlunos) {
                final DecimalFormat decimal = new DecimalFormat("00000");

                if (aluno == null) //Se  tentar ler um valor null para saltar as proximas instruçoes
                    continue;
                System.out.print(decimal.format(cod++)+" ");
                System.out.printf("%-20s", aluno);
                System.out.println();
            }
            interC.showMsg("Enter para sair");
        

    }

    /**
     * Método a ser implementando no TP1 O método deverá listar todos os nomes dos
     * alunos guardados no array nomesAlunos. O método deverá verificar se já foi
     * carregado os dados para o array. Se sim mostra os nomes dos alunos. Senão
     * deve mostrar a mensagem "Não há dados". Neste método os dados não são
     * mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10
     * em 10. Esse número que indica quantos nomes enchem a tela é um parâmetro do
     * método.
     * 
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */
    public static void mostrarAlunos(int tela) {
        if (nomeAlunos[0] == null) {
            /**
             * Testar se o ficheiro foi carregada ou nao Se nao, mostrar a mensagem 'Nao ha
             * dados', e sair
             */
            interC.showMsg("Nao ha dados");
            return;

        } 
            int cont = 0, cod = 1;
            int jump = (alunosLidos / tela) + 1;
            /**
             * variavel que armazena o limite do primeiro loop, este que é responsavel pela
             * paginaçao este loop servira como paginas
             */
            for (int j = 1; j <= jump; j++) {
                System.out.println("Codigo" + "  " + "Nome estudante");
                System.out.println();
                for (int i = cont; i < ((j * tela) < nomeAlunos.length ? j * tela : (j) * tela - 1); i++) {
                    final DecimalFormat decimal = new DecimalFormat("00000");
                    /**
                     * Segundo loop que sera responsavel por mostrar os nomes, ((j *
                     * tela)<nomeAlunos.length?j*tela:(j)*tela-1)-> operador ternario que vai
                     * definir o limite do loop, sendo que o limite do loop vai variar de acordo com
                     * o numero de nomes por paginas o operador ternario funciona da seguinte forma:
                     * se o resultado de j*tela[j sendo o indice do primeiro loop, e tela o numero
                     * de nome por paginas] for menor que o tamanho do array de nome, entao o limite
                     * sera j*tela, se for maior entao sera j*tela-1 para impedir que o indice ter o
                     * valor maior que i limite do array
                     */

                    if (nomeAlunos[i] == null)
                        /**
                         * Se encontrar espaços vazios para sair do loop So funciona se espaços vazios
                         * se encontrarem no fim do array
                         */
                        break;

                    System.out.print(decimal.format(cod++) + "  " );
                    System.out.printf("%-20s",nomeAlunos[i]);
                    System.out.println();
                   

                }

                cont += tela;/**
                              * var cont sera responsavel pelo valor da variavel i do segundo loop a variavel
                              * i tem quer dinamica, como o limite, assim sempre que todos nomes forem
                              * mostrados, de acordo com o numero de nomes por paginas forem escolhidos, a
                              * variavel i começara no indice do proximo nome, ou seja, o indice onde começou
                              * a pagina, ex:4, mais o numero de nomes por paginas que foram escolhidos,
                              * ex:4+4, assim o proximo nome que inicializara a pagina estara no indice 8,
                              * assim sucessivamente
                              */

                interC.showMsg("Enter para continuar...");

            
        }

    }

    /**
     * Método a ser implementando no TP1. O método deverá percorrer o array de
     * notas, calcular o valor da média aritmética de notas, a nota máximo e a nota
     * mínima. Os valores calculados devem ser guaraddos na variáveis notaAVG
     * (média), notaMax (nota máxima) e notaMin(nota mínima) Devem validar se o
     * array de notas tem elementos. Se estiver vazio devem somente apresentar a
     * mensagem "Não há dados"
     */
    private static void calcularMaxMinAvg() {
        if (notasAlunos[0] == 0) {
            interC.showMsg("Nao ha dados");
            return;
        }
        int sum = 0, max = 0, min = 0;
        for (int indice = 0; indice < notasAlunos.length; indice++) {//percorrendo o array notas
            if (notasAlunos[indice] > max) { //defininfo o maior valor do array
                max = notasAlunos[indice];  //Se encontar uma nota maior que o atual valor da variavel max,
                                           //a variavel max recebera o valor da nota
               

            } else if (notasAlunos[indice] < min) {
                min = notasAlunos[indice];//Se encontar uma nota menor que o atual valor da variavel min,
                                        //a variavel min recebera o valor da nota
            }
            sum += notasAlunos[indice];//somando as notas
        }
        notaAvg = sum / notasAlunos.length;
        notaMax = max;
        notaMin = min;

        System.out.println("Nota maxima = " + notaMax + " Nota minimia = " + notaMin);
        System.out.println("Media da turma = " + notaAvg);

    }

    /**
     * Método a ser implementando no TP1. O método deverá apresentar um resumo da
     * avaliação; Nota máxima, Nota mínima, Nota média. Número de alunos com nota
     * superior a média e número de alunos com nota inferior a média. a mensagem
     * "Não há dados"
     */
    public static void mostraResumo() {
        int contSup = 0, contInf = 0;
        System.out.println(alunosLidos + " alunos presentes");

        calcularMaxMinAvg();
        for (int i = 0; i < notasAlunos.length; i++) {
            if (notasAlunos[i] > notaAvg) { //Se encontrar uma nota maior que a media
                contSup++;                   //incrementar o valor da variavel contSup(contar superior)
            } else
                contInf++;                  //caso contrario incrementar a variavel contInf(contar inferior)

        }
        System.out.println(contSup + " alunos com nota superior a media");
        System.out.println(contInf + " alunos com nota inferior a media");

        interC.showMsg("Enter para continuar ...");

    }

    /**
     * Método a ser implementando no TP1. O método deverá apresentar o nome dos três
     * alunos que têm as melhores notas.
     */

    public static void mostrarTop() {

        if (notasAlunos[0] == 0) {
            interC.showMsg("Nao ha dados");
            return;

        }
        /** Variaves */
        String[] alunoTop = new String[3];      /**
                                                *Array alunoTop do tipo string armanezara os tres alunos com as melhores nota
                                                *O array notasTop armanezara as tres(3) melhores notas */
        int[] notasTop = new int[3];

        int maxValue = -1; 
        int index = 0;

        int[] copyNum = new int[notasAlunos.length];

        /** */
        /** Codigo paa copiar um array para o outro */
        for (int i = 0; i < copyNum.length; i++) {
            copyNum[i] = notasAlunos[i];
        }

        for (int j = 0; j < 3; j++) { //este for sera responsavel pela repetiçao da busca pelo maior 3 vezes 
                                    //e armazenar os 3 maiores valores no array notasTop e os respetivos aluno no array alunoTop

            for (int indice = 0; indice < copyNum.length; indice++) {//iteraçao responsavel pela busca do maior valor
                if (copyNum[indice] > maxValue) { //identificar o valor maximo do array
                    maxValue = copyNum[indice]; //armazenar esse valor no var maxValue
                    index = indice;             /**armazenar o indice para conseguir o nome do aluno,
                                                *ja que o nome do aluno e a nota estao na mesma posiçao, mas em arrays diferentes  */
                }
            }//fim do segundo for

            notasTop[j] = maxValue;  //armazenar o maior valor no array notas top
            alunoTop[j] = nomeAlunos[index]; //armazenar o nome do aluno na array alunoTop, utilizando indice guardado anteriormente
            maxValue = -1;                  //fazer reset da variavel maxValue, para armazenar outro valor maior
            copyNum[index] = -1;   
                     //retirar o atual valor maior do array para que na proxima iteraçao nao seja definido como maior
        }//fim do primeiro for

        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.printf("%-20s",alunoTop[i]);
            System.out.println(notasTop[i]);

        }

        interC.showMsg("Enter para continuar ...");

    }

    /**
     * Método a ser implementando no TP1. Apresentar a pauta com nomes dos alunos e
     * á frente cada nome a respectiva nota obtida.
     */
    public static void mostrarPauta() {
        if (notasAlunos[0] == 0) {
            interC.showMsg("Nao ha dados");
            return;

        }
        int jump = (alunosLidos / 10) + 1;
        int cont = 0, cod = 1;
        for (int j = 1; j <= jump; j++) {
            System.out.println("codigo" + "   " + "Nome estudante" + "   " + "Nota");
            for (int i = cont; i < ((j * 10) < nomeAlunos.length ? j * 10 : (j) * 10 - 1); i++) {//mesmo codigo do metodo paginar
                final DecimalFormat decimal = new DecimalFormat("00000");
                if (nomeAlunos[i] == null)
                    /**
                     * Se encontrar espaços vazios para sair do loop So funciona se espaços vazios
                     * se encontrarem no fim do array
                     */
                    break;
                System.out.print(decimal.format(cod++)+" ");
                System.out.printf("%-20s",nomeAlunos[i]);
                System.out.println(notasAlunos[i]);
                

            }

            cont += 10;//mesmo codigo do metodo paginar
            interC.showMsg("A ser implementado ...");
        }

    }

    /**
     * Método a ser implementando no TP1 Apresentar para um aluno específico em que
     * o nome é dado como parâmetro a nota de avaliação
     * 
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a
     *             sua nota
     * @return
     */
    public static void mostrarDetalhesAluno(String nome) {
        interC.showMsg("A ser implementado ...");

    }

    /**
     * Método a ser implementando no TP1 O método deverá pedir um nome e pesquisar o
     * array de nomes. Caso existir ou caso existem nomes parecidos apresentar a
     * lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou
     * três primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(String nome) {

        for (String aluno : nomeAlunos) {
            String str = aluno;
            if (aluno == null) { /*
                                  * Se o programa estiver a ler um valor null, para este passar para a proxima
                                  * instruçao
                                  */
                System.out.println("Nao ha dados");
                continue;
            } else {

                String last;

                String[] strSp = str.split(" ",
                        2);/* Instruçao para separar o nome do apelido. Cria um array de String */
                String first = strSp[0];
                String[] nameMiddle = strSp[1].split(" ", 2);/*
                                                              * Instruçao para separar os apelidos com duas palavras
                                                              * (Ex:Elmer dos Santos). Cria um array de String (ex:[dos, Santos])
                                                              */

                if (nameMiddle.length > 1) {/* Se o apelido tiver duas palavras: (ex:Elmes dos Santos)*/

                    last = nameMiddle[1];// Armazenar o ultimo elemento do array, que sera o apelido, na variavel last [dos, santos]
                } else {
                    last = nameMiddle[0];// Caso o array tiver um elemento, esse elemento sera o apelido ex:Bruno Angelo
                                        //array = [angelo]
                }

                if (nome.equalsIgnoreCase(first) || nome.equalsIgnoreCase(last)) {// Testar se foi introduzido o apelido
                                                                                  // ou o primeiro nome
                    System.out.println(aluno);// Mostrar o(s) nome(s)/apelido(s) pesquisados
                }
            }

        }

        interC.showMsg("Enter para continuar ...");

    }

    /**
     * Método a ser implementando no TP1 O método deverá pedir um nome e pesquisar o
     * array de nomes. Caso existir ou caso existem nomes parecidos apresentar a
     * lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou
     * três primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(int nota) {
        if (notasAlunos[0] == 0) {
            System.out.println("Nao ha dados");
            return;
        }
        System.out.println(notasAlunos[0]);
        System.out.println("Nome estudante" + "     " + "NOtas");
        for (int i = 0; i < notasAlunos.length; i++) {
            if (nota == notasAlunos[i]) {
                System.out.println(nomeAlunos[i] + "  " + notasAlunos[i]);
            } else {
                System.out.println("Nao existe aluno com esta nota");
            }
        }

        interC.showMsg("Enter para continuar ...");

    }

    private String[] searchByName(String nome) {
        return null;
    }

}
