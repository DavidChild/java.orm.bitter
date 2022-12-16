package io.github.davidchild.bitter.parbag;

import java.util.LinkedHashMap;

public class ExecuteParBagExecute extends ExecuteParBag {

    private String commandText;
    private LinkedHashMap<String, Object> paraMap;
    private Object defaultValue = null;

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public LinkedHashMap<String, Object> getParaMap() {
        return paraMap;
    }

    public void setParaMap(LinkedHashMap<String, Object> paraMap) {
        this.paraMap = paraMap;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
