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
package org.springframework.samples.websocket.echo.sockjs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.websocket.echo.EchoService;
import org.springframework.sockjs.SockJsHandlerAdapter;
import org.springframework.sockjs.SockJsSession;

public class EchoSockJsHandler extends SockJsHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(EchoSockJsHandler.class);

	private final EchoService echoService;

	@Autowired
	public EchoSockJsHandler(EchoService echoService) {
		this.echoService = echoService;
	}

	@Override
	public void newSession(SockJsSession session) throws Exception {
		logger.debug("Opened new session in instance " + this);
	}

	@Override
	public void handleMessage(SockJsSession session, String message) throws Exception {
		logger.debug("Echoing message: " + message);
		session.sendMessage(this.echoService.getMessage(message));
	}

}
