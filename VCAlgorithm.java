package br.uece.lotus.tools.MaxSat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ranniery on 16/02/2016.
 */
public class VCAlgorithm {
    public static void VertexCover(int[][] matrix) {

        //DUVIDO Q ESSA PARTE SEJA MUITO UTIL OU SEMELHANTE
        //POIS JA TEMOS A MATRIZ DE REPRESENTAÇÃO DO GRAFO
        //EM FORMA DE INT[][]
        //Ler grafo
        System.out.println("Vertex Cover Algorithm.");
        int n = 0, i, j, k = 0, p , q, r, s, min, edge, counter = 0;
        //infile >> n;                    ???????
        //Encontrar vizinhos
        int[][] graph = matrix;
        /*
        for (i = 0; i < graph.length; i++) {
            int[] row;
            for(j = 0; j < n; j++) {
                //infile >> edge;
                //row.push_back(edge);
            }
            //graph.push_back(row);
        }
        */

        //Encontrar vizinhos
        List<Integer[]> neighbors2 = new ArrayList<>();
        Integer[][] neighbors = null;
        for (i=0; i<graph.length; i++) {
            //int[] neighbor;
            List<Integer> neighbor = new ArrayList<>();
            for (j=0; j < graph[i].length; j++) {
                if (graph[i][j] == 1) {
                    neighbor.add(j);
                    //neighbor.push_back(j);
                }
            }
            Integer[] simpleArray = new Integer[neighbor.size()];
            neighbor.toArray(simpleArray);
            //neighbors.push_back(neighbor);
            neighbors2.add(simpleArray);
        }

        Integer[][] simpleArray2 = new Integer[neighbors2.size()][];
        neighbors2.toArray(simpleArray2);
        neighbors = simpleArray2;

        System.out.println("Graph has n = " + n + " vertices.");
        //Ler tamanho minimo de Vertex Cover pedido
        System.out.println("Find a Vertex Cover of size at most k = ");
        //cin >> k;

        //Find Vertex Covers
        boolean found = false;
        System.out.println("Finding Vertex Covers...");
        min = n + 1;
        List<Integer[]> covers2 = new ArrayList<>();
        Integer[][] covers = null;
        List<Integer> allcover2 = new ArrayList<>();
        Integer[] allcover = null;
        for (i = 0; i < graph.length; i++)
            //allcover.push_back(1);
            allcover2.add(1);
            for (i = 0; i < allcover.length; i++) {
                if (found)
                    break;
                counter++;
                System.out.println(counter + ". ");
                //outfile << counter << ". ";
                Integer[] simpleArray = new Integer[allcover2.size()];
                allcover2.toArray(simpleArray);
                Integer[] cover = allcover = simpleArray;
                cover[i] = 0;
                cover = procedure_1(neighbors, cover);
                s = cover_size(cover);
                if (s < min)
                    min = s;
                if (s <= k) {
                    //outfile << "Vertex Cover (" << s << "): ";
                    for (j = 0; j < cover.length; j++)
                        if (cover[j] == 1)
                            //outfile << j + 1 << " ";
                            //outfile << endl;
                            System.out.println("Vertex Cover Size: " + s);
                    //covers.push_back(cover);
                    covers2.add(cover);
                    found = true;
                    break;
                }
                for (j = 0; j < n - k; j++)
                    cover = procedure_2(neighbors, cover, j);
                s = cover_size(cover);
                if (s < min)
                    min = s;
                //outfile << "Vertex Cover (" << s << "): ";
                for (j = 0; j < cover.length; j++)
                    if (cover[j] == 1)
                        //outfile << j + 1 << " ";
                        //outfile << endl;
                        System.out.println("Vertex Cover Size: " + s);
                //covers.push_back(cover);
                covers2.add(cover);
                if (s <= k) {
                    found = true;
                    break;
                }
            }

        Integer[][] simpleArray3 = new Integer[covers2.size()][];
        covers2.toArray(simpleArray3);
        covers = simpleArray3;

        //Pairwise Unions
        for (p = 0; p < covers.length; p++) {
            if (found)
                break;
            for (q = p + 1; q < covers.length; q++) {
                if (found)
                    break;
                counter++;
                System.out.println(counter + ". ");
                //outfile << counter << ". ";
                Integer[] cover = allcover;
                for (r = 0; r < cover.length; r++)
                    if (covers[p][r] == 0 && covers[q][r] == 0)
                        cover[r] = 0;
                cover = procedure_1(neighbors, cover);
                s = cover_size(cover);
                if (s < min)
                    min = s;
                if (s <= k) {
                    //outfile << "Vertex Cover (" << s << "): ";
                    for (j = 0; j < cover.length; j++)
                        if (cover[j] == 1)
                            //outfile << j + 1 << " ";
                            //outfile << endl;
                            System.out.println("Vertex Cover Size: " + s);
                    found = true;
                    break;
                }
                for (j = 0; j < k; j++)
                    cover = procedure_2(neighbors, cover, j);
                s = cover_size(cover);
                if (s < min)
                    min = s;
                //outfile << "Vertex Cover (" << s << "): ";
                for (j = 0; j < cover.length; j++)
                    if (cover[j] == 1)
                        //outfile << j + 1 << " ";
                        //outfile << endl;
                        System.out.println("Vertex Cover Size: " + s);
                if (s <= k) {
                    found = true;
                    break;
                }
            }
        }
        if (found)
            System.out.println("Found Vertex Cover of size at most " + k + ".");
        else
            System.out.println("Could not find Vertex Cover of size at most " + k + "." +
                    "\nMinimum Vertex Cover size found is " + min + ".");
    }

    static boolean removable(Integer[] neighbor, Integer[] cover) {
        boolean check = true;
        for (int i = 0; i < neighbor.length; i++)
            if (cover[neighbor[i]] == 0) {
                check = false;
                break;
            }
        return check;
    }

    static int max_removable(Integer[][] neighbors, Integer[] cover) {
        int r = -1, max = -1;
        for (int i = 0; i < cover.length; i++) {
            if (cover[i] == 1 && removable(neighbors[i], cover) == true) {
                Integer[] temp_cover = cover;
                temp_cover[i] = 0;
                int sum = 0;
                for (int j = 0; j < temp_cover.length; j++)
                    if (temp_cover[j] == 1
                            && removable(neighbors[j], temp_cover) == true)
                        sum++;
                if (sum > max) {
                    max = sum;
                    r = i;
                }
            }
        }
        return r;
    }

    static Integer[] procedure_1(Integer[][] neighbors, Integer[] cover) {
        Integer[] temp_cover = cover;
        int r = 0;
        while (r != -1) {
            r = max_removable(neighbors, temp_cover);
            if (r != -1)
                temp_cover[r] = 0;
        }
        return temp_cover;
    }

    static Integer[] procedure_2(Integer[][] neighbors, Integer[] cover,
                             int k) {
        int count = 0;
        Integer[] temp_cover = cover;
        int i = 0;
        for (i = 0; i < temp_cover.length; i++) {
            if (temp_cover[i] == 1) {
                int sum = 0, index = 0;
                for (int j = 0; j < neighbors[i].length; j++)
                    if (temp_cover[neighbors[i][j]] == 0) {
                        index = j;
                        sum++;
                    }
                if (sum == 1 && cover[neighbors[i][index]] == 0) {
                    temp_cover[neighbors[i][index]] = 1;
                    temp_cover[i] = 0;
                    temp_cover = procedure_1(neighbors, temp_cover);
                    count++;
                }
                if (count > k)
                    break;
            }
        }
        return temp_cover;
    }

    static int cover_size(Integer[] cover) {
        int count = 0;
        for (int i = 0; i < cover.length; i++)
            if (cover[i] == 1)
                count++;
        return count;
    }
}
