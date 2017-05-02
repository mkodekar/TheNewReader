package com.newsreader.thenewsreader.frame;

import android.content.Context;
import android.util.AttributeSet;

import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.appfactory.BasicMortarContextFactory;
import com.newsreader.thenewsreader.appfactory.ScreenScoper;
import com.newsreader.thenewsreader.paths.FramePathContainerView;
import com.newsreader.thenewsreader.paths.SimplePathContainer;

import flow.path.Path;

/**
 * Created by rkodekar on 4/30/17.
 */

public class MainFrame extends FramePathContainerView {
    public MainFrame(Context context, AttributeSet attrs) {
        super(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag,
                Path.contextFactory(new BasicMortarContextFactory(new ScreenScoper()))));
    }
}
