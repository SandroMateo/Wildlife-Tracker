import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimalTest {
  private Animal firstAnimal;
  private Animal secondAnimal;

  @Before
  public void initialize() {
    firstAnimal = new Animal("Bird");
    secondAnimal = new Animal("Bear");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Animal_instantiatesCorrectly_true() {
    assertEquals(true, firstAnimal instanceof Animal);
  }

  @Test
  public void getName_returnsName_String() {
    assertEquals("Bird", firstAnimal.getName());
  }

  @Test
  public void getId_returnsId_true() {
    firstAnimal.save();
    assertTrue(firstAnimal.getId() > 0);
  }

  // @Test
  // public void getSightings_returnListOfSightings_True() {
  //   firstAnimal.save();
  //   Sighting firstSighting = new Sighting( firstAnimal.getId());
  //   firstSighting.save();
  //   Sighting secondSighting = new Sighting( firstAnimal.getId());
  //   secondSighting.save();
  //   assertTrue(firstAnimal.getSightings().contains(firstSighting));
  //   assertTrue(firstAnimal.getSightings().contains(secondSighting));
  // }

  @Test
  public void all_returnsAllInstancesOfAnimal_true() {
    firstAnimal.save();
    secondAnimal.save();
    assertTrue(Animal.all().get(0).equals(firstAnimal));
    assertTrue(Animal.all().get(1).equals(secondAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    firstAnimal.save();
    secondAnimal.save();
    assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  }

  @Test
  public void search_returnAnimalListWithSearchedString_true() {
    firstAnimal.save();
    secondAnimal.save();
    assertTrue(Animal.search("B").contains(firstAnimal));
    assertTrue(Animal.search("B").contains(secondAnimal));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Animal myAnimal = new Animal("Bird");
    assertTrue(firstAnimal.equals(myAnimal));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstAnimal.save();
    assertTrue(Animal.all().get(0).equals(firstAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    firstAnimal.save();
    Animal savedAnimal = Animal.all().get(0);
    assertEquals(firstAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void updateName_updatesAnimalName_true() {
    firstAnimal.save();
    firstAnimal.updateName("Fox");
    assertEquals("Fox", Animal.find(firstAnimal.getId()).getName());
  }

  @Test
  public void delete_deletesAnimal_true() {
    firstAnimal.save();
    int firstAnimalId = firstAnimal.getId();
    firstAnimal.delete();
    assertEquals(null, Animal.find(firstAnimalId));
  }
}
