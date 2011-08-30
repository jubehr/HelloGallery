package org.piconbehr.hellogallery;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class HelloGallery extends Activity {
    private static final String DEBUG_TAG = null;

    private Typeface textFont; 
    private int textColor;
    private float textSize;
    private int maxHeightCriteria;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    final Window win = getWindow();
	    win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    setContentView(R.layout.main);

	    
	    
	   /* GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapterGrid(this));
        
         gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(HelloGallery.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });	
        */
        
	    /*Gallery gallery = (Gallery) findViewById(R.id.gallery);
	    gallery.setAdapter(n
	   
	    
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(HelloGallery.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });*/
	    try {
	    	textFont = Typeface.createFromAsset(getAssets(), "fonts/Zapfino.ttf");
	    }
	    catch (Exception e) {
            Log.e(DEBUG_TAG, "Failed to load font", e);
        }
	    
	    try {
	    //Lecture de la liste des vins dans le XML
	    XmlResourceParser vines = getResources().getXml(R.xml.datavine);
	    
        	//Récupération de la listview créée dans le fichier main.xml
        	TableLayout allVinesTable = (TableLayout) findViewById(R.id.TableLayout_AllVines);
          
          
              processVines(allVinesTable, vines);
              fillRightSide();
              
          } catch (Exception e) {
              Log.e(DEBUG_TAG, "Failed to load vines", e);
          }

	}
	
	private void processVines(final TableLayout vineTable, XmlResourceParser vines)  {
int eventType = -1;
boolean bFoundVines= false;

// Find Score records from XML
while (eventType != XmlResourceParser.END_DOCUMENT) {
    if (eventType == XmlResourceParser.START_TAG) {
        // Get the name of the tag (eg scores or score)
        String strName = vines.getName();
        if (strName.equals("vine")) {
            bFoundVines = true;
            
            String vineImg = vines.getAttributeValue(null, "img");
            String vineDesc = vines.getAttributeValue(null, "design");
            String vinePrice = vines.getAttributeValue(null, "prix");
            insertVineRow(vineTable, vineImg, vineDesc, vinePrice);
        }
    }
    try {
		eventType = vines.next();
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		Log.e(DEBUG_TAG, "Failed to parse vines file", e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		Log.e(DEBUG_TAG, "Failed with vines file", e);
	}
}
// Handle no scores available
if (bFoundVines == false) {
    final TableRow newRow = new TableRow(this);
    TextView noResults = new TextView(this);
    noResults.setText(getResources().getString(R.string.no_vines));
    newRow.addView(noResults);
    vineTable.addView(newRow);
}
}
	 private void insertVineRow(final TableLayout vineTable, String vineImg, String vineDesc, String vinePrice) {
	        final TableRow newRow = new TableRow(this);
	        textColor = getResources().getColor(R.color.title_color);
	        textSize = getResources().getDimension(R.dimen.help_text_size);
	        addImgToRowWithValues(newRow, vineImg, 80);
	        addTextToRowWithValues(newRow, vineDesc,  textColor, textSize);
	        addTextToRowWithValues(newRow, vinePrice,  textColor, textSize);
	        newRow.setPadding(2, 2, 2, 2);
	        
	        vineTable.addView(newRow);
	    }
	 
	 private void addTextToRowWithValues(final TableRow tableRow, String text, int textColor, float textSize) {
	        TextView textView = new TextView(this);
	        textView.setTextSize(textSize);
	        textView.setTextColor(textColor);
	        textView.setTypeface(textFont);
	        textView.setGravity(Gravity.CENTER_VERTICAL);
	        textView.setText(text);
	        tableRow.addView(textView);
	    }
	 
	 private void addImgToRowWithValues(final TableRow tableRow, String res, int imgSize) {
	        ImageView imgView = new ImageView(this);
	        
	        imgView.setImageResource(getResources().getIdentifier(res,"drawable","org.piconbehr.hellogallery"));
	        imgView.setAdjustViewBounds(true);
	        imgView.setMaxHeight(imgSize);
	        imgView.setMaxWidth(imgSize);
	        
	        
	        tableRow.addView(imgView);
	    }
	 
	 private void fillRightSide() {
		 
		 //Initialize parameters
		 maxHeightCriteria = 100;
		 
		 //Set labels
		 textColor = getResources().getColor(R.color.title_color);
	     textSize = getResources().getDimension(R.dimen.help_text_size);
	     TextView tvVolume = (TextView) findViewById(R.id.label_volume);
	     formatText(tvVolume, getResources().getString(R.string.label_volume));
	     TextView tvRobe = (TextView) findViewById(R.id.label_robe);
	     formatText(tvRobe, getResources().getString(R.string.label_robe));
	     TextView tvAccord = (TextView) findViewById(R.id.label_accord);
	     formatText(tvAccord, getResources().getString(R.string.label_accord));
	     TextView tvPrix = (TextView) findViewById(R.id.label_prix);
	     formatText(tvPrix, getResources().getString(R.string.label_prix));
	     
	     
	     //Set criterias
	     //for Volume
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Volume),"verre", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Volume),"pot", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Volume),"bouteille", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Volume),"magnum", 60);
	     
	     //for Robe
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Robe),"vin_blanc", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Robe),"vin_liquoreux", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Robe),"vin_rose", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Robe),"vin_rouge", 60);
	     
	     //for Prix
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Prix),"prix_small", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Prix),"prix_medium", 60);
	     insertCriteriaRow((LinearLayout) findViewById(R.id.TableLayout_Prix),"prix_high", 60);
	 }
	 
	 private void formatText(final TextView textView, String text) {
		 	
	        textView.setTextSize(textSize);
	        textView.setTextColor(textColor);
	        textView.setTypeface(textFont);
	        textView.setGravity(Gravity.CENTER_VERTICAL);
	        textView.setText(text);
	    }
	 
	 private void insertCriteriaRow(final LinearLayout critTable, String critImg, int critSize) {
		 	ImageView imgView = new ImageView(this);
	        
	        imgView.setImageResource(getResources().getIdentifier(critImg,"drawable","org.piconbehr.hellogallery"));
	        imgView.setAdjustViewBounds(true);
	        imgView.setClickable(true);
	        imgView.setMinimumWidth(critSize);
	        imgView.setMaxHeight(maxHeightCriteria);
	        imgView.setMaxWidth(critSize);
	        imgView.setPadding(20, 2, 20, 2);
	        
	        critTable.addView(imgView);
	    }
}
