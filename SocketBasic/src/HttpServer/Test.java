package HttpServer;  
  
import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.URL;  

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
  
public class Test {  
	private static String WhiteList = "139.224.6.124"; 
    public static void main(String[] args) throws Exception {  
        startWork();  
    }  
  
    public static void startWork() throws Exception {  

    	TrustManager[] tm = { new TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        SSLSocketFactory ssf = sslContext.getSocketFactory();      
        URL url = new URL("https://139.224.6.124:8081/service");  
        HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection(); 
        urlConn.setHostnameVerifier(new HostnameVerifier()
        {
			public boolean verify(String ip, SSLSession sec) {
				if(WhiteList.contains(ip))return true;
				else return false;
			}
        });
        urlConn.setSSLSocketFactory(ssf);
        urlConn.setDoOutput(true);  
        urlConn.setDoInput(true);        
        
        
        urlConn.setRequestMethod("POST");  
        String teststr = "this is a test message";  
        OutputStream out = urlConn.getOutputStream();  
        out.write(teststr.getBytes());  
        out.flush();  
        
        
        if(urlConn.getContentLength() != -1) {  
            if (urlConn.getResponseCode() == 200) {  
                InputStream in = urlConn.getInputStream();  
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
                String temp = "";  
                while ((temp = reader.readLine()) != null) {  
                    System.out.println("server response:" + temp);
                }
                reader.close();  
                in.close();  
                urlConn.disconnect();  
            }  
        }  
    }  
}  
