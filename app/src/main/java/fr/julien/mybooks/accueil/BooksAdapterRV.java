package fr.julien.mybooks.accueil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import fr.julien.mybooks.R;
import fr.julien.mybooks.databinding.BookItemBinding;
import fr.julien.mybooks.models.MyBooks;
import java.lang.ref.WeakReference;
import java.util.List;

public class BooksAdapterRV extends RecyclerView.Adapter<BooksAdapterRV.MyViewHolder>{

    public interface OnBooksItemClickListener {
        void onClickBookItem(MyBooks book, View viewClicked);
    }

    private final OnBooksItemClickListener callback;
    private WeakReference<OnBooksItemClickListener> callbackWeakRef;
    private final List<MyBooks> items;

    public BooksAdapterRV(List<MyBooks> items, OnBooksItemClickListener callback){
        this.items = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new MyViewHolder(BookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        this.callbackWeakRef = new WeakReference<OnBooksItemClickListener>(callback);
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
                OnBooksItemClickListener callBack = callbackWeakRef.get();
                if (callBack != null) callBack.onClickBookItem(book, holder.itemView);
            });
        }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        BookItemBinding binding;

        public MyViewHolder(BookItemBinding bindingItem){
            super(bindingItem.getRoot());
            binding = bindingItem;
        }
    }
}

