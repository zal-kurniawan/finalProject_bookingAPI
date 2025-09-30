package scenarios.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class CreateBooking extends BaseTest {

    @Test
    public void createBookingWithoutFirstName() {
        String request = "{\n" + //
                "    \"lastname\" : \"Brown\",\n" + //
                "    \"totalprice\" : 111,\n" + //
                "    \"depositpaid\" : true,\n" + //
                "    \"bookingdates\" : {\n" + //
                "        \"checkin\" : \"2018-01-01\",\n" + //
                "        \"checkout\" : \"2019-01-01\"\n" + //
                "    },\n" + //
                "    \"additionalneeds\" : \"Breakfast\"\n" + //
                "}";
        Response response = BookingAPI.createBooking(request);
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test
    public void createBookingWithEmptyBookingDates() {
        String request = "{\n" + //
                "    \"firstname\" : \"Jim\",\n" + //
                "    \"lastname\" : \"Brown\",\n" + //
                "    \"totalprice\" : 111,\n" + //
                "    \"depositpaid\" : true,\n" + //
                "    \"bookingdates\" : {},\n" + //
                "    \"additionalneeds\" : \"Breakfast\"\n" + //
                "}";
        Response response = BookingAPI.createBooking(request);
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test
    public void createBookingWithEmptyPayloads() {
        String request = "{}";
        Response response = BookingAPI.createBooking(request);
        Assert.assertEquals(response.getStatusCode(), 500);
    }
}
