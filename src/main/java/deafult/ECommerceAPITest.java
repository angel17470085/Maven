package deafult;

import PojoEcommerce.Login;
import PojoEcommerce.LoginResponse;
import PojoEcommerce.Orders;
import PojoEcommerce.OrderDetail;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args) {


        //creating the reques specification for the base uri
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        //creating a login object with email and password to pass it to the body part , this is known as serializing a class a request
        Login loginRequest = new Login("17470085@campeche.tecnm.mx","Jarhead003") ;

        //creating the reques specification for body request part, passing the ree object and loginRequest object.
        System.out.println("---------------------Post Login request-------------------");
       RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);

       //mapping the reponse to the LoginREsponse pojo class
       LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);

        System.out.println("The token is :"+ loginResponse.getToken());
        System.out.println("The userId is:"+ loginResponse.getUserId());


        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        //add product
        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).build();

        //here i am buildind the form parameter, note, this is not a json content type, this is form paramater.
        System.out.println("---------------executing  addProduct Post request--------------");
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
                .param("productName","Shirt")
                .param("productAddedBy",userId)
                .param("productCategory","fashion")
                .param("productSubCategory","shirts")
                .param("productPrice",11500)
                .param("productDescription","addias originals")
                .param("productFor","woman")
                .multiPart("productImage",new File("C:\\Users\\Minios\\Downloads\\shirt.jpg"));

           String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                   .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.getString("productId");

        //create order
        RequestSpecification createOrderBaseReq=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail> ();
        orderDetailList.add(orderDetail);
        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq=given().log().all().spec(createOrderBaseReq).body(orders);

        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
        System.out.println(responseAddOrder);


        //Delete product
        System.out.println("----------------Executing Delete product request----------------");

        RequestSpecification deleteProdBaseReq=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();

        RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);

        String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().asString();

            JsonPath js2 = new JsonPath(deleteProductResponse);

          String message = js2.getString("message");

        Assert.assertEquals( message, "Product Deleted Successfully");

    }
}
