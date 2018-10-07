package org.guoqing.java_functional.function;

import java.util.function.Function;
import java.util.regex.Pattern;

import static function.Case.match;
import static function.Case.matchCase;
import static function.Result.failure;
import static function.Result.success;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public class EmailValidation {
    static Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\" + ".[a-z]{2,4}$");
    static Function<String, Result> emailChecker = s -> {
        if (s == null) {
            return failure("email must not be null");
        } else if (s.length() == 0) {
            return failure("email must not be empty");
        } else if (emailPattern.matcher(s).matches()) {
            return success(s);
        } else {
            return failure("email " + s + " is invalid");
        }
    };

    static Effect<String> success = s -> System.out.println("mail sent to " + s);
    static Effect<String> failure =
            s -> System.out.println("error message " + "logged:" + s);
    static Function<String, Result<String>> emailValid =
            s -> match(matchCase(() -> success(s)), matchCase(() -> s == null,
                    () -> failure("email must not be null")),
                    matchCase(() -> s.length() == 0,
                            () -> failure("email must not be empty")),
                    matchCase(() -> !emailPattern.matcher(s).matches(),
                            () -> failure("email " + s + " is invalid.")));

    private static void logError(String s) {
        System.out.println("Error message logged: " + s);
    }

    private static void sendVerificationMail(String s) {
        System.out.println("Mail sent to" + s);
    }

    static void validate(String s) {
        Result result = emailChecker.apply(s);
        if (result instanceof Result.Success) {
            sendVerificationMail(s);
        } else {
            logError(((Result.Failure) result).getMessage());
        }
    }

    public static void main(String[] args) {
        validate("this.is@my.email");
        validate("");
        validate(null);
        validate("john.doe@acme.com");
        System.out.println("=============");
        emailChecker.apply("this.is@my.email").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("john.doe@acme.com").bind(success, failure);

        System.out.println("-------");
        emailValid.apply("this.is@my.email").bind(success, failure);
        emailValid.apply(null).bind(success, failure);
        emailValid.apply("").bind(success, failure);
        emailValid.apply("john.doe@acme.com").bind(success, failure);
    }
}
