package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.View;

/**
 * Created by Ingo on 17.07.2015.
 */
public interface TableHeaderAdapter {

    View getHeaderView(Context context, int columnIndex);

}
