package scenarios.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class UpdateBooking extends BaseTest {
    @Test
    public void updateBookingWithoutFirstName() {
        String request = "{\n" + //
                "    \"lastname\" : \"Jhon\",\n" + //
                "    \"totalprice\" : 50000,\n" + //
                "    \"depositpaid\" : true,\n" + //
                "    \"bookingdates\" : {\n" + //
                "        \"checkin\" : \"2018-01-01\",\n" + //
                "        \"checkout\" : \"2019-01-01\"\n" + //
                "    },\n" + //
                "    \"additionalneeds\" : \"Breakfast\"\n" + //
                "}";
        Response response = BookingAPI.updateBooking("1", request, token);
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void updateBookingWithInvalidToken() {
        String request = "{\n" + //
                "            \"firstname\": \"Jono\",\n" + //
                "            \"lastname\": \"Joni\",\n" + //
                "            \"totalprice\": 500000,\n" + //
                "            \"depositpaid\": true,\n" + //
                "            \"bookingdates\": {\n" + //
                "                \"checkin\": \"2018-01-01\",\n" + //
                "                \"checkout\": \"2019-01-01\"\n" + //
                "            },\n" + //
                "            \"additionalneeds\": \"Smooking Room\"\n" + //
                "        }";
        Response response = BookingAPI.updateBooking("1", request, "123");
        Assert.assertEquals(response.getStatusCode(), 403);

    }

    @Test
    public void updateBookingWithEmptyPayloads() {
        String request = "{}";
        Response response = BookingAPI.updateBooking("1", request, token);
        Assert.assertEquals(response.getStatusCode(), 400);
    }

}
