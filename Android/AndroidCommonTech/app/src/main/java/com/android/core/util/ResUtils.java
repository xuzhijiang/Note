package com.android.core.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class ResUtils {

    public static InputStream readBinaryFromAssets(Context context, String filename) throws IOException {
        InputStream is = context.getResources().getAssets().open(filename);
        return is;
    }


}
