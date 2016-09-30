import org.sql2o.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Ranger {
  private String name;
  private String password;
  private int badgeNumber;
  private int id;
  private String contactInfo;

  public static boolean loggedIn = false;

  public static final boolean LOGGEDIN_TRUE = true;
  public static final boolean LOGGEDIN_FALSE = false;

  public Ranger(String name, int badgeNumber, String password, String contactInfo) {
    this.name = name;
    this.badgeNumber = badgeNumber;
    this.password = password;
    this.contactInfo = contactInfo;
    Ranger.loggedIn = Ranger.LOGGEDIN_TRUE;
  }

  public String getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public int getBadgeNumber() {
    return this.badgeNumber;
  }

  public String getContactInfo() {
    return this.contactInfo;
  }

  public int getId() {
    return this.id;
  }

  public static boolean isLoggedIn() {
    return Ranger.loggedIn;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rangers (name, badgeNumber, password, contactInfo) VALUES (:name, :badgeNumber, :password, :contactInfo)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("badgeNumber", this.badgeNumber)
        .addParameter("password", this.password)
        .addParameter("contactInfo", this.contactInfo)
        .executeUpdate()
        .getKey();
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE rangerid = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public void updateName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET name = :name";
      con.createQuery(sql)
        .addParameter("name", name)
        .executeUpdate();
    }
  }

  public void updatePassword(String password) {
    this.password = password;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET password = :password";
      con.createQuery(sql)
        .addParameter("password", password)
        .executeUpdate();
    }
  }

  public void updateBadgeNumber(int badgeNumber) {
    this.badgeNumber = badgeNumber;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET badgeNumber = :badgeNumber";
      con.createQuery(sql)
        .addParameter("badgeNumber", badgeNumber)
        .executeUpdate();
    }
  }

  public void updateContactInfo(String contactInfo) {
    this.contactInfo = contactInfo;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET contactInfo = :contactInfo";
      con.createQuery(sql)
        .addParameter("contactInfo", contactInfo)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM rangers WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Ranger> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers";
      return con.createQuery(sql).executeAndFetch(Ranger.class);
    }
  }

  public static Ranger find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ranger.class);
    }
  }

  public static Ranger login(int badgeNumber, String password) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE badgenumber = :badgeNumber AND password = :password";
      Ranger ranger =  con.createQuery(sql)
        .addParameter("badgeNumber", badgeNumber)
        .addParameter("password", password)
        .executeAndFetchFirst(Ranger.class);
      if(ranger == null) {
        throw new RuntimeException("Invalid login information!");
      }
      return ranger;
    }
  }

  public static boolean checkLogin(int badgeNumber, String password) {
    try {
      Ranger.login(name, badgeNumber, password);
    } catch (RuntimeException exception) {
      Ranger.loggedIn = Ranger.LOGGEDIN_FALSE;
      return Ranger.loggedIn;
    }
    Ranger.loggedIn = Ranger.LOGGEDIN_TRUE;
    return Ranger.loggedIn;
  }

  public static List<Ranger> search(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE name LIKE :name";
      return con.createQuery(sql)
        .addParameter("name", (name + "%"))
        .executeAndFetch(Ranger.class);
    }
  }

  @Override
  public boolean equals(Object otherRanger) {
    if (!(otherRanger instanceof Ranger)) {
      return false;
    } else {
      Ranger newRanger = (Ranger) otherRanger;
      return this.getName().equals(newRanger.getName()) &&
      this.getPassword().equals(newRanger.getPassword()) &&
      this.getContactInfo().equals(newRanger.getContactInfo()) &&
      this.getBadgeNumber() == newRanger.getBadgeNumber() &&
      this.getId() == newRanger.getId();
    }
  }
}
