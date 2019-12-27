package org.orange.manager.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.orange.manager.domain.Result;
import org.orange.manager.entity.Group;
import org.orange.manager.entity.Host;
import org.orange.manager.service.GroupService;
import org.orange.manager.service.HostService;
import org.orange.manager.service.NodeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

/**
 * @author hiram 2019年12月27日 16:16
 */
@ServerEndpoint(value = "/orange")
@Component
public class OrangeWebsocket {
    private final Logger logger = LoggerFactory.getLogger(OrangeWebsocket.class);

    private static ApplicationContext applicationContext;
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("--->>> WebSocket connected! -> sessionId: " + session.getId());
    }

    @OnClose
    public void onClose() {
        logger.info("--->>> WebSocket closed!");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("--->>> WebSocket error! -> sessionId: " + session.getId() + " err: " + error.getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info(session.getId() + " -> " + message);
        JSONObject reqMsgJson = JSONObject.parseObject(message);
        int reqType = reqMsgJson.getIntValue("reqType");
        JSONObject respMsgJson = new JSONObject();
        respMsgJson.put("respType", reqType);
        switch (reqType) {
            case 10001: // 请求hosts
                handle10001(respMsgJson, session);
                break;
            case 10002: // 请求groups
                handle10002(respMsgJson, session);
                break;
            case 10003: // 执行命令
                handle10003(respMsgJson, reqMsgJson, session);
                break;
            default:
                logger.info("default");
                break;
        }
    }

    private void sendMsg(String msg, Session session) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理请求hosts
     * @param respMsgJson 响应json
     * @param session 会话session
     */
    private void handle10001(JSONObject respMsgJson, Session session) {
        HostService hostService = applicationContext.getBean(HostService.class);
        List<Host> hosts = hostService.queryHosts();
        respMsgJson.put("data", hosts);
        logger.info(respMsgJson.toJSONString());
        sendMsg(respMsgJson.toJSONString(), session);
    }

    /**
     * 处理请求groups
     * @param respMsgJson 响应json
     * @param session 会话session
     */
    private void handle10002(JSONObject respMsgJson, Session session) {
        GroupService groupService = applicationContext.getBean(GroupService.class);
        List<Group> groups = groupService.queryGroups();
        respMsgJson.put("data", groups);
        logger.info(respMsgJson.toJSONString());
        sendMsg(respMsgJson.toJSONString(), session);
    }

    /**
     * 处理执行命令请求
     * @param respMsgJson 响应json
     * @param reqMsgJson 请求json
     * @param session 会话session
     */
    private void handle10003(JSONObject respMsgJson, JSONObject reqMsgJson, Session session) {
        JSONObject target = reqMsgJson.getJSONObject("target");
        // 处理hosts
        JSONArray hosts10003 = target.getJSONArray("hosts");
        if (!CollectionUtils.isEmpty(hosts10003)) {
            JSONArray hostsArr = new JSONArray();
            hosts10003.parallelStream().forEach(host10003 -> {
                long start = System.currentTimeMillis();
                Result<?> res = NodeManager.send(((JSONObject)host10003).getString("ip"), reqMsgJson.getString("commands"));
                ((JSONObject) host10003).put("ms", System.currentTimeMillis() - start);
                ((JSONObject) host10003).put("result", res.getData());
                hostsArr.add(host10003);
            });
            respMsgJson.put("hosts", hostsArr);
        }
        // 处理groups
        JSONArray groups10003 = target.getJSONArray("groups");
        if (!CollectionUtils.isEmpty(groups10003)) {
            JSONArray groupsArr = new JSONArray();
            groups10003.parallelStream().forEach(group10003 -> {
                long start = System.currentTimeMillis();
                JSONArray groupHostsArr = ((JSONObject) group10003).getJSONArray("hosts");
                groupHostsArr.parallelStream().forEach(host10003 -> {
                    long startIn = System.currentTimeMillis();
                    Result<?> res = NodeManager.send(((JSONObject)host10003).getString("ip"), reqMsgJson.getString("commands"));
                    ((JSONObject) host10003).put("ms", System.currentTimeMillis() - startIn);
                    ((JSONObject) host10003).put("result", res.getData());
                });
                ((JSONObject) group10003).put("ms", System.currentTimeMillis() - start);
                groupsArr.add(group10003);
            });
            respMsgJson.put("groups", groupsArr);
        }
        logger.info(respMsgJson.toJSONString());
        sendMsg(respMsgJson.toJSONString(), session);
    }
}
