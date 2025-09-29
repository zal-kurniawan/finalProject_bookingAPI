package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class GetBookingIds extends BaseTest {
    @Test
    public void getAllBookingIds() {
        Response response = BookingAPI.getBookingIds("booking");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().get("bookingid"));
    }

    @Test
    public void getBookingIdsByName() {
        String firstname = "Jim";
        String lastname = "Brown";
        Response response = BookingAPI.getBookingIds("booking?firstname=" + firstname + "&lastname=" + lastname + "");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().get("bookingid"));
    }

    @Test
    public void getBookingIdsByChekIn() {
        Response response = BookingAPI.getBookingIds("booking?checkin=2018-01-01");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().get("bookingid"));
    }
}
