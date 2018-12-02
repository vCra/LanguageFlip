package io.vcra.apps.languageflip.data.settings;

import android.app.Application;

import io.vcra.apps.languageflip.data.LFDB;

public class FirstRunDetector {
    private SettingDAO dao;

    public FirstRunDetector(Application app) {
        LFDB db = LFDB.Companion.getDatabase(app);
        dao = db.settingDAO();
    }

    public boolean isFirstRun(){
        return dao.getSettingsCount() == 0;
    }
}
