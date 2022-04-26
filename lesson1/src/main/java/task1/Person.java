package task1;

public class Person {

    private String firstName;
    private String lastName;
    private  String middleName;
    private  String country;
    private  String address;
    private String phone;
    private int age;
    private String gender;

    public Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.country = builder.country;
        this.address = builder.address;
        this.phone = builder.phone;
        this.age = builder.age;
        this.gender = builder.gender;
    }

    public static class Builder{

        private String firstName;
        private String lastName;
        private  String middleName;
        private  String country;
        private  String address;
        private String phone;
        private int age;
        private String gender;

        public Builder() {
        }

        public Builder fullName(String firstName, String middleName, String lastName){
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            return this;
        }

        public Builder contactInfo(String country, String address, String phone){
            this.country = country;
            this.address = address;
            this.phone = phone;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Builder gender(String gender){
            this.gender = gender;
            return this;
        }

        public Person build(){
            return new Person(this);
        }

    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
