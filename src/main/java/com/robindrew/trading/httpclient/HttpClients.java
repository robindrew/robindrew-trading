package com.robindrew.trading.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.robindrew.common.util.Java;

public class HttpClients {

	public static String getTextContent(HttpEntity entity) {
		return getTextContent(entity, Charsets.UTF_8);
	}

	public static String getTextContent(HttpEntity entity, Charset charset) {
		try {
			InputStream stream = entity.getContent();
			if (stream == null) {
				return "";
			}
			return CharStreams.toString(new InputStreamReader(stream, charset));
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static byte[] getBinaryContent(HttpEntity entity, Charset charset) {
		try {
			InputStream stream = entity.getContent();
			if (stream == null) {
				return new byte[0];
			}
			return ByteStreams.toByteArray(stream);
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

}
