package kr.or.sencha.hamlet.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.or.sencha.hamlet.db.MyBatisManager;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

public class ServerListDao {
	
    public ServerListDao() {
    }
    
    private Gson gson = new Gson();
    private SqlSession session = MyBatisManager.getSqlMapper().openSession();

	public String getServerList() {
        String json = gson.toJson(session.selectList("Default.getServerList"));
        session.close();

        return json;
	}

	public String getServerData(String serverId, int limit) {
		HashMap param = new HashMap();
		param.put("serverId", serverId);
		param.put("limit", limit);
		
        String json = gson.toJson(session.selectList("Default.getServerData", param));
        session.close();

        return json;
	}

	public String getErrorLog(int limit) {
		HashMap param = new HashMap();
		param.put("limit", limit);
		
        String json = gson.toJson(session.selectList("Default.getErrorLog", param));
        session.close();

        return json;
	}

	public String getLowDiskSpace() {
		
		HashMap param = new HashMap();
		param.put("createdDate", getLast7Date());
		
        String json = gson.toJson(session.selectList("Default.getLowDiskSpace", param));
        session.close();

        return json;
	}

	/**
	 * 현재부터 7일전의 날짜를 리턴
	 * @return
	 */
	private String getLast7Date() {
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		Date currentTime = new Date ();
		
		currentTime.setTime(currentTime.getTime() - ( (long) 1000 * 60 * 60 * 24 * 7 ));
		
		String dTime = formatter.format ( currentTime ) + "_00_00_00";
		return dTime;
	}
	
	/**
	 * Local Test 용도의 main()
	 * @param args
	 */
	public static void main(String[] args) {
		ServerListDao dao = new ServerListDao();
		String s = dao.getServerList();
		System.out.println( s );
	}
}
