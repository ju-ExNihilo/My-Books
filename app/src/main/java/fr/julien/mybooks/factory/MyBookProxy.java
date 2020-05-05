package fr.julien.mybooks.factory;

import fr.julien.mybooks.models.Item;
import fr.julien.mybooks.models.MyBooks;
import java.util.ArrayList;
import java.util.List;

public class MyBookProxy {

    public static List<MyBooks> getAllMyBooks(List<Item> itemList){
        List<MyBooks> myFinalBooks = new ArrayList<>();
        for (Item item : itemList){
            String byLink = "";
            String price = "";
            String pdfLink = "";
            String author = "";
            String description = "";
            String language = "";
            String pic = "";

            if (item.getSaleInfo().getBuyLink() == null){ byLink = "Not for sale";}else{byLink = item.getSaleInfo().getBuyLink();}

            if (item.getSaleInfo().getSaleability().contentEquals("NOT_FOR_SALE") || item.getSaleInfo().getSaleability().contentEquals("FREE")){
                price = "Free";
            }else{ price = String.valueOf(item.getSaleInfo().getListPrice().getAmount()) + " €"; }

            if (item.getAccessInfo().getWebReaderLink() == null ){ pdfLink = "No have pdf link";}else{pdfLink = item.getAccessInfo().getWebReaderLink();}

            if (item.getVolumeInfo().getAuthors() == null ){ author = "dont know";}else{author = item.getVolumeInfo().getAuthors().get(0);}

            if (item.getVolumeInfo().getDescription() == null ){ description = "No description yet";}else{description = item.getVolumeInfo().getDescription();}

            if (item.getVolumeInfo().getLanguage() == null ){ language = "dont know";}else{language = item.getVolumeInfo().getLanguage();}

            if (item.getVolumeInfo().getImageLinks().getSmallThumbnail() == null ){ pic = "null";}else{pic = item.getVolumeInfo().getImageLinks().getSmallThumbnail();}

            MyBooks book = new MyBooks(item.getId(),item.getVolumeInfo().getTitle(),author,pic,
                    language,price, pdfLink, byLink, description);
            myFinalBooks.add(book);
        }
        return myFinalBooks;
    }

}
