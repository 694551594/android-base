package cn.yhq.base;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.youngfeng.snake.Snake;

import timber.log.Timber;

/**
 * Created by Yanghuiqiang on 2016/11/4.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Snake.init(this);

//        // 异常处理
//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//                .enabled(false) //default: true
//                .showErrorDetails(false) //default: true
//                .showRestartButton(false) //default: true
//                .logErrorOnRestart(false) //default: true
//                .trackActivities(true) //default: false
//                .minTimeBetweenCrashesMs(2000) //default: 3000
//                .errorDrawable(R.drawable.ic_custom_drawable) //default: bug image
//                .restartActivity(YourCustomActivity.class) //default: null (your app's launch activity)
//                .errorActivity(YourCustomErrorActivity.class) //default: null (default error activity)
//                .eventListener(new YourCustomEventListener()) //default: null
//                .apply();

        // 日志打印
        Logger.addLogAdapter(new AndroidLogAdapter());

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                Logger.log(priority, tag, message, t);
            }
        });
    }

}
