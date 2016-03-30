package HttpServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;  
import java.net.InetSocketAddress;  
import java.security.KeyStore;  
import java.security.PrivateKey;

import javax.net.ssl.KeyManagerFactory;  
import javax.net.ssl.SSLContext;  

import sun.misc.BASE64Encoder;
import sun.net.www.protocol.http.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;  
import com.sun.net.httpserver.HttpHandler;  
import com.sun.net.httpserver.HttpsConfigurator;  
import com.sun.net.httpserver.HttpsServer;


public class MyHttpServer {  
	private static String pass = "minivision";
	private static void printPrivateKey(PrivateKey pk) throws IOException {
		BASE64Encoder encoder=new BASE64Encoder();    
        String encoded=encoder.encode(pk.getEncoded()); 
        FileWriter fw = new FileWriter(new File("F:/privatekey.key"));  
        fw.write("-----BEGIN RSA PRIVATE KEY-----\n");  
        fw.write(encoded);  
        fw.write("\n");  
        fw.write("-----END RSA PRIVATE KEY-----");  
        fw.close();  
	}
    private static void httpserverService() throws Exception {  
    	HttpsServer https = HttpsServer.create(new InetSocketAddress(8081), 1000);  
        https.createContext("/test", new MyHttpHandler());  
        https.setExecutor(null);  
        KeyStore ks = KeyStore.getInstance("JKS");   
        ks.load(new FileInputStream("F:/serverkey"), pass.toCharArray()); 
        PrivateKey pk = (PrivateKey) ks.getKey("testkey", pass.toCharArray());
        //printPrivateKey(pk);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");  
        kmf.init(ks, pass.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");    
        sslContext.init(kmf.getKeyManagers(), null, null); 
        HttpsConfigurator httpsConfigurator = new HttpsConfigurator(sslContext);  
        https.setHttpsConfigurator(httpsConfigurator);  
        https.start();  
        System.out.println("server started!");  
    }  

    static class MyHttpHandler implements HttpHandler {  
        public void handle(HttpExchange httpExchange) throws IOException {  
            String responseMsg = "This is response!"; 
            InputStream in = httpExchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
            String temp = null;  
            while((temp = reader.readLine()) != null) {  
                System.out.println("request:" + temp);  
            }  
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseMsg.getBytes().length);
            OutputStream out = httpExchange.getResponseBody();
            out.write(responseMsg.getBytes());  
            out.flush();  
            httpExchange.close();                                 
        }  
    }  
    public static void main(String[] args) throws Exception {  
        httpserverService();  
    }  
}  
