package io.github.davidchild.bitter.tools;

import io.github.davidchild.bitter.init.BitterConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class BitterLogUtil {
    public static Logger getInstance() {
        return LoggerFactory.getLogger("bitter");
    }

    public static void logWriteError(Exception ex, List<Object> params) {
        if (ex != null) {
            BitterLogUtil.getInstance().error("bitter-sql-execute-error:" + ex.getMessage()
                    + "\r\n------------params------------\r\n" + JsonUtil.object2String(params), ex);
        }
    }

    public static void logWriteError(Exception ex, String params) {
        if (ex != null) {
            BitterLogUtil.getInstance().error(
                    "bitter-sql-execute-error:" + ex.getMessage() + "\r\n------------params------------\r\n" + params, ex);
        }
    }

    public static void logWriteSql(String commandTest, List<Object> params) {
        if (BitterConfig.getInstance().isSqlLog()) {
            BitterLogUtil.getInstance().info("bitter will  execute sql:" + "\r\n" + commandTest + "\r\n"
                    + "------------params------------" + JsonUtil.object2String(params));
        }
    }

    public static void logWriteSql(String commandTest, String params) {
        if (BitterConfig.getInstance().isSqlLog()) {
            BitterLogUtil.getInstance().info("bitter will  execute sql:" + "\r\n" + commandTest + "\r\n"
                    + "------------params------------" + params);
        }
    }
}
