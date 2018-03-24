package cn.yhq.base.log;

import com.orhanobut.logger.LogAdapter;

/**
 * Created by Yanghuiqiang on 2016/11/4.
 */

public class FileLogAdapter implements LogAdapter {

    @Override
    public boolean isLoggable(int priority, String tag) {
        return true;
    }

    @Override
    public void log(int priority, String tag, String message) {

    }
}
