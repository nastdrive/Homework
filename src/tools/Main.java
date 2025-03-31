package tools;

import animals.Animal;
import animals.birds.Duck;
import animals.pets.Cat;
import animals.pets.Dog;
import data.AnimalTypeData;
import data.ColorData;
import data.CommandsData;
import factory.AnimalFactory;
import tools.NumberTools;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        NumberTools numberTools = new NumberTools();

        while (true) {
            List<String> nameStr = new ArrayList<>();
            for (CommandsData commandsData : CommandsData.values()) {
                nameStr.add(commandsData.name().toLowerCase());
            }

            System.out.println(String.format("Введите команду: %s", String.join("/", nameStr)));

            String userCommand = scan.next().trim();
            String userCommandsUpperCase = userCommand.toUpperCase();

            boolean isCommandExist = false;
            for (CommandsData commandsData : CommandsData.values()) {
                if (userCommandsUpperCase.equals(commandsData.name())) {
                    isCommandExist = true;
                    break;
                }
            }

            if (!isCommandExist) {
                System.out.printf("Команда %s не поддерживается\n", userCommand);
                continue;
            }

                    switch (CommandsData.valueOf(userCommandsUpperCase)) {
                    case ADD: {
                    addAnimal (animals,numberTools);
                    break;
                    }
                    case LIST: {
                        for (Animal animal : animals) {
                            System.out.println(animal.toString());
                        }
                        break;
                    }
                    case EXIT: {
                        System.exit(0);
                    }
                }
            }
        }
            private static void addAnimal(List<Animal> animals, NumberTools numberTools) {

            while (true) {
            System.out.println("Какое животное добавить? cat/dog/duck");
            String typeAnimal = scan.next().trim().toUpperCase();

            AnimalTypeData type;
            try {
                type = AnimalTypeData.valueOf(String.valueOf(typeAnimal));
            } catch (IllegalArgumentException e) {
                System.out.println("Такого животного нет");
                continue;
            }

            System.out.println("Введите имя животного");
            String name = scan.next().trim();

            int age;

            while (true) {
                System.out.println("Введите возраст");
                String ageInput = scan.next().trim();

                if (numberTools.isNumber(ageInput)) {
                    age = Integer.parseInt(ageInput);
                    break;
                } else {
                    System.out.println("Ошибка! Введите целое число от 1 до 99");
                }
            }
                int weight;

                while (true) {
                    System.out.println("Введите вес");
                    String weightInput = scan.next().trim();

                    if (numberTools.isNumber(weightInput)) {
                        weight = Integer.parseInt(weightInput);
                        break;
                    } else {
                        System.out.println("Ошибка! Введите целое число от 1 до 99");
                    }
                }

                ColorData color;

                    while (true) {
                        System.out.println("Введите цвет black/white/brown");
                        String colorCommand = scan.next().trim().toUpperCase();
                        try {
                            color = ColorData.valueOf(String.valueOf(colorCommand));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Такого цвета нет");
                            continue;
                        }

                    AnimalFactory factory = new AnimalFactory(name, age, weight, color);
                    Animal animal = factory.create(type);
                    animals.add(animal);
                    System.out.println("Животное добавлено");
                    animal.say();
                    break;
                }
                break;
            }
            }
        }





