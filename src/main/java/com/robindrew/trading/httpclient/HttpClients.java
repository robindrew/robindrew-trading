package com.robindrew.trading.httpclient;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Java;

public class HttpClients {

	public static String getTextContent(HttpEntity entity) {
		return getTextContent(entity, Charsets.UTF_8);
	}

	public static String getTextContent(HttpEntity entity, Charset charset) {
		try {
			return EntityUtils.toString(entity, charset);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static byte[] getBinaryContent(HttpEntity entity) {
		try {
			return EntityUtils.toByteArray(entity);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static String getHeader(HttpResponse response, String name) {
		Header[] headers = response.getHeaders(name);
		if (headers != null) {
			for (Header header : headers) {
				String value = header.getValue();
				if (value != null && !value.isEmpty()) {
					return value;
				}
			}
		}
		return null;
	}

	public static void setJsonContent(HttpEntityEnclosingRequestBase request, String json) {
		request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
	}

	public static void setXmlContent(HttpEntityEnclosingRequestBase request, String json) {
		request.setEntity(new StringEntity(json, ContentType.TEXT_XML));
	}

}
