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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.ctsdata.config.CtsDataConfig;
import prv.pgergely.ctsdata.utility.CsvRefiner;
import prv.pgergely.ctsdata.utility.Schema;
import prv.pgergely.ctsdata.utility.TableInsertValues;
import prv.pgergely.ctsdata.utility.WebSocketSessionHandler;

@Service
public class DataPreparation {
	
	private Logger logger = LogManager.getLogger(DataPreparation.class);
	
	@Autowired
	private CtsDataConfig config;
	
	@Autowired
	private GtfsTablesService srvc;
	
	@Autowired
	private CsvRefiner transform;
	
	@Autowired
	private Schema schema;
	
	@Autowired
	private MessagePublisher sender;
	
    public void extractZipFile(byte[] zipStream) throws IOException, CannotGetJdbcConnectionException, SQLException{
    	sender.publish(new SourceState(schema.getFeedId(), schema.getSchemaName().toUpperCase(), DataSourceState.UPDATING));
    	logger.info("Clear tables");
    	srvc.clearTables();
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
        logger.info("Init view materalization...");
        srvc.refreshMateralizedViews();
        logger.info("Materalization complete.");
        zis.closeEntry();
        zis.close();
        sender.publish(new SourceState(schema.getFeedId(), schema.getSchemaName().toUpperCase(), DataSourceState.ONLINE));
    }
	
	private void copyCsvContent(String fileName, InputStream stream) throws CannotGetJdbcConnectionException, SQLException, IOException {
        String tableName = fileName.replace(".txt", "");
        logger.info("Table next: "+tableName);
        TableInsertValues value = TableInsertValues.getInsertValueByTableName(tableName);
        List<String> columns = value.getColNames();
        ByteArrayOutputStream outStream = transform.generateNormalizedStreamFromCsv(stream, columns);
        String joinedCols = columns.stream().collect(Collectors.joining(",","(",")"));
        String copy = "COPY "+tableName+" "+joinedCols+" FROM STDIN WITH CSV DELIMITER ',' QUOTE '\"' ";
        try(InputStream inStream = new ByteArrayInputStream(outStream.toByteArray())){
        	srvc.copy(copy, inStream);
        }
        srvc.vacuumTable(tableName);
        logger.info(tableName+" is done.");
	}
    
    public void prepareCsvContentForTest(String fileName, InputStream stream) throws IOException {
    	String tableName = fileName.replace(".txt", "");
        TableInsertValues value = TableInsertValues.getInsertValueByTableName(tableName);
        List<String> columns = value.getColNames();
        ByteArrayOutputStream outStream = transform.generateNormalizedStreamFromCsv(stream, columns);
        String output = new String(outStream.toByteArray());
        
        System.out.println(output);
    }
}
