package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Ingo on 17.07.2015.
 */
class TableHeaderView extends LinearLayout {

    public TableHeaderView(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

}
