package com.rbkmoney.midgard.adapter.mts.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.midgard.adapter.mts.data.TerminalOptionalJson;
import com.rbkmoney.midgard.adapter.mts.test.data.JsonTestData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JsonTest {

    @Test
    public void terminalOptionsJsonTest() throws IOException {
        String optionsJson = JsonTestData.OPTIONAL_JSON;
        TerminalOptionalJson terminalOptionalJson = JsonTestData.getTerminalOptionalJson();
        ObjectMapper mapper = new ObjectMapper();
        TerminalOptionalJson json = mapper.readValue(optionsJson, TerminalOptionalJson.class);
        Assert.assertNotNull("Resulting json object is null", json);
        Assert.assertTrue("Resulting json object is not equal to the example",
                json.equals(terminalOptionalJson));
    }

}