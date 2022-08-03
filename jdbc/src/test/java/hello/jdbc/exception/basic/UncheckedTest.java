package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {


    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(()->service.callThrow()).isInstanceOf(MyUncheckedException.class);
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /**
     * Unchecked 예외는
     * 예외를 잡거나 던지지 않아도 된다.
     * 예외를 던지지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();
        public void callCatch(){
            try{
                repository.call();
            }catch (MyUncheckedException e) {
                //예외 처리 로직
                log.info("예외 처리, message={}",e.getMessage(),e);
            }
        }

        /**
         * 예외를 잡지않아도 자동으로 상위로 넘어간다.
         * 체크 예외와 다르게 throws를 선언하지 않아도 된다.
         */
        public void callThrow(){
            repository.call();
        }
    }
    static class Repository {
        //언체크예외는 자동 throws된다.
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }
}
