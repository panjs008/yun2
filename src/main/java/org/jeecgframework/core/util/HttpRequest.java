package org.jeecgframework.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author Link Xue 
 * @version 2017年8月20日 上午11:03:13
 * 模拟HTTP请求获取返回值
 */
public class HttpRequest {

	private static Logger logger = Logger.getLogger(HttpRequest.class);
	
    /**
     * post请求
     * @param url         url地址
     * @param param     请求参数
     * @param noNeedResponse    不需要返回结果
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String httpPost(String url,String param, boolean noNeedResponse) throws ClientProtocolException, IOException{    	
    	
        url = URLDecoder.decode(url, "UTF-8");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        HttpPost method = new HttpPost(url);        

        /*RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(300).build();//设置请求和传输超时时间
        method.setConfig(requestConfig);*/
        
        if (null != param) {             
            StringEntity entity = new StringEntity(param, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/x-www-form-urlencoded");
            method.setEntity(entity);
        }

        try {
            HttpResponse resultRep = httpClient.execute(method);
            /**请求发送成功，并得到响应**/
            if (resultRep.getStatusLine().getStatusCode() == 200) {
                String str = "";
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(resultRep.getEntity());
                if (noNeedResponse) {
                    return null;
                }
                /**把json字符串转换成json对象**/
                //jsonResult = JSONObject.parseObject(str);
                result = str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据key读取value
     *
     * @param filePath
     * @param keyWord
     * @return String
     * @throws
     * @Title: getProperties_1
     * @Description: 第一种方式：根据文件名使用spring中的工具类进行解析
     * filePath是相对路劲，文件需在classpath目录下
     * 比如：config.properties在包com.test.config下，
     * 路径就是com/test/config/config.properties
     */
    public static String getProperties_1(String filePath, String keyWord) {
        Properties prop = null;
        String value = null;
        try {
            // 通过Spring中的PropertiesLoaderUtils工具类进行获取
            prop = PropertiesLoaderUtils.loadAllProperties(filePath);
            // 根据关键字查询相应的值
            value = prop.getProperty(keyWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    public static String sendMail(String title, String content, String emailAccount) {
        Properties prop = null;
        String value = null;
        try {
            String emailUrl = getProperties_1("sysConfig.properties","emailUrl");
            emailUrl = emailUrl+"&title="+title+"&content="+content+"&emailAccount="+emailAccount;
            String  res = null;
            try {
                res = httpPost(emailUrl,null,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void main(String[] args) {
        String emailUrl = getProperties_1("sysConfig.properties","emailUrl");
        emailUrl = emailUrl+"&title=采购需求单&content=采购需求单12345&emailAccount=245613376@qq.com";
        System.out.println(emailUrl);
        String  res = null;
        try {
            res = httpPost(emailUrl,null,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
}
