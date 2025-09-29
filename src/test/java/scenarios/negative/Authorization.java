package scenarios.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.example.base.BaseTest;
import com.example.models.request.RequestAuth;
import com.example.utils.Helper;
import apiEngine.BookingAPI;
import io.restassured.response.Response;

public class Authorization extends BaseTest {

    @Test
    public void authWithInvalidCredentials() {
        RequestAuth request = Helper.findPayloadByUseCase("authorization.json", "negative_authWithInvalidCredentials",
                RequestAuth.class);
        Response response = BookingAPI.authorization(request);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("reason"), "Bad credentials");
    }

    @Test
    public void authWithEmptyCredentials() {
        RequestAuth request = Helper.findPayloadByUseCase("authorization.json", "negative_authWithEmptyCredentials",
                RequestAuth.class);
        Response response = BookingAPI.authorization(request);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("reason"), "Bad credentials");
    }

    @Test
    public void authWithEmptyPayload() {
        String request = "{}";
        Response response = BookingAPI.authorization(request);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("reason"), "Bad credentials");
    }
}
