/**
 * 版权所有     							 
 *																		 
 * 文件名称：MD5Util.java													 
 * 文件标识：MD5加密类
 * 内容摘要： 
 * 其它说明： 
 * 当前版本：1.0													
 * 作        者：
 * 完成日期：
 * 修改记录：
 * 修改日期：															 
 * 版   本  号：															 
 * 修   改  人：															 
 * 修改内容：
 * 备          注： 
 */

package com.siz.adobeair;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/** 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合 */
	private static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//	protected static MessageDigest messagedigest = null;
//	static {
//		try {
//			messagedigest = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException nsaex)
//		{
//
//		}
//	}

	private static MessageDigest getMessageDigest(){
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 函数名称：getMD5String
	 * 功能描述：生成字符串的md5校验值
	 * 输入参数：@param  s
	 * 输出参数：无 
	 * 返  回   值：MD5加密字符串
	 * 备          注：无
	 */
	public static String getMd5String(String s) {
		try {
			return getMd5String(s.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 函数名称：checkPassword
	 * 功能描述：判断字符串的md5校验码是否与一个已知的md5码相匹配
	 * 输入参数：@param password  要校验的字符串
	 *          @param md5PwdStr 已知的md5校验码  
	 * 输出参数：无 
	 * 返  回   值：true  false
	 * 备          注：无
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMd5String(password);
		return s.equals(md5PwdStr);
	}

	/**
	 * 函数名称：getFileMD5String
	 * 功能描述：生成文件的md5校验值
	 * 输入参数：@param file
	 * 输出参数：无 
	 * 返  回   值：MD5校验码
	 * 备          注：无
	 */
	private static String getFileMd5String(File file) throws Exception {
		InputStream fis;
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest messageDigest = getMessageDigest();
		while ((numRead = fis.read(buffer)) > 0) 
		{
			messageDigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messageDigest.digest());
	}

	public static String getFileMd5(File file){
		try {
			return getFileMd5String(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getFileMd5(String fileName){
		return getFileMd5(new File(fileName));
	}

	/**
	 * 函数名称：getFileMD5String_old
	 * 功能描述：生成文件的md5校验值,不推荐使用
	 * 输入参数：@param file
	 * 输出参数：无 
	 * 返  回   值：无
	 * 备          注：JDK1.4中不支持以MappedByteBuffer类型为参数update方法，并且网上有讨论要慎用MappedByteBuffer，
	 *       原因是当使用 FileChannel.map 方法时，MappedByteBuffer 已经在系统内占用了一个句柄， 而使用
	 *       FileChannel.close 方法是无法释放这个句柄的，且FileChannel有没有提供类似 unmap 的方法，
	 *       因此会出现无法删除文件的情况。
	 */
	public static String getFileMD5String_old(File file) throws Exception {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MessageDigest messageDigest = getMessageDigest();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messageDigest.update(byteBuffer);
		in.close();
		return bufferToHex(messageDigest.digest());
	}

	/**
	 * 函数名称：getMD5String
	 * 功能描述：
	 * 输入参数：无
	 * 输出参数：无 
	 * 返  回   值：无
	 * 备          注：无
	 */
	private static String getMd5String(byte[] bytes) throws Exception {
		MessageDigest messageDigest = getMessageDigest();
		messageDigest.update(bytes);
		return bufferToHex(messageDigest.digest());
	}

	/**
	 * 函数名称：bufferToHex
	 * 功能描述：
	 * 输入参数：无
	 * 输出参数：无 
	 * 返  回   值：无
	 * 备          注：无
	 */
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	/**
	 * 函数名称：bufferToHex
	 * 功能描述：
	 * 输入参数：无
	 * 输出参数：无 
	 * 返  回   值：无
	 * 备          注：无
	 */
	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) 
		{
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	/**
	 * 函数名称：appendHexPair
	 * 功能描述：
	 * 输入参数：无
	 * 输出参数：无 
	 * 返  回   值：无
	 * 备          注：无
	 */
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = HEX_DIGITS[(bt & 0xf0) >> 4];
		/** 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同 */
		char c1 = HEX_DIGITS[bt & 0xf];
		/** 取字节中低 4 位的数字转换 */
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
}