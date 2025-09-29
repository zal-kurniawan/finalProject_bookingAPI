package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.models.request.RequestUpdateBooking;
import com.example.models.response.ResponseUpdateBooking;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class UpdateBooking extends BaseTest {
    public String bookingId;

    @BeforeMethod
    public void createBookingForUpdateScenario() {
        RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                "positive_createBookingForUpdateDeleteScenario", RequestCreateBooking.class);
        Response responseCreate = BookingAPI.createBooking(requestCreate);
        Assert.assertEquals(responseCreate.getStatusCode(), 200);
        bookingId = responseCreate.jsonPath().getString("bookingid");
    }

    @Test
    public void updateBookingWithValidRequest() {
        RequestUpdateBooking request = Helper.findPayloadByUseCase("update_booking.json",
                "positive_updateBookingWithValidRequest", RequestUpdateBooking.class);
        Response responseActual = BookingAPI.updateBooking(bookingId, request, token);
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_booking.json",
                "positive_updateBookingWithValidRequest", ResponseUpdateBooking.class);
        Assert.assertEquals(responseActual.getStatusCode(), 200);
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"),
                responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"),
                responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);
    }

    @Test
    public void updateBookingWithReversedPayload() {
        String request = "{\n" + //
                "    \"additionalneeds\" : \"Smooking Room\",\n" + //
                "    \"bookingdates\" : {\n" + //
                "        \"checkout\" : \"2019-01-01\",\n" + //
                "        \"checkin\" : \"2018-01-01\"\n" + //
                "    },\n" + //
                "    \"lastname\" : \"Joni\",\n" + //
                "    \"depositpaid\" : true,\n" + //
                "    \"totalprice\" : 500000,\n" + //
                "    \"firstname\" : \"Jono\"\n" + //
                "}";
        Response responseActual = BookingAPI.updateBooking(bookingId, request, token);
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_booking.json",
                "positive_updateBookingWithReversedPayload", ResponseUpdateBooking.class);
        Assert.assertEquals(responseActual.getStatusCode(), 200);
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"),
                responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"),
                responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);
    }

}
