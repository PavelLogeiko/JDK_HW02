package Server;

import java.io.*;

public class Repository {
    private final String PATH = "/Users/logeyko/IdeaProjects/JDK_HW02/src/History";
    public void writeToFile(String userData){
        try{
            File file = new File(PATH);
            StringBuilder sb = new StringBuilder();
            if(!file.exists()){
                file.createNewFile();
            }
            sb.append(userData);
            sb.append(System.lineSeparator());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
            bw.write(sb.toString());
            bw.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public String readFile(){

        try(BufferedReader br = new BufferedReader(new FileReader(PATH))){
            StringBuilder sb = new StringBuilder();
            String userData = br.readLine();
            while(userData != null){
                sb.append(userData);
                sb.append(System.lineSeparator());
                userData = br.readLine();
            }
            return sb.toString();
        }catch(IOException ex){
            System.out.println("Файла не существует");
            return null;
        }
    }
}