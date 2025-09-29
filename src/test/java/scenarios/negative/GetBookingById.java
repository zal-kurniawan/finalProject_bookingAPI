package scenarios.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.example.base.BaseTest;

import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class GetBookingById extends BaseTest {
    @Test
    public void getBookingByIdWithInvalidMethod() {
        Response response = given()
                .when()
                .put("booking/1");
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test
    public void getBookingByIdWithInvalidPath() {
        Response response = BookingAPI.getBookingById("bookings", "1");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getBookingByIdWithInvalidParam() {
        Response response = BookingAPI.getBookingById("bookings", "test");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

}
