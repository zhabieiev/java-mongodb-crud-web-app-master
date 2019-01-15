package com.edu.services;

import com.edu.dao.StudentDao;
import com.edu.model.Student;
//import com.ecorise.model.Student;
import com.google.gson.*;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StudentService {

	public static final TypeAdapter<Number> UNRELIABLE_INTEGER = new TypeAdapter<Number>() {
		@Override
		public Number read(JsonReader in) throws IOException {
			JsonToken jsonToken = in.peek();
			switch (jsonToken) {
				case NUMBER:
				case STRING:
					String s = in.nextString();
					try {
						return Integer.parseInt(s);
					} catch (NumberFormatException ignored) {
					}
					try {
						return (int)Double.parseDouble(s);
					} catch (NumberFormatException ignored) {
					}
					return null;
				case NULL:
					in.nextNull();
					return null;
				case BOOLEAN:
					in.nextBoolean();
					return null;
				default:
					throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
			}
		}
		@Override
		public void write(JsonWriter out, Number value) throws IOException {
			out.value(value);
		}
	};
	public static final TypeAdapterFactory UNRELIABLE_INTEGER_FACTORY = TypeAdapters.newFactory(int.class, Integer.class, UNRELIABLE_INTEGER);
	
 public void insertData(String studentdata) {
	 
	 	Student student =  makebeanfromjson(studentdata);
	 	StudentDao dao = new StudentDao();
	 	dao.insertData(student);
		
	}
 
 
public void updateData(String studentdata) {
	// TODO Auto-generated method stub
	Student student =  makebeanfromjson(studentdata);
 	StudentDao dao = new StudentDao();
 	dao.updateData(student);
}

private Student makebeanfromjson(String studentdata) {

	//Gson gson = new Gson();
	Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(UNRELIABLE_INTEGER_FACTORY)
			.create();


	return gson.fromJson(studentdata, Student.class);
}

}
