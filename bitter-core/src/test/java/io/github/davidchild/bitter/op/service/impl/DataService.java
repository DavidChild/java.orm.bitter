package io.github.davidchild.bitter.op.service.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import io.github.davidchild.bitter.annotation.TargetSource;
import io.github.davidchild.bitter.op.service.IDataServe;

@Service
public class DataService implements IDataServe {

    @Override
    @TargetSource("SLAVE")
    public void testInspcet() throws InstantiationException, IllegalAccessException, SQLException {

    }
}
