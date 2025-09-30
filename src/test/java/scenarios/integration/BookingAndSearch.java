package scenarios.integration;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.models.response.ResponseCreateBooking;
import com.example.models.response.ResponseGetBookingByID;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class BookingAndSearch extends BaseTest {
    String firstname, lastname, bookingId;

    @Test(priority = 1)
    public void createBooking() {
        RequestCreateBooking request = Helper.findPayloadByUseCase("create_booking.json",
                "integration_createBookingAndSearch",
                RequestCreateBooking.class);
        ResponseCreateBooking responseExpected = Helper.findResponseByUseCase("create_booking.json",
                "integration_createBookingAndSearch", ResponseCreateBooking.class);
        Response responseActual = BookingAPI.createBooking(request);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(responseActual.jsonPath().getString("bookingid"), "Booking ID should not be null");
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
        // Prepare for next case
        firstname = responseActual.jsonPath().getString("booking.firstname");
        lastname = responseActual.jsonPath().getString("booking.lastname");

    }

    @Test(priority = 2, dependsOnMethods = "createBooking")
    public void getIdBookingByFirstAndLastName() {
        Response response = BookingAPI
                .getBookingIds("booking?firstname=" + firstname + "&lastname=" + lastname + "");
        // Assertion
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(response.jsonPath().getString("[0].bookingid"));
        // Prepare for next case
        bookingId = response.jsonPath().getString("[0].bookingid");
    }

    @Test(priority = 3, dependsOnMethods = "getIdBookingByFirstAndLastName")
    public void getBookingDetail() {
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "integration_getBookingDetail", ResponseGetBookingByID.class);
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

    @Test(priority = 4, dependsOnMethods = "getIdBookingByFirstAndLastName")
    public void deleteBooking() {
        Response response = BookingAPI.deleteBooking(bookingId, token);
        // Assertion
        Assert.assertEquals(response.statusCode(), 201, "Status code should be 201");
    }

    @Test(priority = 5, dependsOnMethods = "deleteBooking")
    public void getBookingAfterDelete() {
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        // Assertion
        Assert.assertEquals(responseActual.statusCode(), 404, "Status code should be 404");
    }
}
