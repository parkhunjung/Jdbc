package com.test.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Ex04_Stream {
	
	public static void main(String[] args) {
		
		//람다식을 배우는 목적
		//1. 익명 객체(익명 메소드)를 쉽게 만들기 위해서
		// ex) 컬렉션(제공 메소드)에서 많이 사용
		//2. 스트림에서 사용하기 위해
		
		
		//스트림, Stream
		// - JDK1.8에서 추가
		// - 배열(컬렉션)을 탐색하는 도구
		// - 람다식을 적극적으로 활용
		// - Iterator을 대신하는 경우가 많음
		
//		m1();
//		m2();
		m3();
		
		
		
	}

	private static void m3() {
			
		List<String> list = new ArrayList<String>();
		
		list.add("볼펜");
		list.add("마우스");
		list.add("샤프");
		list.add("지우개");
		list.add("노트");
		
		//정렬 + 출력
		// - Collections.sort(list)
		// - obj.sort((s1,s2) -> s1.compareTo(s2))
		
		Stream<String> stream = list.stream();
		stream = stream.sorted((s1, s2) -> s1.length() - s2.length());
		
		stream.forEach(s->System.out.println(s));
		System.out.println();
		
		list.stream().sorted().forEach(s -> System.out.println(s));
		
		list.stream()
		.sorted((s1, s2) -> s1.length() - s2.length())
		.forEach(s -> System.out.println(s));
		
		User[] list2 = {
				new User("홍길동", 20),
				new User("아무개", 22),
				new User("히히히", 29),
				new User("허허허", 25),
				new User("하하하", 27),
		};
		
		Arrays.stream(list2)
			.sorted((o1, o2) -> o1.getAge() - o2.getAge())
			.forEach(user -> System.out.println(user));
		
		Arrays.stream(list2)
			.sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
			.forEach(user -> System.out.println(user));
		
		
	}

	private static void m2() {
		
		String[] list = {"홍길동", "아무개", "호호호", "하하하"};
		
		
		Stream<String> stream =  Arrays.stream(list);
		
		stream.forEach(name -> System.out.println(name));
		System.out.println();
		
		stream.close();
		
		//스트림 : 전진 커서
		//다시 읽기 > 스트림도 재생성
		stream = Arrays.stream(list); //다시열기
		stream.forEach(name -> System.out.println(name));
		
		stream.close();
		
		
		
	}

	private static void m1() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("볼펜");
		list.add("마우스");
		list.add("샤프");
		list.add("지우개");
		list.add("노트");
		
		//목적] 요소 탐색(순서대로 항목 접근)
		//1. 일반 for문
		//2. 향상 for문
		//3. Iterator
		//4. Stream
		
		Iterator<String> iter = list.iterator();
		
		while(iter.hasNext()) { //rs.next
			System.out.println(iter.next()); //rs.getString
		}
		System.out.println();
		
		Stream<String> stream =  list.stream();
		
		Consumer<String> c1 = s -> System.out.println(s);
//		c1.accept("홍길동");
		
		stream.forEach(c1);
		
		
		list.stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		list.stream().forEach(s -> {
			if(s.length() >= 3) {
				System.out.println(s);
			}
		});
		System.out.println();
	}
	
}




























