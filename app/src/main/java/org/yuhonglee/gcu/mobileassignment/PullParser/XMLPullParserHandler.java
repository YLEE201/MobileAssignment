package org.yuhonglee.gcu.mobileassignment.PullParser;
//Name.Yu Hong Lee
//StD No.S1620580

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.yuhonglee.gcu.mobileassignment.ListItemAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {
    private List<ListItemAdapter> current = new ArrayList<ListItemAdapter>();
    private ListItemAdapter item;
    private String text;

    //declare this way as the xml tag for georss:point is declared point
    static final String GEO_POINT = "point";
    static final String DESCRIPTION = "description";
    static final String TITLE = "title";
    static final String ITEM = "item";
    static final String PUBLISH = "pubDate";


    public List<ListItemAdapter> getItem() {
        return current;
    }

    //parses the data and stores it in the list adapter class as getters and setters
    public List<ListItemAdapter> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagname = parser.getName();
                        if (tagname.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                            // create a new instance of Items
                            item = new ListItemAdapter();
                        } else if (item != null){
                            if (tagname.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "TITLE");
                                item.setTitle(parser.nextText());

                            } else if (tagname.equalsIgnoreCase(DESCRIPTION)) {
                                Log.i("Attribute", "PUBLISH");
                                item.setDescription(parser.nextText());

                            } else if (tagname.equalsIgnoreCase(GEO_POINT)) {
                                Log.i("Attribute", "GEO_POINT");
                                //splits the string for location into lon and lat and stores in getters and setters
                                String[] textParts = parser.nextText().split("\\s+");
                                item.setLatGeo(Double.parseDouble(textParts[0]));
                                item.setLongGeo(Double.parseDouble(textParts[1]));

                            } else if (tagname.equalsIgnoreCase(PUBLISH)) {
                                Log.i("Attribute", "PUBLISH");
                                item.setPubDate(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagname = parser.getName();
                        Log.i("End tag", tagname);
                        if (tagname.equalsIgnoreCase(ITEM) && item != null) {
                            Log.i("Added", item.toString());
                            current.add(item);
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return current;
    }
}
