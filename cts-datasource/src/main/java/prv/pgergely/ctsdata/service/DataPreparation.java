package prv.pgergely.ctsdata.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctsdata.config.CtsDataConfig;
import prv.pgergely.ctsdata.utility.TableInsertValues;

@Service
public class DataPreparation {
	
	private Logger logger = LogManager.getLogger(DataPreparation.class);
	
	@Autowired
	private CtsDataConfig config;
	
	@Autowired
	private GtfsTablesService srvc;
	
    public List<File> checkFolderForContent(){
    	File sourceFolder = new File(config.getTempDirectory());
        File[] fileList = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".zip") || name.toLowerCase().endsWith(".rar"));
        
        return Arrays.asList(fileList);
    }
	
    public void extractZipFile(String zipPath) throws IOException{
    	logger.info("Starting extracting files from zip...");
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
        ZipEntry zipEntry = zis.getNextEntry();
        byte[] buffer = new byte[1024];
        while(zipEntry != null){
            String fileName = zipEntry.getName();
            if(config.getTables().contains(fileName.replace(".txt", ""))){
            	File feedFile = new File(config.getTempDirectory()+fileName);
            	try(FileOutputStream f = new FileOutputStream(feedFile)){
            		int len;
            		while ((len = zis.read(buffer)) > 0) {
            			f.write(buffer, 0, len);
            		}
            	}catch(IOException e){
            		logger.error("File extraction faild: "+e);
            	}
            }
            zipEntry = zis.getNextEntry();
        }
        logger.info("File extraction done!");
        zis.closeEntry();
        zis.close();
    }
    
	public void createInsertFromFile() throws IOException{
        File[] listOfFiles = new File(config.getTempDirectory()).listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        Map<String, TableInsertValues> tableList = Arrays.asList(TableInsertValues.values()).stream().collect(Collectors.toMap(TableInsertValues::getTableName, v -> v));
        for (File file : listOfFiles) {
            List<String> fileContent = Files.readAllLines(Paths.get(config.getTempDirectory()+file.getName()), Charset.forName("utf-8"));
            String tableName = file.getName().replace(".txt", "");
            logger.info("Table next: "+tableName);
            TableInsertValues value = tableList.get(tableName);
            List<String> columns = value.getColNames(new ArrayList<>(Arrays.asList(fileContent.remove(0).split(","))));
            String joinedCols = columns.stream().collect(Collectors.joining(",","(",")"));
            while(0<fileContent.size()){
            	StringBuilder insert = new StringBuilder();
            	insert.append("INSERT INTO ").append(tableName).append(" ");
            	insert.append(joinedCols).append(" VALUES ");
            	StringJoiner joinValues = new StringJoiner(",");
            	int cnt = 10000; // Optimal for inserts
            	while(0<cnt){
            		if(fileContent.size()==0){
            			break;
            		}
            		String raw = replaceUselessComma(fileContent.remove(0));
            		String[] rows = raw.endsWith(",") ? (raw+"null").replace("\"", "").split(",") : raw.replace("\"", "").split(",");//
            		Map<String, String> content = new HashMap<>();
            		for (int j = 0; j < columns.size(); j++) {
            			String row = rows[j];
            			content.put(columns.get(j), row.isEmpty() ? "null" : row);
            		}
            		joinValues.add(value.getInsertValue(columns, content));
            		cnt--;
            		content = null;
            	}
            	insert.append(joinValues);
            	srvc.insertValues(insert.toString());
            }
            logger.info(tableName+" insert done.");
            Files.delete(Paths.get(config.getTempDirectory()+file.getName()));
        }
        srvc.refreshMateralizedViews();
	}
	
    private String replaceUselessComma(String rawStr){// Some GTFS file contains comma as an inner string separator, the problem with this, the comma is also the delimiter of the csv file...
        Matcher match = Pattern.compile("\"(?<line>[^\"]*)\"").matcher(rawStr);
        while(match.find()){
            String mtch = match.group("line");
            rawStr = rawStr.replace(mtch, mtch.replaceAll(",", ";"));
        }
        
        return rawStr;
    }
    
    public void clearTables() {
    	srvc.clearTables();
    }
}
