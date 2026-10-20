
public class CreditCardValidatorTest {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: " + name);
        }
    }

    private static void checkEquals(String name, Object expected, Object actual) {
        boolean ok = expected.equals(actual);
        if (!ok) {
            System.out.println("  expected: " + expected + "  actual: " + actual);
        }
        check(name, ok);
    }

    public static void main(String[] args) {

        checkEquals("visa", "Visa", CreditCardValidator.getCardType("4388576018410707"));
        checkEquals("mastercard", "MasterCard", CreditCardValidator.getCardType("5399831619690403"));
        checkEquals("amex", "American Express", CreditCardValidator.getCardType("378282246310005"));
        checkEquals("discover", "Discover", CreditCardValidator.getCardType("6011111111111117"));
        checkEquals("unknown prefix", "Invalid Card", CreditCardValidator.getCardType("234319283049582"));
        checkEquals("3 without 7", "Invalid Card", CreditCardValidator.getCardType("341111111111111"));

        
        checkEquals("double even sum", 37, CreditCardValidator.sumOfDoubleEvenPlaces("4388576018402626"));
        checkEquals("odd sum", 38, CreditCardValidator.sumOfOddPlaces("4388576018402626"));
        check("invalid example fails Luhn", !CreditCardValidator.passesLuhnCheck("4388576018402626"));
        check("valid example passes Luhn", CreditCardValidator.passesLuhnCheck("4388576018410707"));

       
        check("valid mastercard", CreditCardValidator.isValid("5399831619690403"));
        check("invalid mastercard", !CreditCardValidator.isValid("5399831619690404"));
        check("invalid card from brief", !CreditCardValidator.isValid("234319283049582"));
        check("valid amex", CreditCardValidator.isValid("378282246310005"));
        check("too short", !CreditCardValidator.isValid("400000000008"));
        check("too long", !CreditCardValidator.isValid("40000000000000006"));
        check("dashes rejected", !CreditCardValidator.isValid("4388-5760-1841-0707"));
        check("letters rejected", !CreditCardValidator.isValid("abcdefghijklmnop"));
        check("empty rejected", !CreditCardValidator.isValid(""));

       
        String banner = "*".repeat(40);
        String expected = String.join("\n",
                banner,
                "**Credit Card Type: MasterCard",
                "**Credit Card Number: 5399831619690403",
                "**Credit Card Digit Length: 16",
                "**Credit Card Validity Status: Valid",
                banner);
        checkEquals("valid output block", expected, CreditCardValidator.formatResult("5399831619690403"));

        String bad = CreditCardValidator.formatResult("234319283049582");
        check("invalid type line", bad.contains("**Credit Card Type: Invalid Card"));
        check("invalid length line", bad.contains("**Credit Card Digit Length: 15"));
        check("invalid status line", bad.contains("**Credit Card Validity Status: Invalid"));

        System.out.println(passed + " passed, " + failed + " failed");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
