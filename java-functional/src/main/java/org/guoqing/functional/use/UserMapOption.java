package org.guoqing.functional.use;

import org.guoqing.functional.collection.MapOption;
import org.guoqing.functional.utils.Option;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-10-18
 */
public class UserMapOption {
    public static void main(String[] args) {
        MapOption<String, Toon> toons = new MapOption<String, Toon>()
                .put("Mickey", new Toon("Mickey", "Mouse", "mickey@disney.com"))
                .put("Minnie", new Toon("Minnie", "Mouse"))
                .put("Donald", new Toon("Donald", "Duck", "donald@disney.com"));
        Option<String> mickey = toons.get("Mickey").flatMap(Toon::getEmail);
        Option<String> minnie = toons.get("Minnie").flatMap(Toon::getEmail);
        Option<String> goofy = toons.get("goofy").flatMap(Toon::getEmail);

        System.out.println(mickey);
        System.out.println(minnie);
        System.out.println(goofy);
    }

    static class Toon {
        private final String firstName;
        private final String lastName;
        private final Option<String> email;

        public Toon(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = Option.some(email);
        }

        public Toon(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = Option.none();
        }

        public Option<String> getEmail() {
            return email;
        }
    }
}
