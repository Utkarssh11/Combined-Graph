package com.elegantjbi.amcharts;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.parser.Parser;

import com.elegantjbi.amcharts.vo.Graphs;
import com.elegantjbi.entity.graph.GraphInfo;
import com.elegantjbi.service.graph.GraphCommandNameList;
import com.elegantjbi.util.AppConstants;
import com.elegantjbi.util.StringUtil;
import com.elegantjbi.vo.properties.kpi.TrendLineProperties;
public class AmCombinedGraphs {

	 //Start
	 /**
	 * @param graphInfo
	 * @return
	 */
	public static List<Graphs> graphsJson(GraphInfo graphInfo,int startIndex,int Quantity){
		
		
		String bullet = null;
		List colorWiseLineIndex = new ArrayList<>();
		 List<String> bulletList = new ArrayList<String>();
		 List<Integer> bulletSizeList = new ArrayList<Integer>();
		 List<Integer> lineStyleList = new ArrayList<Integer>();
		 List<Integer> lineThicknessList = new ArrayList<Integer>();
		 List<String> borderColorList = new ArrayList<String>();
		 List<Integer> borderWidthList = new ArrayList<Integer>();
		 List<Integer> bulletStyleList = new ArrayList<Integer>();
		 
		 String barRowLabel=graphInfo.getGraphData().getCmbBarrowLabel();
		 String lineRowLabel=graphInfo.getGraphData().getCmbLinerowLabel();
		 boolean sameRowflag=false; 
		 
		 if(barRowLabel!=null && lineRowLabel!=null && barRowLabel.equals(lineRowLabel))
		 {
			 sameRowflag=true; 
		 }
		 //System.out.println("samerowflag="+sameRowflag+barRowLabel+" "+lineRowLabel+" "+graphInfo.getGraphData().getCmbBarcolLabel()+" "+graphInfo.getGraphData().getCmbLinecolLabel());
		 List dateBarRowList = graphInfo.getGraphData().getDatecmbBarrowList();
		 List dateLineRowList = graphInfo.getGraphData().getDatecmbLinerowList();
		 
		 List barcolList=graphInfo.getGraphData().getCmbBarcolList();
		 List barrowList=graphInfo.getGraphData().getCmbBarrowList();
		 List linerowList=graphInfo.getGraphData().getCmbLinerowList();
		 //Line data Label
		 
		 List barList= graphInfo.getDataColLabels3();
		 List lineList= graphInfo.getTheDataColLabels4();		
		
		 int countmm = 0;		
		 boolean tempBarLine = false;		
		 
		 int barcolListsize=barcolList.size();		 
		 int barrowListsize=barrowList.size();		 
		 int linerowListsize=linerowList.size();
		 boolean colLabelsName = false;
		 boolean colLabelsName2 = false;
		//non-clustered bar with colNames
		if(graphInfo.getGraphData().getColLabelsName() != null && graphInfo.getGraphData().getColLabelsName().size()>0)
			colLabelsName = true;
		//non-clustered line with colNames
		if(graphInfo.getGraphData().getColLabelsName2() != null && graphInfo.getGraphData().getColLabelsName2().size()>0)
			colLabelsName2 = true;
		
		//added for showing multiple measure
		 if((barrowList.isEmpty() && linerowList.isEmpty()) || sameRowflag)
			 tempBarLine=true;
		
		/*if(graphInfo.getTheDataColLabels4().equals(graphInfo.getGraphData().getCmbLinerowList())) 
			tempLine=true;*/
		
		 boolean isLegendVisible = true;
		 boolean isBarLegendVisible = true;
		 boolean isLineLegendVisible = true;
		 
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
*/
		 String[] barColor =new String[]{"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
			    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
			    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
			    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
			    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
			    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
			    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 
		 
		 //String[] barColor =new String[]{"rgb(141,170,203)","rgb(252,115,98)","rgb(187,216,84)","rgb(255,217,47)","rgb(102,194,150)","rgb(255, 148, 10)","rgb(148, 247, 244)"};
		/* String[] lineColors =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"
				 						,"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		*/
		 String[] lineColors =new String[]{"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
				    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
				    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
				    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
				    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
				    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
				    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 
		/* String[] bulletColor =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"
				 						,"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 */
		 String[] bulletColor =new String[]{"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
			    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
			    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
			    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
			    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
			    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
			    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 
		 String[] bulletTypeArray = new String[]{"square","round","triangleUp","diamond"};


		 String drillAxis = "drillAxis";
		 String drillBarLegend = "drillBarLegend";
		 String drillLineLegend = "drillLineLegend";
		 List drillBarList = graphInfo.getGraphData().getDrillBarLinkList();
		 List drillLineList = graphInfo.getGraphData().getDrillLineLinkList();
		 List drillcolList = graphInfo.getGraphData().getDrillColLinkList();
		 
		
		 String barColLabel = graphInfo.getGraphData().getCmbBarcolLabel();
		 String barDataLabel = graphInfo.getGraphData().getCmbBardataLabel();
		 
		
		 String lineColLabel = graphInfo.getGraphData().getCmbLinecolLabel();
		 String lineDataLabel = graphInfo.getGraphData().getCmbLinedataLabel();
		 
	 //Trend Line line Start
		 Map trendLineMap = graphInfo.getGraphData().getTrendMap();
		 boolean isTrend =false;
		 int trendCount;

		 int noOfTrendLines = 0;
		 if(trendLineMap != null)
			 noOfTrendLines = trendLineMap.size(); 
		 List trendValue = new ArrayList();
		 List trendColor = new ArrayList();
		 List trendLineName = new ArrayList();
		 List trendLineColoumn = new ArrayList();
		 List trendLineThickness = new ArrayList();
		 List trendLineStyle = new ArrayList();
		 if(noOfTrendLines > 0)
		 {
			 isTrend=true;
			 trendCount = noOfTrendLines;

			 Map<Integer, TrendLineProperties> testMap = graphInfo.getGraphProperties().getLinetrendlinePropertiesMap();//graphInfo.getGraphProperties().getTrendlinePropertiesMap();
			 for (Entry<Integer, TrendLineProperties> entry : testMap.entrySet()) {
				 String[] splitString = ((String) entry.getValue().getTrendLineColumn()).split(",");
				 trendValue.add(splitString[0]);
				 trendColor.add(entry.getValue().getTrendLineColor());
				 trendLineName.add(entry.getValue().getTrendLineName());//Name of the trend Line given by the user
				 trendLineColoumn.add(entry.getValue().getTrendLineColumn());
				 trendLineThickness.add(entry.getValue().getTrendLineThickness());
				 trendLineStyle.add(entry.getValue().getTrendLineStyle());
			 }
		 }
		//trend Line line End
		 
		//trend line bar start
		 Map trendBarMap = graphInfo.getGraphData().getTrendCmbBarMap();
		 boolean isBarTrend =false;
		 int trendBarCount;

		 int noOfBarTrendLines = 0;
		 if(trendBarMap != null)
			 noOfBarTrendLines = trendBarMap.size(); 
		 List trendBarValue = new ArrayList();
		 List trendBarColor = new ArrayList();
		 List trendBarLineName = new ArrayList();
		 List trendBarValues = new ArrayList();
		 List trendBarLineColoumn = new ArrayList();
		 List trendBarLineThickness = new ArrayList();
		 List trendBarLineStyle = new ArrayList();
		 if(noOfBarTrendLines > 0)
		 {
			 isBarTrend=true;
			 trendBarCount = noOfBarTrendLines;

			 Map<Integer, TrendLineProperties> testMap = graphInfo.getGraphProperties().getBartrendlinePropertiesMap();//graphInfo.getGraphProperties().getTrendlinePropertiesMap();
			 for (Entry<Integer, TrendLineProperties> entry : testMap.entrySet()) {
				 String[] splitString = ((String) entry.getValue().getTrendLineColumn()).split(",");
				 trendBarValue.add(splitString[0]);
				 trendBarColor.add(entry.getValue().getTrendLineColor());
				 trendBarLineName.add(entry.getValue().getTrendLineName());//Name of the trend Line given by the user
				 trendBarLineColoumn.add(entry.getValue().getTrendLineColumn());
				 trendBarLineThickness.add(entry.getValue().getTrendLineThickness());
				 trendBarLineStyle.add(entry.getValue().getTrendLineStyle());
			 }}
		 
		 
		//trend line bar end
		
		 
		 int paginationBarIndex=startIndex+Quantity;
		 
			if(paginationBarIndex > barrowListsize)
				paginationBarIndex=barrowListsize-1;
			if(barrowListsize < Quantity)
				paginationBarIndex=barrowListsize;
		
			int paginationLineIndex=startIndex+Quantity;

			if(paginationLineIndex > linerowListsize)
				paginationLineIndex=linerowListsize-1;
			if(linerowListsize < Quantity)
				paginationLineIndex=linerowListsize;
			
			
		 
		 for (int k = 0; k < linerowListsize; k++) {
			 bulletList.add("round");
			 bulletSizeList.add(8);
			 lineStyleList.add(0);
			 lineThicknessList.add(1);
			 borderWidthList.add(0);
			 borderColorList.add("none");
			 bulletStyleList.add(7);
		 }
		 //Bar Custom Color 
		 
		 switch(graphInfo.getGraphProperties().getColorType())
			{
			case 1:
				if(graphInfo.getGraphProperties().getCustomColors() != null)
				{
					for (int i = 0; i < graphInfo.getGraphProperties().getCustomColors().size(); i++) {
						if(i > (barColor.length-1))
						{
							barColor = appendValue(barColor, graphInfo.getGraphProperties().getCustomColors().get(i));
							//lineColors = appendValue(barColor, graphInfo.getGraphProperties().getCustomColors().get(i));
						}
						else
						{	
						barColor[i] = graphInfo.getGraphProperties().getCustomColors().get(i);
						//lineColors[i] = graphInfo.getGraphProperties().getCustomColors().get(i);
						}
					}
				}
				break;
			case 2:
				barColor = new String[]{graphInfo.getGraphProperties().getColor()};
				break;
			}
		 
		 //line custom color
		 int colorType = graphInfo.getGraphProperties().getLineColorType();
			List<String> customColors = graphInfo.getGraphProperties().getLineCustomColors();
			String sameColor = graphInfo.getGraphProperties().getLinecolor();
			
			int pointColorType = graphInfo.getGraphProperties().getPointColorType();
			List<String> pointCustomColors = graphInfo.getGraphProperties().getPointCustomColors();
			String pointSameColor = graphInfo.getGraphProperties().getPointcolor();
		
			switch(colorType)
			{
			case 1:
				if(customColors != null)
				{
					for (int i = 0; i < customColors.size(); i++) {
						if(i > (lineColors.length-1))// || i > (bulletColor.length-1))
						{
							lineColors = appendValue(lineColors, customColors.get(i));
						}
						else
						{	
							lineColors[i] = customColors.get(i);
						}
					}
				}
				break;
			case 2:
				lineColors = new String[]{sameColor};
				break;
			}
			switch(pointColorType)
			{
			case 0:
				bulletColor = lineColors;break;
			case 1:
				if(pointCustomColors != null)
				{
					for (int i = 0; i < pointCustomColors.size(); i++) {
						if(i > (bulletColor.length-1))// || i > (bulletColor.length-1))
							bulletColor = appendValue(bulletColor, pointCustomColors.get(i));
						else
							bulletColor[i] = pointCustomColors.get(i);
					}
				}
				break;
			case 2:
				bulletColor = new String[]{pointSameColor};
				break;
			}
			if(graphInfo.getGraphProperties().getLineType() == 0)
			{
				int dashLength = 0;
				for (int i = 0; i < linerowListsize; i++) {

					int lineStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getAllLineStyle());
					int customLineThickness = graphInfo.getGraphProperties().getGraphLineProperties().getAllLineWidth();
					//Switch case for line style (dash/dot)
					switch (lineStyle) {
					case 0:
						dashLength = 0;
						break;
					case 1:
						dashLength = 0;
						break;
					case 2:
						dashLength = 7;
						break;
					case 3:
						dashLength = 1;
						break;
					}
					lineStyleList.set(i, dashLength);
					lineThicknessList.set(i, customLineThickness);
				}
			}
			else
			{
				if(null != graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList() && graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().size() != 0)
				{
					int dashLength = 0;

				int temp = 0;
				for (int l = 0; l < graphInfo.getTheDataColLabels4().size(); l++) {

					if (graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList()
							.size() > l) {

						// for (int l = 0; l <
						// graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().size();
						// l++) {
						int lineStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties()
								.getGraphlinePropertiesList().get(l).getStyle());
						int customLineThickness = Integer.parseInt(graphInfo.getGraphProperties()
								.getGraphLineProperties().getGraphlinePropertiesList().get(l).getThickness());
						// Switch case for line style (dash/dot)
						switch (lineStyle) {
						case 0:
							dashLength = 0;
							break;
						case 1:
							dashLength = 0;
							break;
						case 2:
							dashLength = 7;
							break;
						case 3:
							dashLength = 1;
							break;
						}
						if (l < lineStyleList.size())
							lineStyleList.set(l, dashLength);

						if (l < lineThicknessList.size())
							lineThicknessList.set(l, customLineThickness);
					} else {

						int lineStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties()
								.getGraphlinePropertiesList().get(temp).getStyle());
						int customLineThickness = Integer.parseInt(graphInfo.getGraphProperties()
								.getGraphLineProperties().getGraphlinePropertiesList().get(temp).getThickness());
						// Switch case for line style (dash/dot)
						switch (lineStyle) {
						case 0:
							dashLength = 0;
							break;
						case 1:
							dashLength = 0;
							break;
						case 2:
							dashLength = 7;
							break;
						case 3:
							dashLength = 1;
							break;
						}
						if (l < lineStyleList.size())
							lineStyleList.set(l, dashLength);

						if (l < lineThicknessList.size())
							lineThicknessList.set(l, customLineThickness);
					}
				}
				}
			}
			if(graphInfo.getGraphProperties().getPointType() == 0)
			{
				for (int i = 0; i < linerowListsize; i++) {

					int bulletType = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getAllPointStyle());
					int bulletSize = graphInfo.getGraphProperties().getGraphLineProperties().getAllPointWidth();
					String bordercolor = graphInfo.getGraphProperties().getGraphLineProperties().getAllbordercolor();
					int borderwidth = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getAllborderwidth());
					int bulletStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getAllborderstyle());
					//Switch case for line style (dash/dot)
					switch (bulletType) {
					case 0:
						bullet = "square";
						break;
					case 1:
						bullet = "round";
						break;
					case 2:
						bullet = "triangleUp";
						break;
					case 3:
						bullet = "diamond";
						break;
					}
					switch(graphInfo.getGraphProperties().getGraphLineProperties().getAllborderwidth())
					{
					case "-1":borderwidth = 0;break;
					}
					if(bulletList.size()-1 >= i)
					{
						bulletList.set(i, bullet);					 
						bulletSizeList.set(i, bulletSize);
						if(i < bulletTypeArray.length)
						{
							bulletTypeArray[i] = bullet;
						}
						borderWidthList.set(i, borderwidth);
						if(!graphInfo.getGraphProperties().getGraphLineProperties().isAllbordercoloraslinecolor())
							borderColorList.set(i, bordercolor);
						else
							borderColorList.set(i, lineColors[i%lineColors.length]);
						bulletStyleList.set(i,bulletStyle);
					}
				
				}
			}
			else
			{
				if(null != graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList() && graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().size() != 0)
				{
                     	int temp =0;
					
					for (int l = 0; l < graphInfo.getTheDataColLabels4().size(); l++) {

						if (graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList()
								.size() > l) {


						int bulletType = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(l).getStyle());
						int bulletSize = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(l).getThickness());
						String bordercolor = graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(l).getBordercolor();
						int borderwidth = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(l).getBorderwidth());
						int bulletStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(l).getBorderstyle());
						//Switch case for line style (dash/dot)
						switch (bulletType) {
						case 0:
							bullet = "square";
							break;
						case 1:
							bullet = "round";
							break;
						case 2:
							bullet = "triangleUp";
							break;
						case 3:
							bullet = "diamond";
							break;
						}
						switch(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(l).getBorderwidth())
						{
						case "-1":borderwidth = 0;break;
						}
						if(bulletList.size()-1 >= l)
						{
							if(l < bulletList.size())
								bulletList.set(l, bullet);
							if(l < bulletSizeList.size())
								bulletSizeList.set(l, bulletSize);
							if(l < bulletTypeArray.length)
							{
								bulletTypeArray[l] = bullet;
							}
							if(l < borderWidthList.size())
								borderWidthList.set(l, borderwidth);
							if(l < borderColorList.size())
								borderColorList.set(l, bordercolor);
							if(l < bulletStyleList.size())
								bulletStyleList.set(l,bulletStyle);
						}
					}else {
						int bulletType = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(temp).getStyle());
						int bulletSize = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(temp).getThickness());
						String bordercolor = graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(temp).getBordercolor();
						int borderwidth = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(temp).getBorderwidth());
						int bulletStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(temp).getBorderstyle());
						//Switch case for line style (dash/dot)
						switch (bulletType) {
						case 0:
							bullet = "square";
							break;
						case 1:
							bullet = "round";
							break;
						case 2:
							bullet = "triangleUp";
							break;
						case 3:
							bullet = "diamond";
							break;
						}
						switch(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().get(temp).getBorderwidth())
						{
						case "-1":borderwidth = 0;break;
						}
						if(bulletList.size()-1 >= temp)
						{
							if(l < bulletList.size())
								bulletList.set(l%bulletList.size(), bullet);
							if(l < bulletSizeList.size())
								bulletSizeList.set(l%bulletSizeList.size(), bulletSize);
							if(l < bulletTypeArray.length)
							{
								bulletTypeArray[l] = bullet;
							}
							
							if(l < borderWidthList.size())
								borderWidthList.set(l%borderWidthList.size(), borderwidth);
							
							if(l < borderColorList.size())
								borderColorList.set(l%borderColorList.size(), bordercolor);
							
							if(l < bulletStyleList.size())
								bulletStyleList.set(l%bulletStyleList.size(),bulletStyle);
						}

					}
						}
				}
			}
			/* if(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList() != null)
			 {
				 int dashLength = 0;
				 for (int l = 0; l < graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().size(); l++) {
					 int bulletType = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getPointStyle());
					 String pointColor = graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getPointColor();
					 String lineColor =  graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getColor();
					 int lineStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getStyle());
					 String bordercolor = graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getBordercolor();
					 int borderwidth = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getBorderwidth());
					 int customLineThickness = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getThickness());
					 int bulletStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getBorderstyle());
					 //Switch case for bullet(marker) shape 
					 switch (bulletType) {
					 case 0:
						 bullet = "square";
						 break;
					 case 1:
						 bullet = "round";
						 break;
					 case 2:
						 bullet = "triangleUp";
						 break;
					 case 3:
						 bullet = "diamond";
						 break;
					 }
					 //Switch case for line style (dash/dot)
					 switch (lineStyle) {
					 case 0:
						 dashLength = 0;
						 break;
					 case 1:
						 dashLength = 0;
						 break;
					 case 2:
						 dashLength = 7;
						 break;
					 case 3:
						 dashLength = 1;
						 break;
					 }
					 int bulletSize = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getPointThickness());
					 switch(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getBorderstyle())
					 {
					 case "-1":borderwidth = 0;break;
					 }
					 if(bulletList.size()-1 >= l)
					 {
						 bulletList.set(l, bullet);					 
						 bulletColor[l] = pointColor;
						 bulletTypeArray[l] = bullet;
						 bulletSizeList.set(l, bulletSize);
						 lineColors[l] = lineColor;
						 lineStyleList.set(l, dashLength);
						 lineThicknessList.set(l, customLineThickness);
						 borderWidthList.set(l, borderwidth);
						 borderColorList.set(l, bordercolor);
						 bulletStyleList.set(l,bulletStyle);
					 }
				 }
			 }*/
		 
		 
		
		 int colorLength = barColor.length;
		 
		 
		 String precisionBarLabel=null;			
			if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isShowadAdjustedSuffixed())
			{	
				int prefix = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getAdjustedDigit();
				switch(prefix)
				{
				case 0:
					precisionBarLabel="";
					break;
				case 3:
					precisionBarLabel="K";
					break;
				case 5:
					precisionBarLabel="L";
					break;
				case 6:
					precisionBarLabel="M";
					break;
				case 7:
					precisionBarLabel="Cr";
					break;
				case 9:
					precisionBarLabel="Bn";
					break;
				}
			}
			else
			{
				precisionBarLabel="";
				
			} 
			
			
			String precisionLineLabel=null;			
			if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isShowadAdjustedSuffixed())
			{	
				int prefix = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getAdjustedDigit();
				switch(prefix)
				{
				case 0:
					precisionLineLabel="";
					break;
				case 3:
					precisionLineLabel="K";
					break;
				case 5:
					precisionLineLabel="L";
					break;
				case 6:
					precisionLineLabel="M";
					break;
				case 7:
					precisionLineLabel="Cr";
					break;
				case 9:
					precisionLineLabel="Bn";
					break;
				}
			}
			else
			{
				precisionLineLabel="";
				
			} 

			int digitsBarftDecimal = 0;
			
			// yaxis labels BAR digits after decimal start
			int digitsLineftDecimal = 0;
			
			// yaxis labels digits after decimal end

			//BAR  data value digits after decimal start
			int precision = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getNumberOfDigits();
			switch(precision)
			{
			case 0:
				digitsBarftDecimal = 0;
				break;
			case 1:				
				digitsBarftDecimal = 1;
				break;
			case 2:
				digitsBarftDecimal = 2;
				break;				
			case 3:
				digitsBarftDecimal = 3;
				break;				
			case 4:
				digitsBarftDecimal = 4;
				break;				
			case 5:
				digitsBarftDecimal = 5;
				break;				
			}
			//Line  data value digits after decimal start
			int lineprecision = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getNumberOfDigits();
			switch(lineprecision)
			{
			case 0:
				digitsLineftDecimal = 0;
				break;
			case 1:
				digitsLineftDecimal = 1;
				break;
			case 2:
				digitsLineftDecimal = 2;
				break;
			case 3:
				digitsLineftDecimal = 3;
				break;
			case 4:
				digitsLineftDecimal = 4;
				break;
			case 5:
				digitsLineftDecimal = 5;
				break;
				
			}
			// data value digits after decimal start
			// 9 Apr add [p.p]
			
			List hideGraphsListCol = new ArrayList();
			List hideGraphsListLine = new ArrayList();
			
			Map graphsVisibleMap = graphInfo.getGraphProperties().getGraphsVisibleMap();
			for(int i=0;i<graphInfo.getDataColLabels3().size();i++)//Changed from getDataColumns() to getDataColLabels3() for Jira Bug SDEVAPR20-79
			{
				
				if(graphsVisibleMap.get(graphInfo.getDataColLabels3().get(i)) != null && graphsVisibleMap.get(graphInfo.getDataColLabels3().get(i)).toString().equals("false")) {
					hideGraphsListCol.add(i);
				}
			}
			for(int k=0;k<graphInfo.getTheDataColLabels4().size();k++)
			{
				if(graphsVisibleMap.get(graphInfo.getTheDataColLabels4().get(k)) != null && graphsVisibleMap.get(graphInfo.getTheDataColLabels4().get(k)).toString().equals("false"))
					hideGraphsListLine.add(k);
	
			}

		
			// 9 Apr
			int startIndexclone=startIndex;
			
		 
			List<Graphs> graphsList = new ArrayList<>();
			/*for (int mm = 0; mm < barDataSize; mm++) {*/
				for (int j = 0; j < barrowListsize; j++) {
					if((j==startIndex || barrowListsize < Quantity) && !hideGraphsListCol.contains(j)){
						if(paginationBarIndex==startIndex)
						{
							break;
						}
						Graphs graphs = new Graphs();
						graphs.setShowHandOnHover(true);////feature req 13494
						//Bar width
						if(graphInfo.getGraphProperties().getBarProperties().getBarWidth() != 100)
						{
							double barWidth = (double)graphInfo.getGraphProperties().getBarProperties().getBarWidth()/100;
							graphs.setColumnWidth(barWidth);//between 0-1
						}
						//Bar width
						String label = "";
						if(isBarLegendVisible){
							if(sameRowflag)
							{
								label = "Abs"+j+(barrowList.get(j).toString()+barDataLabel).replaceAll("[^\\s\\w]*","");
							}
							else
							{
								label = "Abs"+j+barrowList.get(j).toString().replaceAll("[^\\s\\w]*","");
							}
		
						}else{
							if(null != graphInfo.getGraphData().getCmbBardataLabel())
								label = "Abs"+j+graphInfo.getGraphData().getCmbBardataLabel().replaceAll("[^\\s\\w]*","");					
						}
						if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().isMouseOverTextEnable())
						{
							String mouseOverString = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverFormatText();
							if(mouseOverString!=null){
								mouseOverString=mouseOverString.replace("&lt;/br&gt", "");
								mouseOverString=mouseOverString.replace("&lt;br&gt", "");
							}
//							mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[["+label+"]]");
//							mouseOverString = mouseOverString.replace("[["+label+"]]", "[["+label+"]]"+precisionBarLabel);
							if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isAutovalue()) {
							    mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
							} else {
							    mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[" + label + "]]");
							    mouseOverString = mouseOverString.replace("[[" + label + "]]", "[[" + label + "]]" + precisionBarLabel);
							}
							mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_VALUE,"[[truncatedLabel]]");
						
							
							if((!graphInfo.getDataColLabels3().isEmpty() || !graphInfo.getTheDataColLabels4().isEmpty()) && tempBarLine)
							{								
								String desLabel1="";
								countmm = 0;
								for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{	
										++countmm;
										desLabel1="Abs"+m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*","");										
										if(sameRowflag) {
											desLabel1="Abs"+m+(graphInfo.getDataColLabels3().get(m).toString()+barDataLabel);
										}
										
										mouseOverString = StringUtil.replace(mouseOverString,"$BAR-Y-AXIS_VALUE"+countmm+"$","[["+desLabel1+"]]"+precisionBarLabel);
										mouseOverString = StringUtil.replace(mouseOverString,"$BAR-Y-AXIS_TITLE"+countmm+"$",graphInfo.getDataColLabels3().get(m).toString());
								}
								countmm = 0;
								for(int m=0; m<graphInfo.getTheDataColLabels4().size();m++)	{	
										++countmm;
										desLabel1 = m+graphInfo.getTheDataColLabels4().get(m).toString().replaceAll("[^\\s\\w]*", "");								
										mouseOverString = StringUtil.replace(mouseOverString,"$LINE-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLineLabel);														
										mouseOverString = StringUtil.replace(mouseOverString,"$LINE-Y-AXIS_TITLE"+countmm+"$",graphInfo.getTheDataColLabels4().get(m).toString());
								}
							}
							if(barrowList != null && !barrowList.isEmpty())
							{
								
								if(isBarLegendVisible && graphInfo.getDataColLabels3().size() > 1 && colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= barrowListsize)
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"[[barYAxisTitle"+graphInfo.getGraphData().getTempBarLabelsList().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
								else if(graphInfo.getDataColLabels3().size() == 1 && colLabelsName)
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"[[barYAxisTitle"+graphInfo.getChangedDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
								else {									
									if(!(barrowList.equals(graphInfo.getDataColLabels3())))
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,barrowList.get(j).toString());
									else
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"");
								}
							}
							else
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"");
							
							
							//Y_AXIS_TITLE start
							if(barDataLabel != null && (barDataLabel.equalsIgnoreCase("null") || barDataLabel.equalsIgnoreCase("Data")))
							{
								if(graphInfo.getDataColLabels3() != null && graphInfo.getDataColLabels3().size() >= barrowListsize)
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[barYAxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
								else
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[barYAxisTitle"+graphInfo.getDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
								
							}
							else
							{
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[barYAxisTitle"+graphInfo.getDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
							}
							//Y_AXIS_TITLE end
							
							//X_AXIS_TITLE start
							if(barColLabel != null && !barColLabel.equalsIgnoreCase("null"))
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"[[barXAxisTitle]]");
							/*else
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"");*/
							//X_AXIS_TITLE end
							
							//Z_AXIS_TITLE start
							if(isBarLegendVisible)
							{
								if((barRowLabel != null && barRowLabel.equalsIgnoreCase("Legend")))
								{
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE+":","");
								}
								else
								{
								/*	if(graphInfo.getDataColLabels3() != null && graphInfo.getDataColLabels3().size() >1)
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
									else*/
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,barRowLabel);
								}
							}
							else
							{								
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE+": ","");
							}
							//Z_AXIS_TITLE end
		
							
							graphs.setBalloonText(mouseOverString);
						}
						else
						{
							graphs.setBalloonText("");
						}
		
						if(barrowList.size()==0 || barRowLabel.equals(barColLabel))//Added or condition for bug no 11496
							graphs.setFillColorsField("color");
						graphs.setPrecision(digitsBarftDecimal);
						graphs.setType("column");			 
						graphs.setTopRadius("bar");
						graphs.setFillAlphas(1);
						graphs.setLineThickness(0);
						graphs.setLineAlpha(0);
		
						//Transperency of Bar
						//double newTransparency = ((100-getTransparency)/100);
						//tranceperancy
						if(graphInfo.getGraphProperties().getTranceperancy()>0)
						{	 
							double transperency =graphInfo.getGraphProperties().getTranceperancy();
							double barTransparency = ((100-transperency)/100); 
							graphs.setFillAlphas(barTransparency);
						}
						//dataValueProperties.dataValuePoint.dataValuePointVisible
						if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValuePoint().isDataValuePointVisible())
						{
							String labelPosition = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValuePoint().getPosition();
							if(labelPosition.equalsIgnoreCase("Top"))
							{
								labelPosition = "top";
							}
							if(labelPosition.equalsIgnoreCase("Bottom"))
							{
								labelPosition = "inside";
							}
							if(labelPosition.equalsIgnoreCase("Center"))
							{
								labelPosition = "middle";
							}
							//Vertical 
							graphs.setLabelRotation(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValuePoint().getAmRotationAngle());
							String unEscapeHtml = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValuePoint().getDataValuePointFormatText();
//							String dataVAlues = unescapeHtml(unEscapeHtml);
//							dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[["+label+"]]");
//							dataVAlues = dataVAlues.replace("[["+label+"]]", "[["+label+"]]"+precisionBarLabel);
							String dataVAlues = unescapeHtml(unEscapeHtml);
							// Auto Value check for Data Labels
							if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isAutovalue()) {
							    dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
							} else {
							    dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[" + label + "]]");
							    dataVAlues = dataVAlues.replace("[[" + label + "]]", "[[" + label + "]]" + precisionBarLabel);
							}
							dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_VALUE,"[[truncatedLabel]]");					
							
							
							if((!graphInfo.getDataColLabels3().isEmpty() || !graphInfo.getTheDataColLabels4().isEmpty()) && tempBarLine)
							{	
								countmm = 0;
								for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{	
										++countmm;
										String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
										if(sameRowflag)
										{
											desLabel1 = (m+graphInfo.getDataColLabels3().get(m).toString()+barDataLabel).replaceAll("[^\\s\\w]*", "");
										}
										//dataVAlues = StringUtil.replace(dataVAlues,"$BAR-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionBarLabel);
										if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isAutovalue()) {
										    dataVAlues = StringUtil.replace(dataVAlues, "$BAR-Y-AXIS_VALUE" + countmm + "$", "[[value]]");
										} else {
										    dataVAlues = StringUtil.replace(dataVAlues, "$BAR-Y-AXIS_VALUE" + countmm + "$", "[[Abs" + desLabel1 + "]]" + precisionBarLabel);
										}
										dataVAlues = StringUtil.replace(dataVAlues,"$BAR-Y-AXIS_TITLE"+countmm+"$",graphInfo.getDataColLabels3().get(m).toString());
								}
								countmm = 0;
								for(int m=0; m<graphInfo.getTheDataColLabels4().size();m++)	{	
										++countmm;
										String desLabel1 = m+graphInfo.getTheDataColLabels4().get(m).toString().replaceAll("[^\\s\\w]*", "");								
										//dataVAlues = StringUtil.replace(dataVAlues,"$LINE-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLineLabel);	
										if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isAutovalue()) {
										    dataVAlues = StringUtil.replace(dataVAlues, "$LINE-Y-AXIS_VALUE" + countmm + "$", "[[value]]");
										} else {
										    dataVAlues = StringUtil.replace(dataVAlues, "$LINE-Y-AXIS_VALUE" + countmm + "$", "[[Abs" + desLabel1 + "]]" + precisionLineLabel);
										}
										dataVAlues = StringUtil.replace(dataVAlues,"$LINE-Y-AXIS_TITLE"+countmm+"$",graphInfo.getTheDataColLabels4().get(m).toString());
								}
							}
							
							if(barrowList != null && !barrowList.isEmpty())
							{
								if(isBarLegendVisible && graphInfo.getDataColLabels3().size() > 1 && colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= barrowListsize)
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"[[barYAxisTitle"+graphInfo.getGraphData().getColLabelsName().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
								else if(graphInfo.getDataColLabels3().size() == 1 && colLabelsName)
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"[[barYAxisTitle"+graphInfo.getGraphData().getColLabelsName().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
								else
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,barrowList.get(j).toString());
							}
							else
								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"");
							
							//Z_AXIS_TITLE start
							if(isBarLegendVisible)
							{
								if((barRowLabel != null && barRowLabel.equalsIgnoreCase("Legend")))
								{
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE+":","");
								}
								else
								{
									/*if(!(graphInfo.getDataColLabels3() != null && graphInfo.getDataColLabels3().size() >1))/*
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
									else*/
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,barRowLabel);
								}
							}
							/*else
							{
								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
							}*/
							//Z_AXIS_TITLE end
		
							//Y_AXIS_TITLE start
							if(barDataLabel != null && (barDataLabel.equalsIgnoreCase("null") || barDataLabel.equalsIgnoreCase("Data")))
							{
								if(graphInfo.getDataColLabels3() != null && graphInfo.getDataColLabels3().size() >= barrowListsize)
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[barYAxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
								else
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[barYAxisTitle"+graphInfo.getDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
								
							}
							else
							{
								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[barYAxisTitle"+graphInfo.getDataColLabels3().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
							}
							//Y_AXIS_TITLE end
							
							//X_AXIS_TITLE start
							if(barColLabel != null && !barColLabel.equalsIgnoreCase("null"))
								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"[[barXAxisTitle]]");
						/*	else
								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"");*/
							//X_AXIS_TITLE end
		
							dataVAlues = Parser.unescapeEntities(dataVAlues, false);	
							graphs.setLabelPosition(labelPosition);
							graphs.setLabelText(dataVAlues);//values on bar
							graphs.setColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValuePoint().getFontProperties().getFontColor());//values on bar color
							graphs.setFontSize(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValuePoint().getFontProperties().getFontSize());
							graphs.setShowAllValueLabels(true);
		
						}
		
		
		
						List<String> fillColorsList = new ArrayList<String>();
						if(!graphInfo.getCmbBarColorInfoList().isEmpty()) {
							if(graphInfo.getDataColLabels3().size()==1 && graphInfo.getTheDataColLabels4().size()==1 
								&& (graphInfo.getGraphData().getCmbBarcolLabel() != null && graphInfo.getGraphData().getCmbBarrowLabel() == null
								&& graphInfo.getGraphData().getCmbLinerowLabel() == null) || (graphInfo.getGraphData().getCmbBarcolLabel() == null && ( (graphInfo.getGraphData().getCmbBarrowLabel() != null
								&& graphInfo.getGraphData().getCmbLinerowLabel() == null) || (graphInfo.getGraphData().getCmbBarrowLabel() == null  && graphInfo.getGraphData().getCmbLinerowLabel() != null)))){
								fillColorsList.add("#000000");
							}
							else {
							  fillColorsList.add(barColor[graphInfo.getCmbBarColorInfoList().get(j)%colorLength]);
							}
							
						}		
						String tmp = null; 
						if(isBarLegendVisible){
							
							tmp = barrowList.get(j).toString();//Added for Bug SDEVAPR20-369 (16 Dec 2020)
							if(!dateBarRowList.isEmpty() && dateBarRowList.size() > j && barrowListsize==dateBarRowList.size() //Added for Bug SDEVAPR20-369 (16 Dec 2020)
								&& null != dateBarRowList.get(j) && !dateBarRowList.get(j).equals(AppConstants.NULL_DISPLAY_VALUE)) {
								String stringFormat;
								stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
								stringFormat = stringFormat.replaceAll("&#39;", "'");
								Calendar cal = Calendar.getInstance();
								Date axisDate = new Date();
								axisDate = (Date) dateBarRowList.get(j);
								cal.setTime(axisDate);
								stringFormat=stringFormat.trim();
								tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
							}
							
							switch(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
							{
							case "auto":
								//tmp = barrowList.get(j).toString();
								/*if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= barrowListsize)
									tmp = graphInfo.getGraphData().getColLabelsName().get(j).toString();*/
								int truncateCharLimitAuto = 15;
								if (tmp.length() > truncateCharLimitAuto)
									tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
								break;
							case "custom":
								//tmp = barrowList.get(j).toString();
								/*if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= barrowListsize)
									tmp = graphInfo.getGraphData().getColLabelsName().get(j).toString();*/
								int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
								if (tmp.length() > truncateCharLimit)
									tmp = tmp.substring(0, truncateCharLimit)+"..";
								break;
							/*default:
								tmp = barrowList.get(j).toString();
								break;*/
		
							}
							if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= barrowListsize
									&& barRowLabel != null && (barRowLabel.equalsIgnoreCase("Legend") || barRowLabel.equalsIgnoreCase(barColLabel)))
								tmp = graphInfo.getGraphData().getColLabelsName().get(j).toString();
							if(tmp != null)
								tmp = Parser.unescapeEntities(tmp, false);
							graphs.setTitle(tmp);
		
							if(sameRowflag)
							{
								graphs.setValueField(barrowList.get(j).toString()+barDataLabel);
								graphs.setDescriptionField("Abs"+barrowList.get(j).toString()+barDataLabel);
							}
							else
							{
								graphs.setValueField(barrowList.get(j).toString());
								graphs.setDescriptionField("Abs"+barrowList.get(j).toString());
							}
		
						}else{
							//graphs.setTitle(barrowList.get(j).toString());
							graphs.setTitle(graphInfo.getDataColLabels3().get(0).toString());
							if(sameRowflag)
							{
								graphs.setValueField(graphInfo.getGraphData().getCmbBardataLabel()+barDataLabel);
								graphs.setDescriptionField("Abs"+graphInfo.getGraphData().getCmbBardataLabel());
							}
							else
							{
								graphs.setValueField(graphInfo.getGraphData().getCmbBardataLabel());
								graphs.setDescriptionField("Abs"+graphInfo.getGraphData().getCmbBardataLabel());
							}
						}
		
		
		
						if(graphInfo.getGraphProperties().getBarProperties().getType() == 2)
						{
		
							graphs.setTopRadius("1");
						}
						else if(graphInfo.getGraphProperties().getBarProperties().getType() == 3)
						{
		
							graphs.setTopRadius("0");
						}
						else
						{
							graphs.setTopRadius("null");
						}
						//Area Gradient Start.
						if (graphInfo.getGraphProperties(). getGraphArea().getGradient().isVisible())
						{
							fillColorsList.add(graphInfo.getGraphProperties().getGraphArea().getGradient().getColor());
						}
		
						//Bar Gradient Start.
						if(graphInfo.getGraphProperties().getBarProperties().getGradient().isVisible())
						{
							fillColorsList.add(graphInfo.getGraphProperties().getBarProperties().getGradient().getColor());
							graphs.setFillColorsField("");//Added for Bug #15383
							if(graphInfo.getGraphProperties().getBarProperties().getGradient().isTransparent()){
								graphs.setFillAlphas(0.50);
							}
						}
						//Bar gradient End
						//Corner radius 
						if(graphInfo.getGraphProperties().getBarProperties().getCornerRadius() > 0)
						{
							graphs.setCornerRadiusTop(graphInfo.getGraphProperties().getBarProperties().getCornerRadius());
		
						}
		
						graphs.setFillColors(fillColorsList);
						graphsList.add(graphs);
						startIndex++;
					}
					//trend Bar start
					if(isBarTrend)
					{
						isBarTrend=false;
						 String label;
						 for(int c=0;c<noOfBarTrendLines;c++)
						 {
							 Graphs graphs = new Graphs();
							 graphs.setShowHandOnHover(true);////feature req 13494
							 graphs.setLineThickness((int)trendBarLineThickness.get(c));
							 graphs.setLineAlpha(1);
							 graphs.setType("line");
							 graphs.setLabelText("[[TrendLabelBar" + c+ "]]" );
							 graphs.setFillAlphas(0);
							 graphs.setValueField(trendBarLineName.get(c).toString()+"trendBar");
							 graphs.setDescriptionField("Abs"+trendBarLineName.get(c).toString()+"trendBar");
							 graphs.setLineColor(trendBarColor.get(c).toString());
							 graphs.setVisibleInLegend(false);
							 graphs.setValueAxis("ValueAxis-1");
							 //line style
							 int lineStyle = Integer.valueOf(trendBarLineStyle.get(c).toString());
							 int trendDashLength = 0;
							 switch (lineStyle) {
							 case 0:
								 trendDashLength = 0;
								 break;
							 case 1:
								 trendDashLength = 7;
								 break;
							 case 2:
								 trendDashLength = 3;
								 break;
							/* case 3:
								 trendDashLength = 1;
								 break;*/
							 }
							 graphs.setDashLength(trendDashLength);//year quater
							 graphs.setFontSize(10);
							 if(!graphInfo.getGraphData().getCmbLinecolLabel().equalsIgnoreCase(trendBarValue.get(c).toString()) && isLegendVisible==false && graphInfo.getGraphData().getCmbBarrowLabel() == null)
							 {
								 graphs.setLineAlpha(0);
								 graphs.setLineThickness(0);
								 graphs.setLabelText("");
							 }
							 graphsList.add(graphs);
						 }
					 
					}
				}
		/*}*/
		
		
		
		startIndex=startIndexclone;
		
		 int lineAlpha = 0;
		 int bulletAlpha = 0;
		 String type = "line";
		 boolean isLine = false;
		 int bulletBorderAlpha = 0;
		 switch(graphInfo.getGraphProperties().getGraphLineProperties().getType())
			{
			case 1:lineAlpha =1;bulletAlpha = 0; bulletBorderAlpha = 0;isLine=true;break;
			case 2:lineAlpha = 0;bulletAlpha = 1;bulletBorderAlpha = 1;break;
			case 3:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;type = "smoothedLine";break;
			case 4:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;type = "step";break;
			default:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;break;
			}
		 
		 if(graphInfo.getGraphData().getCmbLinedataLabel() != null)
		 {	 
			 // 9 Apr [p.p] bar and line row dimension and hideing measure
			 List FinalGraphsList = new ArrayList();
				
				
				if(graphInfo.getGraphData().getCmbBarrowLabel()!=null
						&& !graphInfo.getGraphData().getCmbBarrowLabel().equalsIgnoreCase("Legend") 
						&& graphInfo.getGraphData().getCmbLinerowLabel()!=null
						&& !graphInfo.getGraphData().getCmbLinerowLabel().equalsIgnoreCase("Legend"))
				{
					for(int i=0;i<hideGraphsListLine.size();i++)
					{
						
						int startInd = (int) hideGraphsListLine.get(i)*linerowListsize;
						int endInd = (((int) hideGraphsListLine.get(i)+1)*linerowListsize);
							for(int j =startInd ;j<endInd;j++)
							{
								FinalGraphsList.add(j);//cahnges [p.p]
							}
							
					}
				hideGraphsListLine = new ArrayList();
				hideGraphsListLine.addAll(FinalGraphsList);
				
				}
			 // 9 Apr [p.p]
			 for (int j = 0; j < linerowListsize; j++) {
				 if((j==startIndex || barrowListsize < Quantity) && !hideGraphsListLine.contains(j)){
					 if(paginationLineIndex==startIndex)
						 return graphsList;
					 Graphs graphs = new Graphs();
					 graphs.setShowHandOnHover(true);////feature req 13494
					 String desLabel = "";
					 if(isLineLegendVisible){
						 desLabel = "Abs"+j+linerowList.get(j).toString().replaceAll("[^\\s\\w]*","");
					 }else{
						 desLabel = "Abs"+j+graphInfo.getGraphData().getCmbLinedataLabel().replaceAll("[^\\s\\w]*","");
					 }
					 if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().isMouseOverTextEnable())
					 {

						 String mouseOverString = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverFormatText();
						 if(mouseOverString!=null){
							 mouseOverString=mouseOverString.replace("&lt;/br&gt", "");
							 mouseOverString=mouseOverString.replace("&lt;br&gt", "");
						 }
//						 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[["+desLabel+"]]");
//						 mouseOverString = mouseOverString.replace("[["+desLabel+"]]", "[["+desLabel+"]]"+precisionLineLabel);
						 if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isAutovalue()) {
							    mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
							} else {
							    mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[" + desLabel + "]]");
							    mouseOverString = mouseOverString.replace("[[" + desLabel + "]]", "[[" + desLabel + "]]" + precisionLineLabel);
							}
						 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_VALUE,"[[truncatedLabel]]");
						 
						
						 if((!graphInfo.getDataColLabels3().isEmpty() || !graphInfo.getTheDataColLabels4().isEmpty()) && tempBarLine)
							{	
								countmm = 0;
								for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{	
										++countmm;
										String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
										if(sameRowflag)
										{
											desLabel1 = (m+graphInfo.getDataColLabels3().get(m).toString()+barDataLabel).replaceAll("[^\\s\\w]*", "");
										}
										mouseOverString = StringUtil.replace(mouseOverString,"$BAR-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionBarLabel);	
										mouseOverString = StringUtil.replace(mouseOverString,"$BAR-Y-AXIS_TITLE"+countmm+"$",graphInfo.getDataColLabels3().get(m).toString());
								}
								countmm = 0;
								for(int m=0; m<graphInfo.getTheDataColLabels4().size();m++)	{	
										++countmm;
										String desLabel1 = m+graphInfo.getTheDataColLabels4().get(m).toString().replaceAll("[^\\s\\w]*", "");								
										mouseOverString = StringUtil.replace(mouseOverString,"$LINE-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLineLabel);														
										mouseOverString = StringUtil.replace(mouseOverString,"$LINE-Y-AXIS_TITLE"+countmm+"$",graphInfo.getTheDataColLabels4().get(m).toString());
								}
							}
						 if(linerowList != null && !linerowList.isEmpty())
						 {
							 if(isLegendVisible && graphInfo.getTheDataColLabels4().size() > 1 && colLabelsName2 && graphInfo.getGraphData().getColLabelsName2().size() >= linerowListsize)
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"[[lineYAxisTitle"+graphInfo.getGraphData().getColLabelsName2().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 else if(graphInfo.getTheDataColLabels4().size() == 1 && colLabelsName2)
								 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"[[lineYAxisTitle"+graphInfo.getGraphData().getColLabelsName2().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 else
								 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,linerowList.get(j).toString());	 
						 }
						 /*else
							 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"[[title]]");*/
							 
						 
						 //Y_AXIS_TITLE start
						 if(lineDataLabel != null && (lineDataLabel.equalsIgnoreCase("null") || lineDataLabel.equalsIgnoreCase("Data")))
						 {
							 if(graphInfo.getTheDataColLabels4() != null && graphInfo.getTheDataColLabels4().size() >= linerowListsize)
								 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 else
								 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 
						 }
						 else
						 {
							 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
						 }
							
						
						 //Y_AXIS_TITLE end

						 //X_AXIS_TITLE start
						 if(lineColLabel != null && !lineColLabel.equalsIgnoreCase("null"))
							 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"[[lineXAxisTitle]]");
					/*	 else
							 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"");*/
						 //X_AXIS_TITLE end

						 //Z_AXIS_TITLE start
						 if(isLineLegendVisible)
						 {
							 if(!(lineRowLabel != null && lineRowLabel.equalsIgnoreCase("Legend")))
							 {/*
								 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
							 }
							 else
							 {*/
								 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,lineRowLabel);
							 }
						 }
						 /*else
						 {
							 mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
						 }*/
						 //Z_AXIS_TITLE end		
						 graphs.setBalloonText(mouseOverString);
					 }
					 else
					 {
						 graphs.setBalloonText("");
					 }



					 if(graphInfo.getGraphProperties().getTranceperancy()>0)
					 {	 
						 double transperency =graphInfo.getGraphProperties().getTranceperancy();
						 double barTransparency = ((100-transperency)/100); 
						 graphs.setFillAlphas(barTransparency);
					 }

					 //dataValueProperties.dataValuePoint.dataValuePointVisible
					 if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValuePoint().isDataValuePointVisible())
					 {
						 String labelPosition = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValuePoint().getPosition();
						 if(labelPosition.equalsIgnoreCase("Top"))
						 {
							 labelPosition = "top";
						 }
						 if(labelPosition.equalsIgnoreCase("Bottom"))
						 {
							 labelPosition = "bottom";//inside[14560]
						 }
						 if(labelPosition.equalsIgnoreCase("Center"))
						 {
							 labelPosition = "middle";
						 }
						 //Vertical 
						 graphs.setLabelRotation(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValuePoint().getAmRotationAngle());
						 String unEscapeHtml = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValuePoint().getDataValuePointFormatText();
//						 String dataVAlues = unescapeHtml(unEscapeHtml);
//						 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[["+desLabel+"]]");
//						 dataVAlues = dataVAlues.replace("[["+desLabel+"]]", "[["+desLabel+"]]"+precisionLineLabel);
						 String dataVAlues = unescapeHtml(unEscapeHtml);
						// Auto Value check for Line Data Labels
						if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isAutovalue()) {
						    dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
						} else {
						    dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[" + desLabel + "]]");
						    dataVAlues = dataVAlues.replace("[[" + desLabel + "]]", "[[" + desLabel + "]]" + precisionLineLabel);
						}
						 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_VALUE,"[[truncatedLabel]]");
						 
						 
						 if((!graphInfo.getDataColLabels3().isEmpty() || !graphInfo.getTheDataColLabels4().isEmpty()) && tempBarLine)
							{	
								countmm = 0;
								for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{	
										++countmm;
										String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
										if(sameRowflag)
										{
											desLabel1 = (m+graphInfo.getDataColLabels3().get(m).toString()+barDataLabel).replaceAll("[^\\s\\w]*", "");
										}
										//dataVAlues = StringUtil.replace(dataVAlues,"$BAR-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionBarLabel);
										if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isAutovalue()) {
										    dataVAlues = StringUtil.replace(dataVAlues, "$BAR-Y-AXIS_VALUE" + countmm + "$", "[[value]]");
										} else {
										    dataVAlues = StringUtil.replace(dataVAlues, "$BAR-Y-AXIS_VALUE" + countmm + "$", "[[Abs" + desLabel1 + "]]" + precisionBarLabel);
										}
										dataVAlues = StringUtil.replace(dataVAlues,"$BAR-Y-AXIS_TITLE"+countmm+"$",graphInfo.getDataColLabels3().get(m).toString());
								}
								countmm = 0;
								for(int m=0; m<graphInfo.getTheDataColLabels4().size();m++)	{	
										++countmm;
										String desLabel1 = m+graphInfo.getTheDataColLabels4().get(m).toString().replaceAll("[^\\s\\w]*", "");								
										//dataVAlues = StringUtil.replace(dataVAlues,"$LINE-Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLineLabel);
										if (graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isAutovalue()) {
										    dataVAlues = StringUtil.replace(dataVAlues, "$LINE-Y-AXIS_VALUE" + countmm + "$", "[[value]]");
										} else {
										    dataVAlues = StringUtil.replace(dataVAlues, "$LINE-Y-AXIS_VALUE" + countmm + "$", "[[Abs" + desLabel1 + "]]" + precisionLineLabel);
										}
										dataVAlues = StringUtil.replace(dataVAlues,"$LINE-Y-AXIS_TITLE"+countmm+"$",graphInfo.getTheDataColLabels4().get(m).toString());
								}
							}
						 if(linerowList != null && !linerowList.isEmpty())
						 {
							 if(isLegendVisible && graphInfo.getTheDataColLabels4().size() > 1 && colLabelsName2 && graphInfo.getGraphData().getColLabelsName2().size() >= linerowListsize)
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"[[lineYAxisTitle"+graphInfo.getGraphData().getColLabelsName2().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 else if(graphInfo.getTheDataColLabels4().size() == 1 && colLabelsName2)
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"[[lineYAxisTitle"+graphInfo.getGraphData().getColLabelsName2().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 else
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,linerowList.get(j).toString());
						 }
//						 else
//							 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"[[title]]");
						
						 
						 //Z_AXIS_TITLE start
						 if(isLineLegendVisible)
						 {
							 if(!(lineRowLabel != null && lineRowLabel.equalsIgnoreCase("Legend")))
							 {/*
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
							 }
							 else
							 {*/
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,lineRowLabel);
							 }
						 }
				/*		 else
						 {
							 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
						 }
				*/		 //Z_AXIS_TITLE end

						 //Y_AXIS_TITLE start
						 if(lineDataLabel != null && (lineDataLabel.equalsIgnoreCase("null") || lineDataLabel.equalsIgnoreCase("Data")))
						 {
							 if(graphInfo.getTheDataColLabels4() != null && graphInfo.getTheDataColLabels4().size() >= linerowListsize)
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
							 else
								 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
							
						 }
						 else
						 {
							 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[lineYAxisTitle"+graphInfo.getTheDataColLabels4().get(0).toString().replaceAll("[^\\s\\w]*","")+"]]");
						 }						
							
						 //Y_AXIS_TITLE end

						 //X_AXIS_TITLE start
						 if(lineColLabel != null && !lineColLabel.equalsIgnoreCase("null"))
							 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"[[lineXAxisTitle]]");
				/*		 else
							 dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"");
				*/		 //X_AXIS_TITLE end

						 graphs.setLabelPosition(labelPosition);
						 graphs.setLabelText(dataVAlues);//values on bar
						 graphs.setColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValuePoint().getFontProperties().getFontColor());//values on bar color
						 graphs.setFontSize(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValuePoint().getFontProperties().getFontSize());
						 graphs.setShowAllValueLabels(true);
					 }

					 List<String> fillColorsList = new ArrayList<>();
					 if(!graphInfo.getCmbLineColorInfoList().isEmpty()) {
						 fillColorsList.add(lineColors[graphInfo.getCmbLineColorInfoList().get(j)%lineColors.length]);
						 colorWiseLineIndex.add(graphInfo.getCmbLineColorInfoList().get(j)%lineColors.length);
					 }
					 if (graphInfo.getGraphProperties().getGraphArea().getGradient().isVisible())
					 {
						 fillColorsList.add(graphInfo.getGraphProperties().getGraphArea().getGradient().getColor());
					 }
					 String tmp = null; 
					 if(isLineLegendVisible){
						 
						 tmp = linerowList.get(j).toString();//Added for Bug SDEVAPR20-369 (16 Dec 2020)
						 if(!dateLineRowList.isEmpty() && dateLineRowList.size() > j && linerowList.size()==dateLineRowList.size()//Added for Bug SDEVAPR20-369 (16 Dec 2020)
								 && null != dateLineRowList.get(j) && !dateLineRowList.get(j).equals(AppConstants.NULL_DISPLAY_VALUE)) {
							 String stringFormat;
							 stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
							 stringFormat = stringFormat.replaceAll("&#39;", "'");
							 Calendar cal = Calendar.getInstance();
							 Date axisDate = new Date();
							 axisDate = (Date) dateLineRowList.get(j);
							 cal.setTime(axisDate);
							 stringFormat=stringFormat.trim();
							 tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
						 }
						 
						 switch(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
						 {
						 case "auto":
							// tmp = linerowList.get(j).toString();
							 /*if(colLabelsName2 && graphInfo.getGraphData().getColLabelsName2().size() >= linerowListsize)
									tmp = graphInfo.getGraphData().getColLabelsName2().get(j).toString();*/
							 int truncateCharLimitAuto = 15;
							 if (tmp.length() > truncateCharLimitAuto)
								 tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
							 break;
						 case "custom":
							 //tmp = linerowList.get(j).toString();
							 /*if(colLabelsName2 && graphInfo.getGraphData().getColLabelsName2().size() >= linerowListsize)
									tmp = graphInfo.getGraphData().getColLabelsName2().get(j).toString();*/
							 int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
							 if (tmp.length() > truncateCharLimit)
								 tmp = tmp.substring(0, truncateCharLimit)+"..";
							 break;
						 /*default:
							 tmp = linerowList.get(j).toString();
							 break;*/

						 }
						 if(colLabelsName2 && graphInfo.getGraphData().getColLabelsName2().size() >= linerowListsize
						 	&& lineRowLabel != null && lineRowLabel.equalsIgnoreCase("Legend"))
								tmp = graphInfo.getGraphData().getColLabelsName2().get(j).toString();
						 if(tmp != null)
								tmp = Parser.unescapeEntities(tmp, false);
						 graphs.setTitle(tmp);

						 graphs.setValueField(linerowList.get(j).toString());
						 graphs.setDescriptionField("Abs"+linerowList.get(j).toString());
					 }else{
						 
						 graphs.setTitle(graphInfo.getGraphData().getCmbLinedataLabel());//graphs.setTitle(graphInfo.getTheDataColLabels4().get(0).toString()); 13368
						 graphs.setValueField(graphInfo.getGraphData().getCmbLinedataLabel());
						 graphs.setDescriptionField("Abs"+graphInfo.getGraphData().getCmbLinedataLabel());
					 }

					 if(graphInfo.getGraphProperties().getGraphLineProperties().getType() != 3)//Added for Bug #12669
					 	graphs.setFillColorsField("color");
					 graphs.setPrecision(digitsLineftDecimal);
					 graphs.setType(type);
					 graphs.setFillAlphas(0);
					 graphs.setLineAlpha(lineAlpha);
					 graphs.setLineThickness(1);
					 graphs.setValueAxis("ValueAxis-2");
					 graphs.setBulletAlpha(bulletAlpha);			 
					 graphs.setBullet(bulletTypeArray[j%bulletTypeArray.length]);
					 if(!graphInfo.getCmbLineColorInfoList().isEmpty()) {
						 graphs.setBulletColor(bulletColor[graphInfo.getCmbLineColorInfoList().get(j)%bulletColor.length]);
					 }else {
						 graphs.setBulletColor(bulletColor[j]);
					 }
					 graphs.setBulletSize(bulletSizeList.get(j));
					 graphs.setDashLength(lineStyleList.get(j));
					 graphs.setLineThickness(lineThicknessList.get(j));
					 //graphs.setBulletBorderAlpha(0);
					 if(bulletStyleList.get(j) == 0)
					 {
						 graphs.setBulletBorderColor(borderColorList.get(j));
						 graphs.setBulletBorderThickness(borderWidthList.get(j));
						 if(borderWidthList.get(j) > 0)
						 {
							 if(isLine)
							 {
								 bulletBorderAlpha = 0;
							 }
							 else
							 {	
								 bulletBorderAlpha = 1;
							 }
						 }
						 graphs.setBulletBorderAlpha(bulletBorderAlpha);
					 }
					 StringBuilder sb = new StringBuilder();

					 for(String str : fillColorsList){
						 sb.append(str);
					 }

					 graphs.setLineColor(sb.toString());
					 graphsList.add(graphs);
					 //	 graphs.setFillColors(fillColorsList);
					 //Trend line start
					 if(isTrend)//trend
					 {
						 isTrend=false;
						 String label;
						 for(int c=0;c<noOfTrendLines;c++)
						 {
							 graphs = new Graphs();
							 graphs.setShowHandOnHover(true);////feature req 13494
							 graphs.setLineThickness((int)trendLineThickness.get(c));
							 graphs.setLineAlpha(1);
							 graphs.setType("line");
							 graphs.setLabelText("[[TrendLabel" + c+ "]]" );
							 graphs.setFillAlphas(0);
							 graphs.setValueField(trendLineName.get(c).toString()+"trendLine");
							 graphs.setDescriptionField("Abs"+trendLineName.get(c).toString()+"trendLine");
							 graphs.setLineColor(trendColor.get(c).toString());
							 graphs.setVisibleInLegend(false);
							 graphs.setValueAxis("ValueAxis-2");
							 //line style
							 int lineStyle = Integer.valueOf(trendLineStyle.get(c).toString());
							 int trendDashLength = 0;
							 switch (lineStyle) {
							 case 0:
								 trendDashLength = 0;
								 break;
							 case 1:
								 trendDashLength = 3;
								 break;
							 case 2:
								 trendDashLength = 7;
								 break;
							/* case 3:
								 trendDashLength = 1;
								 break;*/
							 }
							 graphs.setDashLength(trendDashLength);//year quater
							 graphs.setFontSize(10);
							 if(!graphInfo.getGraphData().getCmbLinecolLabel().equalsIgnoreCase(trendValue.get(c).toString()) && isLegendVisible==false && graphInfo.getGraphData().getCmbLinerowLabel() == null)
							 {
								 graphs.setLineAlpha(0);
								 graphs.setLineThickness(0);
								 graphs.setLabelText("");
							 }
							 graphsList.add(graphs);
						 }
					 }
					 //Trend line end
					 
					 startIndex++;
				 }
			 }
		 }
		
		
		 setDisplayColorIndex(colorWiseLineIndex,graphInfo);	
		
	return graphsList;	
	}
	
	private static final String[] appendValue(String[] s1 ,String newValue) {

		  String[] erg = new String[s1.length + 1];
		  erg[erg.length-1] = newValue;
	      System.arraycopy(s1, 0, erg, 0, s1.length);

	      return erg;

	  }
	
	private static void setDisplayColorIndex(List colorWiseLineIndex, GraphInfo graphInfo) {
		if(colorWiseLineIndex != null && !colorWiseLineIndex.isEmpty()) {
			HashSet<String> uniqueElements = new HashSet<>(colorWiseLineIndex);
			colorWiseLineIndex = new ArrayList<>(uniqueElements);
		}
		graphInfo.setDisplayLineIndexList(colorWiseLineIndex);
	}
	
	}
