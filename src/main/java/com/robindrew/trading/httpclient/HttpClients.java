package com.robindrew.trading.httpclient;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.robindrew.common.text.LineBuilder;
import com.robindrew.common.util.Java;

public class HttpClients {

	private static final Logger log = LoggerFactory.getLogger(HttpClients.class);

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

	public static String toString(HttpUriRequest request) {

		// HTTP Request
		LineBuilder lines = new LineBuilder();
		lines.appendLine(request.getRequestLine());
		for (Header header : request.getAllHeaders()) {
			lines.append(header.getName()).append(": ").append(header.getValue()).appendLine();
		}
		if (request instanceof HttpEntityEnclosingRequestBase) {
			HttpEntityEnclosingRequestBase base = (HttpEntityEnclosingRequestBase) request;
			lines.appendLine();
			lines.append(HttpClients.getTextContent(base.getEntity()));
		}
		return lines.toString();
	}

	public static String toString(HttpResponse response, String textContent) {

		// HTTP Response
		LineBuilder lines = new LineBuilder();
		lines.appendLine(response.getStatusLine());
		for (Header header : response.getAllHeaders()) {
			lines.append(header.getName()).append(": ").append(header.getValue()).appendLine();
		}
		lines.appendLine();
		if (textContent != null) {
			lines.append(textContent);
		}
		return lines.toString();
	}

	public static String toString(HttpResponse response, boolean includeContent) {

		// HTTP Response
		LineBuilder lines = new LineBuilder();
		lines.appendLine(response.getStatusLine());
		for (Header header : response.getAllHeaders()) {
			lines.append(header.getName()).append(": ").append(header.getValue()).appendLine();
		}
		lines.appendLine();
		if (includeContent) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					String textContent = EntityUtils.toString(entity);
					lines.append(textContent);
				} catch (Exception e) {
					log.warn("Exception parsing HTTP response content", e);
				}
			}
		}
		return lines.toString();
	}

}
