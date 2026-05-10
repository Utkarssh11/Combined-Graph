package com.elegantjbi.amcharts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.parser.Parser;

import com.elegantjbi.amcharts.vo.Balloon;
import com.elegantjbi.amcharts.vo.CategoryAxis;
import com.elegantjbi.amcharts.vo.ChartCursor;
import com.elegantjbi.amcharts.vo.GraphJson;
import com.elegantjbi.amcharts.vo.GraphLegendJson;
import com.elegantjbi.amcharts.vo.Graphs;
import com.elegantjbi.amcharts.vo.Guides;
import com.elegantjbi.amcharts.vo.Legend;
import com.elegantjbi.amcharts.vo.Responsive;
import com.elegantjbi.amcharts.vo.ValueAxes;
import com.elegantjbi.amcharts.vo.ValueAxis;
import com.elegantjbi.amcharts.vo.ValueScrollbar;
import com.elegantjbi.core.olap.CubeLabelInfo;
import com.elegantjbi.core.olap.CubeRankDataLabel;
import com.elegantjbi.entity.graph.GraphInfo;
import com.elegantjbi.service.kpi.KPIConstants;
import com.elegantjbi.util.AppConstants;
import com.elegantjbi.util.GeneralFiltersUtil;
import com.elegantjbi.util.GraphsUtil;
import com.elegantjbi.util.StringUtil;
import com.elegantjbi.util.logger.ApplicationLog;
import com.elegantjbi.vo.properties.graph.ReferenceLine;
public class AmCombinedGraph {
	
	public static String amJson(GraphInfo graphInfo,boolean isContextFilter) {
		return amJson(graphInfo, isContextFilter, false);
	}

	 //Start
	 /**
	 * @param graphInfo
	 * @return
	 */
	public static String amJson(GraphInfo graphInfo,boolean isContextFilter,boolean isFromPredictive) 
	 {
		 ObjectMapper objectMapper = new ObjectMapper();
		 
		 GraphJson graphJson = null;
		 
		 List jsonList = new ArrayList();
		 List<String> bulletList = new ArrayList<String>();
		 List<Integer> bulletSizeList = new ArrayList<Integer>();
		 List<Integer> lineStyleList = new ArrayList<Integer>();
		 List<Integer> lineThicknessList = new ArrayList<Integer>();
		 List<String> borderColorList = new ArrayList<String>();
		 List<Integer> borderWidthList = new ArrayList<Integer>();
		 List<Integer> bulletStyleList = new ArrayList<Integer>();
		 
		 ValueAxes valuebarAxes = new ValueAxes();
		 ValueAxes valuelineAxes = new ValueAxes();
		 
		 String[] barColor =new String[]{"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
				    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
				    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
				    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
				    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
				    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
				    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 
		 //String[] lineColors =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		
		 String[] lineColors =new String[] {"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
			    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
			    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
			    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
			    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
			    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
			    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};																						
		 
		// String[] bulletColor =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};;
		 
		 String[] bulletColor =new String[] {"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
			    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
			    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
			    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
			    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
			    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
			    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		 
		 String[] bulletTypeArray = new String[]{"square","round","triangleUp","diamond"};

		 String json = null;

		 String chartType = null;
		 
		 int lineAlpha = 0;
		 boolean rotate=false;
		 String stackType="none";
		 String bullet = null;
		
		 int bulletAlpha = 0;
	
		 String barColLable=graphInfo.getGraphData().getCmbBarcolLabel();
		 String lineColLable=graphInfo.getGraphData().getCmbLinecolLabel();
		 String barDataLable=graphInfo.getGraphData().getCmbBardataLabel();
		 String lineDataLabel=graphInfo.getGraphData().getCmbLinedataLabel();
		 String barRowLabel=graphInfo.getGraphData().getCmbBarrowLabel();
		 String lineRowLabel=graphInfo.getGraphData().getCmbLinerowLabel();
		 
		 
		 List barcolList=graphInfo.getGraphData().getCmbBarcolList();
		 List linecolList=graphInfo.getGraphData().getCmbLinecolList();
		 List bardataList=graphInfo.getGraphData().getCmbBardataList();
		 List linedataList=graphInfo.getGraphData().getCmbLinedataList();
		 List barrowList=graphInfo.getGraphData().getCmbBarrowList();
		 List linerowList=graphInfo.getGraphData().getCmbLinerowList();
		 
		 boolean colLabelsName = false;
		 boolean colLabelsName2 = false;
		//non-clustered bar with colNames
		if(graphInfo.getGraphData().getColLabelsName() != null && graphInfo.getGraphData().getColLabelsName().size()>0)
			colLabelsName = true;
		//non-clustered line with colNames
		if(graphInfo.getGraphData().getColLabelsName2() != null && graphInfo.getGraphData().getColLabelsName2().size()>0)
			colLabelsName2 = true;
		
		 Map<String, String> colLabelsMap =  graphInfo.getGraphProperties().getColLabelsMap();
		 //Line data Label
		 
		 if(isFromPredictive)
			 rotate=true;
		 
		 int barcolListsize=barcolList.size();
		 
		 int barrowListsize=barrowList.size();
		 int linerowListsize=linerowList.size();
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

		//Added for bug no 11496 start
		 int barDataLabelListSize = graphInfo.getDataColLabels3().size();
		 int lineDataLabelListSize = graphInfo.getTheDataColLabels4().size();
		 if(barDataLabelListSize >= 1 || lineDataLabelListSize >= 1)
		 {
			 isBarLegendVisible = true;
			 isLineLegendVisible = true;
		 }
		 else
		 {
			 if((barRowLabel != null && !"Legend".equalsIgnoreCase(barRowLabel) && !barRowLabel.equals(barColLable))
					 || (lineRowLabel != null && !"Legend".equalsIgnoreCase(lineRowLabel)))
			 {
				 isBarLegendVisible = true;
				 isLineLegendVisible = true;
			 }
			 else
			 {
				 isBarLegendVisible = false;
				 isLineLegendVisible = false;
			 }
		 }
		//Added for bug no 11496 end

		 String drillAxis = "drillAxis";
		 String drillBarLegend = "drillBarLegend";
		 String drillLineLegend = "drillLineLegend";
		 List drillBarList = graphInfo.getGraphData().getDrillBarLinkList();
		 List drillLineList = graphInfo.getGraphData().getDrillLineLinkList();
		 List drillcolList = graphInfo.getGraphData().getDrillColLinkList();
		
		 
		 
		
		 
		 graphJson = new GraphLegendJson();
		 //rank maintain the sequence of graph in desc order
		 if(graphInfo.getRankList() != null && graphInfo.getRankList().size() > 0)
			{
				boolean rankEnable = false;
				for (int cnt = 0; cnt < graphInfo.getRankList().size(); cnt++) {
					CubeRankDataLabel rankDataLabel = graphInfo.getRankList().get(cnt);
					if(rankDataLabel.isStatus()
						&& ((null != barRowLabel && rankDataLabel.getColumnName().equalsIgnoreCase(barRowLabel)) || (null != barColLable && rankDataLabel.getColumnName().equalsIgnoreCase(barColLable))))
						rankEnable = true;
				}
				if(rankEnable)
					graphJson.setSortColumns(true);
			}
		 //rank maintian the sequence of graph in desc order
		 
		//Code to maintain the sequence of graph when Advance sort applied (Bug #14832) start
		if(graphInfo.getSortList() != null && graphInfo.getSortList().size() > 0)
		{
			boolean sortEnable = false;
			boolean isAscendingSortEnable = false;
			//System.out.println(graphInfo.getSortList()+" "+barRowLabel+" "+barColLable);
			for (int cnt = 0; cnt < graphInfo.getSortList().size(); cnt++) {
				
				CubeLabelInfo cubeLabelInfo = graphInfo.getSortList().get(cnt); //[bug-SDEVAPR20-979] below and cond is added
				//System.out.println(cubeLabelInfo.isStatus() +"&&"+ cubeLabelInfo.sortType);
				
				if(cubeLabelInfo.isStatus() && cubeLabelInfo.getSortType() == 1 && (graphInfo.getDataColLabels3() !=null && !(graphInfo.getDataColLabels3().size() > 1))
						&& ((null != graphInfo.getRowLabelForLovBar() && cubeLabelInfo.getName().equalsIgnoreCase(graphInfo.getRowLabelForLovBar())) || (graphInfo.getRowLabelForLovBar() == null && null != barColLable && cubeLabelInfo.getName().equalsIgnoreCase(barColLable))) ) {
					sortEnable = true;
					if(!cubeLabelInfo.isDescOrder())//For checking whether descending or not
					{
						isAscendingSortEnable = true;
					}
				}
			}
			if(sortEnable)
				graphJson.setSortColumns(true);
			
			if(isAscendingSortEnable) {
				graphJson.setAscendingSortEnable(true);
			}
		}
		//Code to maintain the sequence of graph when Advance sort applied (Bug #14832) end

		if("Legend".equals(barRowLabel))//Added barRowLabel != "Legend" check for Bug #15046
		{ graphJson.setSortColumns(false); }
		 
		 //Decides the type of graph
		 
		 
		 graphJson.setType("serial");
		 graphJson.setTheme("none");
		 graphJson.setPathToImages("http://cdn.amcharts.com/lib/3/images/");
		 graphJson.setStartEffect("easeOutSine");
		 graphJson.setUsePrefixes(false);
		 graphJson.setColumnSpacing(0);
		 graphJson.setAddClassNames(true);
		 graphJson.setColors(Arrays.asList(barColor));
		 
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

		 graphJson.setChartType("combined");
		 graphJson.setRotate(rotate);

		 //mouseWheelZoomEnabled

		 //graphJson.setMouseWheelZoomEnabled(true);
		
		 
		 
		 
		 
		 
		 
		

		 //--------------------------------------------Graph Area Start------------------------------------------------

		 //Margin

		 
		//Margin start
			/*if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll()==0.0)
			{
				graphJson.setMarginTop(20.0);
				graphJson.setAutoMarginOffset(20.0);
				graphJson.setMarginBottom(20.0);
				graphJson.setMarginLeft(20.0);
				graphJson.setMarginRight(20.0);
				
			}else{*/
				graphJson.setMarginTop(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
				graphJson.setAutoMarginOffset(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
				graphJson.setMarginBottom(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
				graphJson.setMarginLeft(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
				graphJson.setMarginRight(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
			/*}*/
		// data value digits after decimal start
			//----------------------------------------------------3D START---------------------------------------------------//
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isVisible())
			{
				graphJson.setAngle(30);
				graphJson.setDepth3D(30);
			}
			
			
			//----------------------------------------------------3D END---------------------------------------------------//

		 
		 
		 
		 //grid for both axes  tickLen=xAxisProperties.lineProperties.axisMajorLineTickTrendProperties.height
		 //distance=xAxisProperties.labelProperties.distanceFromLine
		 CategoryAxis categoryAxis = new CategoryAxis();
		
		 String xaxisTitle = "";
		 String yaxisTitle ="";
		 String valueAxesPosition="";
		 boolean labelsEnabled=false;
		 boolean inside;
		 if(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().isVisible())
		 {
			 if(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getTitle().equals(""))
			 {
				 xaxisTitle = barColLable;
			 }
			 else
			 {
				 xaxisTitle = graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getTitle();
			 }
			 if(xaxisTitle != null)
				 xaxisTitle = Parser.unescapeEntities(xaxisTitle, false);
			 categoryAxis.setTitle(xaxisTitle);
			 
		 }
		 
		//Adjusted Bar Digit
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
				graphJson.setPrecision(-1);
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
				graphJson.setPrecision(-1);
			} 
		 
			valuebarAxes.setLocale(Locale.getDefault().getLanguage());
			// yaxis labels BAR digits after decimal start
			int digitsBarftDecimal = 0;
			int yaxisPrecision = graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getNumberOfDigits();
			switch(yaxisPrecision)
			{
			case 0:
				valuebarAxes.setPrecision(0);
				break;	
			case 1:
				valuebarAxes.setPrecision(1);
				break;
			case 2:
				valuebarAxes.setPrecision(2);
				break;
			case 3:
				valuebarAxes.setPrecision(3);
				break;
			case 4:
				valuebarAxes.setPrecision(4);
				break;
			case 5:
				valuebarAxes.setPrecision(5);
				break;				
			}
			valuelineAxes.setLocale(Locale.getDefault().getLanguage());
			// yaxis labels BAR digits after decimal start
			int digitsLineftDecimal = 0;
			yaxisPrecision = graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getNumberOfDigits();
			switch(yaxisPrecision)
			{
			case 0:
				valuelineAxes.setPrecision(0);
				break;				
			case 1:
				valuelineAxes.setPrecision(1);
				break;				
			case 2:
				valuelineAxes.setPrecision(2);
				break;				
			case 3:
				valuelineAxes.setPrecision(3);
				break;				
			case 4:
				valuelineAxes.setPrecision(4);
				break;				
			case 5:
				valuelineAxes.setPrecision(5);
				break;				
			} 
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

		 //Bar
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getyAxisTitleTrendProperties().isVisible())
		 {
			 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getyAxisTitleTrendProperties().getTitle().equals(""))
			 {
				 yaxisTitle = graphInfo.getGraphData().getCmbBardataLabel();
				 if(graphInfo.getGraphData().getColLabelsName() != null && graphInfo.getGraphData().getColLabelsName().size()>0 && (barRowLabel != null && !barRowLabel.equalsIgnoreCase("legend")))
					 yaxisTitle = graphInfo.getGraphData().getColLabelsName().get(0).toString();//Added for 11545
				 if(graphInfo.getGraphData().getCmbBarcolLabel() != null && barRowLabel != null && barRowLabel.equals(graphInfo.getGraphData().getCmbBarcolLabel()) && 
						 graphInfo.getDataColLabels3().size() > 1)
					 yaxisTitle = "";
				 if(yaxisTitle != null && yaxisTitle.equalsIgnoreCase("data"))
						yaxisTitle = "";
			 }
			 else
			 {
				 yaxisTitle = graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getyAxisTitleTrendProperties().getTitle();
			 }
			 if(yaxisTitle != null)
				 yaxisTitle = Parser.unescapeEntities(yaxisTitle, false);			 
			 valuebarAxes.setTitle(yaxisTitle);
			 
		 } 
		 // Auto Value toggle: Only set manual unit suffix when Auto Value is OFF
			if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().isAutovalue() == false) {
				 valuebarAxes.setUnit(precisionBarLabel);

			}
			else {
				valuebarAxes.setUnit("");
				valuebarAxes.setPrecision(digitsBarftDecimal);
				valuebarAxes.setUsePrefixes(true);
				List<Map<String, Object>> prefixesOfBigNumbers2 = GraphsUtil.getPrefixes(10);
				valuebarAxes.setPrefixesOfBigNumbers(prefixesOfBigNumbers2);
				graphJson.setUsePrefixes(true);
				//valueAxes.setPrecision(digitsaftDecimal);
				//valueAxes.setLabelFunction("function(value) {if (value >= 1000000) {return (value / 1000000).toFixed(2) + \"M\";} if (value >= 1000) { return (value / 1000).toFixed(2) + \"K\"; } return value.toFixed(2);}");
			}
		 //valuebarAxes.setPrecision(digitsBarftDecimal);
		 //Line
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getyAxisTitleTrendProperties().isVisible())
		 {
			 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getyAxisTitleTrendProperties().getTitle().equals(""))
			 {
				 yaxisTitle = graphInfo.getGraphData().getCmbLinedataLabel();
				 if(graphInfo.getGraphData().getColLabelsName2() != null && graphInfo.getGraphData().getColLabelsName2().size()>0 && (lineRowLabel != null && !lineRowLabel.equalsIgnoreCase("legend")))
					 yaxisTitle = graphInfo.getGraphData().getColLabelsName2().get(0).toString();//Added for 11545
				 if(yaxisTitle != null && yaxisTitle.equalsIgnoreCase("data"))
						yaxisTitle = "";
				 
				 if(graphInfo.getTheDataColLabels4().size() == 1)
				 {
					 yaxisTitle = graphInfo.getTheDataColLabels4().get(0).toString();
					 if(graphInfo.getGraphData().getColLabelsName2() != null && graphInfo.getGraphData().getColLabelsName2().size()>0)
						 yaxisTitle = graphInfo.getGraphData().getColLabelsName2().get(0).toString();//Added for 11545
				 }
			 }
			 else
			 {
				 yaxisTitle = graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getyAxisTitleTrendProperties().getTitle();
			 }
			 if(yaxisTitle != null && colLabelsMap.size() > 0)//for 11545
			 {
			  	String titleWithCollabels = "";
			  	titleWithCollabels = colLabelsMap.get(yaxisTitle);
			 	if(titleWithCollabels != null)
			 	{
			 		yaxisTitle=titleWithCollabels;
			 	}
			 }
			 if(yaxisTitle != null)
				 yaxisTitle = Parser.unescapeEntities(yaxisTitle, false);	
			 valuelineAxes.setTitle(yaxisTitle);
			 
		 } 
		 // Auto Value toggle: Only set manual unit suffix when Auto Value is OFF
		 // Fixed: Use getLinenumberFormat() for line values instead of getBarnumberFormat()
			if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().isAutovalue()== false) {
				 valuelineAxes.setUnit(precisionLineLabel);
					
			}
			else {
				valuelineAxes.setUnit("");
				valuelineAxes.setPrecision(digitsLineftDecimal);
				valuelineAxes.setUsePrefixes(true);
				List<Map<String, Object>> prefixesOfBigNumbers2 = GraphsUtil.getPrefixes(10);
				valuelineAxes.setPrefixesOfBigNumbers(prefixesOfBigNumbers2);
				graphJson.setUsePrefixes(true);
				//valueAxes.setPrecision(digitsaftDecimal);
				//valueAxes.setLabelFunction("function(value) {if (value >= 1000000) {return (value / 1000000).toFixed(2) + \"M\";} if (value >= 1000) { return (value / 1000).toFixed(2) + \"K\"; } return value.toFixed(2);}");
			}

		 //valuelineAxes.setPrecision(digitsLineftDecimal);
		
		 
		 //-----------------------------------------------------------CATEGOTY AXES START---------------------------------------------------------------------------------//
		 //Tick Line Position 
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getAlignment().equalsIgnoreCase("Left"))
		 {
			 categoryAxis.setTickPosition("start");
		 }
		 else
		 {
			 categoryAxis.setTickPosition("middle");
		 }
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getTickPosition() == 2)
		 {
			 categoryAxis.setInside(true);
		 }
		 categoryAxis.setTitleFontSize(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getFontProperties().getFontSize());
		 categoryAxis.setTitleColor(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getFontProperties().getFontColor());
		 
		 if(isFromPredictive)
			 categoryAxis.setTitleRotation(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getRotateCharacter());
		 
		 categoryAxis.setAxisColor(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getColor());
		 categoryAxis.setAxisAlpha(1);
		 categoryAxis.setAxisThickness(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getThickness());

		 //Axis position
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getPosition().equalsIgnoreCase("Top"))
		 {
			 categoryAxis.setPosition("top");
		 }
		 else
		 {
			 categoryAxis.setPosition("bottom");
		 }
		 //Labels Visible
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isVisible())
		 {
			 categoryAxis.setLabelsEnabled(true);
			 categoryAxis.setTickLength(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getHeight());
			 categoryAxis.setLabelOffset(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDistanceFromLine());
			 categoryAxis.setLabelRotation(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getRotationAngle());
			 categoryAxis.setColor(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getFontColor());
			 categoryAxis.setFontSize(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getFontSize());
			 switch(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit())
				{
				case "none":
					categoryAxis.setTruncateLabels("undefined");
					break;
				case "auto":
					categoryAxis.setTruncateLabels("undefined");
					break;
				case "custom":
					categoryAxis.setTruncateLabels(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCustomCharacterLimit());
					break;
				} 
		 } 
		 
		 //Tick Line visibility
		 if(!graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().isVisible())
		 {
			 categoryAxis.setTickLength(0);
		 }
		 else
		 {
			 categoryAxis.setTickLength(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getHeight());
		 }
		 
		 //Axis Line visibility xAxisProperties.lineProperties.visible
		 if(!graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().isVisible())
		 {
			 categoryAxis.setAxisThickness(0);
		 }
		 categoryAxis.setLabelFunction("");
		//Stagger Start xAxisProperties.labelProperties.staggerEnable
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isStaggerEnable())
		 {
			 	categoryAxis.setStagger(true);
			 	if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getStartFrom().equalsIgnoreCase("startfromtop"))
			 		categoryAxis.setStaggertopbottom(true);
			 	else
			 		categoryAxis.setStaggertopbottom(false);
			 //categoryAxis.setParseDates(true);
		 }
		 //Stagger End
		 //ODD AND EVEN 
		 if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isGridStripVisible())
		 {
			 categoryAxis.setFillColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getOddStripColor());
			 double getTransparency = graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getTransparency();
			 double newTransparency = ((100-getTransparency)/100);
			 categoryAxis.setFillAlpha(newTransparency);
			 categoryAxis.setGridPosition("start");
		 }
		 
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isAll())
		 {
		 	categoryAxis.setAutoGridCount(false);
		 	categoryAxis.setGridCount(barcolListsize+1);
		 }
		 graphJson.setCategoryAxis(categoryAxis);
		 //-----------------------------------------------------------CATEGOTY AXES END---------------------------------------------------------------------------------//

		 //-----------------------------------------------------------BAR VALUE AXES START---------------------------------------------------------------------------------//
		 //Y-axis Bar Position		
		 valuebarAxes.setAutoGridCount(true);
		 valuebarAxes.setId("ValueAxis-1");
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().getPosition().equalsIgnoreCase("Left"))
		 {
			 valueAxesPosition = "left";
		 }
		 else
		 {
			 valueAxesPosition = "right";
		 }
		 //Y-axis Tick position
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getTickPosition() == 2)
		 {
			 inside = true;
		 }
		 else
		 {
			 inside =false;
		 }
		 //Yaxis label visiblity
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().isVisible())
		 {
			 labelsEnabled = true;
		 }
		 //Title
		 valuebarAxes.setTitleFontSize(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getyAxisTitleTrendProperties().getFontProperties().getFontSize());
		 valuebarAxes.setTitleColor(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getyAxisTitleTrendProperties().getFontProperties().getFontColor());
		 //Axis
		 valuebarAxes.setAxisColor(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().getColor());
		 valuebarAxes.setAxisAlpha(1);
		 valuebarAxes.setAxisThickness(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().getThickness());
		 //Tick and Label
		 valuebarAxes.setInside(inside);
		 valuebarAxes.setLabelsEnabled(labelsEnabled);
		 valuebarAxes.setTickLength(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getHeight());
		 valuebarAxes.setLabelOffset(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getDistanceFromLine());
		 valuebarAxes.setLabelRotation(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getRotationAngle());
		 valuebarAxes.setColor(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getFontProperties().getFontColor());
		 valuebarAxes.setFontSize(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getFontProperties().getFontSize());
		 
		 //Tick Line visibility
		 if(!graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().isVisible())
		 {
			 valuebarAxes.setTickLength(0);
		 }
		 //Axis Line yAxisProperties.lineProperties.visible
		 if(!graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLineProperties().isVisible())
		 {
			 valuebarAxes.setAxisThickness(0);
		 }
		 
		 //Minor Grid
		 /*valuebarAxes.setMinorGridAlpha(1);
		 valuebarAxes.setMinorTickLength(3);*/
		 valuebarAxes.setMinorGridEnabled(false);
			
		 //Stack Type
		 valuebarAxes.setStackType(stackType);//stacked bar
		 valuebarAxes.setPosition(valueAxesPosition); 
		 
		 valuebarAxes.setTitleRotation(270);
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getyAxisTitleTrendProperties().getRotateCharacter() == 90)
		 {
			valuebarAxes.setTitleRotation(90);
		 } 
		 //for predictive
		 if(rotate && isFromPredictive) {
			 valuebarAxes.setTitleRotation(0);
		 }
		 //end
		 String blank = "";
		 graphJson.setThousandsSeparator(blank);
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().isCommaSeprator())
         {
			 valuebarAxes.setLabelFunction("");
			 switch(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getCommaFormat())
			 {
			 case 1:
				 //graphJson.setThousandsSeparator(",");
				 valuebarAxes.setCommaSeparatorUsStyle(true);
				 break;
			 case 2:
				 
				 valuebarAxes.setCommaSeparatorIndianStyle(true);
				 break;
			 }
         }
		 
		 //-----------------------------------------------------------BAR VALUE AXES END---------------------------------------------------------------------------------//
		 
		//-----------------------------------------------------------Line VALUE AXES START---------------------------------------------------------------------------------//
		 //Y-axis Bar Position	
		 int scrollOffset = 0;
		 if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartScrollbar().isEnable())
			 scrollOffset = 15;
		 valuelineAxes.setId("ValueAxis-2");
		 valuelineAxes.setAutoGridCount(true);
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().getPosition().equalsIgnoreCase("Left"))
		 {
			 valueAxesPosition = "left";
		 }
		 else
		 {
			 valueAxesPosition = "right";
		 }
		 //Y-axis Tick position
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getTickPosition() == 2)
		 {
			 inside = true;
		 }
		 else
		 {
			 inside =false;
		 }
		 //Yaxis label visiblity
		 /*if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().isVisible())
		 {
			 labelsEnabled = true;
		 }*/
		 //Title
		 valuelineAxes.setTitleFontSize(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getyAxisTitleTrendProperties().getFontProperties().getFontSize());
		 valuelineAxes.setTitleColor(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getyAxisTitleTrendProperties().getFontProperties().getFontColor());
		 //Axis
		 valuelineAxes.setAxisColor(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().getColor());
		 valuelineAxes.setAxisAlpha(1);
		 valuelineAxes.setAxisThickness(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().getThickness());
		 //Tick and Label
		 valuelineAxes.setInside(inside);
		 valuelineAxes.setLabelsEnabled(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().isVisible());
		 valuelineAxes.setTickLength(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getHeight());
		
		 valuelineAxes.setLabelOffset(scrollOffset+graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getDistanceFromLine());
		 valuelineAxes.setLabelRotation(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getRotationAngle());
		 valuelineAxes.setColor(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getFontProperties().getFontColor());
		 valuelineAxes.setFontSize(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getFontProperties().getFontSize());
		 
		 //Tick Line visibility
		 if(!graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().isVisible())
		 {
			 valuelineAxes.setTickLength(0);
		 }
		 //Axis Line yAxisProperties.lineProperties.visible
		 if(!graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLineProperties().isVisible())
		 {
			 valuelineAxes.setAxisThickness(0);
		 }
		 
		 //Minor Grid
		 /*valuelineAxes.setMinorGridAlpha(1);
		 valuelineAxes.setMinorTickLength(3);*/
		 valuelineAxes.setMinorGridEnabled(false);
		 valuelineAxes.setStackType(stackType);
		 valuelineAxes.setPosition(valueAxesPosition);
		 

		 valuelineAxes.setTitleRotation(270);
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getyAxisTitleTrendProperties().getRotateCharacter() == 90)
		 {
			 valuelineAxes.setTitleRotation(90);
		 } 
		//for value title for rotation in KIR AKSHAY  
		 if(rotate && isFromPredictive) {
			 valuelineAxes.setTitleRotation(0);
		 }
		 //end
		 String blankSeparator = "";
		 graphJson.setThousandsSeparator(blankSeparator);
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().isCommaSeprator())
         {
			 //graphJson.setThousandsSeparator(",");
			 valuelineAxes.setLabelFunction("");
			 switch(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getCommaFormat())
			 {
			 case 1:
				 valuelineAxes.setCommaSeparatorUsStyle(true);
				 break;
			 case 2:
				 valuelineAxes.setCommaSeparatorIndianStyle(true);
				 break;
			 }
         }

		 //-----------------------------------------------------------Line VALUE AXES END---------------------------------------------------------------------------------//

		 
		 
		 
		 
		 
		 
		 ///Bar Grid Lines
		 
		 
		 List<ValueAxes> valueAxesList = new ArrayList<ValueAxes>();
		 if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isGridLineVisible())
		 {
			 categoryAxis.setGridColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineColor());
			 categoryAxis.setGridAlpha(1);
			 categoryAxis.setGridThickness(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
			 categoryAxis.setGridPosition("start");

			 int dashLength = 0;
			 int gridType=graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getStyle();
			 //Switch case for Grid style (Dash/Dotted)
			 switch (gridType) {
				case 1: //Dash
					dashLength = 9;
					break;
				case 2: //Dot
					dashLength = 3;
					break;
				default:
					dashLength = 0;
					break;
			 }
			 valuebarAxes.setDashLength(dashLength);
			 categoryAxis.setDashLength(dashLength);

			 //yAxis
			

			 valuebarAxes.setGridColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineColor());
			 valuebarAxes.setGridAlpha(1);
			 valuebarAxes.setGridThickness(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
			 valuebarAxes.setGridPosition("start");
			 
			 //valueAxesList.add(valuebarAxes);
			 
			 //graphJson.setValueAxes(valueAxesList);
		 }
		 else
		 {
			 categoryAxis.setGridPosition("");
			 categoryAxis.setGridColor("");
			 categoryAxis.setGridAlpha(0);
			 categoryAxis.setGridThickness(1);
			 categoryAxis.setGridPosition("start");

			
			 valuebarAxes.setGridColor("");
			 valuebarAxes.setGridPosition("");
			 valuebarAxes.setGridAlpha(0);
			 valuebarAxes.setGridThickness(1);
			
			// valuebarAxes.setStackType(stackType);
			// valueAxesList.add(valuebarAxes);
			 //graphJson.setValueAxes(valueAxesList);
		 }
		 
		 //Line Grid Lines
		 if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isGridLineVisible())
		 {
			 categoryAxis.setGridColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineColor());
			 categoryAxis.setGridAlpha(1);
			 categoryAxis.setGridThickness(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
			 categoryAxis.setGridPosition("start");

			 int dashLength = 0;
			 int gridType=graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getStyle();
			 //Switch case for Grid style (Dash/Dotted)
			 switch (gridType) {
				case 1: //Dash
					dashLength = 9;
					break;
				case 2: //Dot
					dashLength = 3;
					break;
				default:
					dashLength = 0;
					break;
			 }
			 valuelineAxes.setDashLength(dashLength);
			 categoryAxis.setDashLength(dashLength);

			 //yAxis
			 

			 valuelineAxes.setGridColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineColor());
			 valuelineAxes.setGridAlpha(1);
			 valuelineAxes.setGridThickness(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
			 valuelineAxes.setGridPosition("start");
			// valueAxesList.add(valuelineAxes);
			 
			// graphJson.setValueAxes(valueAxesList);
		 }
		 else
		 {
			 categoryAxis.setGridPosition("");
			 categoryAxis.setGridColor("");
			 categoryAxis.setGridAlpha(0);
			 categoryAxis.setGridThickness(1);
			 categoryAxis.setGridPosition("start");

			
			 valuelineAxes.setGridColor("");
			 valuelineAxes.setGridPosition("");
			 valuelineAxes.setGridAlpha(0);
			 valuelineAxes.setGridThickness(1);			 			 
			// valueAxesList.add(valuelineAxes);
			// graphJson.setValueAxes(valueAxesList);
		 }
		 
		 
		 
		 //--------------------------------------------Graph Area End------------------------------------------------ 

		 //--------------------------------------------Graph Title start------------------------------------------------
		 /*if(graphInfo.getGraphProperties().getTitleProperties().isTitleVisible())
		 {

			 List<Titles> graphTitleList = new ArrayList<Titles>();
			 Titles grphtitles = new Titles();
			 grphtitles.setText(graphInfo.getGraphProperties().getTitleProperties().getTitle());
			 grphtitles.setSize(graphInfo.getGraphProperties().getTitleProperties().getTitleFont().getFontSize());
			 grphtitles.setColor(graphInfo.getGraphProperties().getTitleProperties().getTitleFont().getFontColor());

			 graphTitleList.add(grphtitles);

			 graphJson.setTitles(graphTitleList);

		 }*/
		 //--------------------------------------------Graph Title end------------------------------------------------ 
		 //------------------------------------------- Data Provider Start Bar-----------------------------------------------
		 
		 
		 List dataBarList = graphInfo.getGraphData().getCmbBardataList();
		 List dataLineList = graphInfo.getGraphData().getCmbLinedataList();
		 List<Map<String, Object>> dpList =  new ArrayList<Map<String,Object>>();

		 
		 double maxValue=0.0;
		 double customBarMax=0.0;
		 double customLineMax=0.0;
		 boolean barflag=false;//if true it will set all the data values greater to customMax equal to customMax
		 boolean lineflag=false;//if true it will set all the data values greater to customMax equal to customMax
		 //When we provide custom max value this will set flag to true.
		 int adjustedDigitBar = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getAdjustedDigit();
		 int divValueBar = (int)(Math.pow(10, adjustedDigitBar));
		 
		 int adjustedDigitLine = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getAdjustedDigit();
		 int divValueLine = (int)(Math.pow(10, adjustedDigitLine));
		 
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxValType() == 1)
		 {
			 //graphInfo.getGraphProperties().getCombinedDataValueProperties()
			 
			 customBarMax =Double.parseDouble(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxCustomVal());
			 customBarMax= customBarMax/divValueBar;	
			 barflag=true;
			 valuebarAxes.setStrictMinMax(true);			
		 }
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxValType() == 1)
		 {
			 customLineMax =Double.parseDouble(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxCustomVal());
			 customLineMax= customLineMax/divValueLine;	
			 lineflag=true;			 
			 valuelineAxes.setStrictMinMax(true);
		 }
		 
		 if(graphInfo.getMeasureMaxValueList() != null && graphInfo.getMeasureMaxValueList().size() > 0 && graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxValType() == 2)
			{
			 List measureWiseMax =graphInfo.getMeasureMaxValueList();
			 List measureWiseMin = graphInfo.getMeasureMinValueList();
				try {
					valuelineAxes.setStrictMinMax(true);
					double max = 0.0;
					double min = 0.0001;
					if( measureWiseMax.size() > 0)
						max = ((double)measureWiseMax.get(0)/divValueBar);
					
					
					max = max + (max*0.10);
					valuebarAxes.setMaximum(max);
					
					if(measureWiseMin.size() > 0)
						min = ((double)measureWiseMin.get(0)/divValueBar);
					
					if(min < 0.0001)
						valuebarAxes.setMinimum(min);
					else
						valuebarAxes.setMinimum(0.0001);
					
				}
				catch(Exception e)
				{
					ApplicationLog.error(e);
				}
			}
		 if(graphInfo.getMeasureMaxValueList() != null && graphInfo.getMeasureMaxValueList().size() > 1 && graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxValType() == 2)
			{
			 List measureWiseMax =graphInfo.getMeasureMaxValueList();
			 List measureWiseMin = graphInfo.getMeasureMinValueList();
				try {
					valuelineAxes.setStrictMinMax(true);
					double max = 0.0;
					double min = 0.0001;
					if( measureWiseMax.size() > 0)
						max = ((double)measureWiseMax.get(1)/divValueLine);
					
					
					max = max + (max*0.10);
					valuelineAxes.setMaximum(max);
					
					if(measureWiseMin.size() > 0)
						min = ((double)measureWiseMin.get(1)/divValueLine);
					
					if(min < 0.0001)
						valuelineAxes.setMinimum(min);
					else
						valuebarAxes.setMinimum(0.0001);
					
				}
				catch(Exception e)
				{
					ApplicationLog.error(e);
				}
			}



	/*	 for (int i = 0; i < barcolListsize; i++) {
			 Map<String, Object> dpMap =  new HashMap<String, Object>();			 
			 Map<String, String> drillBarMap =new HashMap<String, String>();
			 Map<String, String> drillLineMap =new HashMap<String, String>();
			 Map<String, Object> barMap =new HashMap<String, Object>();
			 Map<String, Object> lineMap =new HashMap<String, Object>();
			 
			 
			 dpMap.put(barColLable, barcolList.get(i).toString());
			 

			 int drillIndex = i;
			 if(isBarLegendVisible)
			 {
				 
				 drillBarMap = new HashMap<String, String>();
			 }
			

			 for (int j = 0; j < barrowListsize; j++) {
				 String label = null;

				 if(isBarLegendVisible){
					 label = barrowList.get(j).toString();
					 drillBarMap.put(barrowList.get(j).toString(), drillBarList.get(j).toString());
				 }else{
					 label = graphInfo.getGraphData().getCmbBardataLabel();
					 if(barrowList.size() >0)
					 {
						 barMap.put("color", barColor[j%barColor.length]);
					 }
					 else
					 {
						 barMap.put("color", barColor[i%barColor.length]);
					 }
				 }
				 //Changes bar values to customMaxValue if flag is true
				 if(barflag)
				 {
					 if((Double)dataBarList.get(i*barrowListsize+j) > customBarMax)
					 {
						 dataBarList.set(i*barrowListsize+j, customBarMax);
					 }
				 }
				 barMap.put(label, dataBarList.get(i*barrowListsize+j).toString());
			 }
			 if(isBarLegendVisible){
				 barMap.put(drillBarLegend, drillBarMap);
			 }
			 
			 
			 
			
		 
			// dpMap =  new HashMap<String, Object>();		
			 for (int j = 0; j < linerowListsize; j++) {
				 String label = null;

				 if(isLineLegendVisible){
					 label = linerowList.get(j).toString();
					
						 drillLineMap.put(linerowList.get(j).toString(), drillLineList.get(j).toString());
				 }else{
					 label = graphInfo.getGraphData().getCmbLinedataLabel();
					 if(linerowList.size() >0)
					 lineMap.put("color", barColor[j%barColor.length]);
					 else
						 lineMap.put("color", barColor[i%barColor.length]);
				 }
				
				 //Changes bar values to customMaxValue if flag is true
				 if(lineflag)
				 {
					 if((Double)dataLineList.get(i*linerowListsize+j) > customLineMax)
					 {
						 dataLineList.set(i*linerowListsize+j, customLineMax);
					 }
				 }
				 if(dataLineList.size() > (i*linerowListsize+j))
				 lineMap.put(label, dataLineList.get(i*linerowListsize+j).toString());
			 }
		if(isLineLegendVisible){
			lineMap.put(drillLineLegend, drillLineMap);
			 }
		
		//map3 = new HashMap();
		 if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isVisible())
		 {	
			 if(drillcolList.size() > drillIndex)
				 dpMap.put(drillAxis, drillcolList.get(drillIndex).toString());
		 }
		dpMap.putAll(barMap);
		dpMap.putAll(lineMap);

			 dpList.add(dpMap);
		 }
		 
		*/

		 
		 
		

	/*	 for (int i = 0; i < linecolListsize; i++) {
			 Map<String, Object> dpMap =  new HashMap<String, Object>();
			 Map<String, String> drillMap =null;

			 dpMap.put(lineColLable, linecolList.get(i).toString());

			/* int drillIndex = i;
			 if(isLegendVisible)
			 {
				 drillIndex=linerowListsize+i;
				 drillMap = new HashMap<String, String>();
			 }
			 if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isVisible())
			 {
				 dpMap.put(drillAxis, drillList.get(drillIndex).toString());
			 }*/

			 

//			 if(isLegendVisible){
//				 dpMap.put(drillLegend, drillMap);
//			 }

		//	 dpList.add(dpMap);
		 //}
		 
		 
		 
		 
	//	 graphJson.setDataProvider(dpList);
		 
		 
		 
		 
		 
		 //--------------------------------------------Custom/Auto Bar--------------------------------------------------------//
		
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxValType() == 1)
		 {
//			 String s="";
//			 s=String.valueOf(customBarMax);
			 valuebarAxes.setMaximum(customBarMax);
		 }
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMinValType() == 1)
		 {
			 double customMin;
			 customMin =Double.parseDouble(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMinCustomVal());
			 customMin = customMin/divValueBar;
			 if(customMin == 0.0 || customMin == 0)
				 customMin=0.000001;
			 valuebarAxes.setMinimum(customMin);
		 }		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().isLogarithmic()) {
			 valuebarAxes.setLogarithmic(true);
			 valuebarAxes.setUsePrefixes(true);
			 valuebarAxes.setTreatZeroAs(0.01);
				
		}
		 
		 
		 valueAxesList.add(valuebarAxes);
		 
		 //--------------------------------------------Custom/Auto Line--------------------------------------------------------//
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxValType() == 1)
		 {
//			 String s="";
//			 s=String.valueOf(customLineMax);
			 valuelineAxes.setMaximum(customLineMax);
		 }
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMinValType() == 1)
		 {
			 double customMin;
			 customMin =Double.parseDouble(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMinCustomVal());
			 customMin = customMin/divValueLine;
			 if(customMin == 0.0 || customMin == 0)
				 customMin=0.000001;
			 valuelineAxes.setMinimum(customMin);
		 }
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().isLogarithmic()) {
			 valuelineAxes.setLogarithmic(true);
			 valuelineAxes.setUsePrefixes(true);
			 valuelineAxes.setTreatZeroAs(0.01);
				
		}
		 
		 valueAxesList.add(valuelineAxes);
		 
		 if(graphInfo.getGraphProperties().getCombinedYaxisProperties().getLineYaxisProperties().getLabelProperties().getMaxValType() == 1 || graphInfo.getGraphProperties().getCombinedYaxisProperties().getBarYaxisProperties().getLabelProperties().getMaxValType() == 1)
			 graphJson.setValueAxes(valueAxesList);
		// graphJson.setValueAxes(valueAxesList);

		 //--------------------------------------------Custom/Auto--------------------------------------------------------//


		 //------------------------------------------- Bar Legend Start--------------------------------------------------
		 
	
		 String tmp=null;
		 if(isLineLegendVisible || isBarLegendVisible){
			 //legendProperties.legendPanelProperties.legendPanelVisible
			 List<Map<String, Object>>  dataRulesMap =  new	ArrayList<Map<String,Object>>();
			 List<Map<String, Object>>  linedataRulesMap =  new	ArrayList<Map<String,Object>>();
			 Legend legend= new Legend();	
			 legend.setValueWidth(0);//for purpose of removing white spaces(Legned)
			 List<Integer> updatedColorInfoList = graphInfo.getcmbLegendColorInfoList();
			 
			 List<Integer> updatedLineColorInfoList =graphInfo.getCmbLineColorInfoList();
			 if(graphInfo.getcmbLineLegendColorInfoList() != null) {
				updatedLineColorInfoList = graphInfo.getcmbLineLegendColorInfoList();
			 }
			 if(null!= graphInfo.getGraphProperties() && null !=graphInfo.getGraphProperties().getLegendCustomValueList() && 
						!graphInfo.getGraphProperties().getLegendCustomValueList().isEmpty()) {
				
					/*Map<String, Integer> colorMapping = new HashMap<>();
			        for (int k = 0; k < graphInfo.getLovListForColor().size(); k++) {
			        	if(graphInfo.getColorInfoList().size()>k) {
			            colorMapping.put(graphInfo.getLovListForColor().get(k), graphInfo.getColorInfoList().get(k));}
			        }

			        // Create a new list for updated colorInfoList
			        updatedColorInfoList = new ArrayList<>();
			        for (Object category : graphInfo.getGraphProperties().getLegendCustomValueList()) {
			        	if(null!= colorMapping.get(category))
			            updatedColorInfoList.add(colorMapping.get(category));
			        }*/
			        if(graphInfo.getCmbBarColorInfoList().size() !=updatedColorInfoList.size()) {
				    	  updatedColorInfoList = graphInfo.getcmbLegendColorInfoList();
				     }
			 }
			 List legendValList = new ArrayList<>();
				legendValList.addAll(barrowList);

				if (graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option3")
						&& graphInfo.getGraphProperties().getLegendCustomValueList() != null
						&& !graphInfo.getGraphProperties().getLegendCustomValueList().isEmpty() && graphInfo.getDrilldownBreadcrumbMap() == null) {
					legendValList.clear();
					legendValList.addAll(graphInfo.getGraphProperties().getLegendCustomValueList());
				}
				if(isBarLegendVisible){		
					if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().isLegendPanelVisible())
					{		
						List dateBarRowList = graphInfo.getGraphData().getDatecmbBarrowList();	
						if(!barrowList.isEmpty()) {
						for (int i = 0; i < barrowListsize; i++) {	
								
							Map<String, Object> barDataMap =  new HashMap<String, Object>();					
							tmp = legendValList.get(i).toString();//Added for Bug SDEVAPR20-369 (16 Dec 2020)
							
							if(!dateBarRowList.isEmpty() && dateBarRowList.size() > i //Added for Bug SDEVAPR20-369 (16 Dec 2020)
								&& null != dateBarRowList.get(i) && !dateBarRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE)
								&& barrowListsize==dateBarRowList.size()) {
								String stringFormat;
								stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
								stringFormat = stringFormat.replaceAll("&#39;", "'");
								Calendar cal = Calendar.getInstance();
								Date axisDate = new Date();
								axisDate = (Date) dateBarRowList.get(i);
								cal.setTime(axisDate);
								stringFormat=stringFormat.trim();
								tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
							}
							if (null !=graphInfo.getDataColLabels3() && graphInfo.getDataColLabels3().size() < 2 && graphInfo.getDateFrequencyMap() != null&& null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != graphInfo.getDateFrequencyMap().get(graphInfo.getRowColumns().elementAt(0).toString()) && !graphInfo.getDateFrequencyMap().get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty()) {
								Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
								String frequency = "";
								if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
									String strCol = graphInfo.getColColumns().elementAt(0).toString();
									if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
											&& !dateFrequencyMap.get(strCol).isEmpty()) {
										frequency = dateFrequencyMap.get(strCol);
										
									}
								}
								if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
									String strCol = graphInfo.getRowColumns().elementAt(0).toString();
									if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
											&& !dateFrequencyMap.get(strCol).isEmpty()) {
										frequency = dateFrequencyMap.get(strCol);
										
									}
								}
								if(frequency != null && !frequency.isEmpty() ) {
									if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
									
										String stringFormat;
										stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat();
										stringFormat = stringFormat.replaceAll("&#39;", "'");
										Calendar cal = Calendar.getInstance();
										
										String strData = tmp;
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
										tmp = strData;
									}
									
								
								}
								
							}
							switch(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
							{
							case "auto":						
								int truncateCharLimitAuto = 15;
								if (tmp.length() > truncateCharLimitAuto)
									tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
								break;
							case "custom":						
								int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
								if (tmp.length() > truncateCharLimit)
									tmp = tmp.substring(0, truncateCharLimit)+"..";
								break;				
							}
							if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= barrowListsize
									&& barRowLabel != null && (barRowLabel.equalsIgnoreCase("Legend") || barRowLabel.equalsIgnoreCase(barColLable)))
								tmp = graphInfo.getGraphData().getColLabelsName().get(i).toString();
							if(tmp != null)
								tmp = Parser.unescapeEntities(tmp, false);					
							
							barDataMap.put("title", tmp);
							barDataMap.put("valueField", legendValList.get(i).toString());
							barDataMap.put("color", barColor[graphInfo.getcmbLegendColorInfoList().get(i)%barColor.length]);
							//barDataMap.put("markerType", "square");
							dataRulesMap.add(barDataMap);		
						}
					}
						try {
							if(!dataRulesMap.isEmpty() && !graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option3")){
								dataRulesMap.sort(Comparator.comparing(o -> String.valueOf(o.get("title"))));
								
							}
							
						}catch(Exception e) {
							ApplicationLog.error(e);
						}				
					}				
				}
							
				if(isLineLegendVisible){
					 //legendProperties.legendPanelProperties.legendPanelVisible
					 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().isLegendPanelVisible())
					 {					
						List dateLineRowList = graphInfo.getGraphData().getDatecmbLinerowList();
						
						if(!linerowList.isEmpty()) {
						for (int i = 0; i < linerowListsize; i++) {					
								
						Map<String, Object> dataMap =  new HashMap<String, Object>();
						tmp = linerowList.get(i).toString();//Added for Bug SDEVAPR20-369 (16 Dec 2020)
						if(!dateLineRowList.isEmpty() && dateLineRowList.size() > i //Added for Bug SDEVAPR20-369 (16 Dec 2020)
								&& null != dateLineRowList.get(i) && !dateLineRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE)
								&& dateLineRowList.size()==linerowListsize) {
								String stringFormat;
								stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
								stringFormat = stringFormat.replaceAll("&#39;", "'");
								Calendar cal = Calendar.getInstance();
								Date axisDate = new Date();
								axisDate = (Date) dateLineRowList.get(i);
								cal.setTime(axisDate);
								stringFormat=stringFormat.trim();
								tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
						}		
						if (null !=graphInfo.getDataColLabels3() && graphInfo.getDataColLabels3().size() < 2 &&graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()) {
							Map<String, String> dateFrequencyMap = graphInfo.getDateFrequencyMap();
							String frequency = "";
							if (null != graphInfo.getColColumns() && !graphInfo.getColColumns().isEmpty()) {
								String strCol = graphInfo.getColColumns().elementAt(0).toString();
								if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
										&& !dateFrequencyMap.get(strCol).isEmpty()) {
									frequency = dateFrequencyMap.get(strCol);
									
								}
							}
							if (null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty()) {
								String strCol = graphInfo.getRowColumns().elementAt(0).toString();
								if (dateFrequencyMap != null && !dateFrequencyMap.isEmpty() && dateFrequencyMap.get(strCol) != null
										&& !dateFrequencyMap.get(strCol).isEmpty()) {
									frequency = dateFrequencyMap.get(strCol);
									
								}
							}
							if(frequency != null && !frequency.isEmpty() ) {
								if(frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_QUARTERLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_MONTHLY) || frequency.equalsIgnoreCase(KPIConstants.FREQUENCY_WEEKLY)) {
								
									String stringFormat;
									stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat();
									stringFormat = stringFormat.replaceAll("&#39;", "'");
									Calendar cal = Calendar.getInstance();
									
									String strData = tmp;
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
									tmp = strData;
								}
								
							
							}
							
						}
						switch(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
						{
							case "auto":						
								int truncateCharLimitAuto = 15;
								if (tmp.length() > truncateCharLimitAuto)
									tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
								break;
							case "custom":						
								int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
								if (tmp.length() > truncateCharLimit)
									tmp = tmp.substring(0, truncateCharLimit)+"..";
								break;
						
						}
						if(colLabelsName2 && graphInfo.getGraphData().getColLabelsName2().size() >= linerowListsize
							&& lineRowLabel != null && lineRowLabel.equalsIgnoreCase("Legend"))
								tmp = graphInfo.getGraphData().getColLabelsName2().get(i).toString();
							if(tmp != null)
								tmp = Parser.unescapeEntities(tmp, false);
								
							dataMap.put("title", tmp);
							dataMap.put("valueField", linerowList.get(i).toString());
							if(linerowListsize<=updatedColorInfoList.size())
							dataMap.put("color", lineColors[updatedLineColorInfoList.get(i)%lineColors.length]);
							//dataMap.put("markerBorderThickness", 10);
							//dataMap.put("markerType", "line");					
								
							linedataRulesMap.add(dataMap);		
						}
						}
						try {							
							if((!linedataRulesMap.isEmpty()) && !graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option3"))// && graphJson.isSortColumns())|| graphInfo.getDataColLabels3().size()>1)
								linedataRulesMap.sort(Comparator.comparing(o -> String.valueOf(o.get("title"))));
						}catch(Exception e) {
							ApplicationLog.error(e);
						}			
					}				
				}
			 
			dataRulesMap.addAll(linedataRulesMap);			
			/*legend.setData(dataRulesMap);
			*/
			 if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().isLegendPanelVisible())
			 {			 			
				 
				 if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isEnable())
					{
						legend.setValueText("");
					}
					if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().isDrillDown())
					{
						legend.setSwitchable(false);
					}
					else
					{
						legend.setSwitchable(true);
					}
					if(isContextFilter)
					{
						legend.setSwitchable(false);
					}
					if(isContextFilter
							&& ((graphInfo.getGraphData().getCmbBardataLabel() != null && graphInfo.getGraphData().getCmbBardataLabel().equalsIgnoreCase("data"))
							|| (graphInfo.getGraphData().getCmbLinedataLabel() != null && graphInfo.getGraphData().getCmbLinedataLabel().equalsIgnoreCase("data"))))
					{
						legend.setSwitchable(true);
					}
				 
				 //------------------------------------------- Bar Legend Panel Start--------------------------------------------------
					legend.setVerticalGap(5);
					String position="";
					switch(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelPosition())
					{
					case 1: position ="top";
						legend.setHorizontalGap(0);
						break;
					case 2: position ="left";
						legend.setHorizontalGap(10);
						break;
					case 3: position ="right";
					legend.setHorizontalGap(10);
						break;
					case 4: position="bottom";
						legend.setHorizontalGap(0);
						break;
					}
				 legend.setPosition(position);
				 //legend Visible
				 if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().isVisible()) {
					 legend.setBackgroundAlpha(1);
					 legend.setBackgroundColor(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().getBackGroundColor());

					 if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().isBackgroundTransparent()) {
						 legend.setBackgroundAlpha(0);
					 }
					 else{
						 legend.setBackgroundAlpha(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().getTransparency()/100.0);
					 }
				 }
				 else {
					 legend.setBackgroundAlpha(0);
					 legend.setBackgroundColor("");
				 }
				 
				
				 //legend.setDivId("chartlegenddiv");
				 //Legend Margin start
				 legend.setAutoMargins(false);
				 
				 
				 legend.setMarginTop(5.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getTopMargin());
					legend.setMarginBottom(5.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getBottomMargin());
					legend.setMarginLeft(10.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getLeftMargin());
					legend.setMarginRight(20.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getRightMargin());
					
					if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll() != 0.0)
					{	
						legend.setMarginTop(5.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
						legend.setMarginBottom(5.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
						legend.setMarginLeft(10.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
						legend.setMarginRight(20.0+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
					}
				 
				 
				/* //legendProperties.legendPanelProperties.legendPanelMarginProperties.all
				 legend.setMarginTop(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getTopMargin());
				 legend.setMarginBottom(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getBottomMargin());
				 legend.setMarginLeft(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getLeftMargin());
				 legend.setMarginRight(20.0f+graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getRightMargin());*/
				 //Legend Margin end

				 //this allows distance between on hover value and legend value(title)
				 legend.setEqualWidths(false);
				 legend.setAlign("center");

				 //------------------------------------------- Bar Legend Panel End--------------------------------------------------

				 //------------------------------------------- Bar Legend Title Start--------------------------------------------------
				/* if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getTitleProperties().isTitleVisible())
					{
						String legendTitle = null;
						if(graphInfo.getGraphProperties().getLegendProperties().getTitleProperties().getTitle().equals(""))
						{
							if(isLegendVisible)
							{
								legendTitle = graphInfo.getGraphData().getCmbBarrowLabel();	
							}
							else
							{
								legendTitle = graphInfo.getGraphData().getCmbBarcolLabel();
								if(lineRowLabel.equalsIgnoreCase("Legend"))//to negelect legend title while changing from bar to combined
									legendTitle = "";
							}
						}
						else
						{	
							legendTitle = graphInfo.getGraphProperties().getLegendProperties().getTitleProperties().getTitle();
						}
						if(legendTitle != null)
							legendTitle = Parser.unescapeEntities(legendTitle, false);	
						
						legend.setTitle(legendTitle);
					}
					else
					{	*/
						legend.setTitle("");
					//}
						legend.setFontSize(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getFontSize());
								
				 //-------------------------------------------Bar Legend Title End--------------------------------------------------
				 //------------------------------------------- Bar Legend Values Start--------------------------------------------------
				 legend.setMaxColumns(1000);
				 legend.setSpacing(10);
					/*legend.setVerticalGap(10);
					legend.setHorizontalGap(10);*/

				 if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option1") || 
						 graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option3")){
						legend.setReversedOrder(false);
					} 
					else if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option2")){
						legend.setReversedOrder(true);
					}
				 legend.setColor(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getFontColor());
				 switch(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
				 {
				 case "none":
					 legend.setTruncateLabels("undefined");
					 break;
				 case "auto":
					 legend.setTruncateLabels("undefined");
					 break;
				 case "custom":
					 legend.setTruncateLabels(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
					 break;
				 } 
				 
				 if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesColumn()==0)
					 legend.setMaxColumns(100);
				 else
					 legend.setMaxColumns(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendValuesProperties().getLegendValuesColumn());	 
					 
				 legend.setVerticalGap(1);

				 //-------------------------------------------Bar Legend Values End--------------------------------------------------
				 //-------------------------------------------Bar Legend Icon Start--------------------------------------------------
				 String legendPosition ="";
				 String legendIconShape="";

				 int markerSize = graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendIconProperties().getWidth();
				 switch(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendIconProperties().getLegendIconSelectShape())
				 {
				 case "Square": legendIconShape="square"; break;
				 case "Circle": legendIconShape="circle"; break;
				 //case "Horizontal Line": legendIconShape="line"; break;
				 case "Vertical Line": legendIconShape="line"; break;
				 }
				 legend.setMarkerSize(markerSize);
				 legend.setMarkerType(legendIconShape);

				 if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().isVisible()
						 && graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderStyle().equalsIgnoreCase("solid")){
					 legend.setMarkerBorderThickness(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderWidth());
					 legend.setMarkerBorderColor(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderColor());
					 legend.setMarkerBorderAlpha(1);
				 }
				 else
				 {
					 legend.setMarkerBorderColor("");
					 legend.setMarkerBorderAlpha(0);
				 }
				 
				 
				 //-------------------------------------------Bar Legend Icon End--------------------------------------------------
				 legend.setUseGraphSettings(true);				 
				 ((GraphLegendJson) graphJson).setLegend(legend);
				 
			 }
		 }
		 else {
			 if(isFromPredictive) {
				 Legend legend= new Legend();
				 legend.setEnabled(true);
				 legend.setUseGraphSettings(true);
				 legend.setAlign("center");
				 legend.setPosition("bottom");
				 legend.setMaxColumns(5);
				 ((GraphLegendJson) graphJson).setLegend(legend); 
			 }
			
		 }
			 
			
			 
			 //------------------------------------------- Line Legend Start--------------------------------------------------
			 
			 
		/*	 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().isLegendPanelVisible())
			 {
				 
				 
				 Legend legend= new Legend();
				 //-------------------------------------------Line Legend Panel Start--------------------------------------------------
				 String position="";
				 switch(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelPosition())
				 {
				 case 1: position ="top";break;
				 case 2: position ="left";break;
				 case 3: position ="right";break;
				 case 4: position="bottom";break;
				 }
				 legend.setPosition(position);
				 //legend Visible
				 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().isVisible()) {
					 legend.setBackgroundAlpha(1);
					 legend.setBackgroundColor(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().getBackGroundColor());

					 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().isBackgroundTransparent()) {
						 legend.setBackgroundAlpha(0);
					 }
					 else{
						 legend.setBackgroundAlpha(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().getTransparency()/100.0);
					 }
				 }
				 else {
					 legend.setBackgroundAlpha(0);
					 legend.setBackgroundColor("");
				 }
				 //legend.setDivId("chartlegenddiv");
				 //Legend Margin start
				 legend.setAutoMargins(false);
				 //legendProperties.legendPanelProperties.legendPanelMarginProperties.all
				 legend.setMarginTop(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getTopMargin());
				 legend.setMarginBottom(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getBottomMargin());
				 legend.setMarginLeft(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getLeftMargin());
				 legend.setMarginRight(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getRightMargin());
				 //Legend Margin end

				 //this allows distance between on hover value and legend value(title)
				 legend.setEqualWidths(false);

				 //-------------------------------------------Line Legend Panel End--------------------------------------------------

				 //------------------------------------------- Line Legend Title Start--------------------------------------------------
				 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getTitleProperties().isTitleVisible())
				 {
					 legend.setTitle(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getTitleProperties().getTitle());
					 legend.setFontSize(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getTitleProperties().getTitleFont().getFontSize());
				 }
				 //------------------------------------------- Line Legend Title End--------------------------------------------------
				 //------------------------------------------- Line Legend Values Start--------------------------------------------------
				 legend.setMaxColumns(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesColumn());
				 legend.setVerticalGap(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesLineSpacing());


				 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option1")){
					 legend.setReversedOrder(false);
				 } else {
					 legend.setReversedOrder(true);
				 }
				 legend.setColor(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getFontColor());
				 switch(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
				 {
				 case "none":
					 legend.setTruncateLabels("undefined");
					 break;
				 case "auto":
					 legend.setTruncateLabels("undefined");
					 break;
				 case "custom":
					 legend.setTruncateLabels(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
					 break;
				 } 
				 //------------------------------------------- Line Legend Values End--------------------------------------------------
				 //------------------------------------------- Line Legend Icon Start--------------------------------------------------
				 String legendPosition ="";
				 String legendIconShape="";

				 int markerSize = graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendIconProperties().getWidth();
				 switch(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendIconProperties().getLegendIconSelectShape())
				 {
				 case "Square": legendIconShape="square"; break;
				 case "Circle": legendIconShape="circle"; break;
				 //case "Horizontal Line": legendIconShape="line"; break;
				 case "Vertical Line": legendIconShape="line"; break;
				 }
				 legend.setMarkerSize(markerSize);
				 legend.setMarkerType(legendIconShape);

				 if(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().isVisible()){
					 legend.setMarkerBorderThickness(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderWidth());
					 legend.setMarkerBorderColor(graphInfo.getGraphProperties().getCombinedGraph().getLineLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderColor());
				 }
				 else
				 {
					 legend.setMarkerBorderColor("");
				 }
				 //-------------------------------------------Line Legend Icon End-------------------------------------------------- 
				// ((GraphLegendJson) graphJson).setLegend(legend);
				// legendlst.add(legend);
			 }			 
		 }		 */
		 
		 

		 //------------------------------------------- Legend End--------------------------------------------------

		//Dashboard purpose
		if(barColLable != null && barRowLabel != null && (barRowLabel.equalsIgnoreCase("Legend") || barColLable.equals(barRowLabel)))
			graphJson.setMultipleMeasure(true);
		else
			graphJson.setMultipleMeasure(false);
		if(isLineLegendVisible)
		{
			if(lineColLable != null && (lineRowLabel != null && lineRowLabel.equalsIgnoreCase("Legend")))
				graphJson.setMultipleMeasureLine(true);
			else
				graphJson.setMultipleMeasureLine(false);
		}
		else
		{
			graphJson.setMultipleMeasureLine(false);
		}
		//Dashboard purpose end
		 
		 List allLabels = new ArrayList();
		 graphJson.setAllLabels(allLabels);

		 List<ValueAxis> valueAxisList = new ArrayList<ValueAxis>();
		 ValueAxis valueAxis = new ValueAxis();
		 valueAxis.setAxisAlpha(0);
		 //yAxisProperties.lineProperties.position
		 valueAxis.setPosition("left");
		 valueAxis.setTitle("New Ones");
		 valueAxisList.add(valueAxis);
		 graphJson.setValueAxis(valueAxisList);

		 double startDuration = 0.0;
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isAnimationPlayable())
				startDuration = 0.25;
			graphJson.setStartDuration(startDuration);//Play or Stop Animation
		 List<Graphs> graphsList = new ArrayList<Graphs>();
		 graphJson.setValueAxes(valueAxesList);
		 
		 
		
		 //------------------------------------------- Bar Graphs Start--------------------------------------------------
		 
		 
		
			
		 			
 			
			if(graphInfo.getGraphProperties().getBarProperties().getType() == 2)
			{
				graphJson.setAngle(30);
				graphJson.setDepth3D(30);
				
			}
			else if(graphInfo.getGraphProperties().getBarProperties().getType() == 3)
			{
				graphJson.setAngle(30);
				graphJson.setDepth3D(30);
				
			}
			
			
           
		
		 //------------------------------------------- Line Graphs Start--------------------------------------------------

		 
		
		 
		
		 graphJson.setGraphs(graphsList);
		 //------------------------------------------- Graphs End--------------------------------------------------
		 graphJson.setCategoryField(barColLable);

		 
		 List<Balloon> balloonlst = new ArrayList<Balloon>();
		 
		 //-------------------------------------------Ballon Start-----dataValueProperties.dataValueMouseOver.mouseOverTextEnable-----------------------------------------------
		 if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().isMouseOverTextEnable())
		 {
			 Balloon balloon = new Balloon();
			 balloon.setColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverFont().getFontColor());
			 balloon.setFontSize(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverFont().getFontSize());
			 balloon.setFillAlpha(1);
			 balloon.setFillColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverBackground().getBackGroundColor());
			 balloon.setAdjustBorderColor(true);
			 if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverBorder().isVisible())
			 {
				 balloon.setBorderColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverBorder().getAllBorderColor());
				 balloon.setBorderThickness(graphInfo.getGraphProperties().getCombinedDataValueProperties().getBardataValueMouseOver().getDataValueMouseOverBorder().getAllBorderWidth());
				 balloon.setAdjustBorderColor(false);	
			 }
			// graphJson.setBalloon(balloon);
			 graphJson.setBalloon(balloon); 
		 }
		 /*
		//-------------------------------------------Ballon Start-----dataValueProperties.dataValueMouseOver.mouseOverTextEnable-----------------------------------------------
		 if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().isMouseOverTextEnable())
		 {
			 Balloon balloon = new Balloon();
			 balloon.setColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverFont().getFontColor());
			 balloon.setFontSize(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverFont().getFontSize());
			 balloon.setFillAlpha(1);
			 balloon.setFillColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverBackground().getBackGroundColor());
			 balloon.setAdjustBorderColor(true);
			 if(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverBorder().isVisible())
			 {
				 balloon.setBorderColor(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverBorder().getAllBorderColor());
				 balloon.setBorderThickness(graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinedataValueMouseOver().getDataValueMouseOverBorder().getAllBorderWidth());
				 balloon.setAdjustBorderColor(false);	
			 }
			 balloonlst.add(balloon);
			
		 }*/
		 
		 //--------------------------------------------Balloon Ends--------------------------------------------------

		/* //------------------------------------------- Graphs Scroll Start--------------------------------------------------
		 ChartScrollbar chartScrollbar=new ChartScrollbar();
		 chartScrollbar.setEnabled(true);
		 graphJson.setChartScrollbar(chartScrollbar);
		 //------------------------------------------- Graphs Scroll End--------------------------------------------------
		 */
		// area gradient start
			
		 
		 
		 //Bar Reference Line Start
		 List<Guides> guideList = new ArrayList<Guides>();
		 int refLineStyle = 0;
		 int refDashLength = 0;
		 int i=0;//Fetching reference line style index
		 Map<Integer, ReferenceLine> testMapBar = graphInfo.getGraphProperties().getBarReferencelinePropertiesMap();
		 for (Entry<Integer, ReferenceLine> entry : testMapBar.entrySet()) {
			 i++;
			 Guides guides = new Guides();
			 ReferenceLine referenceLine = entry.getValue();
			 refLineStyle = Integer.parseInt(referenceLine.getStyle());
			 switch (refLineStyle) {
			 case 0:
				 refDashLength = 0;
				 break;
			 case 1:
				 refDashLength = 0;
				 break;
			 case 2:
				 refDashLength = 7;
				 break;
			 case 3:
				 refDashLength = 2;
				 break;
			 }
			 guides.setLineAlpha(1);
			 guides.setLineColor(referenceLine.getColor());
			 guides.setLabel(referenceLine.getLabel());
			 double sd=Double.parseDouble(referenceLine.getValue());
			 int adjustedDigit = graphInfo.getGraphProperties().getCombinedDataValueProperties().getBarnumberFormat().getAdjustedDigit();//getyAxisPropertiesMap().get("M0").getLabelProperties().getAdjustedDigit();
			 int divValue = (int)(Math.pow(10, adjustedDigit));
			 if(sd>0 && sd/divValue>0)
					guides.setValue(sd/divValue);
				else
					guides.setValue(sd);
			 //guides.setValue(sd);
			 guides.setInside(true);
			 guides.setLineThickness(referenceLine.getWidth());
			 guides.setDashLength(refDashLength);
			 guides.setValueAxis("ValueAxis-1");
			 guides.setToValue(guides.getValue());
			 guideList.add(guides);
		 }
		 
		  refLineStyle = 0;
		 refDashLength = 0;
		 i=0;//Fetching reference line style index
		 
		 Map<Integer, ReferenceLine> testMapLine = graphInfo.getGraphProperties().getLineReferencelinePropertiesMap();
		 for (Entry<Integer, ReferenceLine> entry : testMapLine.entrySet()) {
			 i++;
			 Guides guides = new Guides();
			 ReferenceLine referenceLine = entry.getValue();
			 refLineStyle = Integer.parseInt(referenceLine.getStyle());
			 switch (refLineStyle) {
			 case 0:
				 refDashLength = 0;
				 break;
			 case 1:
				 refDashLength = 0;
				 break;
			 case 2:
				 refDashLength = 7;
				 break;
			 case 3:
				 refDashLength = 2;
				 break;
			 }
			 guides.setLineAlpha(1);
			 guides.setLineColor(referenceLine.getColor());
			 guides.setLabel(referenceLine.getLabel());
			 double sd=Double.parseDouble(referenceLine.getValue());
			 int adjustedDigit = graphInfo.getGraphProperties().getCombinedDataValueProperties().getLinenumberFormat().getAdjustedDigit();
			 int divValue = (int)(Math.pow(10, adjustedDigit));
			 if(sd>0 && sd/divValue>0)
					guides.setValue(sd/divValue);
				else
					guides.setValue(sd);
			 //guides.setValue(sd);
			 guides.setInside(true);
			 guides.setLineThickness(referenceLine.getWidth());
			 guides.setDashLength(refDashLength);
			 guides.setValueAxis("ValueAxis-2");
			 guides.setToValue(guides.getValue());
			 guideList.add(guides);
		 }
		 graphJson.setGuides(guideList);
		 //Reference Line End
		 
		//------------------------------------------- Graphs Scroll Start--------------------------------------------------
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartScrollbar().isEnable())
			{
				//mouseWheelZoomEnabled
				graphJson.setMouseWheelZoomEnabled(true);
				
				graphJson.setScrollBar(true);
				//value ScrollBar
				ValueScrollbar valueScrollbar = new ValueScrollbar();
				valueScrollbar.setEnabled(true);
				graphJson.setValueScrollbar(valueScrollbar);
			}
			//------------------------------------------- Graphs Scroll End--------------------------------------------------
			
			//------------------------------------------- Chart Cursor Start--------------------------------------------------

			if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isEnable())
			{
				ChartCursor chartCursor  = new ChartCursor();
				if(graphInfo.getGraphProperties().getZoomType() == 0)//Both
				{
					chartCursor.setCursorLineAlpha(0);
					//chartCursor.setCursorAlpha(0.3);
					chartCursor.setZoomable(true);
					chartCursor.setValueZoomable(true);
					//chartCursor.setValueLineAlpha(0.3);
					chartCursor.setValueLineEnabled(true);
					graphJson.setZoomType("both");
				}
				else if(graphInfo.getGraphProperties().getZoomType() == 1)//Horizontal
				{
					chartCursor.setCursorLineAlpha(0);
					//chartCursor.setCursorAlpha(0.3);
					chartCursor.setZoomable(true);
					chartCursor.setValueLineAlpha(0);
					//chartCursor.setValueBalloonsEnabled(true);
					//chartCursor.setCategoryBalloonEnabled(true);
					/*chartCursor.setValueZoomable(true);
					chartCursor.setValueLineEnabled(true);*/
					graphJson.setZoomType("horizontal");
				}
				else if(graphInfo.getGraphProperties().getZoomType() == 2)//Vertical
				{
					chartCursor.setValueZoomable(true);
					chartCursor.setValueLineEnabled(true);
					chartCursor.setZoomable(false);
					chartCursor.setCursorAlpha(0);
					//chartCursor.setValueLineAlpha(0.3);
					chartCursor.setCursorLineAlpha(0);
					/*chartCursor.setCategoryBalloonEnabled(false);
					chartCursor.setValueLineBalloonEnabled(true);*/
					//chartCursor.setValueBalloonsEnabled(false);
					graphJson.setZoomType("vertical");
				}

				chartCursor.setCursorColor("black");
				chartCursor.setCursorPosition("mouse");
				if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isFullWidth())
				{
					chartCursor.setFullWidth(true);
					chartCursor.setCursorPosition("middle");
				}
				/*if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isSelectWithoutZooming())
				{
					chartCursor.setSelectWithoutZooming(true);
				}*/
				chartCursor.setAvoidBalloonOverlapping(false);
				chartCursor.setSelectionAlpha(0.3);

				graphJson.setChartCursor(chartCursor);
			}
			//------------------------------------------- Chart Cursor End--------------------------------------------------
		 
		//Responsive start
			Responsive responsive = new Responsive();
			responsive.setEnabled(true);
			responsive.setAddDefaultRules(false);

			List<LinkedHashMap<String, Object>> rulesMapList = new ArrayList<LinkedHashMap<String, Object>>();
			LinkedHashMap<String, Object> dpRulesMap =  new LinkedHashMap<String, Object>();
			boolean adaptiveBehaviour = graphInfo.getGraphProperties().getAdaptiveBehaviour();
			if(graphInfo.getGraphProperties().getCombinedGraph().getBarLegendProperties().getLegendPanelProperties().isLegendPanelVisible() && (isLegendVisible || isBarLegendVisible || isLineLegendVisible) && adaptiveBehaviour)
			{
			dpRulesMap.put("maxWidth", 300);
			LinkedHashMap<String, Object> legendMap = new LinkedHashMap<String, Object>();
			
			dpRulesMap.put("overrides", legendMap);
			LinkedHashMap<String, Object> ruleMap = new LinkedHashMap<String, Object>();
			ruleMap.put("enabled", true);
			ruleMap.put("position", "bottom");
			ruleMap.put("maxColumns", "undefined");
			legendMap.put("legend", ruleMap);
			}
			rulesMapList.add(dpRulesMap);
			responsive.setRules(rulesMapList);

			graphJson.setResponsive(responsive);
			graphJson.setChartname("CombineChart");
			//Responsive end

		 jsonList.add(graphJson);
		 //End

		 try {
			// objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
			 
			 json = objectMapper.writeValueAsString(jsonList);
			 
		 } catch (IOException e) {
			 ApplicationLog.error(e);
		 }		 
		 return json;
	 }
	private static final String[] appendValue(String[] s1 ,String newValue) {

		  String[] erg = new String[s1.length + 1];
		  erg[erg.length-1] = newValue;
	      System.arraycopy(s1, 0, erg, 0, s1.length);

	      return erg;

	  }
}
