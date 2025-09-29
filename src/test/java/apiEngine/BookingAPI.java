package apiEngine;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class BookingAPI {

    public static <T> Response authorization(T reqBody) {
        return given()
                .body(reqBody)
                .when()
                .post("auth");

    }

    public static Response getBookingById(String path, String bookingId) {
        return given()
                .when()
                .get(path + "/" + bookingId);
    }

    public static Response getBookingIds(String path) {
        return given()
                .when()
                .get(path);
    }

    public static <T> Response createBooking(T reqBody) {
        return given()
                .body(reqBody)
                .when()
                .post("booking");
    }

    public static <T> Response updateBooking(String bookingId, T reqBody, String token) {
        return given()
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(reqBody)
                .when()
                .put("booking/" + bookingId);
    }

    public static <T> Response partialUpdateBooking(String bookingId, T reqBody, String token) {
        return given()
                .header("Cookie", "token=" + token)
                .body(reqBody)
                .when()
                .patch("booking/" + bookingId);
    }

    public static Response deleteBooking(String bookingId, String token) {
        return given()
                .header("Cookie", "token=" + token)
                .when()
                .delete("booking/" + bookingId);
    }
}