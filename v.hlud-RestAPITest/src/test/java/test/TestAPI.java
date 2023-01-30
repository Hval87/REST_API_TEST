package test;

import bean.UserRequestModel;
import bean.UserResponseModel;
import bean.UserData;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.APIUtil;
import util.ConfigDataMNGR;
import util.TestDataMNGR;
import util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
public class TestAPI {

    private UserData userDataFromResponse;
    private static final String JSON_SCHEMA_PATH = "src/test/resources/response-schema.json";

    @Test
    public void testGettingListOfPostsFromDB() {

        Integer statusCode = Math.toIntExact(TestDataMNGR.getValueFromArray("testGettingListOfPostsFromDB", "expectedStatusCode"));
        String expectedType = TestDataMNGR.getValueFromArray("testGettingListOfPostsFromDB", "expectedContentType");
        String URI = ConfigDataMNGR.getValueFromArray("testGettingListOfPostsFromDB", "uri");
        Response response = APIUtil.getResponse(URI);
        List<UserResponseModel> userList = APIUtil.getListFromResponse(URI, UserResponseModel.class, ".");
        List<UserResponseModel> expectedList = userList
                .stream()
                .sorted(Comparator.comparing(UserResponseModel::getUserId))
                .collect(Collectors.toList());
        Assert.assertEquals(userList, expectedList, "userList from response is not equals the user list from DB ");
        Assert.assertEquals(statusCode, response.getStatusCode(), "statusCode is not correct");
        Assert.assertEquals(expectedType, response.getContentType(), "content type is incorrect");
    }

    @Test
    public void testGettingUserFromDB() {
        Integer expectedStatusCode = Math.toIntExact(TestDataMNGR.getValueFromArray("testGettingUserFromDB", "expectedStatusCode"));
        int userPositionForRequest=Math.toIntExact(TestDataMNGR.getValueFromArray("testGettingUserFromDB","userPositionForRequest"));
        String URI = String.format(ConfigDataMNGR.getValueFromArray("testGettingUserFromDB", "uri")+"%s",userPositionForRequest);
        UserResponseModel user=APIUtil.getModelFromResponse(URI,UserResponseModel.class);

        Assert.assertEquals(user.getId(), (long) TestDataMNGR.getValueFromArray("testGettingUserFromDB", "id"), "user is incorrect");
        Assert.assertEquals(user.getUserId(), (long) TestDataMNGR.getValueFromArray("testGettingUserFromDB", "userId"));
        Assert.assertNotNull(user.getBody(), "user body is empty ");
        Assert.assertNotNull(user.getTitle(), "user title is empty");
        Assert.assertEquals(APIUtil.getResponse(URI).getStatusCode(), expectedStatusCode, "status code is not correct");
    }

    @Test
    public void testIncorrectRequest() {
        Integer expectedStatusCode = Math.toIntExact(TestDataMNGR.getValueFromArray("testIncorrectRequest", "expectedStatusCode"));
        int userPositionForRequest=Math.toIntExact(TestDataMNGR.getValueFromArray("testIncorrectRequest","incorrectUserPosition"));
        String URI = String.format(ConfigDataMNGR.getValueFromArray("testIncorrectRequest", "uri")+"%s",userPositionForRequest);
        UserResponseModel user = APIUtil.getModelFromResponse(URI,UserResponseModel.class);

        Assert.assertEquals(user.getUserId(), 0, "user is not empty");
        Assert.assertEquals(user.getId(), 0, "user is not empty");
        Assert.assertEquals(user.getBody(), null, "user is not empty");
        Assert.assertEquals(user.getTitle(), null, "user is not empty");
        Assert.assertEquals(APIUtil.getResponse(URI).getStatusCode(), expectedStatusCode, "status code is not correct");
    }

    @Test
    public void sendPostRequest() {
        Integer expectedStatusCode = Math.toIntExact(TestDataMNGR.getValueFromArray("sendPostRequest()", "expectedStatusCode"));
        String title = RandomUtil.generateRandomString();
        String body = RandomUtil.generateRandomString();
        String URI = ConfigDataMNGR.getValueFromArray("sendPostRequest", "uri");

        UserRequestModel requestedUser = new UserRequestModel(Integer.parseInt(TestDataMNGR.getValue("userId")), title, body);
        UserResponseModel responseUser=APIUtil.getModelFromPostResponse(requestedUser,URI,UserResponseModel.class);

        Assert.assertEquals(responseUser.getTitle(), requestedUser.getTitle(), "users are not equal");
        Assert.assertEquals(responseUser.getBody(), requestedUser.getBody(), "data about users is not same");
        Assert.assertEquals(responseUser.getUserId(), requestedUser.getUserId(), "ata about users is not same");
        Assert.assertTrue(responseUser.getId() != 0, "id value is null");
        Assert.assertEquals(APIUtil.getResponseFromPost(requestedUser, URI).getStatusCode(), expectedStatusCode, "status code is not correct");
    }

    @Test
    public void gettingUsersList() {
        Integer expectedStatusCode = Math.toIntExact(TestDataMNGR.getValueFromArray("gettingUsersList", "expectedStatusCode"));
        String URI = ConfigDataMNGR.getValueFromArray("gettingUsersList", "uri");
        int userPositionNumber=Math.toIntExact(TestDataMNGR.getValueFromArray("gettingUsersList","userPositionNumber"));

        List<UserData> responsedUsers = APIUtil.getListFromResponse(URI, UserData.class, ".");
        userDataFromResponse = responsedUsers.stream().filter((x) -> x.getId() == userPositionNumber).collect(Collectors.toList()).get(0);
        UserData userDataFromJSON = TestDataMNGR.readJSONtoBean(JSON_SCHEMA_PATH, UserData.class);

        Assert.assertEquals(this.userDataFromResponse, userDataFromJSON, "user from response is not equals the user from response");
        Assert.assertEquals(APIUtil.getResponse(URI).getStatusCode(), expectedStatusCode, "status code is not correct");
    }

    @Test
    public void testOfGettingCorrectDataByID() {
        Integer expectedStatusCode = Math.toIntExact(TestDataMNGR.getValueFromArray("testOfGettingCorrectDataByID", "expectedStatusCode"));
        int userPositionForRequest=Math.toIntExact(TestDataMNGR.getValueFromArray("testOfGettingCorrectDataByID","userPositionForRequest"));
        String URI = String.format(ConfigDataMNGR.getValueFromArray("testOfGettingCorrectDataByID", "uri")+"%s",userPositionForRequest);

        UserData curentUser = APIUtil.getModelFromResponse(URI,UserData.class);

        Assert.assertEquals(userDataFromResponse, curentUser, "users are not equal");
        Assert.assertEquals(APIUtil.getResponse(URI).getStatusCode(), expectedStatusCode, "incorrect status code!!");
    }
}

