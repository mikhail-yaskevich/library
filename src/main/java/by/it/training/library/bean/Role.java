package by.it.training.library.bean;

public enum Role {
    NONE,
    ADD_USER, EDIT_USER, REMOVE_USER,
    ADD_BOOK, EDIT_BOOK, REMOVE_BOOK, READ_BOOK, RESERVE_BOOK, TAKE_BOOK;

    public Role enable(boolean flag) {
        return flag ? this : NONE;
    }
}
