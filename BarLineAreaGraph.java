package com.elegantjbi.amcharts;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.elegantjbi.service.graph.GraphCommandNameList;
import com.elegantjbi.service.graph.GraphConstants;
import com.elegantjbi.service.kpi.KPIConstants;
import com.elegantjbi.util.AppConstants;
import com.elegantjbi.util.GeneralFiltersUtil;
import com.elegantjbi.util.GraphsUtil;
import com.elegantjbi.util.StringUtil;
import com.elegantjbi.util.logger.ApplicationLog;
import com.elegantjbi.vo.properties.graph.ReferenceLine;
import com.elegantjbi.vo.properties.kpi.TrendLineProperties;
import com.elegantjbi.vo.properties.kpi.YaxisTrendProperties;

public class BarLineAreaGraph {

	public static String amJson(GraphInfo graphInfo,boolean isContextFilter) 
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
		
		/*String[] barColor =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"
										,"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		*/
		String[] barColor =new String[]{"#67b7dc","#6794dc","#6771dc","#8067dc","#a367dc","#c767dc","#dc67ce","#dc67ab","#dc6788","#dc6967",
			    "#dc8c67","#dcaf67","#dcd267","#c3dc67","#a0dc67","#7ddc67","#67dc75","#67dc98","#67dcbb","#67dadc",
			    "#80d0f5","#80adf5","#808af5","#9980f5","#bc80f5","#e080f5","#f580e7", "#f7d584", "#b1fb83", "#50407f", 
			    "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c", "#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296",
			    "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424",
			    "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92",
			    "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"};
		/*String[] bulletColor =new String[]{"#8daacb", "#fc7362", "#bbd854", "#ffd92f", "#66c296", "#e5b694", "#e78ad2", "#b3b3b3", "#a6d8e3", "#abe9bc", "#1b7d9c", "#ffbfc9", "#4da741", "#c4b2d6", "#b22424", "#00acac", "#be6c2c", "#695496", "#349152", "#c9a16c", "#2d6396", "#fb2600", "#1596ff", "#fc9400", "#36fa92", "#ec8b8b", "#93c2ff", "#f7d584", "#b1fb83", "#50407f", "#64c7cd", "#02adf2", "#828813", "#3ab54a", "#ed008c"
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
		String json = null;

		String chartType = null;
		String type = null;
		double fillAlpha = 0;
		int lineAlpha = 0;
		int bulletAlpha = 0;
		int bulletBorderAlpha = 0;
		boolean isLine = false;
		boolean rotate=false;
		String stackType="none";
		String bullet = null;
		String colLabel = graphInfo.getGraphData().getColLabel();
		String rowLabel = graphInfo.getGraphData().getRowLabel();
		String dataLabel = graphInfo.getGraphData().getDataLabel();
		int gridCountSize = graphInfo.getGraphData().getColList().size();
		int noOfMeasure = graphInfo.getDataColLabels3().size();
		boolean isMultiMeasure = graphInfo.getDataColLabels3().size()>1 && (dataLabel.equalsIgnoreCase("Data") || dataLabel==null || dataLabel.equalsIgnoreCase("null"));
		
		List dateColList = graphInfo.getGraphData().getDatecolList();
		List dateRowList = graphInfo.getGraphData().getDaterowList();
		
		
		boolean isPercentageChart = graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH 
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH;
		
		//barGraphs variable start
		List dataLabelList = new ArrayList<String>();
		int multipleMeasures = graphInfo.getDataColLabels3().size();
		boolean isMultipleMeasure = false;
		int noOfYAxis = 1;
		int colr=0;
		int countmm=0;
		//barGraphs variable end
		
		boolean isBarChart = false;//for barWidth
		if(graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
		{
			isBarChart = true;
		}
		
		List rowList = graphInfo.getGraphData().getRowList();
		int rowListSize = rowList.size();
		boolean colLabelsName = false;
		//This is when non clustered and multiple measure along with col labels
		if(graphInfo.getGraphData().getColLabelsName() != null && graphInfo.getGraphData().getColLabelsName().size()>0)
			colLabelsName = true;
		
		boolean isLegendVisible = true;
		if(rowListSize == 0){
			rowListSize = 1;
			isLegendVisible =  false;
		}
		graphJson = new GraphLegendJson();
		String truncatedLabelString = "";
		int dvTruncateCharLimit = 15;
		
		//rank maintain the sequence of graph in desc order
		if(graphInfo.getRankList() != null && graphInfo.getRankList().size() > 0)
		{
			boolean rankEnable = false;
			for (int cnt = 0; cnt < graphInfo.getRankList().size(); cnt++) {
				CubeRankDataLabel rankDataLabel = graphInfo.getRankList().get(cnt);
				if(rankDataLabel.isStatus()
					&& ((null != rowLabel && rankDataLabel.getColumnName().equalsIgnoreCase(rowLabel)) || (null != colLabel && rankDataLabel.getColumnName().equalsIgnoreCase(colLabel))))
					rankEnable = true;
			}
			if(rankEnable)
				graphJson.setSortColumns(true);
		}
		//rank maintain the sequence of graph in desc order
		
		//Code to maintain the sequence of graph when Advance sort applied (Bug #14832) start
		if(graphInfo.getSortList() != null && graphInfo.getSortList().size() > 0)
		{
			boolean sortEnable = false;
			boolean isAscendingSortEnable = false;
			for (int cnt = 0; cnt < graphInfo.getSortList().size(); cnt++) {
				CubeLabelInfo cubeLabelInfo = graphInfo.getSortList().get(cnt);
				if(cubeLabelInfo.isStatus() && cubeLabelInfo.getSortType() == 1
					&& ((null != graphInfo.getRowLabelForLov() && cubeLabelInfo.getName().equalsIgnoreCase(graphInfo.getRowLabelForLov())) || (graphInfo.getRowLabelForLov() == null && null != colLabel && cubeLabelInfo.getName().equalsIgnoreCase(colLabel))) ) {
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
		
		if("Legend".equals(rowLabel))//Added rowLabel != "Legend" check for Bug #15046
		{ graphJson.setSortColumns(false); }

		for (int k = 0; k < rowListSize; k++) {
			lineThicknessList.add(1);
		}
		int colorType = graphInfo.getGraphProperties().getColorType();
		List<String> customColors = graphInfo.getGraphProperties().getCustomColors();
		String sameColor = graphInfo.getGraphProperties().getColor();
		
		int pointColorType = graphInfo.getGraphProperties().getPointColorType();
		List<String> pointCustomColors = graphInfo.getGraphProperties().getPointCustomColors();
		String pointSameColor = graphInfo.getGraphProperties().getPointcolor();
		if(graphInfo.getGraphType() == GraphConstants.LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH)
		{	
			colorType = graphInfo.getGraphProperties().getLineColorType();
			customColors = graphInfo.getGraphProperties().getLineCustomColors();
			sameColor = graphInfo.getGraphProperties().getLinecolor();
		}
		switch(colorType)
		{
		case 1:
			if(customColors != null)
			{
				for (int i = 0; i < customColors.size(); i++) {
					if(i > (barColor.length-1))// || i > (bulletColor.length-1))
					{
						barColor = appendValue(barColor, customColors.get(i));
					}
					else
					{	
					barColor[i] = customColors.get(i);
					}
				}
			}
			break;
		case 2:
			barColor = new String[]{sameColor};
			break;
		}
		switch(pointColorType)
		{
		case 0:
			bulletColor = barColor;break;
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
		
		//Decides the type of graph
		if(graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH)
		{
			type = "column";
			chartType = "bar";
			fillAlpha = 1;
			if(graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH)
			{
				stackType="regular";
			}
			if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH)
			{
				stackType="100%";
			}
			for (int k = 0; k < rowListSize; k++) {
				lineThicknessList.set(k,0);
			}
		}
		else if(graphInfo.getGraphType() == GraphConstants.LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH)
		{
			type = "line";
			chartType = "line";
			fillAlpha = 0;
			switch(graphInfo.getGraphProperties().getGraphLineProperties().getType())
			{
			case 1:lineAlpha =1;bulletAlpha = 0; bulletBorderAlpha = 0;isLine=true;break;
			case 2:lineAlpha = 0;bulletAlpha = 1;bulletBorderAlpha = 1;break;
			case 3:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 1;type = "smoothedLine";break;
			case 4:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 1;type = "step";break;
			default:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;break;
			}
			if(graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH)
			{
				stackType = "regular";
			}
			if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH)
			{
				stackType = "100%";
			}
			for (int k = 0; k < rowListSize; k++) {
				bulletList.add("round");
				bulletSizeList.add(8);
				lineStyleList.add(0);
				lineThicknessList.add(8);
				borderWidthList.add(0);
				bulletStyleList.add(7);
				borderColorList.add("none");
			}
			if(graphInfo.getGraphProperties().getLineType() == 0)
			{
				int dashLength = 0;
				for (int i = 0; i < rowListSize; i++) {

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

					for (int l = 0; l < graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().size(); l++) {
						int lineStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getStyle());
						int customLineThickness = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getThickness());
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
						lineStyleList.set(l%lineStyleList.size(), dashLength);
						lineThicknessList.set(l%lineThicknessList.size(), customLineThickness);
					}
				}
			}
			if(graphInfo.getGraphProperties().getPointType() == 0)
			{
				for (int i = 0; i < rowListSize; i++) {

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
							borderColorList.set(i, barColor[graphInfo.getColorInfoList().get(i)%barColor.length]);//Changed for Bug #15447 barColor[i%barColor.length]);
						bulletStyleList.set(i,bulletStyle);
					}
				
				}
			}
			else
			{
				if(null != graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList() && graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().size() != 0)
				{
					for (int l = 0; l < graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().size(); l++) {


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
							bulletList.set(l, bullet);					 
							bulletSizeList.set(l, bulletSize);
							if(l < bulletTypeArray.length)
							{
								bulletTypeArray[l] = bullet;
							}
							borderWidthList.set(l%borderWidthList.size(), borderwidth);
							
							borderColorList.set(l%borderColorList.size(), barColor[l]);
							bulletStyleList.set(l%bulletStyleList.size(),bulletStyle);
						}
					}
				}
			}
		}
		else if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
				|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
		{
			type = "column";
			chartType = "bar";
			fillAlpha = 1;
			rotate=true;
			if(graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
			{
				stackType="regular";
			}
			if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
			{
				stackType="100%";
			}
			for (int k = 0; k < rowListSize; k++) {
				lineThicknessList.set(k, 0);
			}
		}
		else if(graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
		{
			type = "line";
			chartType = "area";
			double getAreaTransparency = (double)graphInfo.getGraphProperties().getTranceperancy();
			fillAlpha = (100 - getAreaTransparency) / 100;

			bulletAlpha = 0;

			if(graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH)
			{
				stackType="regular";
			}
			if(graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
			{
				stackType="100%";
			}
		}


		int colorLength = barColor.length;

		graphJson.setChartType(chartType);
		graphJson.setType("serial");
		graphJson.setRotate(rotate);
		graphJson.setTheme("none");
		graphJson.setStartEffect("easeOutSine");
		graphJson.setUsePrefixes(false);
		graphJson.setColumnSpacing(0);
		graphJson.setAddClassNames(true);
		graphJson.setColors(Arrays.asList(barColor));

		//String precisionLabel="";
		
		ChartCursor chartCursor = new ChartCursor();//As it is required by both clusteredMouseOver as well as chartCursor itself.
		//Adjusted Digit
		int precisionLabelCounter=1;
		List precisionLabelList= new ArrayList();
		if((graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))
				|| (graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH) && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
		{
			precisionLabelCounter = graphInfo.getDataColLabels3().size();
		}
		String firstMeasurePrecision = "";
		for(int i=0;i<precisionLabelCounter;i++)
		{	
			String precisionLabel="";
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isShowadAdjustedSuffixed())
			{	
				int prefix = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getAdjustedDigit();
				
				switch(prefix)
				{
				case 0:
					precisionLabel="";
					break;
				case 3:
					precisionLabel="K";
					break;
				case 5:
					precisionLabel="L";
					break;
				case 6:
					precisionLabel="M";
					break;
				case 7:
					precisionLabel="Cr";
					break;
				case 9:
					precisionLabel="Bn";
					break;
				}
				precisionLabelList.add(precisionLabel);
			}
			else
			{
				precisionLabel="";
				graphJson.setPrecision(-1);
				precisionLabelList.add(precisionLabel);
			} 
			if(i==0) {
				firstMeasurePrecision = precisionLabel;
			}
		}
		

		/*// yaxis labels digits after decimal start
		int digitsaftDecimal = 0;
		int yaxisPrecision = graphInfo.getGraphProperties().getyAxisProperties().getLabelProperties().getNumberOfDigits();
		switch(yaxisPrecision)
		{
		case 0:
			digitsaftDecimal = 0;
			break;
		case 1:
			digitsaftDecimal = 1;
			break;
		case 2:
			digitsaftDecimal = 2;
			break;
		case 3:
			digitsaftDecimal = 3;
			break;
		case 4:
			digitsaftDecimal = 4;
			break;
		case 5:
			digitsaftDecimal = 5;
			break;
		} 
		// yaxis labels digits after decimal end
*/
		// data value digits after decimal start
		int precision = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M0").getLabelProperties().getNumberOfDigits();//getDataValueProperties().getNumberFormat().getNumberOfDigits();
		switch(precision)
		{
		case 0:
			graphJson.setPrecision(0);
			break;
		case 1:
			graphJson.setPrecision(1);
			break;
		case 2:
			graphJson.setPrecision(2);
			break;
		case 3:
			graphJson.setPrecision(3);
			break;
		case 4:
			graphJson.setPrecision(4);
			break;
		case 5:
			graphJson.setPrecision(5);
			break;
		} 
		// data value digits after decimal start
		//----------------------------------------------------3D START---------------------------------------------------//
		if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isVisible())
		{
			graphJson.setAngle(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getAngle());
			graphJson.setDepth3D(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getDepth3d());
		}
		//----------------------------------------------------3D END---------------------------------------------------//

		//--------------------------------------------Graph Area Start------------------------------------------------
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

			/*
			 *Note:- Apply graphJson.setMarginRight 
			 *		separately for getGraphHorizontalAreaProperties
			 *		because it will affect default view
			 *		of Stacked HORIZONTAL Graph 
			 */
			graphJson.setMarginRight(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
		/*}*/
		//Margin end

		//grid for both axes
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
				xaxisTitle = colLabel;
			}
			else
			{
				xaxisTitle = graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getTitle();
			}
			if(xaxisTitle!=null )
				xaxisTitle = Parser.unescapeEntities(xaxisTitle, false);	
			categoryAxis.setTitle(xaxisTitle);
		}
		int categoryAxisTitleRotation = 0;
		if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
		{
			
			if(graphInfo.getGraphProperties().getyAxisProperties().getyAxisTitleTrendProperties().getRotateCharacter() == 0)
				categoryAxisTitleRotation = 270;
			else
				categoryAxisTitleRotation = graphInfo.getGraphProperties().getyAxisProperties().getyAxisTitleTrendProperties().getRotateCharacter();
			
			categoryAxis.setTitleRotation(categoryAxisTitleRotation);
			
			//Added for default view
			if(graphInfo.getGraphProperties().getyAxisProperties().getyAxisTitleTrendProperties().isVisible())
			{
				graphJson.setMarginRight(graphInfo.getGraphProperties().getGraphHorizontalAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getRightMargin());
				if(graphInfo.getGraphProperties().getGraphHorizontalAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll() > 50)
					graphJson.setMarginRight(graphInfo.getGraphProperties().getGraphHorizontalAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getAll());
			}
		}
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
		
		categoryAxis.setTitleFontSize(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getFontProperties().getFontSize());
		categoryAxis.setTitleColor(graphInfo.getGraphProperties().getxAxisProperties().getxAxisTitleTrendProperties().getFontProperties().getFontColor());

		categoryAxis.setAxisColor(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getColor());
		categoryAxis.setAxisAlpha(1);
		categoryAxis.setAxisThickness(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getThickness());

		//Axis position start
		if(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getPosition().equalsIgnoreCase("Top"))
		{
			categoryAxis.setPosition("top");
			graphJson.setMarginBottom(10.0f+graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getGeneralProperties().getPanelMargin().getBottomMargin());
		}
		else
		{
			categoryAxis.setPosition("bottom");
		}
		//Axis position end

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
		if(graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().getAxisMajorLineTickTrendProperties().getTickPosition() == 2)
		{
			categoryAxis.setInside(true);
			categoryAxis.setLabelOffset(-40);
			graphJson.setGridAboveGraphs(true);
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

		//Axis Line visible start
		if(!graphInfo.getGraphProperties().getxAxisProperties().getLineProperties().isVisible())
		{
			categoryAxis.setAxisThickness(0);
		}
		//Axis Line visible end
		categoryAxis.setLabelFunction("");

		//Stagger Start
		if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isStaggerEnable())
		{
			categoryAxis.setStagger(true);
			if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getStartFrom().equalsIgnoreCase("startfromtop"))
				categoryAxis.setStaggertopbottom(true);
			else
				categoryAxis.setStaggertopbottom(false);
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
		
		if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isGridLineVisible())
		{
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
//Removed			valueAxes.setDashLength(dashLength);
			categoryAxis.setDashLength(dashLength);
		}
		
		if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isAll())
		{
			categoryAxis.setAutoGridCount(false);
			categoryAxis.setGridCount(gridCountSize+1);
		}

		categoryAxis.setEqualSpacing(true);//For omitting empty values on Category Axis 
		graphJson.setCategoryAxis(categoryAxis);
		//-----------------------------------------------------------CATEGOTY AXES END---------------------------------------------------------------------------------//

		
		//-----------------------------------------------------------VALUE AXES START---------------------------------------------------------------------------------//
		List<ValueAxes> valueAxesList = new ArrayList<ValueAxes>();
		
		ValueAxes valueAxes=null;
		int offset=80;
		int valueAxesCounter = 1;
		//List measureNameList = new ArrayList(graphInfo.getDataColLabels3());
		/*if(graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))//Multiple Measures)
		{
			valueAxesCounter = graphInfo.getDataColLabels3().size();
			if(graphInfo.getGraphData().getRowLabel() != null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend"))//when more then one measure and non  clust
				valueAxesCounter = 1;
		}*/
		if((graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))
				|| (graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH) && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
			{
			valueAxesCounter = graphInfo.getDataColLabels3().size();
			}
		int leftValueAxis = 0;
		int rightValueAxis = 0;
		int leftValueAxesOffset = 0;
		int rightValueAxesOffset = 0;
		 if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartScrollbar().isEnable())
			 rightValueAxesOffset = 15;
		for(int i=0;i < valueAxesCounter;i++){
			valueAxes = new ValueAxes();
			valueAxes.setLocale(Locale.getDefault().getLanguage());
			
			valueAxes.setAutoGridCount(true);
			valueAxes.setAutoOffset(false);
			valueAxes.setId("valueAxes"+i);
			/*if(i > 0)
				valueAxes.setOffset(offset);*/
			// yaxis labels digits after decimal start
			int digitsaftDecimal = 0;
			int yaxisPrecision = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getNumberOfDigits();
			switch(yaxisPrecision)
			{
			case 0:
				digitsaftDecimal = 0;
				break;
			case 1:
				digitsaftDecimal = 1;
				break;
			case 2:
				digitsaftDecimal = 2;
				break;
			case 3:
				digitsaftDecimal = 3;
				break;
			case 4:
				digitsaftDecimal = 4;
				break;
			case 5:
				digitsaftDecimal = 5;
				break;
			} 
			// yaxis labels digits after decimal end
			
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().isVisible())
			{
				if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getTitle().equals(""))
				{
					if(graphInfo.getGraphData().getColLabelsName() != null && !graphInfo.getGraphData().getColLabelsName().isEmpty())
						yaxisTitle =  graphInfo.getGraphData().getColLabelsName().get(i).toString();
					else if(valueAxesCounter == 1)
						yaxisTitle =  graphInfo.getGraphData().getDataLabel2();
					else{
						yaxisTitle =  graphInfo.getDataColLabels3().get(i).toString();
						if(graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
							yaxisTitle =  graphInfo.getGraphData().getDataLabel2();						
					}					
					
					if(yaxisTitle != null && yaxisTitle.equalsIgnoreCase("data"))
						yaxisTitle = "";
				}
				else
				{
					yaxisTitle = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getTitle();
				}
				if(yaxisTitle != null)
					yaxisTitle = Parser.unescapeEntities(yaxisTitle, false);
				
				valueAxes.setTitle(yaxisTitle);
				int valueAxesTitleRotation = 0;
				if(graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.LINE_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
				{
					if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getRotateCharacter() == 0)
						valueAxesTitleRotation = 270;
					else
						valueAxesTitleRotation = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getRotateCharacter();
				}
				else if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
						|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
				{
					if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getRotateCharacter() == 0)
						valueAxesTitleRotation = 0;
					else
						valueAxesTitleRotation = 180;//When Horizontal as per amcharts requirement Bug #12614
				}
				valueAxes.setTitleRotation(valueAxesTitleRotation);
			}
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isGridLineVisible())
			{
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
				valueAxes.setDashLength(dashLength);
			}
			
			//Y-axis Line Position start
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getPosition().equalsIgnoreCase("Left"))
			{
				valueAxesPosition = "left";
			}
			else
			{
				valueAxesPosition = "right";
			}
			//Y-axis Line Position end

			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible())
			{
				labelsEnabled = true;
			}
			else
			{
				labelsEnabled = false;
			}

			//Title
			valueAxes.setTitleFontSize(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getFontProperties().getFontSize());
			valueAxes.setTitleColor(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().getFontProperties().getFontColor());

			//Axis
			valueAxes.setAxisColor(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getColor());
			valueAxes.setAxisAlpha(1);
			valueAxes.setAxisThickness(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getThickness());

			//Tick and Label
			valueAxes.setLabelsEnabled(labelsEnabled);
			valueAxes.setTickLength(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getAxisMajorLineTickTrendProperties().getHeight());
			valueAxes.setLabelOffset(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getDistanceFromLine());
			valueAxes.setLabelRotation(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getRotationAngle());
			valueAxes.setColor(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getFontProperties().getFontColor());
			valueAxes.setFontSize(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getFontProperties().getFontSize());

			//Y-axis Tick position start
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getAxisMajorLineTickTrendProperties().getTickPosition() == 2)
			{
				inside = true;
				valueAxes.setLabelOffset(-50);
				graphJson.setGridAboveGraphs(true);
			}
			else
			{
				inside = false;
			}
			valueAxes.setInside(inside);
			//Y-axis Tick position end

			//Tick Line visibility
			if(!graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getAxisMajorLineTickTrendProperties().isVisible())
			{
				valueAxes.setTickLength(0);
			}

			//Axis Line yAxis visible start
			if(!graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().isVisible())
			{
				valueAxes.setAxisThickness(0);
			}
			//Axis Line yAxis visible end

			
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().isVisible()
					|| graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible()
					|| graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().getAxisMajorLineTickTrendProperties().isVisible()
				    || graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().isVisible())
			{
				valueAxes.setAutoOffset(true);
			}
			
			//Minor Grid
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isMinorGridEnable())
			{
				valueAxes.setMinorGridAlpha(1);
				valueAxes.setMinorGridEnabled(true);
				valueAxes.setMinorTickLength(0);
			}
			
			//Stack Type
			valueAxes.setStackType(stackType);//stacked bar
			valueAxes.setPosition(valueAxesPosition);
			
			String blank = "";
			graphJson.setThousandsSeparator(blank);
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isCommaSeprator())
            {
				//graphJson.setThousandsSeparator(",");
				valueAxes.setLabelFunction("");
				 switch(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getCommaFormat())
				 {
				 case 1:
					 valueAxes.setCommaSeparatorUsStyle(true);
					 break;
				 case 2:
					 
					 valueAxes.setCommaSeparatorIndianStyle(true);
					 break;
				 }
	         
            }

			//-----------------------------------------------------------VALUE AXES END---------------------------------------------------------------------------------//
			// Grid Lines
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().isGridLineVisible())
			{
				categoryAxis.setGridColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineColor());
				categoryAxis.setGridAlpha(1);
				categoryAxis.setGridThickness(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
				categoryAxis.setGridPosition("start");

				int gridType=graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getStyle();
				if(gridType == 1)
				{
					valueAxes.setDashLength(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
					categoryAxis.setDashLength(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
				}

				//yAxis
				

				valueAxes.setGridColor(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineColor());
				valueAxes.setGridAlpha(1);
				valueAxes.setGridThickness(graphInfo.getGraphProperties().getGraphAreaProperties().getBackGroundGrid().getGridLineThickness());
				valueAxes.setGridPosition("start");
				//valueAxesList.add(valueAxes);

				//graphJson.setValueAxes(valueAxesList);
			}
			else
			{
				categoryAxis.setGridPosition("");
				categoryAxis.setGridColor("");
				categoryAxis.setGridAlpha(0);
				categoryAxis.setGridThickness(1);
				categoryAxis.setGridPosition("start");


				valueAxes.setGridColor("");
				valueAxes.setGridPosition("");
				valueAxes.setGridAlpha(0);
				valueAxes.setGridThickness(1);
				valueAxes.setPosition(valueAxesPosition);
				valueAxes.setStackType(stackType);
				//valueAxesList.add(valueAxes);
				//graphJson.setValueAxes(valueAxesList);
			}
			if(valueAxesPosition.equalsIgnoreCase("left"))
			{
				leftValueAxis++;
				if(leftValueAxis>1)
					leftValueAxesOffset=leftValueAxesOffset+80;
				//valueAxes.setOffset(leftValueAxesOffset);
				
			}
			else
			{
				rightValueAxis++;
				if(rightValueAxis>1)
					rightValueAxesOffset=rightValueAxesOffset+80;
				//valueAxes.setOffset(rightValueAxesOffset);
			}
			//--------------------------------------------Graph Area End------------------------------------------------ 

			double customMax = 0.0;
			//When we provide custom max value this will set flag to true.
			int adjustedDigit = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getAdjustedDigit();
			// When AutoValue is enabled, don't divide - AMCharts will auto-format values
			int divValue = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isAutoValue() 
					? 1 : (int)(Math.pow(10, adjustedDigit));
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getMaxValType() == 1)
			{
					customMax =Double.parseDouble(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getMaxCustomVal());
					customMax= customMax/divValue;					
					valueAxes.setStrictMinMax(true);
			}
					
			//--------------------------------------------Custom/Auto start--------------------------------------------------------//
			
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getMaxValType() == 1)
			{
				valueAxes.setMaximum(customMax);
			}
			/*else
			{
				valueAxes.setM(0.0);
			}*/
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getMinValType() == 1)
			{
				double customMin;
				customMin = Double.parseDouble(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getMinCustomVal());
				customMin = customMin/divValue;
				if(customMin == 0.0 || customMin == 0)
					customMin=0.000001;
				
				valueAxes.setMinimum(customMin);
			}
			/*else
			{
				valueAxes.setMinimum(0.0);
			}*/
			
			// When AutoValue is enabled, use AMCharts usePrefixes for auto-formatting (K, M, B)
			// When AutoValue is disabled, use manual unit suffix
			if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isAutoValue()) {
				valueAxes.setUsePrefixes(true);
				valueAxes.setUnit(""); // Clear manual unit when using auto prefixes
				// Set prefixesOfBigNumbers on valueAxes and graphJson for proper K/M/B formatting in tooltips and data values
				List<Map<String, Object>> prefixesOfBigNumbers = getPrefixes(
					graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getAdjustedDigit(),
					graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getCommaFormat());
				valueAxes.setPrefixesOfBigNumbers(prefixesOfBigNumbers);
				graphJson.setUsePrefixes(true);
				graphJson.setPrefixesOfBigNumbers(prefixesOfBigNumbers);
			} else {
				valueAxes.setUnit(precisionLabelList.get(i).toString());
			}
			if(isPercentageChart)// for 14161
				valueAxes.setUnit("%");
			valueAxes.setPrecision(digitsaftDecimal);
			
			if((graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
					&& graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().isShowTotalValue())
			{
				// When AutoValue is enabled, use [[total]] which AMCharts will auto-format
				// When AutoValue is disabled, use manual Abs placeholder with suffix
				if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isAutoValue()) {
					valueAxes.setTotalText("[[total]]");
				} else {
					String suffix = precisionLabelList.get(i).toString();
					if(suffix=="" || firstMeasurePrecision!=suffix) {
						suffix = firstMeasurePrecision;
					}
					if(valueAxesCounter>1) {
						valueAxes.setTotalText("[[AbsrealTotal"+i+"]]"+suffix);
					}else {
						valueAxes.setTotalText("[[AbsrealTotal]]"+suffix);//"[[total]]"+precisionLabelList.get(i).toString());//Added for feature request of Stack total
					}
				}
			}
			
			valueAxesList.add(valueAxes);
			/*if(i > 0)
			offset=offset+80;*/
			}
		

		graphJson.setValueAxes(valueAxesList);

		//--------------------------------------------Custom/Auto end--------------------------------------------------------//

		//------------------------------------------- Legend Start--------------------------------------------------
	
		
			if(isLegendVisible && graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().isLegendPanelVisible())
			{
				//9 Apr changes [p.p]
				List<Integer> hideGraphsListlegend = new ArrayList<>();
				Map graphsVisibleMaplegend = graphInfo.getGraphProperties().getGraphsVisibleMap();
				if(graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")){
				for(int i=0;i<graphInfo.getDataColLabels3().size();i++)//Changed from getDataColumns() to getDataColLabels3() for Jira Bug SDEVAPR20-79
					{
						if(graphsVisibleMaplegend.get(graphInfo.getDataColLabels3().get(i)) != null && 
								graphsVisibleMaplegend.get(graphInfo.getDataColLabels3().get(i)).toString().equals("false")) {
							hideGraphsListlegend.add(i);
						}
					}
				}
					Legend legend= new Legend();				
					legend.setValueWidth(0);//for purpose of removing white spaces(Legned)
					List<Map<String, Object>>  dataRulesMap =  new ArrayList<>();
					Map<String, Object>  dataMapOther = new HashMap<>(); 
					 List<Integer> updatedColorInfoList = graphInfo.getColorInfoList();
					 if(null!= graphInfo.getGraphProperties() && null !=graphInfo.getGraphProperties().getLegendCustomValueList() && 
								!graphInfo.getGraphProperties().getLegendCustomValueList().isEmpty()) {
						
							Map<String, Integer> colorMapping = new HashMap<>();
					        for (int k = 0; k < graphInfo.getLovListForColor().size(); k++) {
					            colorMapping.put(graphInfo.getLovListForColor().get(k), graphInfo.getColorInfoList().get(k));
					        }
		
					        // Create a new list for updated colorInfoList
					        updatedColorInfoList = new ArrayList<>();
					        for (Object category : graphInfo.getGraphProperties().getLegendCustomValueList()) {
					            updatedColorInfoList.add(colorMapping.get(category));
					        }
					        if(graphInfo.getColorInfoList().size() !=updatedColorInfoList.size()) {
						    	  updatedColorInfoList = graphInfo.getColorInfoList();
						      }
					 }
					 List legendValList = new ArrayList<>();
						legendValList.addAll(rowList);

						if (graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties()
								.getLegendValuesOrder().equalsIgnoreCase("option3")
								&& graphInfo.getGraphProperties().getLegendCustomValueList() != null
								&& !graphInfo.getGraphProperties().getLegendCustomValueList().isEmpty() && graphInfo.getDrilldownBreadcrumbMap() == null) {
							legendValList.clear();
							legendValList.addAll(graphInfo.getGraphProperties().getLegendCustomValueList());
						}
					if(!rowList.isEmpty()) {
					for (int i = 0; i < rowList.size(); i++) {
						if(!hideGraphsListlegend.contains(i))  // 9 Apr changes [p.p]
						{
						Map<String, Object> dataMap =  new HashMap<>();
						String tmp = legendValList.get(i).toString();
						String tmp1 = legendValList.get(i).toString();
						
						//Added code for Bug #13406 start
						if(!dateRowList.isEmpty() && dateRowList.size() > i
								&& null != dateRowList.get(i) && !dateRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE)) {
							String stringFormat;
							stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
							stringFormat = stringFormat.replaceAll("&#39;", "'");
							Calendar cal = Calendar.getInstance();
							Date axisDate = new Date();
							axisDate = (Date) dateRowList.get(i);
							cal.setTime(axisDate);
							stringFormat=stringFormat.trim();
							tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
						}//Added code for Bug #13406 end
						if (null !=graphInfo.getDataColLabels3() && graphInfo.getDataColLabels3().size() < 2 && graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty() && null != graphInfo.getRowColumns() && !graphInfo.getRowColumns().isEmpty() && null != graphInfo.getDateFrequencyMap().get(graphInfo.getRowColumns().elementAt(0).toString()) && !graphInfo.getDateFrequencyMap().get(graphInfo.getRowColumns().elementAt(0).toString()).isEmpty()) {
							tmp  = GraphsUtil.getLegendDateFormat(graphInfo,tmp);
											
						}

						switch(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
						{
						case "auto":							
							int truncateCharLimitAuto = 15;
							if (tmp.length() > truncateCharLimitAuto)
								tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
							break;
						case "custom":							
							int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
							if (tmp.length() > truncateCharLimit)
								tmp = tmp.substring(0, truncateCharLimit)+"..";
							break;
						/*default:
							tmp = rowList.get(i).toString();
							break;*/
						}
						if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= rowListSize
								&& graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("legend") && tmp!= graphInfo.getGraphData().getColLabelsName().get(i) && !rowList.get(i).equals(graphInfo.getGraphData().getColLabelsName().get(i)) )
							tmp = graphInfo.getGraphData().getColLabelsName().get(i).toString();
						
						if(tmp != null)
							tmp = Parser.unescapeEntities(tmp, false);
						
						if(!tmp.equalsIgnoreCase("other")) {
							dataMap.put("title", tmp);
							dataMap.put("color", barColor[updatedColorInfoList.get(i)%barColor.length]);
							dataMap.put("valueField", tmp1);
							dataRulesMap.add(dataMap);
						}else {
							dataMapOther.put("title", tmp);
							dataMapOther.put("color", barColor[updatedColorInfoList.get(i)%barColor.length]);

							dataMapOther.put("valueField", tmp1);	
						}
					}				 
				}
					try {
						if(!dataRulesMap.isEmpty() && !graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties()
								.getLegendValuesOrder().equalsIgnoreCase("option3")){
							dataRulesMap.sort(Comparator.comparing(o -> String.valueOf(o.get("title")), String.CASE_INSENSITIVE_ORDER));
							
						}
						if(!dataMapOther.isEmpty()) {
							dataRulesMap.add(dataMapOther);
						}
					}catch(Exception e) {
						ApplicationLog.error(e);
					}
					legend.setData(dataRulesMap);
				}
				//}
				
				
				//legend.setDivId("legenddivs");
				if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isEnable())
				{//
					legend.setValueText("");
				}
				if(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().isDrillDown())
				{
					legend.setSwitchable(false);
					//legend.setClickLabel("");
				}
				else
				{
					legend.setSwitchable(true);
				}
				if(isContextFilter)
				{
					legend.setSwitchable(false);
				}
				if(isContextFilter && graphInfo.getGraphData().getDataLabel() != null && graphInfo.getGraphData().getDataLabel().equalsIgnoreCase("data"))
				{
					legend.setSwitchable(true);
				}
				
				//------------------------------------------- Legend Panel Start--------------------------------------------------
				legend.setVerticalGap(5);
				String position="";
				switch(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelPosition())
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
				if(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().isVisible()) {
					legend.setBackgroundAlpha(1);
					legend.setBackgroundColor(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().getBackGroundColor());

					if(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().isBackgroundTransparent()) {
						legend.setBackgroundAlpha(0);
					}
					else{
						legend.setBackgroundAlpha(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelBackgroundProperties().getTransparency()/100.0);
					}
				}
				else {
					legend.setBackgroundAlpha(0);
					legend.setBackgroundColor("");
				}
				//Legend Margin start
				//legend.setAutoMargins(true);
				
				legend.setMarginTop(5.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getTopMargin());
				legend.setMarginBottom(5.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getBottomMargin());
				legend.setMarginLeft(10.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getLeftMargin());
				legend.setMarginRight(20.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getRightMargin());
				
				if(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll() != 0.0)
				{	
					legend.setMarginTop(5.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
					legend.setMarginBottom(5.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
					legend.setMarginLeft(10.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
					legend.setMarginRight(20.0+graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().getLegendPanelMarginProperties().getAll());
				}
				
				//Legend Margin end

				//this allows distance between on hover value and legend value(title)
				legend.setEqualWidths(false);
				legend.setAlign("center");

				//------------------------------------------- Legend Panel End--------------------------------------------------

				//------------------------------------------- Legend Title Start--------------------------------------------------
				if(graphInfo.getGraphProperties().getLegendProperties().getTitleProperties().isTitleVisible())
				{
					String legendTitle = null;
					if(graphInfo.getGraphProperties().getLegendProperties().getTitleProperties().getTitle().equals(""))
					{
						if(isLegendVisible)
						{
							legendTitle = graphInfo.getGraphData().getRowLabel();	
							if(rowLabel.equalsIgnoreCase("Legend"))
								legendTitle = "";
						}
						else
						{
							legendTitle = graphInfo.getGraphData().getColLabel();
						}
					}
					else
					{	
						legendTitle = graphInfo.getGraphProperties().getLegendProperties().getTitleProperties().getTitle();
					}	
					if(legendTitle!=null)
					legendTitle = Parser.unescapeEntities(legendTitle, false);		

					legend.setTitle(legendTitle);
				}
				else
				{	
					legend.setTitle("");
				}
				legend.setFontSize(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getFontSize());
			/*	legend.setFontSize(graphInfo.getGraphProperties().getLegendProperties().getTitleProperties().getTitleFont().getFontSize());*/
				//------------------------------------------- Legend Title End--------------------------------------------------
				//------------------------------------------- Legend Values Start--------------------------------------------------
				int maxColumns = 0;
				switch(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesColumn())
				{
				case 1:
					maxColumns = 1;
					break;
				case 2:
					maxColumns = 2;
					break;
				case 3:
					maxColumns = 3;
					break;
				case 4:
					maxColumns =4;
					break;
				default:
					maxColumns = 100;
					break;

				}
				legend.setMaxColumns(maxColumns);
				/*legend.setVerticalGap(5);
				legend.setHorizontalGap(0);*/

				if(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option1") || 
						graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties()
						.getLegendValuesOrder().equalsIgnoreCase("option3")){
					legend.setReversedOrder(false);
				} 
				else if(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesOrder().equalsIgnoreCase("option2")){
					legend.setReversedOrder(true);
				}

				//------------------------------------------- Legend Values End--------------------------------------------------
				//------------------------------------------- Legend Icon Start--------------------------------------------------
				String legendIconShape="";

				int markerSize = graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getWidth();
				switch(graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getLegendIconSelectShape())
				{
				case "None": legendIconShape="none"; break;
				case "Square": legendIconShape="square"; break;
				case "Circle": legendIconShape="circle"; break;
				case "TriangleUp": legendIconShape="triangleUp"; break;
				case "TriangleDown": legendIconShape="triangleDown"; break;
				case "TriangleLeft": legendIconShape="triangleLeft"; break;
				case "TriangleRight": legendIconShape="triangleRight"; break;
				/*case "Line": legendIconShape="line"; break;*/
				case "Diamond": legendIconShape="diamond"; break;
				case "Bubble": legendIconShape="bubble"; break;
				}
				legend.setMarkerSize(markerSize);
				legend.setMarkerType(legendIconShape);

				if(graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().isVisible()
						&& graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderStyle().equalsIgnoreCase("solid")){
					legend.setMarkerBorderAlpha(1);
					legend.setMarkerBorderThickness(graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderWidth());
					legend.setMarkerBorderColor(graphInfo.getGraphProperties().getLegendProperties().getLegendIconProperties().getLegendIconBorderProperties().getAllBorderColor());
				}
				else
				{
					legend.setMarkerBorderAlpha(0);
					legend.setMarkerBorderColor("");
				}
				//------------------------------------------- Legend Icon End-------------------------------------------------- 
				((GraphLegendJson) graphJson).setLegend(legend);			
			}
		
		//------------------------------------------- Legend End--------------------------------------------------
		
		//Dashboard purpose
		if(colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
		{
			graphJson.setMultipleMeasure(true);
		}
		else
		{
			graphJson.setMultipleMeasure(false);
		}
		//Dashboard purpose end
		
		List allLabels = new ArrayList();
		graphJson.setAllLabels(allLabels);

		List<ValueAxis> valueAxisList = new ArrayList<ValueAxis>();
		ValueAxis valueAxis = new ValueAxis();
		valueAxis.setAxisAlpha(0);
		valueAxis.setPosition("left");
		valueAxis.setTitle("New Ones");
		valueAxisList.add(valueAxis);
		graphJson.setValueAxis(valueAxisList);

		double startDuration = 0.0;
		if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isAnimationPlayable())
			startDuration = 0.25;
		graphJson.setStartDuration(startDuration);//Play or Stop Animation

		//------------------------------------------- Graphs Start (barGraphs start)--------------------------------------------------	
		if(isLegendVisible && multipleMeasures > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))
		{
			isMultipleMeasure = true;
			if(graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("legend"))
				noOfMeasure = 1;
			else	
			noOfMeasure = graphInfo.getDataColLabels3().size();//Multiple Measures
			rowListSize*=noOfMeasure;
			
			for (int d=0;d < graphInfo.getDataColLabels3().size();d++) //changed to noofmeasure to datalabelsize
			{
				dataLabelList.add(d, graphInfo.getDataColLabels3().get(d).toString());
			}
			noOfYAxis = noOfMeasure;
		}
		if((graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH) && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
		{
			noOfYAxis = rowListSize;
		}
		
		
		//Precision label list
		List digitsAfterDecimal = new ArrayList();
		List yAxisTitleList = new ArrayList();
		precisionLabelCounter=1;
		
		if((graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH) && (graphInfo.getGraphData().getRowLabel() != null &&  graphInfo.getGraphData().getColLabel() != null))
				|| (graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH) && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend"))
				|| (graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH) && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
		{
			precisionLabelList = new ArrayList();//for bug 13304
			precisionLabelCounter = graphInfo.getDataColLabels3().size();
			if(graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))
			{	
				for(int i=0;i<precisionLabelCounter;i++)
				{
					for(int j=0;j<rowList.size();j++)
					{	
						if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isShowadAdjustedSuffixed())
						{	
							int prefix = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getAdjustedDigit();
							if(!graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible())
								prefix = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit();
							String precisionLabel="";
							switch(prefix)
							{
							case 0:
								precisionLabel="";
								break;
							case 3:
								precisionLabel="K";
								break;
							case 5:
								precisionLabel="L";
								break;
							case 6:
								precisionLabel="M";
								break;
							case 7:
								precisionLabel="Cr";
								break;
							case 9:
								precisionLabel="Bn";
								break;
							}
							
								
							precisionLabelList.add(precisionLabel);
						}
						else
						{
							precisionLabelList.add("");
						}
						digitsAfterDecimal.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getNumberOfDigits());
					}
					//Code for Data value Y Axis Title start
					yAxisTitleList.add(graphInfo.getDataColLabels3().get(i).toString());
					//Code for Data value Y Axis Title end
				}
			}
			else
			{
				for(int i=0;i<precisionLabelCounter;i++)
				{	
					if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isShowadAdjustedSuffixed())
					{	
						int prefix = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getAdjustedDigit();
						if(!graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible())
							prefix = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit();
						String precisionLabel="";
						switch(prefix)
						{
						case 0:
							precisionLabel="";
							break;
						case 3:
							precisionLabel="K";
							break;
						case 5:
							precisionLabel="L";
							break;
						case 6:
							precisionLabel="M";
							break;
						case 7:
							precisionLabel="Cr";
							break;
						case 9:
							precisionLabel="Bn";
							break;
						}
						precisionLabelList.add(precisionLabel);
					}
					else
					{
						precisionLabelList.add("");
					}
					digitsAfterDecimal.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getNumberOfDigits());
					
					//Code for Data value Y Axis Title start
					if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() > i && graphInfo.getGraphData().getColLabelsName().get(i) != null)
						yAxisTitleList.add(graphInfo.getGraphData().getColLabelsName().get(i).toString());
					else
						yAxisTitleList.add(graphInfo.getDataColLabels3().get(i).toString());
					//Code for Data value Y Axis Title end
				}
			}
		}
		else
		{
			for(int i=0;i<rowListSize;i++)
			{	
				if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().isShowadAdjustedSuffixed())
				{	
					int prefix = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit();
					String precisionLabel="";
					switch(prefix)
					{
					case 0:
						precisionLabel="";
						break;
					case 3:
						precisionLabel="K";
						break;
					case 5:
						precisionLabel="L";
						break;
					case 6:
						precisionLabel="M";
						break;
					case 7:
						precisionLabel="Cr";
						break;
					case 9:
						precisionLabel="Bn";
						break;
					}
					precisionLabelList.add(precisionLabel);
				}
				else
				{
					precisionLabelList.add("");
				}
				digitsAfterDecimal.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getNumberOfDigits());
				
				//Code for Data value Y Axis Title start
				if(colLabelsName)
				{
					if(graphInfo.getGraphData().getColLabelsName().size() < rowListSize)
						yAxisTitleList.add(graphInfo.getGraphData().getColLabelsName().get(0).toString());
					else
						yAxisTitleList.add(graphInfo.getGraphData().getColLabelsName().get(i).toString());
				}
					
				else
				{
					if(dataLabel != null)
						yAxisTitleList.add(dataLabel);
				}
				//Code for Data value Y Axis Title end
			}
		}
		
		List dataColList = new ArrayList();
		if(graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
		{
			precisionLabelCounter = graphInfo.getDataColLabels3().size();
			for(int i=0;i<precisionLabelCounter;i++)
			{	
				if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() > i && graphInfo.getGraphData().getColLabelsName().get(i) != null)
					dataColList.add(graphInfo.getGraphData().getColLabelsName().get(i).toString());
				else
					dataColList.add(graphInfo.getDataColLabels3().get(i).toString());
			}
		}
		else
		{
			for (int i = 0; i < rowListSize; i++) {
				if(colLabelsName)
				{
					if(graphInfo.getGraphData().getColLabelsName().size() < rowListSize)
						dataColList.add(graphInfo.getGraphData().getColLabelsName().get(0).toString());
					else
						dataColList.add(graphInfo.getGraphData().getColLabelsName().get(i).toString());
				}
					
				else
				{
					if(dataLabel != null)
						dataColList.add(dataLabel);
				}
			}
		}
		
		List<String> originalDataColList = new ArrayList<String>();
		if(!dataColList.isEmpty()) {
			Map<String, String> colLabelsMap = graphInfo.getGraphProperties().getColLabelsMap();
			for (int i = 0; i < dataColList.size(); i++) {
				for (Entry<String, String> e : colLabelsMap.entrySet()) {
					if(dataColList.get(i).equals(e.getValue()))
						originalDataColList.add(e.getKey());
				}
			}
		}
		if(originalDataColList.isEmpty())
			originalDataColList.addAll(dataColList);
		
		List<String> originalRowList = new ArrayList<String>();
		//Added code for Column Labels Special char($) (For Bug #12443)
		if(null != rowLabel && rowLabel.equalsIgnoreCase("Legend")
				 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)) {
			
			if(!rowList.isEmpty()) {
				Map<String, String> colLabelsMap = graphInfo.getGraphProperties().getColLabelsMap();
				for (int i = 0; i < rowList.size(); i++) {
					for (Entry<String, String> e : colLabelsMap.entrySet()) {
						if(rowList.get(i).equals(e.getKey())) // changed value to key
							originalRowList.add(e.getKey());
					}
				}
				if(!originalRowList.isEmpty()) {
					rowList = new ArrayList();
					rowList.addAll(originalRowList);
				}
			}
		}
		
		List<String> originalDataList = new ArrayList<String>();
		List<String> originalDataLabelList = new ArrayList<String>();
		List<String> dataColLabelsList = new ArrayList<String>(graphInfo.getDataColLabels3());
		if(graphInfo.getDataColLabels3().size() > 1
				 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)) {
			
			if(!dataColLabelsList.isEmpty()) {
				Map<String, String> colLabelsMap = graphInfo.getGraphProperties().getColLabelsMap();
				for (int i = 0; i < dataColLabelsList.size(); i++) {
					for (Entry<String, String> e : colLabelsMap.entrySet()) {
						if(dataColLabelsList.get(i).equals(e.getKey())) {	//value to key
							originalDataList.add(e.getKey());
							originalDataLabelList.add(e.getValue());
						}
					}
				}
				if(originalDataList.isEmpty())
					originalDataList.addAll(dataColLabelsList);
				if(originalDataLabelList.isEmpty())
					originalDataLabelList.addAll(dataColLabelsList);
			}
		}
		
		//Column Labels Special char($) end
		
		for (int k = 0; k < rowListSize; k++) {
			lineThicknessList.add(1);
		}
		//below variables same as barGraph 
		//int colorType = graphInfo.getGraphProperties().getColorType();
		//List<String> customColors = graphInfo.getGraphProperties().getCustomColors();
		//String sameColor = graphInfo.getGraphProperties().getColor();
		
		//int pointColorType = graphInfo.getGraphProperties().getPointColorType();
		//List<String> pointCustomColors = graphInfo.getGraphProperties().getPointCustomColors();
		//String pointSameColor = graphInfo.getGraphProperties().getPointcolor();
		if(graphInfo.getGraphType() == GraphConstants.LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH)
		{	
			colorType = graphInfo.getGraphProperties().getLineColorType();
			customColors = graphInfo.getGraphProperties().getLineCustomColors();
			sameColor = graphInfo.getGraphProperties().getLinecolor();

		}
		
		switch(colorType)
		{
		case 1:
			if(customColors != null)
			{
				for (int i = 0; i < customColors.size(); i++) {
					if(i > (barColor.length-1))// || i > (bulletColor.length-1))
					{
						barColor = appendValue(barColor, customColors.get(i));
					}
					else
					{	
					barColor[i] = customColors.get(i);
					}
				}
			}
			break;
		case 2:
			barColor = new String[]{sameColor};
			break;
		}
		switch(pointColorType)
		{
		case 0:
			bulletColor = barColor;break;
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
		//Decides the type of graph
		if(graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH 
				|| graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH)
		{
			type = "column";			
			fillAlpha = 1;

			for (int k = 0; k < rowListSize; k++) {
				lineThicknessList.set(k,0);
			}
		}
		else if(graphInfo.getGraphType() == GraphConstants.LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH)
		{
			type = "line";			
			fillAlpha = 0;
			switch(graphInfo.getGraphProperties().getGraphLineProperties().getType())
			{
			case 1:lineAlpha =1;bulletAlpha = 0; bulletBorderAlpha = 0;isLine=true;break;
			case 2:lineAlpha = 0;bulletAlpha = 1;bulletBorderAlpha = 1;break;
			case 3:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;type = "smoothedLine";break;
			case 4:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;type = "step";break;
			default:lineAlpha =1;bulletAlpha = 1;bulletBorderAlpha = 0;break;
			}
			for (int k = 0; k < rowListSize; k++) {
				bulletList.add("round");
				bulletSizeList.add(8);
				lineStyleList.add(0);
				lineThicknessList.add(8);
				borderWidthList.add(0);
				bulletStyleList.add(7);
				borderColorList.add("none");
			}
			if(graphInfo.getGraphProperties().getLineType() == 0)
			{
				int dashLength = 0;
				for (int i = 0; i < rowListSize; i++) {

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

					for (int l = 0; l < graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().size(); l++) {
						int lineStyle = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getStyle());
						int customLineThickness = Integer.parseInt(graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinePropertiesList().get(l).getThickness());
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
						if(l < lineStyleList.size())
						{	
							lineStyleList.set(l, dashLength);
							lineThicknessList.set(l, customLineThickness);
						}
					}
				}
			}
			if(graphInfo.getGraphProperties().getPointType() == 0)
			{
				for (int i = 0; i < rowListSize; i++) {

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
							borderColorList.set(i, barColor[graphInfo.getColorInfoList().get(i)%barColor.length]);//Changed for Bug #15447 barColor[i%barColor.length]);
						bulletStyleList.set(i,bulletStyle);
					}
				
				}
			}
			else
			{
				if(null != graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList() && graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().size() != 0)
				{
					for (int l = 0; l < graphInfo.getGraphProperties().getGraphLineProperties().getGraphlinepointPropertiesList().size(); l++) {


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
		else if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
				|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
		{
			type = "column";			
			fillAlpha = 1;			

			for (int k = 0; k < rowListSize; k++) {
				lineThicknessList.set(k, 0);
			}
		}
		else if(graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH
				|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
		{
			type = "line";			
			double getAreaTransparency = (double)graphInfo.getGraphProperties().getTranceperancy();
			fillAlpha = (100 - getAreaTransparency) / 100;

			bulletAlpha = 0;

		}
		colorLength = barColor.length;
		
		//not req
		/*int paginationIndex=startIndex+Quantity;
		if(paginationIndex >= rowListSize)
			paginationIndex=rowListSize;
		if(rowListSize < Quantity)
			paginationIndex=rowListSize;*/
		
		int k=0;
		
		//String colLabel = graphInfo.getGraphData().getColLabel();
		List colList = graphInfo.getGraphData().getColList();
		
		int colListSize = colList.size();

		if(colListSize >1 && rowListSize == 1) //Row Is blank , Only Column and Measure 
			colListSize=1;

		//		if(colListSize >1 && rowListSize > 1) //Row Column and Measure
		//			colListSize=1;

		//List dataList = graphInfo.getGraphData().getDataList();

		//Trend Line Start
		Map trendMAp = graphInfo.getGraphData().getTrendMap();
		boolean isTrend =false;
		int trendCount;

		int noOfTrendLines = 0;
		if(trendMAp != null)
			noOfTrendLines = trendMAp.size(); 
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

			Map<Integer, TrendLineProperties> testMap = graphInfo.getGraphProperties().getTrendlinePropertiesMap();
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
		//trend Line End
		
		int inull=0;
		
		List valueList = new ArrayList<>();
		List<Graphs> graphsList = new ArrayList<Graphs>();
		
		String unEscapeHtml = "";
		
		//9 Apr 2019[for graph]
		List hideGraphsList = new ArrayList();
		Map graphsVisibleMap = graphInfo.getGraphProperties().getGraphsVisibleMap();
		for(int i=0;i<graphInfo.getDataColLabels3().size();i++)//Changed from getDataColumns() to getDataColLabels3() for Jira Bug SDEVAPR20-79
		{
			if(graphsVisibleMap.get(graphInfo.getDataColLabels3().get(i)) != null && graphsVisibleMap.get(graphInfo.getDataColLabels3().get(i)).toString().equals("false")) {
				hideGraphsList.add(i);
			}
		}

		//9 Apr 2019
		
		if(isLegendVisible && multipleMeasures > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))
		{
			int[] val = new int[rowListSize];
			int valueAxisNumber = -1;
			
			int sizes=graphInfo.getGraphProperties().getyAxisPropertiesMap().size();
			int counter=0;
			List valueAxisVisiblityList= new ArrayList();
			
			// 9 Apr [p.p]
			List FinalGraphsList = new ArrayList();
			
			int rowSize = rowList.size();
			if(!graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend"))
			{
			for(int i=0;i<hideGraphsList.size();i++)
			{
				
				int startInd = (int) hideGraphsList.get(i)*rowSize;
				int endInd = (((int) hideGraphsList.get(i)+1)*rowSize);
					for(int j =startInd ;j<endInd;j++)
					{
						FinalGraphsList.add(j);//cahnges [p.p]
					}
					
			}
			hideGraphsList = new ArrayList();
			hideGraphsList.addAll(FinalGraphsList);
			
			}
			//9 Apr[p.p]
			for(int i=0;i<sizes;i++)
			{
				
				if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().isVisible()
						/*|| graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().isVisible()*/
						|| graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible())
				{
					for(int j=0;j<rowList.size();j++)
					{
						valueAxisVisiblityList.add("valueAxes"+counter);
					}
					counter++;
				}
				else
				{
										
					boolean sizeflag=false;
					for(int l=0;l<sizes;l++)
					{
						if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+l).getLineProperties().isVisible())
						{
							for(int j=0;j<rowList.size();j++)
							{
							valueAxisVisiblityList.add("valueAxes"+l);
							
							}
							sizeflag=true;
							break;
						}else{
							continue;
						}
						
					}
					if(!sizeflag)
					{
						for(int j=0;j<rowList.size();j++)
						{
						valueAxisVisiblityList.add("valueAxes0");
						}
					}
					counter++;
				
					
					
					
//					for(int j=0;j<rowList.size();j++)
//					{
//						valueAxisVisiblityList.add("valueAxes0");
//					}
//					counter++;
				}
			}
			
			
			
			/*for(int i=0;i<rowListSize;i++)
			{
				if((i%(rowListSize/multipleMeasures))==0)
				{
					valueAxisNumber++;
				}
				val[i] = valueAxisNumber;
			}*/
			if(graphInfo.getGraphData().getRowLabel()!= null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend"))//when more then one measure and non  clust
			{
				val = new int[1];
				val[0] = 0;
			}
			int index = 0;
			int titleIndex = 0;
			int cnt = -1;
			for (int j = 0; j < rowListSize; j++) {
				/*if(!dataList.isEmpty())
				{*/
					/*if(dataList.get(i*rowListSize+j)!=null || rowListSize < Quantity)
					{*/			
						/*if(k==startIndex || rowListSize < Quantity)
						{

							if(paginationIndex==startIndex)
								return graphsList;*/
								if((j%(rowListSize/noOfMeasure))==0)
								{
									index=0;//Fetching appropriate ValueField when Multiple Measure
									cnt++;
								}
								Graphs graphs = new Graphs();  // changes [p.p]
								
								if((j%(rowListSize/noOfMeasure))==0 && j != 0)
								{
									graphs.setNewStack(true);
									//For $Y_AXIS_STACKED_TITLE$
									titleIndex++;
								}
							if(!hideGraphsList.contains(j)) { // changes [p.p]
								
								if(rowListSize==graphInfo.getDataColLabels3().size()) // added to get proper data when hide
									index=j;
								
							graphs.setShowHandOnHover(true);////feature req 13494
							if(digitsAfterDecimal.get(j) != null)
								graphs.setPrecision(Double.valueOf(digitsAfterDecimal.get(j).toString()));
							else
								graphs.setPrecision(0.0);
							if(graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend"))
							{
								graphs.setValueAxis("valueAxes"+val[0]);
							}
							else
							{
							graphs.setValueAxis(valueAxisVisiblityList.get(j).toString());//0
								if((graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
									&& isLegendVisible && multipleMeasures > 1 && graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().isShowTotalValue())
								{
									graphs.setValueAxis("valueAxes"+titleIndex);//Added for feature request of Stack total
								}
							}
							
							/*if((j%(rowListSize/noOfMeasure))==0 && j != 0)
							{
								graphs.setNewStack(true);
								//For $Y_AXIS_STACKED_TITLE$
								titleIndex++;
							}*/

							//negative bar color
							if(!graphInfo.getGraphProperties().getNegativeBarColor().equalsIgnoreCase("#ff0000"))
							{
								graphs.setNegativeFillColors(graphInfo.getGraphProperties().getNegativeBarColor());
							}

							/*if((j%(rowListSize/noOfMeasure))==0)
							{
								index=0;//Fetching appropriate ValueField when Multiple Measure
								cnt++;
							}*/
							//Data value start
							if(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().isDataValuePointVisible())
							{
								String labelPosition = graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getPosition();
								if(labelPosition.equalsIgnoreCase("Top"))
								{
									labelPosition = "top";
								}
								if(labelPosition.equalsIgnoreCase("Bottom"))
								{
									labelPosition = "inside";
								}
								if(labelPosition.equalsIgnoreCase("Top") &&
										 (graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH ))
								{
										labelPosition = "bottom";
								}
								
								if(labelPosition.equalsIgnoreCase("Center"))
								{
									labelPosition = "middle";
								}
								//for bug 12193
								if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
								{
									if(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getPosition().equalsIgnoreCase("Top"))
									{
										labelPosition = "right";
										if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
											labelPosition = "left";
									}
								}
								///for bug 12193
								//Vertical 
								graphs.setLabelRotation(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getRotationAngle());
								graphs.setLabelOffset(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getLabelOffset());
								
								unEscapeHtml = graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getDataValuePointFormatText();
								String dataVAlues = unescapeHtml(unEscapeHtml);

								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_VALUE,"[[dvTruncatedLabel]]");//Changed from truncatedLabel to dvTruncatedLabel for feature request 15092
								
								String formattedRowListValue = rowList.get(index).toString();
								//Added code for Bug #13406 start
								if(!dateRowList.isEmpty() && dateRowList.size() > index
										&& null != dateRowList.get(index) && !dateRowList.get(index).equals(AppConstants.NULL_DISPLAY_VALUE)) {
									//String tmp2 = dateRowList.get(index).toString();
									String stringFormat;
									stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
									stringFormat = stringFormat.replaceAll("&#39;", "'");
									Calendar cal = Calendar.getInstance();
									Date axisDate = new Date();
									axisDate = (Date) dateRowList.get(index);
									cal.setTime(axisDate);
									stringFormat=stringFormat.trim();
									formattedRowListValue = new SimpleDateFormat(stringFormat).format(cal.getTime());
								}//Added code for Bug #13406 end
								
								//Added for NeGD feature request 15092 start [8 Aug 2019]
								truncatedLabelString = formattedRowListValue;//rowList.get(index).toString();
								if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
									|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
									|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
								{
									switch(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCharacterLimit())
									{
									case "auto":
										dvTruncateCharLimit = 15;
										break;
									case "custom":
										dvTruncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCustomCharacterLimit());
										break;
									}
									if (truncatedLabelString.length() > dvTruncateCharLimit) {
										truncatedLabelString = truncatedLabelString.substring(0, dvTruncateCharLimit)+"..";
									}
								}
								//Added for NeGD feature request end [8 Aug 2019]
								dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,truncatedLabelString);
								if(dataColList != null && !dataColList.isEmpty())
								{
									if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
									{
									if(dataColList.get(index) != null && dataColList.get(index).toString().equalsIgnoreCase("data"))
										dataVAlues = dataVAlues.replace(rowList.get(index).toString(),"[[zaxisvalue"+graphInfo.getDataColLabels3().get(index).toString().replaceAll("[^\\s\\w]*","")+"]]");
									else
										dataVAlues = dataVAlues.replace(rowList.get(index).toString(),originalDataLabelList.get(index).toString());//"[[zaxisvalue"+originalDataColList.get(index).toString().replaceAll("[^\\s\\w]*","")+"]]");
									}
								}
								//X_AXIS_TITLE start
								if(colLabel != null && !colLabel.equalsIgnoreCase("null"))
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"[[xaxisTitle]]");
								else
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"");
								//X_AXIS_TITLE end
								
								//Z_AXIS_TITLE start
								if(isLegendVisible)
								{
									if(!(rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
									{/*
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
									}
									else
									{*/
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,StringUtil.replaceSpecialCharWithHTMLEntity(rowLabel));
									}
								}
								/*else
								{
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
								}*/
								//Z_AXIS_TITLE end
								
								if(graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH || 
										graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
								{
									String yaxisStackedValue = index+(rowList.get(index).toString()+originalDataList.get(cnt).toString()).replaceAll("[^\\s\\w]*","");
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[[AbsrealTotal" +titleIndex+ "]]");
									dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[Abs"+yaxisStackedValue+"]]");
									dataVAlues = dataVAlues.replace("[[Abs"+yaxisStackedValue+"]]", "[[Abs"+yaxisStackedValue+"]]"+precisionLabelList.get(j));
									//dataVAlues = dataVAlues.replace("[[AbsrealTotal]]", "[[AbsrealTotal]]"+precisionLabelList.get(j));
									if(colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
									{
										dataVAlues = dataVAlues.replace("[[AbsrealTotal" +titleIndex+ "]]", "[[AbsrealTotal]]"); 
												//StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[[AbsrealTotal]]");
										dataVAlues = dataVAlues.replace("[[AbsrealTotal]]", "[[AbsrealTotal]]"+precisionLabelList.get(j));
										if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
										{
											if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
												dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[yaxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
											else
												dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,originalDataLabelList.get(j).toString());//"[[yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
										}
										
										/*if(yAxisTitleList.get(j) != null && yAxisTitleList.get(j).toString().equalsIgnoreCase("data"))
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,graphInfo.getDataColLabels3().get(j).toString());
										else
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,yAxisTitleList.get(j).toString());*/
									}
									else
									{
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[[AbsrealTotal" +titleIndex+ "]]");
										dataVAlues = dataVAlues.replace("[[AbsrealTotal" +titleIndex+ "]]", "[[AbsrealTotal" +titleIndex+ "]]"+precisionLabelList.get(j));
										dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_TITLE, originalDataLabelList.get(titleIndex).toString());//"[[yAxisTitle" + titleIndex + "]]");
									}
									
									
									countmm = 0;
									if(isMultiMeasure)
									{
										for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
											//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
												++countmm;
												String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString()+graphInfo.getDataColLabels3().get(cnt).toString().replaceAll("[^\\s\\w]*", "");
												dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
												dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_TITLE"+countmm+"$",originalDataLabelList.get(m).toString());//"[[yaxisTitle"+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*","")+"]]");
												
											//}
										}
									}else {
										if(multipleMeasures>1 && (dataLabel!=null || dataLabel!="Data" || dataLabel!="data" || dataLabel!="")) {
											
											for (int m = 0; m < multipleMeasures; m++) {
												++countmm;
												yaxisStackedValue = index+(rowList.get(index).toString()
														+ graphInfo.getDataColLabels3().get(m).toString()).replaceAll("[^\\s\\w]*", "");
												dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_STACKED_VALUE" + countmm + "$",
														"[[Abs" + yaxisStackedValue + "]]" + precisionLabelList.get(j));												
												dataVAlues = StringUtil.replace(dataVAlues, "$Y-AXIS_TITLE" + countmm + "$",
														originalDataLabelList.get(m).toString());


											}
										}
									}
								}
								else
								{
									String yaxisValue = index+(rowList.get(index).toString()+dataLabelList.get(cnt).toString()).replaceAll("[^\\s\\w]*","");
									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[[Abs"+yaxisValue+"]]");
									dataVAlues = dataVAlues.replace("[[Abs"+yaxisValue+"]]", "[[Abs"+yaxisValue+"]]"+precisionLabelList.get(j));
									//dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[AbsrealTotal]]");
									if(dataLabel != null && !dataLabel.equalsIgnoreCase("null"))
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,dataLabel);
									else
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"");
									
									
									countmm = 0;
									if(isMultipleMeasure)
									{
										for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
											if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
												countmm++;
												String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString()+graphInfo.getDataColLabels3().get(cnt).toString().replaceAll("[^\\s\\w]*", "");
												dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
												dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_TITLE"+countmm+"$","[[yaxisTitle"+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*","")+"]]");
												
											}
										}
									}
								}
								
								if(dataVAlues!=null)
								dataVAlues = Parser.unescapeEntities(dataVAlues, false);		
								graphs.setLabelPosition(labelPosition);
								graphs.setLabelText(dataVAlues);//values on bar
								graphs.setColor(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getFontColor());//values on bar color
								graphs.setFontSize(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getFontSize());
								graphs.setShowAllValueLabels(true);

							}
							//Data value end

							//Mouse over value start
							if(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isMouseOverTextEnable())
							{
								String mouseOverString = graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().getDataValueMouseOverFormatText();
								if(mouseOverString!=null)
								mouseOverString=mouseOverString.replace("&lt;/br&gt", "");
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_VALUE,"[[truncatedLabel]]");
								
								String formattedRowListValue = rowList.get(index).toString();
								//Added code for Bug #13406 start
								if(!dateRowList.isEmpty() && dateRowList.size() > index
										&& null != dateRowList.get(index) && !dateRowList.get(index).equals(AppConstants.NULL_DISPLAY_VALUE)) {
									//String tmp2 = dateRowList.get(index).toString();
									String stringFormat;
									stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
									stringFormat = stringFormat.replaceAll("&#39;", "'");
									Calendar cal = Calendar.getInstance();
									Date axisDate = new Date();
									axisDate = (Date) dateRowList.get(index);
									cal.setTime(axisDate);
									stringFormat=stringFormat.trim();
									formattedRowListValue = new SimpleDateFormat(stringFormat).format(cal.getTime());
								}//Added code for Bug #13406 end
							
								mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,formattedRowListValue);
							
								if(dataColList != null && !dataColList.isEmpty())
								{
									if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
									{
									if(dataColList.get(index) != null && dataColList.get(index).toString().equalsIgnoreCase("data"))
										mouseOverString = mouseOverString.replace(formattedRowListValue,"[[zaxisvalue"+graphInfo.getDataColLabels3().get(index).toString().replaceAll("[^\\s\\w]*","")+"]]");
									else
										mouseOverString = mouseOverString.replace(formattedRowListValue,originalDataLabelList.get(index).toString());//"[[zaxisvalue"+originalDataColList.get(index).toString().replaceAll("[^\\s\\w]*","")+"]]");
									}
								}
								//X_AXIS_TITLE start
								if(colLabel != null && !colLabel.equalsIgnoreCase("null"))
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"[[xaxisTitle]]");
								else
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"");
								//X_AXIS_TITLE end
								
								//Z_AXIS_TITLE start
								if(isLegendVisible)
								{
									if(!(rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
									{/*
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
									}
									else
									{*/
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,StringUtil.replaceSpecialCharWithHTMLEntity(rowLabel));
									}
								}
							/*	else
								{
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
								}*/
								//Z_AXIS_TITLE end

								if(graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH || 
										graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH ||
												graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH ||
										graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)//non clus multi value
								{
									// Added AutoValue-based tooltip handling for stacked graph values.
									// When AutoValue is enabled, use rendered graph value ([[value]])
									// instead of manual Abs value placeholders to avoid tooltip mismatch
									String yaxisStackedValue = index+(rowList.get(index).toString()+originalDataList.get(cnt).toString()).replaceAll("[^\\s\\w]*","");
									if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[AbsrealTotal"+ titleIndex + "]]");
										mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[Abs"+yaxisStackedValue+"]]");
										mouseOverString = mouseOverString.replace("[[Abs"+yaxisStackedValue+"]]", "[[Abs"+yaxisStackedValue+"]]"+precisionLabelList.get(j));
										mouseOverString = mouseOverString.replace("[[AbsrealTotal]]", "[[AbsrealTotal]]"+precisionLabelList.get(j));
									} else {
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[value]]");
										mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[value]]");
									}
									if(colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
									{
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											mouseOverString = mouseOverString.replace("[[AbsrealTotal" +titleIndex+ "]]", "[[AbsrealTotal]]"); 
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[AbsrealTotal]]");
											mouseOverString = mouseOverString.replace("[[AbsrealTotal]]", "[[AbsrealTotal]]"+precisionLabelList.get(j));
										} else {
											mouseOverString = mouseOverString.replace("[[AbsrealTotal" +titleIndex+ "]]", "[[value]]"); 
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[value]]");
										}
										if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
										{
											if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
												mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[yaxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
											else
												mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,originalDataLabelList.get(j).toString());//"[[yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
										}
										
									/*	if(yAxisTitleList.get(j) != null && yAxisTitleList.get(j).toString().equalsIgnoreCase("data"))
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,graphInfo.getDataColLabels3().get(j).toString());
										else
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,yAxisTitleList.get(j).toString());*/
									}
									else
									{
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[AbsrealTotal"+ titleIndex + "]]");
											mouseOverString = mouseOverString.replace("[[AbsrealTotal"+ titleIndex + "]]", "[[AbsrealTotal"+ titleIndex + "]]"+precisionLabelList.get(j));
										} else {
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[value]]");
										}
										mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_TITLE,originalDataLabelList.get(titleIndex).toString());// "[[yAxisTitle" + titleIndex + "]]");
									}
									
									countmm = 0;
									if(isMultiMeasure)
									{
										for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
											//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
												++countmm;
												String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString()+graphInfo.getDataColLabels3().get(cnt).toString().replaceAll("[^\\s\\w]*", "");
												// Added AutoValue support for stacked tooltip placeholder replacement.
												// Use dynamic rendered value ([[value]]) when AutoValue is enabled.
												if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
													mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
												} else {
													mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[value]]");
												}
												mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_TITLE"+countmm+"$","[[yaxisTitle"+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*","")+"]]");
												
											//}
										}
									}else {
										if(multipleMeasures>1 && (dataLabel!=null || dataLabel!="Data" || dataLabel!="data" || dataLabel!="")) {
											
											for (int m = 0; m < multipleMeasures; m++) {
												++countmm;
												yaxisStackedValue = index+(rowList.get(index).toString()
														+ graphInfo.getDataColLabels3().get(m).toString()).replaceAll("[^\\s\\w]*", "");
												if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
													mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE" + countmm + "$",
															"[[Abs" + yaxisStackedValue + "]]" + precisionLabelList.get(j));
												} else {
													mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE" + countmm + "$","[[value]]");
												}											
																
												mouseOverString = StringUtil.replace(mouseOverString, "$Y-AXIS_TITLE" + countmm + "$",
														originalDataLabelList.get(m).toString());


											}
										}
									}
								}
								else
								{
									String yaxisValue = index+(rowList.get(index).toString()+dataLabelList.get(cnt).toString()).replaceAll("[^\\s\\w]*","");
									if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[Abs"+yaxisValue+"]]");
										mouseOverString = mouseOverString.replace("[[Abs"+yaxisValue+"]]", "[[Abs"+yaxisValue+"]]"+precisionLabelList.get(j));
									} else {
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[value]]");
									}
									//mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[AbsrealTotal]]");
									if(dataLabel != null && !dataLabel.equalsIgnoreCase("null"))
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,dataLabel);
									else
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"");
									
									
									countmm = 0;
									if(isMultiMeasure)
									{
										for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
											//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
												++countmm;
												String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString()+graphInfo.getDataColLabels3().get(cnt).toString().replaceAll("[^\\s\\w]*", "");
												if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
													mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
												} else {
													mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_VALUE"+countmm+"$","[[value]]");
												}
												mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_TITLE"+countmm+"$",originalDataLabelList.get(m).toString());//"[[yaxisTitle"+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*","")+"]]");
												
											//}
										}
									}
								}
								graphs.setBalloonText(mouseOverString);
							}
							else
							{
								graphs.setBalloonText("");
							}
							if(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isClusteredMouseOverTextEnable())
							{
								graphs.setBalloonText("[[Abs"+index+rowList.get(index).toString()+dataLabelList.get(cnt)+"]]");
							}
							if(!graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isClusteredMouseOverTextEnable() && !graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isMouseOverTextEnable())
							{
								graphs.setBalloonText("");
							}

							//Mouse over value end

							graphs.setFillColorsField("color");
							graphs.setType(type);
							graphs.setFillAlphas(fillAlpha);
							graphs.setLineAlpha(lineAlpha);
							graphs.setLineThickness(lineThicknessList.get(j));
							graphs.setBulletAlpha(bulletAlpha);
							//Bar Graph Transparency start 
							if(graphInfo.getGraphProperties().getTranceperancy()>0)
							{	 
								double transperency =graphInfo.getGraphProperties().getTranceperancy();
								double barTransparency = ((100-transperency)/100); 
								graphs.setFillAlphas(barTransparency);
							}
							//Bar Graph Transparency end

							List<String> fillColorsList = new ArrayList<String>();
							
							if(colr== rowListSize/noOfMeasure)
							{
								colr=0;
							}
							if(graphInfo.getColorInfoList() != null && j < graphInfo.getColorInfoList().size())
								fillColorsList.add(barColor[graphInfo.getColorInfoList().get(j)%colorLength]);
							else
								fillColorsList.add(barColor[colr%colorLength]);
							

							String tmp = rowList.get(index).toString(); 
							//if(isLegendVisible){
								
								//Added code for Bug #13406 start
								if(!dateRowList.isEmpty() && dateRowList.size() > index
										&& null != dateRowList.get(index) && !dateRowList.get(index).equals(AppConstants.NULL_DISPLAY_VALUE)) {
									String stringFormat;
									stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
									stringFormat = stringFormat.replaceAll("&#39;", "'");
									Calendar cal = Calendar.getInstance();
									Date axisDate = new Date();
									axisDate = (Date) dateRowList.get(index);
									cal.setTime(axisDate);
									stringFormat=stringFormat.trim();
									tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
								}//Added code for Bug #13406 end
								
								switch(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
								{
								case "auto":
									//tmp = rowList.get(index).toString();
									int truncateCharLimitAuto = 15;
									if (tmp.length() > truncateCharLimitAuto)
										tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
									break;
								case "custom":
									tmp = rowList.get(index).toString();
									int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
									if (tmp.length() > truncateCharLimit)
										tmp = tmp.substring(0, truncateCharLimit)+"..";
									break;
								/*default:
									tmp = rowList.get(index).toString();
									break;*/
								}
								
								if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= rowListSize
									&& graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("legend"))
										tmp = graphInfo.getGraphData().getColLabelsName().get(index).toString();
								if(tmp != null)
									tmp = Parser.unescapeEntities(tmp, false);
								graphs.setTitle(tmp);
								
								if(null != rowLabel && rowLabel.equalsIgnoreCase("Legend"))
									graphs.setValueField((originalRowList.get(index).toString()+originalDataList.get(cnt).toString()));//.replaceAll("[^\\s\\w]*",""));
								else
								{
									if(null != originalDataList && originalDataList.size() > cnt)//Added for show preview
										graphs.setValueField((rowList.get(index).toString()+originalDataList.get(cnt).toString()));//.replaceAll("[^\\s\\w]*",""));
									else
										graphs.setValueField((rowList.get(index).toString()+dataLabelList.get(cnt).toString()));//.replaceAll("[^\\s\\w]*",""));
								}
								graphs.setDescriptionField("Abs"+rowList.get(index).toString()+dataLabelList.get(cnt));
								index++;
							/*}else{
								graphs.setValueField(graphInfo.getGraphData().getDataLabel());
							}*/

														
							/*if(graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH
								|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
							{*/	 
								if(graphInfo.getGraphProperties().getBarProperties().getType() == 2)
								{
									//graphJson.setAngle(30);
									//graphJson.setDepth3D(30);
									graphs.setTopRadius("1");
								}
								else if(graphInfo.getGraphProperties().getBarProperties().getType() == 3)
								{
									//	graphJson.setAngle(30);
									//	graphJson.setDepth3D(30);
									graphs.setTopRadius("0");
								}

								//Bar Gradient Start.
								if (graphInfo.getGraphProperties().getBarProperties().getGradient().isVisible())
								{
									fillColorsList.add(graphInfo.getGraphProperties().getBarProperties().getGradient().getColor());
									graphs.setFillColorsField("");
									if(graphInfo.getGraphProperties().getBarProperties().getGradient().isTransparent()){
										graphs.setFillAlphas(0.50);
									}
								}
								//Bar Gradient End
								//Corner radius
								if(graphInfo.getGraphProperties().getBarProperties().getCornerRadius() > 0)
								{
									graphs.setCornerRadiusTop(graphInfo.getGraphProperties().getBarProperties().getCornerRadius());
									//graphJson.setAngle(0);
									//graphJson.setDepth3D(0);
								}
								
								//Bar width
								if(graphInfo.getGraphProperties().getBarProperties().getBarWidth() != 100 && isBarChart)
								{
									double barWidth = (double)graphInfo.getGraphProperties().getBarProperties().getBarWidth()/100;
									graphs.setColumnWidth(barWidth);//between 0-1
								}
								//Bar width
								
								//Bar border start
								if(graphInfo.getGraphProperties().getBarProperties().getBorderProperties().isVisible()
										&& !graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderStyle().equalsIgnoreCase("none"))
								{
									graphs.setLineAlpha(1);
									graphs.setLineThickness(graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderWidth());
									graphs.setLineColor(graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderColor());
									int barBorderStyle = 0;
									switch (graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderStyle()) {
									case "none":
										graphs.setLineAlpha(0);
										graphs.setLineThickness(0);
										break;
									case "solid":
										barBorderStyle = 0;
										break;
									case "dashed":
										barBorderStyle = 5;
										break;
									case "dotted":
										barBorderStyle = 2;
										break;
									}
									graphs.setDashLength(barBorderStyle);
								}
								else
								{
									graphs.setLineAlpha(0);
									graphs.setLineThickness(0);
									//graphs.setLineColor();
									//graphs.setDashLength();
								}
								//Bar border end
							//}
							String legendColor=barColor[graphInfo.getColorInfoList().get(j)%barColor.length];
							switch(graphInfo.getGraphProperties().getGraphLineProperties().getType())
							{
							case 2:legendColor = bulletColor[graphInfo.getColorInfoList().get(j)%bulletColor.length];break;
							}
							graphs.setLegendColor(legendColor);//Legend Icon(Marker) color
							//graphs.setLegendColor(barColor[graphInfo.getColorInfoList().get(j)%barColor.length]);//Legend Icon(Marker) color
							graphs.setFillColors(fillColorsList);
							graphs.setLabelFunction("");
							graphsList.add(graphs);
							//startIndex++;

						//}
					/*}else{
						continue;
					}*/
				/*}*/
				colr++;
				k++;			
			}
		}
			//Trend Start for( bug 11562)
			if(isTrend)//trend
			{
				isTrend=false;
				String label;
				for(int c=0;c<noOfTrendLines;c++)
				{
					Graphs graphs = new Graphs();
					graphs.setShowHandOnHover(true);////feature req 13494
					graphs.setLineThickness((int)trendLineThickness.get(c));
					graphs.setLineAlpha(1);
					graphs.setType("line");
					graphs.setLabelText("[[TrendLabel" + c+ "]]" );
					graphs.setFillAlphas(0);
					graphs.setValueField(trendLineName.get(c).toString()+"trend");
					graphs.setDescriptionField("Abs"+trendLineName.get(c).toString()+"trend");
					graphs.setLineColor(trendColor.get(c).toString());
					graphs.setVisibleInLegend(false);
					graphs.setValueAxis("valueAxes1");

					if(graphInfo.getDataColLabels3().size() > 1)
					{
						String[] splitString = trendLineColoumn.get(c).toString().split(",");
						List measure = new ArrayList(graphInfo.getDataColLabels3());
						if(measure.contains(splitString[0]))
						{
							int occurenceIndex= measure.indexOf(splitString[0]);
							graphs.setValueAxis("valueAxes"+occurenceIndex);
						}
					}


					//line style
					int lineStyle = Integer.valueOf(trendLineStyle.get(c).toString());
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
						/*case 3:
						trendDashLength = 1;
						break;*/
					}
					graphs.setDashLength(trendDashLength);//year quater
					graphs.setFontSize(10);
					if(!graphInfo.getGraphData().getColLabel().equalsIgnoreCase(trendValue.get(c).toString()) && isLegendVisible==false)
					{
						graphs.setLineAlpha(0);
						graphs.setLineThickness(0);
						graphs.setLabelText("");
					}
					graphsList.add(graphs);
				}

			}
		}
		else
		{
			
			
			int[] val = new int[rowListSize];
			int valueAxisNumber = -1;
			
			int sizes=graphInfo.getGraphProperties().getyAxisPropertiesMap().size();
			int counter=0;
			List valueAxisVisiblityList= new ArrayList();
			int measurerowlistsize=rowListSize;
			
			if(multipleMeasures > 1 && graphInfo.getGraphData().getRowLabel()!=null && (graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend") || graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Data")))
				measurerowlistsize=1;

			for(int i=0;i<sizes;i++)
			{
				
				if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLineProperties().isVisible()
						/*|| graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getyAxisTitleTrendProperties().isVisible()*/
						|| graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible())
				{
					for(int j=0;j<measurerowlistsize;j++)
					{
					valueAxisVisiblityList.add("valueAxes"+counter);
					counter++;
					}
				}
				else
				{
					
					boolean sizeflag=false;
					for(int l=0;l<sizes;l++)
					{
						if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+l).getLineProperties().isVisible())
						{
							for(int j=0;j<measurerowlistsize;j++)
							{
							valueAxisVisiblityList.add("valueAxes"+l);
							
							}
							sizeflag=true;
							break;
						}else{
							continue;
						}
						
					}
					if(!sizeflag)
						for(int j=0;j<measurerowlistsize;j++)
						{
							valueAxisVisiblityList.add("valueAxes0");	//valueAxes0 to valueAxes"+counter					
						}
						
					counter++;
				}
			}

			for (int j = 0; j < rowListSize; j++) {
							if(!hideGraphsList.contains(j))
							{

								/*if(paginationIndex==startIndex
										&& graphInfo.getGraphType() != GraphConstants.STACKED_VBAR_GRAPH
										&& graphInfo.getGraphType() != GraphConstants.STACKED_HBAR_GRAPH)
									return graphsList;*/

								Graphs graphs = new Graphs();
								graphs.setShowHandOnHover(true);////feature req 13494
								String desLabel = "";
								String dvDesLabel = "";//Added for NeGD feature request 15092 [13 Aug 2019]
								if(isLegendVisible){
									desLabel = "Abs"+j+rowList.get(j).toString().replaceAll("[^\\s\\w]*","");
									dvDesLabel = "AbsDv"+j+rowList.get(j).toString().replaceAll("[^\\s\\w]*","");
								}else{
									desLabel = "Abs"+j+graphInfo.getGraphData().getDataLabel().replaceAll("[^\\s\\w]*","");
									dvDesLabel = "AbsDv"+j+graphInfo.getGraphData().getDataLabel().replaceAll("[^\\s\\w]*","");
								}
								if(digitsAfterDecimal.get(j) != null)
									graphs.setPrecision(Double.valueOf(digitsAfterDecimal.get(j).toString()));
								else
									graphs.setPrecision(0.0);
								if(colr== rowListSize/noOfMeasure)
								{
								colr=0;
								}
								//graphs.setValueAxis("valueAxes"+colr);
								graphs.setValueAxis(valueAxisVisiblityList.get(j).toString());
								//negative bar color
								if(!graphInfo.getGraphProperties().getNegativeBarColor().equalsIgnoreCase("#ff0000"))
								{
									graphs.setNegativeFillColors(graphInfo.getGraphProperties().getNegativeBarColor());
								}

								//Data value start
								if(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().isDataValuePointVisible())
								{
									String labelPosition = graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getPosition();
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
									if(labelPosition.equalsIgnoreCase("Top") &&
											 (graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH ||
												graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH ||
												graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH ))
									{
											labelPosition = "bottom";
									}
									//for bug 12193
									if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH)
									{
										if(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getPosition().equalsIgnoreCase("Top"))
										{
											labelPosition = "right";
											if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
												labelPosition = "left";
										}
									}
									///for bug 12193
									//Vertical 
									graphs.setLabelRotation(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getRotationAngle());
									graphs.setLabelOffset(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getLabelOffset());
									
									//String source = "The less than sign (<) and ampersand (&) must be escaped before using them in HTML";
									unEscapeHtml = graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getDataValuePointFormatText();
									String dataVAlues = unescapeHtml(unEscapeHtml);

									dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_VALUE,"[[dvTruncatedLabel]]");//Changed from truncatedLabel to dvTruncatedLabel for feature request 15092
									if(isLegendVisible)
									{
										if(rowList != null && !rowList.isEmpty())
										{
											String formattedRowListValue = rowList.get(j).toString();
											//Added code for Bug #13406 start
											if(!dateRowList.isEmpty() && dateRowList.size() > j
													&& null != dateRowList.get(j) && !dateRowList.get(j).equals(AppConstants.NULL_DISPLAY_VALUE)) {
												//String tmp2 = dateRowList.get(index).toString();
												String stringFormat;
												stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
												stringFormat = stringFormat.replaceAll("&#39;", "'");
												Calendar cal = Calendar.getInstance();
												Date axisDate = new Date();
												axisDate = (Date) dateRowList.get(j);
												cal.setTime(axisDate);
												stringFormat=stringFormat.trim();
												formattedRowListValue = new SimpleDateFormat(stringFormat).format(cal.getTime());
											}//Added code for Bug #13406 end
											
											//Added for NeGD feature request 15092 start [8 Aug 2019]
											truncatedLabelString = formattedRowListValue;
											if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
												|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
												|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
											{
												switch(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCharacterLimit())
												{
												case "auto":
													dvTruncateCharLimit = 15;
													break;
												case "custom":
													dvTruncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCustomCharacterLimit());
													break;
												}
												if (truncatedLabelString.length() > dvTruncateCharLimit) {
													truncatedLabelString = truncatedLabelString.substring(0, dvTruncateCharLimit)+"..";
												}
											}
											//Added for NeGD feature request end [8 Aug 2019]
											
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,truncatedLabelString);
											if(dataColList != null && !dataColList.isEmpty())
											{
												if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
												{
													if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
														dataVAlues = dataVAlues.replace(rowList.get(j).toString(),"[[zaxisvalue"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
													else
														dataVAlues = dataVAlues.replace(rowList.get(j).toString(),"[[zaxisvalue"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
												}
											}
											/*if(isLegendVisible && multipleMeasures > 1 && colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= rowListSize)
											dataVAlues = dataVAlues.replace(rowList.get(j).toString(),graphInfo.getGraphData().getColLabelsName().get(j).toString());*/
										}
										else
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_VALUE,"[[title]]");
									}
									//X_AXIS_TITLE start
									if(colLabel != null && !colLabel.equalsIgnoreCase("null"))
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"[[xaxisTitle]]");
									else
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.X_AXIS_TITLE,"");
									//X_AXIS_TITLE end
									
									//Z_AXIS_TITLE start
									if(isLegendVisible)
									{
										if(!(rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
										{/*
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
										}
										else
										{*/
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,StringUtil.replaceSpecialCharWithHTMLEntity(rowLabel));
										}
									}
									/*else
									{
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Z_AXIS_TITLE,"");
									}*/
									//Z_AXIS_TITLE end
									
									//Y_AXIS_TITLE start
									if(dataColList != null && !dataColList.isEmpty())
									{
										if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[yaxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
										else
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"[[yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
									}
									else
									{
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"");
									}
									
									
									countmm = 0;
									if(isMultiMeasure)
									{
										for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
											//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
												++countmm;												
												dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_TITLE"+countmm+"$",graphInfo.getDataColLabels3().get(m).toString());														
											//}
										}
									}
									/*if(yAxisTitleList != null && !yAxisTitleList.isEmpty())
									{
										if(yAxisTitleList.get(j) != null && yAxisTitleList.get(j).toString().equalsIgnoreCase("data"))
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,graphInfo.getDataColLabels3().get(j).toString());
										else
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,yAxisTitleList.get(j).toString());
									}
									else
									{
										dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_TITLE,"");
									}*/
									//Y_AXIS_TITLE end
	
									if(graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH)
											//&& (colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))))
									{
										// Added AutoValue-based data value handling for stacked graph values.
										// When AutoValue is enabled, use rendered graph value ([[value]])
										// instead of manual Abs value placeholders
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_STACKED_VALUE,"[["+dvDesLabel+"]]");
											dataVAlues = dataVAlues.replace("[["+dvDesLabel+"]]", "[["+dvDesLabel+"]]"+precisionLabelList.get(j));
											dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[AbsrealTotal]]");	
											dataVAlues = dataVAlues.replace("[[AbsrealTotal]]", "[[AbsrealTotal]]"+precisionLabelList.get(j));
										} else {
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_STACKED_VALUE,"[[value]]");
											dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
										}
										
										countmm = 0;
										if(isMultiMeasure)
										{
											for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
												//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
													++countmm;
													String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
													dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));														
												//}
											}
										}
									}
									else if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
									{
										//dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_PERCENTAGE_VALUE,"[[percentageValues" + j + "]]");
										dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_PERCENTAGE_VALUE, "[[percents]]"+"%");
										//dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_PERCENTAGE_VALUE, "[[realPercentageValues" + j + "]]"+"%");
										//dataVAlues = dataVAlues.replace("[[percentageValues" + j + "]]", "[[percentageValues" + j + "]]"+"%");
										/**
										 * 12/16/2d016 12:00 PM added by krishna for showing actual value and stacked value for percentage graph
										 */
										// Added AutoValue-based data value handling for percentage graph values.
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[AbsyaxisValue"+j+"]]"+precisionLabelList.get(j));
											dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[AbsrealTotal]]"+precisionLabelList.get(j));
										} else {
											dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[value]]");
											dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
										}
										
										countmm =0;										
										if(isMultiMeasure) {
											for(int m=0; m<multipleMeasures;m++) {
													//if(!(graphInfo.getDataColLabels3().get(m).equals(originalDataColList.get(j))))	
												//{
													++countmm;
													//dataVAlues = StringUtil.replace(dataVAlues, "$Y-AXIS_STACKED_PERCENTAGE_VALUE"+countmm+"$", "[[percents]]"+"%");
													dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[AbsyaxisValue"+m+"]]"+precisionLabelList.get(j));
												//}											
											}
										}
									}
									else
									{
										// Added AutoValue-based data value handling for regular (non-stacked) graph values.
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[["+dvDesLabel+"]]");
											dataVAlues = dataVAlues.replace("[["+dvDesLabel+"]]", "[["+dvDesLabel+"]]"+precisionLabelList.get(j));
										} else {
											dataVAlues = StringUtil.replace(dataVAlues,GraphCommandNameList.Y_AXIS_VALUE,"[[value]]");
										}
										//dataVAlues = StringUtil.replace(dataVAlues, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[AbsrealTotal]]");
										
										countmm = 0;
										if(isMultiMeasure)
										{
											for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
												//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
													++countmm;
													String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
													if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
														dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
													} else {
														dataVAlues = StringUtil.replace(dataVAlues,"$Y-AXIS_VALUE"+countmm+"$","[[value]]");
													}
												//}
											}
										}
									}


									graphs.setLabelPosition(labelPosition);
									graphs.setLabelText(dataVAlues);//values on bar
									graphs.setColor(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getFontColor());//values on bar color
									graphs.setFontSize(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getFontSize());
									graphs.setShowAllValueLabels(true);

								}
								//Data value end

								//Mouse over value start
								if(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isMouseOverTextEnable())
								{
									String mouseOverString = graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().getDataValueMouseOverFormatText();
									if(mouseOverString!=null){
									mouseOverString=mouseOverString.replace("&lt;/br&gt", "");
									mouseOverString=mouseOverString.replace("&lt;br&gt", "");
									}
									
									mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_VALUE,"[[truncatedLabel]]");
									if(isLegendVisible)
									{
										if(rowList != null && !rowList.isEmpty())
										{
											String formattedRowListValue = rowList.get(j).toString();
											//Added code for Bug #13406 start
											if(!dateRowList.isEmpty() && dateRowList.size() > j
													&& null != dateRowList.get(j) && !dateRowList.get(j).equals(AppConstants.NULL_DISPLAY_VALUE)) {
												//String tmp2 = dateRowList.get(index).toString();
												String stringFormat;
												stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
												stringFormat = stringFormat.replaceAll("&#39;", "'");
												Calendar cal = Calendar.getInstance();
												Date axisDate = new Date();
												axisDate = (Date) dateRowList.get(j);
												cal.setTime(axisDate);
												stringFormat=stringFormat.trim();
												formattedRowListValue = new SimpleDateFormat(stringFormat).format(cal.getTime());
											}//Added code for Bug #13406 end
											
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,formattedRowListValue);
											/*if(isLegendVisible && multipleMeasures > 1 && colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= rowListSize)
												mouseOverString = mouseOverString.replaceAll(rowList.get(j).toString(),"[[zaxisvalue"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");*/
											
											if(dataColList != null && !dataColList.isEmpty())
											{
												if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
												{
												if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
													mouseOverString = mouseOverString.replace(formattedRowListValue,"[[zaxisvalue"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
												else
													mouseOverString = mouseOverString.replace(formattedRowListValue,"[[zaxisvalue"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
												}
											}
										}
										else
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_VALUE,"[[title]]");
									}
									
									//Y_AXIS_TITLE start
									if(dataColList != null && !dataColList.isEmpty())
									{
										if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[yaxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
										else
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"[[yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*","")+"]]");
									}
									else
									{
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_TITLE,"");
									}
									
									countmm = 0;
									if(isMultiMeasure)
									{ 
										
										for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
											//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
												++countmm;												
												mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_TITLE"+countmm+"$",graphInfo.getDataColLabels3().get(m).toString());														
											//}
										}
									}
									//Y_AXIS_TITLE end
									
									//X_AXIS_TITLE start
									if(colLabel != null && !colLabel.equalsIgnoreCase("null"))
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"[[xaxisTitle]]");
									else
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.X_AXIS_TITLE,"");
									//X_AXIS_TITLE end
									
									//Z_AXIS_TITLE start
									if(isLegendVisible)
									{
										if(!(rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
										{/*
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
										}
										else
										{*/
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,StringUtil.replaceSpecialCharWithHTMLEntity(rowLabel));
										}
									}
								/*	else
									{
										mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Z_AXIS_TITLE,"");
									}*/
									//Z_AXIS_TITLE end

									if(graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH ||
											graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH)
										//&& (colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))))
									{
										// Added AutoValue-based tooltip handling for stacked graph values.
										// When AutoValue is enabled, use rendered graph value ([[value]])
										// instead of manual Abs value placeholders to avoid tooltip mismatch
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_STACKED_VALUE,"[["+desLabel+"]]");
											mouseOverString = mouseOverString.replace("[["+desLabel+"]]", "[["+desLabel+"]]"+precisionLabelList.get(j));
											mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[AbsrealTotal]]");	
											mouseOverString = mouseOverString.replace("[[AbsrealTotal]]", "[[AbsrealTotal]]"+precisionLabelList.get(j));
										} else {
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_STACKED_VALUE,"[[value]]");
											mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
										}
										
										
										countmm = 0;
										if(isMultiMeasure)
										{
											for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
												//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
													++countmm;
													String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
													// Added AutoValue support for stacked tooltip placeholder replacement.
													if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
														mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
													} else {
														mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[value]]");
													}
												//}
											}
										}
									}
									else if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
									{
										//mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_PERCENTAGE_VALUE,"[[percentageValues" + j + "]]");
										mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_PERCENTAGE_VALUE, "[[percents]]"+"%");
										//mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_PERCENTAGE_VALUE, "[[realPercentageValues" + j + "]]"+"%");
										//mouseOverString = mouseOverString.replace("[[percentageValues" + j + "]]", "[[percentageValues" + j + "]]"+"%");
										/**
										 * 12/16/2d016 12:00 PM added by krishna for showing actual value and stacked value for percentage graph
										 */
										// Added AutoValue-based tooltip handling for percentage graph values.
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[AbsyaxisValue"+j+"]]"+precisionLabelList.get(j));
											mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[AbsrealTotal]]"+precisionLabelList.get(j));
										} else {
											mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[value]]");
											mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_VALUE, "[[value]]");
										}
										
										countmm =0;										
										if(isMultiMeasure) {
										for(int m=0; m<multipleMeasures;m++)
										{
												//if(!(graphInfo.getDataColLabels3().get(m).equals(originalDataColList.get(j))))	
												//{
													++countmm;
													//mouseOverString = StringUtil.replace(mouseOverString, "$Y-AXIS_STACKED_PERCENTAGE_VALUE"+countmm+"$", "[[percents]]"+"%");
													if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
														mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[AbsyaxisValue"+m+"]]"+precisionLabelList.get(j));
													} else {
														mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_STACKED_VALUE"+countmm+"$","[[value]]");
													}
												//}
											}
										}
									}
									else
									{
										// Added AutoValue-based tooltip handling for regular (non-stacked) graph values.
										if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[["+desLabel+"]]");
											mouseOverString = mouseOverString.replace("[["+desLabel+"]]", "[["+desLabel+"]]"+precisionLabelList.get(j));
										} else {
											mouseOverString = StringUtil.replace(mouseOverString,GraphCommandNameList.Y_AXIS_VALUE,"[[value]]");
										}
										//mouseOverString = StringUtil.replace(mouseOverString, GraphCommandNameList.Y_AXIS_STACKED_VALUE, "[[AbsrealTotal]]"+precisionLabelList.get(j));
										
										countmm = 0;
										if(isMultiMeasure)
										{
											for(int m=0; m<graphInfo.getDataColLabels3().size();m++)	{					
												//if(!(graphInfo.getDataColLabels3().get(m).equals(graphInfo.getDataColLabels3().get(j)))) {
													++countmm;
													String desLabel1 = m+graphInfo.getDataColLabels3().get(m).toString().replaceAll("[^\\s\\w]*", "");
													if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().isAutoValue()== false) {
														mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_VALUE"+countmm+"$","[[Abs"+desLabel1+"]]"+precisionLabelList.get(j));
													} else {
														mouseOverString = StringUtil.replace(mouseOverString,"$Y-AXIS_VALUE"+countmm+"$","[[value]]");
													}
												//}
											}
										}
									}
									graphs.setBalloonText(mouseOverString);
								}
								else
								{
									graphs.setBalloonText("");
								}
								if(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isClusteredMouseOverTextEnable())
								{

									graphs.setBalloonText("[["+desLabel+"]]");


								}
								if(!graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isClusteredMouseOverTextEnable() && !graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isMouseOverTextEnable())
								{
									graphs.setBalloonText("");
								}

								//Mouse over value end

								//Area graph bullet on mouse over start
								if(graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH
										||	graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
								{	 
									graphs.setBullet("round");
								}
								//Area graph bullet on mouse over end

								if(graphInfo.getGraphProperties().getGraphLineProperties().getType() != 3)//Added for Bug #12669
								graphs.setFillColorsField("color");
								graphs.setType(type);
								graphs.setFillAlphas(fillAlpha);
								graphs.setLineAlpha(lineAlpha);
								graphs.setLineThickness(lineThicknessList.get(j));
								graphs.setBulletAlpha(bulletAlpha);
								//Bar Graph Transparency start 
								if(graphInfo.getGraphProperties().getTranceperancy()>0)
								{	 
									double transperency =graphInfo.getGraphProperties().getTranceperancy();
									double barTransparency = ((100-transperency)/100); 
									graphs.setFillAlphas(barTransparency);
								}
								//Bar Graph Transparency end

								List<String> fillColorsList = new ArrayList<String>();
								fillColorsList.add(barColor[graphInfo.getColorInfoList().get(j)%colorLength]);
								
								//Bug: 14488[Adv sort applied hence colorInfoList was comming something like 1,0,3....]
								//but as custom color is applied and no dim in ROW,first custom color is expected
								//Reflecting code of 14488 in BarLineArea for Bug #15207 
								if(/*colorType == 1 && Commented for Bug #15407 */rowLabel == null && graphInfo.getDataColLabels3().size() == 1 && rowListSize == 1)
								{
									fillColorsList.clear();
									fillColorsList.add(barColor[0]);
								}

								if(isLegendVisible){
									
									String tmp = rowList.get(j).toString(); 
									//Added code for Bug #13406 start
									if(!dateRowList.isEmpty() && dateRowList.size() > j
											&& null != dateRowList.get(j) && !dateRowList.get(j).equals(AppConstants.NULL_DISPLAY_VALUE)) {
										String stringFormat;
										stringFormat = graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getTimeFormat();
										stringFormat = stringFormat.replaceAll("&#39;", "'");
										Calendar cal = Calendar.getInstance();
										Date axisDate = new Date();
										axisDate = (Date) dateRowList.get(j);
										cal.setTime(axisDate);
										stringFormat=stringFormat.trim();
										tmp = new SimpleDateFormat(stringFormat).format(cal.getTime());
									}//Added code for Bug #13406 end
									
									switch(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCharacterLimit())
									{
									case "auto":
										//tmp = rowList.get(j).toString();
										int truncateCharLimitAuto = 15;
										if (tmp.length() > truncateCharLimitAuto)
											tmp = tmp.substring(0, truncateCharLimitAuto)+"..";
										break;
									case "custom":
										//tmp = rowList.get(j).toString();
										int truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getLegendProperties().getLegendValuesProperties().getLegendValuesFontProperties().getCustomCharacterLimit());
										if (tmp.length() > truncateCharLimit)
											tmp = tmp.substring(0, truncateCharLimit)+"..";
										break;
									/*default:
										tmp = rowList.get(j).toString();
										if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= rowListSize)
											tmp = graphInfo.getGraphData().getColLabelsName().get(j).toString();
										break;*/

									}
									if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() >= rowListSize
										&& graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("legend"))
											tmp = graphInfo.getGraphData().getColLabelsName().get(j).toString();
									if(tmp != null)
										tmp = Parser.unescapeEntities(tmp, false);
									graphs.setTitle(tmp);

									graphs.setValueField(rowList.get(j).toString());//.replaceAll("[^\\s\\w]*",""));
									graphs.setDescriptionField("Abs"+rowList.get(j).toString());

								}else{
									graphs.setValueField(graphInfo.getGraphData().getDataLabel());//.replaceAll("[^\\s\\w]*",""));
									graphs.setDescriptionField("Abs"+graphInfo.getGraphData().getDataLabel());
								}

								if(graphInfo.getGraphType() == GraphConstants.LINE_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.STACKED_LINE_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH)
								{	 
									graphs.setBullet(bulletTypeArray[j%bulletTypeArray.length]);
									if(null == rowLabel)//Added IF to set proper color when 1D and Sort is applied (Ritu bug)
										graphs.setBulletColor(bulletColor[0]);
									else
										graphs.setBulletColor(bulletColor[graphInfo.getColorInfoList().get(j)%bulletColor.length]);
									graphs.setBulletSize(bulletSizeList.get(j));
									graphs.setDashLength(lineStyleList.get(j));
									graphs.setLineThickness(lineThicknessList.get(j));
									//graphs.setBulletBorderAlpha(bulletBorderAlpha);
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
										graphs.setBulletBorderAlpha(bulletBorderAlpha);//(1);
									}
									if(null == rowLabel)//Added IF to set proper color when 1D and Sort is applied (Ritu bug)
										graphs.setLineColor(barColor[0]);//Added for only Line Colors
									else
										graphs.setLineColor(barColor[graphInfo.getColorInfoList().get(j)%barColor.length]);//Added for only Line Colors
								}
								if(graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH
										||	graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
								{	 
									graphs.setBullet("round");
									graphs.setBulletAlpha(0);
									graphs.setBulletColor(bulletColor[graphInfo.getColorInfoList().get(j)%bulletColor.length]);
									if(null == rowLabel)//Added for Bug #15407
									{
										graphs.setLineColor(barColor[0]);//Added for only Area Colors
									}

									// area gradient start
									if (graphInfo.getGraphProperties().getGraphArea().getGradient().isVisible())
									{
										fillColorsList.add(graphInfo.getGraphProperties().getGraphArea().getGradient().getColor());
										graphs.setFillColorsField("");
										if(graphInfo.getGraphProperties().getGraphArea().getGradient().isTransparent()){
											graphs.setFillAlphas(0.50);
										}
									}
									// area gradient end
									
									//Area border start
									if(graphInfo.getGraphProperties().getGraphArea().getBorderProperties().isVisible()
											&& !graphInfo.getGraphProperties().getGraphArea().getBorderProperties().getAllBorderStyle().equalsIgnoreCase("none"))
									{
										graphs.setLineAlpha(1);
										graphs.setLineThickness(graphInfo.getGraphProperties().getGraphArea().getBorderProperties().getAllBorderWidth());
										graphs.setLineColor(graphInfo.getGraphProperties().getGraphArea().getBorderProperties().getAllBorderColor());
										int areaBorderStyle = 0;
										switch (graphInfo.getGraphProperties().getGraphArea().getBorderProperties().getAllBorderStyle()) {
										case "none":
											graphs.setLineAlpha(0);
											graphs.setLineThickness(0);
											break;
										case "solid":
											areaBorderStyle = 0;
											break;
										case "dashed":
											areaBorderStyle = 5;
											break;
										case "dotted":
											areaBorderStyle = 2;
											break;
										}
										graphs.setDashLength(areaBorderStyle);
									}
									else
									{
										graphs.setLineAlpha(0);
										graphs.setLineThickness(0);
										//graphs.setLineColor();
										//graphs.setDashLength();
									}
									//Area border end
								}
								if(graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH)
								{	 
									if(graphInfo.getGraphProperties().getBarProperties().getType() == 2)
									{
										//graphJson.setAngle(30);
										//graphJson.setDepth3D(30);
										graphs.setTopRadius("1");
									}
									else if(graphInfo.getGraphProperties().getBarProperties().getType() == 3)
									{
										//	graphJson.setAngle(30);
										//	graphJson.setDepth3D(30);
										graphs.setTopRadius("0");
									}

									//Bar Gradient Start.
									if (graphInfo.getGraphProperties().getBarProperties().getGradient().isVisible())
									{
										fillColorsList.add(graphInfo.getGraphProperties().getBarProperties().getGradient().getColor());
										graphs.setFillColorsField("");
										if(graphInfo.getGraphProperties().getBarProperties().getGradient().isTransparent()){
											graphs.setFillAlphas(0.50);
										}
									}
									//Bar Gradient End
									//Corner radius
									if(graphInfo.getGraphProperties().getBarProperties().getCornerRadius() > 0)
									{
										graphs.setCornerRadiusTop(graphInfo.getGraphProperties().getBarProperties().getCornerRadius());
										//graphJson.setAngle(0);
										//graphJson.setDepth3D(0);
									}
									
									//Bar width
									if(graphInfo.getGraphProperties().getBarProperties().getBarWidth() != 100 && isBarChart)
									{
										double barWidth = (double)graphInfo.getGraphProperties().getBarProperties().getBarWidth()/100;
										graphs.setColumnWidth(barWidth);//between 0-1
									}
									//Bar width
									
									//Bar border start
									if(graphInfo.getGraphProperties().getBarProperties().getBorderProperties().isVisible()
											&& !graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderStyle().equalsIgnoreCase("none"))
									{
										graphs.setLineAlpha(1);
										graphs.setLineThickness(graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderWidth());
										graphs.setLineColor(graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderColor());
										int barBorderStyle = 0;
										switch (graphInfo.getGraphProperties().getBarProperties().getBorderProperties().getAllBorderStyle()) {
										case "none":
											graphs.setLineAlpha(0);
											graphs.setLineThickness(0);
											break;
										case "solid":
											barBorderStyle = 0;
											break;
										case "dashed":
											barBorderStyle = 5;
											break;
										case "dotted":
											barBorderStyle = 2;
											break;
										}
										graphs.setDashLength(barBorderStyle);
									}
									else
									{
										graphs.setLineAlpha(0);
										graphs.setLineThickness(0);
										//graphs.setLineColor();
										//graphs.setDashLength();
									}
									//Bar border end
								}
								String legendColor=barColor[graphInfo.getColorInfoList().get(j)%barColor.length];
								switch(graphInfo.getGraphProperties().getGraphLineProperties().getType())
								{
								case 2:legendColor = bulletColor[graphInfo.getColorInfoList().get(j)%bulletColor.length];break;
								}
								graphs.setLegendColor(legendColor);//Legend Icon(Marker) color
								graphs.setFillColors(fillColorsList);
								graphs.setLabelFunction("");
								graphsList.add(graphs);
								//startIndex++;
								colr++;
							}
						}
				
				//if(rowListSize < Quantity)
					if(isTrend)//trend
					{
						isTrend=false;
						String label;
						for(int c=0;c<noOfTrendLines;c++)
						{
							Graphs graphs = new Graphs();
							graphs.setShowHandOnHover(true);////feature req 13494
							graphs.setLineThickness((int)trendLineThickness.get(c));
							graphs.setLineAlpha(1);
							graphs.setType("line");
							graphs.setLabelText("[[TrendLabel" + c+ "]]" );
							graphs.setFillAlphas(0);
							graphs.setValueField(trendLineName.get(c).toString()+"trend");
							graphs.setDescriptionField("Abs"+trendLineName.get(c).toString()+"trend");
							graphs.setLineColor(trendColor.get(c).toString());
							graphs.setVisibleInLegend(false);
							graphs.setValueAxis("valueAxes1");
							
							//for Bug 11562
							if(graphInfo.getDataColLabels3().size() > 1)
							{
								String[] splitString = trendLineColoumn.get(c).toString().split(",");
								List measure = new ArrayList(graphInfo.getDataColLabels3());
								if(measure.contains(splitString[0]))
								{
									int occurenceIndex= measure.indexOf(splitString[0]);
									graphs.setValueAxis("valueAxes"+occurenceIndex);
								}
							}
								
							
							//line style
							int lineStyle = Integer.valueOf(trendLineStyle.get(c).toString());
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
							/*case 3:
								trendDashLength = 1;
								break;*/
							}
							graphs.setDashLength(trendDashLength);//year quater
							graphs.setFontSize(10);
							if(!graphInfo.getGraphData().getColLabel().equalsIgnoreCase(trendValue.get(c).toString()) && isLegendVisible==false)
							{
								graphs.setLineAlpha(0);
								graphs.setLineThickness(0);
								graphs.setLabelText("");
							}
							graphsList.add(graphs);
						}
			}
		}
		
		
		//------------------------------------------- Graphs End (barGraphs end)--------------------------------------------------
		graphJson.setCategoryField(colLabel);
		
		/*List<Graphs> graphsList = new ArrayList<Graphs>();
		graphJson.setGraphs(graphsList);*/
		graphJson.setGraphs(graphsList);
		
		
		//-------------------------------------------- Data Provider Start(barGraphDataProvider)-------------------------------------
		noOfMeasure = 1;
		colList = graphInfo.getGraphData().getColList();
		colListSize = colList.size();
		List colList2 = new ArrayList();
		colList2.addAll(colList);
		List dvTruncatedColList = new ArrayList();//Added for NeGD feature request 15092 [13 Aug 2019]
		dvTruncatedColList.addAll(colList);
		List truncatedColList = new ArrayList();
		truncatedColList.addAll(colList);
		String truncatedLabel = "truncatedLabel";
		String dvTruncatedLabel = "dvTruncatedLabel";
		
		//Added for NeGD feature request 15092 start [8 Aug 2019]
		if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
			|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
			|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
		{
			for (int i = 0; i < dvTruncatedColList.size(); i++) {
				truncatedLabelString = dvTruncatedColList.get(i).toString();
				switch(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCharacterLimit())
				{
				case "auto":
					dvTruncateCharLimit = 15;
					break;
				case "custom":
					dvTruncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCustomCharacterLimit());
					break;
				}
				if (truncatedLabelString.length() > dvTruncateCharLimit) {
					truncatedLabelString = truncatedLabelString.substring(0, dvTruncateCharLimit)+"..";
				}
				dvTruncatedColList.set(i, truncatedLabelString);
			}
		}
		//Added for NeGD feature request end [8 Aug 2019]
		
		rowListSize = rowList.size();
		
		if(rowListSize == 0){
			rowListSize = 1;
			isLegendVisible =  false;
		}

		int truncateCharLimit = 15;
		boolean isCharacterLimitNone = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit().equalsIgnoreCase("none");
		if(colList != null && !graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getFontProperties().getCharacterLimit().equalsIgnoreCase("none"))
		{
			String array_element;
			for (int i = 0; i < colList.size(); i++) {
				array_element = colList.get(i).toString();

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
				
				colList2.set(i, array_element);
			}
		}
		
		if(isLegendVisible && multipleMeasures > 1
				&& (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH))
		{
			isMultipleMeasure = true;
			if(graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("legend"))
				noOfMeasure = 1;
			else	
			noOfMeasure = graphInfo.getDataColLabels3().size();//Multiple Measures
			//noOfMeasure = graphInfo.getDataColLabels3().size();//Multiple Measures
			//rowListSize*=noOfMeasure;
			for (int d=0;d < graphInfo.getDataColLabels3().size();d++)//Fetching dataLabels for Multiple Measure	//change size
			{
				dataLabelList.add(d, graphInfo.getDataColLabels3().get(d).toString());
			}
			noOfYAxis = noOfMeasure;
		}
		if((graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH) && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
		{
			noOfYAxis = rowListSize;
		}
		String drillAxis = "drillAxis";
		String drillLegend = "drillLegend";
		List drillList = graphInfo.getGraphData().getDrillLinkList();
		int nullSize = colListSize*rowListSize*noOfMeasure;
		
		if(drillList.isEmpty() || nullSize > graphInfo.getGraphData().getDrillLinkList().size())
		{
			for(int i=0;i<nullSize;i++)
			{
				drillList.add("null");
			}
		}
			
		boolean isMultipleValueAxis = false;
		boolean[] flag = new boolean[multipleMeasures];
		double[] customMax = new double[multipleMeasures];
		dataLabel = graphInfo.getGraphData().getDataLabel();
		//List percentageValueList= graphInfo.getGraphData().getPercentageValueList();
		//List realPercentageValueList=graphInfo.getGraphData().getRealPercentageValueList();
		
		//dataList = graphInfo.getGraphData().getDataList();
		
		//Performance Changes
		Map keyValueMap = graphInfo.getGraphData().getKeyValueMap();
		
		List totalList = graphInfo.getGraphData().getTotalValueList();
		
		Map stackedvalueMap =  graphInfo.getGraphData().getStackedTotalValues();
		
		Map  stackedDataTotalValues = graphInfo.getGraphData().getStackedDataTotalValues();
		
		//int dataListSize = dataList.size();
		
		flag = new boolean[rowListSize];
		customMax = new double[rowListSize];
		
		int customMaxRowListSize = rowListSize;
		int customMaxNumberofMeasure = 1;
		
		for(int i=0;i<customMaxRowListSize;i++)
		{
		
		if(graphInfo.getGraphData().getRowLabel() != null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("legend"))
		{			
			customMaxNumberofMeasure = multipleMeasures;
			customMaxRowListSize = 1;
		}
		
			for(int j=0;j<customMaxNumberofMeasure;j++)
			{
				if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().getMaxValType() == 1)
				{
					flag[i]=true;
					customMax[i] =Double.parseDouble(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+j).getLabelProperties().getMaxCustomVal());
				}
			}
		}
		colLabelsName = false;
		//This is when non clustered and multiple measure along with col labels
		if(graphInfo.getGraphData().getColLabelsName() != null && graphInfo.getGraphData().getColLabelsName().size()>0)
			colLabelsName = true;
		dataColList = new ArrayList();
		precisionLabelCounter=1;
		if(graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphData().getRowLabel()!=null && graphInfo.getGraphData().getRowLabel().equalsIgnoreCase("Legend")))
		{
			precisionLabelCounter = graphInfo.getDataColLabels3().size();
			for(int i=0;i<precisionLabelCounter;i++)
			{	
				if(colLabelsName && graphInfo.getGraphData().getColLabelsName().size() > i && graphInfo.getGraphData().getColLabelsName().get(i) != null)
					dataColList.add(graphInfo.getGraphData().getColLabelsName().get(i).toString());
				else
					dataColList.add(graphInfo.getDataColLabels3().get(i).toString());
			}
		}
		else
		{
			for (int i = 0; i < rowListSize; i++) {
				if(colLabelsName)
				{
					if(graphInfo.getGraphData().getColLabelsName().size() < rowListSize)
						dataColList.add(graphInfo.getGraphData().getColLabelsName().get(0).toString());
					else
						dataColList.add(graphInfo.getGraphData().getColLabelsName().get(i).toString());
				}
					
				else
				{
					if(dataLabel != null)
						dataColList.add(dataLabel);
				}
			}
		}
		
		/*List<String> originalDataColList = new ArrayList<String>();
		if(!dataColList.isEmpty()) {
			Map<String, String> colLabelsMap = graphInfo.getGraphProperties().getColLabelsMap();
			for (int i = 0; i < dataColList.size(); i++) {
				for (Entry<String, String> e : colLabelsMap.entrySet()) {
					if(dataColList.get(i).equals(e.getValue()))
						originalDataColList.add(e.getKey());
				}
			}
		}
		if(originalDataColList.isEmpty())
			originalDataColList.addAll(dataColList);*/
		
		switch(graphInfo.getGraphProperties().getColorType())
		{
		case 1:
			if(graphInfo.getGraphProperties().getCustomColors() != null)
			{
				for (int i = 0; i < graphInfo.getGraphProperties().getCustomColors().size(); i++) {
					if(i > (barColor.length-1))// || i > (bulletColor.length-1))
					{
						barColor = appendValue(barColor, graphInfo.getGraphProperties().getCustomColors().get(i));
						//Commented unwanted code for Bug #15417 bulletColor = appendValue(bulletColor, graphInfo.getGraphProperties().getCustomColors().get(i));
					}
					else
					{	
						barColor[i] = graphInfo.getGraphProperties().getCustomColors().get(i);
						//Commented unwanted code for Bug #15417 bulletColor[i] = graphInfo.getGraphProperties().getCustomColors().get(i);
					}
				}
			}
			break;
		case 2:
			barColor = new String[]{graphInfo.getGraphProperties().getColor()};
			//Commented unwanted code for Bug #15417 bulletColor = new String[]{graphInfo.getGraphProperties().getColor()};
			break;
		}
		
		/*if(categoryIndex > colListSize)
			categoryIndex=colListSize;
		if(startIndex > rowListSize)
			startIndex=rowListSize;*/
		
		boolean legendflag=false;
	/*	int startIndexcolne=startIndex;	
		int paginationIndex=startIndex+Quantity;
		int categorypaginationIndex=categoryIndex+categoryQuantity;
		if(categorypaginationIndex > colListSize)
			categorypaginationIndex=colListSize+1;
		if(paginationIndex >= rowListSize)
		{
			legendflag=true;
			paginationIndex=rowListSize;
		}*/	
		k=0;
		int drillIndex=0;
		List<Map<String, Object>> dpList =  new ArrayList<Map<String,Object>>();

		//Trend Line Start
		trendMAp = graphInfo.getGraphData().getTrendMap();
		isTrend =false;
		trendCount = 0;

		noOfTrendLines = 0;
		if(trendMAp != null)
			noOfTrendLines = trendMAp.size(); 
		trendColor = new ArrayList();
		trendLineName = new ArrayList();
		List trendValues = new ArrayList();
		trendLineColoumn = new ArrayList();
		List trendAlgoList = new ArrayList();
		if(noOfTrendLines > 0)
		{
			isTrend=true;
			trendCount = noOfTrendLines;

			Map<Integer, TrendLineProperties> testMap = graphInfo.getGraphProperties().getTrendlinePropertiesMap();
			for (Entry<Integer, TrendLineProperties> entry : testMap.entrySet()) {
				trendColor.add(entry.getValue().getTrendLineColor());
				trendLineName.add(entry.getValue().getTrendLineName());//Name of the trend Line given by the user
				trendLineColoumn.add(entry.getValue().getTrendLineColumn());
				trendAlgoList.add(entry.getValue().getTrendLineType().toString());
			}
		}
		//trend Line End
		//int index = 0,even=0;
		//int cnt = -1;
		int counts=0;
		String yAxisTitle = "yAxisTitle";
		String percentageValues = "percentageValues";
		//String realPercentageValues = "realPercentageValues";
		//Performance Changes
		List adjustedDigitList=new ArrayList();
		List autoValueList=new ArrayList(); // Track which measures have AutoValue enabled
		
		if((graphInfo.getDataColLabels3().size() > 1 && (graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH) && (graphInfo.getGraphData().getRowLabel() != null &&  graphInfo.getGraphData().getColLabel() != null))
				|| (graphInfo.getGraphType() == GraphConstants.VBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH || graphInfo.getGraphType() == GraphConstants.LINE_GRAPH || graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH)
				&& ((graphInfo.getGraphData().getRowLabel() == null || (graphInfo.getGraphData().getRowLabel() != null && "Legend".equals(graphInfo.getGraphData().getRowLabel()))) ||  graphInfo.getGraphData().getColLabel() == null) && graphInfo.getDataColLabels3().size()>1)                    // Bug #14959 changes by [p.p]
		{
			if(graphInfo.getDataColLabels3().size() > 1)
			{
				for(int i=0;i<graphInfo.getGraphProperties().getyAxisPropertiesMap().size();i++)
				{
					if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isVisible()) {
						adjustedDigitList.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().getAdjustedDigit());
						autoValueList.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+i).getLabelProperties().isAutoValue());
					} else {
						adjustedDigitList.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit());
						autoValueList.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().isAutoValue());
					}
				}
			}
		}
		else
		{
			int adjustedDigit = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().getAdjustedDigit();
			adjustedDigitList.add(adjustedDigit);
			autoValueList.add(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+0).getLabelProperties().isAutoValue());
		}
		int divValue;
		List multiDivValueList = new ArrayList();
		for(int z=0;z<adjustedDigitList.size();z++)
		{
			// When AutoValue is enabled, don't divide values - AMCharts will auto-format them (K, M, B)
			// When AutoValue is disabled, divide values by the adjusted digit power and add manual suffix
			if(autoValueList.size() > z && (Boolean)autoValueList.get(z) == true) {
				divValue = 1; // No division when AutoValue is enabled
			} else {
				divValue = (int)(Math.pow(10, Double.valueOf(adjustedDigitList.get(z).toString())));
			}
			multiDivValueList.add(divValue);
		}
		
		double stackTotal = 0.0;
		
		String colLabelNew = "";
		String dvColLabelNew = "";
		String mapKeyStr = "";
		boolean nonSatckedMultipleMeasure = graphInfo.getDataColLabels3().size() > 1 && rowLabel != null && rowLabel.equalsIgnoreCase("Legend");
		Double dataValue = 0.0;
		
		for (int i = 0; i < colListSize; i++) {
			Map<String, Object> dpMap =  new HashMap<String, Object>();
			stackTotal = 0.0;
			if(i+1==colListSize)
				dpMap.put("colLastPage", "1");
			/*if(paginationIndex==rowListSize)
			{
				dpMap.put("rowLastPage", 1);
				startIndex=startIndexcolne;
			}*/
			Map<String, String> drillMap =null;
			/*if(i==categoryIndex)
			{*/
			if( (!graphInfo.getGraphData().getDaterowList().isEmpty() && (null == rowLabel || (null != rowLabel && "Legend".equals(rowLabel))))
                    || !graphInfo.getGraphData().getDatecolList().isEmpty() )//Added for Bug #13406 start
            { 
					String stringFormat;
					stringFormat = graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getDateFormat() + " " +graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().getTimeFormat();
					stringFormat = stringFormat.replaceAll("&#39;", "'");
					Calendar cal = Calendar.getInstance();
					
					Date axisDate = null;
					if(!dateColList.isEmpty())
					{
						if(dateColList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
							colLabelNew = dateColList.get(i).toString();				 
						else
							axisDate = (Date) dateColList.get(i);
					}
					else
					{
						if(!dateRowList.isEmpty())
						{
						 if(dateRowList.get(i).equals(AppConstants.NULL_DISPLAY_VALUE))
							colLabelNew = dateRowList.get(i).toString();				 
						else
							axisDate = (Date) dateRowList.get(i);
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
					dpMap.put(colLabel, colLabelNew);
					
					//Added for NeGD feature request 15092 start [8 Aug 2019]
					dvColLabelNew = colLabelNew;
                    if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
        				|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
        				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
            		{
        				switch(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCharacterLimit())
        				{
        				case "auto":
        					truncateCharLimit = 15;
        					break;
        				case "custom":
        					truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCustomCharacterLimit());
        					break;
        				}
        				if (dvColLabelNew.length() > truncateCharLimit) {
        					dvColLabelNew = dvColLabelNew.substring(0, truncateCharLimit)+"..";
        				}
            		}
					dpMap.put(dvTruncatedLabel, dvColLabelNew);
					//Added for NeGD feature request end [8 Aug 2019]
					
				}//Added for Bug #13406 end
				else if (graphInfo.getDateFrequencyMap() != null && !graphInfo.getDateFrequencyMap().isEmpty()  ) {
					
					GraphsUtil.getDateFormat(graphInfo,dpMap,i);
				}
				else {
					dpMap.put(colLabel, colList2.get(i).toString());
					dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
					dpMap.put(dvTruncatedLabel, dvTruncatedColList.get(i).toString());
				}
				if(isLegendVisible && isMultipleMeasure)
				{
					for(int measureCnt = 0; measureCnt < multipleMeasures; measureCnt++)
					{
						if(graphInfo.getDataColLabels3().get(measureCnt) != null && !graphInfo.getDataColLabels3().isEmpty())
							dpMap.put("yAxisTitle"+measureCnt, unescapeHtml(graphInfo.getDataColLabels3().get(measureCnt).toString()));
					}
				}
				else
				{
					dpMap.put("yAxisTitle", StringUtil.replaceSpecialCharWithHTMLEntity(dataLabel));
				}
				if(colLabel != null && !colLabel.equalsIgnoreCase("null"))
					dpMap.put("xaxisTitle", StringUtil.replaceSpecialCharWithHTMLEntity(colLabel));
				drillIndex = i;
				if(isLegendVisible)
				{
					drillIndex=rowListSize+i;
					drillMap = new HashMap<String, String>();
				}
				if(graphInfo.getGraphProperties().getxAxisProperties().getLabelProperties().isVisible())
				{
					if(drillList.size() > drillIndex && (!drillList.isEmpty() && drillList.get(drillIndex)!=null) && !drillList.get(drillIndex).equals("null"))
						dpMap.put(drillAxis, drillList.get(drillIndex).toString());
				}
			
			innerloop:
			
				for (int j = 0; j < rowListSize; j++) {
					/*if(j==startIndex)
					{*/
/*						if(startIndex==paginationIndex && !legendflag)
						{

							if(!dpMap.containsKey(colLabel))
							{
								dpMap.put(colLabel, colList2.get(i).toString());
								dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
							}
							if(!dpMap.containsKey(drillAxis))
							{
								if(!drillList.isEmpty() && drillList.size() > drillIndex && drillList.get(drillIndex)!=null && !drillList.get(drillIndex).equals("null")) 
								dpMap.put(drillAxis, drillList.get(drillIndex).toString());

							}
							if(j==rowListSize-1)									
								dpMap.put("rowLastPage", 1);										
							//startIndex=startIndexcolne;
							//dpList.add(dpMap);
							break innerloop;
						}
*/					for(int theIndex = 0; theIndex < noOfMeasure; theIndex++)
					{
					/*if(!dataList.isEmpty())
					{*/
							
						mapKeyStr = "";
						if(null != colList && colList.size() > 0)
							mapKeyStr += colList.get(i).toString();
						if(null != rowList && rowList.size() > 0)
							mapKeyStr += rowList.get(j).toString();
						if(!nonSatckedMultipleMeasure) {
							if(null != rowLabel && !rowLabel.equalsIgnoreCase("Legend") && !originalDataList.isEmpty())
								mapKeyStr += originalDataList.get(theIndex).toString();
							else
								mapKeyStr += graphInfo.getDataColLabels3().get(theIndex).toString();
						}
						
						
						if(keyValueMap.get(mapKeyStr)!=null){		
							
							
								
	
								String label = null;
								String label2 = "";
								String totalValueKey = "";
								if(stackedvalueMap != null && stackedvalueMap.size() > 0)
								{
									totalValueKey = truncatedColList.get(i).toString();
									if(null != rowLabel && !rowLabel.equalsIgnoreCase("Legend") && !originalDataList.isEmpty())
										totalValueKey += originalDataList.get(theIndex).toString();
									else
										totalValueKey += graphInfo.getDataColLabels3().get(theIndex).toString();
									
								}
								if(isLegendVisible){
									
									label = rowList.get(j).toString();
									if(isMultipleMeasure)
									{
										if(graphInfo.getDataColLabels3().size() > theIndex)
											label2 = label + graphInfo.getDataColLabels3().get(theIndex).toString();//Added for Y_axis_Stacked value when multiple measure & stacked bar Bug #12741
										/*if((j%(rowListSize/noOfMeasure))==0)
										{
											index=0;//Fetching appropriate label when Multiple Measure
											cnt++;
										}
										label = rowList.get(index).toString();*/
										if(null != rowLabel && rowLabel.equalsIgnoreCase("Legend") && !originalDataColList.isEmpty())
											label += originalDataColList.get(theIndex).toString();
										else if(null != rowLabel && !rowLabel.equalsIgnoreCase("Legend") && !originalDataList.isEmpty())
											label += originalDataList.get(theIndex).toString();
										else
											label += dataLabelList.get(theIndex).toString();
									}
									//label = label;//.replaceAll("[^\\s\\w]*","");
									if(drillMap==null)
										drillMap = new HashMap<String, String>();
									if(!drillList.isEmpty() && rowList.size() > j && drillList.size() > drillIndex &&  drillList.get(j)!=null && !drillList.get(j).equals("null"))
									{
										/*if(isMultipleMeasure)
											drillMap.put(rowList.get(index).toString(), drillList.get(j).toString());
										else*/
										drillMap.put(rowList.get(j).toString(), drillList.get(j).toString());
									}
									if(dataColList != null && !dataColList.isEmpty())
									{
										if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
										{
											if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
											{	
												dpMap.put("yaxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*",""), StringUtil.replaceSpecialCharWithHTMLEntity(graphInfo.getDataColLabels3().get(j).toString()));
												dpMap.put("zaxisvalue"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*",""), StringUtil.replaceSpecialCharWithHTMLEntity(graphInfo.getDataColLabels3().get(j).toString()));
											}
											else
											{
												dpMap.put("yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(dataColList.get(j).toString()));
												dpMap.put("zaxisvalue"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(dataColList.get(j).toString()));
											}
										}
										else{
											dpMap.put("yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(dataColList.get(j).toString()));
										}
									}
									
									
									//index++;
									if(colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend")) && (totalList != null && totalList.get(theIndex) != null))
									{
										//dpMap.put("realTotal", totalList.get(i));
										stackTotal = stackTotal + (Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(theIndex).toString()));//Double.parseDouble(totalList.get(i).toString());//Double.parseDouble(totalList.get(i).toString());
										String t = commaFormats(stackTotal, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex), graphInfo);
										dpMap.put("AbsrealTotal",t);
										//String t = commaFormats(Double.parseDouble(totalList.get(i).toString()), graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex), graphInfo);
										//String t = commaFormats(totalList.get(i).toString(),graphInfo,theIndex);
										//absRealTotal = absRealTotal + Double.parseDouble(t);
									}
									
									/*else if(totalList != null && totalList.get(theIndex) != null)
									{*/
										/*if((graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH ||
												graphInfo.getGraphType() == GraphConstants.STACKED_VBAR_GRAPH))
										{
											dpMap.put("realTotal", totalList.get(j));//j
											String t = commaFormats(totalList.get(i).toString(),graphInfo,theIndex);
											dpMap.put("AbsrealTotal",t);
										}
										else
										{*/
										
										if(multiDivValueList.size() >= rowListSize && (graphInfo.getGraphType() != GraphConstants.STACKED_VBAR_GRAPH && graphInfo.getGraphType() != GraphConstants.STACKED_HBAR_GRAPH))//Added for bug no 12277
											stackTotal = stackTotal + (Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(j).toString()));
										else	
											stackTotal = stackTotal + (Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(theIndex).toString()));//Double.parseDouble(totalList.get(i).toString())//Double.parseDouble(totalList.get(i).toString());
										String t = commaFormats(stackTotal, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex), graphInfo);
										//absRealTotal = absRealTotal + Double.parseDouble(t);
										dpMap.put("AbsrealTotal",t);
											/*dpMap.put("realTotal", totalList.get(i));
											String t = commaFormats(Double.parseDouble(totalList.get(i).toString()), graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex), graphInfo);
											//String t = commaFormats(totalList.get(i).toString(),graphInfo,theIndex);
											dpMap.put("AbsrealTotal",t);*/
										//}
									//}	
									if(stackedvalueMap != null && stackedvalueMap.size() > 0){
										dpMap.put("realTotal"+theIndex, stackedvalueMap.get(totalValueKey));
										t = commaFormats(Double.parseDouble(stackedvalueMap.get(totalValueKey).toString()), graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex), graphInfo);
										//String t = commaFormats(stackedvalueMap.get(totalValueKey).toString(),graphInfo,theIndex);
										dpMap.put("AbsrealTotal"+theIndex,t);
									}
									else{
										dpMap.put("realTotal", "");
									}
									dpMap.put(drillLegend, drillMap);
								}else{
									label = graphInfo.getGraphData().getDataLabel();
									//label = label;//.replaceAll("[^\\s\\w]*","");
									if(graphInfo.getGraphType() == GraphConstants.AREA_DEPTH_GRAPH
											||	graphInfo.getGraphType() == GraphConstants.AREA_STACK_GRAPH
											|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
									{
										if(null == rowLabel) {//Added for Bug #15407
											dpMap.put("color", barColor[0]);
										} else {
											dpMap.put("color", barColor[graphInfo.getColorInfoList().get(j)%barColor.length]);
										}
									}
									else
									{
										dpMap.put("color", barColor[graphInfo.getColorInfoList().get(i)%barColor.length]);
									}
									
									//This is when one measure and dimension in stacked
									if(graphInfo.getDataColLabels3().size() == 1)
									{
										//Performance Changes
										dataValue = Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(theIndex).toString());
										String t = commaFormats(dataValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex), graphInfo);
										//String t = commaFormats(dataList.get((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex)).toString(), graphInfo,theIndex);
										dpMap.put("AbsrealTotal",t);
									}
								}
								//Changes bar values to customMaxValue if flag is true
	
								if(flag[j])
								{
									/*if((Double)dataList.get((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex)) > customMax[j])
									{
										dataList.set((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex), customMax[j]);
									}*/
								}
								
								/*dpMap.put(label, dataList.get((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex)).toString());*/
								//Performance Changes
								if(multiDivValueList.size() >= rowListSize && (graphInfo.getGraphType() != GraphConstants.STACKED_VBAR_GRAPH && graphInfo.getGraphType() != GraphConstants.STACKED_HBAR_GRAPH))//Added for bug no 12277
									dataValue = Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(j).toString());
								else
									dataValue = Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(theIndex).toString());
								dpMap.put(label, dataValue);
								if(dataColList != null && !dataColList.isEmpty())
								{
									if(rowLabel != null && rowLabel.equalsIgnoreCase("Legend"))
									{
										if(dataColList.get(j) != null && dataColList.get(j).toString().equalsIgnoreCase("data"))
										{	
											dpMap.put("yaxisTitle"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*",""), StringUtil.replaceSpecialCharWithHTMLEntity(graphInfo.getDataColLabels3().get(j).toString()));
											dpMap.put("zaxisvalue"+graphInfo.getDataColLabels3().get(j).toString().replaceAll("[^\\s\\w]*",""), StringUtil.replaceSpecialCharWithHTMLEntity(graphInfo.getDataColLabels3().get(j).toString()));
										}
										else
										{
											dpMap.put("yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(dataColList.get(j).toString().toString()));
											dpMap.put("zaxisvalue"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(dataColList.get(j).toString().toString()));
										}
									}
									else{
										dpMap.put("yaxisTitle"+originalDataColList.get(j).toString().replaceAll("[^\\s\\w]*",""), unescapeHtml(dataColList.get(j).toString().toString()));
									}
								}
								//commaseparator start
								int theIndexProp = theIndex;
								if(graphInfo.getGraphType() != GraphConstants.STACKED_HBAR_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.STACKED_VBAR_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.PERCENTAGE_HBAR_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.PERCENTAGE_VBAR_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.STACKED_LINE_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.PERCENTAGE_LINE_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.AREA_STACK_GRAPH &&
										graphInfo.getGraphType() != GraphConstants.AREA_PERCENTAGE_GRAPH)
								{	
									if(colLabel != null && (rowLabel != null && rowLabel.equalsIgnoreCase("Legend")))
										theIndexProp = j;
								}
								//Performance Chanegs
								if(multiDivValueList.size() >= rowListSize && (graphInfo.getGraphType() != GraphConstants.STACKED_VBAR_GRAPH && graphInfo.getGraphType() != GraphConstants.STACKED_HBAR_GRAPH))//Added for bug no 12277
									dataValue = Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(j).toString());
								else
									dataValue = Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(theIndex).toString());
                                //double parseValue = dataValue;
                                
                                precision = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp).getLabelProperties().getNumberOfDigits();
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
                            //NumberFormat formatter = new DecimalFormat(digitsAfterDecimalString,DecimalFormatSymbols.getInstance(Locale.ENGLISH));    
                            //parseValue = Double.valueOf(formatter.format(parseValue));
                                
                                //String commaSeparated = "";
                                String commaSeparated = commaFormats(dataValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp), graphInfo);
                                String dvCommaSeparated = commaFormats(dataValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp), graphInfo);
                                
                                //Added for NeGD feature request 15092 start [8 Aug 2019]
                                if(graphInfo.getGraphType() == GraphConstants.HBAR_GRAPH 
                    				|| graphInfo.getGraphType() == GraphConstants.STACKED_HBAR_GRAPH
                    				|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH) 
                        		{
	                				switch(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCharacterLimit())
	                				{
	                				case "auto":
	                					truncateCharLimit = 15;
	                					break;
	                				case "custom":
	                					truncateCharLimit = Integer.parseInt(graphInfo.getGraphProperties().getDataValueProperties().getDataValuePoint().getFontProperties().getCustomCharacterLimit());
	                					break;
	                				}
	                				if (dvCommaSeparated.length() > truncateCharLimit) {
	                					dvCommaSeparated = dvCommaSeparated.substring(0, truncateCharLimit)+"..";
	                				}
                        		}
                				//Added for NeGD feature request end [8 Aug 2019]
                                
                                dpMap.put("Abs"+j+label.replaceAll("[^\\s\\w]*", ""), commaSeparated);
                                dpMap.put("AbsDv"+j+label.replaceAll("[^\\s\\w]*", ""), dvCommaSeparated);
                                if(!"".equals(label2))
                                	dpMap.put("Abs"+j+label2.replaceAll("[^\\s\\w]*", ""), commaSeparated);
                               /* if(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp).getLabelProperties().isCommaSeprator())
                                {
                                	switch(graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp).getLabelProperties().getCommaFormat())
                                	{
                                	case 1:
                                		commaSeparated = parseformat("#,###.##", parseValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp).getLabelProperties().getNumberOfDigits());
                                		dpMap.put("Abs"+label, commaSeparated);
                                		break;
                                	case 2:
                                		try{
                        					if(parseValue<0)
                                			{
                                				if(parseValue < -999)
                                				{
                                					parseValue=Math.abs(parseValue);
                                					double hundreds = parseValue % 1000;
                                					int other = (int) (parseValue / 1000);
                                					commaSeparated = parseformat(",##", other,0) + ',' + parseformat("000", hundreds,graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex).getLabelProperties().getNumberOfDigits());
                                					commaSeparated="-"+commaSeparated;
                                				}
                                				else
                                				{
                                					//commaSeparated=String.valueOf(parseValue);
                                					commaSeparated = parseformat("#,###.##", parseValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex).getLabelProperties().getNumberOfDigits());
                                				}
                                			}
                        					else
                        					{
                        						if(parseValue == 0 || (parseValue > 0 && parseValue < 1000))
                                				{
                        							commaSeparated = parseformat("#,###.##", parseValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex).getLabelProperties().getNumberOfDigits());
                                				}
                        						else
                        						{
                        							double hundreds = parseValue % 1000;
                        							int other = (int) (parseValue / 1000);
                        							commaSeparated = parseformat(",##", other,0) + ',' + parseformat("000", hundreds,graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndex).getLabelProperties().getNumberOfDigits());
                        						}
                        					}
                        				}catch(Exception e){
                                			System.out.println(e);
                                		}
                                		dpMap.put("Abs"+label, commaSeparated);
                                		break;
                                	}
                                }*/
                               /* else
                                {
                                	commaSeparated = parseformat("####.##", parseValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp).getLabelProperties().getNumberOfDigits());
                            		dpMap.put("Abs"+label, commaSeparated);	
                                }*/
                                //commma seaparator ends
								if(graphInfo.getGraphType() == GraphConstants.PERCENTAGE_VBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_HBAR_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.PERCENTAGE_LINE_GRAPH
										|| graphInfo.getGraphType() == GraphConstants.AREA_PERCENTAGE_GRAPH)
								{
									/*if(percentageValueList != null && percentageValueList.size() > 0)
									{*/
										/*	Commented while developing NeGD feature request 15081 as it was always taking 100 [1 Aug 2019]	
										double percValue = 100;
										//double percValue = Double.valueOf(percentageValueList.get(i).toString());
										String commaPercValue = commaFormats(percValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp), graphInfo);
										dpMap.put(percentageValues+j,commaPercValue);*/
									//}
									/*if(realPercentageValueList != null && realPercentageValueList.size() > 0)
									{
										double realPercValue = Double.valueOf(realPercentageValueList.get((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex)).toString());
										//String commaRealPercValue = commaFormats(realPercValue, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp), graphInfo);
										dpMap.put(realPercentageValues+j, realPercValue);//Removed comma separator for bug fixing (as not required in % chart)
									}*/
									//Performance Changes
									dataValue = Double.valueOf(keyValueMap.get(mapKeyStr).toString()) / Double.valueOf(multiDivValueList.get(theIndex).toString());
									dpMap.put("yaxisValue"+j, dataValue);
									double value = dataValue.doubleValue();
									dpMap.put("AbsyaxisValue"+j, commaFormats(value, graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp), graphInfo));
									
									if(null != stackedDataTotalValues)
									{
										dpMap.put("yaxisStackedValue"+j, stackedDataTotalValues.get((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex)));
										value = Double.valueOf(stackedDataTotalValues.get((i*rowListSize*noOfMeasure)+(j*noOfMeasure+theIndex)).toString());
										dpMap.put("AbsyaxisStackedValue"+j, commaFormats(value,graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M"+theIndexProp) ,graphInfo));
									}
									
								}
									
							}else{
								//12934
								if(!drillList.isEmpty() && rowList.size() > j  && drillList.size() > j &&  drillList.get(j)!=null && !drillList.get(j).equals("null"))
								{
									/*if(isMultipleMeasure)
										drillMap.put(rowList.get(index).toString(), drillList.get(j).toString());
									else*/
									drillMap.put(rowList.get(j).toString(), drillList.get(j).toString());
								}
								//12934
								/*if(startIndex==paginationIndex)
								{
									if(j==rowListSize-1)									
										dpMap.put("rowLastPage", 1);
									startIndex=startIndexcolne;;
									break innerloop;
								}*/
								//startIndex++;
								counts++;
								continue;	
							}
						/*}*/
					}
					
					counts++;
					/*if(startIndex==paginationIndex && legendflag)
					{

						if(!dpMap.containsKey(colLabel))
						{
							dpMap.put(colLabel, colList2.get(i).toString());
							dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
						}
						if(!dpMap.containsKey(drillAxis))
						{
							if(!drillList.isEmpty() && drillList.size() > drillIndex && drillList.get(drillIndex)!=null && !drillList.get(drillIndex).equals("null"))
						{
							dpMap.put(drillAxis, drillList.get(drillIndex).toString());
						}
						}
						if(j==rowListSize-1)									
							dpMap.put("rowLastPage", 1);										
						startIndex=startIndexcolne;
						//dpList.add(dpMap);
						break innerloop;
					}*/
					//startIndex++;
				//}
			}
				if(!dpMap.containsKey(drillAxis))//13494
				{
					if(!drillList.isEmpty() && drillList.size() > drillIndex && drillList.get(drillIndex)!=null && !drillList.get(drillIndex).equals("null")) 
					dpMap.put(drillAxis, drillList.get(drillIndex).toString());

				}

				//Performance Changes				
				dpMap.put("realTotal", stackTotal);
				
				
			//cnt = -1;
			if(isTrend)//Trend Line
			{
				String label;

				for(int c=0;c<noOfTrendLines;c++)
				{
					String[] splitString = trendLineColoumn.get(c).toString().split(",");
					String key = (splitString[0])+(String)trendAlgoList.get(c);
					List temp=(ArrayList)(trendMAp.get(key));//fetch the coloumn given by the user
					label=trendLineName.get(c).toString();
					if(temp.get(i)!=null)
					dpMap.put(label+"trend", temp.get(i));
					if(i==colListSize-1)
						dpMap.put("TrendLabel"+c, label);
				}

			}
			dpList.add(dpMap);
		/*	if(i==categoryIndex)
			{
				if(!dpMap.containsKey(colLabel) && dpMap.size() >0)
				{
					dpMap.put(colLabel, colList2.get(i).toString());
					dpMap.put(truncatedLabel,truncatedColList.get(i).toString());
				}
				if(!dpMap.containsKey(drillAxis) && dpMap.size() >0)
				{
					if(drillList.size() > drillIndex && (!drillList.isEmpty() && drillList.get(drillIndex)!=null) && !drillList.get(drillIndex).equals("null"))
						dpMap.put(drillAxis, drillList.get(drillIndex).toString());

				}
				if((categorypaginationIndex-1)==categoryIndex)
				{
					
					dpList.add(dpMap);
					return dpList;
				}
				if(dpMap.size() >0)
					dpList.add(dpMap);
				categoryIndex++;
			}*/
			//}
			
		}
		
		graphJson.setDataProvider(dpList);
		//-------------------------------------------- Data Provider End(barGraphDataProvider)---------------------------------------
		
		//-------------------------------------------Balloon Start----------------------------------------------------
		if(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().isMouseOverTextEnable())
		{
			Balloon balloon = new Balloon();
			balloon.setColor(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().getDataValueMouseOverFont().getFontColor());
			balloon.setFontSize(graphInfo.getGraphProperties().getDataValueProperties().getDataValueMouseOver().getDataValueMouseOverFont().getFontSize());
			balloon.setAdjustBorderColor(true);
			balloon.setBorderThickness(0);
			graphJson.setBalloon(balloon);
		}
		//--------------------------------------------Balloon End--------------------------------------------------

		//Reference Line Start
		List<Guides> guideList = new ArrayList<Guides>();
		int refLineStyle = 0;
		int refDashLength = 0;
		int i=0;//Fetching reference line style index
		Map<Integer, ReferenceLine> testMap = graphInfo.getGraphProperties().getReferencelinePropertiesMap();
		for (Entry<Integer, ReferenceLine> entry : testMap.entrySet()) {
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
			guides.setValueAxis("valueAxes0");
			guides.setLabel(referenceLine.getLabel());
			double sd=Double.parseDouble(referenceLine.getValue());
			int adjustedDigit = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M0").getLabelProperties().getAdjustedDigit();
			// When AutoValue is enabled, don't divide reference line value
			divValue = graphInfo.getGraphProperties().getyAxisPropertiesMap().get("M0").getLabelProperties().isAutoValue() 
					? 1 : (int)(Math.pow(10, adjustedDigit));
			if(sd>0 && sd/divValue>0)
				guides.setValue(sd/divValue);
			else
				guides.setValue(sd);
			guides.setInside(true);
			guides.setLineThickness(referenceLine.getWidth());
			guides.setDashLength(refDashLength);
			guides.setToValue(guides.getValue());
			guideList.add(guides);
		}
		graphJson.setGuides(guideList);
		//Reference Line End

		//cone and cylinder
		if(graphInfo.getGraphProperties().getBarProperties().getType() == 2)
		{
			graphJson.setAngle(30);
			graphJson.setDepth3D(30);
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getAngle() > 1 && graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isVisible())
				graphJson.setAngle(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getAngle());
			if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getDepth3d() > 1 && graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isVisible())
				graphJson.setDepth3D(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getDepth3d());
		}
		else if(graphInfo.getGraphProperties().getBarProperties().getType() == 3)
		{
				graphJson.setAngle(30);
				graphJson.setDepth3D(30);
				if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getAngle() > 1 && graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isVisible())
					graphJson.setAngle(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getAngle());
				if(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getDepth3d() > 1 && graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().isVisible())
					graphJson.setDepth3D(graphInfo.getGraphProperties().getGraphAreaProperties().getGeneralGraphArea().getDepth3d());
		}
		
		
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

		if(graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isEnable())//.isHorizontal() || graphInfo.getGraphProperties().getGraphAreaProperties().getGraphChartCursor().isVertical())
		{
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
        boolean adaptiveBehaviour = graphInfo.getGraphProperties().getAdaptiveBehaviour();
        List<LinkedHashMap<String, Object>> rulesMapList = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String, Object> dpRulesMap =  new LinkedHashMap<String, Object>();
        if(graphInfo.getGraphProperties().getLegendProperties().getLegendPanelProperties().isLegendPanelVisible() && isLegendVisible && adaptiveBehaviour)
		{
        dpRulesMap.put("maxWidth", 320);
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
		//Responsive end
        //Double d = new Double(graphJson.getPrecision());
		graphJson.setPercentPrecision(2);//Setting default 2 instead (d.intValue()) for NeGD feature request 15081 [1 Aug 2019]
		
		jsonList.add(graphJson);
		
		try {
			json = objectMapper.writeValueAsString(jsonList);

			//System.out.println(json);

		} catch (IOException e) {
			ApplicationLog.error(e);
		}
		
		return (json);
	}
	private static final String[] appendValue(String[] s1 ,String newValue) {

		  String[] erg = new String[s1.length + 1];
		  erg[erg.length-1] = newValue;
	      System.arraycopy(s1, 0, erg, 0, s1.length);

	      return erg;

	  }
	public static String commaFormats(double in_objValue, YaxisTrendProperties yaxisTrendProperties,GraphInfo graphInfo){

		double dValue = in_objValue;
		String strData = "";
		/*ApplicationLog.info(in_objValue+"colProp..."+colProp);
		ApplicationLog.info("colProp.getValueFormat()..."+colProp.getValueFormat());
		ApplicationLog.info("colProp.getValueFormat().getNumberFormat()..."+colProp.getValueFormat().getNumberFormat());
		ApplicationLog.info("colProp.getValueFormat().getNumberFormat().getAdjustedDigit()..."+colProp.getValueFormat().getNumberFormat().getAdjustedDigit());*/
		int place = yaxisTrendProperties.getLabelProperties().getAdjustedDigit();//in_alsItemInfo.getColumnProperties()
		/*if (place > 0) {
			dValue /= Math.pow(10, place);
		}*/

		Object commaSepObj = yaxisTrendProperties.getLabelProperties().isCommaSeprator();
		boolean commaSep = false;
		if (commaSepObj != null) {
			commaSep = ((Boolean) commaSepObj).booleanValue();
		}

		int commaPosStyleObj =  yaxisTrendProperties.getLabelProperties().getCommaFormat();
		//commaPosStyleObj = false;
		boolean commaPosStyle = false;
		if (commaPosStyleObj == 2) {
			
			//commaPosStyle = ((Boolean) commaPosStyleObj).booleanValue();
			commaPosStyle = true;
		}
		
		if (commaSep && commaPosStyle
				&& (dValue <= -100000 || dValue >= 100000)) {				
			yaxisTrendProperties.getLabelProperties().setCommaSeprator(false);
			String strFmt = graphInfo.GetDecimalFormatString(yaxisTrendProperties,0);
			yaxisTrendProperties.getLabelProperties().setCommaSeprator(true);				
			java.text.DecimalFormat DecFormat = new java.text.DecimalFormat(
					strFmt);
			strData = DecFormat.format(dValue);
			strData = StringUtil.modifyDataForIndianStyleCommaPos(strData,
					null, false);
		} else {
			String strFmt = graphInfo.GetDecimalFormatString(yaxisTrendProperties,0);
			java.text.DecimalFormat DecFormat = new java.text.DecimalFormat(
					strFmt);
			// Check The Infinate & NaN value By Piyush Ramani Bug:5949
			if (Double.isInfinite(dValue) || Double.isNaN(dValue))
				strData = "";
			else
				strData = DecFormat.format(dValue);
		}
		/*if(yaxisTrendProperties.getLabelProperties().isShowadAdjustedSuffixed())
			strData += GeneralUtil.getAdjustedDigitSuffix(place);*/
	
		return strData;
	}

	/**
	 * Generate prefixes for big numbers (K, M, B) based on comma format setting.
	 * @param adjustedDigits The adjusted digits value (not currently used, defaults to full range)
	 * @param commaFormat 1 = US System (K, M, B), 2 = Indian System (K, L, Cr)
	 * @return List of prefix maps for AMCharts prefixesOfBigNumbers
	 */
	public static List<Map<String, Object>> getPrefixes(int adjustedDigits, int commaFormat) {
		List<Map<String, Object>> list = new ArrayList<>();
		adjustedDigits = 10; // Default to full range
		if (commaFormat == 1) {
			// US Numbering System (K, M, B)
			switch (adjustedDigits) {
				case 3:
					list.add(prefix(1_000, "K"));
					break;
				case 6:
					list.add(prefix(1_000_000, "M"));
					break;
				case 9:
					list.add(prefix(1_000_000_000, "B"));
					break;
				default:
					list.add(prefix(1_000, "K"));
					list.add(prefix(1_000_000, "M"));
					list.add(prefix(1_000_000_000, "B"));
					break;
			}
		} else if (commaFormat == 2) {
			// Indian Numbering System (K, L, Cr)
			switch (adjustedDigits) {
				case 3:
					list.add(prefix(1_000, "K"));
					break;
				case 5:
					list.add(prefix(1_00_000, "L"));
					break;
				case 7:
					list.add(prefix(1_00_00_000, "Cr"));
					break;
				default:
					list.add(prefix(1_000, "K"));
					list.add(prefix(1_00_000, "L"));
					list.add(prefix(1_00_00_000, "Cr"));
					break;
			}
		}
		return list;
	}

	private static Map<String, Object> prefix(int number, String prefix) {
		Map<String, Object> map = new HashMap<>();
		map.put("number", number);
		map.put("prefix", prefix);
		return map;
	}

}
