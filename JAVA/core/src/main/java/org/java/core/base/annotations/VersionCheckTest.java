package org.java.core.base.annotations;

import java.io.*;

public class VersionCheckTest {
    public static void main(String[] args){
        //version();

         //merge();


    }

    private static void merge() {
        File f1 = new File("C:\\Users\\a\\Desktop\\SN-sh-1.txt");
        File f2 = new File("C:\\Users\\a\\Desktop\\cmccsn.txt");

        File output = new File("C:\\Users\\a\\Desktop\\aa.txt");

        try {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));

            String s1 = null;
            String s2 = null;

            StringBuffer sb = new StringBuffer();
            while((s1 = br1.readLine()) != null){
                String[] lines = s1.split("  ");

                while((s2 = br2.readLine()) != null){
                    if(!s2.startsWith("CMCCSN")) continue;

                    for(int i=0;i<lines.length;i++){
                        if(i != lines.length-1){
                            if(lines[i].startsWith("MAC")){
                                sb.append(lines[i]);
                                sb.append("  ");
                                sb.append(s2.trim());
                                sb.append("  ");
                            }else{
                                sb.append(lines[i]);
                                sb.append("  ");
                            }
                        }else{
                            sb.append(lines[i]);
                            sb.append("\r\n");
                        }
                    }
                    break;
                }
            }

            FileOutputStream fos = new FileOutputStream(output);
            fos.write(sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @VersionCheckAnnotation(major_version = 2,minor_version = 3)
    private static void version(){
        System.out.println();
    }
}