import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class WriteRInterface {
	
	private BufferedWriter bw;
	
	
	boolean growthrate;
	boolean trend;
	boolean correlation_distribution;
	boolean scatter;
	boolean multiple_time_series;
	boolean phillips;
	boolean beveridge;
	boolean bandpass_filter;
	boolean quantiles;
	boolean herfindahl;
	boolean cross_correlation_function;
	boolean heat_maps;
	boolean heat_maps_2V;
	boolean time_series;
	boolean ratio;
	boolean boxplot;
	boolean histogram;
	
	WriteRInterface(){
		
		 time_series = false;
		 ratio = false;
		 boxplot = false;
		 growthrate = false;
		 correlation_distribution = false;
		 scatter = false;
		 multiple_time_series = false;
		 bandpass_filter = false;
		 quantiles = false;
		 herfindahl = false;
		 cross_correlation_function = false;
		 heat_maps = false;
		 heat_maps_2V = false;
		 histogram=false;
		
	}
	
	public void writeVariableTXTFile(){
		
		
		try{
			
			
			mergeHeatmapsList();
			mergeHistogramList();
			mergeBoxplotList();
			mergeCorrelationListList();
			mergeHeatmaps2VList();
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/variables.txt",false)));
		
		
		bw.append("Variable\t VariableName\t Agent\t Method\t Filter1\t Value\t OP\t Filter2\t Value\t OP\t Weighting\t PV");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++)
		{

			for(int j=0; j< PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size();j++){
				
				
				if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).isVariable){
					
		
					/*Variable name*/
					bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).variable.name);
					bw.append("\t");
					
					/*Instance name*/
					
					bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName);
					bw.append("\t");
					
					/*Agent*/
					
					bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName);
					bw.append("\t");
					
					/*Method*/
					
					
					boolean weighted = false;
					String weightedWith = "";
					
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.contains("(")){
						
						bw.append("mean");
						bw.append("\t");
						weighted = true;
						int ind1 = PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.indexOf("(");
						int ind2 = PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.indexOf(")");
						
						weightedWith =  PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.substring(ind1+1, ind2);
						
					}else if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.equalsIgnoreCase("standard deviation")){
						
						bw.append("sd");
						bw.append("\t");
						
					}else if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.equalsIgnoreCase("standard deviation percent")){
						
						bw.append("sd_percent");
						bw.append("\t");
						
					}
					
					
					else{
						
						bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceMethod.toLowerCase());
						bw.append("\t");
						
					}
				
					
					/*Filter 1 Variable*/
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1.equals("No Filter")){
						
						bw.append("No");
						bw.append("\t");
						
						/*Filter 1 Value*/
						
						bw.append("No");
						bw.append("\t");
						
						
						/*Filter 1 OP*/
						
						bw.append("=");
						bw.append("\t");
						
					}else{
					
					
						for(int k=0; k<PlottingSettings.listOfAgentsVariableInstances.get(i).filter.size();k++){
							
							if(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).filterName.equals(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1)){
								
								
								bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).variableName);
								bw.append("\t");
								
								/*Filter 1 Value*/
								
								bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).filterValue);
								bw.append("\t");
								
								
								/*Filter 1 OP*/
								
								bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).filterMethod);
								bw.append("\t");
								
								break;
								
								
							}
							
							
						}
					
					}
					
					/*Filter 2 Variable*/
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2.equals("No Filter")){
						
						bw.append("No");
						bw.append("\t");
						
						/*Filter 2 Value*/
						
						bw.append("No");
						bw.append("\t");
						
						
						/*Filter 2 OP*/
						
						bw.append("=");
						bw.append("\t");
						
					}else{
					
					
						for(int k=0; k<PlottingSettings.listOfAgentsVariableInstances.get(i).filter.size();k++){
							
							if(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).filterName.equals(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2)){
								
								
								bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).variableName);
								bw.append("\t");
								
								/*Filter 1 Value*/
								
								bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).filterValue);
								bw.append("\t");
								
								
								/*Filter 1 OP*/
								
								bw.append(PlottingSettings.listOfAgentsVariableInstances.get(i).filter.get(k).filterMethod);
								bw.append("\t");
								
								break;
								
								
							}
							
							
						}
					
					}
					
					/*Weight*/
					
					if(weighted){
					
						bw.append(weightedWith);
						bw.append("\t");
					}else{
						
						bw.append("No");
						bw.append("\t");
						
					}
		
					/*PV*/
					
					bw.append("1");
					bw.append("\t");
					
					bw.newLine();
					
				}
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		bw.close();
		
		deleteTempVarInstances();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
		deleteTempVarInstances();
	}
		
	}
	
	
	
	
public void writeRatioTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/ratio_data.txt",false)));
		
		
		bw.append("Variable	\t PV \t Variable \t PV \t Name");
		bw.newLine();
		
		for(int i=0; i< PlottingSettings.listOfRatioInstances.size();i++)
		{

				ratio = true;
		
					/*Variable Instance name*/
					bw.append( PlottingSettings.listOfRatioInstances.get(i).numerator.instanceName);
					bw.append("\t");
					
					/*Process variable name*/
					
					bw.append("1");
					bw.append("\t");
					
					/*Variable Instance name*/
					bw.append( PlottingSettings.listOfRatioInstances.get(i).denominator.instanceName);
					bw.append("\t");
					
					/*Process PV*/
					
					bw.append("1");
					bw.append("\t");
					
					
					/*Ratio name*/
					bw.append( PlottingSettings.listOfRatioInstances.get(i).ratioInstanceName);
					bw.append("\t");
					
					bw.newLine();
			
			
		}
		
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}





public void writeGrowthrateTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/growth_rate_data.txt",false)));
		
		
		bw.append("Variable \t	Monthly \t	Quarter	\t Yearly \t Aggreg");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfAgentsVariableInstances.size();i++)
		{

			for(int j=0; j< PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size();j++){
				
				
				if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).isVariable){
					
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).growthRate.equals("Annual")){
						
						growthrate = true;
						
						
						/*Variable Instance name*/
						bw.append( PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName);
						bw.append("\t");
						
						/*Monthly*/
						bw.append( "No");
						bw.append("\t");
						
						/*Quarterly*/
						bw.append(  "No");
						bw.append("\t");
						
						/*Annual*/
						bw.append( "Yes");
						bw.append("\t");
						
						/*Aggregation*/
						bw.append( "sum");
						bw.append("\t");
						
						bw.newLine();
							
						
					}else if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).growthRate.equals("Quarterly")){
						
						growthrate = true;
						
						/*Variable Instance name*/
						bw.append( PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName);
						bw.append("\t");
						
						/*Monthly*/
						bw.append( "No");
						bw.append("\t");
						
						/*Quarterly*/
						bw.append(  "Yes");
						bw.append("\t");
						
						/*Annual*/
						bw.append( "No");
						bw.append("\t");
						
						/*Aggregation*/
						bw.append( "sum");
						bw.append("\t");
						
						bw.newLine();
						
						
					}else if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).growthRate.equals("Monthly")){
						
						growthrate = true;
						
						/*Variable Instance name*/
						bw.append( PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName);
						bw.append("\t");
						
						/*Monthly*/
						bw.append( "Yes");
						bw.append("\t");
						
						/*Quarterly*/
						bw.append(  "No");
						bw.append("\t");
						
						/*Annual*/
						bw.append( "No");
						bw.append("\t");
						
						/*Aggregation*/
						bw.append( "sum");
						bw.append("\t");
						
						bw.newLine();
						
						
					}
					
					
					
				}
				
			}
		}
		
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}





public void writeSingleTimeSeriesTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/time_series_data.txt",false)));
		
		bw.append("Name \t LowerBound \t upperBound \t tMin \t tMax");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfSingleTimeSeries.size();i++)
		{

					time_series = true;
		
					/*Variable Instance name*/
					bw.append( PlottingSettings.listOfSingleTimeSeries.get(i).instanceName);
					bw.append("\t");
					
					
					/*Lower bound*/
					if(!PlottingSettings.listOfSingleTimeSeries.get(i).lowerBoundEnabled){
						
						
						bw.append("No");
						bw.append("\t");
						
					}else{
						
					
						bw.append( Double.toString(PlottingSettings.listOfSingleTimeSeries.get(i).lowerBound));
						bw.append("\t");
						
					}
					
					/*Upper bound*/
					if(!PlottingSettings.listOfSingleTimeSeries.get(i).upperBoundEnabled){
						
						
						bw.append("No");
						bw.append("\t");
						
					}else{
						
					/*Variable Instance name*/
						bw.append( Double.toString(PlottingSettings.listOfSingleTimeSeries.get(i).upperBound));
						bw.append("\t");
						
					}
					
					
					/*Tmin*/
					bw.append( 	Integer.toString(PlottingSettings.listOfSingleTimeSeries.get(i).tmin));
					bw.append("\t");
					
					/*Tmax*/
					bw.append( 	Integer.toString(PlottingSettings.listOfSingleTimeSeries.get(i).tmax));
					bw.append("\t");
			
					
					bw.newLine();
			
			
		}
		
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}




public void writeMultipleTimeSeriesTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/multiple_time_series_data.txt",false)));
		
		
		bw.append("Variable \t PV \t Number \t lowLim \t upperLimit \t tmin \t tmax \t Plot_Name_and_Y_axis");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfMultipleTimeSeries.size();i++)
		{
			
			multiple_time_series = true;

			for(int j=0; j <PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++ )
			{
				
				/*Variable Instance name*/
				bw.append( PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
				bw.append("\t");
				
		
				
				if(j==0){
					
					String nums = Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size() + PlottingSettings.listOfMultipleTimeSeries.get(j).ratiosUsedForMultioleTimeSeries.size());
					
					/* PV*/
					bw.append( "1");
					bw.append("\t");
					
					
					bw.append(nums);
					bw.append("\t");
					
				
					
				}else{
					
					
					/* PV*/
					bw.append( "0");
					bw.append("\t");
					
					bw.append("0");
					bw.append("\t");
					
			
					
				}
				
				
				/*Lower bound*/
				if(!PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBoundEnabled){
					
					
					bw.append("No");
					bw.append("\t");
					
				}else{
					
				
					bw.append( Double.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBound));
					bw.append("\t");
					
				}
				
				/*Upper bound*/
			if(!PlottingSettings.listOfMultipleTimeSeries.get(i).upperBoundEnabled){
					
					
					bw.append("No");
					bw.append("\t");
					
				}else{
					
				/*Variable Instance name*/
					bw.append( Double.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).upperBound));
					bw.append("\t");
					
				}
				
				
				/*Tmin*/
				bw.append( 	Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).tmin));
				bw.append("\t");
				
				/*Tmax*/
				bw.append( 	Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).tmax));
				bw.append("\t");
				
				bw.append( PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName);
				
				bw.newLine();
				
				
				
			}
			
			for(int j=0; j <PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++ )
			{
				
				/*Variable Instance name*/
				bw.append( PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName);
				bw.append("\t");
				
		
				
				if(j==0 && PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size() ==0){
					
					String nums = Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size());
					
					/* PV*/
					bw.append( "1");
					bw.append("\t");
					
					bw.append(nums);
					bw.append("\t");
					
					
				}else{
					
					/* PV*/
					bw.append( "0");
					bw.append("\t");
					
					bw.append("0");
					bw.append("\t");
					
					/*Lower bound*/
					if(!PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBoundEnabled){
						
						
						bw.append("No");
						bw.append("\t");
						
					}else{
						
					
						bw.append( Double.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).lowerBound));
						bw.append("\t");
						
					}
					
					/*Upper bound*/
				if(!PlottingSettings.listOfMultipleTimeSeries.get(i).upperBoundEnabled){
						
						
						bw.append("No");
						bw.append("\t");
						
					}else{
						
					/*Variable Instance name*/
						bw.append( Double.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).upperBound));
						bw.append("\t");
						
					}
					
					
					/*Tmin*/
					bw.append( 	Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).tmin));
					bw.append("\t");
					
					/*Tmax*/
					bw.append( 	Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).tmax));
					bw.append("\t");
					
					bw.append( PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName);
					
					bw.newLine();
					
				}
				
				
				
			}
				

		bw.newLine();
			
		}
	
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}










public void writeBandpassFilterTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/bandpass_filter_data.txt",false)));
		
		
		bw.append("Variable \t PV \t Cor \t Log \t Aggreg \t Low \t high \t NFix \t Drift \t Number \t	lowerBound \t upperBound \t tmin \t tmax \t Plot_Name_and_Y_axis");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfSingleBandpassFilteredTimeSeries.size();i++)
		{
			
			bandpass_filter = true;
			
			bw.append( PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).instanceName);
			bw.append("\t");
			
			bw.append("1");
			bw.append("\t");
			
			if(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).correlation)
				bw.append("Yes");
			else
				bw.append("No");
		
			bw.append("\t");
			
			if(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).logarithmic)
				bw.append("Yes");
			else
				bw.append("No");
		
			bw.append("\t");
			
			
			bw.append( PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).aggregation.toLowerCase());
			bw.append("\t");
			
			bw.append( Integer.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).low));
			bw.append("\t");
			
			bw.append( Integer.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).high));
			bw.append("\t");
			
			bw.append( Integer.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).Nfix));
			bw.append("\t");
			
			if(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).drift)
				bw.append("TRUE");
			else
				bw.append("FALSE");
			
			bw.append("\t");
			
			bw.append("1");
			bw.append("\t");
			
			if(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled)
				bw.append( Double.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).lowerBound));
			else
				bw.append("No");
			bw.append("\t");
			
			if(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).upperBoundEnabled)
				bw.append( Double.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).upperBound));
			else
				bw.append("No");
			bw.append("\t");
			
			
			
			bw.append( Integer.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).tmin));
			bw.append("\t");
			
			bw.append( Integer.toString(PlottingSettings.listOfSingleBandpassFilteredTimeSeries.get(i).tmax));
			bw.append("\t");
			
			
			
			bw.newLine();
			
		}
		
		
		for(int i=0; i< PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.size();i++)
		{

			for(int j=0; j <PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size();j++ )
			{
				
				bandpass_filter = true;
				
				/*Variable Instance name*/
				bw.append( PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.get(j).instanceName);
				bw.append("\t");
				
		
				
				if(j==0){
					
					String nums = Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size() + PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(j).ratiosUsedForMultioleTimeSeries.size());
					
					/* PV*/
					bw.append( "1");
					bw.append("\t");
					
					
					
					
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).correlation)
						bw.append("Yes");
					else
						bw.append("No");
				
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).logarithmic)
						bw.append("Yes");
					else
						bw.append("No");
				
					bw.append("\t");
					
					
					bw.append( PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).aggregation.toLowerCase());
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).low));
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).high));
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).Nfix));
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).drift)
						bw.append("TRUE");
					else
						bw.append("FALSE");
					
					bw.append("\t");
					
				
					
					
					
					bw.append(nums);
					bw.append("\t");
					
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled)
						bw.append( Double.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).lowerBound));
					else
						bw.append("No");
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).upperBoundEnabled)
						bw.append( Double.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).upperBound));
					else
						bw.append("No");
					bw.append("\t");
					
					
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).tmin));
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).tmax));
					bw.append("\t");
					
					bw.append( PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).timeSeriesName);
					
				}else{
					
					
					/* PV*/
					bw.append( "0");
					bw.append("\t");
					
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).correlation)
						bw.append("Yes");
					else
						bw.append("No");
				
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).logarithmic)
						bw.append("Yes");
					else
						bw.append("No");
				
					bw.append("\t");
					
					
					bw.append( PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).aggregation.toLowerCase());
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).low));
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).high));
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).Nfix));
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).drift)
						bw.append("TRUE");
					else
						bw.append("FALSE");
					
					bw.append("\t");
					
					bw.append("0");
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).lowerBoundEnabled)
						bw.append( Double.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).lowerBound));
					else
						bw.append("No");
					bw.append("\t");
					
					if(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).upperBoundEnabled)
						bw.append( Double.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).upperBound));
					else
						bw.append("No");
					bw.append("\t");
					
					
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).tmin));
					bw.append("\t");
					
					bw.append( Integer.toString(PlottingSettings.listOfMultipleBandpassFilteredTimeSeries.get(i).tmax));
					bw.append("\t");
					
					bw.append("");
					
				}
				
				bw.newLine();
				
			}
			
			for(int j=0; j <PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size();j++ )
			{
				
				/*Variable Instance name*/
				bw.append( PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.get(j).ratioInstanceName);
				bw.append("\t");
				
		
				
				if(j==0 && PlottingSettings.listOfMultipleTimeSeries.get(i).variableInstancesUsedForMultioleTimeSeries.size() ==0){
					
					String nums = Integer.toString(PlottingSettings.listOfMultipleTimeSeries.get(i).ratiosUsedForMultioleTimeSeries.size());
					
					/* PV*/
					bw.append( "1");
					bw.append("\t");
					
					bw.append(nums);
					bw.append("\t");
					
					bw.append( PlottingSettings.listOfMultipleTimeSeries.get(i).timeSeriesName);
					
				}else{
					
					/* PV*/
					bw.append( "0");
					bw.append("\t");
					
					bw.append("0");
					bw.append("\t");
					
					bw.append("");
					
				}
				
				bw.newLine();
				
			}
				
	
		
			
		}
	
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}











public void writeCrossCorrelationTXTFile(){
	
	
	try{
	
	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/cross_correlation_function_data.txt",false)));
	
	
	bw.append("Variable \t aggregation \t PV \t variable \t aggregation \t PV \t Lags");
	bw.newLine();
	
	
	for(int i=0; i< PlottingSettings.listOfCrossCorrelation.size();i++)
	{
		
		cross_correlation_function = true;
		
		bw.append( PlottingSettings.listOfCrossCorrelation.get(i).firstComponent.getName());
		bw.append( "\t");
		
		if(PlottingSettings.listOfCrossCorrelation.get(i).firstComponent.aggregationMethod.equals("No"))
			bw.append("No");
		else
			bw.append( PlottingSettings.listOfCrossCorrelation.get(i).firstComponent.aggregationMethod.toLowerCase());
		bw.append( "\t");
		
		bw.append("1");
		bw.append( "\t");
		
		bw.append( PlottingSettings.listOfCrossCorrelation.get(i).secondComponent.getName());
		bw.append( "\t");
		
		if(PlottingSettings.listOfCrossCorrelation.get(i).secondComponent.aggregationMethod.equals("No"))
			bw.append("No");
		else
			bw.append( PlottingSettings.listOfCrossCorrelation.get(i).secondComponent.aggregationMethod.toLowerCase());
		bw.append( "\t");
		
		bw.append("1");
		bw.append( "\t");
		
		bw.append( Integer.toString(PlottingSettings.listOfCrossCorrelation.get(i).timeLag));
		bw.newLine();
		
	}

		
	bw.close();

}
catch(Exception e)
{
	System.out.println("Content was not written to file!");
}
	
}







public void writeHistogramTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/histogram_data.txt",false)));
		
		
		bw.append("Variable \t ");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfHistograms.size();i++)
		{
			
			bw.append( PlottingSettings.listOfHistograms.get(i).instanceName);
			
			histogram = true;
		
			
			bw.newLine();
			
		}

			
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}






public void writeBoxplotsTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/boxplot_data.txt",false)));
		
		
		bw.append("Variable \t ");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfBoxplots.size();i++)
		{
			
			bw.append( PlottingSettings.listOfBoxplots.get(i).instanceName);
			
			boxplot = true;
		
			
			bw.newLine();
			
		}

			
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}



public void writeHeatmapsTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/heat_maps_data.txt",false)));
		
		
		bw.append("Variable\t PV\t Denominator\t PV\t bins \t lowerBound\t upperBound\t tmin\t tmax \t Name");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfHeatmaps.size();i++)
		{
			
			heat_maps = true;
			
			if(PlottingSettings.listOfHeatmaps.get(i).isVariable){
				bw.append( PlottingSettings.listOfHeatmaps.get(i).instanceName );
				bw.append( "\t");
				
				bw.append( "1" );
				bw.append( "\t");
				
				bw.append( "No");
				bw.append( "\t");
				
				bw.append( "1" );
				bw.append( "\t");
				
			}else{
				
				bw.append( PlottingSettings.listOfHeatmaps.get(i).agentRatio.numerator.name );
				bw.append( "\t");
				
				bw.append( "1" );
				bw.append( "\t");
				
				bw.append( PlottingSettings.listOfHeatmaps.get(i).agentRatio.denominator.name );
				bw.append( "\t");
				
				bw.append( "1" );
				bw.append( "\t");
				
			}
			
			
			
			bw.append( Integer.toString(PlottingSettings.listOfHeatmaps.get(i).bins ));
			bw.append( "\t");
			
			if(PlottingSettings.listOfHeatmaps.get(i).lowerBoundEnabled){
				
				bw.append( Double.toString(PlottingSettings.listOfHeatmaps.get(i).lowerBound) );
				bw.append( "\t");
				
			}else{
				bw.append( "No");
				bw.append( "\t");
			}
			if(PlottingSettings.listOfHeatmaps.get(i).upperBoundEnabled){
				
				bw.append( Double.toString(PlottingSettings.listOfHeatmaps.get(i).upperBound) );
				bw.append( "\t");
				
			}else{
				bw.append( "No");
				bw.append( "\t");
			}
			
			bw.append( Integer.toString(PlottingSettings.listOfHeatmaps.get(i).tmin ));
			bw.append( "\t");
			
			bw.append( Integer.toString(PlottingSettings.listOfHeatmaps.get(i).tmax ));
			bw.append( "\t");
			
			bw.append( PlottingSettings.listOfHeatmaps.get(i).name );
			
			bw.newLine();
			
		}

			
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}



public void writeScatterTXTFile(){
	
	
	try{
	
	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/scatter_data.txt",false)));
	
	
	bw.append("Variable\t PV\t Variable\t PV\t lags");
	bw.newLine();
	
	
	for(int i=0; i< PlottingSettings.listOfScatterPlots.size();i++)
	{
		
		scatter = true;
		
		bw.append( PlottingSettings.listOfScatterPlots.get(i).firstComponent.getName());
		bw.append( "\t");
		
		bw.append( "1");
		bw.append( "\t");
		
		bw.append( PlottingSettings.listOfScatterPlots.get(i).secondComponent.getName());
		bw.append( "\t");
		
		bw.append( "1");
		bw.append( "\t");
		
		bw.append( Integer.toString(PlottingSettings.listOfScatterPlots.get(i).timeLag ));
		bw.append( "\t");
		
		bw.newLine();
		
	}

		
	bw.close();

}
catch(Exception e)
{
	System.out.println("Content was not written to file!");
}
	
}



public void writeCorrelationTXTFile(){
	
	
	try{
	
	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/correlation_distribution_data.txt",false)));
	
	
	bw.append("Variable \t PV \t variable \t  PV ");
	bw.newLine();
	
	
	for(int i=0; i< PlottingSettings.listOfCorrelation.size();i++)
	{
		
		correlation_distribution = true;
		
		bw.append( PlottingSettings.listOfCorrelation.get(i).instanceName1);
		bw.append( "\t");
		
	
		
		bw.append("1");
		bw.append( "\t");
		
		bw.append( PlottingSettings.listOfCorrelation.get(i).instanceName2);
		bw.append( "\t");

		bw.append("1");
	
		bw.newLine();
		
	}

		
	bw.close();

}
catch(Exception e)
{
	System.out.println("Content was not written to file!");
}
	
}





public void writeHeatmaps2VTXTFile(){
		
		
		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/heat_maps_data_2V.txt",false)));
		
		
		bw.append("Variable\t PV\t Denominator\t PV\t l_kim\t u_lim\t Variable2\t PV\t Denominator2\t PV\t l_lim\t u_lim\t bins \t tmin\t tmax \t name ");
		bw.newLine();
		
		
		for(int i=0; i< PlottingSettings.listOfHeatmaps2V.size();i++)
		{
			
			heat_maps_2V = true;
			
			if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable1){
				bw.append( PlottingSettings.listOfHeatmaps2V.get(i).instanceName1 );
				bw.append( "\t");
				
				bw.append( "1" );
				bw.append( "\t");
				
				bw.append( "No");
				bw.append( "\t");
				
				bw.append( "0" );
				bw.append( "\t");
				
			}else{
				
				bw.append( PlottingSettings.listOfHeatmaps2V.get(i).instanceName1 );
				bw.append( "\t");
				
				bw.append( "1" );
				bw.append( "\t");
				
				bw.append( PlottingSettings.listOfHeatmaps2V.get(i).instanceNameDenominator1 );
				bw.append( "\t");
				
				bw.append( "0" );
				bw.append( "\t");
				
			}
			
			
			if(PlottingSettings.listOfHeatmaps2V.get(i).lowerBoundEnabledX){
				
				bw.append( Double.toString(PlottingSettings.listOfHeatmaps2V.get(i).lowerBoundX) );
				bw.append( "\t");
				
			}else{
				bw.append( "No");
				bw.append( "\t");
			}
			if(PlottingSettings.listOfHeatmaps2V.get(i).upperBoundEnabledX){
				
				bw.append( Double.toString(PlottingSettings.listOfHeatmaps2V.get(i).upperBoundX) );
				bw.append( "\t");
				
			}else{
				bw.append( "No");
				bw.append( "\t");
			}
			
			
			
			if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable2){
				bw.append( PlottingSettings.listOfHeatmaps2V.get(i).instanceName2 );
				bw.append( "\t");
				
				bw.append( "0" );
				bw.append( "\t");
				
				bw.append( "No");
				bw.append( "\t");
				
				bw.append( "0" );
				bw.append( "\t");
				
			}else{
				
				bw.append( PlottingSettings.listOfHeatmaps2V.get(i).instanceName2 );
				bw.append( "\t");
				
				bw.append( "0" );
				bw.append( "\t");
				
				bw.append( PlottingSettings.listOfHeatmaps2V.get(i).instanceNameDenominator2 );
				bw.append( "\t");
				
				bw.append( "0" );
				bw.append( "\t");
				
			}
			
			
			if(PlottingSettings.listOfHeatmaps2V.get(i).lowerBoundEnabledY){
				
				bw.append( Double.toString(PlottingSettings.listOfHeatmaps2V.get(i).lowerBoundY) );
				bw.append( "\t");
				
			}else{
				bw.append( "No");
				bw.append( "\t");
			}
			if(PlottingSettings.listOfHeatmaps2V.get(i).upperBoundEnabledY){
				
				bw.append( Double.toString(PlottingSettings.listOfHeatmaps2V.get(i).upperBoundY) );
				bw.append( "\t");
				
			}else{
				bw.append( "No");
				bw.append( "\t");
			}
			
			
			bw.append( Integer.toString(PlottingSettings.listOfHeatmaps2V.get(i).bins ));
			bw.append( "\t");
			
			
			bw.append( Integer.toString(PlottingSettings.listOfHeatmaps2V.get(i).tmin ));
			bw.append( "\t");
			
			bw.append( Integer.toString(PlottingSettings.listOfHeatmaps2V.get(i).tmax ));
			bw.append( "\t");
			
		
			bw.append(PlottingSettings.listOfHeatmaps2V.get(i).name);
			bw.append( "\t");
			
			bw.newLine();
			
		}

			
		bw.close();

	}
	catch(Exception e)
	{
		System.out.println("Content was not written to file!");
	}
		
	}



	void writeConfigureFile(){
		

		try{
		
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SimulationSettings.WORKING_DIRECTORY+"/Configure.r",false)));
		
		
		
		bw.append("library(\"RSQLite\")\nlibrary(\"mFilter\")\nlibrary(\"fields\")\n\n########################################Input-Section: Path Locations ###############################################################");
		bw.newLine();
		/*Snapshots saved?*/
		bw.append(returnStringOfBooleanVar("save_snapshots",SimulationSettings.SNAPSHOTS));
		bw.newLine();
		

		bw.append("# Experiment name = name of Parameter");
		bw.newLine();
		
		String expName ;
		
		if(SimulationSettings.numParameters==0){
			
			bw.append("experiment_name<-\"Batch\"");
			
			expName="Batch";
			
		}else{
			
			bw.append("experiment_name<-\""+SimulationSettings.PARAMETER_1.name+"\"");
			expName=SimulationSettings.PARAMETER_1.name;
			
		}
		bw.newLine();
		bw.append("# Path to the root directory of the databases.");
		bw.newLine();
		bw.append("database_location<-paste(\""+SimulationSettings.WORKING_DIRECTORY+"/its/"+expName+"/\",sep=\"\")");
		bw.newLine();
		bw.append("# Path to the root directory plots.");
		bw.newLine();
		bw.append("plot_directory<-\""+SimulationSettings.WORKING_DIRECTORY+"/Plots/\"");
		bw.newLine();
		bw.append("dir.create(plot_directory, showWarnings = FALSE, mode = \"0777\")");
		bw.newLine();

		
		bw.append("# Path to the root directory of scripts.");
		bw.newLine();
		bw.append("#root_directory<-\"/home/sgemkow/Desktop/Data_Analysis_GUI_Serial/Sub_Scripts/\"");
		bw.newLine();		
		bw.append("root_directory<-paste(system('echo $PATH_R_SCRIPTS', intern=TRUE),\"Sub_Scripts/\", sep=\"\")");
		bw.newLine();
		bw.append("# Path to the root directory of input files data_*.txt.");
		bw.newLine();
		bw.append("data_file_directory<-paste(system('echo $PATH_R_SCRIPTS', intern=TRUE),\"Data_Files/\", sep=\"\")");
		bw.newLine();
		
		
		

		bw.append("########################################Input-Section: Data Analysis Options ###############################################################\n# Execute single run analysis?\n");

		
		bw.append(returnStringOfBooleanVar("single_analysis",PlottingSettings.singleRunAnalyis));
		bw.newLine();
		bw.append("# Execute batch run analysis?");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("batch_analysis",PlottingSettings.batchRunAnalyis));
		bw.newLine();
		bw.append("# Execute parameter analysis?");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("parameter_analysis",PlottingSettings.parameterAnalyis));
		bw.newLine();
		bw.append("# Add legend to plot.");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("make_legend",PlottingSettings.addLegend));
		bw.newLine();
		bw.append("# Lines in plot have different colors.");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("colored_lines",PlottingSettings.coloured));
		bw.newLine();
		


		bw.append("########################################Input-Section: Simulation Settings ###############################################################");
		bw.newLine();
		bw.append("# Number of runs per parameter value.");
		bw.newLine();
		bw.append("runs<-"+SimulationSettings.numBatchRuns);
		bw.newLine();
		bw.append("# Values of parameter.");
		bw.newLine();
		
		
		String patamezerValues="parameter_values<-c(\"";
		try{
			
			if(SimulationSettings.numParameters>0){
			patamezerValues = patamezerValues+SimulationSettings.PARAMETER_1.values.get(0).value+"\"";
		
		
				for(int i=1; i<SimulationSettings.PARAMETER_1.values.size();i++ ){
					
					if(!SimulationSettings.PARAMETER_1.values.get(i).value.equals("")){
						
						patamezerValues = patamezerValues+", \""+SimulationSettings.PARAMETER_1.values.get(i).value+"\"";
						
					
					}
					
				}
				
			
				if(SimulationSettings.PARAMETER_1.values.size()>0)
					patamezerValues = patamezerValues+")";
				else
					patamezerValues = patamezerValues+"\")";
				
			
			}else{
				
				patamezerValues = patamezerValues+"\")";
			}
			bw.append(patamezerValues);
			
		
		}catch(Exception e){
			System.out.println(e);
			bw.append("parameter_values<-c(\"\")");
		}
		
		
		bw.newLine();
		bw.append("print(\"Parameter values: \")");
		bw.newLine();
		bw.append("print(parameter_values)");
		bw.newLine();

		bw.newLine();
		bw.append("# Number of parameter values.");
		bw.newLine();
		bw.append("number_of_parameters<-length(parameter_values)");
		bw.newLine();
		bw.append("# Number of iterations.");
		bw.newLine();
		bw.append("iterations ="+SimulationSettings.numIterations );
		bw.newLine();
		
		if(AgentSettings.agents.size()>0)
			bw.append("frequency ="+AgentSettings.agents.get(0).dataStorageSettings.period);	
		else
			bw.append("frequency =20");	
			
		bw.newLine();
		
		
		bw.append("# Number of iterations in transition phase.");
		bw.newLine();
		bw.append("transition_phase = "+PlottingSettings.transitionPhase);
		bw.newLine();
		bw.append("# Delete transition phase. 1 = Yes or 0 = No");
		bw.newLine();
		if(PlottingSettings.transitionPhase>0)
			bw.append("delete_transition = 1");
		else
			bw.append("delete_transition = 0");
		
		bw.newLine();
		
		
		
		bw.append("# Number of xml-files.\nif(delete_transition == 1)\n{\nnumber_xml = (iterations - transition_phase)/frequency\nif (save_snapshots) number_xml = ceiling(number_xml)	#correct for fractional number_xml\n}else\n{\nnumber_xml = iterations/frequency\nif (save_snapshots) number_xml = ceiling(number_xml)\n}\nprint(paste(\"Number xml files: \", number_xml))\n\n" );
			
				
		bw.append(	"########################################Input-Section: Agents###############################################################\n\n\n");
		bw.append(		"# Number of Agent types.\n" );
		
		
		for(int i=0; i < SimulationSettings.agentNumbers.size();i++){
			
			bw.append(	SimulationSettings.agentNumbers.get(i).agentName+" = "+SimulationSettings.agentNumbers.get(i).numberOfAgents+"\n" );
	
		}
		
		
		
		bw.append(	"########################################Input-Section: Settings for Analysis #########################################################\n");

		bw.append("# Create time series graphs? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("time_series",time_series));
		bw.newLine();
		bw.append("	# Create time series of correlation of two variables? 1 = Yes or 0 = No");
		bw.newLine();

		bw.append(returnStringOfBooleanVar("correlation_distribution",correlation_distribution));
		bw.newLine();
		bw.append("	# Plot ratios.");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("ratio",ratio));
		bw.newLine();

		bw.append("	# Create boxplots? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("boxplot",boxplot));
		bw.newLine();
		
		
		if(PlottingSettings.defaultsBoxplots.iterations.size()>0 ){
			bw.append("boxplot_iteration_vector<-c( "+(Integer.parseInt(PlottingSettings.defaultsBoxplots.iterations.get(0).iteration)-PlottingSettings.transitionPhase/20));
			
			for(int i=1; i< PlottingSettings.defaultsHistogram.iterations.size();i++){
				
				bw.append(","+(Integer.parseInt(PlottingSettings.defaultsBoxplots.iterations.get(i).iteration)-PlottingSettings.transitionPhase/20));
				
			}
		
			bw.append(")");
			
			bw.newLine();

	
			bw.append("print(\"Boxplots_iteration_vector: \")");
			bw.newLine();
			bw.append("print(as.numeric(boxplot_iteration_vector))");
			bw.newLine();
			
			
		}
		
		
		bw.append("# Choose number of last observations. Boxplots for parameter comparison are based on the average of the last observations in each run.");
		bw.newLine();
		bw.append("last_observations<-20");
				

		bw.append("# Create histograms? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("histogram",histogram));
		bw.newLine();
		
		

		bw.append("# Choose the iterations for histograms.");
		bw.newLine();
		
		if(PlottingSettings.defaultsHistogram.iterations.size()>0 ){
			bw.append("histogram_iteration_vector<-c( "+(Integer.parseInt(PlottingSettings.defaultsHistogram.iterations.get(0).iteration)-PlottingSettings.transitionPhase/20));
			
			for(int i=1; i< PlottingSettings.defaultsHistogram.iterations.size();i++){
				
				bw.append(","+(Integer.parseInt(PlottingSettings.defaultsHistogram.iterations.get(i).iteration)-PlottingSettings.transitionPhase/20));
				
			}
		
			bw.append(")");
			
			bw.newLine();
			
			bw.append("# Choose number of bins for batch run.");
			bw.newLine();
			bw.append("bins<-10");
			bw.newLine();
	
			bw.append("print(\"Histogram_iteration_vector: \")");
			bw.newLine();
			bw.append("print(as.numeric(histogram_iteration_vector))");
			bw.newLine();
			
			
		}
		

		bw.append("# Create growth rate plots? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("growth_rate",growthrate));
		bw.newLine();
		bw.append("# Create scatter plots? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("scatter",scatter));
		bw.newLine();

		bw.append("# Calculate correlation? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append("correlation = 0");
		bw.newLine();

		bw.append("# Plot multiple time series.");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("multiple_time_series",multiple_time_series));
		bw.newLine();

		bw.append("# Create bandpass filtered plots. 1 = Yes or 0 = No");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("bandpass_filter",bandpass_filter));
		bw.newLine();
		bw.append("# Create 0,25,50,75,100 quantiles time series  1 = Yes or 0 = No");
		bw.newLine();
		bw.append("quantiles = 0");
		bw.newLine();

		bw.append("# Calculate Herfindahl Index? 1 = Yes or 0 = No");
		bw.newLine();
		bw.append("herfindahl = 0");
		bw.newLine();

		bw.append("# Variables");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("cross_correlation_function",cross_correlation_function));
		bw.newLine();

		bw.append("#Create heat maps");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("heat_maps",heat_maps));
		bw.newLine();
		bw.append("# Create heat maps with two variables");
		bw.newLine();
		bw.append(returnStringOfBooleanVar("heat_maps_2V",heat_maps_2V));
		bw.newLine();
		
		bw.close();
		
		}
		catch(Exception e)
		{
			System.out.println("Content was not written to file!");
		}
	}
	
	private String returnStringOfBooleanVar(String input, boolean bool){
		
		if(bool){
			return input+"<-1";
		}else{
			return input+"<-0";
		}
		
	}
	
	
	

public void mergeHeatmapsList(){
	
	for(int i=0; i< PlottingSettings.listOfHeatmaps.size();i++ ){
		
		
		if(PlottingSettings.listOfHeatmaps.get(i).isVariable){
			
			PlottingSettings.listOfHeatmaps.get(i).instanceName = getVariableInstance(PlottingSettings.listOfHeatmaps.get(i).variable,PlottingSettings.listOfHeatmaps.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps.get(i).Filter1, PlottingSettings.listOfHeatmaps.get(i).Filter2);
			
		}else{
			
			PlottingSettings.listOfHeatmaps.get(i).instanceName = getVariableInstance(PlottingSettings.listOfHeatmaps.get(i).agentRatio.numerator,PlottingSettings.listOfHeatmaps.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps.get(i).Filter1, PlottingSettings.listOfHeatmaps.get(i).Filter2);
			
			PlottingSettings.listOfHeatmaps.get(i).instanceNameDenominator = getVariableInstance(PlottingSettings.listOfHeatmaps.get(i).agentRatio.denominator,PlottingSettings.listOfHeatmaps.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps.get(i).Filter1, PlottingSettings.listOfHeatmaps.get(i).Filter2);
			
		}
		
		
	}
		
	
	}





public void mergeHistogramList(){
	
for(int i=0; i< PlottingSettings.listOfHistograms.size();i++ ){
		
		
		if(PlottingSettings.listOfHistograms.get(i).isVariable){
			
			PlottingSettings.listOfHistograms.get(i).instanceName = getVariableInstance(PlottingSettings.listOfHistograms.get(i).variable,PlottingSettings.listOfHistograms.get(i).histBelongsTo, PlottingSettings.listOfHistograms.get(i).Filter1, PlottingSettings.listOfHistograms.get(i).Filter2);
			
		}else{
			
			PlottingSettings.listOfHistograms.get(i).instanceName = getVariableInstance(PlottingSettings.listOfHistograms.get(i).agentRatio.numerator,PlottingSettings.listOfHistograms.get(i).histBelongsTo, PlottingSettings.listOfHistograms.get(i).Filter1, PlottingSettings.listOfHistograms.get(i).Filter2);
			
			PlottingSettings.listOfHistograms.get(i).instanceNameDenominator = getVariableInstance(PlottingSettings.listOfHistograms.get(i).agentRatio.denominator,PlottingSettings.listOfHistograms.get(i).histBelongsTo, PlottingSettings.listOfHistograms.get(i).Filter1, PlottingSettings.listOfHistograms.get(i).Filter2);
			
		}
		
		
	}
		
		
		
	}



public void mergeBoxplotList(){
	
	for(int i=0; i< PlottingSettings.listOfBoxplots.size();i++ ){
		

		if(PlottingSettings.listOfBoxplots.get(i).isVariable){
			
			PlottingSettings.listOfBoxplots.get(i).instanceName = getVariableInstance(PlottingSettings.listOfBoxplots.get(i).variable,PlottingSettings.listOfBoxplots.get(i).histBelongsTo, PlottingSettings.listOfBoxplots.get(i).Filter1, PlottingSettings.listOfBoxplots.get(i).Filter2);
			
		}else{
			
			PlottingSettings.listOfBoxplots.get(i).instanceName = getVariableInstance(PlottingSettings.listOfBoxplots.get(i).agentRatio.numerator,PlottingSettings.listOfBoxplots.get(i).histBelongsTo, PlottingSettings.listOfBoxplots.get(i).Filter1, PlottingSettings.listOfBoxplots.get(i).Filter2);
			
			PlottingSettings.listOfBoxplots.get(i).instanceNameDenominator = getVariableInstance(PlottingSettings.listOfBoxplots.get(i).agentRatio.denominator,PlottingSettings.listOfBoxplots.get(i).histBelongsTo, PlottingSettings.listOfBoxplots.get(i).Filter1, PlottingSettings.listOfBoxplots.get(i).Filter2);
			
		}
		
		
	}
		

		
	}




public void mergeCorrelationListList(){
		
		for(int i=0; i< PlottingSettings.listOfCorrelation.size();i++ ){
	
			PlottingSettings.listOfCorrelation.get(i).instanceName1 =  getVariableInstance(PlottingSettings.listOfCorrelation.get(i).variable1,PlottingSettings.listOfCorrelation.get(i).histBelongsTo, PlottingSettings.listOfCorrelation.get(i).Filter1, PlottingSettings.listOfCorrelation.get(i).Filter2);
			PlottingSettings.listOfCorrelation.get(i).instanceName2 =  getVariableInstance(PlottingSettings.listOfCorrelation.get(i).variable2,PlottingSettings.listOfCorrelation.get(i).histBelongsTo, PlottingSettings.listOfCorrelation.get(i).Filter1, PlottingSettings.listOfCorrelation.get(i).Filter2);
		
		}
		

	}





public void mergeHeatmaps2VList(){
	
	for(int i=0; i< PlottingSettings.listOfHeatmaps2V.size();i++ ){
		
		
		
		if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable1){
			
			PlottingSettings.listOfHeatmaps2V.get(i).instanceName1 =  getVariableInstance(PlottingSettings.listOfHeatmaps2V.get(i).variable1,PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps2V.get(i).Filter1, PlottingSettings.listOfHeatmaps2V.get(i).Filter2);
			
		}else{
			
			PlottingSettings.listOfHeatmaps2V.get(i).instanceName1 =  getVariableInstance(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio1.numerator,PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps2V.get(i).Filter1, PlottingSettings.listOfHeatmaps2V.get(i).Filter2);
			PlottingSettings.listOfHeatmaps2V.get(i).instanceNameDenominator1 =  getVariableInstance(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio1.denominator,PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps2V.get(i).Filter1, PlottingSettings.listOfHeatmaps2V.get(i).Filter2);
		}
		
		
		
	if(PlottingSettings.listOfHeatmaps2V.get(i).isVariable2){
			
			PlottingSettings.listOfHeatmaps2V.get(i).instanceName2 =  getVariableInstance(PlottingSettings.listOfHeatmaps2V.get(i).variable2,PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps2V.get(i).Filter1, PlottingSettings.listOfHeatmaps2V.get(i).Filter2);
			
		}else{
			
			PlottingSettings.listOfHeatmaps2V.get(i).instanceName2 =  getVariableInstance(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio2.numerator,PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps2V.get(i).Filter1, PlottingSettings.listOfHeatmaps2V.get(i).Filter2);
			PlottingSettings.listOfHeatmaps2V.get(i).instanceNameDenominator2 =  getVariableInstance(PlottingSettings.listOfHeatmaps2V.get(i).agentRatio2.denominator,PlottingSettings.listOfHeatmaps2V.get(i).histBelongsTo, PlottingSettings.listOfHeatmaps2V.get(i).Filter1, PlottingSettings.listOfHeatmaps2V.get(i).Filter2);
		}
			
		
		
	}
		
	}




public String getVariableInstance(Variable variable, String agent, String filter1, String filter2){
	

	
	for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
		
		if(agent.equals(PlottingSettings.listOfAgentsVariableInstances.get(i).agentName)){
			
				for(int j=0; j<PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size();j++){
					
					
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).isVariable && (PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).variable.name.equals(variable.name))){
						
						if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1.equals(filter1) &&PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2.equals(filter2)){
							
							return PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName;
							
						}else if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter1.equals(filter1) &&PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).whichFilter2.equals(filter2)){
							
							return PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).instanceName;
							
						}
						
						
						
					}
					
					
					
				}
				
				
				
				
				PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.add((new PlottingSettings()).new VariableInstance("temp_"+variable.name,agent, variable, "mean",filter1,filter2, "No Partitioning", "No"));
				
				PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size()-1).isSpecialVariableInstance = true;
				
				return "temp_"+variable.name;
				
				
		}
		
		
		
		
	}
	

	return "";
	
	

}


public void deleteTempVarInstances(){
	
	
	for(int i=0; i < PlottingSettings.listOfAgentsVariableInstances.size();i++){
		
		
				for(int j=0; j<PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.size();j++){
	
					if(PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.get(j).isSpecialVariableInstance){
						
						
						PlottingSettings.listOfAgentsVariableInstances.get(i).listOfVariableInstances.remove(j);
						j--;
						
						
					}
				
				
				}
				
	}

}
	

}
