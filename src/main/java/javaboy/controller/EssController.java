package javaboy.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class EssController {
    static final MediaType UTF8_TEXT_EVENTSTREAM = new MediaType("text", "event-stream", StandardCharsets.UTF_8);
    @RequestMapping(value = "/es",produces={MediaType.TEXT_EVENT_STREAM_VALUE})
    public @ResponseBody void esPost(String deviceNO, Integer index, HttpServletResponse response){
         try {
             response.setContentType("text/event-stream");//如果用流返回，这必须得写上面的MediaType没效了
             response.setCharacterEncoding("utf-8");

             System.out.println(deviceNO + index);
             PrintWriter out = response.getWriter();
             for (int i = 0;i < 10;i++){
                 if(i > 4){
                     out.write("event:don\n");
                 }else{
                     out.write("event:message\n");
                 }
                 out.write("id:" + i + "\n");
                 out.write("retry:" + 1000 + "\n");
                 out.write("data: 江南一点雨:" + i + deviceNO + index + "\n\n");
                 out.flush();
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }catch (Exception e){
             e.printStackTrace();
         }
    }


    @RequestMapping(value = "/push", produces = "text/event-stream;charset=UTF-8")
    public @ResponseBody String push() {
        Random r = new Random();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//SSE返回数据格式是固定的以data:开头，以\n\n结束
        return "data:Testing 1,2,3" + r.nextInt() + "\n\n";
    }
}
