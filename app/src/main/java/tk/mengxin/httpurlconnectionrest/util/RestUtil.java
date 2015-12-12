package tk.mengxin.httpurlconnectionrest.util;

import org.apache.http.NameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Android Studio.
 * User: xin
 * Date: 02/12/2015
 * Time: 11:59
 * Version: V 1.0
 */

public class RestUtil {
    public static RestTask obtainGetTask(String url)
            throws MalformedURLException, IOException {
        HttpURLConnection connection =
                (HttpURLConnection) (new URL(url))
                        .openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        RestTask task = new RestTask(connection);
        return task;
    }
    public static RestTask obtainFormPostTask(String url,
                                              List<NameValuePair> formData)
            throws MalformedURLException, IOException {
        HttpURLConnection connection =
                (HttpURLConnection) (new URL(url))
                        .openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        RestTask task = new RestTask(connection);
        task.setFormBody(formData);
        return task;
    }
    public static RestTask obtainMultipartPostTask(
            String url, List<NameValuePair> formPart,
            File file, String fileName)
            throws MalformedURLException, IOException {
        HttpURLConnection connection =
                (HttpURLConnection) (new URL(url))
                        .openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        RestTask task = new RestTask(connection);
        task.setFormBody(formPart);
        task.setUploadFile(file, fileName);
        return task;
    }
}