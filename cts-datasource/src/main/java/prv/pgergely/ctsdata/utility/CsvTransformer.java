package prv.pgergely.ctsdata.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class can transform the given CSV content according to the given columns
 * @author PapGergely
 */
@Component
public class CsvTransformer {
    
    private String[] columns;
    private InputStream stream;
    private List<String> defaultColumnList;
    
    private Logger logger = LogManager.getLogger(CsvTransformer.class);
    
    public void setStream(InputStream stream) {
    	this.stream = stream;
    }
    
    public void setDefaultColumns(List<String> defaultColumnList) {
    	this.defaultColumnList = defaultColumnList;
    }
    
    private byte[] streamToByteArray(InputStream stream)throws IOException{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int read;
        byte[] buffer = new byte[stream.available()];
        while((read=stream.read(buffer,0,buffer.length)) != -1){
            outStream.write(buffer);
        }
        outStream.flush();

        return outStream.toByteArray();
    }
    
    public ByteArrayOutputStream generateNormalizedStreamFromCsv() throws IOException{
    	BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
    	ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
    	if(bfr.ready()){
    		columns = bfr.readLine().split(",");
    	}
    	int[] columnSet = getColumnSet();
    	while(bfr.ready()){
    		String line = bfr.readLine();
    		String[] actualRow = replaceCommaWithinDoubleQoute(line).split(",", -1);
    		if(actualRow != null && actualRow.length != 0) {
    			byte[] remainingCsvRows = new byte[0];
    			for (int j = 0; j < columnSet.length; j++) {
    				int idx = columnSet[j];
    				byte[] separator = ((j+1) != columnSet.length) ? new byte[]{','} : new byte[]{'\n'};
    				if(idx >= 0){
    					byte[] actualCol = actualRow[idx].getBytes(StandardCharsets.UTF_8);
    					remainingCsvRows = mergeArray(remainingCsvRows, mergeArray(actualCol, separator));
    				}else{
    					remainingCsvRows = mergeArray(remainingCsvRows, mergeArray(new byte[0], separator));
    				}
    			}
    			streamOut.write(remainingCsvRows);
    		}
    	}
    	
    	return streamOut;
    }
    
    private String replaceCommaWithinDoubleQoute(String line){
        final byte doubleQute = '"';
        final byte comma = ',';
        boolean start = false;
        boolean end = false;
        byte[] lineArray = line.getBytes();
        for (int i = 0; i < lineArray.length; i++) {
            byte actualByte = lineArray[i];
            boolean isDoubleQuote = actualByte == doubleQute;
            if(isDoubleQuote && !start){
                start = true;
                end = false;
            }else if(isDoubleQuote && !end){
                start = false;
                end = true;
            }else if(actualByte == comma && start && !end){
                lineArray[i] = ';';
            }
        }
        
        return new String(lineArray);
    }
    
    private int[] getColumnSet() {
        int[] idxArray = new int[defaultColumnList.size()];
        int cIdx = 0;
        for (String customCol : defaultColumnList){
            boolean isNotIn = true;
            int idx = -1;
            for (int i = 0; i < columns.length; i++) {
                if(customCol.equals(columns[i])){
                    idxArray[cIdx++] = i;
                    isNotIn = false;
                }
                idx = cIdx;
            }
            if(isNotIn){
                idxArray[cIdx++] = idx*-1;
            }
        }
        
        return idxArray;
    }
    
    private byte[] mergeArray(byte[] old, byte[] actual){
        int size = old.length+actual.length;
        byte[] res = new byte[size];
        for (int i = 0; i < old.length; i++) {
            res[i] = old[i];
        }
        int idx = 0;
        for (int i = old.length; i < size; i++) {
            res[i] = actual[idx++];
        }
        
        return res;
    }
    
}