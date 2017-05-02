package com.newsreader.thenewsreader.paths;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.modifiers.HandlesBack;
import com.newsreader.thenewsreader.utils.BackSupport;

import flow.Flow;
import flow.path.Path;
import flow.path.PathContainer;
import flow.path.PathContainerView;

/**
 * Created by rkodekar on 4/30/17.
 */

public class FramePathContainerView extends FrameLayout implements HandlesBack, PathContainerView {

    private final PathContainer container;
    private boolean disabled;

    @SuppressWarnings("UnusedDeclaration") // Used by layout inflation, of course!
    public FramePathContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag, Path.contextFactory()));
    }

    /**
     * Allows subclasses to use custom {@link PathContainer} implementations. Allows the use
     * of more sophisticated transition schemes, and customized context wrappers.
     */
    protected FramePathContainerView(Context context, AttributeSet attrs, PathContainer container) {
        super(context, attrs);
        this.container = container;
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        return !disabled && super.dispatchTouchEvent(ev);
    }

    @Override public ViewGroup getContainerView() {
        return this;
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        disabled = true;
        container.executeTraversal(this, traversal, () -> {
            callback.onTraversalCompleted();
            disabled = false;
        });
    }

    @Override public boolean onBackPressed() {
        return BackSupport.onBackPressed(getCurrentChild());
    }

    @Override public ViewGroup getCurrentChild() {
        return (ViewGroup) getContainerView().getChildAt(0);
    }
}
