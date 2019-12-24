package org.orange.manager.util;

import org.orange.manager.domain.Message;
import org.orange.manager.domain.MessageEnum;

public class MessageUtil {

	public static Message command(String data) {
		return new Message(MessageEnum.COMMAND_CODE.getCode(), data);
	}

	public static Message heartbeat() {
		return new Message(MessageEnum.HEARTBEAT_CODE.getCode(), "heartbeat");
	}

}
