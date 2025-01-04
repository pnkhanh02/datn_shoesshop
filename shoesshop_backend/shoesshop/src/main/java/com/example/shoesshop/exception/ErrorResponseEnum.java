package com.example.shoesshop.exception;

public enum ErrorResponseEnum {
    NOT_FOUND_PRODUCT(404, "Không tìm thấy sản phẩm"),
    NOT_FOUND_ACCOUNT(404, "Không tìm thấy người dùng"),
    USERNAME_EXISTED(400, "Username đã tồn tại!"),
    EMAIL_EXISTED(400, "Email đã tồn tại"),
    PASSWORD_LESSTHAN_6CHARACTERS(400, "Phải nhập mật khẩu tối thiểu 6 ký tự"),
    NOT_ENOUGH_INFORMATION_COLOR_SIZE(400, "Hãy chọn đủ thông tin (màu sắc, size, số lượng)"),
    NOT_ENOUGH_QUANTITY_OF_PRODUCT(400, "Số lượng sản phẩm hiện tại không đủ"),
    OUT_OF_PRODUCT(400, "Sản phẩm này đã hết hàng"),

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
