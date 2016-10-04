import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("rangers", Ranger.all());
      model.put("animals", Animal.allAnimals());
      model.put("loggedIn", Ranger.isLoggedIn());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/login", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("template", "templates/login.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/login", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int badgeNumber = Integer.parseInt(request.queryParams("badgeNumber"));
      String password = request.queryParams("password");
      if(Ranger.checkLogin(badgeNumber, password)) {
        request.session().attribute("rangerId", Ranger.login(badgeNumber, password).getId());
        model.put("rangers", Ranger.all());
        model.put("animals", Animal.allAnimals());
        model.put("loggedIn", Ranger.isLoggedIn());
        model.put("endangeredAnimals", EndangeredAnimal.all());
        model.put("template", "templates/index.vtl");
      } else {
        model.put("loggedIn", Ranger.isLoggedIn());
        model.put("template", "templates/login.vtl");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/ranger/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("template", "templates/new-ranger.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ranger/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String name = request.queryParams("name");
      String password = request.queryParams("password");
      String checkPassword = request.queryParams("checkPassword");
      String contact = request.queryParams("contact");
      if (password.equals(checkPassword)) {
        Ranger newRanger = new Ranger(name, password, contact);
        newRanger.save();
        model.put("ranger", newRanger);
        model.put("animals", Animal.allAnimals());
        model.put("EndangeredAnimal", EndangeredAnimal.all());
        model.put("zoneA", Sighting.LOCATION_ZONEA);
        model.put("ne", Sighting.LOCATION_NE);
        model.put("river", Sighting.LOCATION_RIVER);
        model.put("loggedIn", Ranger.isLoggedIn());
        model.put("template", "templates/ranger.vtl");
      } else {
        model.put("created", false);
        model.put("template", "templates/new-ranger.vtl");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("template", "templates/new-animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      boolean isEndangered = Boolean.parseBoolean(request.queryParams("endangered"));
      request.session().attribute("name", name);
      request.session().attribute("description", description);
      if (isEndangered == true) {
        model.put("endangered", true);
        model.put("healthy", EndangeredAnimal.HEALTH_HEALTHY);
        model.put("okay", EndangeredAnimal.HEALTH_OKAY);
        model.put("ill", EndangeredAnimal.HEALTH_ILL);
        model.put("newborn", EndangeredAnimal.AGE_NEWBORN);
        model.put("young", EndangeredAnimal.AGE_YOUNG);
        model.put("adult", EndangeredAnimal.AGE_ADULT);
      } else {
        model.put("endangered", false);
      }
      model.put("zoneA", Sighting.LOCATION_ZONEA);
      model.put("ne", Sighting.LOCATION_NE);
      model.put("river", Sighting.LOCATION_RIVER);
      model.put("template", "templates/new-animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/new/sighting", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String name = request.session().attribute("name");
      String description = request.session().attribute("description");
      String health = request.queryParams("health");
      String age = request.queryParams("age");
      int animalId = 0;
      if (health.length() > 0 && age.length() > 0) {
        EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, description, health, age);
        endangeredAnimal.save();
        animalId = endangeredAnimal.getId();
        model.put("animal", endangeredAnimal);
      } else {
        Animal animal = new Animal(name, description);
        animal.save();
        animalId = animal.getId();
        model.put("animal", animal);
      }
      String location = request.queryParams("location");
      int rangerId = request.session().attribute("rangerId");
      Sighting newSighting = new Sighting(location, rangerId, animalId);
      newSighting.save();
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      if(Animal.findAnimals(Integer.parseInt(request.params("id"))) == null) {
        EndangeredAnimal endangeredAnimal = EndangeredAnimal.find(Integer.parseInt(request.params("id")));
        model.put("animal", endangeredAnimal);
      } else {
        Animal animal = Animal.findAnimals(Integer.parseInt(request.params("id")));
        model.put("animal", animal);
      }
      model.put("zoneA", Sighting.LOCATION_ZONEA);
      model.put("ne", Sighting.LOCATION_NE);
      model.put("river", Sighting.LOCATION_RIVER);
      model.put("loggedIn", Ranger.isLoggedIn());
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/:id/sighting/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      int animalId = Integer.parseInt(request.params("id"));
      int rangerId = request.session().attribute("rangerId");
      String location = request.queryParams("location");
      Sighting newSighting = new Sighting(location, rangerId, animalId);
      newSighting.save();
      if(Animal.findAnimals(animalId) == null) {
        EndangeredAnimal endangeredAnimal = EndangeredAnimal.find(animalId);
        model.put("animal", endangeredAnimal);
      } else {
        Animal animal = Animal.findAnimals(animalId);
        model.put("animal", animal);
      }
      model.put("rangers", Ranger.all());
      model.put("animals", Animal.allAnimals());
      model.put("loggedIn", Ranger.isLoggedIn());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/ranger/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Ranger ranger = Ranger.find(Integer.parseInt(request.params("id")));
      model.put("ranger", ranger);
      model.put("animals", Animal.allAnimals());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("zoneA", Sighting.LOCATION_ZONEA);
      model.put("ne", Sighting.LOCATION_NE);
      model.put("river", Sighting.LOCATION_RIVER);
      model.put("loggedIn", Ranger.isLoggedIn());
      model.put("template", "templates/ranger.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/", (request, response) -> {
    //   Map<String, Object> model = new HashMap<>();
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
