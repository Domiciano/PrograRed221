package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.*;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(6000);
		while(true) {
			System.out.println("Esperando...");
			Socket socket = server.accept();
			System.out.println("Conectado");
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			Gson gson = new Gson();
			//1. Student
			Student t = new Student();
			t.setCode("A172832");
			t.setName("Esteban Mendoza");
			
			String json = gson.toJson(t);
			writer.write(json+"\n");
			writer.flush();
			
			//2. Course
			Student a = new Student();
			a.setCode("A172832");
			a.setName("Esteban Mendoza");
			
			Student b = new Student();
			b.setCode("A172832");
			b.setName("Jhorman Mera");
			
			Teacher c = new Teacher();
			c.setCode("A48545");
			c.setName("Andres Navarro");
			
			Course d = new Course();
			d.setCredits(3);
			d.setNRC("A3487534");
			d.setName("Señales y sistemas");
			
			ArrayList<Student> students = new ArrayList<>();
			students.add(a);
			students.add(b);
			ArrayList<Teacher> teachers = new ArrayList<>();
			teachers.add(c);
			
			d.setTeachers(teachers);
			d.setStudents(students);
			
			String jsonCourse = gson.toJson(d);
			writer.write(jsonCourse+"\n");
			writer.flush();
			
			
			//3. Arreglo de estudiantes
			String jsonArray = gson.toJson(students);
			writer.write(jsonArray+"\n");
			writer.flush();
			
			
		}
		

	}

}
