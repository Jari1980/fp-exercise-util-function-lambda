package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.data.DataStorageImpl;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       TODO:  1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> "Erik".equals(person.getFirstName());
        List<Person> result = storage.findMany(filter);
        result.forEach(Person -> System.out.println(Person.getFirstName() + " " + Person.getLastName()));

        System.out.println("----------------------");
    }

    /*
        TODO:  2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> Gender.FEMALE.equals(person.getGender());
        List<Person> result = storage.findMany(filter);
        result.forEach(Person -> System.out.println(Person.getFirstName() + " " + Person.getLastName()));

        System.out.println("----------------------");
    }

    /*
        TODO:  3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getBirthDate().isAfter(LocalDate.of(2000,1,1));
        List<Person> result = storage.findMany(filter);
        result.forEach(Person -> System.out.println(Person.getFirstName() + " " + Person.getLastName()));

        System.out.println("----------------------");
    }

    /*
        TODO: 4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getId() == 123;
        Person person = storage.findOne(filter);
        System.out.println(person.toString());

        System.out.println("----------------------");

    }

    /*
        TODO:  5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getId() == 456;
        Function<Person, String> result = p -> "Name: " + p.getFirstName() + " " + p.getLastName() + " born " + p.getBirthDate();
        String res = storage.findOneAndMapToString(filter, result);
        System.out.println(res);

        System.out.println("----------------------");
    }

    /*
        TODO:  6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getFirstName().startsWith("E") && person.getGender() == Gender.MALE;
        Function<Person, String> result = p -> p.getFirstName() + " " + p.getLastName();
        List<String> list = storage.findManyAndMapEachToString(filter, result);

        list.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        TODO:  7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getBirthDate().isAfter(LocalDate.now().minusYears(10));
        Function<Person, String> result = p -> p.getFirstName() + " " + p.getLastName() + " " + (LocalDate.now().getYear() - p.getBirthDate().getYear()) + " years.";
        List<String> list = storage.findManyAndMapEachToString(filter, result);

        list.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> "Ulf".equals(person.getFirstName());
        Consumer<Person> consumer = c -> System.out.println(c.toString());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        TODO:  9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getLastName().contains(person.getFirstName());
        Consumer<Person> consumer = c -> System.out.println(c.toString());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        TODO:  10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> {
            String result = person.getFirstName().toLowerCase();
            int lenght = result.length();
            for(int i = 0; i < lenght / 2; i++){
                if(result.charAt(i) != result.charAt(lenght - 1 - i)){
                    return false;
                }
            }
            return true;
        };
        Consumer<Person> consumer = person -> System.out.println(person.toString());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }


    /*
        TODO:  11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getFirstName().startsWith("A");
        Comparator<Person> comparator = Comparator.comparing(person -> person.getBirthDate());
        List<Person> result = storage.findAndSort(filter, comparator);
        result.forEach(person -> System.out.println(person.toString()));
        System.out.println("----------------------");
    }

    /*
        TODO:  12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        Predicate<Person> filter = person -> person.getBirthDate().getYear() < 1950;
        Comparator<Person> comparator = Comparator.comparing(Person::getBirthDate).reversed();
        //Comparator<Person> comparator = Comparator.comparing(person -> person.getBirthDate()).reversed()
        List<Person> result = storage.findAndSort(filter, comparator);
        result.forEach(person -> System.out.println(person.toString()));

        System.out.println("----------------------");
    }

    /*
        TODO:  13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        Comparator<Person> comparator = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate);
        List<Person> result = storage.findAndSort(comparator);
        result.forEach(person -> System.out.println(person.toString()));

        System.out.println("----------------------");
    }
}
