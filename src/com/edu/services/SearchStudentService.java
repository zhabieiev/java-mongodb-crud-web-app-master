package com.edu.services;

import java.io.IOException;
import java.util.ArrayList;

import com.edu.dao.SearchStudentDao;
import com.edu.model.Student;
import com.google.gson.*;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class SearchStudentService {

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

	public String getStudentData(Integer rollno) {
		SearchStudentDao dao = new SearchStudentDao();
		ArrayList<Student> student = dao.searchStudentData(rollno);
		String jsondata = makejsonfrombean(student);
		
		return jsondata;
	}

	private String makejsonfrombean(ArrayList<Student> student) {
		//Gson gson = new Gson();

		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(UNRELIABLE_INTEGER_FACTORY)
				.create();


		return gson.toJson(student);
	}

}
