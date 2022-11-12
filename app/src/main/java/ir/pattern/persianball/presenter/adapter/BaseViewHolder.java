package ir.pattern.persianball.presenter.adapter;


import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ir.pattern.persianball.data.model.base.PersianBallRecyclerData;

public abstract class BaseViewHolder<T extends PersianBallRecyclerData> extends RecyclerView.ViewHolder {

    public interface OnClickListener<V extends BaseViewHolder, T> {
        void onClick(View view, V viewHolder, T recyclerData);
    }

    public interface OnLongClickListener<V extends BaseViewHolder, T> {
        void onLongClick(View view, V viewHolder, T recyclerData);
    }

    public interface OnCheckedChangeListener<V extends BaseViewHolder, T> {
        void onCheckedChange(View view, V viewHolder, T recyclerData);
    }

    public interface OnMenuItemClickListener<V extends BaseViewHolder, T> {
        void onMenuItemClick(MenuItem item, V viewHolder, T recyclerData);
    }


    public <V extends BaseViewHolder, K> void setOnClickListener(View view, final OnClickListener<V, K>
            onClickListener, final V viewHolder, final K recyclerData) {
        if (onClickListener != null) {
            view.setOnClickListener(getOnClickListener(onClickListener, viewHolder, recyclerData));
        }
    }

    protected <V extends BaseViewHolder, K> View.OnClickListener getOnClickListener(final OnClickListener<V,
            K> onClickListener, final V viewHolder, final K recyclerData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v, viewHolder, recyclerData);
            }
        };
    }

    public <V extends BaseViewHolder, K> void setOnLongClickListener(
            View view, final OnLongClickListener<V, K> onLongClickListener, final V viewHolder, final K
            recyclerData) {
        if (onLongClickListener != null) {
            view.setOnLongClickListener(getOnLongClickListener(onLongClickListener, viewHolder,
                    recyclerData));
        }
    }

    protected <V extends BaseViewHolder, K> View.OnLongClickListener getOnLongClickListener(
            final OnLongClickListener<V, K> onLongClickListener, final V viewHolder, final K recyclerData) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onLongClick(v, viewHolder, recyclerData);
                return true;
            }
        };
    }
//
//    public <V extends BaseViewHolder, K> void setOnCheckedChangeListener(
//            View view, final OnCheckedChangeListener<V, K> onCheckedChangeListener, final V viewHolder,
//            final K
//                    recyclerData) {
//        if (onCheckedChangeListener != null && view instanceof MyketSwitch) {
//            ((MyketSwitch) view).setOnCheckedChangeListener(getOnCheckedChangeListener(onCheckedChangeListener
//                    , viewHolder, recyclerData));
//        }
//    }
//
//    protected <V extends BaseViewHolder, K> CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener(
//            final OnCheckedChangeListener<V, K> onCheckedChangeListener, final V viewHolder,
//            final K recyclerData) {
//        return new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton v, boolean b) {
//                onCheckedChangeListener.onCheckedChange(v, viewHolder, recyclerData);
//            }
//        };
//    }

    public <V extends BaseViewHolder, K> void setOnMenuItemClickListener(
            MenuItem menuItem, final OnMenuItemClickListener<V, K> onMenuItemClickListener, final V
            viewHolder, final K recyclerData) {
        if (onMenuItemClickListener != null) {
            menuItem.setOnMenuItemClickListener(getOnMenuItemClickListener(onMenuItemClickListener,
                    viewHolder, recyclerData));
        }
    }

    protected <V extends BaseViewHolder, K> MenuItem.OnMenuItemClickListener getOnMenuItemClickListener(
            final OnMenuItemClickListener<V, K> onMenuItemClickListener, final V viewHolder, final K
            recyclerData) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMenuItemClickListener.onMenuItemClick(item, viewHolder, recyclerData);
                return false;
            }
        };
    }

    protected ItemTouchHelper itemTouchHelper;
    @Nullable
    protected ViewDataBinding viewDataBinding;


    public abstract void onBindView(T data);

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void onDetach(T data) {
        itemTouchHelper = null;
        viewDataBinding = null;
    }

    public void onAttach(T data) {

    }

//    protected ViewHolderComponent activityComponent() {
//        return ApplicationLauncher.getInstance().viewHolderComponent();
//    }

    @Override
    public String toString() {
        return super.toString() + getClass();
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public boolean isMovable() {
        return false;
    }

    public boolean isDragChangeBackground() {
        return false;
    }

    public void setViewDataBinding(@Nullable ViewDataBinding viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
    }
}