package cn.edu.buaa.soft.eureka.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class Utils {

	/**
	 * ��������
	 * ������
	 * 1. ȡ��SD�����ݿ��·��
	 * 2. ��SD���ϴ����ļ���
	 * 3. ȡ��SD��������Ϣ
	 * 4. Log��ӡ������ͨ��CatLog���ԣ�
	 */

	/**
	 * ��ȡSdCard·��
	 * 
	 * @return /sdCard
	 */
	public static String getExternalStoragePath() {

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		// ��ȡSdCard״̬
		String state = android.os.Environment.getExternalStorageState();
		// �ж�SdCard�Ƿ���ڲ����ǿ��õ�
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
			return android.os.Environment.getExternalStorageDirectory()
					.getAbsolutePath();
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		Log.d(Constants.TAG,"SD Card is not available!!");
		return null;
	}

	/**
	 * @return /sdCard/Eureka
	 */
	public static String getEurekaPath() {
		return getExternalStoragePath() + File.separator + Constants.dbDirName;

	}

	/**
	 * ��ָ��·���´����ļ���
	 * 
	 * @param parentPath
	 *            �ļ��еĸ�·��
	 * @param folderNmae
	 *            �ļ�����
	 * @return true �����ɹ� false ����ʧ��
	 */
	public static boolean createFolder(String parentPath, String folderName) {
		String filePath = parentPath + File.separator + folderName;
		try {
			File dirFile = new File(filePath);
			String state = android.os.Environment.getExternalStorageState();
			// �ж�SdCard�Ƿ���ڲ����ǿ��õ�
			if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
				Log.d(Constants.TAG,"sd card is available");
				Log.d(Constants.TAG,"SDCard's status : "+Environment.getExternalStorageState());
			}
			
			if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
				boolean isCreated = dirFile.mkdirs();               // false
				if (isCreated) {
					// System.out.println( " ok:�����ļ��гɹ��� " );
					Log.d(Constants.TAG, filePath + "created.");
					return true;
				} else {
					// System.out.println( " err:�����ļ���ʧ�ܣ� " );
					Log.d(Constants.TAG, filePath + " failed to create.");
					return false;
				}
			}
			else{
				Log.d(Constants.TAG,"DB Dir have exited");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
	}
	

	/**
	 * ��ȡ�洢����ʣ����������λΪ�ֽ�
	 * 
	 * @param filePath
	 * @return availableSpare
	 */

	public static long getAvailableStore(String filePath) {

		// ȡ��sdcard�ļ�·��
		StatFs statFs = new StatFs(filePath);

		// ��ȡblock��SIZE
		long blocSize = statFs.getBlockSize();

		// ��ȡBLOCK����
		// long totalBlocks = statFs.getBlockCount();

		// ��ʹ�õ�Block������
		long availaBlock = statFs.getAvailableBlocks();

		// long total = totalBlocks * blocSize;

		long availableSpare = availaBlock * blocSize;

		return availableSpare;

	}
		
	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @throws IOException
	 */
	public File creatSDFile(String fileName) throws IOException {
		File file = new File(fileName);
		System.out.println("chenxu+utils: "+fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * ��һ��InputStream���������д�뵽SD����
	 */
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{						
			file = creatSDFile(path + fileName);			
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			while((input.read(buffer)) != -1){
				output.write(buffer);
			}
			output.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static void myLog(String meg){
		Log.d(Constants.TAG,meg);
	}
	public static void LogMegs(String[] strs){
		for(int i = 0; i<strs.length; i++){
			Log.d(Constants.TAG,strs[i]);
		}
	}
	public static void LogMegs(List<String> list){
		for(int i = 0; i<list.size(); i++){
			Log.d(Constants.TAG,list.get(i));
		}
	}
	
	

	public static void main(String[] args) {
		// for testing

	}

}
