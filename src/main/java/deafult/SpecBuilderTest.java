package deafult;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import okhttp3.Request;
import pojoGmap.*;
import java.util.ArrayList;
import java.util.List;
public class SpecBuilderTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("amp. josefa ortiz");
        p.setLanguage("spanish");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://google.com");
        p.setName("angelino");
        List<String> list = new ArrayList<String>(){{
            add("Shoe park");
            add("shop");
        }};
        p.setTypes(list);
        p.setLocation(new Location(){
            {
                setLat(33.333);
                setLng(22.222);
            }
        });

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick")
                .setContentType(ContentType.JSON).build();


        ResponseSpecification respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification res = given().spec(req)
                .body(p);

        Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(respec).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }
}
