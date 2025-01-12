package com.example.shoesshop.exception;

public enum ErrorResponseEnum {

    //user
    USERNAME_EXISTED(400, "Username đã tồn tại!"),
    EMAIL_EXISTED(400, "Email đã tồn tại"),
    PASSWORD_LESSTHAN_6CHARACTERS(400, "Phải nhập mật khẩu tối thiểu 6 ký tự"),
    FRISTNAME_NULL(400, "Hãy nhập Firstname"),
    LASTNAME_NULL(400, "Hãy nhập Lastname"),
    ADDRESS_NULL(400, "Hãy nhập địa chỉ"),
    BIRTHDAY_NULL(400, "Hãy nhập ngày sinh"),

    //cart
    NOT_ENOUGH_INFORMATION_COLOR_SIZE(400, "Hãy chọn đủ thông tin (màu sắc, size, số lượng)"),
    NOT_ENOUGH_QUANTITY_OF_PRODUCT(400, "Số lượng sản phẩm hiện tại không đủ"),
    OUT_OF_PRODUCT(400, "Sản phẩm này đã hết hàng"),

    USERNAME_NOT_FOUND(401, "Username không tồn tại"),
    PASSWORD_FAILS(401, "Sai password");

    public final int status;
    public final String message;
    ErrorResponseEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
