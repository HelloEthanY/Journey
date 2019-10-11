package com.journey.org.ui.custom;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import me.tatarka.bindingcollectionadapter2.LayoutManagers;
import me.tatarka.bindingcollectionadapter2.LayoutManagers.LayoutManagerFactory;

/**
 * 不能滑动RecycleView
 *
 * @author yu
 * @Date 2019/4/25
 */
public class NoScrollLayoutManagers {
    /**
     * A {@link NoScrollLayoutManagers}.
     */
    public static LayoutManagerFactory linear() {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                return new NoScrollLinearLayoutManager(recyclerView.getContext());
            }
        };
    }

    /**
     * A {@link NoScrollLayoutManagers} with the given orientation and reverseLayout.
     */
    public static LayoutManagerFactory linear(@LayoutManagers.Orientation final int orientation, final boolean reverseLayout) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                return new NoScrollLinearLayoutManager(recyclerView.getContext(), orientation, reverseLayout);
            }
        };
    }

    /**
     * A {@link GridLayoutManager} with the given spanCount.
     */
    public static LayoutManagerFactory grid(final int spanCount) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                return new NoScrollGridLayoutManager(recyclerView.getContext(), spanCount);
            }
        };
    }

    /**
     * A {@link GridLayoutManager} with the given spanCount, orientation and reverseLayout.
     **/
    public static LayoutManagerFactory grid(final int spanCount, @LayoutManagers.Orientation final int orientation, final boolean reverseLayout) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                return new NoScrollGridLayoutManager(recyclerView.getContext(), spanCount, orientation, reverseLayout);
            }
        };
    }

    private static class NoScrollLinearLayoutManager extends LinearLayoutManager {
        public NoScrollLinearLayoutManager(Context context) {
            super(context);
        }

        public NoScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public NoScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }

    private static class NoScrollGridLayoutManager extends GridLayoutManager {

        public NoScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public NoScrollGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public NoScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }
}
