package io.github.davidchild.bitter.samples;

import io.github.davidchild.bitter.samples.business.entity.TDept;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TestBatchInsert extends TsRequest {

    private TsRequest request;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;

    public TestBatchInsert() {
        InitTsRequest("http://localhost:8097/", "", "");
    }

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        System.out.println("init mock module");
    }
    
    @Test
    void testInsert() throws Exception {
        TDept dept = new TDept();
        dept.setDeptName("测试部分");
        dept.setCreateBy("davidChild");
        dept.setCreateTime(new Date());
        String responseString = mvc.perform(post("/business/t-dept/save-dept/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dept))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        writeResult(responseString);

    }

}
