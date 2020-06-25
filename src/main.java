import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main
{
    public static void main(String [] args) throws IOException
    {
        File f = new File("information.txt");
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Would you like to use previous settings?(y/n): ");
        String ans = keyboard.next();

        if(ans.equals("n"))
        {
            if(f.delete())
                f.createNewFile();
            PrintWriter myWriter = new PrintWriter(f);
            System.out.print("Enter the new Directory: ");
            ans = keyboard.next();
            myWriter.write(ans+"\n");
            myWriter.close();
        }

        Scanner sc = new Scanner(f);

        String path = sc.nextLine();
        path = path.replaceAll(Pattern.quote("\\"),  Matcher.quoteReplacement("\\\\"));
        System.out.println(path);

        new File(path+"\\Pdf").mkdir();
        new File(path+"\\Text Files").mkdir();
        new File(path+"\\Images").mkdir();
        new File(path+"\\Word Documents").mkdir();

        f = new File(path+"\\");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
        ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));
        for(int x=0;x<names.size();x++)
        {
            System.out.println(names.get(x)+"    "+files.get(x));
            if(names.get(x).toLowerCase().endsWith(".jpg"))
            {
                String dir = ""+files.get(x);
                dir = dir.replaceAll(Pattern.quote("\\"),  Matcher.quoteReplacement("\\\\"));
                String [] arr = dir.split("\\\\");
                String newDir = "";
                for(int y=0;y<arr.length-1;y++)
                {
                    newDir+=arr[y]+"\\";
                }
                newDir+="Images\\\\"+arr[arr.length-1];
                System.out.println(newDir);
                files.get(x).renameTo(new File(newDir));
            }
            else if(names.get(x).toLowerCase().endsWith(".pdf"))
            {
                String dir = ""+files.get(x);
                dir = dir.replaceAll(Pattern.quote("\\"),  Matcher.quoteReplacement("\\\\"));
                String [] arr = dir.split("\\\\");
                String newDir = "";
                for(int y=0;y<arr.length-1;y++)
                {
                    newDir+=arr[y]+"\\";
                }
                newDir+="Pdf\\\\"+arr[arr.length-1];
                System.out.println(newDir);
                files.get(x).renameTo(new File(newDir));
            }
            else if(names.get(x).toLowerCase().endsWith(".txt"))
            {
                String dir = ""+files.get(x);
                dir = dir.replaceAll(Pattern.quote("\\"),  Matcher.quoteReplacement("\\\\"));
                String [] arr = dir.split("\\\\");
                String newDir = "";
                for(int y=0;y<arr.length-1;y++)
                {
                    newDir+=arr[y]+"\\";
                }
                newDir+="Text Files\\\\"+arr[arr.length-1];
                System.out.println(newDir);
                files.get(x).renameTo(new File(newDir));
            }
            else if(names.get(x).toLowerCase().endsWith(".doc") || names.get(x).toLowerCase().endsWith(".docx"))
            {
                String dir = ""+files.get(x);
                dir = dir.replaceAll(Pattern.quote("\\"),  Matcher.quoteReplacement("\\\\"));
                String [] arr = dir.split("\\\\");
                String newDir = "";
                for(int y=0;y<arr.length-1;y++)
                {
                    newDir+=arr[y]+"\\";
                }
                newDir+="Word Documents\\\\"+arr[arr.length-1];
                System.out.println(newDir);
                files.get(x).renameTo(new File(newDir));
            }
        }
    }
    private static void moveFile(String src, String dest ) {
        Path result = null;
        try {
            result =  Files.move(Paths.get(src), Paths.get(dest));
        } catch (IOException e) {
            System.out.println("Exception while moving file: " + e.getMessage());
        }
        if(result != null) {
            System.out.println("File moved successfully.");
        }else{
            System.out.println("File movement failed.");
        }
    }
}
