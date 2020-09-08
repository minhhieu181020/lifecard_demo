package com.lpb.lifecardsdk.widget.htmltextview;

import android.support.annotation.Nullable;
import android.view.View;



/**
 * This listener can define what happens when the a tag is clicked
 */
public interface OnClickATagListener {
    void onClick(View widget, @Nullable String href);

}
