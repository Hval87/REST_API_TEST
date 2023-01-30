package util;

import bean.UserData;
import bean.UserRequestModel;
import bean.UserResponseModel;
import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.List;

import lombok.extern.log4j.Log4j;

import static util.BaseAPIUtil.getRequest;

@Log4j
public class APIUtil {
    private static String URL;
    private static String fullRequestPath;

    static {
        URL = ConfigDataMNGR.getConfValue("url");
    }

    public static Response getResponse(String URI) {
        fullRequestPath = String.format(URL + "%s", URI);
        return getRequest()
                .get(fullRequestPath)
                .then()
                .extract().response();
    }

    public static Response getResponseFromPost(UserRequestModel user, String URI) {
        fullRequestPath = String.format(URL + "%s", URI);
        return getRequest()
                .body(user)
                .post(fullRequestPath)
                .then()
                .extract().response();
    }

    public static <T> List getListFromResponse(String URI, Class type, String path) {
        fullRequestPath = String.format(URL + "%s", URI);
        return getRequest()
                .get(fullRequestPath)
                .jsonPath().getList(path, type);
    }


    public static <T> T getModelFromPostResponse(UserRequestModel user, String URI, Class<?> target) {
        fullRequestPath = String.format(URL + "%s", URI);
        Class type = null;
        try {
            type = Class.forName(target.getName());
        } catch (ClassNotFoundException e) {
            log.error("error while getting class-> ", e);
        }
        return getRequest()
                .body(user)
                .post(fullRequestPath)
                .as((Type) type);
    }

    public static <T> T getModelFromResponse(String URI, Class<?> target) {
        fullRequestPath = String.format(URL + "%s", URI);
        Class type = null;
        try {
            type = Class.forName(target.getName());
        } catch (ClassNotFoundException e) {
            log.error("error while getting class-> ", e);
        }
        return getRequest()
                .get(fullRequestPath)
                .as((Type) type);
    }
}
