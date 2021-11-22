#Test Fixtures
https://github.com/junit-team/junit4/wiki/Test-fixtures

test fixtures - 각 메소드, 클래스 단위로 메소드설정과 메소드정리를 명시하는 것.

텍스트 픽스쳐는 테스트를 실행할 때, 기준으로 사용되는 객체 집단의 고정된 상태입니다. 텍스트 픽스쳐의 목적은 잘 알고 고정된 환경 안에서 테스트들을 실행하여 결과가 반복적(같은 결과)으로 나오는 것을 확실시 하는 것입니다. 픽스쳐들의 예시들입니다:

- input data를 준비하고, 목 객체들을 설정하거나 생성하는 것
- 데이터베이스를 특정한 데이터나 알고 있는 데이터로 로딩하는 것
- 알고 있는 특정한 테스트 픽스쳐를 생성하는 파일들의 집합을 복사하는 것은 특정 상황으로 초기화 된 객체들의 집합을 생성해줍니다

JUnit은 어노테이션을 제공해서 테스트 클래스들이 매 테스트 전후로 픽스쳐를 실행시킬 수 있게 해줍니다, 또는

(JUnit provides annotations so that test classes can have fixture run before or after every test, or one time fixtures that run before and after only once for all test methods in a class.)

4개의 픽스쳐 어노테이션이 있습니다: 2개는 클래스-레벨의 픽스쳐이고 2개는 메소드 레벨의 픽스쳐입니다. 클래스 레벨에는 `@BeforeClass`와 `@AfterClass`가 있고, 메소드(또는 테스트) 레벨에서는 `@Before`과 `@After`가 있습니다.

픽스쳐에 대한 깊은 내용과 `Rules`을 통해서 어떻게 구현되는가는 [이곳](https://garygregory.wordpress.com/2011/09/25/understaning-junit-method-order-execution/) 에서 확인해주세요.

사용 예시:
~~~ java
package test;

import java.io.Closeable;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFixturesExample {
  static class ExpensiveManagedResource implements Closeable {
    @Override
    public void close() throws IOException {}
  }

  static class ManagedResource implements Closeable {
    @Override
    public void close() throws IOException {}
  }

  @BeforeClass
  public static void setUpClass() {
    System.out.println("@BeforeClass setUpClass");
    myExpensiveManagedResource = new ExpensiveManagedResource();
  }

  @AfterClass
  public static void tearDownClass() throws IOException {
    System.out.println("@AfterClass tearDownClass");
    myExpensiveManagedResource.close();
    myExpensiveManagedResource = null;
  }

  private ManagedResource myManagedResource;
  private static ExpensiveManagedResource myExpensiveManagedResource;

  private void println(String string) {
    System.out.println(string);
  }

  @Before
  public void setUp() {
    this.println("@Before setUp");
    this.myManagedResource = new ManagedResource();
  }

  @After
  public void tearDown() throws IOException {
    this.println("@After tearDown");
    this.myManagedResource.close();
    this.myManagedResource = null;
  }

  @Test
  public void test1() {
    this.println("@Test test1()");
  }

  @Test
  public void test2() {
    this.println("@Test test2()");
  }
}
~~~
결과는 다음처럼 나올겁니다:
~~~
@BeforeClass setUpClass
@Before setUp
@Test test2()
@After tearDown
@Before setUp
@Test test1()
@After tearDown
@AfterClass tearDownClass
~~~