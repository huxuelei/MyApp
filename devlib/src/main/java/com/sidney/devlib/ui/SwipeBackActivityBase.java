package com.sidney.devlib.ui;


import com.sidney.devlib.comment.widget.SwipeBack.SwipeBackLayout;

/**
 * @author huxuelei
 */
public interface SwipeBackActivityBase {
	/**
	 * @return the SwipeBackLayout associated with this activity.
	 */
	public abstract SwipeBackLayout getSwipeBackLayout();

	public abstract void setSwipeBackEnable(boolean enable);

	/**
	 * Scroll out contentView and finish the activity
	 */
	public abstract void scrollToFinishActivity();

}
