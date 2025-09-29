package scenarios.negative;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;

import io.restassured.response.Response;

public class DeleteBooking extends BaseTest {
    @Test
    public void deleteBookingWithInvalidMethod() {
        Response response = given()
                .header("Cookie", "token=" + token)
                .when()
                .put("booking/1");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void deleteBookingWithInvalidPath() {
        Response response = given()
                .header("Cookie", "token=" + token)
                .when()
                .delete("bookings/1");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deleteBookingWithInvalidToken() {
        Response response = given()
                .header("Cookie", "token=1")
                .when()
                .delete("booking/1");
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
