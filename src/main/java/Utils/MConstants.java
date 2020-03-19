package Utils;

public class MConstants {
    //region Checker Constant Strings
    public static final String CHECKER_STATUS_COMPLETE = "Done completely";
    public static final String SIGNAL_CHECKER_STATUS_FAILED_RO = "Telefonul a fost mutat";
    public static final String SIGNAL_CHECKER_STATUS_FAILED_EN = "The device was moved";
    public static final String TEST_PASSED_RO = "Testul a trecut";
    public static final String TEST_PASSED_EN = "Test passed";
    public static final String TEST_FAILED_RO = "Testul a picat";
    public static final String TEST_FAILED_EN = "Test failed";
    public static final String TEST_NEUTRAL_RO = "Neutru";
    public static final String TEST_NEUTRAL_EN = "Neutral";
    public static final int NUM_OF_CHECKS = 7;
    //endregion

    //region Checking Signal
    public static final String SIGNAL_CHECKER = "sigcheck";
    public static final String SIGNAL_CHECKING_TEXT_RO = "Verificare putere semnal";
    public static final String SIGNAL_CHECKING_TEXT_EN = "Checking signal power";
    public static final int SIGNAL_CHECKING_TEXT_VIEW_ID = 9458654;
    public static final int SIGNAL_CHECKING_STATUS_ID1 = 1000000;
    //endregion

    //region Checking Public DB
    public static final String PUBLIC_DB_CHECKER = "pbdbcheck";
    public static final String PUBLIC_DB_CHECKING_TEXT_RO = "Verificare BD publica";
    public static final String PUBLIC_DB_CHECKING_TEXT_EN ="Checking public DB";
    public static final int PBDB_CHECKING_TEXT_VIEW_ID = 9458655;
    public static final int PBDB_CHECKING_STATUS_ID1 = 1000001;
    //endregion

    //region Checking Internal DB
    public static final String INTERNAL_DB_CHECKER = "intdbcheck";
    public static final String INTERNAL_DB_CHECKING_TEXT_RO = "Verificare BD interna";
    public static final String INTERNAL_DB_CHECKING_TEXT_EN ="Checking internal DB";
    public static final int INTERNAL_CHECKING_TEXT_VIEW_ID = 9458656;
    public static final int INTERNAL_CHECKING_STATUS_ID1 = 1000002;
    //endregion

    //region Checking Neighbour List
    public static final String NEIGHBOUR_LIST_CHECKER = "nblistcheck";
    public static final String NEIGHBOUR_LIST_TEXT_RO = "Verificare lista vecini";
    public static final String NEIGHBOUR_LIST_TEXT_EN ="Checking neighbour list";
    public static final int NEIGHBOUR_LIST_TEXT_VIEW_ID = 9458657;
    public static final int NEIGHBOUR_LIST_STATUS_ID1 = 1000003;
    public static final String SAMSUNG_PHONE_MODEL = "SAMSUNG";
    //endregion

    //region Checking Cell Consistency
    public static final String CELL_CONSISTENCY_CHECKER = "consistencytcheck";
    public static final String CELL_CONSISTENCY_TEXT_RO = "Verificare date celula";
    public static final String CELL_CONSISTENCY_TEXT_EN ="Checking cell consistency";
    public static final int CELL_CONSISTENCY_TEXT_VIEW_ID = 9458658;
    public static final int CELL_CONSISTENCY_STATUS_ID1 = 1000004;
    //endregion

    //region Checking Overall
    public static final String OVERALL_CHECKER = "overallcheck";
    public static final String OVERALL_PASSED_RO = "Celula apartine unui furnizor GSM";
    public static final String OVERALL_PASSED_EN ="The base station is real";
    public static final String OVERALL_FAILED_RO = "Celula este un IMSI Catcher!";
    public static final String OVERALL_FAILED_EN ="The cell is an IMSI Catcher!";
    public static final int OVERALL_TEXT_VIEW_ID = 9458659;
    //endregion

    //region Checking Conectivity
    public static final String CONNECTIVITY_CHECKER = "connectcheck";
    //endregion


    public static final class TextViewParameters {
        public static final String SHARE_TECH_MONO_FONT = "share_tech_mono";
        public static final String BLACK_COLOR_ID = "#000000";
        public static final String GREEN_COLOR_ID = "#014a21";
        public static final String RED_COLOR_ID = "#a90000";
        public static final String BLEU_COLOR_ID = "#00bcd4";
    }

    public static final class AppLanguages {
        public static final String RO_LANG = "ro";
        public static final String EN_LANG = "en";
    }

    public static final class Cell {
        public static final String GOOD = "GOOD";
        public static final String WARNING = "WARNING";
        public static final String ALERT = "ALERT";
    }

    public static final class Map_Choices {
        public static final String ALL = "ALL";
        public static final String GOOD = "GOOD";
        public static final String WARNING = "WARNING";
        public static final String ALERT = "ALERT";
    }

    //endregion

    //region Global Constants
    public static final String STRING_EMPTY = "";
    //endregion


    public static class FirebaseHelper {
        public static final String EXISTS_IN_GOOD = "Exists in GOOD";
        public static final String EXISTS_IN_WARNING = "Exists in WARNING";
        public static final String EXISTS_IN_ALERT = "Exists in ALERT";
    }

    public static class Database {
        public static final String DATABASE_NAME = "imsiCatcherDetector.db";
        public static final int VERSION = 1;

        public static class SIGNAL_TABLE {
            public static final String TABLE_NAME = "SIGNAl";
            public static final String KEY_ID = "ID";
            public static final String CELL_ID = "CELL_ID";
            public  static final String KEY_SIGNAL_VALUE = "SIGNAL_VALUE";
        }

    }
}
