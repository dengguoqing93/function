package org.guoqing.functional.function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public interface Result<T> {
    static <T> Result<T> failure(String message) {
        return new Failure(message);
    }

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    void bind(Effect<T> success, Effect<String> failure);

    class Success<T> implements Result<T> {
        private T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            success.apply(value);
        }
    }

    class Failure<T> implements Result<T> {
        private final String message;

        private Failure(String message) {
            this.message = message;
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            failure.apply(message);
        }

        public String getMessage() {
            return message;
        }
    }
}
