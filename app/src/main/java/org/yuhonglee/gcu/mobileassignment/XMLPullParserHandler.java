package org.yuhonglee.gcu.mobileassignment;
//Name.Yu Hong Lee
//StD No.S1620580

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.yuhonglee.gcu.mobileassignment.ui.main.currentincidents;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {
    private List<currentincidents> current = new ArrayList<currentincidents>();
    private currentincidents item;
    private String text;

    //declare this way as xml tag for georss:point is declared point(very sneaky!!!)
    static final String GEO_POINT = "point";
    static final String DESCRIPTION = "description";
    static final String TITLE = "title";
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String PUBLISH = "pubDate";


    public List<currentincidents> getItem() {
        return current;
    }

    public List<currentincidents> parse(InputStream is) {
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
                            // create a new instance of employee
                            item = new currentincidents();
                        } else if (item != null){
                            if (tagname.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "TITLE");
                                item.setTitle(parser.nextText());
                            }  else if (tagname.equalsIgnoreCase(DESCRIPTION)) {
                                Log.i("Attribute", "DESCRIPTION");
                                item.setDescription(parser.nextText());
                            }  else if (tagname.equalsIgnoreCase(LINK)) {
                                Log.i("Attribute", "LINK");
                                item.setLink(parser.nextText());
                            } else if (tagname.equalsIgnoreCase(GEO_POINT)) {
                                Log.i("Attribute", "GEO_POINT");
                                item.setStrGeo(parser.nextText());
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
