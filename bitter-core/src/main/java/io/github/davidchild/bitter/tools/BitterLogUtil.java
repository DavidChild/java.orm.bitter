package io.github.davidchild.bitter.tools;

import io.github.davidchild.bitter.init.BitterConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class BitterLogUtil {
    public static Logger getInstance() {
        return LoggerFactory.getLogger("bitter");
    }

    private static List<Object> getParams(LinkedHashMap<String, Object> params){
        if(params == null|| params.size()< 1) return new ArrayList<>();
        List<Object> list= new ArrayList<>();
        params.forEach((k,v)->{
            list.add(v);
        });
        return  list;
    }
    public static void logWriteError(Exception ex, LinkedHashMap<String, Object> params) {
        if (ex != null) {
            BitterLogUtil.getInstance().error("bitter-sql-execute-error:" + ex.getMessage()
                    + "\r\n------------params------------\r\n" + JsonUtil.object2String(getParams(params)), ex);
        }
    }

    public static void logWriteError(Exception ex, String params) {
        if (ex != null) {
            BitterLogUtil.getInstance().error(
                    "bitter-sql-execute-error:" + ex.getMessage() + "\r\n------------params------------\r\n" + params, ex);
        }
    }

    public static void logWriteSql(String commandTest, LinkedHashMap<String, Object> params) {
        if (BitterConfig.getInstance().isSqlLog()) {
            BitterLogUtil.getInstance().info("bitter will  execute sql:" + "\r\n" + commandTest + "\r\n"
                    + "------------params------------" + JsonUtil.object2String(getParams(params)));
        }
    }

    public static void logWriteSql(String commandTest, String params) {
        if (BitterConfig.getInstance().isSqlLog()) {
            BitterLogUtil.getInstance().info("bitter will  execute sql:" + "\r\n" + commandTest + "\r\n"
                    + "------------params------------" + params);
        }
    }
}
