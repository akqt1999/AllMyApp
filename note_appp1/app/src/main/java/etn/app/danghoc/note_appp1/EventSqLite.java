package etn.app.danghoc.note_appp1;

import java.util.List;

import etn.app.danghoc.note_appp1.model.ItemFavorite;
import etn.app.danghoc.note_appp1.model.ItemTitle;

public interface EventSqLite {
     void deleteItem (int posotion);
     void addItem(ItemTitle itemTitle);
     void addFavorite(int id,int checkFavorite);
     void deleteFavorite(int id,int checkFavorite);
}
