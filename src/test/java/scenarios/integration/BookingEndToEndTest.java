package scenarios.integration;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.models.request.RequestUpdateBooking;
import com.example.models.response.ResponseCreateBooking;
import com.example.models.response.ResponseGetBookingByID;
import com.example.models.response.ResponseUpdateBooking;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class BookingEndToEndTest extends BaseTest {
    String bookingId;

    @Test(priority = 1)
    public void createBooking() {
        RequestCreateBooking request = Helper.findPayloadByUseCase("create_booking.json", "integration_createBooking",
                RequestCreateBooking.class);
        ResponseCreateBooking responseExpected = Helper.findResponseByUseCase("create_booking.json",
                "integration_createBooking", ResponseCreateBooking.class);
        Response responseActual = BookingAPI.createBooking(request);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(responseActual.jsonPath().getString("bookingid"), "Booking ID should not be null");
        bookingId = responseActual.jsonPath().getString("bookingid");
        Assert.assertEquals(responseActual.jsonPath().getString("booking.firstname"),
                responseExpected.booking.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("booking.lastname"),
                responseExpected.booking.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("booking.totalprice"),
                responseExpected.booking.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("booking.depositpaid"),
                responseExpected.booking.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("booking.bookingdates.checkin"),
                responseExpected.booking.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("booking.bookingdates.checkout"),
                responseExpected.booking.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("booking.additionalneeds"),
                responseExpected.booking.additionalneeds);
    }

    @Test(priority = 2, dependsOnMethods = "createBooking")
    public void getBookingAfterCreate() {
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "integration_getBookingAfterCreate", ResponseGetBookingByID.class);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);

    }

    @Test(priority = 3, dependsOnMethods = "createBooking")
    public void updateBooking() {
        RequestUpdateBooking request = Helper.findPayloadByUseCase("update_booking.json", "integration_updateBooking",
                RequestUpdateBooking.class);
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_booking.json",
                "integration_updateBooking", ResponseUpdateBooking.class);
        Response responseActual = BookingAPI.updateBooking(bookingId, request,
                token);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);
    }

    @Test(priority = 4, dependsOnMethods = "updateBooking")
    public void getBookingAfterUpdate() {
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "integration_getBookingAfterUpdate", ResponseGetBookingByID.class);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);
    }

    @Test(priority = 5, dependsOnMethods = "updateBooking")
    public void partialUpdateBooking() {
        String request = "{\n" + //
                "  \"firstname\":\"Wawan\",\n" + //
                "  \"lastname\":\"Hariyono\",\n" + //
                "  \"additionalneeds\": \"Dinner, Private Pool, Smooking Room\"\n" + //
                "}";
        ResponseUpdateBooking responseExpected = Helper.findResponseByUseCase("update_booking.json",
                "integration_updatePartialBooking", ResponseUpdateBooking.class);
        // Assertion
        Response responseActual = BookingAPI.partialUpdateBooking(bookingId,
                request, token);
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);
    }

    @Test(priority = 6, dependsOnMethods = "partialUpdateBooking")
    public void getBookingAfterPartialUpdate() {
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "integration_getBookingAfterPartialUpdate", ResponseGetBookingByID.class);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(responseActual.jsonPath().getString("firstname"),
                responseExpected.firstname);
        Assert.assertEquals(responseActual.jsonPath().getString("lastname"),
                responseExpected.lastname);
        Assert.assertEquals(responseActual.jsonPath().getInt("totalprice"), responseExpected.totalprice);
        Assert.assertEquals(responseActual.jsonPath().getBoolean("depositpaid"), responseExpected.depositpaid);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkin"),
                responseExpected.bookingdates.checkin);
        Assert.assertEquals(responseActual.jsonPath().getString("bookingdates.checkout"),
                responseExpected.bookingdates.checkout);
        Assert.assertEquals(responseActual.jsonPath().getString("additionalneeds"),
                responseExpected.additionalneeds);
    }

    @Test(priority = 7, dependsOnMethods = "partialUpdateBooking")
    public void deleteBooking() {
        Response response = BookingAPI.deleteBooking(bookingId, token);
        // Assertion
        Assert.assertEquals(response.statusCode(), 201, "Status code should be 201");
    }

    @Test(priority = 8, dependsOnMethods = "deleteBooking")
    public void getBookingAfterDelete() {
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 404, "Status code should be 404");
    }
}
