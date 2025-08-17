import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

class Solution {
    public static class Coordinate {
        private int row;
        private int col;
        private int val;
        
        public Coordinate(int row, int col, int val) {
            this.row = row;
            this.col = col;
        }
        
        public int getRow() {
            return row;
        }
        
        public int getCol() {
            return col;
        }
        
        public int getVal() {
            return val;
        }
    }

    public static List<List<Integer>> floodFill(int r, int c, int replacement, List<List<Integer>> image) {
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        int initalValue = image.get(r).get(c);
        queue.add(new Coordinate(r, c, initalValue));
        boolean[][] visited = new boolean[image.size()][image.get(0).size()];
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            Coordinate curr = queue.poll();
            if (curr.getVal() == initalValue) {
                image.get(curr.getRow()).set(curr.getCol(), replacement);
            }
            List<Coordinate> neighbors = getNeighbors(curr, image);
            for (Coordinate neighbor : neighbors) {
                int row = neighbor.getRow();
                int col = neighbor.getCol();
                if (!visited[row][col]) {
                    queue.add(neighbor);
                    visited[row][col] = true;
                }
            }
        }
        
        return image;
    }

    public static List<Coordinate> getNeighbors(Coordinate coord, List<List<Integer>> image) {
        int[] rowOffset = {-1, 0, 1, 0};
        int[] colOffset = {0, 1, 0, -1};
        int row = coord.getRow();
        int col = coord.getCol();
        List<Coordinate> res = new ArrayList<Coordinate>();
        for (int i = 0; i < 4; ++i) {
            int newRow = row + rowOffset[i];
            int newCol = col + colOffset[i];
            if (0 <= newRow && newRow < image.size() && 0 <= newCol && newCol < image.get(0).size()) {
                res.add(new Coordinate(newRow, newCol, image.get(newRow).get(newCol)));
            }
        }

        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            File testInput = new File("./input.txt");
            Scanner scan = new Scanner(testInput);
            int r = Integer.parseInt(scan.nextLine());
            int c = Integer.parseInt(scan.nextLine());
            int replacement = Integer.parseInt(scan.nextLine());
            int imageLength = Integer.parseInt(scan.nextLine());
            List<List<Integer>> image = new ArrayList<>();
            for (int i = 0; i < imageLength; i++) {
                image.add(splitWords(scan.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
            }
            scan.close();
            List<List<Integer>> res = floodFill(r, c, replacement, image);
            for (List<Integer> row : res) {
                System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
            }
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
