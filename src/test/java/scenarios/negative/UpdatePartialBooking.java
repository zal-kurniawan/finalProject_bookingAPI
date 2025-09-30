package scenarios.negative;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import io.restassured.response.Response;

public class UpdatePartialBooking extends BaseTest {

        @Test
        public void updatePartialBookingWithInvalidMethod() {
                String request = "{\n" + //
                                "    \"firstname\" : \"James\",\n" + //
                                "    \"lastname\" : \"Brown\"\n" + //
                                "}";
                Response response = given()
                                .header("Cookie", "token=" + token)
                                .body(request)
                                .when()
                                .put("booking/1");
                Assert.assertEquals(response.getStatusCode(), 400);
        }

        @Test
        public void updatePartialBookingWithInvalidToken() {
                String request = "{\n" + //
                                "    \"firstname\" : \"James\",\n" + //
                                "    \"lastname\" : \"Brown\"\n" + //
                                "}";
                Response response = given()
                                .header("Cookie", "token=1")
                                .body(request)
                                .when()
                                .patch("booking/1");
                Assert.assertEquals(response.getStatusCode(), 403);
        }

        @Test
        public void updatePartialBookingWithInvalidPath() {
                String request = "{\n" + //
                                "    \"firstname\" : \"James\",\n" + //
                                "    \"lastname\" : \"Brown\"\n" + //
                                "}";
                Response response = given()
                                .header("Cookie", "token=" + token)
                                .body(request)
                                .when()
                                .patch("bookings/1");
                Assert.assertEquals(response.getStatusCode(), 404);
        }
}
