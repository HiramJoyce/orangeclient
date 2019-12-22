package org.orange.client.orangeclient.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiram 2019年12月20日 14:58
 */
@RestController("/")
public class OrangeClientController {

    @GetMapping("hello")
    public String hello() {
        return "Hello Orange!";
    }

    @PostMapping("execute")
    public String execute(String command) {
        System.out.println("--->>> " + command);
        JSONObject request = JSON.parseObject(command);
//        List<String> results = new ArrayList<>();
        JSONObject resultJson = new JSONObject(true);
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(request.getString("home")));

        JSONArray cmds = request.getJSONArray("cmds");
        List<String> cmdList;
        String line;
        for (int i = 0; i < cmds.size(); i++) {
            JSONArray jsonArray = new JSONArray();
            JSONArray cmd = (JSONArray) cmds.get(i);
            cmdList = new ArrayList<>();
            for (Object aCmd : cmd) {
                cmdList.add((String) aCmd);
            }
            pb.command(cmdList);
            Process start = null;
            try {
                start = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (start != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(start.getInputStream()));
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
//                        results.add(line);
                        jsonArray.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resultJson.put(i + "-" + cmdList.toString(), jsonArray);
        }
        return resultJson.toJSONString();
    }

    public static void main(String[] args) throws IOException {


        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File("/Users/hiram"));
        pb.command("pwd");
        Process start = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<>();
        list.add("cat");
        list.add("package-lock.json");
        pb.command(list);
        start = pb.start();
        reader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Process exec = null;
//        try {
//            exec = Runtime.getRuntime().exec(new String[]{"cd", "src"});
//            exec.waitFor();
//            System.out.println(exec.exitValue());
//        } catch (InterruptedException | IOException e) {
//            System.out.println("Failure:" + e.getMessage());
//        }
//        BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
