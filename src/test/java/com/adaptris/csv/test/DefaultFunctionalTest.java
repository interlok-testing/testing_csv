package com.adaptris.csv.test;

import com.adaptris.interlok.boot.InterlokLauncher;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.util.List;


public class DefaultFunctionalTest {
    private static CloseableHttpClient client = HttpClients.createDefault();

    @BeforeAll
    public static void setup() throws Exception {
        InterlokLauncher.main(new String[] {"./config/bootstrap.properties"});
        Thread.sleep(10000); // wait for adapter to start up
        // TODO: is there some way to hook into when the adapter has started up?
    }

    @AfterAll
    public static void tearDown() throws Exception {
        client.close();
    }

    static class TestCaseData {
        String url;
        String input;
        String expected;

        TestCaseData(String url, String resource, String expected) {
            this.url = url;
            this.input = resource;
            this.expected = expected;
        }
    }

    static List<TestCaseData> default_test_case_data_csv_json_xml() {
        return List.of(
            new TestCaseData("http://localhost:8081/csv-to/xml", "example-3-lines.csv", "example-3-lines.xml"),
            new TestCaseData("http://localhost:8081/csv-to/xml", "example-586-lines.csv", "example-586-lines.xml"),
            new TestCaseData("http://localhost:8081/streaming-csv-to/xml", "example-586-lines.csv", "example-586-lines-streaming.xml"),
            new TestCaseData("http://localhost:8081/unchecked-csv-to/xml", "unchecked-example.csv", "unchecked-example.xml"),
            new TestCaseData("http://localhost:8081/csv-to/json/array", "example-3-lines.csv", "example-3-records.json"),
            new TestCaseData("http://localhost:8081/csv-to/json/lines", "example-3-lines.csv", "example-3-lines2.json"),
            new TestCaseData("http://localhost:8081/json-to/csv", "example-3-records.json", "example-3-lines.csv"),
            new TestCaseData("http://localhost:8081/json-to-fixed/csv?header=Priority,Issue+key,Status", "example-3-records.json", "example-3-lines-fixed.csv")
        );
    }


    @ParameterizedTest
    @MethodSource("default_test_case_data_csv_json_xml")
    void test(TestCaseData testCaseData) throws Exception{
        HttpPost httpPost = new HttpPost(testCaseData.url);
        ClassLoader loader = getClass().getClassLoader();
        try (InputStream is = loader.getResourceAsStream(testCaseData.input); InputStream is1 = loader.getResourceAsStream(testCaseData.expected)) {
            String input = new String(is.readAllBytes());
            String expected = new String(is1.readAllBytes());
            httpPost.setEntity(HttpEntities.create(input));
            try (CloseableHttpResponse response = client.execute(httpPost)) {
                String responseStr = new String(response.getEntity().getContent().readAllBytes());
                // strip out xml declaration as encoding could be different
                Assertions.assertEquals(StringUtils.deleteWhitespace(expected.substring(expected.indexOf("?>") + 2))
                ,StringUtils.deleteWhitespace(responseStr.substring(responseStr.indexOf("?>") + 2)));
            }
        }
    }
}
