package com.journey.org.app;

/**
 * 阿里云-热修复
 * @author 逍遥
 * @Date 2019/11/8
 *
 */
public class SophixStubApplication /*extends SophixApplication*/ {

    private final String TAG = "SophixStubApplication";

    private final String hotfixId = "28137808";
    private final String hotfixappKey = "e8f5dc0445fa529c891cd774630c1cfd";
    private final String hotfixrsaSecret = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPz4xL3DzeVpb3INyioeDXNFHFMSdGTX0TNmTTIf23UMmiTNeRQXA3JX6tCB+hfTfW2ConK3ZraiPIeFCf+WbQda54myH9Ugk9VzFUTQBsKRrleMhph3WR/IRrIhVqZ6fiWYKgMs+NVLGgh/OGq4S8SwGWai8I2RDtFLr3y7m59QIDAQAB";
/*
    @Keep // 不被混淆
    @SophixEntry(JourneyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 联网下载新的插件
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //         如果需要使用MultiDex，需要在此处调用。
        //         MultiDex.install(this);
        // 初始化阿里云热修复
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(hotfixId, hotfixappKey, hotfixrsaSecret)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }*/
}
