package io.github.davidchild.bitter.test.business.controller;


import io.github.davidchild.bitter.test.business.entity.TStudent;
import io.github.davidchild.bitter.test.business.service.ITStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author davidChild
 * @since 2022-12-19
 */
@RestController
@RequestMapping("/business/t-student")
public class TStudentController {
    @Autowired
    private ITStudentService studentService;

    @PostMapping("/insert")
    public Long insert(@RequestBody TStudent student) {
        return studentService.insert(student);
    }

    @GetMapping("/single-query")
    public TStudent singleQuery(@RequestParam String name) {
        return studentService.singleQuery(name);
    }

    @GetMapping("/query-by-id")
    public TStudent singleQuery(@RequestParam Long id) {
        return studentService.queryById(id);
    }


    @GetMapping("/query-list")
    public List<TStudent> queryList(@RequestParam String name) {
        return studentService.queryList(name);
    }

    @GetMapping("/delete")
    public Long delete(@RequestParam Long id) {
        return studentService.deleteById(id);
    }

}
