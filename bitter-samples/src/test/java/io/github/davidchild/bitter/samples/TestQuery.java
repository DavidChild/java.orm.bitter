package io.github.davidchild.bitter.samples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TestQuery extends TsRequest {

    private TsRequest request;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;

    public TestQuery() {
        InitTsRequest("http://localhost:8097/", "", "");
    }

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        System.out.println("init mock module");
    }

    
    @Test
    void testSingleQuery() throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.get("/business/t-student/single-query?name=david-child").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("获取结果为：" + responseString);

    }

    @Test
    void testQueryList() throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.get("/business/t-student/query-list?name=david-child").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("获取结果为：" + responseString);

    }

}
