package fr.julien.mybooks.mysavebooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import fr.julien.mybooks.R;
import fr.julien.mybooks.databinding.BookItemSaveListBinding;
import fr.julien.mybooks.models.MyBooks;
import java.lang.ref.WeakReference;
import java.util.List;

public class MySaveBooksAdapterRV extends RecyclerView.Adapter<MySaveBooksAdapterRV.ViewHolder>{

    public interface OnSaveBooksItemClickListener {
        void onClickBookItem(MyBooks book, View viewClicked);
        void onDeleteClick(MyBooks books);
    }

    private final MySaveBooksAdapterRV.OnSaveBooksItemClickListener callback;
    private WeakReference<MySaveBooksAdapterRV.OnSaveBooksItemClickListener> callbackWeakRef;
    private final List<MyBooks> items;
    private boolean fromMyBooks;

    public MySaveBooksAdapterRV(List<MyBooks> items, MySaveBooksAdapterRV.OnSaveBooksItemClickListener callback,boolean fromMyBooks){
        this.items = items;
        this.callback = callback;
        this.fromMyBooks = fromMyBooks;
    }

    @NonNull
    @Override
    public MySaveBooksAdapterRV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new MySaveBooksAdapterRV.ViewHolder(BookItemSaveListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MySaveBooksAdapterRV.ViewHolder holder, int position){
        this.callbackWeakRef = new WeakReference<OnSaveBooksItemClickListener>(callback);
        if (items.get(position) != null){
            MyBooks book = items.get(position);
            if (book.getPic().startsWith("http")){
                Glide.with(holder.binding.bookImageSave.getContext())
                        .load(book.getPic())
                        .into(holder.binding.bookImageSave);
            }else{
                holder.binding.bookImageSave.setImageResource(R.drawable.book);
            }
            holder.binding.bookTitleSave.setText(book.getTitle());

            /** Listener with callback **/
            holder.itemView.setOnClickListener(v -> {
                MySaveBooksAdapterRV.OnSaveBooksItemClickListener callBack = callbackWeakRef.get();
                if (callBack != null) callBack.onClickBookItem(book, holder.itemView);
            });

            if (fromMyBooks){
                holder.binding.itemStopSearchButton.setVisibility(View.VISIBLE);
                holder.binding.itemStopSearchButton.setOnClickListener(v -> {
                    OnSaveBooksItemClickListener callBack = callbackWeakRef.get();
                    if (callBack != null) callBack.onDeleteClick(book);
                });
            }

        }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        BookItemSaveListBinding binding;

        public ViewHolder(BookItemSaveListBinding bindingItem){
            super(bindingItem.getRoot());
            binding = bindingItem;
        }
    }
}
