/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.websocket.client.GreetingService;
import org.springframework.samples.websocket.client.SimpleClientEndpoint;
import org.springframework.samples.websocket.client.SimpleGreetingService;
import org.springframework.samples.websocket.echo.DefaultEchoService;
import org.springframework.samples.websocket.echo.EchoService;
import org.springframework.samples.websocket.echo.endpoint.EchoEndpoint;
import org.springframework.websocket.client.EndpointConnectionManager;
import org.springframework.websocket.server.endpoint.EndpointRegistration;
import org.springframework.websocket.server.endpoint.ServletEndpointExporter;

@Configuration
public class RootConfig {

	// -------------------------------------------------------------------------------------
	// EndpointExporter:
	//   Detect/export javax.websocket.server.ServerEndpointConfig beans(type-based API)
	//   Detect/export @ServerEndpoint beans

	@Bean
	public ServletEndpointExporter endpointExporter() {
		ServletEndpointExporter exporter = new ServletEndpointExporter();
		// Uncomment when container scan is disabled (see <absolute-ordering> in web.xml)
		// exporter.setAnnotatedEndpointClasses(EchoAnnotatedEndpoint.class, ChatAnnotatedEndpoint.class);
		return exporter;
	}

	// Spring initialized javax.websocket.Endpoint with instance per connection scope semantics

	@Bean
	public EndpointRegistration echoEndpoint() {
		return new EndpointRegistration("/echoEndpoint", EchoEndpoint.class);
	}

	// javax.websocket.Endpoint singleton serves all incoming connections

	@Bean
	public EndpointRegistration echoEndpointSingleton() {
		return new EndpointRegistration("/echoEndpointSingleton", new EchoEndpoint(echoService()));
	}

	@Bean
	public EchoService echoService() {
		return new DefaultEchoService("Did you say \"%s\"?");
	}

	// --------------------------------------------------------------------------
	// Client EndpointConnectionManager connecting to the server echo endpoint

	@Bean
	public EndpointConnectionManager echoEndpointConnectionManager() {
		String uri = "ws://localhost:8080/spring-websocket-test/echoEndpoint";
		EndpointConnectionManager connectionManager = new EndpointConnectionManager(SimpleClientEndpoint.class, uri);
//		connectionManager.setAutoStartup(true);
		return connectionManager;
	}

	@Bean
	public GreetingService greetingService() {
		return new SimpleGreetingService();
	}

}
