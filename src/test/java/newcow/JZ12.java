package newcow;

public class JZ12 {

    private int length;
    private int width;
    /**
     * 标记之前是否已经走过该点，“是”为ture
     */
    private boolean[][] flag;
    /**
     * x探针，实时探测横向位置，每轮清零
     */
    private int xProbe;
    /**
     * y探针，实时探测纵向位置，每轮清零
     */
    private int yProbe;

    private char[] chars;


    public boolean hasPath(char[][] matrix, String word) {
        if (matrix == null) {
            return false;
        }

        length = matrix[0].length;
        width = matrix.length;

        flag = new boolean[width][length];
        chars = word.toCharArray();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j] == chars[0]) {
                    xProbe = i;
                    yProbe = j;
                    if (dfsExplore(matrix, xProbe, yProbe, 0)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfsExplore(char[][] matrix, int x, int y, int index) {
        if (x < 0 || y < 0 || x >= width || y >= length || matrix[x][y] != chars[index]) {
            return false;
        }

        if (matrix[x][y] == chars[index]) {
            index++;
        }

        if (index == chars.length - 1) {
            return true;
        }

        dfsExplore(matrix, x, ++y, index);
        dfsExplore(matrix, ++x, y, index);
        dfsExplore(matrix, x, --y, index);
        dfsExplore(matrix, --x, y, index);

        return false;

//        if (x + 1 < width && matrix[x + 1][y] == chars[index] && flag[x + 1][y] == false) {
//            flag[x + 1][y] = true;
//            xProbe++;
//            index++;
//            return dfsExplore(matrix, xProbe, yProbe, index);
//        }
//
//        if (x - 1 >= 0 && matrix[x - 1][y] == chars[index] && flag[x - 1][y] == false) {
//            flag[x - 1][y] = true;
//            xProbe--;
//            index++;
//            return dfsExplore(matrix, xProbe, yProbe, index);
//        }
//
//        if (y + 1 < length && matrix[x][y + 1] == chars[index] && flag[x][y + 1] == false) {
//            flag[x][y + 1] = true;
//            yProbe++;
//            index++;
//            return dfsExplore(matrix, xProbe, yProbe, index);
//        }
//
//        if (y - 1 >= 0 && matrix[x][y - 1] == chars[index] && flag[x][y - 1] == false) {
//            flag[x][y - 1] = true;
//            yProbe--;
//            index++;
//            return dfsExplore(matrix, xProbe, yProbe, index);
//        }



//        dfsExplore(matrix, ++x, y, ++index);
//        dfsExplore(matrix, --x, y, ++index);
//        dfsExplore(matrix, x, ++y, ++index);
//        dfsExplore(matrix, x, --y, ++index);
    }

//    private boolean explore(char[][] matrix, int x, int y, char target) {
//        if (x + 1 < width && matrix[x + 1][y] == target && flag[x + 1][y] == false) {
//            flag[x + 1][y] = true;
//            xProbe++;
//            return true;
//        }
//
//        if (x - 1 >= 0 && matrix[x - 1][y] == target && flag[x - 1][y] == false) {
//            flag[x - 1][y] = true;
//            xProbe--;
//            return true;
//        }
//
//        if (y + 1 < length && matrix[x][y + 1] == target && flag[x][y + 1] == false) {
//            flag[x][y + 1] = true;
//            yProbe++;
//            return true;
//        }
//
//        if (y - 1 >= 0 && matrix[x][y - 1] == target && flag[x][y - 1] == false) {
//            flag[x][y - 1] = true;
//            yProbe--;
//            return true;
//        }
//
//        return false;
//    }

    public static void main(String[] args) {
        char[][] matrex = {{'a', 'b', 'c', 'e'},
                           {'s', 'f', 'c', 'f'},
                           {'a', 'd', 'e', 'e'}};
        String word = "abccfs";

        System.out.println(new JZ12().hasPath(matrex, word));
    }
}
