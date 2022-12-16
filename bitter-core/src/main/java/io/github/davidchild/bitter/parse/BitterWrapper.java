package io.github.davidchild.bitter.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BitterWrapper implements Map.Entry<StringBuilder, List<Object>> {

    private final StringBuilder sql;
    private final List<Object> params;

    public BitterWrapper() {
        this.sql = new StringBuilder();
        this.params = new ArrayList<>();
    }

    @Override
    public synchronized StringBuilder getKey() {
        return this.sql;
    }

    public synchronized StringBuilder addSql(CharSequence input) {
        this.sql.append(input);
        return this.sql;
    }

    @Override
    public synchronized List<Object> getValue() {
        return this.params;
    }

    public synchronized List<Object> addParams(Object o) {
        this.params.add(o);
        return this.params;
    }

    @Override
    public List<Object> setValue(List<Object> value) {
        throw new UnsupportedOperationException();
    }

    public synchronized void clear() {
        this.sql.setLength(0);
        this.params.clear();
    }

    @Override
    public String toString() {
        return this.sql.toString() + System.lineSeparator() + this.params.toString();
    }

}
