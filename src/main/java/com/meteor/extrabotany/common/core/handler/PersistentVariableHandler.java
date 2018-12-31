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
import java.util.UUID;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class PersistentVariableHandler {
	
	private static File cacheFile;
	
	private static final String TAG_CONTRIBUTORS = "contributors";
	private static final String TAG_CONTRIBUTORS_PREFIX = "contributor";
	private static final String TAG_CONTRIBUTORS_COUNT = "contributorCount";
	public static final List<String> contributors = new ArrayList<>();
	
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
