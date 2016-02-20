package testelerarquivo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Created by Ranniery on 11/02/2016.
 */

public class MaxSatAlgorithm {

    public static int[][] MatrixToGraph(int[][] matrix){
        int nVars = matrix[0][0];
        int nClausulas = matrix[0][1];
        
        //A matriz correspondente ao grafo vai ter que ter nVars*4 linhas, pois as primeiras nVars*2 vao ser somente as
        //linhas dos subgrafos com 2 vertices, e a partir de nVars*2+1 até nVars*4 vao ser as linhas dos subgrafos de
        //3 vertices.
        
        int[][] graph = new int[nVars*2 + nClausulas*3][nVars*2 + nClausulas*3];
        graph = zerarMatriz(graph, nVars*2 + nClausulas*3);

        int i = 0;
        int j = 0;

        //Seta arestas entre Xi e -Xi. (subgrafos de 2 vertices)
        //Da posicao 0 até a posicao nVars-1 sao as variaveis normais.
        //Da posicao nVars até nVars*2 - 1 sao as negações das variáveis.
        //Ou seja, pra pegar a linha correspondente à negativa de uma variavel Xi, eu tenho que somar i ao numero
        //de nVars-1 (que eh onde acabam as variaveis positivas).
/*
        for(i = 0; i < nVars; i++){
            //Faz uma aresta entre Xi e -Xi.
            graph[i][nVars + i] = 1;
            //Eh preciso setar também na linha da variavel negativa, para fazer a aresta "de volta".
            graph[nVars + i][i] = 1;
        }
*/
        for(i = 0; i < nVars+2; i = i+2){
            //Faz uma aresta entre Xi e -Xi.
            graph[i][i+1] = 1;
            //Eh preciso setar também na linha da variavel negativa, para fazer a aresta "de volta".
            graph[i+1][i] = 1;
        }

        //Agora eh preciso setar os subgrafos de 3 vertices. O indice tem que comecar de nVars*2, pois a ultima variavel
        //negativa vai estar na posicao (nVars*2 - 1).

        //O contador vai parar quando chegar na ultima linha do arquivo. O arquivo tem nClausulas + 1 linhas.
        //O contador ja começa de 1 pois na primeira linha nao ha informacoes sobre clausulas.

        //indice1 ate indice3 sao os indices das variaveis das clausulas
        int indice1 = 0;
        int indice2 = 0;
        int indice3 = 0;

        //indice4 ate indice6 vao guardar os indices das novas linhas 'criadas' pra cada variavel de cada clausula
        int indice4 = 0;
        int indice5 = 0;
        int indice6 = 0;


        //j vai ser o indice da nova linha 'criada' pra cada variavel de cada clausula.
        j = nVars*2;

        for(i = 1 ; i < nClausulas+1; i++){
            //Verifica se o numero que ele pegou eh negativo. Se for, entao a representacao dele vai ta na linha
            //(nVars - 1) + indice da variavel.
            //Se for positivo, entao a representacao dele vai ta na linha indice - 1.
            indice1 = matrix[i][0];
            if(indice1 < 0) {
                indice1 = (abs(indice1) - 1)*2 + 1;
            }
            else{
                indice1 = (indice1 - 1)*2;
            }

            //Liga o vértice ao dispositivo de 2 vertices correspondente.
            graph[indice1][j] = 1;
            graph[j][indice1] = 1;
            indice4 = j;

            j++;

            indice2 = matrix[i][1];
            if(indice2 < 0) {
                indice2 = (abs(indice2) - 1)*2 + 1;
            }
            else{
                indice2 = (indice2 - 1)*2;
            }

            graph[indice2][j] = 1;
            graph[j][indice2] = 1;
            indice5 = j;

            j++;

            indice3 = matrix[i][2];
            if(indice3 < 0) {
                indice3 = (abs(indice3) - 1)*2 + 1;
            }
            else{
                indice3 = (indice3 - 1)*2;
            }

            graph[indice3][j] = 1;
            graph[j][indice3] = 1;
            indice6 = j;

            j++;

            //Cria arestas entre as 3 variaveis lidas, formando um triangulo. Lembrando que sempre tem que fazer
            //a aresta "indo" e "voltando", pois o grafo é nao orientado.
            graph[indice4][indice5] = 1;
            graph[indice4][indice6] = 1;
            graph[indice5][indice4] = 1;
            graph[indice5][indice6] = 1;
            graph[indice6][indice4] = 1;
            graph[indice6][indice5] = 1;
        }

        System.out.println(Arrays.deepToString(graph));

        return graph;
    }

    public static int[][] TxtToMatrix() throws IOException {

        BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\MATHS\\Desktop\\entrada.txt"));

        String currentLine;
        String[] separatedLine;
        //int nLines = NumberOfLines(file);
        //nLines = 10;
        int[][] arquivo1 = new int[301][3];
        int i = 0;
        int j = 0;

        //System.out.println("cheguei 1");

        while((currentLine = file.readLine()) != null){
            //System.out.println("cheguei 0");
            separatedLine = currentLine.split(" ");
            System.out.println(Arrays.toString(separatedLine));

            for(String aux : separatedLine){
                arquivo1[i][j] = Integer.parseInt(aux);
                //System.out.print(arquivo1[i][j] + " ");
                j++;
            }

            //System.out.println();

            j = 0;
            i++;
        }

        file.close();

        return arquivo1;
    }

    private static int NumberOfLines (BufferedReader file) throws IOException{
        String aline;
        int count = 0;

        while((aline = file.readLine()) != null){
            count++;
            //System.out.print("HUEHUE ");
        }

        System.out.println(count);

        file.close();

        return count;

    }

    private static int[][] zerarMatriz(int[][] matriz, int tam){
        for(int i = 0; i < tam; i++){
            for(int j = 0; j < tam; j++){
                matriz[i][j] = 0;
            }
        }

        return matriz;
    }

}
