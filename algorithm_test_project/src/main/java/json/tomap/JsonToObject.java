package json.tomap;

import com.alibaba.fastjson.JSON;

public class JsonToObject {
    public static void main(String[] args) {
        Person p = new Person();
        p.setAge("10");
        p.setName("1");
        Student student = new Student();
        student.setStudentNo("11");
        p.setStudent(student);
        String str =JSON.toJSONString(p);
        System.out.println(str);
        Object o = JSON.parse(str);
        System.out.println(o);
    }
}
