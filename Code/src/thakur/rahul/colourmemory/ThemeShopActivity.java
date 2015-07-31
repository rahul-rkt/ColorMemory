
package thakur.rahul.colourmemory;

import thakur.rahul.colourmemory.controller.FragmentController;
import thakur.rahul.colourmemory.model.ThemeModel;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ThemeShopActivity extends Activity {

	private ThemeModel theme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theme_shop);
		theme = ThemeModel.getInstance();
		ImageView button = getButtonByTheme();
		button.setAlpha(0.25f);
		button.setClickable(false);
		for (String t : theme.getAllThemes()) {
			if (theme.isUnlocked(t))
				continue;
			ImageView b = getButtonByTheme(t);
			b.setBackgroundResource(R.drawable.locked_card);
		}
	}

	/**
	 * Returns the button associated with the current theme
	 */
	public ImageView getButtonByTheme() {

		String t = theme.getTheme();
		return getButtonByTheme(t);
	}

	/**
	 * Returns the button associated with the specified theme
	 */
	public ImageView getButtonByTheme(String theme) {

		String buttonId = (theme + "xxx").replaceAll("_xxx", "");
		int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
		ImageView button = (ImageView) findViewById(resId);
		return button;
	}

	public void setTheme(View v) {

		ImageView b = (ImageView) v;
		if (theme.isUnlocked(b.getTag().toString())) {
			//Sets previous theme to clickable
			ImageView prevButton = getButtonByTheme();
			prevButton.setAlpha(1.0f);
			prevButton.setClickable(true);
			//Sets selected theme to unclickable
			String themePrefix = b.getTag().toString();
			theme.setTheme(themePrefix);
			b.setClickable(false);
			b.setAlpha(0.5f);
		} else {
			String req = theme.getThemeUnlockRequirement(b.getTag().toString());
			ViewGroup vg = (ViewGroup) findViewById(R.id.theme_shop_button_layout);
			for (int i = 0; i < vg.getChildCount(); i++)
				vg.getChildAt(i).setEnabled(false);
			new FragmentController(this).showPopUpMessage(R.id.fragment_container_pop_up_message, req);
		}
	}
}
