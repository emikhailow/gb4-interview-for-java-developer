package task1;

public class Task1App {

    public static void main(String[] args) {

        /* 1. Создать builder для класса Person со следующими полями:
        String firstName, String lastName, String middleName,
        String country, String address, String phone, int age, String gender*/

        Person person = new Person.Builder()
                .fullName("Evgenii", "Vladimirovich", "Mikhailov")
                .contactInfo("Russian Federation", "Moscow, Krasnaya Ploshad, 1", "+79123456789")
                .age(30)
                .gender("M")
                .build();

        System.out.println(person);

    }

}
