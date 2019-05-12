package sample;

public class Figure {

    private int baseX;
    private int baseY;
    private FigureType figureType;

    public int getBaseX() {
        return baseX;
    }

    public int getBaseY() {
        return baseY;
    }

    public FigureType getFigureType() {
        return figureType;
    }

    public void move() {
        baseY++;
    }

    public void toLeft() {
        baseX--;
    }

    public void toRight() {
        baseX++;
    }

    public Figure(int typeNum, int baseX, int baseY) {
        this.baseX = baseX;
        this.baseY = baseY;
        switch (typeNum) {
            case 1:
                figureType = FigureType.TYPE1;
                break;
            case 2:
                figureType = FigureType.TYPE2;
                break;
            case 3:
                figureType = FigureType.TYPE3;
                break;
            case 4:
                figureType = FigureType.TYPE4;
                break;
            case 5:
                figureType = FigureType.TYPE5;
                break;
        }
    }
}
