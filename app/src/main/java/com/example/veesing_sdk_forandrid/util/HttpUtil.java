package com.example.veesing_sdk_forandrid.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Created by 小米 on 2018/9/21.
 */
public class HttpUtil {
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url  发送请求的 URL
     *
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *
     * @return 所代表远程资源的响应结果
     */

    public static String sendPost(String url, HashMap<String, String>  hashUrl ) {
        //拼接hashmap参数字符串
        String hashString = "";
        Iterator iter = hashUrl.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();
            try {
                hashString += key + "=" + URLEncoder.encode(val, "UTF-8");
            } catch (Exception e) {
                // TODO: handle exception
            }
            if(iter.hasNext())
                hashString += "&";
        }

        //输入请求网络日志
        System.out.println("post_url="+url);
        System.out.println("post_param="+hashString);

        BufferedReader in = null;
        String result = "";
        HttpURLConnection conn = null;

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(10000);//设置连接超时
            conn.setReadTimeout(10000);//设置读取超时
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //上传文件 content_type
//      conn.setRequestProperty("Content-Type", "multipart/form-data; boudary= 89alskd&&&ajslkjdflkjalskjdlfja;lksdf");
            conn.connect();
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            osw.write(hashString);
            osw.flush();
            osw.close();
            if (conn.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                System.out.println("post_result="+result);
                in.close();
            }
        } catch (SocketTimeoutException e ) {
            //连接超时、读取超时
            e.printStackTrace();
            return "POST_Exception";
        }catch (ProtocolException e){
            e.printStackTrace();
            return "POST_Exception";
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送 POST 请求出现异常！"+e.getMessage()+"//URL="+url);
            e.printStackTrace();
            return "POST_Exception";
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{

                if (conn != null) conn.disconnect();

                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
