package com.example.shoesshop.exception;

public enum ErrorResponseEnum {
    NOT_FOUND_PRODUCT(404, "Không tìm thấy sản phẩm"),
    NOT_FOUND_ACCOUNT(404, "Không tìm thấy người dùng"),
    USERNAME_EXISTED(400, "Username đã tồn tại!"),

    USERNAME_NOT_FOUND(401, "Username không tồn tại"),
    PASSWORD_FAILS(401, "Sai password"),
    ACC_NOT_ACTIVE(401, "Tài khoản chưa được kích hoạt, kiểm tra mail để kích hoạt tài khoản");

    public final int status;
    public final String message;
    ErrorResponseEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
