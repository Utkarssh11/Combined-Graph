package com.elegantjbi.amcharts;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;

import com.elegantjbi.core.olap.ICubeResultSetSupport;
import com.elegantjbi.entity.graph.GraphInfo;
import com.elegantjbi.service.graph.GraphConstants;
import com.elegantjbi.service.kpi.KPIConstants;
import com.elegantjbi.util.AppConstants;
import com.elegantjbi.util.GeneralFiltersUtil;
import com.elegantjbi.util.ResourceManager;
import com.elegantjbi.util.StringUtil;
import com.elegantjbi.util.logger.ApplicationLog;
import com.elegantjbi.vo.properties.graph.CombinedDataValueProperties;
import com.elegantjbi.vo.properties.kpi.TrendLineProperties;

public class AmCombineDataProvider {

	public static List<Map<String, Object>> dataProviderJson(GraphInfo graphInfo,int startIndex,int Quantity,int categoryIndex,int categoryQuantity)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		if(Quantity == -1) {
			Quantity = 100000;
		}
		if(categoryQuantity == -1) {
			categoryQuantity = 100000;
		}
		List colorWiseIndex = new ArrayList<>();
		List colorWiseLineIndex = new ArrayList<>();
		String barColLable=graphInfo.getGraphData().getCmbBarcolLabel();
		 String lineColLable=graphInfo.getGraphData().getCmbLinecolLabel();
		 String barDataLable=graphInfo.getGraphData().getCmbBardataLabel();
		 String lineDataLabel=graphInfo.getGraphData().getCmbLinedataLabel();
		 String barRowLabel=graphInfo.getGraphData().getCmbBarrowLabel();
		 String lineRowLabel=graphInfo.getGraphData().getCmbLinerowLabel();
		 boolean sameRowflag=false; 
		 
		 
		 if(barRowLabel!=null && lineRowLabel!=null && barRowLabel.equals(lineRowLabel))
		 {
			 sameRowflag=true; 
		 }
		 
		 List dateColList = graphInfo.getGraphData().getDatecmbcolList();
		 List dateBarRowList = graphInfo.getGraphData().getDatecmbBarrowList();
		 List dateLineRowList = graphInfo.getGraphData().getDatecmbLinerowList();
		 List barcolList=graphInfo.getGraphData().getCmbBarcolList();
		 List linecolList=graphInfo.getGraphData().getCmbLinecolList();
		 List bardataList=graphInfo.getGraphData().getCmbBardataList();
		 List linedataList=graphInfo.getGraphData().getCmbLinedataList();
		 List barrowList=graphInfo.getGraphData().getCmbBarrowList();
		 List linerowList=graphInfo.getGraphData().getCmbLinerowList();
		 //Line data Label
		 
		 List barcolList2 = new ArrayList();
		 barcolList2.addAll(barcolList);
		 List truncatedColList = new ArrayList();
		 truncatedColList.addAll(barcolList);
		 String truncatedLabel = "truncatedLabel";
		 String colLabelNew = "";
		 
		 int truncateCharLimit = 15;
		 boolean isCharacterLimitNone = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit().equalsIgnoreCase("none"); 
		if(barcolList != null && !isCharacterLimitNone)
		{
			String array_element;
			for (int i = 0; i < barcolList.size(); i++) {
				array_element = barcolList.get(i).toString();

				switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
				{
				case "auto":
					truncateCharLimit = 15;
					break;
				case "custom":
					truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
					break;
				}
				if (array_element.length() > truncateCharLimit)
					array_element = array_element.substring(0, truncateCharLimit)+"..";
				
				barcolList2.set(i, array_element);
			}
		}
		 
		 int barrowListsize=barrowList.size();
		 int linerowListsize=linerowList.size();
		 int barcolListsize=barcolList.size();
		 boolean isLegendVisible = true;
		 boolean isBarLegendVisible = true;
		 boolean isLineLegendVisible = true;
		
		 String drillAxis = "drillAxis";
		 String drillBarLegend = "drillBarLegend";
		 String drillLineLegend = "drillLineLegend";
		 List drillBarList = graphInfo.getGraphData().getDrillBarLinkList();
		 List drillLineList = graphInfo.getGraphData().getDrillLineLinkList();
		 List drillcolList = graphInfo.getGraphData().getDrillColLinkList();
		 
		 int nullSize = barcolListsize*barrowListsize;
			
		if(drillcolList.isEmpty() || nullSize > graphInfo.getGraphData().getDrillColLinkList().size())
		{
			for(int i=0;i<nullSize;i++)
			{
				drillcolList.add("null");
			}
		}
		if(drillBarList.isEmpty() || nullSize > graphInfo.getGraphData().getDrillBarLinkList().size())
		{
			for(int i=0;i<nullSize;i++)
			{
				drillBarList.add("null");
			}
		}
		if(drillLineList.isEmpty() || nullSize > graphInfo.getGraphData().getDrillLineLinkList().size())
		{
			for(int i=0;i<nullSize;i++)
			{
				drillLineList.add("null");
			}
		}
		
		
		
		 if(barrowListsize == 0){
			 barrowListsize = 1;
				isLegendVisible =  false;
				isBarLegendVisible=false;
			}else{isLegendVisible=true;}		
		 if(linerowListsize == 0){
			 linerowListsize = 1;
				isLegendVisible =  false;
				isLineLegendVisible=  false;				
			}
						 
		
		/* String[] barColor =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"
				 						,"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		*///String[] barColor =new String[]{"rgb(141,170,203)","rgb(252,115,98)","rgb(187,216,84)","rgb(255,217,47)","rgb(102,194,150)","rgb(255, 148, 10)","rgb(148, 247, 244)"};
		 
		 String[] barColor =new String[]{"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
				    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
				    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
				    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
				    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
				    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
				    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 
		switch(graphInfo.getGraphProperties().getColorType())
		{
		case 1:
			if(graphInfo.getGraphProperties().getCustomColors() != null)
			{
				for (int i = 0; i < graphInfo.getGraphProperties().getCustomColors().size(); i++) {
					if(i > (barColor.length-1))
					{
						barColor = appendValue(barColor, graphInfo.getGraphProperties().getCustomColors().get(i));
					}
					else
					{	
					barColor[i] = graphInfo.getGraphProperties().getCustomColors().get(i);
					}
				}
			}
			break;
		case 2:
			barColor = new String[]{graphInfo.getGraphProperties().getColor()};
			break;
		}
		
		List<Map<String, Object>> dpList =  new ArrayList<Map<String,Object>>();
		
		String X="x";
		String Y="y";
		String Value="value";
		String drillValue = null;
		int count=1;
			
	
		//Trend Line line Start
		 Map trendLineMap = graphInfo.getGraphData().getTrendMap();
		 boolean isTrend =false;
		 int trendCount;

		 int noOfTrendLines = 0;
		 if(trendLineMap != null)
			 noOfTrendLines = trendLineMap.size(); 
		 List trendColor = new ArrayList();
		 List trendLineName = new ArrayList();
		 List trendValues = new ArrayList();
		 List trendLineColoumn = new ArrayList();
		 List trendAlgoList = new ArrayList();
		 if(noOfTrendLines > 0)
		 {
			 isTrend=true;
			 trendCount = noOfTrendLines;

			 Map<Integer, TrendLineProperties> testMap = graphInfo.getGraphProperties().getLinetrendlinePropertiesMap();
			 for (Entry<Integer, TrendLineProperties> entry : testMap.entrySet()) {
				 trendColor.add(entry.getValue().getTrendLineColor());
				 trendLineName.add(entry.getValue().getTrendLineName());//Name of the trend Line given by the user
				 trendLineColoumn.add(entry.getValue().getTrendLineColumn());
				 trendAlgoList.add(entry.getValue().getTrendLineType().toString());
			 }
		 }
		//trend Line line End
		 
		 
		 //Trend line bar start
		 Map trendBarMap = graphInfo.getGraphData().getTrendCmbBarMap();
		 boolean isBarTrend =false;
		 int trendBarCount;

		 int noOfBarTrendLines = 0;
		 if(trendBarMap != null)
			 noOfBarTrendLines = trendBarMap.size(); 
		 List trendBarColor = new ArrayList();
		 List trendBarLineName = new ArrayList();
		 List trendBarValues = new ArrayList();
		 List trendBarLineColoumn = new ArrayList();
		 List trendBarAlgoList = new ArrayList();
		 if(noOfBarTrendLines > 0)
		 {
			 isBarTrend=true;
			 trendBarCount = noOfBarTrendLines;

			 Map<Integer, TrendLineProperties> testBarMap = graphInfo.getGraphProperties().getBartrendlinePropertiesMap();
			 for (Entry<Integer, TrendLineProperties> entry : testBarMap.entrySet()) {
				 trendBarColor.add(entry.getValue().getTrendLineColor());
				 trendBarLineName.add(entry.getValue().getTrendLineName());//Name of the trend Line given by the user
				 trendBarLineColoumn.add(entry.getValue().getTrendLineColumn());
				 trendBarAlgoList.add(entry.getValue().getTrendLineType().toString());
			 }
		 }
		 
		 
		 //trend line bar end

		 
		 double maxValue=0.0;
		 double customBarMax=0.0;
		 double customLineMax=0.0;
		 boolean barflag=false;//if true it will set all the data values greater to customMax equal to customMax
		 boolean lineflag=false;//if true it will set all the data values greater to customMax equal to customMax
		 //When we provide custom max value this will set flag to true.
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxValType() == 1)
		 {
			 customBarMax =Double.parseDouble(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxCustomVal());
			 barflag=true;
					
			 
				
				
		 }
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxValType() == 1)
		 {
			 customLineMax =Double.parseDouble(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxCustomVal());
			 lineflag=true;			 
		 }
		 
		 
		 if(categoryIndex > barcolListsize)
				categoryIndex=barcolListsize;
			
		 
		 	
			boolean legendbarflag=false;
			boolean legendlineflag=false;
			int startIndexcolne=startIndex;	
			int paginationIndex=startIndex+Quantity;
			
			int categorypaginationIndex=categoryIndex+categoryQuantity;
			
			
			if(categorypaginationIndex > barcolListsize)
				categorypaginationIndex=barcolListsize;
			
			int paginationBarIndex=0;
			int paginationLineIndex=0;
			
			 paginationBarIndex=startIndex+Quantity;
			 paginationLineIndex=startIndex+Quantity;
			
			if(paginationBarIndex > barrowListsize)
			{
				legendbarflag=true;
				paginationBarIndex=barrowListsize-1;
			}	
		 
			if(paginationLineIndex > linerowListsize)
			{
				legendlineflag=true;
				paginationLineIndex=linerowListsize-1;
			}
		 
		 
		 
		 
		
		 int barStartIndex=startIndex;
		 int lineStartIndex=startIndex;		 
		 int k=0;
		 int l=0;
		 int m=0;
		 int n=0;
		 
		 
		 
		 
		 int drillIndex=0;

		//performance changes start
		String barMapKeyStr = "";
		String lineMapKeyStr = "";
		Double barDataValue = 0.0;
		Double lineDataValue = 0.0;

		Map<String, Double> barKeyValueMap = graphInfo.getGraphData().getKeyValueMap();
		Map<String, Double> lineKeyValueMap = graphInfo.getGraphData().getKeyValueMapLineCmb();
//		int barAdjustedDigit = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getAdjustedDigit();
//		//graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit();
//		Double d = new Double(barAdjustedDigit);
//		int barDivValue = (int) (Math.pow(10, d));
		int barAdjustedDigit = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getAdjustedDigit();
		int barDivValue = 1; // Default No Division
		if (!graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isAutovalue()) {
		    barDivValue = (int) (Math.pow(10, barAdjustedDigit)); // Divide only if Auto is OFF
		}
		boolean barMultipleMeasure = graphInfo.getDataColLabels3().size() > 1 && barRowLabel != null && (barRowLabel.equalsIgnoreCase("Legend") || barRowLabel.equalsIgnoreCase(barColLable));
		//Line
//		int lineAdjustedDigit = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getAdjustedDigit();
//		//graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit();
//		Double d2 = new Double(lineAdjustedDigit);
//		int lineDivValue = (int) (Math.pow(10, d2));
		int lineAdjustedDigit = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getAdjustedDigit();
		int lineDivValue = 1; // Default No Division
		if (!graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isAutovalue()) {
		    lineDivValue = (int) (Math.pow(10, lineAdjustedDigit)); // Divide only if Auto is OFF
		}
		boolean lineMultipleMeasure = graphInfo.getChangedTheDataColLabels4()!=null && graphInfo.getChangedTheDataColLabels4().size() > 1 && lineRowLabel != null && lineRowLabel.equalsIgnoreCase("Legend");
		//performance changes end

		 for (int i = 0; i < barcolListsize; i++) {
			 
				 
			 Map<String, Object> dpMap =  new HashMap<String, Object>();			 
			 Map<String, String> drillBarMap =new HashMap<String, String>();
			 Map<String, String> drillLineMap =new HashMap<String, String>();
			 Map<String, Object> barMap =new HashMap<String, Object>();
			 Map<String, Object> lineMap =new HashMap<String, Object>();

			 if(i+1==barcolListsize)
				 dpMap.put("colLastPage", "1");
			 if(i==categoryIndex)
				{				 
				
				 if( (!dateBarRowList.isEmpty() && (null == barRowLabel || (null != barRowLabel && "Legend".equals(barRowLabel)) || barRowLabel.equals(barColLable)))
						 || !dateColList.isEmpty() )//Added for Bug #15195 start (reflecting #13406 changes)
				 {					
					 String stringFormat;
					 stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
					 stringFormat = stringFormat.replaceAll("&#39;", "'");
					 Calendar cal = Calendar.getInstance();
					 Date axisDate = null;
					 if(!dateColList.isEmpty()) {
						 if(dateColList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
							 colLabelNew = dateColList.get(i).toString();
						 else	
							 axisDate = (Date) dateColList.get(i);
					 } else {
						 if(!dateBarRowList.isEmpty())
						 {
							 if(dateBarRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
								 colLabelNew = dateBarRowList.get(i).toString();
							 else
								 axisDate = (Date) dateBarRowList.get(i);
						 }
					 }
					 if(axisDate != null)
					 {
						 cal.setTime(axisDate);
						 stringFormat=stringFormat.trim();
						 colLabelNew = new SimpleDateFormat(stringFormat).format(cal.getTime());
					 }
					 dpMap.put(truncatedLabel, colLabelNew);
					 if(!isCharacterLimitNone && (colLabelNew.length() > truncateCharLimit))
						 colLabelNew = colLabelNew.substring(0, truncateCharLimit)+"..";
					 dpMap.put(barColLable, colLabelNew);
				 }//Added for Bug #15195 end
				 else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
						Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
						String strData = barcolList.get(i).toString();
						if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
							if(null !=barrowList && !barrowList.isEmpty())
								strData = barrowList.get(i).toString();
						}
						String frequency = "";
						if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
							String strCol = graphInfo.getColColumns().elementAt(0).toString();
							if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
									&& !dateFrequencyMap.get(strCol).isEmpty()) {
								frequency = dateFrequencyMap.get(strCol);
								strData = barcolList.get(i).toString();
								
							}
						}
						if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
							String strCol = graphInfo.getRowColumns().elementAt(0).toString();
							if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
									&& !dateFrequencyMap.get(strCol).isEmpty()) {
								frequency = dateFrequencyMap.get(strCol);
								/*if(null !=barrowList && !barrowList.isEmpty())
								strData = barrowList.get(i).toString();*/
							}
						}
						if(frequency != null && !frequency.isEmpty() ) {
							if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
							
								String stringFormat;
								stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat();
								stringFormat = stringFormat.replaceAll("&#39;", "'");
								Calendar cal = Calendar.getInstance();
								
								
								int iColumnType = 0;
											
											try {
												
												if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString())  && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())
													iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getColColumns().elementAt(0).toString());
												else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
													iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getRowColumns().elementAt(0).toString());
												
											} catch (Exception e) {
												ApplicationLog.error(e);
											}
											
											
											
											if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())			
												strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getColColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
											else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
												strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getRowColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
											if(barcolList != null && !isCharacterLimitNone)
											{
												
													switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
													{
													case "auto":
														truncateCharLimit = 15;
														break;
													case "custom":
														truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
														break;
													}
													if (strData.length() > truncateCharLimit)
														strData = strData.substring(0, truncateCharLimit)+"..";
													
												
											}

											
											
								dpMap.put(barColLable, strData);
								dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
								
							}
							else {
								dpMap.put(barColLable, barcolList.get(i).toString());
								dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
							}
						
						}
						else {
							dpMap.put(barColLable, barcolList.get(i).toString());
							dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
						}
					}

				 else {
					 dpMap.put(barColLable, barcolList2.get(i).toString());
					 dpMap.put(truncatedLabel, truncatedColList.get(i).toString());
				 }
				 drillIndex = i;
				 	if(isBarLegendVisible)
				 	{
				 		drillBarMap = new HashMap<String, String>();
				 	}
				 	 if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isVisible())
					 {	
						 if(drillcolList.size() > drillIndex  && !drillcolList.isEmpty() && drillcolList.size() > 0)//12113(show preview internal))
							 dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());
					 }
				
			 for (int j = 0; j < barrowListsize; j++) {
				 if(j==startIndex){
					 
					//performance changes
					 barMapKeyStr = "";
						if(null != barcolList && barcolList.size() > 0)
							barMapKeyStr += barcolList.get(i).toString();
						if(null != barrowList && barrowList.size() > 0)
							barMapKeyStr += barrowList.get(j).toString();
						if(!barMultipleMeasure && !barMapKeyStr.contains(graphInfo.getDataColLabels3().get(0).toString()))
							barMapKeyStr += graphInfo.getDataColLabels3().get(0).toString();
				 if(barKeyValueMap.get(barMapKeyStr)!=null ){	
				 
					 
					 if(paginationBarIndex==startIndex &&  !legendbarflag)
						{
						 
							dpMap.putAll(barMap);

							 if(!dpMap.containsKey(barColLable))
							 {
								 if( (!dateBarRowList.isEmpty() && (null == barRowLabel || (null != barRowLabel && "Legend".equals(barRowLabel)) || barRowLabel.equals(barColLable)))
										 || !dateColList.isEmpty() )//Added for Bug #15195 start (reflecting #13406 changes)
								 {
									 String stringFormat;
									 stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
									 stringFormat = stringFormat.replaceAll("&#39;", "'");
									 Calendar cal = Calendar.getInstance();
									 
									 Date axisDate = null;
									 if(!dateColList.isEmpty()) {
										 if(dateColList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
											 colLabelNew = dateColList.get(i).toString();
										 else	
											 axisDate = (Date) dateColList.get(i);
									 } else {
										 if(!dateBarRowList.isEmpty())
										 {
											 if(dateBarRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
												 colLabelNew = dateBarRowList.get(i).toString();
											 else
												 axisDate = (Date) dateBarRowList.get(i);
										 }
									 }
									 if(axisDate != null)
									 {
										 cal.setTime(axisDate);
										 stringFormat=stringFormat.trim();
										 colLabelNew = new SimpleDateFormat(stringFormat).format(cal.getTime());
									 }
									 
									 dpMap.put(truncatedLabel, colLabelNew);
									 if(!isCharacterLimitNone && (colLabelNew.length() > truncateCharLimit))
										 colLabelNew = colLabelNew.substring(0, truncateCharLimit)+"..";
									 dpMap.put(barColLable, colLabelNew);
								 }//Added for Bug #15195 end
								 else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
									 Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
										String strData = barcolList.get(i).toString();
										String frequency = "";
										if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
											String strCol = graphInfo.getColColumns().elementAt(0).toString();
											if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
													&& !dateFrequencyMap.get(strCol).isEmpty()) {
												frequency = dateFrequencyMap.get(strCol);
												strData = barcolList.get(i).toString();
												
											}
										}
										if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
											String strCol = graphInfo.getRowColumns().elementAt(0).toString();
											if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
													&& !dateFrequencyMap.get(strCol).isEmpty()) {
												frequency = dateFrequencyMap.get(strCol);
												strData = barrowList.get(i).toString();
											}
										}
										if(frequency != null && !frequency.isEmpty() ) {
											if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
											
												String stringFormat;
												stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat();
												stringFormat = stringFormat.replaceAll("&#39;", "'");
												Calendar cal = Calendar.getInstance();
												
												
												int iColumnType = 0;
															
															try {
																
																if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString())  && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())
																	iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getColColumns().elementAt(0).toString());
																else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																	iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getRowColumns().elementAt(0).toString());
																
															} catch (Exception e) {
																ApplicationLog.error(e);
															}
															
															
															
															if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())			
																strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getColColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
															else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getRowColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
												dpMap.put(barColLable, strData);
												if(barcolList != null && !isCharacterLimitNone)
												{
													
														switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
														{
														case "auto":
															truncateCharLimit = 15;
															break;
														case "custom":
															truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
															break;
														}
														if (strData.length() > truncateCharLimit)
															strData = strData.substring(0, truncateCharLimit)+"..";
														
													
												}

												
									
												dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
												
											}
											else {
												dpMap.put(barColLable, barcolList.get(i).toString());
												dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
											}
										
										}
										else {
											dpMap.put(barColLable, barcolList.get(i).toString());
											dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
										}
									}

								 else {
									 dpMap.put(barColLable, barcolList2.get(i).toString());
									 dpMap.put(truncatedLabel, truncatedColList.get(i).toString());
								 }
							  }
							 if(!dpMap.containsKey(drillAxis)  && !drillcolList.isEmpty() && drillcolList.size() > 0)//12113(show preview internal))
							 {
									dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());
							    		 
							 }
							 if(j==barrowListsize-1)									
									dpMap.put("rowLastPage", 1);										
								startIndex=startIndexcolne;
								//dpList.add(dpMap);
								
							 break;
							
						}
					 
					 
				 String label = null;

				 if(isBarLegendVisible){
					 label = barrowList.get(j).toString();
					 drillBarMap.put(barrowList.get(j).toString(), drillBarList.get(j).toString());
					 //Added for bug no 11496
					 if(barrowList.size() >0)
					 {
						 if((graphInfo.getGraphData().getCmbBarrowLabel() != null || (graphInfo.getGraphData().getCmbBarcolLabel() != null && "Legend".equalsIgnoreCase(graphInfo.getGraphData().getCmbBarrowLabel())))
									&& graphInfo.getDataColLabels3().size() == 1)
						 {
							 barMap.put("color", barColor[graphInfo.getCmbBarColorInfoList().get(j)%barColor.length]);
							 colorWiseIndex.add(graphInfo.getCmbBarColorInfoList().get(j)%barColor.length);
							 //barMap.put("color", barColor[graphInfo.getCmbBarColorInfoList().get(i)%barColor.length]);
						 }
					 }//Added for bug no 11496 end
				 }else{
					 label = graphInfo.getGraphData().getCmbBardataLabel();
					 if(barrowList.size() >0)
					 {
						 barMap.put("color", barColor[graphInfo.getCmbBarColorInfoList().get(j)%barColor.length]);
						 colorWiseIndex.add(graphInfo.getCmbBarColorInfoList().get(j)%barColor.length);
					 }
					 else
					 {
						 barMap.put("color", barColor[graphInfo.getCmbBarColorInfoList().get(i)%barColor.length]);
						 colorWiseIndex.add(graphInfo.getCmbBarColorInfoList().get(i)%barColor.length);
					 }
				 }
				 
				 barDataValue = barKeyValueMap.get(barMapKeyStr) / barDivValue;//performance changes
				 
				 //Changes bar values to customMaxValue if flag is true
				 if(barflag)
				 {
					 if(barDataValue > customBarMax)
					 {
						 barDataValue = customBarMax;
					 }
				 }
				 if(sameRowflag)
				 {
					 label=label+barDataLable;
					 barMap.put(label, barDataValue);
				 }
				 else	 
				 barMap.put(label, barDataValue);
				 
				//commaseparator start
                 double parseValue = barDataValue;
                 
                 int precision = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getNumberOfDigits();
                 String digitsAfterDecimalString="";
                 switch(precision)
                 {
                 case 0:
                 	digitsAfterDecimalString="#.";
                     break;
                 case 1:
                 	digitsAfterDecimalString="#.0";
                     break;
                 case 2:
                 	digitsAfterDecimalString="#.00";
                     break;
                 case 3:
                 	digitsAfterDecimalString="#.000";
                     break;
                 case 4:
                 	digitsAfterDecimalString="#.0000";
                     break;
                 case 5:
                 	digitsAfterDecimalString="#.00000";
                     break;
                 }

                 String commaSeparated = commaFormatsForBar(parseValue, graphInfo.getGraphProperties().getCombinedDataValueProperties(), graphInfo);
                 dpMap.put("Abs"+j+label.replaceAll("[^\\s\\w]*",""), commaSeparated);
                 barMap.put("barXAxisTitle", StringUtil.replaceSpecialCharWithHTMLEntity(graphInfo.getGraphData().getCmbBarcolLabel()));
                 String barDataLabel = graphInfo.getGraphData().getCmbBardataLabel();
             	if(barDataLabel != null && (barDataLabel.equalsIgnoreCase("null") || barDataLabel.equalsIgnoreCase("Data")))
				{
					if(graphInfo.getDataColLabels3() != null && graphInfo.getDataColLabels3().size() >= barrowListsize)
						barMap.put("barYAxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getGraphData().getTempBarLabelsList().get(j).toString()));
					else
						barMap.put("barYAxisTitle"+graphInfo.getChangedDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getChangedDataColLabels3().get(0).toString()));
				}
				else
				{
					if(graphInfo.getGraphData().getColLabelsName() != null && graphInfo.getGraphData().getColLabelsName().size()>0)
						barMap.put("barYAxisTitle"+graphInfo.getDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getGraphData().getColLabelsName().get(0).toString()));
					else
						barMap.put("barYAxisTitle"+graphInfo.getDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(barDataLabel));
				}
             	
                 
				 if(isBarLegendVisible){
					 barMap.put(drillBarLegend, drillBarMap);
				 }
				 if(paginationBarIndex==startIndex && legendbarflag)
					{
					 
						dpMap.putAll(barMap);

						 if(!dpMap.containsKey(barColLable))
						 {
							 if( (!dateBarRowList.isEmpty() && (null == barRowLabel || (null != barRowLabel && "Legend".equals(barRowLabel)) || barRowLabel.equals(barColLable)))
									 || !dateColList.isEmpty() )//Added for Bug #15195 start (reflecting #13406 changes)
							 {						
								 String stringFormat;
								 stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
								 stringFormat = stringFormat.replaceAll("&#39;", "'");
								 Calendar cal = Calendar.getInstance();
								 
								 Date axisDate = null;
								 if(!dateColList.isEmpty()) {
									 if(dateColList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
										 colLabelNew = dateColList.get(i).toString();
									 else	
										 axisDate = (Date) dateColList.get(i);
								 } else {
									 if(!dateBarRowList.isEmpty())
									 {
										 if(dateBarRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
											 colLabelNew = dateBarRowList.get(i).toString();
										 else
											 axisDate = (Date) dateBarRowList.get(i);
									 }
								 }
								 if(axisDate != null)
								 {
									 cal.setTime(axisDate);
									 stringFormat=stringFormat.trim();
									 colLabelNew = new SimpleDateFormat(stringFormat).format(cal.getTime());
								 }
								 
								 dpMap.put(truncatedLabel, colLabelNew);
								 if(!isCharacterLimitNone && (colLabelNew.length() > truncateCharLimit))
									 colLabelNew = colLabelNew.substring(0, truncateCharLimit)+"..";
								 dpMap.put(barColLable, colLabelNew);
							 }//Added for Bug #15195 end
							 else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
								 Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
									String strData = barcolList.get(i).toString();
									String frequency = "";
									if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
										String strCol = graphInfo.getColColumns().elementAt(0).toString();
										if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
												&& !dateFrequencyMap.get(strCol).isEmpty()) {
											frequency = dateFrequencyMap.get(strCol);
											strData = barcolList.get(i).toString();
											
										}
									}
									if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
										String strCol = graphInfo.getRowColumns().elementAt(0).toString();
										if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
												&& !dateFrequencyMap.get(strCol).isEmpty()) {
											frequency = dateFrequencyMap.get(strCol);
											strData = barrowList.get(i).toString();
										}
									}
									if(frequency != null && !frequency.isEmpty() ) {
										if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
										
											String stringFormat;
											stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat();
											stringFormat = stringFormat.replaceAll("&#39;", "'");
											Calendar cal = Calendar.getInstance();
											
											
											int iColumnType = 0;
														
														try {
															
															if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString())  && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())
																iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getColColumns().elementAt(0).toString());
															else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getRowColumns().elementAt(0).toString());
															
														} catch (Exception e) {
															ApplicationLog.error(e);
														}
														
														
														
														if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())			
															strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getColColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
														else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
															strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getRowColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
											
														if(barcolList != null && !isCharacterLimitNone)
														{
															
																switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
																{
																case "auto":
																	truncateCharLimit = 15;
																	break;
																case "custom":
																	truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
																	break;
																}
																if (strData.length() > truncateCharLimit)
																	strData = strData.substring(0, truncateCharLimit)+"..";
																
															
														}

														
											
											dpMap.put(barColLable, strData);
											
											dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
											
										}
										else {
											dpMap.put(barColLable, barcolList.get(i).toString());
											dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
										}
									
									}
									else {
										dpMap.put(barColLable, barcolList.get(i).toString());
										dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
									}
								}

							 else {
								 dpMap.put(barColLable, barcolList2.get(i).toString());
								 dpMap.put(truncatedLabel, truncatedColList.get(i).toString());
							 }
						  }
						 if(!dpMap.containsKey(drillAxis) && !drillcolList.isEmpty() && drillcolList.size() > 0)//12113(show preview internal)
						 {
								dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());
						    		 
						 }
						 if(j==barrowListsize-1)									
								dpMap.put("rowLastPage", 1);										
							startIndex=startIndexcolne;
							//dpList.add(dpMap);
							
						 break;
						
					}
				 startIndex++;
				 }else{
					 if(startIndex==paginationBarIndex)
					 {
						 if(j==barrowListsize-1)									
							dpMap.put("rowLastPage", 1);
							startIndex=startIndexcolne;
							break;
					 }
					 startIndex++;	
				 }				
			 }
			
			
			}
			 if(isTrend)//Trend Line
			 {
				 String label;

				 for(int c=0;c<noOfTrendLines;c++)
				 {
					 String[] splitString = trendLineColoumn.get(c).toString().split(",");
					 String key = (splitString[0])+(String)trendAlgoList.get(c);
					 List temp=(ArrayList)(trendLineMap.get(key));//fetch the coloumn given by the user
					 label=trendLineName.get(c).toString();
					 if(temp.get(i)!=null)
						 dpMap.put(label+"trendLine", temp.get(i));
					 if(i==0)
						 dpMap.put("TrendLabel"+c, label);
				 }

			 } 
			 if(isBarTrend)//Trend Line
			 {
				 String label;

				 for(int c=0;c<noOfBarTrendLines;c++)
				 {
					 String[] splitString = trendBarLineColoumn.get(c).toString().split(",");
					 String key = (splitString[0])+(String)trendBarAlgoList.get(c);
					 List temp=(ArrayList)(trendBarMap.get(key));//fetch the coloumn given by the user
					 label=trendBarLineName.get(c).toString();
					 if(temp.get(i)!=null)
						 dpMap.put(label+"trendBar", temp.get(i));
					 if(i==0)
						 dpMap.put("TrendLabelBar"+c, label);
				 }

			 } 
			
		 
			// dpMap =  new HashMap<String, Object>();
			 /*if(dataLineList.size() > 0)
			 {*/
				 for (int j = 0; j < linerowListsize; j++) {
					 if(j==startIndex){	
						 
						//performance changes
						 lineMapKeyStr = "";
							if(null != linecolList && linecolList.size() > 0)
								lineMapKeyStr += linecolList.get(i).toString();
							if(null != linerowList && linerowList.size() > 0 && !lineMapKeyStr.contains(linerowList.get(j).toString()))
								lineMapKeyStr += linerowList.get(j).toString();
							if(!lineMultipleMeasure && null != graphInfo.getTheDataColLabels4() && graphInfo.getTheDataColLabels4().size() > 0 && !lineMapKeyStr.contains(graphInfo.getTheDataColLabels4().get(0).toString()))
								lineMapKeyStr += graphInfo.getTheDataColLabels4().get(0).toString();
						 if(lineKeyValueMap.get(lineMapKeyStr)!=null ){	


							 if(paginationLineIndex==startIndex && !legendlineflag)
							 {

								 if(!dpMap.containsKey(barColLable))
								 {
									 if( (!dateBarRowList.isEmpty() && (null == barRowLabel || (null != barRowLabel && "Legend".equals(barRowLabel)) || barRowLabel.equals(barColLable)))
											 || !dateColList.isEmpty() )//Added for Bug #15195 start (reflecting #13406 changes)
									 {
										 String stringFormat;
										 stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
										 stringFormat = stringFormat.replaceAll("&#39;", "'");
										 Calendar cal = Calendar.getInstance();
										 Date axisDate = new Date();
										 if(!dateColList.isEmpty()) {
											 axisDate = (Date) dateColList.get(i);
										 } else {
											 if(!dateBarRowList.isEmpty())
												 axisDate = (Date) dateBarRowList.get(i);	
										 }
										 cal.setTime(axisDate);
										 stringFormat=stringFormat.trim();
										 colLabelNew = new SimpleDateFormat(stringFormat).format(cal.getTime());
										 dpMap.put(truncatedLabel, colLabelNew);
										 if(!isCharacterLimitNone && (colLabelNew.length() > truncateCharLimit))
											 colLabelNew = colLabelNew.substring(0, truncateCharLimit)+"..";
										 dpMap.put(barColLable, colLabelNew);
									 }//Added for Bug #15195 end
									 else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
										 Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
											String strData = barcolList.get(i).toString();
											String frequency = "";
											if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
												String strCol = graphInfo.getColColumns().elementAt(0).toString();
												if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
														&& !dateFrequencyMap.get(strCol).isEmpty()) {
													frequency = dateFrequencyMap.get(strCol);
													strData = barcolList.get(i).toString();
													
												}
											}
											if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
												String strCol = graphInfo.getRowColumns().elementAt(0).toString();
												if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
														&& !dateFrequencyMap.get(strCol).isEmpty()) {
													frequency = dateFrequencyMap.get(strCol);
													strData = barrowList.get(i).toString();
												}
											}
											if(frequency != null && !frequency.isEmpty() ) {
												if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
												
													String stringFormat;
													stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat();
													stringFormat = stringFormat.replaceAll("&#39;", "'");
													Calendar cal = Calendar.getInstance();
													
													
													int iColumnType = 0;
																
																try {
																	
																	if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString())  && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())
																		iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getColColumns().elementAt(0).toString());
																	else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																		iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getRowColumns().elementAt(0).toString());
																	
																} catch (Exception e) {
																	ApplicationLog.error(e);
																}
																
																
																
																if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())			
																	strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getColColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
																else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																	strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getRowColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
																if(barcolList != null && !isCharacterLimitNone)
																{
																	
																		switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
																		{
																		case "auto":
																			truncateCharLimit = 15;
																			break;
																		case "custom":
																			truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
																			break;
																		}
																		if (strData.length() > truncateCharLimit)
																			strData = strData.substring(0, truncateCharLimit)+"..";
																		
																	
																}

																
													
																dpMap.put(barColLable, strData);
													dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
													
												}
												else {
													dpMap.put(barColLable, barcolList.get(i).toString());
													dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
												}
											
											}
											else {
												dpMap.put(barColLable, barcolList.get(i).toString());
												dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
											}
										}

									 else {
										 dpMap.put(barColLable, barcolList2.get(i).toString());
										 dpMap.put(truncatedLabel, truncatedColList.get(i).toString());
									 }
								 }
								 if(!dpMap.containsKey(drillAxis) && !drillcolList.isEmpty() && drillcolList.size() > 0)//12113(show preview internal))
								 {
									 dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());

								 }
								 dpMap.putAll(lineMap);

								 if(j==linerowListsize-1)									
									 dpMap.put("rowLastPage", 1);										
								 startIndex=startIndexcolne;
								 n=1;
								 break;

							 }


							 String label = null;

							 if(isLineLegendVisible){
								 label = linerowList.get(j).toString();

								 drillLineMap.put(linerowList.get(j).toString(), drillLineList.get(j).toString());
							 }else{
								 label = graphInfo.getGraphData().getCmbLinedataLabel();
								 if(linerowList.size() >0) {
									 lineMap.put("color", barColor[graphInfo.getCmbLineColorInfoList().get(j)%barColor.length]);
									 colorWiseLineIndex.add(graphInfo.getCmbLineColorInfoList().get(j)%barColor.length);
								 }
								 /*else the else portion commented for BUG 13273
									 lineMap.put("color", barColor[graphInfo.getCmbLineColorInfoList().get(0)%barColor.length]);//Changed i to 0 for solving Bug #13273
*/							 }
							 
							 lineDataValue = lineKeyValueMap.get(lineMapKeyStr) / lineDivValue;//performance changes

							 //Changes bar values to customMaxValue if flag is true
							 if(lineflag)
							 {
								 //for BUG 13367
								 /*if(lineDataValue > customLineMax)
								 {
									 lineDataValue = customLineMax;
								 }*/
							 }
							 //if(dataLineList.size() > (i*linerowListsize+j))
								 lineMap.put(label, lineDataValue);

							 lineMap.put("lineXAxisTitle", StringUtil.replaceSpecialCharWithHTMLEntity(graphInfo.getGraphData().getCmbLinecolLabel()));
							 if(lineDataLabel != null && (lineDataLabel.equalsIgnoreCase("null") || lineDataLabel.equalsIgnoreCase("Data")))
							 {
								 if(graphInfo.getTheDataColLabels4() != null && graphInfo.getTheDataColLabels4().size() >= linerowListsize)
									 lineMap.put("lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getGraphData().getTempLineLabelsList().get(j).toString()));
								 else
									 lineMap.put("lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getChangedTheDataColLabels4().get(0).toString()));
							 }
							 else
							 {
								 if(graphInfo.getGraphData().getColLabelsName2() != null && graphInfo.getGraphData().getColLabelsName2().size()>0)
									 lineMap.put("lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getGraphData().getColLabelsName2().get(0).toString()));
								 else
									 lineMap.put("lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(graphInfo.getChangedTheDataColLabels4().get(0).toString()));
							 }
							//commaseparator start
	                            double parseValue = lineDataValue;
	                            
	                            int precision = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getNumberOfDigits();
                                String digitsAfterDecimalString="";
                                switch(precision)
                                {
                                case 0:
                                	digitsAfterDecimalString="#.";
                                    break;
                                case 1:
                                	digitsAfterDecimalString="#.0";
                                    break;
                                case 2:
                                	digitsAfterDecimalString="#.00";
                                    break;
                                case 3:
                                	digitsAfterDecimalString="#.000";
                                    break;
                                case 4:
                                	digitsAfterDecimalString="#.0000";
                                    break;
                                case 5:
                                	digitsAfterDecimalString="#.00000";
                                    break;
                                }

                            //NumberFormat formatter = new DecimalFormat(digitsAfterDecimalString);
                            NumberFormat formatter = new DecimalFormat(digitsAfterDecimalString,DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            parseValue = Double.valueOf(formatter.format(parseValue));
                            
                            String commaSeparated = commaFormatsForLine(parseValue, graphInfo.getGraphProperties().getCombinedDataValueProperties(), graphInfo);
                            dpMap.put("Abs"+j+label.replaceAll("[^\\s\\w]*",""), commaSeparated);
                            
							 if(isLineLegendVisible){
								 lineMap.put(drillLineLegend, drillLineMap);
							 }
							 if(paginationLineIndex==startIndex && legendlineflag)
							 {

								 if(!dpMap.containsKey(barColLable))
								 {
									 if( (!dateBarRowList.isEmpty() && (null == barRowLabel || (null != barRowLabel && "Legend".equals(barRowLabel)) || barRowLabel.equals(barColLable)))
											 || !dateColList.isEmpty() )//Added for Bug #15195 start (reflecting #13406 changes)
									 {									 
										 String stringFormat;
										 stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
										 stringFormat = stringFormat.replaceAll("&#39;", "'");
										 Calendar cal = Calendar.getInstance();
										 Date axisDate = new Date();
										 if(!dateColList.isEmpty()) {
											 axisDate = (Date) dateColList.get(i);
										 } else {
											 if(!dateBarRowList.isEmpty())
												 axisDate = (Date) dateBarRowList.get(i);	
										 }
										 cal.setTime(axisDate);
										 stringFormat=stringFormat.trim();
										 colLabelNew = new SimpleDateFormat(stringFormat).format(cal.getTime());
										 dpMap.put(truncatedLabel, colLabelNew);
										 if(!isCharacterLimitNone && (colLabelNew.length() > truncateCharLimit))
											 colLabelNew = colLabelNew.substring(0, truncateCharLimit)+"..";
										 dpMap.put(barColLable, colLabelNew);
									 }//Added for Bug #15195 end
									 else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
										 Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
											String strData = barcolList.get(i).toString();
											String frequency = "";
											if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
												String strCol = graphInfo.getColColumns().elementAt(0).toString();
												if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
														&& !dateFrequencyMap.get(strCol).isEmpty()) {
													frequency = dateFrequencyMap.get(strCol);
													strData = barcolList.get(i).toString();
													
												}
											}
											if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
												String strCol = graphInfo.getRowColumns().elementAt(0).toString();
												if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
														&& !dateFrequencyMap.get(strCol).isEmpty()) {
													frequency = dateFrequencyMap.get(strCol);
													strData = barrowList.get(i).toString();
												}
											}
											if(frequency != null && !frequency.isEmpty() ) {
												if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
												
													String stringFormat;
													stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat();
													stringFormat = stringFormat.replaceAll("&#39;", "'");
													Calendar cal = Calendar.getInstance();
													
													
													int iColumnType = 0;
																
																try {
																	
																	if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString())  && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())
																		iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getColColumns().elementAt(0).toString());
																	else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																		iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getRowColumns().elementAt(0).toString());
																	
																} catch (Exception e) {
																	ApplicationLog.error(e);
																}
																
																
																
																if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())			
																	strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getColColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
																else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
																	strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getRowColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
												
																if(barcolList != null && !isCharacterLimitNone)
																{
																	
																		switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
																		{
																		case "auto":
																			truncateCharLimit = 15;
																			break;
																		case "custom":
																			truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
																			break;
																		}
																		if (strData.length() > truncateCharLimit)
																			strData = strData.substring(0, truncateCharLimit)+"..";
																		
																	
																}

																
														
													dpMap.put(barColLable, strData);
													dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
													
												}
												else {
													dpMap.put(barColLable, barcolList.get(i).toString());
													dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
												}
											
											}
											else {
												dpMap.put(barColLable, barcolList.get(i).toString());
												dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
											}
										}

									 else {
										 dpMap.put(barColLable, barcolList2.get(i).toString());
										 dpMap.put(truncatedLabel, truncatedColList.get(i).toString());
									 }
								 }
								 if(!dpMap.containsKey(drillAxis) && !drillcolList.isEmpty() && drillcolList.size() > 0)//12113(show preview internal))
								 {
									 dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());

								 }
								 dpMap.putAll(lineMap);

								 if(j==linerowListsize-1)									
									 dpMap.put("rowLastPage", 1);										
								 startIndex=startIndexcolne;
								 n=1;
								 break;

							 }
							 startIndex++;
						 } else
						 {	

							 if(startIndex==paginationLineIndex)
							 {
								 if(j==linerowListsize-1)									
									 dpMap.put("rowLastPage", 1);
								 startIndex=startIndexcolne;
								 break;
							 }

							 startIndex++;


						 }	

					 }					 

				 }
				 /*if(isBarTrend)//Trend Line
				 {
					 String label;

					 for(int c=0;c<noOfBarTrendLines;c++)
					 {
						 String[] splitString = trendBarLineColoumn.get(c).toString().split(",");
						 String key = (splitString[0])+(String)trendBarAlgoList.get(c);
						 List temp=(ArrayList)(trendBarMap.get(key));//fetch the coloumn given by the user
						 label=trendBarLineName.get(c).toString();
						 if(temp.get(i)!=null)
							 dpMap.put(label+"trendBar", temp.get(i));
						 if(i==0)
							 dpMap.put("TrendLabelBar"+c, label);
					 }

				 } */
				 
				 
			 //}
			 dpMap.putAll(barMap);
			 //if(dataLineList.size() > 0)
		dpMap.putAll(lineMap);
		 
		
		
		  
		 if( i==categorypaginationIndex-1)
		 {
			 if(!dpMap.containsKey(barColLable))
			 {
				 if( (!dateBarRowList.isEmpty() && (null == barRowLabel || (null != barRowLabel && "Legend".equals(barRowLabel)) || barRowLabel.equals(barColLable)))
						 || !dateColList.isEmpty() )//Added for Bug #15195 start (reflecting #13406 changes)
				 {
					 String stringFormat;
					 stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
					 stringFormat = stringFormat.replaceAll("&#39;", "'");
					 Calendar cal = Calendar.getInstance();
					 Date axisDate = new Date();
					 if(!dateColList.isEmpty()) {
						 axisDate = (Date) dateColList.get(i);
					 } else {
						 if(!dateBarRowList.isEmpty())
							 axisDate = (Date) dateBarRowList.get(i);	
					 }
					 cal.setTime(axisDate);
					 stringFormat=stringFormat.trim();
					 colLabelNew = new SimpleDateFormat(stringFormat).format(cal.getTime());
					 dpMap.put(truncatedLabel, colLabelNew);
					 if(!isCharacterLimitNone && (colLabelNew.length() > truncateCharLimit))
						 colLabelNew = colLabelNew.substring(0, truncateCharLimit)+"..";
					 dpMap.put(barColLable, colLabelNew);
				 }//Added for Bug #15195 end
				 else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
					 Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
						String strData = barcolList.get(i).toString();
						String frequency = "";
						if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
							String strCol = graphInfo.getColColumns().elementAt(0).toString();
							if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
									&& !dateFrequencyMap.get(strCol).isEmpty()) {
								frequency = dateFrequencyMap.get(strCol);
								strData = barcolList.get(i).toString();
								
							}
						}
						if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
							String strCol = graphInfo.getRowColumns().elementAt(0).toString();
							if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
									&& !dateFrequencyMap.get(strCol).isEmpty()) {
								frequency = dateFrequencyMap.get(strCol);
								strData = barrowList.get(i).toString();
							}
						}
						if(frequency != null && !frequency.isEmpty() ) {
							if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
							
								String stringFormat;
								stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat();
								stringFormat = stringFormat.replaceAll("&#39;", "'");
								Calendar cal = Calendar.getInstance();
								
								
								int iColumnType = 0;
											
											try {
												
												if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString())  && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())
													iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getColColumns().elementAt(0).toString());
												else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
													iColumnType = GeneralFiltersUtil.getCubeColumnType(graphInfo.getCubeInfo(),graphInfo.getRowColumns().elementAt(0).toString());
												
											} catch (Exception e) {
												ApplicationLog.error(e);
											}
											
											
											
											if(null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getColColumns().elementAt(0).toString()).isEmpty())			
												strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getColColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
											else if(null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() &&null != dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()) && !dateFrequencyMap.get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty())
												strData = StringUtil.getValuebyDateColumn(strData, iColumnType, graphInfo.getRowColumns().elementAt(0).toString(), dateFrequencyMap,stringFormat);
											if(barcolList != null && !isCharacterLimitNone)
											{
												
													switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
													{
													case "auto":
														truncateCharLimit = 15;
														break;
													case "custom":
														truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
														break;
													}
													if (strData.length() > truncateCharLimit)
														strData = strData.substring(0, truncateCharLimit)+"..";
													
												
											}

											
								
											
							    dpMap.put(barColLable, strData);
								dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
								
							}
							else {
								dpMap.put(barColLable, barcolList.get(i).toString());
								dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
							}
						
						}
						else {
							dpMap.put(barColLable, barcolList.get(i).toString());
							dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
						}
					}

				 else {
					 dpMap.put(barColLable, barcolList2.get(i).toString());
					 dpMap.put(truncatedLabel, truncatedColList.get(i).toString());
				 }
			  }
			 if(!dpMap.containsKey(drillAxis) && !drillcolList.isEmpty() && drillcolList.size() > 0)//12113(show preview internal))
			 {
					dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());
			    				 
			 }
			 setDisplayColorIndex(colorWiseIndex, graphInfo);
				 dpList.add(dpMap);
				 return dpList;
							
		 }
		 if(dpMap.size() > 0)
				dpList.add(dpMap);
		 categoryIndex++;
		  }
			 
		}
		setDisplayColorIndex(colorWiseIndex, graphInfo);
		return dpList;
		
		
		
		
		
	}
	
	private static final String[] appendValue(String[] s1 ,String newValue) {

		  String[] erg = new String[s1.length + 1];
		  erg[erg.length-1] = newValue;
	      System.arraycopy(s1, 0, erg, 0, s1.length);

	      return erg;

	  }
	
	public static String commaFormatsForBar(double in_objValue, CombinedDataValueProperties combinedDataValueProperties,GraphInfo graphInfo){

		double dValue = in_objValue;
		String strData = "";

		Object commaSepObj = combinedDataValueProperties.getBarnumberFormat().isCommaSeprator();
		boolean commaSep = false;
		if (commaSepObj != null) {
			commaSep = ((Boolean) commaSepObj).booleanValue();
		}

		int commaPosStyleObj =  combinedDataValueProperties.getBarnumberFormat().getCommaFormat();
		//commaPosStyleObj = false;
		boolean commaPosStyle = false;
		if (commaPosStyleObj == 2) {
			commaPosStyle = true;
		}
		
		if (commaSep && commaPosStyle
				&& (dValue <= -100000 || dValue >= 100000)) {				
			combinedDataValueProperties.getBarnumberFormat().setCommaSeprator(false);
			String strFmt = GetDecimalFormatStringBar(combinedDataValueProperties,0);
			combinedDataValueProperties.getBarnumberFormat().setCommaSeprator(true);				
			java.text.DecimalFormat DecFormat = new java.text.DecimalFormat(
					strFmt);
			strData = DecFormat.format(dValue);
			strData = StringUtil.modifyDataForIndianStyleCommaPos(strData,
					null, false);
		} else {
			String strFmt = GetDecimalFormatStringBar(combinedDataValueProperties,0);
			java.text.DecimalFormat DecFormat = new java.text.DecimalFormat(
					strFmt);
			// Check The Infinate & NaN value By Piyush Ramani Bug:5949
			if (Double.isInfinite(dValue) || Double.isNaN(dValue))
				strData = "";
			else
				strData = DecFormat.format(dValue);
		}
	
		return strData;
	}
	public static String commaFormatsForLine(double in_objValue, CombinedDataValueProperties combinedDataValueProperties,GraphInfo graphInfo){

		double dValue = in_objValue;
		String strData = "";

		Object commaSepObj = combinedDataValueProperties.getLinenumberFormat().isCommaSeprator();
		boolean commaSep = false;
		if (commaSepObj != null) {
			commaSep = ((Boolean) commaSepObj).booleanValue();
		}

		int commaPosStyleObj =  combinedDataValueProperties.getLinenumberFormat().getCommaFormat();
		//commaPosStyleObj = false;
		boolean commaPosStyle = false;
		if (commaPosStyleObj == 2) {
			
			//commaPosStyle = ((Boolean) commaPosStyleObj).booleanValue();
			commaPosStyle = true;
		}
		
		if (commaSep && commaPosStyle
				&& (dValue <= -100000 || dValue >= 100000)) {				
			combinedDataValueProperties.getLinenumberFormat().setCommaSeprator(false);
			String strFmt = GetDecimalFormatStringLine(combinedDataValueProperties,0);
			combinedDataValueProperties.getLinenumberFormat().setCommaSeprator(true);				
			java.text.DecimalFormat DecFormat = new java.text.DecimalFormat(
					strFmt);
			strData = DecFormat.format(dValue);
			strData = StringUtil.modifyDataForIndianStyleCommaPos(strData,
					null, false);
		} else {
			String strFmt = GetDecimalFormatStringLine(combinedDataValueProperties,0);
			java.text.DecimalFormat DecFormat = new java.text.DecimalFormat(
					strFmt);
			// Check The Infinate & NaN value By Piyush Ramani Bug:5949
			if (Double.isInfinite(dValue) || Double.isNaN(dValue))
				strData = "";
			else
				strData = DecFormat.format(dValue);
		}
	
		return strData;
	}
	public  static String GetDecimalFormatStringBar( CombinedDataValueProperties  colProp,int operationType)
	  {
	    String strFormat = "";
	    Object key_coma =  null;
	    if(colProp != null )
	    	key_coma =  colProp.getBarnumberFormat().isCommaSeprator();
	    
	    if(key_coma != null)
	    {
	      boolean bValue = ((Boolean)key_coma).booleanValue();
	      if(bValue)
	      {
	        strFormat += "###,##0";
	      }
	      else
	      {
	        strFormat += "#####0";
	      }
	    }
	    Object obj = null;
	    if(colProp != null)
	    	obj = colProp.getBarnumberFormat().getNumberOfDigits();
	   
	    if(operationType == ICubeResultSetSupport.totalTypeCount || operationType == ICubeResultSetSupport.totalTypeCount2
	    		||operationType == ICubeResultSetSupport.totalTypeCount4) {
	    	obj = 0;
	    }
	    int scale = 0;
	    if(obj != null)
	    {
	      scale = ((Integer)obj).intValue();
	    }
	    for(int i = 0; i < scale; i++ )
	    {
	      if(i == 0)
	      {
	        strFormat += '.';
	      }
	      strFormat += '0';
	    }
	    strFormat += ';';
	    int iChoice = 0;
	    if(colProp != null)
	    	iChoice = colProp.getBarnumberFormat().getNegativeNumberFormat();

		  switch(iChoice)
		  {
		    case 0:
		      strFormat += '-';
		      break;
		    case 1:
		      strFormat += " ";
		      break;
		    case 2:
		      strFormat += '(';
		      break;
		    case 3:
		      strFormat += ResourceManager.getString("DOLLAR");
		      break;
		    case 4:
		      strFormat += ResourceManager.getString("EURO");
		      break;
		  }
	    
	    obj = key_coma;
	    if(obj != null)
	    {
	      boolean bValue = ((Boolean)obj).booleanValue();
	      if(bValue)
	      {
	        strFormat += "###,##0";
	      }
	      else
	      {
	        strFormat += "#####0";
	      }
	    }
	    for(int i = 0; i < scale; i++ )
	    {
	      if(i == 0)
	      {
	        strFormat += '.';
	      }
	      strFormat += '0';
	    }
	    
	      switch(iChoice)
	      {
	        case 2:
	          strFormat += ')';
	          break;
	      }
	    
	    return strFormat;
	  }
	public  static String GetDecimalFormatStringLine( CombinedDataValueProperties  colProp,int operationType)
	  {
	    String strFormat = "";
	    Object key_coma =  null;
	    if(colProp != null )
	    	key_coma =  colProp.getLinenumberFormat().isCommaSeprator();
	  
	    if(key_coma != null)
	    {
	      boolean bValue = ((Boolean)key_coma).booleanValue();
	      if(bValue)
	      {
	        strFormat += "###,##0";
	      }
	      else
	      {
	        strFormat += "#####0";
	      }
	    }
	    Object obj = null;
	    if(colProp != null)
	    	obj = colProp.getLinenumberFormat().getNumberOfDigits();
	   
	    if(operationType == ICubeResultSetSupport.totalTypeCount || operationType == ICubeResultSetSupport.totalTypeCount2
	    		||operationType == ICubeResultSetSupport.totalTypeCount4) {
	    	obj = 0;
	    }
	    int scale = 0;
	    if(obj != null)
	    {
	      scale = ((Integer)obj).intValue();
	    }
	    for(int i = 0; i < scale; i++ )
	    {
	      if(i == 0)
	      {
	        strFormat += '.';
	      }
	      strFormat += '0';
	    }
	    strFormat += ';';
	    int iChoice = 0;
	    if(colProp != null)
	    	iChoice = colProp.getLinenumberFormat().getNegativeNumberFormat();
	 
		  switch(iChoice)
		  {
		    case 0:
		      strFormat += '-';
		      break;
		    case 1:
		      strFormat += " ";
		      break;
		    case 2:
		      strFormat += '(';
		      break;
		    case 3:
		      strFormat += ResourceManager.getString("DOLLAR");
		      break;
		    case 4:
		      strFormat += ResourceManager.getString("EURO");
		      break;
		  }
	    
	    obj = key_coma;
	    if(obj != null)
	    {
	      boolean bValue = ((Boolean)obj).booleanValue();
	      if(bValue)
	      {
	        strFormat += "###,##0";
	      }
	      else
	      {
	        strFormat += "#####0";
	      }
	    }
	    for(int i = 0; i < scale; i++ )
	    {
	      if(i == 0)
	      {
	        strFormat += '.';
	      }
	      strFormat += '0';
	    }
	    
	      switch(iChoice)
	      {
	        case 2:
	          strFormat += ')';
	          break;
	      }
	    
	    return strFormat;
	  }
	
	private static void setDisplayColorIndex(List colorWiseIndex, GraphInfo graphInfo) {
		if(colorWiseIndex != null && !colorWiseIndex.isEmpty()) {
			HashSet<String> uniqueElements = new HashSet<>(colorWiseIndex);
	        colorWiseIndex = new ArrayList<>(uniqueElements);
		}
		graphInfo.setDisplayBarIndexList(colorWiseIndex);
	}
}
