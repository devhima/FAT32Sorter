//By DevHima
package com.devhima.fat32sorter;

import java.io.*;
import java.util.*;

public class FATSorter
{
	//sort files function
	public static void SortFiles(boolean backupOnMobile){
		//get internal & external memory path
		String sdPath = System.getenv("SECONDARY_STORAGE");
		String bakPath = System.getenv("SECONDARY_STORAGE");
		if(backupOnMobile == true){
			bakPath = System.getenv("EXTERNAL_STORAGE");
		}
		//copying files to backup path and storing paths into list array
		List<String> lst = copyFolder(new File(sdPath), new File(bakPath + "/fattmp"));
		//coping files back from backup path to sdcard
		createDirs(new File(bakPath + "/fattmp"), new File(sdPath));
		copyFolderFromArray(lst, new File(sdPath));
		try
		{
			deletePath(new File(bakPath + "/fattmp"));
		}
		catch (IOException e)
		{}
	}
	//copy file function (move)
	private static void copyFile(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}

		source.delete();
	}
	
	//copy folder function
	private static List<String> copyFolder(File src, File dest) {
		List<String> destFiles = new ArrayList<String>();
		boolean mkdr = false;
		// checks
		if(src==null || dest==null)
			return null;
		if(!src.isDirectory())
			return null;
		if(dest.exists()){
			if(!dest.isDirectory()){
				//System.out.println("destination not a folder " + dest);
				return null;
			}
		} else {
			mkdr = true;
		}

		if(src.listFiles()==null || src.listFiles().length==0)
			return null;

		for(File file: src.listFiles()){
			if(mkdr == true){
				dest.mkdir();
			}
			File fileDest = new File(dest, file.getName());
			//System.out.println(fileDest.getAbsolutePath());
			if(file.isDirectory()){
				copyFolder(file, fileDest);
				file.delete();
			}else{
				if(fileDest.exists())
					continue;

				try {
					copyFile(file, fileDest);
					destFiles.add(fileDest.getPath());
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		//sorting files a-z
		Collections.sort(destFiles);
		return destFiles;
	}
	
	//create dirs before copying
	private static void createDirs(File src, File dest) {
		
		// checks
		if(src==null || dest==null)
			return;
		if(!src.isDirectory())
			return;
		if(dest.exists()){
			if(!dest.isDirectory()){
				//System.out.println("destination not a folder " + dest);
				return;
			}
		} else {
			dest.mkdir();
		}

		if(src.listFiles()==null || src.listFiles().length==0)
			return;

		for(File file: src.listFiles()){
			File fileDest = new File(dest, file.getName());
			//System.out.println(fileDest.getAbsolutePath());
			if(file.isDirectory()){
				copyFolder(file, fileDest);
			}else{
				if(fileDest.exists())
					continue;
			}
		}
	}
	
	//coping folder from array
	private static void copyFolderFromArray(List<String> lstFiles, File dest) {
		// checks
		if(dest==null)
			return;
		if(dest.exists()){
			if(!dest.isDirectory()){
				//System.out.println("destination not a folder " + dest);
				return;
			}
		} else {
			dest.mkdir();
		}

		if(lstFiles.size()==0)
			return;

		for(String filePath: lstFiles){
			File file = new File(filePath);
			File fileDest = new File(dest, file.getName());
			//System.out.println(fileDest.getAbsolutePath());
			if(file.isDirectory()){
			}else{
				if(fileDest.exists())
					continue;

				try {
					copyFile(file, fileDest);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
	}
	
	//delete path
	private static void deletePath(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				deletePath(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}
}
