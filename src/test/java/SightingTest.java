import org.sql2o.*;
import org.junit.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SightingTest {
  private Sighting firstSighting;
  private Sighting secondSighting;

  @Before
  public void initialize() {
    firstSighting = new Sighting(Sighting.LOCATION_ZONEA, 1, 1);
    secondSighting = new Sighting(Sighting.LOCATION_NE, 2, 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Sighting_instantiatesCorrectly_true() {
    assertEquals(true, firstSighting instanceof Sighting);
  }

  @Test
  public void getLocation_returnsLocation_String() {
    assertEquals(Sighting.LOCATION_ZONEA, firstSighting.getLocation());
  }

  @Test
  public void getRangerId_returnsRangerId_int() {
    assertEquals(1, firstSighting.getRangerId());
  }

  @Test
  public void getDate_returnsDate_String() {
    firstSighting.save();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), firstSighting.getDate());
  }

  @Test
  public void getAnimalId_returnsAnimalId_int() {
    assertEquals(1, firstSighting.getAnimalId());
  }

  @Test
  public void getId_returnsId_true() {
    firstSighting.save();
    assertTrue(firstSighting.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    firstSighting.save();
    secondSighting.save();
    assertTrue(Sighting.all().get(0).equals(firstSighting));
    assertTrue(Sighting.all().get(1).equals(secondSighting));
  }

  @Test
  public void find_returnsSightingWithSameId_secondSighting() {
    firstSighting.save();
    secondSighting.save();
    assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
  }

  @Test
  public void equals_returnsTrueIfSightingsAreTheSame() {
    Sighting mySighting = new Sighting(Sighting.LOCATION_ZONEA, 1, 1);
    assertTrue(firstSighting.equals(mySighting));
  }

  @Test
  public void save_returnsTrueIfLocationsAreTheSame() {
    firstSighting.save();
    assertTrue(Sighting.all().get(0).equals(firstSighting));
  }

  @Test
  public void save_assignsIdToObject() {
    firstSighting.save();
    Sighting savedSighting = Sighting.all().get(0);
    assertEquals(firstSighting.getId(), savedSighting.getId());
  }

  @Test
  public void delete_deletesSighting_true() {
    firstSighting.save();
    int firstSightingId = firstSighting.getId();
    firstSighting.delete();
    assertEquals(null, Sighting.find(firstSightingId));
  }
}
