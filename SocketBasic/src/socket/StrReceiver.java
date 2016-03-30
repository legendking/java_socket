package socket;

import java.io.File;

import basic.tools.Speeder;

public class StrReceiver extends BaseConnection {

	//private static Speeder speeder = new Speeder(6000);

	@Override
	protected void transaction() throws Exception {
		int cnt = 0;
		for (;;) {
			String s = readString();
			Object f = readObject();
			File img = new File("D:\\test.jpg");
			//speeder.trigger();
			String ip = socket.getInetAddress().toString().replace("/", "");
			if ((cnt++) % 100 == 0) {
				System.out.println(ip + " : " + s);
						//+ speeder.getSpeed());
			}
			//sendInt(1);
			if (s.length() == 0)
				break;
		}
	}
}
