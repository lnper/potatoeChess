package board;

public class CommonMethods {
    public static int convert(char C) {
        int I = 0;
        switch (C) {
            case 'a':
                I = Character.getNumericValue('0');
                break;
            case 'b':
                I = Character.getNumericValue('1');
                break;
            case 'c':
                I = Character.getNumericValue('2');
                break;
            case 'd':
                I = Character.getNumericValue('3');
                break;
            case 'e':
                I = Character.getNumericValue('4');
                break;
            case 'f':
                I = Character.getNumericValue('5');
                break;
            case 'g':
                I = Character.getNumericValue('6');
                break;
            case 'h':
                I = Character.getNumericValue('7');
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
                I = 8-Character.getNumericValue(C);
                break;
        }
        return I;
    }
}
