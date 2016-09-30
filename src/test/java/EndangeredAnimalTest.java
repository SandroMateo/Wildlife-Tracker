import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class EndangeredAnimalTest {
  private EndangeredAnimal firstEndangeredAnimal;
  private EndangeredAnimal secondEndangeredAnimal;

  @Before
  public void initialize() {
    firstEndangeredAnimal = new EndangeredAnimal("Bird", "flying", "healthy", "young");
    secondEndangeredAnimal = new EndangeredAnimal("Bear", "eating", "okay", "adult");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void EndangeredAnimal_instantiatesCorrectly_true() {
    assertEquals(true, firstEndangeredAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void getName_returnsName_String() {
    assertEquals("Bird", firstEndangeredAnimal.getName());
  }

  @Test
  public void getHealth_returnsHealth_String() {
    assertEquals("healthy", firstEndangeredAnimal.getHealth());
  }

  @Test
  public void getAge_returnsAge_String() {
    assertEquals("young", firstEndangeredAnimal.getAge());
  }

  @Test
  public void getId_returnsId_true() {
    firstEndangeredAnimal.save();
    assertTrue(firstEndangeredAnimal.getId() > 0);
  }

  @Test
  public void getSightings_returnListOfSightings_True() {
    firstEndangeredAnimal.save();
    Sighting firstSighting = new Sighting(Sighting.LOCATION_ZONEA, 1, firstEndangeredAnimal.getId());
    firstSighting.save();
    Sighting secondSighting = new Sighting(Sighting.LOCATION_NE, 1, firstEndangeredAnimal.getId());
    secondSighting.save();
    assertTrue(firstEndangeredAnimal.getSightings().contains(firstSighting));
    assertTrue(firstEndangeredAnimal.getSightings().contains(secondSighting));
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    firstEndangeredAnimal.save();
    secondEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
    assertTrue(EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void find_returnsEndangeredAnimalWithSameId_secondEndangeredAnimal() {
    firstEndangeredAnimal.save();
    secondEndangeredAnimal.save();
    assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void search_returnEndangeredAnimalListWithSearchedString_true() {
    firstEndangeredAnimal.save();
    secondEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.search("B").contains(firstEndangeredAnimal));
    assertTrue(EndangeredAnimal.search("B").contains(secondEndangeredAnimal));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Bird", "flying", "healthy", "young");
    assertTrue(firstEndangeredAnimal.equals(myEndangeredAnimal));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    firstEndangeredAnimal.save();
    EndangeredAnimal savedEndangeredAnimal = EndangeredAnimal.all().get(0);
    assertEquals(firstEndangeredAnimal.getId(), savedEndangeredAnimal.getId());
  }

  @Test
  public void updateName_updatesEndangeredAnimalName_true() {
    firstEndangeredAnimal.save();
    firstEndangeredAnimal.updateName("Fox");
    assertEquals("Fox", EndangeredAnimal.find(firstEndangeredAnimal.getId()).getName());
  }

  @Test
  public void updateDescription_updatesEndangeredAnimalDescription_true() {
    firstEndangeredAnimal.save();
    firstEndangeredAnimal.updateDescription("feathers");
    assertEquals("feathers", EndangeredAnimal.find(firstEndangeredAnimal.getId()).getDescription());
  }

  @Test
  public void updateHealth_updatesEndangeredAnimalHealth_true() {
    firstEndangeredAnimal.save();
    firstEndangeredAnimal.updateHealth("ill");
    assertEquals("ill", EndangeredAnimal.find(firstEndangeredAnimal.getId()).getHealth());
  }

  @Test
  public void updateAge_updatesEndangeredAnimalAge_true() {
    firstEndangeredAnimal.save();
    firstEndangeredAnimal.updateAge("newborn");
    assertEquals("newborn", EndangeredAnimal.find(firstEndangeredAnimal.getId()).getAge());
  }

  @Test
  public void delete_deletesEndangeredAnimal_true() {
    firstEndangeredAnimal.save();
    int firstEndangeredAnimalId = firstEndangeredAnimal.getId();
    firstEndangeredAnimal.delete();
    assertEquals(null, EndangeredAnimal.find(firstEndangeredAnimalId));
  }
}
