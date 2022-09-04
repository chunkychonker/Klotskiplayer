package org.steve;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Klotskiplayer {

    private static int BOARD_HEIGHT = 5;

    private static int BOARD_WIDTH = 4;

    public static void main(String[] args) {
        System.out.println(bfs());
    }

    public static int bfs() {
        Queue<int[][]> queue= new LinkedList();
        Queue<Integer> depthQueue = new LinkedList<>();
        HashSet<KlotskiState> visited = new HashSet<KlotskiState>();
        int[][] e = {
                {2, 1, 1, 2},
                {2, 1, 1, 2},
                {2, 3, 3, 2},
                {2, 4, 4, 2},
                {0, 4, 4, 0}
        };
        KlotskiState initialState = new KlotskiState(e);
        queue.add(e);
        depthQueue.add(0);
        visited.add(initialState);
        while (!queue.isEmpty()) {
            int[][] node;
            int depth;
            boolean[][] checked = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
            node = queue.remove();
            depth = depthQueue.remove();
            System.out.println(String.format("queue size %d, depth = %d", queue.size(), depth));
            if (node[4][1] == 1 && node[4][2] == 1) {
                return depth;
            }
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    if (checked[i][j]) {
                        continue;
                    }
                    int width = 0;
                    int height = 0;
                    switch(node[i][j]) {
                        case 0:
                            continue;
                        case 1:
                            width = 2;
                            height = 2;
                            break;
                        case 2:
                            width = 1;
                            height = 2;
                            break;
                        case 3:
                            width = 2;
                            height = 1;
                            break;
                        case 4:
                            width = 1;
                            height = 1;
                            break;
                    }
                    if (height == 0) {
                        continue;
                    }

                    for (int ii  = 0; ii < height; ii++) {
                        for (int jj = 0; jj < width; jj++) {
                            checked[i + ii][j + jj] = true;
                        }
                    }

                    // check if you can move left.
                    if (j > 0 && node[i][j - 1] == 0 && (height == 1 || node[i + 1][j - 1] == 0)) {
                        int[][] neighbor = new int[BOARD_HEIGHT][BOARD_WIDTH];
                        copyBoard(node, neighbor);
                        for (int ii = 0; ii < height; ii++) {
                            for (int jj = 0; jj < width; jj++) {
                                neighbor[i + ii][j + jj - 1] = neighbor[i + ii][j + jj];
                            }
                            neighbor[i + ii][j + width - 1] = 0;
                        }
                        // check if neighbor has already been visited, if not visited, add it to the queue
                        boolean found = isVisited(visited, neighbor);
                        if (!found) {
                            queue.add(neighbor);
                            visited.add(new KlotskiState(neighbor));
                            depthQueue.add(depth + 1);
                        }
                    }
                    // check if you can move up
                    if (i > 0 && node[i - 1][j] == 0 && (width == 1 || node[i - 1][j + 1] == 0)) {
                        int[][] neighbor = new int[BOARD_HEIGHT][BOARD_WIDTH];
                        copyBoard(node, neighbor);
                        for (int jj = 0; jj < width; jj++) {
                            for (int ii = 0; ii < height; ii++) {
                                neighbor[i + ii - 1][j + jj] = neighbor[i + ii][j + jj];
                            }
                            neighbor[i + height - 1][j + jj] = 0;
                        }
                        // check if neighbor has already been visited, if not visited, add it to the queue
                        boolean found = isVisited(visited, neighbor);
                        if (!found) {
                            queue.add(neighbor);
                            visited.add(new KlotskiState(neighbor));
                            depthQueue.add(depth + 1);
                        }
                    }
                    // check if block can move down
                    if (i + height < node.length && node[i + height][j] == 0 && (width == 1 || node[i + height][j + 1] == 0)) {
                        int[][] neighbor = new int[BOARD_HEIGHT][BOARD_WIDTH];
                        copyBoard(node, neighbor);
                        for (int jj = 0; jj < width; jj++) {
                            for (int ii = height - 1; ii >= 0; ii--) {
                                neighbor[i + ii + 1][j + jj] = neighbor[i + ii][j + jj];
                            }
                            neighbor[i][j + jj] = 0;
                        }
                        boolean found = isVisited(visited, neighbor);
                        if(!found) {
                            queue.add(neighbor);
                            visited.add(new KlotskiState(neighbor));
                            depthQueue.add(depth + 1);
                        }
                    }

                    if (j + width < node[0].length && node[i][j + width] == 0 && (height == 1 || node[i + 1][j + width] == 0)) {
                        int[][] neighbor = new int[BOARD_HEIGHT][BOARD_WIDTH];
                        copyBoard(node, neighbor);
                        for (int ii = 0; ii < height; ii++) {
                            for (int jj = width - 1; jj >= 0; jj--) {
                                neighbor[i + ii][j + jj + 1] = neighbor[i + ii][j + jj];
                            }
                            neighbor[i + ii][j] = 0;
                        }
                        boolean found = isVisited(visited, neighbor);
                        if(!found) {
                            queue.add(neighbor);
                            visited.add(new KlotskiState(neighbor));
                            depthQueue.add(depth + 1);
                        }
                    }
                }
            }
        }
        System.out.println("Unable to find paths");
        return Integer.MAX_VALUE;
    }

    private static boolean isVisited(HashSet<KlotskiState> visited, int[][] neighbor) {
        KlotskiState newKlotskiState = new KlotskiState(neighbor);
        return visited.contains(newKlotskiState);
    }

    private static void copyBoard (int[][] source, int[][] dest) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }
}
