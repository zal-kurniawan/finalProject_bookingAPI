package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.models.response.ResponseGetBookingByID;
import com.example.utils.Helper;

import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class GetBookingById extends BaseTest {
    public String bookingId;

    @Test
    public void getBookingByIdWithValidParam() {
        RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                "positive_createBookingForGetBookingById1", RequestCreateBooking.class);
        Response responseCreate = BookingAPI.createBooking(requestCreate);
        bookingId = responseCreate.jsonPath().getString("bookingid");

        // Get Booking By Id
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "positive_getBookingByIdWithValidParam",
                ResponseGetBookingByID.class);
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
    public void getBookingByIdWithNoDeposidPaid() {
        RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                "positive_createBookingForGetBookingById2", RequestCreateBooking.class);
        Response responseCreate = BookingAPI.createBooking(requestCreate);
        bookingId = responseCreate.jsonPath().getString("bookingid");

        // Get Booking By Id
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "positive_getBookingByIdWithNoDeposidPaid",
                ResponseGetBookingByID.class);
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
    public void getBookingByIdWithEmptyAdditionalNeeds() {
        RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                "positive_createBookingForGetBookingById3", RequestCreateBooking.class);
        Response responseCreate = BookingAPI.createBooking(requestCreate);
        bookingId = responseCreate.jsonPath().getString("bookingid");

        // Get Booking By Id
        Response responseActual = BookingAPI.getBookingById("booking", bookingId);
        ResponseGetBookingByID responseExpected = Helper.findResponseByUseCase("get_booking_by_id.json",
                "positive_getBookingByIdWithEmptyAdditionalNeeds",
                ResponseGetBookingByID.class);
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
