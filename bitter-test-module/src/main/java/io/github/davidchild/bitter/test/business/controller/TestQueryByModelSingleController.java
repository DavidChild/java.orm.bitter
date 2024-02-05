package io.github.davidchild.bitter.test.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.tools.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/business/t-query-sigle-table")
public class TestQueryByModelSingleController {
    @GetMapping("/query-by-id")
    public TUser queryById(String id) {
        return db.findQuery(TUser.class).where(f -> f.getId() == id).find().fistOrDefault();
    }

    @GetMapping("/query-list-order")
    public List<TUser> queryListOrder(String name) {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list = db.findQuery(TUser.class).thenAsc(TUser::getId).find();
        return list;
    }

    @GetMapping("/query-list-condition-order")
    public List<TUser> queryListConditionAndOrder(String name) {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list = db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s))
                .where(t -> t.getUsername().contains("hjb"))
                .where(f -> f.getUserAge() > 20)
                .thenAsc(TUser::getId).thenDesc(f -> f.getCreateTime()).find();
        return list;
    }


    @GetMapping("/query-one-recode-condition-order")
    public TUser queryOneRecodeConditionAndOrder(String name) {
        Date s = DateUtils.parseDate("2022-8-10");
        TUser user = db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s))
                .where(t -> t.getUsername().contains("hjb"))
                .where(f -> f.getUserAge() > 20)
                .setSize(1) //note will select [fields...] from table limit 0,1
                .thenAsc(TUser::getId).thenDesc(f -> f.getCreateTime()).find().fistOrDefault();
        return user;
    }

    @GetMapping("/query-condition-order-select-necessary-column")
    public List<TUser> queryConditionAndOrderAndColumn(String name) {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list = db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s))
                .where(t -> t.getUsername().contains("hjb"))
                .where(f -> f.getUserAge() > 20)
                .select(TUser::getId, TUser::getUsername, TUser::getAvatar) // that will be select id,user_name,avatar
                .thenAsc(TUser::getId).thenDesc(f -> f.getCreateTime()).find();
        return list;
    }




}
