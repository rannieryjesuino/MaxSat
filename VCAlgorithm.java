package testelerarquivo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ranniery on 16/02/2016.
 */
public class VCAlgorithm {
	public static int VertexCover(int[][] matrix) {

		// DUVIDO Q ESSA PARTE SEJA MUITO UTIL OU SEMELHANTE
		// POIS JA TEMOS A MATRIZ DE REPRESENTAÇÃO DO GRAFO
		// EM FORMA DE INT[][]

		System.out.println("Vertex Cover Algorithm.");
		int n = matrix.length, i, j, k = 1, p, q, r, s, min, counter = 0;
		int[][] graph = matrix;

		// Encontrar vizinhos
		List<List<Integer>> neighbors = new ArrayList<>();
		for (i = 0; i < graph.length; i++) {
			List<Integer> neighbor = new ArrayList<>();
			for (j = 0; j < graph[i].length; j++) {
				if (graph[i][j] == 1) {
					neighbor.add(j);
				}
			}
			neighbors.add(neighbor);
		}

		System.out.println("Graph has n = " + n + " vertices.");
		// Ler tamanho minimo de Vertex Cover pedido
		System.out.println("Find a Vertex Cover of size at most k = ");
		// cin >> k;

		// Encontrando Coberturas de Vértices
		boolean found = false;
		System.out.println("Finding Vertex Covers...");
		min = n + 1;
		List<List<Integer>> covers = new ArrayList<>();
		List<Integer> allcover = new ArrayList<>();
		for (i = 0; i < graph.length; i++)
			allcover.add(1);
		for (i = 0; i < allcover.size(); i++) {
			if (found) {
				break;
			}
			counter++;
			System.out.println(counter + ". ");
			List<Integer> cover = allcover;
			cover.set(i, 0);
			cover = procedure_1(neighbors, cover);
			s = cover_size(cover);
			if (s < min)
				min = s;
			if (s <= k) {
				for (j = 0; j < cover.size(); j++) {
					if (cover.get(j) == 1) {
						System.out.println("Vertex Cover Size: " + s);
					}
				}
				covers.add(cover);
				found = true;
				break;
			}
			for (j = 0; j < n - k; j++)
				cover = procedure_2(neighbors, cover, j);
			s = cover_size(cover);
			if (s < min)
				min = s;
			for (j = 0; j < cover.size(); j++) {
				if (cover.get(j) == 1) {
					System.out.println("Vertex Cover Size: " + s);
				}
			}
			covers.add(cover);
			if (s <= k) {
				found = true;
				break;
			}
		}

		// Pairwise Unions
		for (p = 0; p < covers.size(); p++) {
			if (found)
				break;
			for (q = p + 1; q < covers.size(); q++) {
				if (found)
					break;
				counter++;
				System.out.println(counter + ". ");
				List<Integer> cover = allcover;
				for (r = 0; r < cover.size(); r++) {
					if (covers.get(p).get(r) == 0 && covers.get(q).get(r) == 0) {
						cover.set(r, 0);
					}
				}
				cover = procedure_1(neighbors, cover);
				s = cover_size(cover);
				if (s < min)
					min = s;
				if (s <= k) {
					for (j = 0; j < cover.size(); j++) {
						if (cover.get(j) == 1) {
							System.out.println("Vertex Cover Size: " + s);
						}
					}
					found = true;
					break;
				}
				for (j = 0; j < k; j++)
					cover = procedure_2(neighbors, cover, j);
				s = cover_size(cover);
				if (s < min)
					min = s;
				for (j = 0; j < cover.size(); j++)
					if (cover.get(j) == 1)
						System.out.println("Vertex Cover Size: " + s);
				if (s <= k) {
					found = true;
					break;
				}
			}
		}
		if (found) {
			System.out.println("Found Vertex Cover of size at most " + k + ".");
		} else {
			System.out.println("Could not find Vertex Cover of size at most " + k + "."
					+ "\nMinimum Vertex Cover size found is " + min + ".");
		}
		return 0;
	}

	static boolean removable(List<Integer> list, List<Integer> temp_cover2) {
		boolean check = true;
		for (int i = 0; i < list.size(); i++)
			if (temp_cover2.get(list.get(i)) == 0) {
				check = false;
				break;
			}
		return check;
	}

	static int max_removable(List<List<Integer>> neighbors, List<Integer> temp_cover2) {
		int r = -1, max = -1;
		for (int i = 0; i < temp_cover2.size(); i++) {
			if (temp_cover2.get(i) == 1 && removable(neighbors.get(i), temp_cover2) == true) {
				List<Integer> temp_cover = temp_cover2;
				temp_cover.set(i, 0);
				int sum = 0;
				for (int j = 0; j < temp_cover.size(); j++)
					if (temp_cover.get(j) == 1 && removable(neighbors.get(j), temp_cover) == true)
						sum++;
				if (sum > max) {
					max = sum;
					r = i;
				}
			}
		}
		return r;
	}

	static List<Integer> procedure_1(List<List<Integer>> neighbors, List<Integer> cover) {
		List<Integer> temp_cover = cover;
		int r = 0;
		while (r != -1) {
			r = max_removable(neighbors, temp_cover);
			if (r != -1)
				temp_cover.set(r, 0);
		}
		return temp_cover;
	}

	static List<Integer> procedure_2(List<List<Integer>> neighbors, List<Integer> cover, int k) {
		int count = 0;
		List<Integer> temp_cover = cover;
		int i = 0;
		for (i = 0; i < temp_cover.size(); i++) {
			if (temp_cover.get(i) == 1) {
				int sum = 0, index = 0;
				for (int j = 0; j < neighbors.get(i).size(); j++)
					if (temp_cover.get(neighbors.get(i).get(j)) == 0) {
						index = j;
						sum++;
					}
				if (sum == 1 && cover.get(neighbors.get(i).get(index)) == 0) {
					temp_cover.get(neighbors.get(i).set(index, 1));
					temp_cover.set(i, 0);
					temp_cover = procedure_1(neighbors, temp_cover);
					count++;
				}
				if (count > k)
					break;
			}
		}
		return temp_cover;
	}

	static int cover_size(List<Integer> cover) {
		int count = 0;
		for (int i = 0; i < cover.size(); i++)
			if (cover.get(i) == 1)
				count++;
		return count;
	}
}
