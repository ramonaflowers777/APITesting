package stepdefinitions;

import io.cucumber.java.en.And;
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
    private final TestContext testContext;
    private static final String BASE_URI = "https://fakestoreapi.com";
    //staticebi unda shevcvalo
    public static Response response;
    public static JSONObject requestParams;
    public  String s;

    public ResponseBody responseBody;
    private int responseCode;

    @Inject
    public ProductsSteps(TestContext testContext) {
        this.testContext = picoContainer.getComponent(TestContext.class);
    }

    @Given("I hit the URL of the get products api endpoint")
    public void i_hit_the_url_of_the_get_products_api_endpoint() {
        RestAssured.baseURI = BASE_URI;
    }

    @When("I pass the URL of products in the request")
    public void i_pass_the_URL_of_products_in_the_request() {
        response = RestAssured.given().
                header("Content-Type", "application/json").
                get("/products");
        Assert.assertNotNull(response, "Response is null - request may have failed.");
    }

    @Then("I receive the response code as {int}")
    public void i_receive_the_response_code_as(int statusCode) {
        responseCode = response.getStatusCode();
        Assert.assertEquals(responseCode, statusCode, "Status code was incorrect");
    }

    @Then("I verify that the rate of the first product is {string}")
    public void i_verify_that_the_rate_of_the_product_is(String rate) {
        responseBody = response.getBody();

        //json representation from the body
        JsonPath jsonPath = response.jsonPath();

         String productRate = jsonPath.getJsonObject("rating[0].rate").toString();

        Assert.assertEquals(productRate, rate);
    }

    @Given("I hit the URL of the post products api endpoint")
    public void iHitTheURLOfThePostProductsApiEndpoint() {
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

    @Then("I receive the response body with id as {string}")
    public void iReceiveTheResponseBodyWithIdAs(String id) {
        System.out.println(testContext.getID());
        Assert.assertEquals(testContext.getID(), id, "Returned id is not correct");
    }

    @Given("I hit the URL of the put products api endpoint")
    public void iHitTheURLOfThePutProductsApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com";
          requestParams = new JSONObject();
    }

    @When("I pass the URL of products in the request with {string}")
    public void iPassTheURLOfProductsInTheRequestWith(String productNum) {
        requestParams.put("title", "test product");
        requestParams.put("price", "34.6");
        requestParams.put("description", "lorem ipsum");
        requestParams.put("image", "https://i.pravatar.cc");
        requestParams.put("category", "clothes");

        Response response =  RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .put("/products/"+productNum);

        JsonPath jsnpath = response.jsonPath();
        String productID = jsnpath.getJsonObject("id").toString();
        testContext.setID(productID);
    }
}
