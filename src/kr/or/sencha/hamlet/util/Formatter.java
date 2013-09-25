package kr.or.sencha.hamlet.util;


import java.text.*;
import java.util.*;
import java.math.*;

public final class Formatter {
	public static final char DOT = '.';
	public static final char COMMA = ',';
	public static final int YYYYMM=0;
	public static final int YYYYMMDD=1;
	public static final int YYYYMMDDHHMI=2;
	public static final int YYYYMMDDHHMISS=3;
	public static final int YYYYXMMXDDX=4;		//2005년11월12일형식으로 변
	/**
	 * 디폴트 생성자를 정의합니다
	 */
	public Formatter() {}
	
	/**
	 * 현재 날자에서 인자로 받은 숫자만큼 날자를 구한다.
	 * @param iDay
	 * @return
	 */
	public static String getDate ( int iDay ) 
	{
		Calendar temp=Calendar.getInstance ( );
		StringBuffer sbDate=new StringBuffer ( );
		temp.add ( Calendar.DAY_OF_MONTH, iDay );

		int nYear = temp.get ( Calendar.YEAR );
		int nMonth = temp.get ( Calendar.MONTH ) + 1;
		int nDay = temp.get ( Calendar.DAY_OF_MONTH );
		sbDate.append ( nYear );
		if ( nMonth < 10 ) 
			sbDate.append ( "0" );
		sbDate.append ( nMonth );
		if ( nDay < 10 ) 
			sbDate.append ( "0" );
		sbDate.append ( nDay );
		return sbDate.toString ( );
	}
	/**
	 * 더블 형태의 숫자를 패턴에 맞게 치환한다.
	 * 
	 * @param doubleObj
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static String convert(double doubleObj , String pattern ) 
    throws Exception 
    {
		if(doubleObj<0) // 음수일 경우 무조건 양수로 바꾸는 로직이다. 밖에서 up&down을 기호로 표시하기에 양수로 변경한다.
			doubleObj = doubleObj*-1;
		DecimalFormat df = new DecimalFormat(pattern) ;
		return df.format(doubleObj).toString() ;
    }

	/**
	 * 문자열을 잘라내고 '...'을 붙인다.
	 * @param Text String
	 * @param CutSize int
	 * @return String
	 */
	public static String Substr(String Text, int CutSize) {
		String CutText = "";
		if(Text.length() > CutSize)
			CutText = Text.substring(0,CutSize) + "...";
		else
			CutText = Text;
		return CutText;
	}
	/**
	 * 한글 CHARSET 변환
	 * @param dd String
	 * @throws Exception
	 * @return String
	 */
	public static String getToKor(String dd) throws Exception{
		return new String(dd.getBytes("8859_1"),"KSC5601");
	}
	
	public static String systemDate(String format){
		String returnValue = null;
		java.text.SimpleDateFormat formatter
			= new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		java.util.Date currentTime = new java.util.Date();
		returnValue = formatter.format(currentTime);
		if( returnValue == null ) returnValue = "";
		return returnValue;
	}
	/**
	 * 문자열을 스크립트 alert 메세지로 보여주고자 할때 변환하는 메소드이다.<BR><BR>
	 *
	 * @param  s  문자열
	 * @return String 변환된 문자열
	 */
	public static String insertNLScript(String s){
		if (s == null || s.equals(""))
			return "";
		StringBuffer sb = new StringBuffer(s);
		char ch;
		for (int i = 0; i < sb.length(); i++) {
			ch = sb.charAt(i);
			if ( Integer.toHexString(ch).equals("a") ){
				sb.replace(i,i+1,"\\n");
				i += 1;
			} else 	if ( ch == '\r' && sb.charAt(i+1) == '\n' ){
				sb.replace(i,i+2,"\\n");
				i += 2;
			} else  if( ch == '\\' ){
				sb.insert(i+1,"\\");
				i += 1;
			} else  if( ch == '\'' || ch == '\"' ){
				sb.insert(i,"\\");
				i += 1;
			}
		}
		return sb.toString();
	}



	/**
	 * 최대 길이 수보다 큰 문자열인 경우에 뒷부분을 "..."로 변환한다.<BR><BR>
	 *
	 * @param text  주어진 문자열
	 * @param maxLength  최대 문자열 길이
	 * @return String 처리된 문자열
	 */
	public static String textMore(String text, int maxLength) {
		String value = "";

		if( text ==  null) return null;

		if( text.length() <= (maxLength+3) ) return text;

		value = text.substring(0,maxLength) + "...";

		return value;
	}

	/**
	 * 2002.05.13 string형식으로 들어오는 문자열에서 특정 값을 제거한 문자열을 리턴한다.
	 *
	 * @param str     문자열
	 * @param delim   제거할 문자
	 * @return String 특정값이 제거된 문자열
	 */
	public static String remove(String str, String delim) {
			StringBuffer    sb = new StringBuffer();              // 리턴할 문자열 버퍼
		StringTokenizer st = new StringTokenizer(str, delim); // 지정된 값으로 문자열을 자른다.

		while (st.hasMoreTokens()) {
				sb.append(st.nextToken());
			 }

		return sb.toString();
	}


	/**
	 * 2002.04.15 형식으로 들어오는 날짜 문자열에서 .을 제거한 문자열을 리턴한다.<BR><BR>
	 *
	 * @param date  날짜 문자열
	 * @return String .이 제거된 문자열
	 */
	public static String removeDot(String temp_date) {
	   String date = temp_date.trim();

		if( date == null ) return null;

		// "2002.03.01" 인 경우 문자열의 길이는 10자리임,
		// "2002.03" 인 경우에는 7자리임.
		if( !(date.length() == 10 || date.length() == 7) )
				return date;

		return remove(date, ".");
	}
	/**
	* 2002.04.15 형식으로 들어오는 날짜 문자열에서 .을 제거한 문자열을 리턴한다.<BR><BR>
	*
	* @param date  날짜 문자열
	* @return String -이 제거된 문자열
	*/
   public static String removeDash(String temp_date) {
	   String date = temp_date.trim();
	   if( date == null ) return null;

	   // "2002.03.01" 인 경우 문자열의 길이는 10자리임,
	   // "2002.03" 인 경우에는 7자리임.
	   if( !(date.length() == 10 || date.length() == 7) )
			   return date;

	   return remove(date, "-");
   }


	/**
	 * 12,345 형식으로 들어오는 숫자 문자열에 ,를 제거한 문자열을 리턴한다.<BR><BR>
	 *
	 * @param price 숫자문자열
	 * @return String ,이 제거된 문자열
	 */
	public static String removeComma(String price) {
		if( price == null ) return null;

		return remove(price, ",");
	}



	/**
	 * 주민등록번호를 출력용으로 변환해준다.<BR><BR>
	 *
	 * @param regNo 주민등록번호 13자리
	 * @return String "-"가 붙은 주민등록번호( 6자리 + "-" + 7자리 )
	 */
	public static String formatJuminNo(String regNo) {

		if( regNo == null ) return null;

		if( regNo.length() != 13) return regNo;

		return ( regNo.substring(0,6) + "-" + regNo.substring(6,13));
	}

	/**
	 * 우편번호를 출력용으로 변환해준다.<BR><BR>
	 *
	 * @param postNo 6자리의 우편번호
	 * @return String 출력용으로 변환된 우편번호 ( 3자리 + "-" + 3자리 )
	 */
	public static String formatZipCd(String postNo) {

		if( postNo == null ) return null;

		if( postNo.length() != 6 ) return postNo;

		return ( postNo.substring(0,3) + "-" + postNo.substring(3,6));
	}

	/**
	 * 문자열로 넘어온 날짜를 각 디폴트 포맷(YYYY.MM.DD)으로 변환한다.<BR>
		 * 포맷하기에 길이가 부족하면 원래 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param date   날짜 문자열
	 * @param format 변환할 타입
	 * @return String 변환된 문자열  ( yyyy.mm.dd )
	 */
	public static String formatDate(String date) {
			return formatDate(date,YYYYMMDD);
	}

	/**
	 * 문자열로 넘어온 날짜를 각각의 포맷에 따라서
		 * 재구성하여 반환한다.<BR>
		 * 포맷하기에 길이가 부족하면 원래 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param date   날짜 문자열
	 * @param format 변환할 타입
	 * @return String 변환된 문자열  ( yyyy.mm )
	 * @return String 변환된 문자열  ( yyyy.mm.dd )
	 * @return String 변환된 문자열  ( yyyy.mm.dd hh:mi )
	 * @return String 변환된 문자열  ( yyyy.mm.dd hh:mi:ss )
	 */
	public static String formatDate(String date,int format) {
		StringBuffer rDate = new StringBuffer();

		if( date == null || date.equals(""))
				return "";


		//*** 추가
		if ( format == YYYYXMMXDDX )
		{
			rDate.append(date.substring(0,4));
			rDate.append("년");
			rDate.append(date.substring(4,6));
			rDate.append("월");
			rDate.append(date.substring(6,8));
			rDate.append("일");
			return rDate.toString();
		}
		//*** 추가

		if( !(format == YYYYMM || format == YYYYMMDD || format == YYYYMMDDHHMI || format == YYYYMMDDHHMISS) )
				return date;

		if( format==YYYYMM && date.length() < 6)
				return date;
		if( format==YYYYMMDD && date.length() < 8)
				return date;
		if( format==YYYYMMDDHHMI && date.length() < 12)
				return date;
		if( format==YYYYMMDDHHMISS  && date.length() < 14)
				return date;

				rDate.append(date.substring(0,4));
				rDate.append(".");
				rDate.append(date.substring(4,6));

				if( format >= YYYYMMDD ) {
						rDate.append(".");
						rDate.append(date.substring(6,8));

						if(format >= YYYYMMDDHHMI ) {
								rDate.append(" ");
								rDate.append(date.substring(8,10));
								rDate.append(":");
								rDate.append(date.substring(10,12));

								 if(format==YYYYMMDDHHMISS) {
										rDate.append(":");
										rDate.append(date.substring(12,14));
						}
						 }
				 }
				return rDate.toString();
	}

	 /**
	 * Date type으로 넘어온 날짜를 디폴트 포맷(YYYY.MM.DD)으로 변환한다.<BR><BR>
		 *
	 * @param date   날짜
	 * @param format 변환할 타입
	 * @return String 변환된 문자열  ( yyyy.mm.dd )
	 */
   public static String formatDate(Date date) {
	 return formatDate(date, YYYYMMDD);
	}

	public static String formatDate2(Date date) {
	  SimpleDateFormat sdf=null;
			  if (date == null) return "";
			  sdf = new SimpleDateFormat("yyyy-MM-dd");
			  return ( sdf.format(date));
	 }


	 /**
	 * Date type으로 넘어온 날짜를 각각의 포맷에 따라서
		 * 재구성하여 반환한다.<BR><BR>
		 *
	 * @param date   날짜
	 * @param format 변환할 타입
	 * @return String 변환된 문자열  ( yyyy.mm )
	 * @return String 변환된 문자열  ( yyyy.mm.dd )
	 * @return String 변환된 문자열  ( yyyy.mm.dd hh:mi )
	 * @return String 변환된 문자열  ( yyyy.mm.dd hh:mi:ss )
	 */
   public static String formatDate(Date date,int format) {
		SimpleDateFormat sdf=null;
				if (date == null) return "";
				if( format==YYYYMM)
						sdf = new SimpleDateFormat("yyyy.MM");
				else if( format==YYYYMMDD)
					sdf = new SimpleDateFormat("yyyy.MM.dd");
				else if( format==YYYYMMDDHHMI)
					sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
				else if( format==YYYYMMDDHHMISS)
					sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return ( sdf.format(date));
	}

	 /**
	 * 문자열이 null인지 판별하여 null이면 defaultValue를 리턴하고
	 * 아니면 원 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param  str 문자열
	 * @param  defaultValue 디폴트값
	 * @return String 치환된 문자열
	 */
   public static String nvl(String str, String defaultValue) {
				if( str == null )
						return defaultValue;
				else
						return str;
	}


	 /**
	 * 문자열이 null인지 판별하여 null이면 defaultValue를 리턴하고
	 * 아니면 원 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param  str 문자열
	 * @param  defaultValue 디폴트값
	 * @return String 치환된 문자열
	 */
   public static String envl(String str, String defaultValue) {
				if( str == null ) {
						return defaultValue;
				} else if (str.equals("")) 	{
						return defaultValue;
				} else {
						return str;
				}
	}
   	public static int envlInt(String str, int defaultValue) {
		if( str == null ) {
				return defaultValue;
		} else if (str.equals("")) 	{
				return defaultValue;
		} else {
				return Integer.parseInt(str);
		}
   	}

	public static String snvl(String str) {
				 if( str == null ) {
						 return "0";
				 } else if (str.equals("")) 	{
						 return "0";
				 } else {
						 return str;
				 }
	 }

	 /**
	 * 문자열이 null인지 판별하여 null이면 ""를 리턴하고
	 * 아니면 원 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param  str 문자열
	 * @param  defaultValue 디폴트값
	 * @return String 치환된 문자열
	 */
   public static String nvl(String str) {
				return nvl(str,"");
	}

	 /**
	 * 문자열이 empty_value_string 인지 판별하여 empty_value_string 이면 defaultValue를 리턴하고
	 * 아니면 원 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param  str 문자열
	 * @param  defaultValue 디폴트값
	 * @return String 치환된 문자열
	 */
   public static String evl(String str, String defaultValue) {
				if( str.trim().length() == 0 )
						return defaultValue;
				else
						return str;
	}

	 /**
	 * 문자열이 empty_value_string 인지 판별하여 empty_value_string 이면 ""를 리턴하고
	 * 아니면 원 문자열을 그대로 리턴한다.<BR><BR>
		 *
	 * @param  str 문자열
	 * @return String 치환된 문자열
	 */
   public static String evl(String str) {
				return evl(str,"");
	}


	 /**
	 * 문자열 받아서 HTML 코드로 변환하는 메소드 <BR><BR>
		 * <xmp>
		 * <  : &lt;
		 * >  : &gt;
		 * &  : &amp;
		 * '  : &#39;
		 * "  : &quot;
		 * white space : &nbsp;
		 * \r\n : <BR>
		 * </xmp>
	 * @param  text 문자열
	 * @return String 치환된 문자열
	 */
   public static String formatHTMLCode(String text) {
			if( text == null || text.equals("") )
					return "";

			StringBuffer sb = new StringBuffer(text);
				char ch;

				for (int i = 0; i < sb.length(); i++) {
						ch = sb.charAt(i);
					if (ch == '<') {
								sb.replace(i,i+1,"&lt;");
								i += 3;
					} else if (ch == '>') {
								sb.replace(i,i+1,"&gt;");
								i += 3;
					} else if (ch == '&') {
								sb.replace(i,i+1,"&amp;");
								i += 4;
					} else if (ch == '\'') {
								sb.replace(i,i+1,"&#39;");
								i += 4;
					} else if (ch == '"') {
								sb.replace(i,i+1,"&quot;");
								i += 5;
					} else if (ch == '\r' && sb.charAt(i+1) == '\n' ){
							sb.replace(i,i+2,"<BR>");
							i += 3;
					}
				}
				return sb.toString();
	}

		  /**
	 * int,long형 숫자를 원하는 숫자포맷으로 변환하는 메소드
		 *
	 * @param  number  숫자
	 * @param  group_b 세자리마다 ,를 찍을지 여부
	 * @param  pre     소수점 앞자리 길이
	 * @param  post    소수점 뒷자리 길이
	 * @param  percent 백분율(%)로 표시할지 여부
	 * @return String 치환된 문자열
	 */
	public static String formatNumber(long number, boolean group_b, int pre, int post, boolean percent) {
				   return formatNumber(String.valueOf(number),group_b,pre,post,percent);
		}

		  /**
	 * int,long형 숫자를 원하는 숫자포맷으로 변환하는 메소드
		 *
	 * @param  number  숫자
	 * @param  group_b 세자리마다 ,를 찍을지 여부
	 * @param  pre     소수점 앞자리 길이
	 * @param  post    소수점 뒷자리 길이
	 * @return String 치환된 문자열
	 */
	public static String formatNumber(long number, boolean group_b, int pre, int post) {
				   return formatNumber(number,group_b,pre,post,false);
		}

		  /**
	 * float,double형 숫자를 원하는 숫자포맷으로 변환하는 메소드
		 *
	 * @param  number  숫자
	 * @param  group_b 세자리마다 ,를 찍을지 여부
	 * @param  pre     소수점 앞자리 길이
	 * @param  post    소수점 뒷자리 길이
	 * @param  percent 백분율(%)로 표시할지 여부
	 * @return String 치환된 문자열
	 */
	public static String formatNumber(double number, boolean group_b, int pre, int post, boolean percent) {
				   return formatNumber(String.valueOf(number),group_b,pre,post,percent);
		}

		  /**
	 * float,double형 숫자를 원하는 숫자포맷으로 변환하는 메소드
		 *
	 * @param  number  숫자
	 * @param  group_b 세자리마다 ,를 찍을지 여부
	 * @param  pre     소수점 앞자리 길이
	 * @param  post    소수점 뒷자리 길이
	 * @return String 치환된 문자열
	 */
	public static String formatNumber(double number, boolean group_b, int pre, int post) {
				   return formatNumber(number,group_b,pre,post,false);
		}

		  /**
	 * 문자열을 원하는 숫자포맷으로 변환하는 메소드
		 *
	 * @param  number  문자열
	 * @param  group_b 세자리마다 ,를 찍을지 여부
	 * @param  pre     소수점 앞자리 길이
	 * @param  post    소수점 뒷자리 길이
	 * @return String 치환된 문자열
	 */

   public static String formatNumber(String number, boolean group_b, int pre, int post) {
				   return formatNumber(number,group_b,pre,post,false);
		}

		  /**
	 * 문자열을 원하는 숫자포맷으로 변환하는 메소드<BR><BR>
		 *
	 * @param  number  문자열
	 * @param  group_b 세자리마다 ,를 찍을지 여부
	 * @param  pre     소수점 앞자리 길이
	 * @param  post    소수점 뒷자리 길이
	 * @param  percent 백분율(%)로 표시할지 여부
	 * @return String 치환된 문자열
	 */

   public static String formatNumber(String number, boolean group_b, int pre, int post, boolean percent) {
				   DecimalFormat df = new DecimalFormat("#,###.#####");
				   StringBuffer  pattern = new StringBuffer();

				   // pattern 결정
				if( pre == 0 ){ //길이가 정해져 있지 않을 경우
						pattern.append("####");
				} else {
						for(int i=0; i<pre; i++)
								pattern.append("0");
				}

				if( group_b )
						pattern.insert(pattern.length()-3,",");

				pattern.append(".");
				if( post == 0 ){
						pattern.append("#####"); //소수점 아래자리 최대 5자리까지
				} else {
						for(int i=0; i<post; i++)
								pattern.append("0");
				}

				if( percent )
						pattern.append("%");

				// 숫자 패턴으로 변환
				df.applyLocalizedPattern(pattern.toString());

				try {
						if( number == null || number.equals("") ) {
								return "";
						   } else {
								   return df.format(Long.parseLong(number));
						   }
				} catch (NumberFormatException e){
								try {
									return df.format(Double.parseDouble(number));
							} catch (NumberFormatException e1){
										throw e1;
							}
				}
		}

/****************************************
 * \n 을 <br>로 바꾼다.
 * @param String
 * @return String 치환된 문자열
*****************************************/
public static String repl(String text)
{
if( text == null || text.equals("") )
		return "";

StringBuffer sb = new StringBuffer(text);
		char ch;

		for (int i = 0; i < sb.length(); i++) {
				ch = sb.charAt(i);
			if (ch == '\r' && sb.charAt(i+1) == '\n' )
			{
					sb.replace(i,i+2,"<BR>");
					i += 3;
			}
		}
		return sb.toString();
}

		/*
		 * 0을 공백으로 바꾼다.
		 *
		 * @param int
		 * @return String 치환된 문자열
		 */
		public static String nonZero(int ss) {
		  if (ss == 0) {
			return "";
		  } else {
			return String.valueOf(ss);
		  }
		}

		/*
		 * 0을 공백으로 바꾼다.
		 *
		 * @param double
		 * @return String 치환된 문자열
		 */
		public static String nonZero(double ss) {
		  if (ss == 0) {
			return "";
		  } else {
			return String.valueOf(ss);
		  }
		}

		/*
		 * 0을 공백으로 바꾼다.
		 * @param double
		 * @return String 치환된 문자열
		 */
		public static String nonZero(BigDecimal ss) {
		  if (ss.compareTo(new BigDecimal("0")) == 0) {
			return "";
		  } else {
			return ss.toString();
		  }
		}

		/*
		 * 0을 공백으로 바꾼다.
		 *
		 * @param String
		 * @return String 치환된 문자열
		 */
		public static String nonZero(String ss) {
		  if (ss == null) {
			return "";
		  }
		  if (ss.equals("0")) {
			return "";
		  }
		  if (ss.trim().equals("0")) {
			return "";
		  }
		  return ss;
		}

		/*
		 * 공백을 0으로 바꾼다.
		 *
		 * @param int
		 * @return String 치환된 문자열
		 */
		public static String toZero(String ss) {
		  if (ss == null) {
			return "0";
		  }
		  if (ss.equals("")) {
			return "0";
		  }
		  return ss;
		}

		/**
		 * yyyyMMdd또는 HHmm형태로 문자열을 리턴하는 메소드<BR><BR>
		 *
		 * @param  number  yyyyMMdd 또는 HHmm
		 * @return String 치환된 문자열
		 */
		public static String getCurrentDay_Time(String a) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		  String current_date = sdf.format(new Date());
		  if(a.equals("yyMMdd")){
			current_date = current_date.substring(0,4) + "년" + current_date.substring(4,6) + "월" + current_date.substring(6,8) + "일";
		  }else if(a.equals("HHmm")){
			current_date = current_date.substring(8, 10) + ":" + current_date.substring(10, 12);
		  }else if(a.equals("YYYYMMDD")){
			current_date = current_date.substring(0,4) + current_date.substring(4,6) + current_date.substring(6,8);
		  }else{
			current_date = "";
		  }
		  return current_date;
		}
		
		public static String setComma(String number) {
				String result = "";
				String tail = "";
				boolean flag = false;

				if( number.equals("") ) return result;

				int pts = number.indexOf(".");

				if(pts > 0){
						tail = number.substring(pts);
						number = number.substring(0,pts);
				}
				if( Long.parseLong(number) < 0 ){
						number = String.valueOf(Long.parseLong(number) * (-1));
						flag = true;
				}
				int len = number.length();

				if(!number.equals("")) {
						StringBuffer buffer = new StringBuffer(number);

						for(int i = len - 3; i > 0; i -= 3)
								buffer.insert(i, ',');

						if( pts > 0 )
								result = buffer.toString()+tail;
						else
								result = buffer.toString();

						if( flag ) result = "-" + result;
				}
				return result;
		}

		/**
		 * 파라미터 좌측으로 0을 붙인다.
		 *
		 * @param  String, int
		 * @return String 치환된 문자열
		 */
		public static String lpad(String param, int length) {
		  String pad[] = {"", "0", "00", "000", "0000", "00000", "000000"};
		  String return_str = "";
		  if (param == null) {
			return "";
		  }

		  return_str = pad[length - param.length()] + param;

		  return return_str;
		}


		public static void main(String[] a){
/*
				System.out.println(Formatter.formatDate("20020404",Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate("20020404",Formatter.YYYYMMDDHHMI));
				System.out.println(Formatter.formatDate("20020404",Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMMDDHHMI));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMMDDHHMI));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate("20020404112199"));

				System.out.println(Formatter.formatDate(new java.util.Date(),Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate(new java.util.Date()));

				System.out.println("upgrade example===========================================");
				System.out.println(Formatter.formatDate("200204",Formatter.YYYYMM));
				System.out.println(Formatter.formatDate("200204",Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate("200204",Formatter.YYYYMMDDHHMI));
				System.out.println(Formatter.formatDate("200204",Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMM));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMMDDHHMI));
				System.out.println(Formatter.formatDate("200204041121",Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMM));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMMDD));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMMDDHHMI));
				System.out.println(Formatter.formatDate("20020404112199",Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate("20020404112199"));
*/
				Calendar cal = Calendar.getInstance();
				System.out.println(Formatter.formatDate(cal.getTime(),Formatter.YYYYMM));
				System.out.println(Formatter.formatDate(cal.getTime(),Formatter.YYYYMMDDHHMISS));
				System.out.println(Formatter.formatDate(cal.getTime()));
/*
				System.out.println(Formatter.nvl("aaa","KIM") +" ... "+ Formatter.nvl(null) );

				String text = "abc\r\n<de&f>";
				System.out.println(Formatter.formatHTMLCode(text));

				System.out.println(Formatter.formatNumber("1234567", false, 3, 2));
				System.out.println(Formatter.formatNumber("1234567", true, 3, 0, false));
				System.out.println(Formatter.formatNumber("123", true, 4, 0));

				System.out.println(Formatter.evl("aaa","111") +" ... "+ Formatter.evl("","222") );
				System.out.println(Formatter.evl(" t") +" ... "+ Formatter.evl("	t","333") );

				System.out.println("remove example===========================================");
				System.out.println(Formatter.remove("2002.02.04","."));
				System.out.println(Formatter.remove("780205-2215432","-"));
				System.out.println(Formatter.remove("137-252","-"));
				System.out.println(Formatter.remove("132-1212-2424","-"));
				System.out.println(Formatter.remove("132%1212%1111%2424","%"));
				System.out.println(Formatter.remove("cho yong yun"," "));
				System.out.println(Formatter.removeDot("2002.02.04"));
				System.out.println(Formatter.removeDot("780205-2215432"));
				System.out.println(Formatter.removeComma("137-252"));
				System.out.println(Formatter.removeComma("132,1212,2424"));
				System.out.println(Formatter.removeDot(null));
				System.out.println(Formatter.removeComma(null));
				System.out.println("remove example===========================================");
*/
		}


}
