package com.taishan.swordsmanli.myapplication.utils;

import android.text.Editable;
import android.widget.EditText;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
	
	public static boolean isNull(String str) {
		if (str == null || str.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * Creates a hexadecimal <code>String</code> representation of the
     * <code>byte[]</code> passed. Each element is converted to a
     * <code>String</code> via the {@link Integer#toHexString(int)} and
     * separated by <code>" "</code>. If the array is <code>null</code>, then
     * <code>""<code> is returned.
     *
     * @param array the <code>byte</code> array to convert.
     * @return the <code>String</code> representation of <code>array</code> in
     * hexadecimal.
     */
    public static String toHexString(byte[] array) {

        String bufferString = "";

        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                String hexChar = Integer.toHexString(array[i] & 0xFF);
                if (hexChar.length() == 1) {
                    hexChar = "0" + hexChar;
                }
                bufferString += hexChar.toUpperCase(Locale.US) + " ";
            }
        }
        return bufferString;
    }

    private static boolean isHexNumber(byte value) {
        if (!(value >= '0' && value <= '9') && !(value >= 'A' && value <= 'F')
                && !(value >= 'a' && value <= 'f')) {
            return false;
        }
        return true;
    }

    /**
     * Checks a hexadecimal <code>String</code> that is contained hexadecimal
     * value or not.
     *
     * @param string the string to check.
     * @return <code>true</code> the <code>string</code> contains Hex number
     * only, <code>false</code> otherwise.
     * @throws NullPointerException if <code>string == null</code>.
     */
    public static boolean isHexNumber(String string) {
        if (string == null)
            throw new NullPointerException("string was null");

        boolean flag = true;

        for (int i = 0; i < string.length(); i++) {
            char cc = string.charAt(i);
            if (!isHexNumber((byte) cc)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * Creates a <code>byte[]</code> representation of the hexadecimal
     * <code>String</code> passed.
     *
     * @param string the hexadecimal string to be converted.
     * @return the <code>array</code> representation of <code>String</code>.
     * @throws IllegalArgumentException if <code>string</code> length is not in even number.
     * @throws NullPointerException     if <code>string == null</code>.
     * @throws NumberFormatException    if <code>string</code> cannot be parsed as a byte value.
     */
    public static byte[] hexString2Bytes(String string) {
        if (string == null)
            throw new NullPointerException("string was null");

        int len = string.length();

        if (len == 0)
            return new byte[0];
        if (len % 2 == 1)
            throw new IllegalArgumentException(
                    "string length should be an even number");

        byte[] ret = new byte[len / 2];
        byte[] tmp = string.getBytes();

        for (int i = 0; i < len; i += 2) {
            if (!isHexNumber(tmp[i]) || !isHexNumber(tmp[i + 1])) {
                throw new NumberFormatException(
                        "string contained invalid value");
            }
            ret[i / 2] = uniteBytes(tmp[i], tmp[i + 1]);
        }
        return ret;
    }

    /**
     * Creates a <code>byte[]</code> representation of the hexadecimal
     * <code>String</code> in the EditText control.
     *
     * @param editText the EditText control which contains hexadecimal string to be
     *                 converted.
     * @return the <code>array</code> representation of <code>String</code> in
     * the EditText control. <code>null</code> if the string format is
     * not correct.
     */
    public static byte[] getEditTextinHexBytes(EditText editText) {
        Editable edit = editText.getText();

        if (edit == null) {
            return null;
        }

        String rawdata = edit.toString();

        if (rawdata == null || rawdata.isEmpty()) {
            return null;
        }

        String command = rawdata.replace(" ", "").replace("\n", "");

        if (command.isEmpty() || command.length() % 2 != 0
                || isHexNumber(command) == false) {
            return null;
        }

        return hexString2Bytes(command);
    }

    public static byte[] getEditTextinHexBytes(String str) {

        if (str == null || str.isEmpty()) {
            return null;
        }

        String command = str.replace(" ", "").replace("\n", "");

        if (command.isEmpty() || command.length() % 2 != 0
                || isHexNumber(command) == false) {
            return null;
        }

        return hexString2Bytes(command);
    }

    /**
     * 十六进制转换字符串
     * 参数之间可有分隔符 如:[61 6C 6B]),
     *
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {

        //整理十六进制字符串(去除字符之间的分隔符,"FF",和"\n")
        hexStr = hexStr.replaceAll(" ", "").replaceAll("FF", "").replaceAll("\n", "");
        byte[] baKeyword = new byte[hexStr.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            hexStr = new String(baKeyword, "GB2312");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return hexStr;
    }

    /**
     * 将“UTF-8”原编码的字符串（任意），转换成“GB2312”原编码的16进制
     */
    public static String str2HexStr(String str) {
        StringBuffer bf = new StringBuffer();
        byte[] bytes = new byte[0];
        try {
            // 字符串按gb2312格式转byte数组
            bytes = str.getBytes("gb2312");
            // 循环数组
            for (byte b : bytes) {
                // 再用Integer每byte转换16进制输
                String tem_str = Integer.toHexString(b);
                // 整理字符串
                bf.append(tem_str.substring(tem_str.length() - 2, tem_str.length()).toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bf.toString().trim();
    }
    
    public static String intToIp(int i) {       
        
        return (i & 0xFF) + "." +  ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF) ;  
    }   

    private static String getShuttleScoreValue(String score) {
    	if (Utils.isNull(score)) {
    		return "";
    	}
	    try {
			BigDecimal b = new BigDecimal(score).setScale(0, BigDecimal.ROUND_HALF_UP);
			int minute = b.intValue()/60;
			int second = b.intValue() - minute*60;
			String strSecond = "";
			if (second < 10) {
				strSecond = "0" + second;
			} else {
				strSecond = String.valueOf(second);
			}
			return minute + "′" + strSecond + "″";
    	} catch (Exception e) {
    		e.printStackTrace();
    		return score;
    	}
    }
    


    public static int getCurrentTimeStamp() {
    	Calendar calendar = Calendar.getInstance();// 获取当前日期  
//		calendar.set(Calendar.HOUR, -12);
//	    calendar.set(Calendar.MINUTE, 0);  
//	    calendar.set(Calendar.SECOND, 0);  
	    int timestamp = Integer.valueOf(String.valueOf(calendar.getTimeInMillis()/1000));
	    return timestamp;
    }
    
    public static int getFromTimeStamp(int year, int month, int day) {
    	Calendar calendar = Calendar.getInstance();// 获取当前日期  
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.MONTH, month);
    	calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, -12);
	    calendar.set(Calendar.MINUTE, 0);  
	    calendar.set(Calendar.SECOND, 0);  
	    int timestamp = Integer.valueOf(String.valueOf(calendar.getTimeInMillis()/1000));
	    return timestamp;
    }
    
    public static int getToTimeStamp(int year, int month, int day) {
    	Calendar calendar = Calendar.getInstance();// 获取当前日期  
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.MONTH, month);
    	calendar.set(Calendar.DAY_OF_MONTH, day);
    	calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
	    int timestamp = Integer.valueOf(String.valueOf(calendar.getTimeInMillis()/1000));
	    return timestamp;
    }
    
    public static String unixTimeStampToDate(SimpleDateFormat simFormat, int unixTimeStamp) {
    	Long timestamp = Long.parseLong(String.valueOf(unixTimeStamp))*1000;
    	return simFormat.format(new Date(timestamp));
    }
    
    public static String getTime() {
    	SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
    	return simFormat.format(new Date());
    }
    
//    public static StuInfoBean getStuInfo(String str) {
//    	StuInfoBean bean = new StuInfoBean();
//    	if (isNull(str)) {
//    		return null;
//    	}
//    	String[] infoArray = str.split(",");
//    	if (infoArray != null && infoArray.length == 8) {
//    		bean.setStudentNumber(infoArray[0]);
//    		bean.setStudentName(infoArray[1]);
//    		bean.setSchoolYear(infoArray[2]);
//    		bean.setClassId(infoArray[3]);
//    		bean.setSex(infoArray[4]);
//    		bean.setBirthday(infoArray[5]);
//    		bean.setDuration(infoArray[6]);
//    		bean.setSchooltype(Integer.valueOf(infoArray[7]));
//    	} else {
//    		return null;
//    	}
//    	return bean;
//    }
    





    
    /**
     * 得到UTC时间戳
     * 如果获取失败，返回null
     * @return
     */
    public static long getUTCTimeStr() {
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, zoneOffset);

        return cal.getTimeInMillis()/1000 ;
    }

    /**
     * 得到UTC时间戳
     * 如果获取失败，返回null
     * @return
     */
    public static long getUTCTimeStrForMillis() {
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, zoneOffset);
       
        return cal.getTimeInMillis() ;
    }
    
    public static String trimSpace(String str) {
    	if (str == null) {
    		return "";
    	}
    	while(str.startsWith(" ")){  
    		str = str.substring(1, str.length()).trim();  
		}  
		while(str.endsWith(" ")){  
			str = str.substring(0, str.length()-1).trim();  
		}  
		
		return str;
    }
    
    public static final byte[] inputStream2Bytes(InputStream is) {
		try {
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			int len = 2048;
			byte[] buff = new byte[len];
			int rc = 0;
			while ((rc = is.read(buff, 0, len)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in2b = swapStream.toByteArray();
			return in2b;
		} catch (Exception e) {
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static final boolean writeToSdCard(File file, InputStream is) {
		return writeToSdCard(file, inputStream2Bytes(is));
	}

	public static final boolean writeToSdCard(File file, byte[] bytes) {
		try {
			if (file.exists() == false) {
				file.createNewFile();
			}
			FileOutputStream os = new FileOutputStream(file);
			os.write(bytes);
			os.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static final String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10)
					hex.append("0");
				hex.append(Integer.toHexString(b & 0xFF));
			}
			return hex.toString().toLowerCase();
		} catch (Exception e) {
		}
		return null;
	}

	public static final int parseInt(String date) {
		if (isNull(date)) {
			return -1;
		}
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		int year = 1970, month = 1, day = 1;
		Matcher matcher = Pattern.compile("\\d+").matcher(date);
		if (matcher.find())
			year = Integer.parseInt(matcher.group());
		if (matcher.find())
			month = Integer.parseInt(matcher.group());
		if (matcher.find())
			day = Integer.parseInt(matcher.group());
		calendar.set(year, month, day);
		int timestamp = Integer.valueOf(String.valueOf(calendar
				.getTimeInMillis() / 1000));
		return timestamp;
	}

//	public static final String parseTimeInMillis(int timestamp) {
//		if (timestamp < 1)
//			return "";
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(timestamp * 1000L);
//		return cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-"
//				+ cal.get(Calendar.DAY_OF_MONTH);
//	}
	
	public static final String parseTimeInMillis(int timestamp) {
        if (timestamp < 1)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND);
    }

    //通过出生日期获取年龄
	public static int getparseAge(String birthDayStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		Date birthDay = format.parse(birthDayStr);
		Calendar calBir = Calendar.getInstance();
		calBir.setTime(birthDay);

		//获取当前系统时间
		Calendar cal = Calendar.getInstance();
		//如果出生日期大于当前时间，则抛出异常
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		//取出系统当前时间的年、月、日部分
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		//将日期设置为出生日期
		cal.setTime(birthDay);
		//取出出生日期的年、月、日部分
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		//当前年份与出生年份相减，初步计算年龄
		int age = yearNow - yearBirth;
		//当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
		if (monthNow <= monthBirth) {
			//如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) age--;
			}else{
				age--;
			}
		}
		System.out.println("age:"+age);
		return age;
	}
	//获取到的年级
	public static int getparseGrade(String birthDayStr ) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		format.setLenient(false);
		Date birthDay = format.parse(birthDayStr);
		Calendar calBir = Calendar.getInstance();
		calBir.setTime(birthDay);


		//获取当前系统时间
		Calendar cal = Calendar.getInstance();
		//如果出生日期大于当前时间，则抛出异常
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		//取出系统当前时间的年、月、日部分
		int yearNow = cal.get(Calendar.YEAR);
//        int monthNow = cal.get(Calendar.MONTH);
//        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		//将日期设置为出生日期
		cal.setTime(birthDay);
		//取出出生日期的年、月、日部分
		int yearBirth = cal.get(Calendar.YEAR);
//        int monthBirth = cal.get(Calendar.MONTH);
//        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		//当前年份与出生年份相减，初步计算年龄
		int grade = yearNow - yearBirth;

		System.out.println("age:"+grade);
		return grade;
	}
	
	//测试状态

    /** 
     * byte转16进制 
     *  
     * @param bytes 
     * @return 
     */  
    public static String bytes2hex02(byte[] bytes)  
    {  
        StringBuilder sb = new StringBuilder();  
        String tmp = null;  
        for (byte b : bytes)  
        {  
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制  
            tmp = Integer.toHexString(0xFF & b);  
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位  
            {  
                tmp = "0" + tmp;  
            }  
            sb.append(tmp.toUpperCase() + " ");  
        }  
  
        return sb.toString();  
  
    }  
    
    public static boolean isValidDate(String s){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			dateFormat.parse(s);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public static boolean isValidYear(String s){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			dateFormat.setLenient(false);
			dateFormat.parse(s);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
    
    /**
     * 根据日期获取学年
     * <p/>
     * date:日期
     */
    public static int getAcademicYear(Date date) {
//    	Date curDate = isValidDate(date);
    	if (date == null) {
    		return -1;
    	}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if (month < 7) {
			year--;
			
		} 
		
		return year;
	}
    
    /**
     * 根据日期获取学期
     * <p/>
     * date:日期
     */

    /**
     * 根据年级获取入学年份
     * <p/>
     * schoolType: 学校类型 0：小学； 1：初中； 2：高中； 3：大学
     * duration:年制  0:三年制;      1:四年制;      2:五年制;      3:六年制;
     * curSchoolYear:学年，如果传空或null,默认当前学年
     */
    public static String getLatestSchoolYear(int schoolType, String duration, String academicYear) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int curYear;
		try {
			curYear = Integer.valueOf(academicYear).intValue();
		} catch (Exception e) {
			// 学年不正确，不是数字
			return "-1";
		}
		if (curYear > year) {
			// 学年或系统时间不正确，学年不能大于系统时间
			return "-2";
		}
		
		if (schoolType < 0 || schoolType > 3) {
			// 学校类型不正确
			return "-3";
		}

		int druationId = -1;
		if (isNull(duration)) {
        	if (schoolType == 0) {
        		druationId = 3;// 默认为六年制
        	} else if (schoolType == 1) {
        		druationId = 0;
        	} else if (schoolType == 2) {
        		druationId = 0;
        	} else if (schoolType == 3) {
        		druationId = 1;
        	}
        } else {
        	try {
        		druationId = Integer.valueOf(duration);
        	} catch (Exception e) {
        		// 年制不正确，不是数字
    			return "-4";
        	}
        }
		
		int calYear;
		if (druationId == 0) {
			calYear = 3;
		} else if (druationId == 1) {
			calYear = 4;
		} else if (druationId == 2) {
			calYear = 5;
		} else if (druationId == 3) {
			calYear = 6;
		} else {
			// 年制不正确，不是数字
			return "-4";
		}
		
		
		return 	String.valueOf(curYear - calYear + 1);
	}
    
    /**
     * 根据入学年份,获取学生现在的年级
     * <p/>
     * schoolYear:入学年份
     * duration:年制  0:三年制;      1:四年制;      2:五年制;      3:六年制;
     * academicYear:学年，如果传空或null,默认当前学年
     */

    public static boolean comPareVersion(String devVesion, String upgVersion) {
		try {
			String[] devSplit = devVesion.split("-");
			String[] upgSplit = upgVersion.split("-");
			if (devSplit.length != 5) {
				return false;
			}
			if (upgSplit.length != 5) {
				return false;
			}
			String[] devVerSplit = devSplit[4].split("\\.");
			String[] upgVerSplit = upgSplit[4].split("\\.");
			if (devVerSplit.length != 3) {
				return false;
			}
			if (upgVerSplit.length != 3) {
				return false;
			}
			if (Integer.valueOf(upgVerSplit[0]) > Integer.valueOf(devVerSplit[0])) {
				return true;
			} else if (Integer.valueOf(upgVerSplit[0]) == Integer.valueOf(devVerSplit[0])) {
				if (Integer.valueOf(upgVerSplit[1]) > Integer.valueOf(devVerSplit[1])) {
					return true;
				} else if (Integer.valueOf(upgVerSplit[1]) == Integer.valueOf(devVerSplit[1])) {
					if (Integer.valueOf(upgVerSplit[2]) > Integer.valueOf(devVerSplit[2])) {
						return true;
					}
					return false;
				} 
				return false;
			} 
				
			return false;
		} catch (Exception e) {
			return false;
		}
	}
    


    public static String  getNoTime() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        int year = calendar.get(Calendar.YEAR);
        int month = (calendar.get(Calendar.MONTH) + 1);
        String disMonth = String.valueOf(month);
        if (month<10){
            disMonth=""+month;
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String disDay = String.valueOf(day);
        if (day<10){
           disDay=""+day;
        }
        return  year + "-" + disMonth + "-" + disDay ;
    }
    
    public static String praseFormat(String format) {
    	format = format.replace(" ", "");
		int IntNo= format.indexOf(".");
		if (IntNo < 0){
			return format;
		}else {
			String intNumber = format.substring(0,format.indexOf("."));
			return intNumber;
		}
	}
    
    // 判断整数（int）  
    public static boolean isInteger(String str) {  
        if (isNull(str)) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile("[1-9][0-9]*");  
        return pattern.matcher(str).matches();  
    }  
    
    // 判断整数或一位小数 
    public static boolean isDecimal(String str) { 
    	if (isNull(str)) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile("[0-9].[0-9]|[1-9][0-9]*.[0-9]|[1-9][0-9]*");  
        return pattern.matcher(str).matches();  
    }

    // 判断正负整数或正负一位小数 
    public static boolean isNegative(String str) { 
    	if (isNull(str)) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile("[0-9].[0-9]|[1-9][0-9]*.[0-9]|[1-9][0-9]*|-[0-9].[0-9]|-[1-9][0-9]*.[0-9]");  
        return pattern.matcher(str).matches();  
    }
    
    public static String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;// 2012年10月03日 23:41:31
	}

	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}
	/***
	 * 判断学校类
	 */
	/***
	 * 判断在导入EXCEL中的xlsx格式时是否缺少信息
	 */

	public static String formatDate(SimpleDateFormat fromFormat, SimpleDateFormat toFormat, String str) {
		if (isNull(str)) {
			return "";
		}
		try {
			return toFormat.format(fromFormat.parse(str));
		} catch (Exception e) {
			return "";
		}
	}
}
