package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.utils.Helper;

import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class DeleteBooking extends BaseTest {
    public String bookingId;

    @BeforeMethod
    public void createBookingForDeleteScenario() {
        RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                "positive_createBookingForUpdateDeleteScenario", RequestCreateBooking.class);
        Response responseCreate = BookingAPI.createBooking(requestCreate);
        Assert.assertEquals(responseCreate.getStatusCode(), 200);
        bookingId = responseCreate.jsonPath().getString("bookingid");
    }

    @Test
    public void deleteBookingWithValidId() {
        Response response = BookingAPI.deleteBooking(bookingId, token);
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void deleteBookingWithHeaderBasicAuth() {
        Response response = given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("booking/" + bookingId);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}
