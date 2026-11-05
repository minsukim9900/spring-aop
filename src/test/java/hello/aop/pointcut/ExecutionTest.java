package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("printMethod")
    void printMethod() throws Exception {
        // public java.lang.String hello.aop.member.annotation.MemberServiceImpl.hello(java.lang
        // .String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName("exactMatch")
    void exactMatch() throws Exception {
        pointcut.setExpression(
                "execution(public String hello.aop.member.MemberServiceImpl.hello(String))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("allMatch")
    void allMatch() throws Exception {
        pointcut.setExpression("execution(* *(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("nameMatch")
    void nameMatch() throws Exception {
        pointcut.setExpression("execution(* hello(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("nameMatchStar1")
    void nameMatchStar1() throws Exception {
        pointcut.setExpression("execution(* hel*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("nameMatchStar2")
    void nameMatchStar2() throws Exception {
        pointcut.setExpression("execution(* *el*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("nameMatchFalse")
    void nameMatchFalse() throws Exception {
        pointcut.setExpression("execution(* nono(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("packageExactMatch1")
    void packageExactMatch1() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("packageExactMatch2")
    void packageExactMatch2() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("packageExactFalse")
    void packageExactFalse() throws Exception {
        pointcut.setExpression("execution(* hello.aop.*.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("packageMatchSubPackage1")
    void packageMatchSubPackage1() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("packageMatchSubPackage2")
    void packageMatchSubPackage2() throws Exception {
        pointcut.setExpression("execution(* hello.aop..*.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("typeExactMatch")
    void typeExactMatch() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("typeMatchSuperType")
    void typeMatchSuperType() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("typeMatchInternal1")
    void typeMatchInternal1() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("typeMatchInternal2")
    void typeMatchInternal2() throws Exception {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("argsMatch")
    void argsMatch() throws Exception {
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("argsMatchNoArgs")
    void argsMatchNoArgs() throws Exception {
        pointcut.setExpression("execution(* *())");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("argsMatchStar")
    void argsMatchStar() throws Exception {
        pointcut.setExpression("execution(* *(*))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("argsMatchAll")
    void argsMatchAll() throws Exception {
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("argsMatchComplex")
    void argsMatchComplex() throws Exception {
        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
