import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Animal {
  public String name;
  public int id;

  public Animal(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE animalid = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public void updateName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Animal> allAnimals() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals";
      return con.createQuery(sql)
        .executeAndFetch(Animal.class);
    }
  }

  public static Animal findAnimals(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id";
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("id", id)
        .executeAndFetchFirst(Animal.class);
    }
  }

  public static List<Animal> searchAnimals(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE name LIKE :name";
      return con.createQuery(sql)
        .addParameter("name", (name + "%"))
        .executeAndFetch(Animal.class);
    }
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if (!(otherAnimal instanceof Animal)) {
      return true;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName()) && this.getId() == newAnimal.getId();
    }
  }
}
