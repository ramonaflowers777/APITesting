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

public class GetProductsSteps {
    public static Response response;
    private  int responseCode;
    public ResponseBody responseBody;
    public static JSONObject requestParams;

    @Given("I hit the URL of the get products api endpoint")
    public void i_hit_the_url_of_the_get_products_api_endpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @When("I pass the URL of products in the request")
    public void i_pass_the_URL_of_products_in_the_request() {
        response = RestAssured.given().
                header("Content-Type","application/json").
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

        //convert the response body into string
        String responseBodyAsString = responseBody.asString();

        //json representation from the body
        JsonPath jsonPath = response.jsonPath();

        String s = jsonPath.getJsonObject("rating[0].rate").toString();

        Assert.assertEquals(s,rate);
    }

    @Given("I hit the URL of the post products api endpoint")
    public void iHitTheURLOfThePostProductsApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        requestParams = new JSONObject();
        requestParams.put("title","shoes");
        requestParams.put("price","34.6");
        requestParams.put("description","lorem ipsum");
        requestParams.put("image","https://i.pravatar.cc");
        requestParams.put("category","clothes");
    }

    @And("I pass the request body of product title {string}")
    public void iPassTheRequestBodyOfProductTitle(String productTitle) {
        requestParams.put("title",productTitle);

        Response response = RestAssured.given()
                .header("Content-Type","application/json").
                body(requestParams.toString())
                .post("/products");

        ResponseBody body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }
}
