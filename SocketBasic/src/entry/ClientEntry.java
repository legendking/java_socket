package entry;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import socket.BaseConnector;

public class ClientEntry {
	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 1; i++)
		{
			new BaseConnector("59.78.57.153", 8012, "socket.StrSender", 1000, 1).start();
		}
	}	
}
