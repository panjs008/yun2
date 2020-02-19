package com.emk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 * @Title: http://www.smschinese.cn/api.shtml
 * @date 2011-3-22
 * @version V1.2  
 */
public class test {
	
	//用户名
	private static String Uid = "panjs008";
	
	//接口安全秘钥
	private static String Key = "d41d8cd98f00b204e980";
	
	//手机号码，多个号码如13800000000,13800000001,13800000002
	private static String smsMob = "18649803708";
	
	//短信内容
	private static String smsText = "【玛氏MyP&O】尊敬的潘森森您好，您下属的劳动合同即将到期，请查收MyP&O TC-Contract Renewal发出的邮件进行确认，谢谢！";
	
	
	public static void main(String[] args) {
		
		/*SmsClientUtil client = SmsClientUtil.getInstance();
		smsText = URLEncoder.encode(smsText);
		//UTF发送
		int result = client.sendMsgUtf8(Uid, Key, smsText, smsMob);
		if(result>0){
			System.out.println("UTF8成功发送条数=="+result);
		}else{
			System.out.println(client.getErrorMsg(result));
		}*/
		/*String ip = "";
		String chinaz = "http://ip.chinaz.com";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while((read=in.readLine())!=null){
				inputLine.append(read+"\r\n");
			}
//System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String ipText = inputLine.toString();
		System.out.println(ipText);
		Pattern p = Pattern.compile("<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(ipText);

		if(m.find()){
			String ipstr = m.group(1);
			ip = ipstr;
			String from = ipText.substring(ipText.indexOf("省")-2,ipText.indexOf("市"));
			ip += "（"+from+"）";
		}*/
		WebFileUtils.delete("D:\\IdeaWorkspace\\yun\\out\\artifacts\\emk_war_exploded\\emk/imp/product/1577255955926.jpg");
	}
}
