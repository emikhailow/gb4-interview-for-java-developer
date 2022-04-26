package task2;

public class Task2App {

    /*2. Описать ошибки в коде и предложить варианты оптимизации:*/

    /*interface Moveable {
        void move();
    }
    interface Stopable {
        void stop();
    }
    abstract class Car {
        public Engine engine;
        private String color;
        private String name;
        protected void start() {
            System.out.println("Car starting");
        }
        abstract void open();
        public Engine getEngine() {
            return engine;
        }
        public void setEngine(Engine engine) {
            this.engine = engine;
        }
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    class LightWeightCar extends Car implements Moveable {
        @Override
        void open() {
            System.out.println("Car is open");
        }
        @Override
        public void move() {
            System.out.println("Car is moving");
        }
    }
    class Lorry extends Car, Moveable, Stopable {
        public void move(){
            System.out.println("Car is moving");
        }
        public void stop(){
            System.out.println("Car is stop");
        }
    }*/

    //1. Метод open лучше определить не в абстрактном классе Car, а в отдельном интерфейсе Openable
    //2. В классе Lorry нет реализации метода open, который является абстрактным в родительском классе,
    //Нужно его определить или класс должен быть тоже абстрактным
    //3. Класс Lorry зачем-то расширяет интерфейсы, хотя сам интерфейсом не является, логики в этом нет.
    //Необходимо указать implements для этих интерфейсов, поскольку класс просто реализует эти интерфейсы
    //4. Нет класса Engine

}



