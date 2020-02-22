package prv.pgergely.ctsdata.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * This class can transform the given CSV content according to the given columns
 * @author PapGergely
 */
@Component
public class CsvTransformer {
    
    private String[] columns;
    private String[][] rows;
    private byte[] stream;
    
    public void setStream(InputStream stream) {
        try {
            this.stream = streamToByteArray(stream);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void generateStringMatrixFromCsv(String filePath){
        try(BufferedReader bfr = new BufferedReader(new FileReader(new File(filePath)))){
            long rowCnt = Files.lines(Paths.get(filePath)).count();
            rows = new String[(int)rowCnt][];
            if(bfr.ready()){
                columns = bfr.readLine().split(",");
            }
            int row = 0;
            while(bfr.ready()){
                String line = bfr.readLine();
                rows[row] = line.split(",");
                row++;
            }
            
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    public void generateStringMatrixFromCsv(){
        try(BufferedReader bfr = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(stream)))){
            int cnt = getCsvRowNumber();
            rows = new String[cnt][0];
            if(bfr.ready()){
                columns = bfr.readLine().split(",");
            }
            System.out.println(Arrays.toString(columns));
            int row = 0;
            while(bfr.ready()){
                String line = bfr.readLine();
                rows[row++] = replaceCommaWithinDoubleQoute(line).split(",", -1);
            }
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    private int getCsvRowNumber() throws IOException{
        try(BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(stream))){
            byte[] buffer = new byte[1024];
            int cnt = 0;
            int read = 0;
            while((read = is.read(buffer)) != -1){
                for (int i = 0; i < read; i++) {
                    if(buffer[i] == '\n'){
                        cnt++;
                    }
                }
            }

            return cnt;
        }catch(IOException ex){
            System.out.println(ex);
        }
        
        return 0;
    }
    
    private int getCsvRowNumberAdv() throws IOException{
        InputStream tempStream = new ByteArrayInputStream(stream);
        ByteBuffer buffer1 = ByteBuffer.allocate(tempStream.available());
        try(ReadableByteChannel channel = Channels.newChannel(tempStream)){
            channel.read(buffer1);
            buffer1.flip();
            int cnt = 0;
            while(buffer1.hasRemaining()){
                byte character = buffer1.get();
                if(character == '\n'){
                    cnt++;
                }
            }
            
            return cnt;
        }catch(IOException ex){
            System.out.println(ex);
        }
        
        return 0;
    }
    
    private byte[] streamToByteArrayWithChannel(InputStream stream)throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(stream.available());
        ReadableByteChannel streamChannel = Channels.newChannel(stream);
        streamChannel.read(buffer);
        buffer.flip();

        return buffer.array();
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
    
    private InputStream copyStream(InputStream origStream) throws IOException{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int read;
        byte[] buffer = new byte[2048];
        while((read=origStream.read(buffer,0,buffer.length)) != -1){
            outStream.write(buffer);
        }
        outStream.flush();
        
        return new ByteArrayInputStream(outStream.toByteArray());
    }
    
    public ByteArrayOutputStream filterCsvContentByColumnList(List<String> columnList) throws IOException{
        byte[][] remainingCsvRows = new byte[rows.length][0];
        int[] idxArray = new int[columnList.size()];
        int cIdx = 0;
        for (String customCol : columnList){
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
        for (int i = 0; i < rows.length; i++) {
        	if(rows[i]!=null && rows[i].length!=0) {
        		for (int j = 0; j < idxArray.length; j++) {
        			int idx = idxArray[j];
        			byte[] separator = ((j+1) != idxArray.length) ? new byte[]{44} : new byte[]{10};
        			if(idx>=0){
        				byte[] actualCol = String.valueOf(rows[i][idx]).getBytes(StandardCharsets.UTF_8);
        				remainingCsvRows[i] = mergeArray(remainingCsvRows[i], mergeArray(actualCol, separator));
        			}else{
        				remainingCsvRows[i] = mergeArray(remainingCsvRows[i], mergeArray(new byte[0], separator));
        			}
        		}
        	}
        }
        ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
        for (int i = 1; i < remainingCsvRows.length; i++) {
            streamOut.write(remainingCsvRows[i]);
        }
        
        return streamOut;
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
    
    public String replaceCommaWithinDoubleQoute(String line){
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
            
            if(!isDoubleQuote && end) {
            	
            }
            
        }
        
        return new String(lineArray);
    }
    
}