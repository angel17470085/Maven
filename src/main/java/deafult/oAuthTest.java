package deafult;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Keys;

import org.testng.Assert;
import pojo.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTest {

    public static void main(String[] args) throws InterruptedException
    {

        //the commented out code for selenium it is not used, due to googles new update..
        // they detect this as a threat...

        //System.out.println("----------------getting the code with selenium----------------------");
        // Set the driver path
        //System.setProperty("webdriver.edge.driver", "\"C:\\Users\\Minios\\IdeaProjects\\WebDrivers\\msedgedriver.exe\"");

        // Start Edge Session
       // String url =driver.getCurre//        WebDriver driver = new EdgeDriver();
        //        //        driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&service=lso&o2v=2&flowName=GeneralOAuthFlow");
        //        //        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("bubulubu001@gmail.com");
        //        //        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        //        //        Thread.sleep(3000);
        //        //        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Jarhead003password");
        //        //        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        //        //        Thread.sleep(4000);ntUrl();

        String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor","feter"};
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VOpAVGKDj_eKIuDAHP1exzYMc1-VL-0TPeE9HhmTFfjUD6elZ5w8iS6WiMqw-tRmQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
        String partialCode = url.split("code=")[1];
       String code = partialCode.split("&scope")[0];
        System.out.println(code);

        System.out.println("------------------ POST REQUEST: ACCESS TOKEN----------------------");
         String accessTokenResponse =  given().urlEncodingEnabled(false)
                .queryParams("code",code)
                .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type","authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");


       System.out.println("-------------- GET REQUEST - get courses--------------------");
       GetCourse gc = given().queryParam("access_token",accessToken).expect().defaultParser(Parser.JSON)
                .when()
               .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println("linkedin url :"+gc.getLinkedIn());
        System.out.println("Instructor :"+gc.getInstructor());

       List<Api> apiCourses = gc.getCourses().getApi();

        for (int i = 0; i < apiCourses.size(); i++)
        {

            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {

                System.out.println("The price is: " +apiCourses.get(i).getPrice());
            }
        }

        //GET COURSES OF WEB AUTOMATION

        ArrayList<String> a = new ArrayList<String>();

        List<WebAutomation>  w = gc.getCourses().getWebAutomation();
        for (int i = 0; i < w.size(); i++)
        {
            a.add(w.get(i).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedList));
            //System.out.println(response);
    }



}
