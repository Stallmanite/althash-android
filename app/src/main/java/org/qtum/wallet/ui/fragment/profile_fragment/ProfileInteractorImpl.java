package org.qtum.wallet.ui.fragment.profile_fragment;

import android.content.Context;

import org.qtum.wallet.datastorage.KeyStorage;
import org.qtum.wallet.datastorage.QtumSettingSharedPreference;
import org.qtum.wallet.datastorage.QtumSharedPreference;
import org.qtum.wallet.datastorage.TinyDB;
import org.qtum.wallet.datastorage.listeners.LanguageChangeListener;

import io.realm.Realm;

class ProfileInteractorImpl implements ProfileInteractor {

    private Context mContext;

    ProfileInteractorImpl(Context context) {
        mContext = context;
    }

    @Override
    public void clearWallet(Realm realm) {
        QtumSharedPreference.getInstance().clear(mContext);
        KeyStorage.getInstance().clearKeyStorage();
        //TODO CLEAR REALM
        realm.removeAllChangeListeners();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        TinyDB db = new TinyDB(mContext);
        db.clearTokenList();
        db.clearContractList();
        db.clearTemplateList();
    }

    @Override
    public void setupLanguageChangeListener(LanguageChangeListener listener) {
        QtumSettingSharedPreference.getInstance().addLanguageListener(listener);
    }

    @Override
    public void removeLanguageListener(LanguageChangeListener listener) {
        QtumSettingSharedPreference.getInstance().removeLanguageListener(listener);
    }

    @Override
    public boolean isTouchIdEnable() {
        return QtumSharedPreference.getInstance().isTouchIdEnable(mContext);
    }

    @Override
    public void saveTouchIdEnable(boolean isChecked) {
        QtumSharedPreference.getInstance().saveTouchIdEnable(mContext, isChecked);
    }
}
