package br.uece.lotus.tools.MaxSat;

import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.Transition;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

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
        
        int[][] graph = new int[nVars*4][nVars*4];
        graph = zerarMatriz(graph, nVars*4);

        int i = 0;
        int j = 0;

        //Seta arestas entre Xi e -Xi. (subgrafos de 2 vertices)
        //Da posicao 0 até a posicao nVars-1 sao as variaveis normais.
        //Da posicao nVars até nVars*2 - 1 sao as negações das variáveis.
        //Ou seja, pra pegar a linha correspondente à negativa de uma variavel Xi, eu tenho que somar i ao numero
        //de nVars-1 (que eh onde acabam as variaveis positivas).

        for(i = 0; i < nVars; i++){
            //Faz uma aresta entre Xi e -Xi.
            graph[i][nVars-1 + i] = 1;
            //Eh preciso setar também na linha da variavel negativa, para fazer a aresta "de volta".
            graph[nVars-1 + i][i] = 1;
        }


        //Agora eh preciso setar os subgrafos de 3 vertices. O indice tem que comecar de nVars*2, pois a ultima variavel
        //negativa vai estar na posicao (nVars*2 - 1).

        //O contador vai parar quando chegar na ultima linha do arquivo. O arquivo tem nClausulas + 1 linhas.
        //O contador ja começa de 1 pois na primeira linha nao ha informacoes sobre clausulas.

        //indice1 ate indice3 sao os indices das variaveis das clausulas
        int indice1 = 0;
        int indice2 = 0;
        int indice3 = 0;
        //indice4 ate indice6 sao os indices das variaveis dos subgrafos pares
        int indice4 = 0;
        int indice5 = 0;
        int indice6 = 0;

        for(i = 1 ; i < nClausulas+1; i++){
            //Verifica se o numero que ele pegou eh negativo. Se for, entao a representacao dele vai ta na linha
            //(nVars*3 - 1) + indice da variavel.
            //Se for positivo, entao a representacao dele vai ta na linha (nVars*2 - 1) + indice da variavel.
            indice4 = indice1 = matrix[i][0];
            if(indice1 < 0) {
                indice1 = nVars*3 - 1 + abs(indice1);
                indice4 = nVars - 1 + abs(indice4);
            }
            else{
                indice1 = nVars*2 - 1 + indice1;
                indice4 = indice4 - 1;
            }

            indice5 = indice2 = matrix[i][1];
            if(indice2 < 0) {
                indice2 = nVars*3 - 1 + abs(indice2);
                indice5 = nVars-1 + abs(indice5);
            }
            else{
                indice2 = nVars*2 - 1 + indice2;
                indice5 = indice5 - 1;
            }

            indice6 = indice3 = matrix[i][2];
            if(indice3 < 0) {
                indice3 = nVars*3 - 1 + abs(indice3);
                indice6 = nVars-1 + abs(indice6);
            }
            else{
                indice3 = nVars*2 - 1 + indice3;
                indice6 = indice6 - 1;
            }

            //Cria arestas entre as 3 variaveis lidas, formando um triangulo. Lembrando que sempre tem que fazer
            //a aresta "indo" e "voltando", pois o grafo é nao orientado.
            graph[indice1][indice2] = 1;
            graph[indice1][indice3] = 1;
            graph[indice2][indice1] = 1;
            graph[indice2][indice3] = 1;
            graph[indice3][indice1] = 1;
            graph[indice3][indice2] = 1;

            //Agora cria arestas entre os subgrafos pares e os vertices dos triangulos correspondentes.

            graph[indice4][indice1] = 1;
            graph[indice1][indice4] = 1;

            graph[indice5][indice2] = 1;
            graph[indice2][indice5] = 1;

            graph[indice6][indice3] = 1;
            graph[indice3][indice6] = 1;
        }

        return graph;
    }

    public static int[][] TxtToMatrix() throws IOException {

        BufferedReader firstFile = new BufferedReader(new FileReader("C:\\Users\\Ranniery\\Documents\\Computação\\PAA\\Trabalho MaxSat\\unWa-3SAT.txt"));

        String currentLine;
        String[] separatedLine;
        int nLines = NumberOfLines(firstFile);
        int[][] arquivo1 = new int[nLines][];
        int i = 0;
        int j = 0;

        while( (currentLine = firstFile.readLine()) != null){
            separatedLine = currentLine.split(" ");
            System.out.println(currentLine);

            for(String aux : separatedLine){
                arquivo1[i][j] = Integer.parseInt(aux);
                j++;
            }

            j = 0;
            i++;
        }

        return arquivo1;
    }

    private static int NumberOfLines (BufferedReader file) throws IOException{
        String aline;
        int count = 0;

        while((aline = file.readLine()) != null){count++;}

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
