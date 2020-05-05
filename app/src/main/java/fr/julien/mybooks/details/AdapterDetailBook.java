package fr.julien.mybooks.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import fr.julien.mybooks.R;
import fr.julien.mybooks.databinding.BookItemDetailBinding;
import fr.julien.mybooks.models.MyBooks;
import java.lang.ref.WeakReference;
import java.util.List;

public class AdapterDetailBook extends RecyclerView.Adapter<AdapterDetailBook.DetailViewHolder>{

    public interface OnBooksItemClickListener {
        void onClickBookItem(MyBooks book, View viewClicked);
    }

    private final AdapterDetailBook.OnBooksItemClickListener callback;
    private WeakReference<AdapterDetailBook.OnBooksItemClickListener> callbackWeakRef;
    private final List<MyBooks> items;

    public AdapterDetailBook(List<MyBooks> items, AdapterDetailBook.OnBooksItemClickListener callback){
        this.items = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public AdapterDetailBook.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new AdapterDetailBook.DetailViewHolder(BookItemDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetailBook.DetailViewHolder holder, int position){
        this.callbackWeakRef = new WeakReference<AdapterDetailBook.OnBooksItemClickListener>(callback);
        if (items.get(position) != null){
            MyBooks book = items.get(position);
            if (book.getPic().startsWith("http")){
                Glide.with(holder.binding.bookImage.getContext())
                        .load(book.getPic())
                        .into(holder.binding.bookImage);
            }else{
                holder.binding.bookImage.setImageResource(R.drawable.book);
            }
            holder.binding.bookTitle.setText(book.getTitle());

            /** Listener with callback **/
            holder.itemView.setOnClickListener(v -> {
                AdapterDetailBook.OnBooksItemClickListener callBack = callbackWeakRef.get();
                if (callBack != null) callBack.onClickBookItem(book, holder.itemView);
            });
        }
    }
    @Override
    public int getItemCount(){
        return items.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder{
        BookItemDetailBinding binding;

        public DetailViewHolder(BookItemDetailBinding bindingItem){
            super(bindingItem.getRoot());
            binding = bindingItem;
        }
    }
}