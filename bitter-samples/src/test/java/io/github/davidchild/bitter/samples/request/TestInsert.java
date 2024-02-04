package io.github.davidchild.bitter.samples.request;

import io.github.davidchild.bitter.samples.business.entity.TStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TestInsert extends TsRequest {

    private TsRequest request;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;

    public TestInsert() {
        InitTsRequest("http://localhost:8097/", "", "");
    }

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        System.out.println("init mock module");
    }

    @Test
    void hello() throws Exception {

        String responseString = mvc.perform(MockMvcRequestBuilders.get("/business/t-dept/hello").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串;
        System.out.println("获取结果为：" + responseString);
    }


    @Test
    void testInsert() throws Exception {
        TStudent student = new TStudent();
        student.setName("david-child");
        String responseString = mvc.perform(post("/business/t-student/insert/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        writeResult(responseString);

    }

}
