package sh.java.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class NetworkStudy {

	public static void main(String[] args) {
		NetworkStudy study = new NetworkStudy();
//		study.test1();
//		study.test2();
		study.test3();
	}

	
	/**
	 * URLConnection
	 * - 특정 URL에 접속후 응답후 받아 처리하는 객체
	 */
	private void test3() {
		try {
			URL url = new URL("https://search.naver.com:443/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%8A%88%ED%81%AC%EB%A6%BC%EB%9D%BC%EB%96%BC#abc");
			URLConnection conn = url.openConnection();
			
			try (
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new FileWriter("슈크림라떼검색.html"));
			){
				String data = null;
				while((data = br.readLine()) != null) {
					System.out.println(data);
					bw.write(data);
					bw.newLine(); // 개행
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * URL
	 * - url을 parsing해서 각 정보를 관리하는 객체
	 */
	private void test2() {
		try {
			URL url = new URL("https://search.naver.com:443/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%8A%88%ED%81%AC%EB%A6%BC%EB%9D%BC%EB%96%BC#abc");
			
			System.out.println(url.getProtocol()); // https 웹통신규약, ftp | telnet | ssh....
			System.out.println(url.getHost()); // search.naver.com
			System.out.println(url.getPort());
			System.out.println(url.getPath()); // /search.naver
			System.out.println(url.getQuery()); // where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%8A%88%ED%81%AC%EB%A6%BC%EB%9D%BC%EB%96%BC
			System.out.println(url.getRef()); // 북마킹
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * InetAddress
	 * - ip주소에 대한 정보를 가진 클래스
	 * - 생성자가 없고, static메소드만 지원
	 */
	private void test1() {
		try {
			InetAddress naver = InetAddress.getByName("naver.com");
			System.out.println(naver.getHostAddress());
			
			InetAddress[] navers = naver.getAllByName("naver.com"); // naver.com에 등록된 모든 ip
			System.out.println(navers.length);
			for(InetAddress ip : navers) {
				System.out.println(ip.getHostAddress());
			}
			
			// 내컴퓨터 localhost
			InetAddress localhost = InetAddress.getLocalHost(); 
			System.out.println(localhost.getHostAddress());
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
