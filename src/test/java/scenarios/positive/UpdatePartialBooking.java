package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.models.response.ResponseUpdateBooking;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class UpdatePartialBooking extends BaseTest {
    public String bookingId;

    @BeforeMethod
    public void createBookingForUpdatePartialScenario() {
        RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                "positive_createBookingForUpdateDeleteScenario", RequestCreateBooking.class);
        Response responseCreate = BookingAPI.createBooking(requestCreate);
        Assert.assertEquals(responseCreate.getStatusCode(), 200);
        bookingId = responseCreate.jsonPath().getString("bookingid");
    }

    @Test
    public void updatePartialBookingFirstNameAndLastName() {
        String request = "{\n" + //
                "    \"firstname\" : \"Adi\",\n" + //
                "    \"lastname\" : \"Sunaryo\"\n" + //
                "}";
        Response responseActual = BookingAPI.partialUpdateBooking(bookingId, request, token);
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_partial_booking.json",
                "positive_updatePartialBookingFirstNameAndLastName", ResponseUpdateBooking.class);
        Assert.assertEquals(responseActual.getStatusCode(), 200);
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"), responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"), responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"), responseExpected.additionalneeds);
    }

    @Test
    public void updatePartialBookingTotalPrice() {
        String request = "{\n" + //
                "    \"totalprice\" : 15000000\n" + //
                "}";
        Response responseActual = BookingAPI.partialUpdateBooking(bookingId, request, token);
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_partial_booking.json",
                "positive_updatePartialBookingTotalPrice", ResponseUpdateBooking.class);
        Assert.assertEquals(responseActual.getStatusCode(), 200);
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"), responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"), responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"), responseExpected.additionalneeds);

    }

    @Test
    public void updatePartialBookingDates() {
        String request = "{\n" + //
                "   \"bookingdates\": {\n" + //
                "        \"checkin\": \"2026-02-26\",\n" + //
                "        \"checkout\": \"2026-03-10\"\n" + //
                "    }\n" + //
                "}";
        Response responseActual = BookingAPI.partialUpdateBooking(bookingId, request, token);
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_partial_booking.json",
                "positive_updatePartialBookingDates", ResponseUpdateBooking.class);
        Assert.assertEquals(responseActual.getStatusCode(), 200);
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"), responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"), responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"), responseExpected.additionalneeds);
    }
}
