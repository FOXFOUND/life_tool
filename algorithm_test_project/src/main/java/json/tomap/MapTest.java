package json.tomap;
import com.alibaba.fastjson.JSON;
import json.tomap.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Student student = new Student();
        student.setStudentNo("123");
        Person person = new Person();
        person.setName("123name");
        person.setAge("123age");
        person.setStudent(student);
        Map<String, Person> map = new HashMap<>();
        map.put("myperson", person);
        System.out.println(JSON.toJSONString(map));

        List list = new ArrayList<>();
        System.out.println(list.toString());
    }


}
