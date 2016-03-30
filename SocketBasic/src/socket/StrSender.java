package socket;

import java.io.File;



public class StrSender extends BaseConnection {

	@Override
	protected void transaction() throws Exception {
		for (;;) {
//			sendString("this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;this is just a test;");
			sendString("pic test");
            File f = new File("D:\\shana.jpg");
			sendObject(f);

//			int res = readInt();
//			if (res != 1) {
//				System.out.println("Response Failed");
//				throw new Exception();
//			}
		}
	}
}
