package tables;

import animals.Animal;
import data.AnimalTypeData;
import data.ColorData;
import db.IDatabase;
import db.MySqlConnectorDb;
import factory.AnimalFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnimalTable extends AbsTable {

    private IDatabase iDatabase = null;

    public AnimalTable () {
        super("animal");
        iDatabase = new MySqlConnectorDb();
    }
    private boolean isTableExist() throws SQLException, IOException {
        String sqlReq="show tables";
        ResultSet tables = iDatabase.requestExecuteWithReturned(sqlReq);

        while (tables.next()) {
            String tableName = tables.getString(1);
            if(tableName.equals(name)) {
                return true;
            }
        }
        return false;

    }
    public void createTable() throws SQLException, IOException {
        if (!isTableExist()) {
            String sqlRequest = String.format("create table %s (iid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name varchar(10), age int, weight int, color varchar(20), type varchar(20))", name);
            iDatabase.requestExecute(sqlRequest);
            System.out.println("Таблица создана");
        } else {
            System.out.println("Таблица уже существует");
        }
    }
    public void insertAnimal(Animal animal) throws SQLException, IOException {
        String type = animal.getClass().getSimpleName().toLowerCase();
        String sqlrequest = String.format ("INSERT INTO %s (name, age, weight, color, type) values ('%s', %d, %d,'%s','%s')",
                name,
                animal.getName(),
                animal.getAge(),
                animal.getWeight(),
                animal.getColor().name(),
                type
        );
        iDatabase.requestExecute(sqlrequest);
    }

    public List<Animal> getAllAnimals() throws SQLException, IOException {
        List<Animal> animals = new ArrayList<>();
        String sqlRequest = String.format("SELECT name, age, weight, color, type FROM %s", name);
        ResultSet resultSet = iDatabase.requestExecuteWithReturned(sqlRequest);

        while (resultSet.next()) {

            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            int weight = resultSet.getInt("weight");
            String colorStr = resultSet.getString("color");
            String typeStr = resultSet.getString("type");

            ColorData color = ColorData.valueOf(colorStr.toUpperCase());
            AnimalTypeData type = AnimalTypeData.valueOf(typeStr.toUpperCase());

            Animal animal = new AnimalFactory(name,age,weight,color).create(type);
            animals.add(animal);
        }
        return animals;
    }
    public  void updateAnimal(int id, String name, int age, int weight, ColorData color) throws SQLException, IOException {
        String sqlRequest = String.format(
                "UPDATE %s SET name = '%s', age = %d, weight = %d, color = '%s' WHERE id = %d",
                this.name, name, age, weight, color.name(),id
                );
        iDatabase.requestExecute(sqlRequest);

    }
    public List<Animal> getAnimalByType(String typeDbfilter) throws SQLException, IOException{
        List<Animal> animals = new ArrayList<>();
        String sqlRequest = String.format("SELECT name, age, weight, color, type FROM %s WHERE LOWER(type) = '%s'", name, typeDbfilter.toLowerCase());
        ResultSet resultSet = iDatabase.requestExecuteWithReturned(sqlRequest);

        while (resultSet.next()){
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            int weight = resultSet.getInt("weight");
            String colorStr = resultSet.getString("color");
            String typeStr = resultSet.getString("type").toUpperCase();

            ColorData colorData = ColorData.valueOf(colorStr.toUpperCase());
            AnimalTypeData animalTypeData = AnimalTypeData.valueOf(typeStr.toUpperCase());

            AnimalFactory factory = new AnimalFactory(name, age, weight, colorData);
            Animal animal = factory.create(animalTypeData);
            animals.add(animal);
        }
        return animals;
    }
}
