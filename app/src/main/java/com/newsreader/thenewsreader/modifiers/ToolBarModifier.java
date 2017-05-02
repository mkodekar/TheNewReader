package com.newsreader.thenewsreader.modifiers;

import android.view.Menu;

/**
 * Created by rkodekar on 4/30/17.
 */

public interface ToolBarModifier {

    boolean onPrepareMenuOptions(Menu menu);
    String title();
}
