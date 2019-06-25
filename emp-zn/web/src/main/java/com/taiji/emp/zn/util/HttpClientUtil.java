package com.taiji.emp.zn.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yhcookie
 * @date 2018/12/20 23:20
 */

public class HttpClientUtil {

    public static String httpGet(String url) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpGet.setHeader("client_id","yingji");
        httpGet.setHeader("client_password","admin@123");

        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity);

        return result;
    }
}