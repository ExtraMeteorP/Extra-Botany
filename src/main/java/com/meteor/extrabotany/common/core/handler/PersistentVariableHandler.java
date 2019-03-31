package com.meteor.extrabotany.common.core.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class PersistentVariableHandler {
	
	private static File cacheFile;
	
	private static final String TAG_CONTRIBUTORS = "contributors";
	private static final String TAG_CONTRIBUTORS_PREFIX = "contributor";
	private static final String TAG_CONTRIBUTORS_COUNT = "contributorCount";
	
	private static final String TAG_CONTRIBUTORSUUID = "contributorsuuid";
	private static final String TAG_CONTRIBUTORSUUID_PREFIX = "contributoruuid";
	private static final String TAG_CONTRIBUTORSUUID_COUNT = "contributoruuidCount";
	
	private static final String TAG_ADVERTISEMENT = "advertisements";
	private static final String TAG_ADVERTISEMENT_PREFIX = "ads";
	private static final String TAG_ADVERTISEMENT_COUNT = "advertisementCount";
	public static final List<String> contributors = new ArrayList<>();
	public static final List<String> contributorsuuid = new ArrayList<>();
	public static final List<String> advertisements = new ArrayList<>();
	
	public static void save() throws IOException {
		NBTTagCompound cmp = new NBTTagCompound();
		try {
			URL url = new URL("https://raw.githubusercontent.com/ExtraMeteorP/Extra-Botany/master/contributors.md");
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;
			while((str=r.readLine())!=null){ 
				contributors.add(str);
	        }
			r.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		int count = contributors.size();
		cmp.setInteger(TAG_CONTRIBUTORS_COUNT, count);
		NBTTagCompound contributorsCmp = new NBTTagCompound();
		for(int i = 0; i < count; i++) {
			NBTTagCompound contributorCmp = new NBTTagCompound();
			contributorCmp.setString(TAG_CONTRIBUTORS_PREFIX, contributors.get(i));
			contributorsCmp.setTag(TAG_CONTRIBUTORS_PREFIX + i, contributorCmp);
		}
		cmp.setTag(TAG_CONTRIBUTORS, contributorsCmp);
		
		try {
			URL url = new URL("https://raw.githubusercontent.com/ExtraMeteorP/Extra-Botany/master/contributorsuuid.md");
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;
			while((str=r.readLine())!=null){ 
				contributorsuuid.add(str);
	        }
			r.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		int countuuid = contributorsuuid.size();
		cmp.setInteger(TAG_CONTRIBUTORSUUID_COUNT, countuuid);
		NBTTagCompound contributorsuuidCmp = new NBTTagCompound();
		for(int i = 0; i < countuuid; i++) {
			NBTTagCompound contributoruuidCmp = new NBTTagCompound();
			contributoruuidCmp.setString(TAG_CONTRIBUTORSUUID_PREFIX, contributorsuuid.get(i));
			contributorsuuidCmp.setTag(TAG_CONTRIBUTORSUUID_PREFIX + i, contributoruuidCmp);
		}
		cmp.setTag(TAG_CONTRIBUTORSUUID, contributorsuuidCmp);

		try {
			URL url = new URL("https://raw.githubusercontent.com/ExtraMeteorP/RandomThings/master/advertisement.md");
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;
			while((str=r.readLine())!=null){ 
				advertisements.add(str);
	        }
			r.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		int countads = advertisements.size();
		cmp.setInteger(TAG_ADVERTISEMENT_COUNT, countads);
		NBTTagCompound adsCmp = new NBTTagCompound();
		for(int i = 0; i < countads; i++) {
			NBTTagCompound adCmp = new NBTTagCompound();
			adCmp.setString(TAG_ADVERTISEMENT_PREFIX, advertisements.get(i));
			adsCmp.setTag(TAG_ADVERTISEMENT_PREFIX + i, adCmp);
		}
		cmp.setTag(TAG_ADVERTISEMENT, adsCmp);
	}
	
	public static void load() throws IOException {
		NBTTagCompound cmp = new NBTTagCompound();
		
		int count = cmp.getInteger(TAG_CONTRIBUTORS_COUNT);
		contributors.clear();
		if(count > 0) {
			NBTTagCompound contributorsCmp = cmp.getCompoundTag(TAG_CONTRIBUTORS);
			for(int i = 0; i < count; i++) {
				NBTTagCompound contributorCmp = contributorsCmp.getCompoundTag(TAG_CONTRIBUTORS_PREFIX + i);
				if(contributorCmp.getString(TAG_CONTRIBUTORS_PREFIX) != null)
					contributors.add(contributorCmp.getString(TAG_CONTRIBUTORS_PREFIX));
			}
		}
		
		int countuuid = cmp.getInteger(TAG_CONTRIBUTORSUUID_COUNT);
		contributorsuuid.clear();
		if(countuuid > 0) {
			NBTTagCompound contributorsuuidCmp = cmp.getCompoundTag(TAG_CONTRIBUTORSUUID);
			for(int i = 0; i < countuuid; i++) {
				NBTTagCompound contributoruuidCmp = contributorsuuidCmp.getCompoundTag(TAG_CONTRIBUTORSUUID_PREFIX + i);
				if(contributoruuidCmp.getString(TAG_CONTRIBUTORSUUID_PREFIX) != null)
					contributorsuuid.add(contributoruuidCmp.getString(TAG_CONTRIBUTORSUUID_PREFIX));
			}
		}
		
		int countads = cmp.getInteger(TAG_ADVERTISEMENT_COUNT);
		advertisements.clear();
		if(countads > 0) {
			NBTTagCompound adsCmp = cmp.getCompoundTag(TAG_ADVERTISEMENT);
			for(int i = 0; i < countads; i++) {
				NBTTagCompound adCmp = adsCmp.getCompoundTag(TAG_ADVERTISEMENT_PREFIX + i);
				if(adCmp.getString(TAG_ADVERTISEMENT_PREFIX) != null)
					advertisements.add(adCmp.getString(TAG_ADVERTISEMENT_PREFIX));
			}
		}
		
		injectNBTToFile(cmp, getCacheFile());
	}
	
	public static void saveSafe() {
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setCacheFile(File f) {
		cacheFile = f;
	}

	private static File getCacheFile() throws IOException {
		if(!cacheFile.exists())
			cacheFile.createNewFile();

		return cacheFile;
	}

	private static NBTTagCompound getCacheCompound() throws IOException {
		return getCacheCompound(getCacheFile());
	}

	private static NBTTagCompound getCacheCompound(File cache) throws IOException {
		if(cache == null)
			throw new RuntimeException("No cache file!");

		try {
			return CompressedStreamTools.readCompressed(new FileInputStream(cache));
		} catch(IOException e) {
			NBTTagCompound cmp = new NBTTagCompound();
			CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(cache));
			return getCacheCompound(cache);
		}
	}

	private static void injectNBTToFile(NBTTagCompound cmp, File f) {
		try {
			CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
