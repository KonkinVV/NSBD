package com.develop.konkin.drawit.model.helper;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.develop.konkin.drawit.R;

public final class FragmentHelper {

    public static void changeFragment(@NonNull final Fragment fragment,
                                      @NonNull final FragmentManager fragmentManager,
                                      final int containerId) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.from_top_anim, R.anim.alpha_anim);
        ft.replace(containerId, fragment).addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }
}
