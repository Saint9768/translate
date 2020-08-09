package com.wind.demo.translate.trans.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wind.demo.translate.lang.LANG;
import com.wind.demo.translate.trans.AbstractTranslator;
import com.wind.demo.util.Util;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public final class IcibaTranslator extends AbstractTranslator {
    private static final String url = "http://fy.iciba.com/ajax.php?a=fy";

    public IcibaTranslator(){
        super(url);
    }

    @Override
    public void setLangSupport() {
        langMap.put(LANG.ZH, "zh");
        langMap.put(LANG.EN, "en");
    }

    @Override
    public void setFormData(LANG from, LANG to, String text) {
        formData.put("f", langMap.get(from));
        formData.put("t", langMap.get(to));
        formData.put("w", text);
    }

    @Override
    public String query() throws Exception {
        HttpPost request = new HttpPost(Util.getUrlWithQueryString(url, formData));
        CloseableHttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "utf-8");

        EntityUtils.consume(entity);
        response.getEntity().getContent().close();
        response.close();

        return result;
    }

    @Override
    public String parses(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(text).path("content").findPath("out").toString();
    }
}
