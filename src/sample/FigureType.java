package sample;

public enum FigureType {
    TYPE1(-1, 0, 1, 0, 2, 0),
    TYPE2(-1, 0, 1, 0, 0, 1),
    TYPE3(1, 0, 0, 1, 1, 1),
    TYPE4(-1, 0, 0, 1, 1, 1),
    TYPE5(-2, 0, -1, 0, 0, 1);

    int[] xs = new int[3];
    int[] ys = new int[3];

    FigureType(int x1, int y1, int x2, int y2, int x3, int y3) {
        xs[0] = x1; ys[0] = y1;
        xs[1] = x2; ys[1] = y2;
        xs[2] = x3; ys[2] = y3;
    }

    private int[] seq1x = {-1, 0, 1, 0};
    private int[] seq1y = {0, -1, 0, 1};
    private int[] seq2x = {1, -1, -1, 1};
    private int[] seq2y = {1, 1, -1, -1};
    private int[] seq3x = {2, 0, -2, 0};
    private int[] seq3y = {0, 2, 0, -2};

    public void rotate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++)  {
                if (xs[i] == seq1x[j] && ys[i] == seq1y[j]) {
                    xs[i] = seq1x[(j + 1) % 4];
                    ys[i] = seq1y[(j + 1) % 4];
                    break;
                }
                else if (xs[i] == seq2x[j] && ys[i] == seq2y[j]) {
                    xs[i] = seq2x[(j + 1) % 4];
                    ys[i] = seq2y[(j + 1) % 4];
                    break;
                }
                else if (xs[i] == seq3x[j] && ys[i] == seq3y[j]) {
                    xs[i] = seq3x[(j + 1) % 4];
                    ys[i] = seq3y[(j + 1) % 4];
                    break;
                }
            }
        }
    }

    public void rotateBack() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++)  {
                if (xs[i] == seq1x[j] && ys[i] == seq1y[j]) {
                    xs[i] = seq1x[(j - 1) < 0 ? 3 : j - 1];
                    ys[i] = seq1y[(j - 1) < 0 ? 3 : j - 1];
                    break;
                }
                else if (xs[i] == seq2x[j] && ys[i] == seq2y[j]) {
                    xs[i] = seq2x[(j - 1) < 0 ? 3 : j - 1];
                    ys[i] = seq2y[(j - 1) < 0 ? 3 : j - 1];
                    break;
                }
                else if (xs[i] == seq3x[j] && ys[i] == seq3y[j]) {
                    xs[i] = seq3x[(j - 1) < 0 ? 3 : j - 1];
                    ys[i] = seq3y[(j - 1) < 0 ? 3 : j - 1];
                    break;
                }
            }
        }
    }
}
