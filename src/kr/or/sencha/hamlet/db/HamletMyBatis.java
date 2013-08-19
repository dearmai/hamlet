package kr.or.sencha.hamlet.db;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HamletMyBatis {
	
	public static void main(String[] args) {
		System.out.println("### HamletMyBatis.main() start ###");
		
		SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
		
		HashMap p = new HashMap();	// Parameter 담을 HashMap
		p.put("id", "1");			// Parameter
		
		try {  
			StringMap result = new StringMap( (HashMap)session.selectOne("Default.selectSample",p) );	// 1건 조회
			System.out.println( result.get("server_id") );		
			System.out.println( result.get("server_name") );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			result.print();		// session.selectOne()  함수와 함께 쓰인다. 디버깅 용도로, 한건의 결과를 화면에 출력한다.
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("### HamletMyBatis.main() End ###");
	}
	
    public HamletMyBatis() {
        // TODO Auto-generated constructor stub
    }
    
    public String getMenuInfo(String pgm_syscd){
    	
    	JSONObject obj = new JSONObject();
    	JSONArray result = getMenuList(pgm_syscd);
    	
    	obj.put("entitys", result);
    	obj.put("totalCount", result.size());
    	obj.put("success", true);
    	
    	return obj.toString();
    }
    
    private JSONArray getMenuList(String pgm_syscd) {
    	
    	JSONArray jsonList = new JSONArray();
    	
    	SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
    	
		try {
			HashMap<String, String> p = new HashMap<String, String>();	// Parameter 담을 HashMap
			p.put("pgm_syscd", pgm_syscd);		// Parameter
			
			StringMap result = new StringMap( session.selectList("Default.getMenuInfoList",p) );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			
			JSONObject obj = null;
			for( int i = 0; i < result.size(); i++ ) {
				obj = new JSONObject();
				obj.put( "pgm_syscd", result.get(i).get("server_id") );
				obj.put( "pgm_sysicon", "grid");
				obj.put( "pgm_sysnm", result.get(i).get("server_name") );
				jsonList.add(obj);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
		return jsonList;
	}

	public String sampleInfo() {
    	JSONObject obj = new JSONObject();
    	
    	obj.put("test1", "REPLACE");
    	obj.put("test2", 3);
    	obj.put("sampleSelectOne", sampleSelectOne());
    	obj.put("sampleSelectList", sampleSelectList());
    	
    	return obj.toString();
    }
    
    /**
     * 여러건 조회 샘플 
     * @return
     */
	public JSONArray sampleSelectList() {
		JSONArray jsonList = new JSONArray();
		SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
		
		try {
			HashMap p = new HashMap();	// Parameter 담을 HashMap
			p.put("serverId", "1");		// Parameter
			
			StringMap result = new StringMap( session.selectList("Default.sampleSelectList",p) );
			
			result.printList();	// session.selectList() 함수와 함께 쓰인다. 디버깅 용도로, 여러건의 결과를 화면에 출력한다.
			
			JSONObject obj = null;
			for( int i = 0; i < result.size(); i++ ) {
				obj = new JSONObject();
				obj.put( "server_id", result.get(i).get("server_id") );
				obj.put( "server_name", result.get(i).get("latency") );
				jsonList.add(obj);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return jsonList;
	}
    
	/**
	 * 한 건 조회 샘플 
	 * @return
	 */
	public String sampleSelectOne() {

		SqlSession session = MyBatisManager.getSqlMapper().openSession();	// DB Connection
		
		HashMap p = new HashMap();	// Parameter 담을 HashMap
		p.put("serverId", "1");		// Parameter
		
		String returnData = null;
		
		try {  
			StringMap result = new StringMap( (HashMap)session.selectOne("Default.sampleSelectOne",p) );
			returnData = result.get("server_name");
			
			result.print();		// session.selectOne()  함수와 함께 쓰인다. 디버깅 용도로, 한건의 결과를 화면에 출력한다.
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return returnData;
	}
}
