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

public class IntegrationBooking extends BaseTest {
        public String firstname, lastname, bookingId;

        @Test(priority = 1)
        public void bookingAndSearch() {
                // Create booking
                RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                                "integration_createBookingAndSearch",
                                RequestCreateBooking.class);
                ResponseCreateBooking responseExpectedCreate = Helper.findResponseByUseCase("create_booking.json",
                                "integration_createBookingAndSearch", ResponseCreateBooking.class);
                Response responseActualCreate = BookingAPI.createBooking(requestCreate);
                // Assertion create
                Assert.assertEquals(responseActualCreate.statusCode(), 200, "Status code should be 200");
                Assert.assertNotNull(responseActualCreate.jsonPath().getString("bookingid"),
                                "Booking ID should not be null");
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.firstname"),
                                responseExpectedCreate.booking.firstname);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.lastname"),
                                responseExpectedCreate.booking.lastname);
                Assert.assertEquals(responseActualCreate.jsonPath().getInt("booking.totalprice"),
                                responseExpectedCreate.booking.totalprice);
                Assert.assertEquals(responseActualCreate.jsonPath().getBoolean("booking.depositpaid"),
                                responseExpectedCreate.booking.depositpaid);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.bookingdates.checkin"),
                                responseExpectedCreate.booking.bookingdates.checkin);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.bookingdates.checkout"),
                                responseExpectedCreate.booking.bookingdates.checkout);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.additionalneeds"),
                                responseExpectedCreate.booking.additionalneeds);
                // Prepare for get id booking
                firstname = responseActualCreate.jsonPath().getString("booking.firstname");
                lastname = responseActualCreate.jsonPath().getString("booking.lastname");

                // Get booking id
                Response responseGetBookingId = BookingAPI
                                .getBookingIds("booking?firstname=" + firstname + "&lastname=" + lastname + "");
                // Assertion get booking id
                Assert.assertEquals(responseGetBookingId.getStatusCode(), 200, "Status code should be 200");
                Assert.assertNotNull(responseGetBookingId.jsonPath().getString("[0].bookingid"));
                // Prepare for get detail booking
                bookingId = responseGetBookingId.jsonPath().getString("[0].bookingid");

                // Get detail booking
                Response responseActualBookingDetail = BookingAPI.getBookingById("booking", bookingId);
                ResponseGetBookingByID responseExpectedBookingDetail = Helper.findResponseByUseCase(
                                "get_booking_by_id.json",
                                "integration_getBookingDetail", ResponseGetBookingByID.class);
                // Assertion get detail booking
                Assert.assertEquals(responseActualBookingDetail.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("firstname"),
                                responseExpectedBookingDetail.firstname);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("lastname"),
                                responseExpectedBookingDetail.lastname);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getInt("totalprice"),
                                responseExpectedBookingDetail.totalprice);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getBoolean("depositpaid"),
                                responseExpectedBookingDetail.depositpaid);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("bookingdates.checkin"),
                                responseExpectedBookingDetail.bookingdates.checkin);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("bookingdates.checkout"),
                                responseExpectedBookingDetail.bookingdates.checkout);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("additionalneeds"),
                                responseExpectedBookingDetail.additionalneeds);

                // Delete booking
                Response responseDelete = BookingAPI.deleteBooking(bookingId, token);
                // Assertion delete booking
                Assert.assertEquals(responseDelete.statusCode(), 201, "Status code should be 201");

                // Verify booking deleted
                Response responseVerifyDeleted = BookingAPI.getBookingById("booking", bookingId);
                Assert.assertEquals(responseVerifyDeleted.statusCode(), 404, "Status code should be 404");
        }

        @Test(priority = 2)
        public void bookingWithFullUpdate() {
                // Create booking
                RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                                "integration_createBookingFullUpdate",
                                RequestCreateBooking.class);
                ResponseCreateBooking responseExpectedCreate = Helper.findResponseByUseCase("create_booking.json",
                                "integration_createBookingFullUpdate", ResponseCreateBooking.class);
                Response responseActualCreate = BookingAPI.createBooking(requestCreate);
                // Assertion create booking
                Assert.assertEquals(responseActualCreate.statusCode(), 200, "Status code should be 200");
                Assert.assertNotNull(responseActualCreate.jsonPath().getString("bookingid"),
                                "Booking ID should not be null");
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.firstname"),
                                responseExpectedCreate.booking.firstname);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.lastname"),
                                responseExpectedCreate.booking.lastname);
                Assert.assertEquals(responseActualCreate.jsonPath().getInt("booking.totalprice"),
                                responseExpectedCreate.booking.totalprice);
                Assert.assertEquals(responseActualCreate.jsonPath().getBoolean("booking.depositpaid"),
                                responseExpectedCreate.booking.depositpaid);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.bookingdates.checkin"),
                                responseExpectedCreate.booking.bookingdates.checkin);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.bookingdates.checkout"),
                                responseExpectedCreate.booking.bookingdates.checkout);
                Assert.assertEquals(responseActualCreate.jsonPath().getString("booking.additionalneeds"),
                                responseExpectedCreate.booking.additionalneeds);
                // Prepare for get booking detail
                bookingId = responseActualCreate.jsonPath().getString("bookingid");

                // Get booking detail
                Response responseActualBookingDetail = BookingAPI.getBookingById("booking", bookingId);
                ResponseGetBookingByID responseExpectedBookingDetail = Helper.findResponseByUseCase(
                                "get_booking_by_id.json",
                                "integration_getBookingAfterCreateFullUpdate", ResponseGetBookingByID.class);
                // Assertion booking detail
                Assert.assertEquals(responseActualBookingDetail.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("firstname"),
                                responseExpectedBookingDetail.firstname);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("lastname"),
                                responseExpectedBookingDetail.lastname);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getInt("totalprice"),
                                responseExpectedBookingDetail.totalprice);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getBoolean("depositpaid"),
                                responseExpectedBookingDetail.depositpaid);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("bookingdates.checkin"),
                                responseExpectedBookingDetail.bookingdates.checkin);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("bookingdates.checkout"),
                                responseExpectedBookingDetail.bookingdates.checkout);
                Assert.assertEquals(responseActualBookingDetail.jsonPath().getString("additionalneeds"),
                                responseExpectedBookingDetail.additionalneeds);

                // Full update booking
                RequestUpdateBooking requestUpdate = Helper.findPayloadByUseCase("update_booking.json",
                                "integration_fullUpdateBooking",
                                RequestUpdateBooking.class);
                ResponseUpdateBooking responseUpdateExpected = Helper.findResponseByUseCase("update_booking.json",
                                "integration_fullUpdateBooking", ResponseUpdateBooking.class);
                Response responseUpdateActual = BookingAPI.updateBooking(bookingId, requestUpdate, token);
                // Assertion full update booking
                Assert.assertEquals(responseUpdateActual.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responseUpdateActual.jsonPath().getString("firstname"),
                                responseUpdateExpected.firstname);
                Assert.assertEquals(responseUpdateActual.jsonPath().getString("lastname"),
                                responseUpdateExpected.lastname);
                Assert.assertEquals(responseUpdateActual.jsonPath().getInt("totalprice"),
                                responseUpdateExpected.totalprice);
                Assert.assertEquals(responseUpdateActual.jsonPath().getBoolean("depositpaid"),
                                responseUpdateExpected.depositpaid);
                Assert.assertEquals(responseUpdateActual.jsonPath().getString("bookingdates.checkin"),
                                responseUpdateExpected.bookingdates.checkin);
                Assert.assertEquals(responseUpdateActual.jsonPath().getString("bookingdates.checkout"),
                                responseUpdateExpected.bookingdates.checkout);
                Assert.assertEquals(responseUpdateActual.jsonPath().getString("additionalneeds"),
                                responseUpdateExpected.additionalneeds);

                // Get booking detail after update
                Response responseDetailUpdateActual = BookingAPI.getBookingById("booking", bookingId);
                ResponseGetBookingByID responseDetailUpdateExpected = Helper.findResponseByUseCase(
                                "get_booking_by_id.json",
                                "integration_getBookingAfterFullUpdate", ResponseGetBookingByID.class);
                // Assertion booking detail after update
                Assert.assertEquals(responseDetailUpdateActual.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("firstname"),
                                responseDetailUpdateExpected.firstname);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("lastname"),
                                responseDetailUpdateExpected.lastname);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getInt("totalprice"),
                                responseDetailUpdateExpected.totalprice);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getBoolean("depositpaid"),
                                responseDetailUpdateExpected.depositpaid);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("bookingdates.checkin"),
                                responseDetailUpdateExpected.bookingdates.checkin);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("bookingdates.checkout"),
                                responseDetailUpdateExpected.bookingdates.checkout);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("additionalneeds"),
                                responseDetailUpdateExpected.additionalneeds);

                // Delete booking
                Response responseDelete = BookingAPI.deleteBooking(bookingId, token);
                // Assertion delete booking
                Assert.assertEquals(responseDelete.statusCode(), 201, "Status code should be 201");

                // Verify booking deleted
                Response responseVerifyDeleted = BookingAPI.getBookingById("booking", bookingId);
                Assert.assertEquals(responseVerifyDeleted.statusCode(), 404, "Status code should be 404");
        }

        @Test(priority = 3)
        public void bookingWithPartialUpdate() {
                // Create booking
                RequestCreateBooking requestCreate = Helper.findPayloadByUseCase("create_booking.json",
                                "integration_createBookingPartialUpdate",
                                RequestCreateBooking.class);
                ResponseCreateBooking responseCreateExpected = Helper.findResponseByUseCase("create_booking.json",
                                "integration_createBookingPartialUpdate", ResponseCreateBooking.class);
                Response responseCreateActual = BookingAPI.createBooking(requestCreate);
                // Assertion create booking
                Assert.assertEquals(responseCreateActual.statusCode(), 200, "Status code should be 200");
                Assert.assertNotNull(responseCreateActual.jsonPath().getString("bookingid"),
                                "Booking ID should not be null");
                Assert.assertEquals(responseCreateActual.jsonPath().getString("booking.firstname"),
                                responseCreateExpected.booking.firstname);
                Assert.assertEquals(responseCreateActual.jsonPath().getString("booking.lastname"),
                                responseCreateExpected.booking.lastname);
                Assert.assertEquals(responseCreateActual.jsonPath().getInt("booking.totalprice"),
                                responseCreateExpected.booking.totalprice);
                Assert.assertEquals(responseCreateActual.jsonPath().getBoolean("booking.depositpaid"),
                                responseCreateExpected.booking.depositpaid);
                Assert.assertEquals(responseCreateActual.jsonPath().getString("booking.bookingdates.checkin"),
                                responseCreateExpected.booking.bookingdates.checkin);
                Assert.assertEquals(responseCreateActual.jsonPath().getString("booking.bookingdates.checkout"),
                                responseCreateExpected.booking.bookingdates.checkout);
                Assert.assertEquals(responseCreateActual.jsonPath().getString("booking.additionalneeds"),
                                responseCreateExpected.booking.additionalneeds);
                // Prepare for get booking detail
                bookingId = responseCreateActual.jsonPath().getString("bookingid");

                // Get booking detail
                Response responseBookingDetailActual = BookingAPI.getBookingById("booking", bookingId);
                ResponseGetBookingByID responseBookingDetailExpected = Helper.findResponseByUseCase(
                                "get_booking_by_id.json",
                                "integration_getBookingAfterCreatePartialUpdate", ResponseGetBookingByID.class);
                // Assertion booking detail
                Assert.assertEquals(responseBookingDetailActual.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getString("firstname"),
                                responseBookingDetailExpected.firstname);
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getString("lastname"),
                                responseBookingDetailExpected.lastname);
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getInt("totalprice"),
                                responseBookingDetailExpected.totalprice);
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getBoolean("depositpaid"),
                                responseBookingDetailExpected.depositpaid);
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getString("bookingdates.checkin"),
                                responseBookingDetailExpected.bookingdates.checkin);
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getString("bookingdates.checkout"),
                                responseBookingDetailExpected.bookingdates.checkout);
                Assert.assertEquals(responseBookingDetailActual.jsonPath().getString("additionalneeds"),
                                responseBookingDetailExpected.additionalneeds);

                // Partial update booking
                String requestPartialUpdate = "{\n" + //
                                " \"firstname\":\"Wawan\",\n" + //
                                " \"lastname\":\"Hariyono\",\n" + //
                                " \"additionalneeds\": \"Dinner, Private Pool, Smooking Room\"\n" + //
                                "}";
                ResponseUpdateBooking responsePartialUpdateExpected = Helper.findResponseByUseCase(
                                "update_partial_booking.json",
                                "integration_updatePartialBooking", ResponseUpdateBooking.class);
                // Assertion partial update booking
                Response responsePartialUpdateActual = BookingAPI.partialUpdateBooking(bookingId,
                                requestPartialUpdate, token);
                Assert.assertEquals(responsePartialUpdateActual.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responsePartialUpdateActual.jsonPath().getString("firstname"),
                                responsePartialUpdateExpected.firstname);
                Assert.assertEquals(responsePartialUpdateActual.jsonPath().getString("lastname"),
                                responsePartialUpdateExpected.lastname);
                Assert.assertEquals(responsePartialUpdateActual.jsonPath().getString("additionalneeds"),
                                responsePartialUpdateExpected.additionalneeds);

                // Get booking detail after partial update
                Response responseDetailUpdateActual = BookingAPI.getBookingById("booking", bookingId);
                ResponseGetBookingByID responseDetailUpdateExpected = Helper.findResponseByUseCase(
                                "get_booking_by_id.json",
                                "integration_getBookingAfterPartialUpdate", ResponseGetBookingByID.class);
                // Assertion booking detail after partial update
                Assert.assertEquals(responseDetailUpdateActual.statusCode(), 200, "Status code should be 200");
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("firstname"),
                                responseDetailUpdateExpected.firstname);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("lastname"),
                                responseDetailUpdateExpected.lastname);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getInt("totalprice"),
                                responseDetailUpdateExpected.totalprice);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getBoolean("depositpaid"),
                                responseDetailUpdateExpected.depositpaid);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("bookingdates.checkin"),
                                responseDetailUpdateExpected.bookingdates.checkin);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("bookingdates.checkout"),
                                responseDetailUpdateExpected.bookingdates.checkout);
                Assert.assertEquals(responseDetailUpdateActual.jsonPath().getString("additionalneeds"),
                                responseDetailUpdateExpected.additionalneeds);

                // Delete booking
                Response responseDelete = BookingAPI.deleteBooking(bookingId, token);
                // Assertion delete booking
                Assert.assertEquals(responseDelete.statusCode(), 201, "Status code should be 201");

                // Verify booking deleted
                Response responseVerifyDeleted = BookingAPI.getBookingById("booking", bookingId);
                Assert.assertEquals(responseVerifyDeleted.statusCode(), 404, "Status code should be 404");
        }

}
