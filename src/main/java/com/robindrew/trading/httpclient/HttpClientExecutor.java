package com.robindrew.trading.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public abstract class HttpClientExecutor<R> implements IHttpClientExecutor<R> {

	private static final Logger log = LoggerFactory.getLogger(HttpClientExecutor.class);

	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public R execute() {
		log.info("[Executing] " + getName());
		Stopwatch timer = Stopwatch.createStarted();

		int attempts = 0;
		while (true) {
			try {

				// Create a client
				HttpClient client = HttpClientBuilder.create().build();

				// Create the request
				HttpUriRequest request = createRequest();

				// Execute the request
				HttpResponse response = execute(client, request);

				// Handle the response
				R returnValue = handleResponse(request, response);
				timer.stop();
				log.info("[Executed] " + getName() + " in " + timer);

				// Done!
				return returnValue;

			} catch (HttpRetryException hre) {
				if (attempts < getRetryAttempts()) {
					attempts++;
					continue;
				}

				// No more attempts?
				throw new HttpClientException("Failed to execute REST request");
			} catch (Exception e) {
				throw new HttpClientException("Failed to execute REST request", e);
			}
		}

	}

	protected HttpResponse execute(HttpClient client, HttpUriRequest request) throws Exception {
		return client.execute(request);
	}

	protected boolean failed(HttpUriRequest request, HttpResponse response) {
		return response.getStatusLine().getStatusCode() != 200;
	}

	protected int getRetryAttempts() {
		return 0;
	}

	protected abstract HttpUriRequest createRequest() throws Exception;

	protected abstract R handleResponse(HttpUriRequest request, HttpResponse response) throws Exception;

}
