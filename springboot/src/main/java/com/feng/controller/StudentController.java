package com.feng.controller;

/**
 * Created by wangxl6 on 2018/8/20.
 */
import java.util.ArrayList;
import java.util.List;

import com.feng.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StudentController {

    @RequestMapping("/getStudentList")
    public String getStudentList(ModelMap map) {
        List<Student> list = new ArrayList<Student>() ;
        for(int i=0; i<=5; i++) {
            Student st = new Student() ;
            st.setId(i+1) ;
            st.setName("章三"+(i+1)) ;
            st.setAge(20+i) ;
            st.setAddress("北京故宫门牌号20"+i) ;
            list.add(st) ;
        }
        map.addAttribute("list", list);

        return "index" ;
    }
    @GetMapping("/index")
    public String getWhale(ModelMap map) {
        map.put("msg", "SpringBoot Ajax 示例");
        return "index1" ;
    }
}