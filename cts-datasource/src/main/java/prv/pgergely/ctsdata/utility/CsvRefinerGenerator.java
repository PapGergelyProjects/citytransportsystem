package prv.pgergely.ctsdata.utility;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CsvRefinerGenerator implements Supplier<ByteArrayOutputStream> {
	
	private String[] columns;
	private BufferedReader bfr;
	private List<String> defaultColumnList;
	private int cnt = 0;
	
	private Logger logger = LogManager.getLogger(CsvRefinerGenerator.class);
	
    public void setSupplierStream(InputStream stream) throws IOException {
    	bfr = new BufferedReader(new InputStreamReader(stream));
		if(bfr.ready()){
			columns = bfr.readLine().split(",");
		}
    }
    
    public void setDefaultColumns(List<String> defaultColumnList) {
    	this.defaultColumnList = defaultColumnList;
    }
	
	@Override
	public ByteArrayOutputStream get() {
		ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
		try {
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
				if(cnt == 100 || !bfr.ready()) {
					//System.out.println("50 record generated.");
					return streamOut;
				}else {
					cnt++;
					continue;
				}
				
			}
		}catch(IOException e) {
			logger.error(e.getMessage());
		}
		
		return null;
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
        for (int i = old.length, idx = 0; i < size; i++, idx++) {
            res[i] = actual[idx];
        }
        
        return res;
    }
	
}
