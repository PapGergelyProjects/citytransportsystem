package prv.pgergely.ctsdata.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
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

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
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
	
	private Map<String, TableInsertValues> tableList;
	
	@PostConstruct
	public void  init() {
		tableList = Arrays.asList(TableInsertValues.values()).stream().collect(Collectors.toMap(TableInsertValues::getTableName, v -> v));
	}
	
    public void extractZipFile(byte[] zipStream) throws IOException, CannotGetJdbcConnectionException, SQLException{
    	logger.info("Clear tables");
    	clearTables();
    	logger.info("Starting extracting files from zip...");
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipStream));
        ZipEntry zipEntry;
        while((zipEntry = zis.getNextEntry()) != null){
            String fileName = zipEntry.getName();
            if(config.getTables().contains(fileName.replace(".txt", ""))){
                copyCsvContent(fileName, zis);
            }
        }
        logger.info("File extraction done!");
        zis.closeEntry();
        zis.close();
    }
	
	private void copyCsvContent(String fileName, InputStream stream) throws CannotGetJdbcConnectionException, SQLException, IOException {
        String tableName = fileName.replace(".txt", "");
        logger.info("Table next: "+tableName);
        TableInsertValues value = tableList.get(tableName);
        List<String> columns = value.getColNames();
        String joinedCols = columns.stream().collect(Collectors.joining(",","(",")"));
        String copy = "COPY "+tableName+" "+joinedCols+" FROM STDIN WITH CSV DELIMITER ',' HEADER";
        srvc.copy(copy, stream);
        logger.info(tableName+" is done.");
	}
    
    public void clearTables() {
    	srvc.clearTables();
    }
}
