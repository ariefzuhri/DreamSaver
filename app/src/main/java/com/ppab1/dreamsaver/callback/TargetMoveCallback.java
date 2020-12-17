package com.ppab1.dreamsaver.callback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.adapter.TargetAdapter;

public class TargetMoveCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperContract adapter;

    public TargetMoveCallback(ItemTouchHelperContract adapter){
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    // Here we implement the code for swiping
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {}

    @Override
    // Here we pass the flags for the directions of drag and swipe
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0); // Since swipe is disable, we pass 0 for it
    }

    @Override
    // Here we set the code for the drag and drop
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        adapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    /* Based on the current state of the RecyclerView and whether it’s pressed or swiped,
    this method gets triggered. Here we can customize the RecyclerView row.
    For example, changing the background color.*/
    // Metode ini dipanggil ketika pengguna menyentuh salah satu item
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if (viewHolder instanceof TargetAdapter.TargetViewHolder){
                TargetAdapter.TargetViewHolder targetViewHolder = (TargetAdapter.TargetViewHolder) viewHolder;
                adapter.onRowSelected(targetViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    // This method gets triggered when the user interaction stops with the RecyclerView row
    // Metode ini dipanggil ketika pengguna tidak lagi menyentuh salah satu item
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof TargetAdapter.TargetViewHolder){
            TargetAdapter.TargetViewHolder targetViewHolder = (TargetAdapter.TargetViewHolder) viewHolder;
            adapter.onRowClear(targetViewHolder);
        }
    }

    public interface ItemTouchHelperContract{
        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(TargetAdapter.TargetViewHolder photoViewHolder);
        void onRowClear(TargetAdapter.TargetViewHolder photoViewHolder);
    }
}