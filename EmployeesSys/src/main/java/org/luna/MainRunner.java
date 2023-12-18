package org.luna;

import org.luna.bean.EmployeeBean;
import org.luna.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class MainRunner {

    @Autowired
    private EmployeeService employeeService;

    public static void main (String[] args) {
        SpringApplication.run(MainRunner.class, args);
    }

    @GetMapping("/")
    @ResponseBody
    List<EmployeeBean> getEmployee(@RequestParam Map params) {
//        return params.get("id") == null ? employeeService.list() : (List<EmployeeBean>) employeeService.listByMap(new HashMap<String, Object>() {{
//            put("id", (String) params.get("id"));
//        }});
        if (params.get("id") != null) {
           return (List<EmployeeBean>) employeeService.listByMap(new HashMap<String, Object>() {{
                put("id", (String) params.get("id"));
            }});
        }
        if (params.get("username") != null) {
            if (params.get("password") != null) {
                return (List<EmployeeBean>) employeeService.listByMap(new HashMap<String, Object>() {{
                    put("username", (String) params.get("username"));
                    put("password", (String) params.get("password"));
                }});
            }
            return (List<EmployeeBean>) employeeService.listByMap(new HashMap<String, Object>() {{
                put("username", (String) params.get("username"));
            }});
        }
        return employeeService.list();
    }

    @PostMapping("/")
    @ResponseBody
    Map saveEmployee(@RequestBody Map body) throws Exception {
        if (
                body.get("username") == null
                || body.get("password") == null
                || body.get("name") == null
                || (body.get("gender") != null && !((String) body.get("gender")).equals("1") && !((String) body.get("gender")).equals("0"))
        ) {
            // throw new Exception("upload message field is not format");
            return new HashMap<String, Object>() {{ put("ok", false); put("error", "upload message field is not format"); }};
        }

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        EmployeeBean bean = new EmployeeBean();
        bean.setUsername((String) body.get("username"));
        bean.setName((String) body.get("name"));
        bean.setPassword((String) body.get("password"));
        bean.setBirthday(ft.parse(body.get("birthday") == null ? "2001-01-01" : (String) body.get("birthday")));
        bean.setAge(
                new Date().getYear() -
                ft.parse(body.get("birthday") == null ? "2001-01-01" : (String) body.get("birthday")).getYear()
        );
        bean.setGender(Integer.parseInt(body.get("gender") == null ? "1" : (String) body.get("gender")));
        bean.setPhone(body.get("phone") == null ? "" : (String) body.get("phone"));
        try {
            employeeService.save(bean);
        } catch (Exception e) {
            System.out.println(e);
            return new HashMap<String, Object>() {{ put("ok", false); put("error", "inner error"); }};
        }
        return new HashMap<String, Object>() {{ put("ok", true); }};
    }

    @DeleteMapping("/")
    @ResponseBody
    Map deleteEmployee(@RequestParam("id") Integer id) {
        try {
            employeeService.removeById(id);
        } catch (Exception e) {
            System.out.println(e);
            return new HashMap<String, Object>() {{ put("ok", false); put("error", "inner error"); }};
        }
        return new HashMap<String, Object>() {{ put("ok", true); }};
    }

    @PutMapping("/")
    @ResponseBody
    Map updateEmployee(@RequestParam("id") Long id, @RequestBody Map body) throws ParseException {
        EmployeeBean bean = new EmployeeBean();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        bean.setId(id);
        if (body.get("username") != null) bean.setUsername((String) body.get("username"));
        if (body.get("password") != null) bean.setPassword((String) body.get("password"));
        if (body.get("name") != null) bean.setName((String) body.get("name"));
        if (body.get("birthday") != null) bean.setBirthday(ft.parse((String) body.get("birthday")));
        if (body.get("age") != null) bean.setAge(
                new Date().getYear() -
                ft.parse(body.get("birthday") == null ? "2001-01-01" : (String) body.get("birthday")).getYear()
        );
        if (body.get("phone") != null) bean.setUsername((String) body.get("phone"));
        if (body.get("gender") != null) bean.setGender(Integer.parseInt(body.get("gender") == null ? "1" : (String) body.get("gender")));
        try {
            employeeService.updateById(bean);
        } catch (Exception e) {
            System.out.println(e);
            return new HashMap<String, Object>() {{ put("ok", false); put("error", "inner error"); }};
        }
        return new HashMap<String, Object>() {{ put("ok", true); }};
    }
}
