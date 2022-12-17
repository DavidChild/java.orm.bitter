package io.github.davidchild.bitter.op.service.impl;

import io.github.davidchild.bitter.op.annotation.TargetSource;
import io.github.davidchild.bitter.op.service.IDataServe;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DataService implements IDataServe {

    @Override
    @TargetSource("SLAVE")
    public void testInspcet() throws InstantiationException, IllegalAccessException, SQLException {

    }
}
