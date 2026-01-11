package com.example.securestoragelabjava.files;


import android.content.Context;

import com.example.securestoragelabjava.model.Student;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class StudentsJsonStore {

    private static final String FILE_NAME = "students.json";

    private StudentsJsonStore() {}

    // Sauvegarde la liste des étudiants dans students.json
    public static void save(Context context, List<Student> students) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Student s : students) {
            JSONObject obj = new JSONObject();
            obj.put("id", s.id);
            obj.put("name", s.name);
            obj.put("age", s.age);
            jsonArray.put(obj);
        }

        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(jsonArray.toString().getBytes("UTF-8"));
        }
    }

    // Charge la liste des étudiants depuis students.json
    public static List<Student> load(Context context) {
        List<Student> students = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String jsonStr = new String(bytes, "UTF-8");

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                students.add(new Student(
                        obj.getInt("id"),
                        obj.getString("name"),
                        obj.getInt("age")
                ));
            }
        } catch (Exception e) {
            // fichier absent ou erreur → retourne liste vide
        }
        return students;
    }

    // Supprime le fichier students.json
    public static boolean delete(Context context) {
        return context.deleteFile(FILE_NAME);
    }
}