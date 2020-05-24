package prv.pgergely.ctsdata.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
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
import prv.pgergely.ctsdata.utility.CsvRefiner;
import prv.pgergely.ctsdata.utility.TableInsertValues;

@Service
public class DataPreparation {
	
	private Logger logger = LogManager.getLogger(DataPreparation.class);
	
	@Autowired
	private CtsDataConfig config;
	
	@Autowired
	private GtfsTablesService srvc;
	
	@Autowired
	private CsvRefiner transform;
	
	@PostConstruct
	public void  init() {
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
        logger.info("File extraction is done!");
        zis.closeEntry();
        zis.close();
    }
	
	private void copyCsvContent(String fileName, InputStream stream) throws CannotGetJdbcConnectionException, SQLException, IOException {
        String tableName = fileName.replace(".txt", "");
        logger.info("Table next: "+tableName);
        TableInsertValues value = TableInsertValues.getInsertValueByTableName(tableName);
        List<String> columns = value.getColNames();
        transform.setDefaultColumns(columns);
        transform.setStream(stream);
        ByteArrayOutputStream outStream = transform.generateNormalizedStreamFromCsv();
        String joinedCols = columns.stream().collect(Collectors.joining(",","(",")"));
        String copy = "COPY "+tableName+" "+joinedCols+" FROM STDIN WITH CSV DELIMITER ',' QUOTE '\"' ";
        try(InputStream inStream = new ByteArrayInputStream(outStream.toByteArray())){
        	srvc.copy(copy, inStream);
        }
        logger.info(tableName+" is done.");
	}
    
    public void clearTables() {
    	srvc.clearTables();
    }
}
