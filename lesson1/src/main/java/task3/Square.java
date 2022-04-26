package task3;

public class Square extends Shape{

    private double side;

    public Square(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    double area() {
        return Math.pow(side, 2);
    }
}
