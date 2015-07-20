package thakur.rahul.colourmemory;

import thakur.rahul.colourmemory.controller.FragmentController;
import thakur.rahul.colourmemory.model.ThemeModel;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ThemeShopActivity extends Activity {

	private ThemeModel theme;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theme_shop);
		
		theme = ThemeModel.getInstance();
		Button button = getButtonByTheme();
		button.setAlpha(0.5f);
		button.setClickable(false);
		
		for( String t: theme.getAllThemes() ) {
			if( theme.isUnlocked(t) ) continue;
			Button b = getButtonByTheme(t);
			b.setBackgroundResource(R.drawable.locked_card);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.theme_shop, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Returns the button associated with the current theme
	 */
	public Button getButtonByTheme() {
		String t = theme.getTheme();
		return getButtonByTheme(t);
		
	}
	
	/**
	 * Returns the button associated with the specified theme
	 */
	public Button getButtonByTheme(String theme) {
		String buttonId= (theme+"xxx").replaceAll("_xxx", "");
		int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
		Button button = (Button)findViewById(resId);
		return button;
		
	}
	
	public void setTheme(View v) {
		
		Button b = (Button)v; 
		
		if( theme.isUnlocked(b.getText().toString())) {
			//Sets previous theme to clickable
			Button prevButton = getButtonByTheme();
			prevButton.setAlpha(1.0f);
			prevButton.setClickable(true);
			
			//Sets selected theme to unclickable
			String themePrefix = b.getText().toString();
			theme.setTheme(themePrefix);
			b.setClickable(false);
			b.setAlpha(0.5f);
		} else {
			String req = theme.getThemeUnlockRequirement(b.getText().toString());
			//TODO: show a popup stating the requirements for theme to unlock
			ViewGroup vg = (ViewGroup)findViewById(R.id.theme_shop_button_layout);
			for( int i = 0; i < vg.getChildCount(); i++ ) {
				vg.getChildAt(i).setEnabled(false);
			}
			new FragmentController(this).showPopUpMessage(R.id.fragment_container_pop_up_message, req );
			
		}
	}
	
	
}
