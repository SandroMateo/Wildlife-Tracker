import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RangerTest {
  private Ranger firstRanger;
  private Ranger secondRanger;

  @Before
  public void initialize() {
    firstRanger = new Ranger("Sandro", "12345", "sandro@sandro.com");
    secondRanger = new Ranger("Satchel", "qwerty", "satchel@satchel.com");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ranger_instantiatesCorrectly_true() {
    assertEquals(true, firstRanger instanceof Ranger);
  }

  @Test
  public void getName_returnsName_String() {
    assertEquals("Sandro", firstRanger.getName());
  }

  @Test
  public void getPassword_returnsPassword_String() {
    assertEquals("12345", firstRanger.getPassword());
  }

  @Test
  public void getContactInfo_returnsContactInfo_String() {
    assertEquals("sandro@sandro.com", firstRanger.getContactInfo());
  }

  @Test
  public void getId_returnsId_true() {
    firstRanger.save();
    assertTrue(firstRanger.getId() > 0);
  }

  @Test
  public void getSightings_returnListOfSightings_True() {
    firstRanger.save();
    Sighting firstSighting = new Sighting(Sighting.LOCATION_ZONEA, firstRanger.getId(), 1);
    firstSighting.save();
    Sighting secondSighting = new Sighting(Sighting.LOCATION_NE, firstRanger.getId(), 1);
    secondSighting.save();
    assertTrue(firstRanger.getSightings().contains(firstSighting));
    assertTrue(firstRanger.getSightings().contains(secondSighting));
  }

  @Test
  public void all_returnsAllInstancesOfRanger_true() {
    firstRanger.save();
    secondRanger.save();
    assertTrue(Ranger.all().get(0).equals(firstRanger));
    assertTrue(Ranger.all().get(1).equals(secondRanger));
  }

  @Test
  public void find_returnsRangerWithSameId_secondRanger() {
    firstRanger.save();
    secondRanger.save();
    assertEquals(Ranger.find(secondRanger.getId()), secondRanger);
  }

  @Test
  public void search_returnRangerListWithSearchedString_true() {
    firstRanger.save();
    secondRanger.save();
    assertTrue(Ranger.search("S").contains(firstRanger));
    assertTrue(Ranger.search("S").contains(secondRanger));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Ranger myRanger = new Ranger("Sandro", "12345", "sandro@sandro.com");
    assertTrue(firstRanger.equals(myRanger));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstRanger.save();
    assertTrue(Ranger.all().get(0).equals(firstRanger));
  }

  @Test
  public void save_assignsIdToObject() {
    firstRanger.save();
    Ranger savedRanger = Ranger.all().get(0);
    assertEquals(firstRanger.getId(), savedRanger.getId());
  }

  @Test
  public void updateName_updatesRangerName_true() {
    firstRanger.save();
    firstRanger.updateName("Gabe");
    assertEquals("Gabe", Ranger.find(firstRanger.getId()).getName());
  }

  @Test
  public void updateBadgeNumber_updatesRangerBadgeNumber_true() {
    firstRanger.save();
    firstRanger.updateBadgeNumber(2);
    assertEquals(2, Ranger.find(firstRanger.getId()).getBadgeNumber());
  }

  @Test
  public void updatePassword_updatesRangerPassword_true() {
    firstRanger.save();
    firstRanger.updatePassword("asdfg");
    assertEquals("asdfg", Ranger.find(firstRanger.getId()).getPassword());
  }

  @Test
  public void updateContactInfo_updatesRangerContactInfo_true() {
    firstRanger.save();
    firstRanger.updateContactInfo("me@me.com");
    assertEquals("me@me.com", Ranger.find(firstRanger.getId()).getContactInfo());
  }

  @Test
  public void login_successReturnsRanger_true() {
    firstRanger.save();
    assertEquals(firstRanger, Ranger.login(1, "12345"));
  }

  @Test(expected = RuntimeException.class)
  public void login_throwsExceptionIfLoginFails() {
    firstRanger.save();
    assertEquals(firstRanger, Ranger.login(1, "qwerty"));
  }

  @Test
  public void login_catchesExceptionIfLoginFails_false() {
    firstRanger.save();
    try {
      Ranger myRanger = Ranger.login(1, "qwerty");
    } catch(RuntimeException exception){ }
      assertFalse(Ranger.checkLogin(1, "qwerty"));
  }

  @Test
  public void delete_deletesRanger_true() {
    firstRanger.save();
    int firstRangerId = firstRanger.getId();
    firstRanger.delete();
    assertEquals(null, Ranger.find(firstRangerId));
  }
}
