package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.TestContext;

import javax.inject.Inject;

import static utils.TestSetUp.picoContainer;

public class ProductsSteps {
    private static final String BASE_URI = "https://fakestoreapi.com";
    //staticebi unda shevcvalo
    public static Response response;
    public static JSONObject requestParams;
    private final TestContext testContext;
    public ResponseBody responseBody;
    private int responseCode;

    @Inject
    public ProductsSteps(TestContext testContext) {
        this.testContext = picoContainer.getComponent(TestContext.class);
    }

    @Given("I hit the URL of the get products api endpoint")
    public void hittingGetApiEndpoint() {
        RestAssured.baseURI = BASE_URI;
    }

    @When("I pass the URL of products in the request")
    public void passingURLofProducts() {
        response = RestAssured.given().
                header("Content-Type", "application/json").
                get("/products");
        Assert.assertNotNull(response, "Response is null - request may have failed.");
    }

    @Then("I receive the response code as {int}")
    public void iReceoveResponseWithStatusCode(int statusCode) {
        responseCode = response.getStatusCode();
        Assert.assertEquals(responseCode, statusCode, "Status code was incorrect");
    }

    @Then("I verify that the rate of the first product is {string}")
    public void iVerifyProductRate(String rate) {
        responseBody = response.getBody();

        //json representation from the body
        JsonPath jsonPath = response.jsonPath();

        String productRate = jsonPath.getJsonObject("rating[0].rate").toString();

        Assert.assertEquals(productRate, rate);
    }

    @Given("I hit the URL of the post products api endpoint")
    public void hittingURlOfPostApiEndpoint() {
        RestAssured.baseURI = BASE_URI;
    }

    @Then("I pass the request body of product title {string}")
    public void iPassTheRequestBodyOfProductTitle(String productTitle) {
        requestParams = new JSONObject();
        requestParams.put("title", productTitle);
        requestParams.put("price", "34.6");
        requestParams.put("description", "lorem ipsum");
        requestParams.put("image", "https://i.pravatar.cc");
        requestParams.put("category", "clothes");

        Response response = RestAssured.given()
                .header("Content-Type", "application/json").
                body(requestParams.toString())
                .post("/products");

        ResponseBody body = response.getBody();

        JsonPath jsonPath = response.jsonPath();
        String productId = jsonPath.getJsonObject("id").toString();
        System.out.println(testContext.getID());
        testContext.setID(productId);
        System.out.println(testContext.getID());

        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }

    @Then("I receive the response body after post with id as {string}")
    public void iReceiveTheResponseBodyAfterPostWithId(String id) {
        JsonPath jsonPath = response.jsonPath();
        testContext.setID(jsonPath.getJsonObject("id").toString());
        Assert.assertEquals(testContext.getID(), id);
        testContext.clearID();
    }

    @Then("I receive the response body with id as {string}")
    public void iReceiveTheResponseBodyWithId(String id) {
        System.out.println(testContext.getID());
        Assert.assertEquals(testContext.getID(), id, "Returned id is not correct");
        testContext.clearID();
    }

    @Given("I hit the URL of the put products api endpoint")
    public void hittingPutApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        requestParams = new JSONObject();
    }

    @When("I pass the URL of update products in the request with {string}")
    public void passingUpdateRequest(String productNum) {
        requestParams.put("title", "test product");
        requestParams.put("price", "34.6");
        requestParams.put("description", "lorem ipsum");
        requestParams.put("image", "https://i.pravatar.cc");
        requestParams.put("category", "clothes");

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .put("/products/" + productNum);

        JsonPath jsnpath = response.jsonPath();
        String productID = jsnpath.getJsonObject("id").toString();
        System.out.println(testContext.getID());
        testContext.setID(productID);
        System.out.println(testContext.getID());
    }

    @Given("I hit the URL of the delete products api endpoint")
    public void hittingURlofDeleteApiEndpoint() {
        RestAssured.baseURI = BASE_URI;
    }


    @When("I pass the URL of delete products in the request with {string}")
    public void passingDeleteProductsRequest(String productNum) {
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .delete("/products/" + productNum);

        JsonPath jsonPath = response.jsonPath();
        testContext.setID(jsonPath.getJsonObject("id").toString());
    }
}
