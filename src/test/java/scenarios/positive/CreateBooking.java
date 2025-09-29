package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestCreateBooking;
import com.example.models.response.ResponseCreateBooking;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class CreateBooking extends BaseTest {

        @Test
        public void createBookingWithValidRequest() {
                RequestCreateBooking request = Helper.findPayloadByUseCase("create_booking.json",
                                "positive_createBookingWithAllFields", RequestCreateBooking.class);
                Response responseActual = BookingAPI.createBooking(request);
                ResponseCreateBooking responseExpected = Helper.findResponseByUseCase("create_booking.json",
                                "positive_createBookingWithAllFields", ResponseCreateBooking.class);
                // Assert
                Assert.assertEquals(responseActual.getStatusCode(), 200);
                Assert.assertNotNull(responseActual.jsonPath().getInt("bookingid"));
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

        @Test
        public void createBookingWithZeroPrice() {
                RequestCreateBooking request = Helper.findPayloadByUseCase("create_booking.json",
                                "positive_createBookingWithZeroPrice", RequestCreateBooking.class);
                Response responseActual = BookingAPI.createBooking(request);
                ResponseCreateBooking responseExpected = Helper.findResponseByUseCase("create_booking.json",
                                "positive_createBookingWithZeroPrice", ResponseCreateBooking.class);
                // Assert
                Assert.assertEquals(responseActual.getStatusCode(), 200);
                Assert.assertNotNull(responseActual.jsonPath().getInt("bookingid"));
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

        @Test
        public void createBookingWithoutDepositPaid() {
                RequestCreateBooking request = Helper.findPayloadByUseCase("create_booking.json",
                                "positive_createBookingWithEmptyCheckInAndCheckOut", RequestCreateBooking.class);
                Response responseActual = BookingAPI.createBooking(request);
                ResponseCreateBooking responseExpected = Helper.findResponseByUseCase("create_booking.json",
                                "positive_createBookingWithEmptyCheckInAndCheckOut", ResponseCreateBooking.class);
                // Assert
                Assert.assertEquals(responseActual.getStatusCode(), 200);
                Assert.assertNotNull(responseActual.jsonPath().getInt("bookingid"));
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
}
