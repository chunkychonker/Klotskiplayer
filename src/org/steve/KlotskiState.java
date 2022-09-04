package org.steve;

import java.util.zip.CRC32;

public class KlotskiState {
    private int[][] State;

    public KlotskiState(int[][] a) {
        State = new int[a.length][a[0].length];
        copyBoard(a, State);
    }

    @Override
    public int hashCode() {
        CRC32 crc = new CRC32();
        for (int i = 0; i < State.length; i++) {
            for (int j = 0; j < State[0].length; j++) {
                crc.update(State[i][j]);
            }
        }
        return (int)crc.getValue();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof KlotskiState)) {
            return false;
        }
        KlotskiState otherState = (KlotskiState) other;
        // compare this.State and other.State
        // return true if the two States are the same, false if not
        return isSame(this.State, otherState.State);
    }

    private static void copyBoard (int[][] source, int[][] dest) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    private static boolean isSame(int[][] a, int[][] b) {

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}