package org.orange.manager.service;

import org.orange.manager.domain.Result;
import org.orange.manager.repository.HostRepository;
import org.orange.manager.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class NodeChecker implements Runnable {

	public static boolean enable = true;

	@Autowired
	private HostRepository hostRepository;

	@Override
	public void run() {

		while (enable) {
			try {
				hostRepository.findAll().parallelStream().forEach(host -> {
					System.out.println("NodeChecker " + host);
					System.out.println("NodeChecker " + host.getIp());
					Result<?> heartbeatRes = NodeManager.heartbeat(host.getIp(),
							JSON.toJSONString(MessageUtil.heartbeat()));
					if (heartbeatRes.isSuccess()) {
						System.out.println(host.getIp()+" UP   :" + heartbeatRes);
						host.setStatus("UP");
					} else {
						System.out.println(host.getIp()+" DOWN : " + heartbeatRes);
						host.setStatus("DOWN");
					}
					hostRepository.save(host);
				});
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
