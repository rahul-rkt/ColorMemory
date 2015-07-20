
package thakur.rahul.colourmemory.view;

import thakur.rahul.colourmemory.R;
import android.app.Activity;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.ImageView;

/**
 * Maps all the ImageViews in <i>include_grid_0.xml</i> & <i>include_grid_1.xml</i> layout files<br>
 * Mapping is done without using loops for faster result. Also, the grid size is fixed at 4x4
 *
 * @author rahulthakur
 */
public class CardView {

	private ImageView[][][] cardViewArray;
	private SparseArray<ImageView> cardViewMap;
	private SparseIntArray cardSwapMap;
	private Activity activity;

	public CardView(Activity activity) {

		cardViewArray = new ImageView[4][4][2];
		cardViewMap = new SparseArray<ImageView>();
		cardSwapMap = new SparseIntArray();
		this.activity = activity;
		setCardViewArray();
		setCardViewMap();
		setCardSwapMap();
	}

	private void setCardViewArray() {

		// =============================== [0] ===============================
		// R0
		cardViewArray[0][0][0] = (ImageView) activity.findViewById(R.id.R0C0_0);
		cardViewArray[0][1][0] = (ImageView) activity.findViewById(R.id.R0C1_0);
		cardViewArray[0][2][0] = (ImageView) activity.findViewById(R.id.R0C2_0);
		cardViewArray[0][3][0] = (ImageView) activity.findViewById(R.id.R0C3_0);
		// R1
		cardViewArray[1][0][0] = (ImageView) activity.findViewById(R.id.R1C0_0);
		cardViewArray[1][1][0] = (ImageView) activity.findViewById(R.id.R1C1_0);
		cardViewArray[1][2][0] = (ImageView) activity.findViewById(R.id.R1C2_0);
		cardViewArray[1][3][0] = (ImageView) activity.findViewById(R.id.R1C3_0);
		// R2
		cardViewArray[2][0][0] = (ImageView) activity.findViewById(R.id.R2C0_0);
		cardViewArray[2][1][0] = (ImageView) activity.findViewById(R.id.R2C1_0);
		cardViewArray[2][2][0] = (ImageView) activity.findViewById(R.id.R2C2_0);
		cardViewArray[2][3][0] = (ImageView) activity.findViewById(R.id.R2C3_0);
		// R3
		cardViewArray[3][0][0] = (ImageView) activity.findViewById(R.id.R3C0_0);
		cardViewArray[3][1][0] = (ImageView) activity.findViewById(R.id.R3C1_0);
		cardViewArray[3][2][0] = (ImageView) activity.findViewById(R.id.R3C2_0);
		cardViewArray[3][3][0] = (ImageView) activity.findViewById(R.id.R3C3_0);
		//
		// =============================== [1] ===============================
		// R0
		cardViewArray[0][0][1] = (ImageView) activity.findViewById(R.id.R0C0_1);
		cardViewArray[0][1][1] = (ImageView) activity.findViewById(R.id.R0C1_1);
		cardViewArray[0][2][1] = (ImageView) activity.findViewById(R.id.R0C2_1);
		cardViewArray[0][3][1] = (ImageView) activity.findViewById(R.id.R0C3_1);
		// R1
		cardViewArray[1][0][1] = (ImageView) activity.findViewById(R.id.R1C0_1);
		cardViewArray[1][1][1] = (ImageView) activity.findViewById(R.id.R1C1_1);
		cardViewArray[1][2][1] = (ImageView) activity.findViewById(R.id.R1C2_1);
		cardViewArray[1][3][1] = (ImageView) activity.findViewById(R.id.R1C3_1);
		// R2
		cardViewArray[2][0][1] = (ImageView) activity.findViewById(R.id.R2C0_1);
		cardViewArray[2][1][1] = (ImageView) activity.findViewById(R.id.R2C1_1);
		cardViewArray[2][2][1] = (ImageView) activity.findViewById(R.id.R2C2_1);
		cardViewArray[2][3][1] = (ImageView) activity.findViewById(R.id.R2C3_1);
		// R3
		cardViewArray[3][0][1] = (ImageView) activity.findViewById(R.id.R3C0_1);
		cardViewArray[3][1][1] = (ImageView) activity.findViewById(R.id.R3C1_1);
		cardViewArray[3][2][1] = (ImageView) activity.findViewById(R.id.R3C2_1);
		cardViewArray[3][3][1] = (ImageView) activity.findViewById(R.id.R3C3_1);
	}

	/**
	 ** CardViewArray contains all the individual ImageViews on both grids
	 */
	public ImageView[][][] getCardViewArray() {

		return cardViewArray;
	}

	private void setCardViewMap() {

		// ====================== [0] ======================
		// R0
		cardViewMap.put(R.id.R0C0_0, cardViewArray[0][0][0]);
		cardViewMap.put(R.id.R0C1_0, cardViewArray[0][1][0]);
		cardViewMap.put(R.id.R0C2_0, cardViewArray[0][2][0]);
		cardViewMap.put(R.id.R0C3_0, cardViewArray[0][3][0]);
		// R1
		cardViewMap.put(R.id.R1C0_0, cardViewArray[1][0][0]);
		cardViewMap.put(R.id.R1C1_0, cardViewArray[1][1][0]);
		cardViewMap.put(R.id.R1C2_0, cardViewArray[1][2][0]);
		cardViewMap.put(R.id.R1C3_0, cardViewArray[1][3][0]);
		// R2
		cardViewMap.put(R.id.R2C0_0, cardViewArray[2][0][0]);
		cardViewMap.put(R.id.R2C1_0, cardViewArray[2][1][0]);
		cardViewMap.put(R.id.R2C2_0, cardViewArray[2][2][0]);
		cardViewMap.put(R.id.R2C3_0, cardViewArray[2][3][0]);
		// R3
		cardViewMap.put(R.id.R3C0_0, cardViewArray[3][0][0]);
		cardViewMap.put(R.id.R3C1_0, cardViewArray[3][1][0]);
		cardViewMap.put(R.id.R3C2_0, cardViewArray[3][2][0]);
		cardViewMap.put(R.id.R3C3_0, cardViewArray[3][3][0]);
		//
		// ====================== [1] ======================
		// R0
		cardViewMap.put(R.id.R0C0_1, cardViewArray[0][0][1]);
		cardViewMap.put(R.id.R0C1_1, cardViewArray[0][1][1]);
		cardViewMap.put(R.id.R0C2_1, cardViewArray[0][2][1]);
		cardViewMap.put(R.id.R0C3_1, cardViewArray[0][3][1]);
		// R1
		cardViewMap.put(R.id.R1C0_1, cardViewArray[1][0][1]);
		cardViewMap.put(R.id.R1C1_1, cardViewArray[1][1][1]);
		cardViewMap.put(R.id.R1C2_1, cardViewArray[1][2][1]);
		cardViewMap.put(R.id.R1C3_1, cardViewArray[1][3][1]);
		// R2
		cardViewMap.put(R.id.R2C0_1, cardViewArray[2][0][1]);
		cardViewMap.put(R.id.R2C1_1, cardViewArray[2][1][1]);
		cardViewMap.put(R.id.R2C2_1, cardViewArray[2][2][1]);
		cardViewMap.put(R.id.R2C3_1, cardViewArray[2][3][1]);
		// R3
		cardViewMap.put(R.id.R3C0_1, cardViewArray[3][0][1]);
		cardViewMap.put(R.id.R3C1_1, cardViewArray[3][1][1]);
		cardViewMap.put(R.id.R3C2_1, cardViewArray[3][2][1]);
		cardViewMap.put(R.id.R3C3_1, cardViewArray[3][3][1]);
	}

	/**
	 ** CardViewMap contains all the ImageViews mapped to their respective IDs for faster search and access time
	 */
	public SparseArray<ImageView> getCardViewMap() {

		return cardViewMap;
	}

	private void setCardSwapMap() {

		// ================ [0] ================
		// R0
		cardSwapMap.put(R.id.R0C0_0, R.id.R0C0_1);
		cardSwapMap.put(R.id.R0C1_0, R.id.R0C1_1);
		cardSwapMap.put(R.id.R0C2_0, R.id.R0C2_1);
		cardSwapMap.put(R.id.R0C3_0, R.id.R0C3_1);
		// R1
		cardSwapMap.put(R.id.R1C0_0, R.id.R1C0_1);
		cardSwapMap.put(R.id.R1C1_0, R.id.R1C1_1);
		cardSwapMap.put(R.id.R1C2_0, R.id.R1C2_1);
		cardSwapMap.put(R.id.R1C3_0, R.id.R1C3_1);
		// R2
		cardSwapMap.put(R.id.R2C0_0, R.id.R2C0_1);
		cardSwapMap.put(R.id.R2C1_0, R.id.R2C1_1);
		cardSwapMap.put(R.id.R2C2_0, R.id.R2C2_1);
		cardSwapMap.put(R.id.R2C3_0, R.id.R2C3_1);
		// R3
		cardSwapMap.put(R.id.R3C0_0, R.id.R3C0_1);
		cardSwapMap.put(R.id.R3C1_0, R.id.R3C1_1);
		cardSwapMap.put(R.id.R3C2_0, R.id.R3C2_1);
		cardSwapMap.put(R.id.R3C3_0, R.id.R3C3_1);
		//
		// ================ [1] ================
		// R0
		cardSwapMap.put(R.id.R0C0_1, R.id.R0C0_0);
		cardSwapMap.put(R.id.R0C1_1, R.id.R0C1_0);
		cardSwapMap.put(R.id.R0C2_1, R.id.R0C2_0);
		cardSwapMap.put(R.id.R0C3_1, R.id.R0C3_0);
		// R1                      ,
		cardSwapMap.put(R.id.R1C0_1, R.id.R1C0_0);
		cardSwapMap.put(R.id.R1C1_1, R.id.R1C1_0);
		cardSwapMap.put(R.id.R1C2_1, R.id.R1C2_0);
		cardSwapMap.put(R.id.R1C3_1, R.id.R1C3_0);
		// R2                      ,
		cardSwapMap.put(R.id.R2C0_1, R.id.R2C0_0);
		cardSwapMap.put(R.id.R2C1_1, R.id.R2C1_0);
		cardSwapMap.put(R.id.R2C2_1, R.id.R2C2_0);
		cardSwapMap.put(R.id.R2C3_1, R.id.R2C3_0);
		// R3                      ,
		cardSwapMap.put(R.id.R3C0_1, R.id.R3C0_0);
		cardSwapMap.put(R.id.R3C1_1, R.id.R3C1_0);
		cardSwapMap.put(R.id.R3C2_1, R.id.R3C2_0);
		cardSwapMap.put(R.id.R3C3_1, R.id.R3C3_0);
	}

	/**
	 ** CardSwapMap contains all the ImageViews mapped to their respective counterparts for easily retrieving animation
	 * partner
	 */
	public SparseIntArray getCardSwapMap() {

		return cardSwapMap;
	}
}
