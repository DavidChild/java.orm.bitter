package io.github.davidchild.bitter.samples.request;

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
class TestDelete extends TsRequest {

    private TsRequest request;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;

    public TestDelete() {
        InitTsRequest("http://localhost:8097/", "", "");
    }

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        System.out.println("init mock module");
    }

    @Test
    void testDelete() throws Exception {
        String responseString = mvc.perform(MockMvcRequestBuilders.get("/business/t-student/delete?id=7").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("获取结果为：" + responseString);
    }

}
