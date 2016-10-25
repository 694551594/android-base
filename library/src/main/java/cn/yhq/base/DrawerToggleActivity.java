package cn.yhq.base;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public abstract class DrawerToggleActivity extends BaseActivity {
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private int mDrawerGravity;

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
        if (mDrawerLayout != null) {
            mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, this.getToolbar(),
                    R.string.open, R.string.close);
            mActionBarDrawerToggle.syncState();
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        }
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mDrawerGravity);
    }

    public void openDrawer(int gravity) {
        this.mDrawerGravity = gravity;
        mDrawerLayout.openDrawer(gravity);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(this.mDrawerGravity)) {
                mDrawerLayout.closeDrawer(this.mDrawerGravity);
                return;
            }
        }
        super.onBackPressed();
    }

    public void setDrawerEnable(boolean enable) {
        mDrawerLayout.setDrawerLockMode(
                enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

}
