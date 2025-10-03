package com.app.exception;

public class SeatAlreadyReservedException extends RuntimeException {
    public SeatAlreadyReservedException(int row, int seat) {
        super("Место row=" + row + ", seat=" + seat + " уже занято!");
    }
}
