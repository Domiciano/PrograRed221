package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import model.Course;
import model.Generic;
import model.Student;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket = new Socket("127.0.0.1", 6000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		while (true) {
			String json = reader.readLine();
			Gson gson = new Gson();

			if (json.startsWith("{")) {
				Generic generic = gson.fromJson(json, Generic.class);
				System.out.println(json);
				System.out.println(generic.type);

				switch (generic.type) {
				case "Student":
					Student st = gson.fromJson(json, Student.class);
					System.out.println(st.getName());
					break;
				case "Course":
					Course cs = gson.fromJson(json, Course.class);
					System.out.println(cs.getName());
					break;
				}
			} else if (json.startsWith("[")) {
				Generic[] generics = gson.fromJson(json, Generic[].class);
				String type = generics[0].type;
				switch (type) {
				case "Student":
					Student[] student = gson.fromJson(json, Student[].class);
					List<Student> alfa = Arrays.asList(student);
					System.out.println(alfa.size());
					break;
				}
			}

		}

	}
}
