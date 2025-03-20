public class CurrencyConverter {

    private static final long EUR_TO_RON_RATE = 4974000; /// represent 4.9749 as 4974900 (scaled up by 1000000)
    private static final long USD_TO_RON_RATE = 4800000;
    private static final long EUR_TO_USD_RATE = 1040000;

    private static final long SCALE = 1000000;

    /// scaling factor for the rates
    /// was scaled up by a factor of 100 once , for the rate , amount must remain * 1000000
    /// long is the integer representation of one balance from db
    /// if the result is a float , last 4 will be cropped
    /// long * double (4.98) would work so must do long*4980000/1000000

    public static long convertEURToRON(long amountEUR) {
        return amountEUR * EUR_TO_RON_RATE / SCALE;
    }

    public static long convertRONToEUR(long amountRON) {
        return amountRON * SCALE / EUR_TO_RON_RATE;
    }

    public static long convertUSDToRON(long amountUSD) {
        return amountUSD * USD_TO_RON_RATE / SCALE;
    }

    public static long convertRONToUSD(long amountRON) {
        return amountRON * SCALE / USD_TO_RON_RATE;
    }

    public static long convertEURToUSD(long amountEUR) {
        return amountEUR * EUR_TO_USD_RATE / SCALE;
    }

    public static long convertUSDToEUR(long amountUSD) {
        return amountUSD * SCALE / EUR_TO_USD_RATE;
    }
}
