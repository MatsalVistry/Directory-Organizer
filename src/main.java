import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
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
        boolean extra = false;

        if(ans.equals("n"))
        {
            if(f.delete())
                f.createNewFile();
            PrintWriter myWriter = new PrintWriter(f);
            System.out.print("Enter the new Directory: ");
            ans = keyboard.next();
            myWriter.write(ans+"\n");

            while(true)
            {
                System.out.print("\nEnter a Folder Name(type done to exit): ");
                ans = keyboard.next();
                if(!ans.equals("done"))
                {
                    myWriter.write(ans+",");
                    System.out.print("Enter file extension(including the .) :");
                    myWriter.write(keyboard.next()+"\n");
                }
                else
                    break;
            }

            myWriter.close();
        }
        System.out.print("Would you like to place any remaining files in a separate folder?(y/n):");
        if(keyboard.next().toLowerCase().equals("y"))
            extra = true;
        Scanner sc = new Scanner(f);

        String path = sc.nextLine();
        path = path.replaceAll(Pattern.quote("\\"),  Matcher.quoteReplacement("\\\\"));
        System.out.println(path);

        HashMap<String,String> extensions = new HashMap<>();
        while(sc.hasNextLine())
        {
            String [] line = sc.nextLine().split(",");
            new File(path+"\\"+line[0]).mkdir();
            extensions.put(line[1],line[0]);
        }
        if(extra)
        {
            new File(path+"\\Extras").mkdir();
        }

        f = new File(path+"\\");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
        ArrayList<File> files = new ArrayList<>(Arrays.asList(f.listFiles()));

        if(names!=null)
        {
            for (int x = 0; x < names.size(); x++)
            {
                System.out.println(names.get(x) + "    " + files.get(x));
                boolean status = false;
                for(String key : extensions.keySet())
                {
                    if (names.get(x).toLowerCase().endsWith(key))
                    {
                        String dir = "" + files.get(x);
                        dir = dir.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));
                        String[] arr = dir.split("\\\\");
                        String newDir = "";
                        for (int y = 0; y < arr.length - 1; y++)
                        {
                            newDir += arr[y] + "\\";
                        }
                        newDir += extensions.get(key)+"\\\\" + arr[arr.length - 1];
                        System.out.println(newDir);
                        files.get(x).renameTo(new File(newDir));
                        status = true;
                        break;
                    }
                }
                if(extra && !status && !files.get(x).isDirectory())
                {
                    String dir = "" + files.get(x);
                    dir = dir.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));
                    String[] arr = dir.split("\\\\");
                    String newDir = "";
                    for (int y = 0; y < arr.length - 1; y++)
                    {
                        newDir += arr[y] + "\\";
                    }
                    newDir += "Extras\\\\" + arr[arr.length - 1];
                    System.out.println(newDir);
                    files.get(x).renameTo(new File(newDir));
                }
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
