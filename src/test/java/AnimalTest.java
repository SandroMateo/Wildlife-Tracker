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
    firstAnimal = new Animal("Bird", "flying");
    secondAnimal = new Animal("Bear", "eating");
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
  public void getDescription_returnsDescription_String() {
    assertEquals("flying", firstAnimal.getDescription());
  }

  @Test
  public void getId_returnsId_true() {
    firstAnimal.save();
    assertTrue(firstAnimal.getId() > 0);
  }

  @Test
  public void getSightings_returnListOfSightings_True() {
    firstAnimal.save();
    Sighting firstSighting = new Sighting(Sighting.LOCATION_ZONEA, 1, firstAnimal.getId());
    firstSighting.save();
    Sighting secondSighting = new Sighting(Sighting.LOCATION_NE, 1, firstAnimal.getId());
    secondSighting.save();
    assertTrue(firstAnimal.getSightings().contains(firstSighting));
    assertTrue(firstAnimal.getSightings().contains(secondSighting));
  }

  @Test
  public void allAnimals_returnsAllInstancesOfAnimal_true() {
    firstAnimal.save();
    secondAnimal.save();
    assertTrue(Animal.allAnimals().get(0).equals(firstAnimal));
    assertTrue(Animal.allAnimals().get(1).equals(secondAnimal));
  }

  @Test
  public void findAnimals_returnsAnimalWithSameId_secondAnimal() {
    firstAnimal.save();
    secondAnimal.save();
    assertEquals(Animal.findAnimals(secondAnimal.getId()), secondAnimal);
  }

  @Test
  public void searchAnimals_returnAnimalListWithSearchedString_true() {
    firstAnimal.save();
    secondAnimal.save();
    assertTrue(Animal.searchAnimals("B").contains(firstAnimal));
    assertTrue(Animal.searchAnimals("B").contains(secondAnimal));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Animal myAnimal = new Animal("Bird", "flying");
    assertTrue(firstAnimal.equals(myAnimal));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstAnimal.save();
    assertTrue(Animal.allAnimals().get(0).equals(firstAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    firstAnimal.save();
    Animal savedAnimal = Animal.allAnimals().get(0);
    assertEquals(firstAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void updateName_updatesAnimalName_true() {
    firstAnimal.save();
    firstAnimal.updateName("Fox");
    assertEquals("Fox", Animal.findAnimals(firstAnimal.getId()).getName());
  }

  @Test
  public void updateDescription_updatesAnimalDescription_true() {
    firstAnimal.save();
    firstAnimal.updateDescription("feathers");
    assertEquals("feathers", Animal.findAnimals(firstAnimal.getId()).getDescription());
  }

  @Test
  public void delete_deletesAnimal_true() {
    firstAnimal.save();
    int firstAnimalId = firstAnimal.getId();
    firstAnimal.delete();
    assertEquals(null, Animal.findAnimals(firstAnimalId));
  }
}
