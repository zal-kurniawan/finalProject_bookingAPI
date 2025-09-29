package scenarios.positive;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestAuth;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class Authorization extends BaseTest {

    @Test
    public void authWithValidCredentials() {
        RequestAuth request = Helper.findPayloadByUseCase("authorization.json", "positive_authWithValidCredentials",
                RequestAuth.class);
        Response response = BookingAPI.authorization(request);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("token"));
    }

    @Test
    public void authWithReversedPayload() {
        String request = "{\n" + //
                "    \"password\": \"password123\",\n" + //
                "    \"username\": \"admin\"\n" + //
                "}";
        Response response = BookingAPI.authorization(request);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("token"));
    }
}
