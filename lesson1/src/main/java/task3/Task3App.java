package task3;

import java.util.ArrayList;
import java.util.List;

public class Task3App {

    public static void main(String[] args) {

       /* 3. Написать пример кода, который реализует принцип полиморфизма,
       на примере фигур — круг, квадрат, треугольник*/

        List<Shape> shapes = new ArrayList<>();

        shapes.add(new Circle(5));
        shapes.add(new Square(4));
        shapes.add(new Triangle(5, 3));

        for (Shape shape : shapes) {
            System.out.println(shape.area());
        }

    }

}
