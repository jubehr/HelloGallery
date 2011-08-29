package org.piconbehr.hellogallery;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class HelloGallery extends Activity {
    private static final String DEBUG_TAG = null;

    private Typeface textFont; 
    private int textColor;
    private float textSize;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
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
	     insertCriteriaRow((TableLayout) findViewById(R.id.TableLayout_Volume),"verre", 60);
	     insertCriteriaRow((TableLayout) findViewById(R.id.TableLayout_Volume),"pot", 60);
	     insertCriteriaRow((TableLayout) findViewById(R.id.TableLayout_Volume),"bouteille", 60);
	     insertCriteriaRow((TableLayout) findViewById(R.id.TableLayout_Volume),"magnum", 60);
	     
	 }
	 
	 private void formatText(final TextView textView, String text) {
		 	
	        textView.setTextSize(textSize);
	        textView.setTextColor(textColor);
	        textView.setTypeface(textFont);
	        textView.setGravity(Gravity.CENTER_VERTICAL);
	        textView.setText(text);
	    }
	 
	 private void insertCriteriaRow(final TableLayout critTable, String critImg, int critSize) {
	        final TableRow newRow = new TableRow(this);
	        addImgToRowWithValues(newRow, critImg, critSize);
	        newRow.setPadding(2, 2, 2, 2);
	        newRow.setOrientation(1); //vertical
	        
	        critTable.addView(newRow);	
	    }
}
