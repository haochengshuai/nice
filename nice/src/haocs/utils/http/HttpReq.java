package haocs.utils.http;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *模拟 POST请求
 * 
 * @author haocs
 */
public class HttpReq implements Runnable {
	
	private String requestName = "";
	
	private static final int countNum = 1000;

	private static CloseableHttpResponse response = null;

	private static CloseableHttpClient httpClient = null;

	private static HttpClientContext context = null;
	
	private static CookieStore cookieStore = null;
	
	private static RequestConfig requestConfig = null;
	
	private static HashMap<String, JSONObject> postParaMap = null;
	
	private static Random random = new Random(new Date().getTime());
	
	static {
		initPostParam();
		initContext();
	}
	
	public HttpReq(String requestName) {
		this.requestName = requestName;
	}
	
	private static void initPostParam() {
		postParaMap = new HashMap<String, JSONObject>();
		JSONObject jsonParam = new JSONObject();
		jsonParam = JSON.parseObject("");
		postParaMap.put("http://127.0.0.1/manager/product/order", jsonParam);
	}
	
	private static void initContext() {
		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）  
		requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000).setConnectionRequestTimeout(60000).build();
		
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create()  
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())  
                .setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(requestConfig)  
                .setDefaultCookieStore(cookieStore).build();  
		
	}
	
	private void login() throws ClientProtocolException, IOException {
		String urlKey = "http://127.0.0.1/login";
		HttpPost httpPost = new HttpPost(urlKey);
		StringEntity entity = new StringEntity(postParaMap.get(urlKey).toString(), "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Referer", "http://127.0.0.1/index.html");
		response = httpClient.execute(httpPost, context);
		cookieStore = context.getCookieStore();
		List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {  
        	System.out.println("key:" + cookie.getName() + "  value:" + cookie.getValue());
        } 
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity != null) {
			System.out.println("------------------" + requestName + "--------------------");
			System.out.println("Response content: "
					+ EntityUtils.toString(httpEntity, "UTF-8"));
			System.out.println("--------------------------------------");
		}
		
	}

	@Override
	public void run() {
		try {
			this.login();
			//访问数
			for(int i = 0; i < countNum; i++) {
				//随机拿请求
				String key = (String)postParaMap.keySet().toArray()[random.nextInt(2)];
				HttpPost post = new HttpPost(key);
				JSONObject jsonParam = new JSONObject();
				StringEntity entity = new StringEntity(postParaMap.get(key).toJSONString(), "");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				post.setEntity(entity);
				System.out.println("executing request " + post.getURI());
				System.out.println(jsonParam.toString());
				post.setHeader("Referer", "http://127.0.0.1/pages/index.html");
				response = httpClient.execute(post, context);
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					System.out.println("------------------" + requestName + "--------------------");
					System.out.println("Response content: "
							+ EntityUtils.toString(httpEntity, "UTF-8"));
					System.out.println("--------------------------------------");
				}
//				if((i % 100) == 0) {
//					Thread.sleep(10000);
//				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
		} finally {
			closeResponse();
		}
	}

	private void closeResponse() {
		try {
			if (response != null) {
				response.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
 
	public static void main(String[] args) {
		for(int i = 0; i < 200; i++) {
			HttpReq request = new HttpReq("request" + i);
			new Thread(request).start();
		}
	}
}
