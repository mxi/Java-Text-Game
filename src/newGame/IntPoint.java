package newGame;

public class IntPoint {

    public int x;
    public int y;

    public IntPoint() {
        x = 0;
        y = 0;
    }

    public IntPoint(int nx, int ny) {
        x = nx;
        y = ny;
    }

    public boolean isEquivalentTo(IntPoint p) {
        return p.getX() == x && p.getY() == y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
