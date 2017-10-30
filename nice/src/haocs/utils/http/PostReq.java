package haocs.utils.http;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;
/**
 * 模拟post 请求
 * @date 2017年10月30日
 */
public class PostReq {
	
	/**
	 * 
	 * @param httpUrl
	 * @param obj  
	 * @return
	 * @throws IOException 
	 */
	public static String postMethod(String httpUrl,JSONObject obj) throws IOException{
		URL url = new URL(httpUrl);
		HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
		try {
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
            connection.setConnectTimeout(300000);
            connection.setReadTimeout(300000);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            String string = obj.toString();
            out.write(string.getBytes()); //string 中文乱码
            out.flush();
            out.close();
        	return readResponseContent(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if (null != connection)
				connection.disconnect();
		}
		return "";
	}
	
	/**
	 * 读取响应字节流并将之转为字符串
	 * 
	 * @param in
	 *            要读取的字节流
	 * @return
	 * @throws IOException
	 */
	private static String readResponseContent(InputStream in)
			throws IOException {
		Reader reader = null;
		StringBuilder content = new StringBuilder();
		try {
			reader = new InputStreamReader(in);
			char[] buffer = new char[1024];
			int head = 0;
			while ((head = reader.read(buffer)) > 0) {
				content.append(new String(buffer, 0, head));
			}
			return content.toString();
		} finally {
			if (null != in)
				in.close();
			if (null != reader)
				reader.close();
		}
	}
	public static void main(String[] args) {
		String httpUrl = "http://localhost:88/xxxxx";
		  JSONObject obj = new JSONObject();
		  obj.put("subjectId",1123);      
		  try {
			String postMethod = postMethod(httpUrl, obj);
			System.out.println(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
