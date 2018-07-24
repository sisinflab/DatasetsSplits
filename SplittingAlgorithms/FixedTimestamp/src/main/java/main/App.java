package main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import splits.TemporalSplitWithConstraints;


public class App {

	// Dateset path
	private static final String DATASETPATH = "datasetPath";;
	
	
	//Path 
	private static final String SPLITPATH = "splitPath";
	
	//Splitter
	private static final String SPLITTER = "splitter";	
	
	// Minimum number of ratings in the training set
	private static final String MINTRAIN = "minTrain";
	
	// Minimum number of ratings in the test set
	private static final String MINTEST  = "minTest";
	
	
	public static void main(String[] args) throws Exception {
		
        CommandLine cl = getCommandLineOptions(args);
        if (cl == null) {
            System.out.println("Error in arguments");
            return;
        } 
        
        // Mandatory arguments for each approach
        String datasetPath = cl.getOptionValue(DATASETPATH);
        String splitter = cl.getOptionValue(SPLITTER);
        String path = cl.getOptionValue(SPLITPATH);

              
        switch(splitter) {
        	case "TemporalSplitterWithConstraints":
        	
        		String minTrainS = cl.getOptionValue(MINTRAIN);
        		String minTestS = cl.getOptionValue(MINTEST);
        		
        		int minTrain = 15;
        		int minTest = 5;
        		
        		if(minTrainS != null) {
        			minTrain = Integer.parseInt(minTrainS);
        		}
        		
        		if(minTestS != null) {
        			minTest = Integer.parseInt(minTestS);
        		}
        		
        		TemporalSplitWithConstraints temporalsplit = new TemporalSplitWithConstraints(minTrain, minTest);
        		temporalsplit.launchTemporalSplitWithConstraints(datasetPath, path);
        		
        		break;
        		
        }
		
	}
	
	
	private static CommandLine getCommandLineOptions(String[] args) {
        Options options = new Options();

        // Dataset path
        Option datasetPath = new Option("dp",DATASETPATH, true, "Dataset path");
        datasetPath.setRequired(true);
        options.addOption(datasetPath);

        // training and test set folder path
        Option outfile = new Option("p", SPLITPATH, true, "Training and test set folder path");
        outfile.setRequired(true);
        options.addOption(outfile);

        // Splitter to user
        Option splitter = new Option("sp", SPLITTER, true, "Name of splitter to deploy");
        splitter.setRequired(true);
        options.addOption(splitter);
        
        // Minimum number of ratings in training set
        Option minTrain = new Option("mtr", MINTRAIN, true, "Set minimum number of ratings in the training set");
        minTrain.setRequired(false);
        options.addOption(minTrain);
        
        // Minimum number of ratings in test set
        Option minTest = new Option("mte", MINTEST, true, "Set minimum number of ratings in the test set");
        minTest.setRequired(false);
        options.addOption(minTest);
        
        

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            return null;
        }
        return cmd;

    }
	
}