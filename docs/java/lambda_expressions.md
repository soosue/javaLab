### 접근 3 : local 클래스로 검색 기준 코드 만들기

다음 메소드는 당신이 특정한 검색 기준을 만족한 회원들을 출력합니다:

    public static void printPersons(
        List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

이 메소드는 List 파라미터 roster에 포함된 각각의 Person 인스턴스가 CheckPerson 파라미터 tester에 특정된 검색 기준을 만족하는지 안하는지 tester.test 메소드를 실행시킴으로써 체크해줍니다.

검색 기준을 특정하기 위해서, 당신은 CheckPerson 인터페이스를 구현해야합니다.

    interface CheckPerson {
        boolean test(Person p);
    }
다음 클래스는 test 메소드를 특정함으로써 CheckPerson 인터페이스를 구현했습니다. 이 메소드는 회원들을 필터링 합니다: 각 사람이 남자고, 18살에서 25살이면 true를 반환합니다. 

    class CheckPersonEligibleForSelectiveService implements CheckPerson {
        public boolean test(Person p) {
            return p.gender == Person.Sex.MALE &&
                p.getAge() >= 18 &&
                p.getAge() <= 25;
        }
    }
이 클래스를 사용하기 위해서는, 당신은 이 클래스의 새로운 객체를 생성 해야하고 printPersons 메소드를 실행해야합니다:   

    printPersons(
        roster, new CheckPersonEligibleForSelectiveService());

비록 이런 접근법이 덜 취약하지만(당신이 Person의 구조를 바꾼다 해도 해당 메소드를 다시 작성할 필요가 없습니다.) 당신이 어플리케이션에서 수행하려는 각 검색에 대한 새로운 인터페이스와 local 클래스라는 추가적인 코드가 남아있습니다. CheckPersonEligibleForSelectiveService는 인터페이스를 구현했기 때문에, 로컬 클래스 대신에 익명 클래스를 사용할 수 있으며, 각 검색에 대해 새로운 클래스를 선언하지 않을 수는 있습니다. 


### 접근 4 : 익명 클래스로 검색 기준 코드 만들기
다음 printPersons 메소드의 arguments 중 하나는 익명 클래스입니다. (그 익명 클래스는 남자고, 18~25살인 회원인지 필터링합니다.)

    printPersons(
        roster,
        new CheckPerson() {
        public boolean test(Person p) {
            return p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25;
            }
        }
    );
이 접근법은 필요한 코드의 양을 줄여줍니다. 각각의 수행해야하는 검색에 대해 새로운 클래스를 만들 필요가 없기 때문이죠. 하지만 익명 클래스 구문은 CheckPerson 인터페이스가 하나의 메소드만 가지고 있는걸 생각하면 다소 덩치가 큽니다. 이런 경우 익명 클래스 대신 람다 표현식을 쓸 수 있습니다. 다음 섹션에서 보죠.

Approach 5: Specify Search Criteria Code with a Lambda Expression
The CheckPerson interface is a functional interface. A functional interface is any interface that contains only one abstract method. (A functional interface may contain one or more default methods or static methods.) Because a functional interface contains only one abstract method, you can omit the name of that method when you implement it. To do this, instead of using an anonymous class expression, you use a lambda expression, which is highlighted in the following method invocation:

### 접근 5 : 람다 표현식으로 검색 기준 코드 만들기
CheckPerson 인터페이스는 함수형인터페이스입니다. 함수형인터페이스란 단 한 개의 abstract 메소드를 가진 모든 인터페이스를 말합니다. (함수형인터페이스는 하나 이상의 default 메소드나 static 메소드들을 가지고 있을 수 있습니다.) 함수형인터페이스가 오직 한 개의 abstract 메소드를 가지고 있기 때문에 해당 메소드를 구현할 때 이름을 생략할 수 있습니다. 그래서 익명클래스 표현을 쓰는 대신에 람다 표현식을 쓸 수 있고, 다음 메소드에 확인할 수 있습니다.

    printPersons(
        roster,
        (Person p) -> p.getGender() == Person.Sex.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25
    );
람다 표현식이 어떻게 정의되는지에 대한 정보를 알고 싶다면 람다 표현식 구문을 보세요.

당신은 함수형인터페이스의 표준을 CheckPerson 인터페이스 대신에 사용할 수 있습니다. 필요한 코드의 양은 더 줄어들죠.

### 접근 6 : 표준적인 함수형인터페이스와 람다 표현식 사용하기
CheckPerson 인터페이스를 다시 봅시다:

    interface CheckPerson {
        boolean test(Person p);
    }
이건 매우 간단한 인터페이스입니다. 그리고 단 한 개의 abstract 메소드가 있으므로, 함수형인터페이스입니다. 이 메소드는 한 개의 파라미터를 받아들이고, 결과로 boolean을 반환합니다. 이 메소드는 매우 간단해서 어플리케이션에서 정의할 가치가 없을 지도 모릅니다. 결과적으로 JDK는 몇가지 표준적인 함수형인터페이스들을 정의해놓았고, java.util.function 패키지에서 찾을 수 있습니다.  

예를 들면, Predicate<T> 인터페이스를 CheckPerson 자리에 쓸 수 있습니다. 이 인터페이스는 boolean test(T t); 메소드를 가지고 있습니다.

    interface Predicate<T> {
        boolean test(T t);
    }
인터페이스 Predicate<T>는 제네릭 인터페이스의 예입니다. (제네릭에 관한 많은 정보를 위해, 제네릭을 봐주세요.) 제네릭 타입은(제네릭 인터페이스 같은) <>안의 한 개 또는 그 이상의 타입 파라미터들을 특정합니다. 이 인터페이스는 단 한개의 타입 파라미터 T를 가지고 있습니다. 실제 타입 arguments를 사용해서 제네릭 타입을 선언하거나 인스턴스화 할 때, 파라미터화된 타입이 생성됩니다. 예를 들면 밑에 파라미터화 된 Predicate<Person>가 있습니다.

    interface Predicate<Person> {
        boolean test(Person t);
    }
이 파라미터화 된 타입은 CheckPerson.boolean test(Person p)와 똑같은 반환 타입과 파라미터들을 가진 메소드를 가집니다. 결과적으로 Predicate<T>를 CheckPerson 자리에 대신 사용할 수 있고, 밑에서 그걸 보여주고 있습니다.

    public static void printPersonsWithPredicate(
        List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }
As a result, the following method invocation is the same as when you invoked printPersons in Approach 3: Specify Search Criteria Code in a Local Class to obtain members who are eligible for Selective Service:
그 결과, 다음 메소드 실행은 회원들을 필터링하기 했던 접근 3 : local class로 검색 기준 코드 만들기의 printPersons를 실행했을 때와 같습니다.

    printPersonsWithPredicate(
        roster,
        p -> p.getGender() == Person.Sex.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25
    );
저 부분만이 이 메소드에서 람다 표현식을 쓸 수 있는 것이 아닙니다. 다음 접근법에서 람다 표현식을 쓰는 다른 방법들을 보겠습니다. 


### 접근 7 : 어플리케이션 전체에서 람다 표현식 쓰기
람다 표현식을 어디에 쓸 수 있을지 printPersonsWithPredicate 메소드를 다시 봅시다:

    public static void printPersonsWithPredicate(
        List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }
이 메소드는 List 파라미터 roster의 각 Person 인스턴스가 Predicate 파라미터 tester의 기준을 만족하는지 안하는지 확인합니다. 만약 Person instance가 tester에 의한 기준을 만족한다면, printPerson 메소드는 Person 인스턴스에 대해 실행됩니다. 

printPerson 메소드를 실행하는 대신에, tester의 기준을 만족한 Person 인스턴스들에게 다른 동작을 수행하도록 특정할 수 있습니다. 그 동작을 람다 표현식으로 특정할 수 있습니다. 하나의 argument(Person 타입의 객체)를 받고, void를 반환하는 printPerson 같이 람다 표현식을 쓰고 싶다고 가정합니다. 기억하세요, 람다 표현식을 쓰고 싶으면 함수형인터페이스를 구현해야 합니다. 이 경우에는 Person 타입의 한 개의 인자를 받고, void를 반환할 수 있는 abstract 메소드를 가진 함수형인터페이스가 필요합니다. Consumer<T> 인터페이스가 그런 특성을 가진 void accept(T t) 메소드를 가지고 있습니다. 다음 메소드는 p.printPerson()을 accept메소드를 실행시키는 Consumer<Person> 인스턴스로 교체하였습니다:

    public static void processPersons(
        List<Person> roster,
        Predicate<Person> tester,
        Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }
그 결과, 다음 메소드 실행은 접근 3 : local 클래스로 검색 기준 코드 만들기의 printPersons를 실행했을 때와 같습니다. 회원들을 출력하기 위한 람다 표현식이 표시되어 있습니다.

    processPersons(
        roster,
        p -> p.getGender() == Person.Sex.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25,
        p -> p.printPerson()
    );
What if you want to do more with your members' profiles than printing them out. Suppose that you want to validate the members' profiles or retrieve their contact information? In this case, you need a functional interface that contains an abstract method that returns a value. The Function<T,R> interface contains the method R apply(T t). The following method retrieves the data specified by the parameter mapper, and then performs an action on it specified by the parameter block:
building_java_projects_with_gradle.md
public static void processPersonsWithFunction(
List<Person> roster,
Predicate<Person> tester,
Function<Person, String> mapper,
Consumer<String> block) {
for (Person p : roster) {
if (tester.test(p)) {
String data = mapper.apply(p);
block.accept(data);
}
}
}
The following method retrieves the email address from each member contained in roster who is eligible for Selective Service and then prints it:

processPersonsWithFunction(
roster,
p -> p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25,
p -> p.getEmailAddress(),
email -> System.out.println(email)
);
Approach 8: Use Generics More Extensively
Reconsider the method processPersonsWithFunction. The following is a generic version of it that accepts, as a parameter, a collection that contains elements of any data type:

public static <X, Y> void processElements(
Iterable<X> source,
Predicate<X> tester,
Function <X, Y> mapper,
Consumer<Y> block) {
for (X p : source) {
if (tester.test(p)) {
Y data = mapper.apply(p);
block.accept(data);
}
}
}
To print the e-mail address of members who are eligible for Selective Service, invoke the processElements method as follows:

processElements(
roster,
p -> p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25,
p -> p.getEmailAddress(),
email -> System.out.println(email)
);
This method invocation performs the following actions:

Obtains a source of objects from the collection source. In this example, it obtains a source of Person objects from the collection roster. Notice that the collection roster, which is a collection of type List, is also an object of type Iterable.
Filters objects that match the Predicate object tester. In this example, the Predicate object is a lambda expression that specifies which members would be eligible for Selective Service.
Maps each filtered object to a value as specified by the Function object mapper. In this example, the Function object is a lambda expression that returns the e-mail address of a member.
Performs an action on each mapped object as specified by the Consumer object block. In this example, the Consumer object is a lambda expression that prints a string, which is the e-mail address returned by the Function object.
You can replace each of these actions with an aggregate operation.

Approach 9: Use Aggregate Operations That Accept Lambda Expressions as Parameters
The following example uses aggregate operations to print the e-mail addresses of those members contained in the collection roster who are eligible for Selective Service:

roster
.stream()
.filter(
p -> p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25)
.map(p -> p.getEmailAddress())
.forEach(email -> System.out.println(email));
The following table maps each of the operations the method processElements performs with the corresponding aggregate operation:

processElements Action	Aggregate Operation
Obtain a source of objects	Stream<E> stream()
Filter objects that match a Predicate object	Stream<T> filter(Predicate<? super T> predicate)
Map objects to another value as specified by a Function object	<R> Stream<R> map(Function<? super T,? extends R> mapper)
Perform an action as specified by a Consumer object	void forEach(Consumer<? super T> action)
The operations filter, map, and forEach are aggregate operations. Aggregate operations process elements from a stream, not directly from a collection (which is the reason why the first method invoked in this example is stream). A stream is a sequence of elements. Unlike a collection, it is not a data structure that stores elements. Instead, a stream carries values from a source, such as collection, through a pipeline. A pipeline is a sequence of stream operations, which in this example is filter- map-forEach. In addition, aggregate operations typically accept lambda expressions as parameters, enabling you to customize how they behave.

For a more thorough discussion of aggregate operations, see the Aggregate Operations lesson.

Lambda Expressions in GUI Applications
To process events in a graphical user interface (GUI) application, such as keyboard actions, mouse actions, and scroll actions, you typically create event handlers, which usually involves implementing a particular interface. Often, event handler interfaces are functional interfaces; they tend to have only one method.

In the JavaFX example HelloWorld.java (discussed in the previous section Anonymous Classes), you can replace the highlighted anonymous class with a lambda expression in this statement:

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
The method invocation btn.setOnAction specifies what happens when you select the button represented by the btn object. This method requires an object of type EventHandler<ActionEvent>. The EventHandler<ActionEvent> interface contains only one method, void handle(T event). This interface is a functional interface, so you could use the following highlighted lambda expression to replace it:

        btn.setOnAction(
          event -> System.out.println("Hello World!")
        );
Syntax of Lambda Expressions
A lambda expression consists of the following:

A comma-separated list of formal parameters enclosed in parentheses. The CheckPerson.test method contains one parameter, p, which represents an instance of the Person class.

Note: You can omit the data type of the parameters in a lambda expression. In addition, you can omit the parentheses if there is only one parameter. For example, the following lambda expression is also valid:

p -> p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25
The arrow token, ->

A body, which consists of a single expression or a statement block. This example uses the following expression:

p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25
If you specify a single expression, then the Java runtime evaluates the expression and then returns its value. Alternatively, you can use a return statement:

p -> {
return p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25;
}
A return statement is not an expression; in a lambda expression, you must enclose statements in braces ({}). However, you do not have to enclose a void method invocation in braces. For example, the following is a valid lambda expression:

email -> System.out.println(email)
Note that a lambda expression looks a lot like a method declaration; you can consider lambda expressions as anonymous methods—methods without a name.

The following example, Calculator, is an example of lambda expressions that take more than one formal parameter:


public class Calculator {

    interface IntegerMath {
        int operation(int a, int b);   
    }
  
    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }
 
    public static void main(String... args) {
    
        Calculator myApp = new Calculator();
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) -> a - b;
        System.out.println("40 + 2 = " +
            myApp.operateBinary(40, 2, addition));
        System.out.println("20 - 10 = " +
            myApp.operateBinary(20, 10, subtraction));    
    }
}

The method operateBinary performs a mathematical operation on two integer operands. The operation itself is specified by an instance of IntegerMath. The example defines two operations with lambda expressions, addition and subtraction. The example prints the following:

40 + 2 = 42
20 - 10 = 10
Accessing Local Variables of the Enclosing Scope
Like local and anonymous classes, lambda expressions can capture variables; they have the same access to local variables of the enclosing scope. However, unlike local and anonymous classes, lambda expressions do not have any shadowing issues (see Shadowing for more information). Lambda expressions are lexically scoped. This means that they do not inherit any names from a supertype or introduce a new level of scoping. Declarations in a lambda expression are interpreted just as they are in the enclosing environment. The following example, LambdaScopeTest, demonstrates this:


import java.util.function.Consumer;

public class LambdaScopeTest {

    public int x = 0;
 
    class FirstLevel {
 
        public int x = 1;
        
        void methodInFirstLevel(int x) {

            int z = 2;
             
            Consumer<Integer> myConsumer = (y) -> 
            {
                // The following statement causes the compiler to generate
                // the error "Local variable z defined in an enclosing scope
                // must be final or effectively final" 
                //
                // z = 99;
                
                System.out.println("x = " + x); 
                System.out.println("y = " + y);
                System.out.println("z = " + z);
                System.out.println("this.x = " + this.x);
                System.out.println("LambdaScopeTest.this.x = " +
                    LambdaScopeTest.this.x);
            };
 
            myConsumer.accept(x);
 
        }
    }
 
    public static void main(String... args) {
        LambdaScopeTest st = new LambdaScopeTest();
        LambdaScopeTest.FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel(23);
    }
}
This example generates the following output:

x = 23
y = 23
z = 2
this.x = 1
LambdaScopeTest.this.x = 0
If you substitute the parameter x in place of y in the declaration of the lambda expression myConsumer, then the compiler generates an error:

Consumer<Integer> myConsumer = (x) -> {
// ...
}
The compiler generates the error "Lambda expression's parameter x cannot redeclare another local variable defined in an enclosing scope" because the lambda expression does not introduce a new level of scoping. Consequently, you can directly access fields, methods, and local variables of the enclosing scope. For example, the lambda expression directly accesses the parameter x of the method methodInFirstLevel. To access variables in the enclosing class, use the keyword this. In this example, this.x refers to the member variable FirstLevel.x.

However, like local and anonymous classes, a lambda expression can only access local variables and parameters of the enclosing block that are final or effectively final. In this example, the variable z is effectively final; its value is never changed after it's initialized. However, suppose that you add the following assignment statement in the the lambda expression myConsumer:

Consumer<Integer> myConsumer = (y) -> {
z = 99;
// ...
}
Because of this assignment statement, the variable z is not effectively final anymore. As a result, the Java compiler generates an error message similar to "Local variable z defined in an enclosing scope must be final or effectively final".

Target Typing
How do you determine the type of a lambda expression? Recall the lambda expression that selected members who are male and between the ages 18 and 25 years:

p -> p.getGender() == Person.Sex.MALE
&& p.getAge() >= 18
&& p.getAge() <= 25
This lambda expression was used in the following two methods:

public static void printPersons(List<Person> roster, CheckPerson tester) in Approach 3: Specify Search Criteria Code in a Local Class

public void printPersonsWithPredicate(List<Person> roster, Predicate<Person> tester) in Approach 6: Use Standard Functional Interfaces with Lambda Expressions

When the Java runtime invokes the method printPersons, it's expecting a data type of CheckPerson, so the lambda expression is of this type. However, when the Java runtime invokes the method printPersonsWithPredicate, it's expecting a data type of Predicate<Person>, so the lambda expression is of this type. The data type that these methods expect is called the target type. To determine the type of a lambda expression, the Java compiler uses the target type of the context or situation in which the lambda expression was found. It follows that you can only use lambda expressions in situations in which the Java compiler can determine a target type:

Variable declarations

Assignments

Return statements

Array initializers

Method or constructor arguments

Lambda expression bodies

Conditional expressions, ?:

Cast expressions

Target Types and Method Arguments
For method arguments, the Java compiler determines the target type with two other language features: overload resolution and type argument inference.

Consider the following two functional interfaces ( java.lang.Runnable and java.util.concurrent.Callable<V>):

public interface Runnable {
void run();
}

public interface Callable<V> {
V call();
}
The method Runnable.run does not return a value, whereas Callable<V>.call does.

Suppose that you have overloaded the method invoke as follows (see Defining Methods for more information about overloading methods):

void invoke(Runnable r) {
r.run();
}

<T> T invoke(Callable<T> c) {
    return c.call();
}
Which method will be invoked in the following statement?

String s = invoke(() -> "done");
The method invoke(Callable<T>) will be invoked because that method returns a value; the method invoke(Runnable) does not. In this case, the type of the lambda expression () -> "done" is Callable<T>.

Serialization
You can serialize a lambda expression if its target type and its captured arguments are serializable. However, like inner classes, the serialization of lambda expressions is strongly discouraged.