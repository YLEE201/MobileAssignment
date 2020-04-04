package org.yuhonglee.gcu.mobileassignment;
//Name.Yu Hong Lee
//StD No.S1620580

//note; this is the same as the other pull parser except for parsing of description
//This is because it is not possible to do a conditional statement for
//the different descriptions between roadworks and current incidents as
//it always returns a empty list when using an if statement,
//This was to achieve the string separation of time for one of the color functions, code tried:
//if(parser.nextText().contains("br")){
//}

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.yuhonglee.gcu.mobileassignment.ui.main.ListItemAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class XMLPullParserRoadworks {
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

                            }  else if (tagname.equalsIgnoreCase(DESCRIPTION)) {

                                String Desc = "";
                                String textWhole1 = "";
                                String textWhole2 = "";
                                String textWhole3 = "";
                                Desc = parser.nextText();

                                //for road works and planned road works removes embedded br tag and splits them into
                                //start time, end time and the delay/ work string

                                Desc = Desc.replaceAll("<br />", " ");
                                String[] textParts = Desc.split("\\s+");

                                int numberOfItems1 = 8;
                                StringBuilder sb1 = new StringBuilder();

                                for (int i = 2; i < numberOfItems1; i++) {
                                    sb1.append(textParts[i] + " ");
                                }

                                int numberOfItems2 = 16;
                                StringBuilder sb2 = new StringBuilder();

                                for (int i = 10; i < numberOfItems2; i++) {
                                    sb2.append(textParts[i] + " ");
                                }

                                int numberOfItems3 = textParts.length;
                                StringBuilder sb3 = new StringBuilder();

                                for (int i = 16; i < numberOfItems3; i++) {
                                    sb3.append(textParts[i] + " ");
                                }

                                textWhole1 = sb1.toString();
                                textWhole2 = sb2.toString();
                                textWhole3 = sb3.toString();

                                DateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy - hh:mm", Locale.ENGLISH);
                                Date date1 = format.parse(textWhole1);
                                Date date2 = format.parse(textWhole2);

                                item.setStartime(date1);
                                item.setEndtime(date2);
                                item.setDelay(textWhole3);

                                item.setDifference(ListItemAdapter.computeDiff(date1,date2).toString());


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
        catch (IOException e) {e.printStackTrace();} catch (ParseException e) {
            e.printStackTrace();
        }

        return current;
    }
}
