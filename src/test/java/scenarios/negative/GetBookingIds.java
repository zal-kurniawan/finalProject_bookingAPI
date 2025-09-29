package scenarios.negative;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class GetBookingIds extends BaseTest {

    @Test
    public void getBookingIdWithInvalidMethod() {
        Response response = given()
                .when()
                .delete("booking");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getBookingIdWithInvalidPath() {
        Response response = BookingAPI.getBookingIds("bookings");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getBookingIdWithInvalidParam() {
        Response response = BookingAPI.getBookingIds("booking?name=budi");
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
