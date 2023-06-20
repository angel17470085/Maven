package deafult;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Keys;
import static io.restassured.RestAssured.given;

public class oAuthTest {

    public static void main(String[] args) throws InterruptedException {

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

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VN_hxQAXjb6xwGDZfg3JY3vlZ3OYD8ja3jpOWtCZV46irYYeMVr7fplnUZJ8v62UQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
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
       String response = given().queryParam("access_token",accessToken)
                .when().log().all()
               .get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);
    }
}
