package tools;

import animals.Animal;
import animals.birds.Duck;
import animals.pets.Cat;
import animals.pets.Dog;
import data.AnimalTypeData;
import data.ColorData;
import data.CommandsData;
import factory.AnimalFactory;
import tables.AnimalTable;
import tools.NumberTools;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        NumberTools numberTools = new NumberTools();
        AnimalTable animalTable = new AnimalTable();
        try {
            animalTable.createTable();
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы" + e.getMessage());
            e.printStackTrace();
        }
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
                    addAnimal(animals, numberTools, animalTable);
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
                case DBLIST: {
                    try {
                        List<Animal> FromDb = animalTable.getAllAnimals();
                        if (FromDb.isEmpty()) {
                            System.out.println("В базе данных нет животных");
                        } else {
                            for (Animal animal : FromDb) {
                                System.out.println(animal);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка при получении списка животных из базы данных");
                    }
                    break;
                }
                case DBEDIT: {
                    System.out.println("Введите ID животного, которое хотите отредактировать");
                    int id = Integer.parseInt(scan.next().trim());

                    System.out.println("Введите новое имя");
                    String name = scan.next().trim();

                    int age;
                    while (true) {
                        System.out.println("Введите возраст");
                        String input = scan.next().trim();
                        if (numberTools.isNumber(input)) {
                            age = Integer.parseInt(input);
                            break;
                        } else {
                            System.out.println("Ошиька! Введите число от 1 до 99");
                        }
                    }

                    int weight;
                    while (true) {
                        System.out.println("Введите вес");
                        String inpWeight = scan.next().trim();
                        if (numberTools.isNumber(inpWeight)) {
                            weight = Integer.parseInt(inpWeight);
                            break;
                        } else {
                            System.out.println("Ошибка! Введите число от 1 до 99");
                        }
                    }
                    ColorData colorData;
                    while (true) {
                        System.out.println("Введите цвет: black/white/brown");
                        String colorCommand = scan.next().trim().toUpperCase();
                        try {
                            colorData = ColorData.valueOf(colorCommand);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Такого цвета нет");
                        }
                    }
                    try {
                        animalTable.updateAnimal(id, name, age, weight, colorData);
                        System.out.println("Животное обновлено");
                    } catch (Exception e) {
                        System.out.println("Ошибка при обновлении");
                        e.printStackTrace();
                    }
                    break;
                }
                case DBFILTER: {
                    AnimalTypeData type = null;
                    while (true) {
                        System.out.println("Введите тип животного");
                        String typeInput = scan.next().trim().toUpperCase();
                        try {
                            type = AnimalTypeData.valueOf(typeInput);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Такого типа нет");
                        }
                    }
                    try {
                        List<Animal> filteredAnimals = animalTable.getAnimalByType(type.name().toLowerCase());
                        if (filteredAnimals.isEmpty()) {
                            System.out.println("Животных такого типа нет");
                        } else {
                            for (Animal animal : filteredAnimals) {
                                System.out.println(animal);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка при фильтрации");
                    }
                    break;
                }
            }
        }
    }

    private static void addAnimal(List<Animal> animals, NumberTools numberTools, AnimalTable animalTable) {

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
            if (name.isEmpty()) {
                System.out.println("Имя не может быть пустым");
                continue;
            }

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
                    color = ColorData.valueOf(colorCommand);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Такого цвета нет");
                }
            }

            AnimalFactory factory = new AnimalFactory(name, age, weight, color);
            Animal animal = factory.create(type);
            animals.add(animal);
            try {
                animalTable.insertAnimal(animal);
                System.out.println("Животное сохранено в базе данных");
            } catch (Exception e) {
                System.out.println("Ошибка при сохранении");
            }
            System.out.println("Животное добавлено");
            animal.say();
            break;
        }
    }
    }













