package com.khit.board.jsondata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Apijson {

	public static void main(String[] args) {

		try {
			//지진 해일 대피소 데이터 테스트
			String serviceKey = "8Xprbm0iOcgtMyHrm5lwWvt1Xn6UzD8wmw0N10UcJzPTT6s8nMDpkj5jooMHQBWf87i64Lwrm4q9eUG4nj7BKQ%3D%3D";
			String url = "https://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
				url += "?serviceKey=" + serviceKey;
				url += "&pageNo=1";
				url += "&numOfRows=10";
				url += "&type=json";
			
			//데이터를 받기 위해서 URL 클래스의 객체 생성
			URL requestUrl = new URL(url);
			System.out.println(url);
			
			//openConnection()을 이용한 연결
			HttpURLConnection conn = (HttpURLConnection)requestUrl.openConnection();
			conn.setRequestMethod("GET");	//대문자 필수
			
			//응답 상태 코드(200, 403, 404, 500)
			int status = conn.getResponseCode();
			System.out.println(status);
			
			//버퍼 - 입출력속도 향상을 위해 데이터를 이릿적으로 메모리 영역에 모아둠
			//BufferedReader(보조스트림) : 기반스트림(생성자) - InputStreamReader
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			
			String responseText = "";
			String line;	//한 행에 있는 데이터
			while((line = br.readLine()) != null) {
				responseText += line;
			}
			System.out.println(responseText);
			
			br.close();	//br 종료
			conn.disconnect();	//연결 종료
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
