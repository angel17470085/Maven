package deafult;


import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import pojoGmap.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {
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

        String res = given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .extract().response().asString();


        System.out.println(res);
    }
}
