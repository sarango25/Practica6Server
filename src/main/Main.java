package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import processing.core.PApplet;

public class Main extends PApplet {

	// Globales
	int xBolita = 250;
	int yBolita = 250;
	int r = 255;
	int g = 0;
	int b = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Main");

	}

	public void settings() {
		size(500, 500);

	}

	public void setup() {
		
		new Thread(() -> {
			try {
				ServerSocket server = new ServerSocket(5000);
				System.out.println("Esperando cliente...");
				Socket socket = server.accept();
				System.out.println("Cliente esta conectado");

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);

				while (true) {
					
					System.out.println("Esperando mensaje...");
					String mensajeRecibido = breader.readLine(); //BW::X::Y::ALTO::ANCHO
					
					System.out.println(mensajeRecibido);
					
					Gson gson = new Gson();
					
					
					Coordenada obj = gson.fromJson(mensajeRecibido, Coordenada.class);
					
					xBolita = obj.getX();
					yBolita = obj.getY();
					r = obj.getR();
					g = obj.getG();
					b = obj.getB();
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	

	}

	public void draw() {
		background(0, 0, 0);
		fill(r, g, b);
		ellipse(xBolita, yBolita, 50, 50);

	}

}
