package com.example.myontheway01.fragment;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myontheway01.bean.StrategyBean;
import com.example.myontheway01.bean.UserInfoBean;

public class HttpGetData {
	private static String startId;
	
    public static String getStartId() {
		return startId;
	}

	/**
     * 从网上获取json数据
     * @param path
     * @return
     */
	public static JSONObject ParseJson(final String path) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		// HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpPost httpPost = new HttpPost(path);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						"utf-8");
				JSONObject jsonObject = new JSONObject(result);
				return jsonObject;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		return null;

	}
	/**
	 * 精选界面的数据 
	 * @return
	 */
	public static List<StrategyBean> getStrategyBean(String Url) {
		List<StrategyBean> list = new ArrayList<StrategyBean>();
		//从网络获取数据并且拿到json
		JSONObject json = ParseJson(Url);
        //解析数据
		if (json == null) {
			return null;
		} else {
			try {

				JSONArray Data = json.getJSONObject("obj").getJSONArray("list");

				for (int i = 0; i < Data.length(); i++) {
					System.out.println("------->" + i);
					JSONObject data = Data.getJSONObject(i);
					StrategyBean StrategyBean = new StrategyBean();
					StrategyBean.setId(data.optString("id"));
					JSONObject element = data.getJSONObject("tour");
					StrategyBean.setTitle(element.optString("title"));
					StrategyBean.setPubdate(element.optString("startdate"));
					StrategyBean.setPictureCount(element.optString("cntP"));
					StrategyBean.setImage(element.optString("coverpic"));
					StrategyBean.setViewCount(element.optString("pcolor"));
					StrategyBean.setFavoriteCount(element.getString("likeCnt"));
					StrategyBean.setViewCount(element.optString("viewCnt"));
					StrategyBean.setForeword(element.optString("foreword"));
					UserInfoBean userInfo = new UserInfoBean();
					JSONObject owner = element.optJSONObject("owner");
					userInfo.setUsername(owner.optString("username"));
					userInfo.setNickname(owner.optString("nickname"));
					userInfo.setUserId(owner.optString("userid"));
					userInfo.setAvatar(owner.getString("avatar"));
					StrategyBean.setUserInfo(userInfo);
					JSONArray dispcitys = element.getJSONArray("dispCities");
					String[] citys = new String[dispcitys.length()];
					for (int j = 0; j < dispcitys.length(); j++) {

						citys[j] = dispcitys.optString(j);
					}
					StrategyBean.setDispCities(citys);
					StrategyBean.setCmtCount(element.getString("cntcmt"));				
					StrategyBean.setTourId(element.optString("id"));
					list.add(StrategyBean);
					if (i == Data.length() - 1) {
						startId = StrategyBean.getId();
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("------->" + list.size());
			return list;

		}

	}
	
}

