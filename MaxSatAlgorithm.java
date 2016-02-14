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

        int[][] graph = new int[nVars*2][nVars*2];
        graph = zerarMatriz(graph, nVars*2);

        int valor1;
        int valor2;
        int valor3;

        for(int i = 0 ; i < nVars*2; i++){
            for(int j = 0; j < 3; j++){
                valor1 = matrix[i][0];
                if(valor1 < 0) {valor1 = nVars-1 + abs(valor1);}
                valor2 = matrix[i][1];
                if(valor2 < 0) {valor2 = nVars-1 + abs(valor2);}
                valor3 = matrix[i][2];
                if(valor3 < 0) {valor3 = nVars-1 + abs(valor3);}

                graph[valor1][valor2] = 1;
                graph[valor1][valor3] = 1;
                graph[valor2][valor1] = 1;
                graph[valor2][valor3] = 1;
                graph[valor3][valor1] = 1;
                graph[valor3][valor2] = 1;
            }
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
