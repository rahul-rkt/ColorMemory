
package thakur.rahul.colourmemory.model;

import thakur.rahul.colourmemory.R;
import android.app.Activity;
import android.util.SparseArray;
import android.view.ViewDebug.RecyclerTraceType;

/**
 * Enum holding all the different types Cards.
 *
 * @author rahulthakur
 */
public enum CardModel {
	CARD1(1, R.drawable.colour1),
	CARD2(2, R.drawable.colour2),
	CARD3(3, R.drawable.colour3),
	CARD4(4, R.drawable.colour4),
	CARD5(5, R.drawable.colour5),
	CARD6(6, R.drawable.colour6),
	CARD7(7, R.drawable.colour7),
	CARD8(8, R.drawable.colour8);

	/**
	 * Adds all Cards to a SparseArray to get via ID.
	 */
	private static final SparseArray<CardModel> byId = new SparseArray<CardModel>();
	private static String prefix = "colour";
	static {
		for (CardModel c : CardModel.values())
			byId.put(c.getId(), c);
		//for(int i = 1; i <= 8; i++ ) {
		//	byId.put(i, getResources().getIdentifier(prefix+i, "drawable", "thakur.rahul.colourmemory");
					
		//}
	}
	private int id;
	private int type;

	/**
	 * Gets Card via ID.
	 */
	public static CardModel getById(int id) {

		return byId.get(id);
	}
	
	public static void setupCards(String prefix, Activity activity) {
		byId.clear();
		for( int i = 1; i <= 8; i++ ) {
			int cardDrawableID = activity.getResources().getIdentifier(prefix+i, "drawable", "thakur.rahul.colourmemory");
			//byId.put( i, new CardModel());
		}
	}
	
	CardModel(int id, int type) {

		this.id = id;
		this.type = type;
	}

	/**
	 * Gets Drawable ID for the associated Card.
	 */
	public int getType() {

		return type;
	}

	public int getId() {

		return id;
	}
}