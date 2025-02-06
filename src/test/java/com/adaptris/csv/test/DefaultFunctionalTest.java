package com.adaptris.csv.test;

import com.adaptris.testing.AbstractAdapterFunctionalTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.util.List;

public class DefaultFunctionalTest extends AbstractAdapterFunctionalTest {
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

    List<TestCaseData> default_test_case_data_csv_json_xml() {
        String adapterBaseUrl = getBaseAdapterUrl();
        return List.of(
            new TestCaseData(adapterBaseUrl + "/csv-to/xml", "example-3-lines.csv", "example-3-lines.xml"),
            new TestCaseData(adapterBaseUrl + "/csv-to/xml", "example-586-lines.csv", "example-586-lines.xml"),
            new TestCaseData(adapterBaseUrl + "/streaming-csv-to/xml", "example-586-lines.csv", "example-586-lines-streaming.xml"),
            new TestCaseData(adapterBaseUrl + "/unchecked-csv-to/xml", "unchecked-example.csv", "unchecked-example.xml"),
            new TestCaseData(adapterBaseUrl + "/csv-to/json/array", "example-3-lines.csv", "example-3-records.json"),
            new TestCaseData(adapterBaseUrl + "/csv-to/json/lines", "example-3-lines.csv", "example-3-lines2.json"),
            new TestCaseData(adapterBaseUrl + "/json-to/csv", "example-3-records.json", "example-3-lines.csv"),
            new TestCaseData(adapterBaseUrl + "/json-to-fixed/csv?header=Priority,Issue+key,Status", "example-3-records.json", "example-3-lines-fixed.csv")
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
