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
	private static String[] ServerList = {"2","3","4"};
	private static int[] bin = {0,0,0};
    public static void main(String[] args) throws Exception {  
        for(int i=1;i<=100;i++)startWork(); 
        for(int i=0;i<bin.length;i++)System.out.println("Server "+ ServerList[i] + ": " + bin[i]);
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
        String teststr = "test";  
        OutputStream out = urlConn.getOutputStream();  
        out.write(teststr.getBytes());  
        out.flush();  
        
        
        if(urlConn.getContentLength() != -1) {  
            if (urlConn.getResponseCode() == 200) {  
                InputStream in = urlConn.getInputStream();  
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
                String temp = "";  
                while ((temp = reader.readLine()) != null) {  
                    //System.out.println("server response:" + temp);
                    
                    //for test
                    for(int i=0;i<ServerList.length;i++) {
                    	if(temp.contains(ServerList[i])) {
                    		bin[i]++;
                    		break;
                    	}
                    }
                }
                reader.close();  
                in.close();  
                urlConn.disconnect();  
            }  
        }  
    }  
}  
